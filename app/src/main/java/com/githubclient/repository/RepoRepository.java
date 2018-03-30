package com.githubclient.repository;

import com.githubclient.model.Repo;
import com.githubclient.network.GithubApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1 on 3/27/2018.
 */

public class RepoRepository {
    @Inject
    GithubApi githubApi;

    @Inject
    public RepoRepository() {
    }

    public Single<List<Repo>> getRepositories(String login){
        return githubApi.getRepos(login)
                .subscribeOn(Schedulers.io())
                ;
    }
}
