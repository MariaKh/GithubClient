package com.githubclient.repository;

import com.githubclient.model.User;
import com.githubclient.network.GithubApi;
import com.githubclient.network.response.UserApiResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1 on 3/27/2018.
 */
@Singleton
public class UserRepository {

    @Inject
    GithubApi githubApi;

    @Inject
    public UserRepository() {
    }

    private User selectedUser;

    public Single<List<User>> getUsers(String critetia, int page){
        return githubApi.getUsers(critetia, page)
                .subscribeOn(Schedulers.io())
                .map(UserApiResponse::getUsers)
                ;
    }

    public void setSelectedUser(User user){
        this.selectedUser = user;
    }

    public User getSelectedUser() {
        return selectedUser;
    }
}
