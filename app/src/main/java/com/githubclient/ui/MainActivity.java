package com.githubclient.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.githubclient.Application;
import com.githubclient.R;
import com.githubclient.model.Repo;
import com.githubclient.model.User;
import com.githubclient.network.GithubApi;
import com.githubclient.network.response.UserApiResponse;
import com.githubclient.repository.RepoRepository;
import com.githubclient.repository.UserRepository;
import com.githubclient.ui.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

//    @Inject
//    UserRepository repository;
//    @Inject
//    RepoRepository repoRepository;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Application.getAppComponent().inject(this);

        GithubViewModel model = ViewModelProviders.of(MainActivity.this).get(GithubViewModel.class);

        searchField = findViewById(R.id.search_field);
        searchField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                model.searchUsers(searchField.getText().toString()).observe(MainActivity.this, users -> {
                    adapter.updateUsers((ArrayList<User>) users);
                });
                return true;
            }
            return false;
        });

        recyclerView = findViewById(R.id.users_list);
        adapter = new UserAdapter(this, user -> {
//                repoRepository.getRepositories(user.getLogin())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(repos -> {
//                                    Toast.makeText(getApplicationContext(),"size="+repos.size(),Toast.LENGTH_SHORT).show();
//                                }
//                                ,error->{
//
//                                });
        });
        adapter.updateUsers((ArrayList<User>) model.getUsers().getValue());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
