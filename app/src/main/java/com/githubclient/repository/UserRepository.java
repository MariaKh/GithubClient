package com.githubclient.repository;

import com.githubclient.model.User;
import com.githubclient.network.GithubApi;
import com.githubclient.network.response.UserApiResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1 on 3/27/2018.
 */

public class UserRepository {

    @Inject
    GithubApi githubApi;

    @Inject
    public UserRepository() {
    }

    public Single<List<User>> getUsers(String critetia){
        return githubApi.getUsers(critetia)
                .subscribeOn(Schedulers.io())
                .map(UserApiResponse::getUsers)
                ;
    }
}
