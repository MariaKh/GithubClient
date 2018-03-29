package com.githubclient.data.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import com.githubclient.data.NetworkState;
import com.githubclient.model.User;
import com.githubclient.network.GithubApi;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1 on 3/29/2018.
 */

public class UsersDataSource extends ItemKeyedDataSource<Long, User> {

    private GithubApi githubService;

    private CompositeDisposable compositeDisposable;

    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    private String searchQuery;

    @Inject
    public UsersDataSource() {

    }

    UsersDataSource(CompositeDisposable compositeDisposable, GithubApi api) {
        this.compositeDisposable = compositeDisposable;
        this.githubService = api;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<User> callback) {
        networkState.postValue(NetworkState.LOADING);
        compositeDisposable.add(githubService.getUsers(searchQuery, params.requestedLoadSize).subscribe(users -> {
                    networkState.postValue(NetworkState.LOADED);
                    callback.onResult(users.getUsers());
                },
                throwable -> {
                    NetworkState error = NetworkState.error(throwable.getMessage());
                    networkState.postValue(error);
                }));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
        networkState.postValue(NetworkState.LOADING);
        compositeDisposable.add(githubService.getUsers(searchQuery, params.requestedLoadSize).subscribe(users -> {
                    networkState.postValue(NetworkState.LOADED);
                    callback.onResult(users.getUsers());
                },
                throwable -> {
                    networkState.postValue(NetworkState.error(throwable.getMessage()));
                }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
    }

    @NonNull
    @Override
    public Long getKey(@NonNull User item) {
        return item.getId();
    }

    @NonNull
    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
