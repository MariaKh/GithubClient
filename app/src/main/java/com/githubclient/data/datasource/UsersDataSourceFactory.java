package com.githubclient.data.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

import com.githubclient.model.User;
import com.githubclient.network.GithubApi;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 1 on 3/29/2018.
 */

public class UsersDataSourceFactory extends DataSource.Factory<Long, User> {

    private CompositeDisposable compositeDisposable;

    private MutableLiveData<UsersDataSource> usersDataSourceLiveData = new MutableLiveData<>();

    GithubApi api;

    private String query;

    @Inject
    public UsersDataSourceFactory(){

    }

    public UsersDataSourceFactory(CompositeDisposable compositeDisposable,GithubApi api , String query) {
        this.compositeDisposable = compositeDisposable;
        this.api = api;
        this.query = query;
    }

    @Override
    public DataSource<Long, User> create() {
        UsersDataSource usersDataSource = new UsersDataSource(compositeDisposable, api);
        usersDataSource.setSearchQuery(query);
        usersDataSourceLiveData.postValue(usersDataSource);
        return usersDataSource;
    }

    @NonNull
    public MutableLiveData<UsersDataSource> getUsersDataSourceLiveData() {
        return usersDataSourceLiveData;
    }
}
