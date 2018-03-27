package com.githubclient.network.response;

import com.githubclient.model.Repo;
import com.githubclient.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 1 on 3/27/2018.
 */

public class RepoApiResponse {

    @SerializedName("items")
    private List<Repo> users;
}
