package com.githubclient.ui.search;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;


import com.githubclient.Application;
import com.githubclient.data.NetworkState;
import com.githubclient.data.datasource.UsersDataSource;
import com.githubclient.data.datasource.UsersDataSourceFactory;
import com.githubclient.model.User;
import com.githubclient.network.GithubApi;
import com.githubclient.repository.RepoRepository;
import com.githubclient.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 1 on 3/27/2018.
 */
public class MainViewModel extends ViewModel {

    LiveData<PagedList<User>> userList;

    @Inject
    UserRepository userRepository;


    private String query = "";


    public MainViewModel() {
        Application.getAppComponent().inject(this);
    }

    public void startSearch(LifecycleOwner lifecycleOwner, String query) {
        if (userList!=null) {
            userList.removeObservers(lifecycleOwner);
        }
        userList = userRepository.startSearch(query);
        this.query = query;
    }

    public LiveData<NetworkState> getNetworkState() {
        return userRepository.getNetworkState();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userRepository.dispose();
    }

    public void setSelectedUser(User user) {
        userRepository.setSelectedUser(user);
    }

}
