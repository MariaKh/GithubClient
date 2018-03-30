package com.githubclient.ui.details;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.githubclient.Application;
import com.githubclient.R;
import com.githubclient.databinding.ActivityDetailsBinding;
import com.githubclient.model.Repo;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {


    private RepoAdapter adapter;
    private DetailsViewModel viewModel;
    private int listItemHorizontalBorderOffset;
    private int listItemVerticalBorderOffset;
    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getAppComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        viewModel = ViewModelProviders.of(DetailsActivity.this).get(DetailsViewModel.class);
        binding.loadMoreBar.setVisibility(View.VISIBLE);
        viewModel.getRepos().observe(DetailsActivity.this, repos -> {
            adapter.updateRepos((ArrayList<Repo>) repos);
            binding.loadMoreBar.setVisibility(View.GONE);
        });
        binding.setUser(viewModel.getSelectedUser());
       setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        adapter = new RepoAdapter(this);
        adapter.updateRepos((ArrayList<Repo>) viewModel.getRepos().getValue());
        binding.repoList.setHasFixedSize(true);
        binding.repoList.setAdapter(adapter);
        listItemHorizontalBorderOffset = getResources().getDimensionPixelOffset(R.dimen.list_item_horizontal_padding);
        listItemVerticalBorderOffset = getResources().getDimensionPixelOffset(R.dimen.list_item_vertical_padding);
        binding.repoList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(listItemHorizontalBorderOffset, listItemVerticalBorderOffset, listItemHorizontalBorderOffset, listItemVerticalBorderOffset);
            }
        });
    }

}
