package com.githubclient.repository;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.githubclient.data.NetworkState;
import com.githubclient.data.datasource.UsersDataSource;
import com.githubclient.data.datasource.UsersDataSourceFactory;
import com.githubclient.model.User;
import com.githubclient.network.GithubApi;
import com.githubclient.network.response.UserApiResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1 on 3/27/2018.
 */
@Singleton
public class UserRepository {

    @Inject
    GithubApi githubApi;

    private CompositeDisposable compositeDisposable;

    private UsersDataSourceFactory usersDataSourceFactory;

    private static final int pageSize = 20;

    private String searchQuery;

    @Inject
    public UserRepository() {
    }

    private User selectedUser;

    private LiveData<PagedList<User>> init() {
        compositeDisposable = new CompositeDisposable();
        usersDataSourceFactory = new UsersDataSourceFactory(compositeDisposable, githubApi, searchQuery);
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                //  .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build();

        return new LivePagedListBuilder<>(usersDataSourceFactory, config).build();
    }


    public LiveData<PagedList<User>> startSearch(String query) {
        this.searchQuery = query;
        return init();
    }

    public LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(usersDataSourceFactory.getUsersDataSourceLiveData(), UsersDataSource::getNetworkState);
    }

    public void dispose() {
        compositeDisposable.dispose();
    }

    public void setSelectedUser(User user) {
        this.selectedUser = user;
    }

    public User getSelectedUser() {
        return selectedUser;
    }
}
