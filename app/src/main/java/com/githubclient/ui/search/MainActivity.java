package com.githubclient.ui.search;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.githubclient.Application;
import com.githubclient.R;
import com.githubclient.model.User;
import com.githubclient.ui.details.DetailsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserAdapter.UserAdapterOnItemClickHandler{

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private EditText searchField;
    private MainViewModel viewModel;
    private int listItemHorizontalBorderOffset;
    private int listItemVerticalBorderOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Application.getAppComponent().inject(this);

        viewModel = ViewModelProviders.of(MainActivity.this).get(MainViewModel.class);

        searchField = findViewById(R.id.search_field);
        searchField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                closeKeyBoard(v.getWindowToken());
                viewModel.searchUsers(searchField.getText().toString());
                return true;
            }
            return false;
        });
        viewModel.getUsers()
                .observe(this, users -> {
            adapter.updateUsers((ArrayList<User>) users);
        });
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        listItemHorizontalBorderOffset = getResources().getDimensionPixelOffset(R.dimen.list_item_horizontal_padding);
        listItemVerticalBorderOffset = getResources().getDimensionPixelOffset(R.dimen.list_item_vertical_padding);
        recyclerView = findViewById(R.id.users_list);
        adapter = new UserAdapter(this, this);
        adapter.updateUsers((ArrayList<User>) viewModel.getUsers().getValue());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(listItemHorizontalBorderOffset, listItemVerticalBorderOffset, listItemHorizontalBorderOffset, listItemVerticalBorderOffset);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        recyclerView.getLayoutManager();
                int lastPosition = layoutManager
                        .findLastVisibleItemPosition();
                if (lastPosition == adapter.getItemCount() - 1) {
                    viewModel.loadNextPage();
                }
            }
        });
    }

    private void openUserDetailsScreen(){
        Intent intent = new Intent(this,DetailsActivity.class);
        startActivity(intent);
    }

    private void closeKeyBoard(IBinder windowToken) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
    }

    @Override
    public void onItemClick(UserAdapter.UserViewHolder vh, User user) {
        viewModel.setSelectedUser(user);
        openUserDetailsScreen();
    }
}
