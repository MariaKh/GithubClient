package com.githubclient.network;

import com.githubclient.model.Repo;
import com.githubclient.network.response.RepoApiResponse;
import com.githubclient.network.response.UserApiResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 1 on 3/27/2018.
 */

public interface GithubApi {

    @GET("search/users")
    Single<UserApiResponse> getUsers(@Query("q") String criteria);

    @GET("users/{login}/repos")
    Single<List<Repo>> getRepos(@Path("login") String login);
}
