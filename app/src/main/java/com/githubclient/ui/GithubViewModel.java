package com.githubclient.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.githubclient.Application;
import com.githubclient.model.User;
import com.githubclient.repository.RepoRepository;
import com.githubclient.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 1 on 3/27/2018.
 */

public class GithubViewModel extends ViewModel{
    @Inject
    UserRepository userRepository;
    @Inject
    RepoRepository repoRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<User>> users = new MutableLiveData<>();


    public GithubViewModel(){
        Application.getAppComponent().inject(this);
    }

    public LiveData<List<User>> searchUsers(String criteria){
        loadUsers(criteria);
        return users;
    }

    public LiveData<List<User>> getUsers(){
        return users;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

    private void loadUsers(String criteria){
        disposables.add(userRepository.getUsers(criteria)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users::setValue
                        ,error->{

                        })
        );
    }
}
