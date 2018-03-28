package com.githubclient.repository;

import com.githubclient.db.dao.UserDao;
import com.githubclient.model.User;
import com.githubclient.network.GithubApi;
import com.githubclient.network.response.UserApiResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1 on 3/27/2018.
 */
@Singleton
public class UserRepository {

    @Inject
    UserDao userDao;

    @Inject
    GithubApi githubApi;

    @Inject
    public UserRepository() {
    }

    private User selectedUser;

    private int pagesAmount;

    public Single<List<User>> getUsers(String critetia, int page){
        userDao.getAllUsers();
        return githubApi.getUsers(critetia, page)
                .subscribeOn(Schedulers.io())
                .doOnEvent((userApiResponse, throwable) -> {if (throwable != null) return;
                            pagesAmount = userApiResponse.getTotalCount() / 100 + (userApiResponse.getTotalCount() % 100 == 0 ? 0 : 1);
                        }
                )
                .map(UserApiResponse::getUsers)
                .doOnEvent((users, throwable) -> {
                    if (throwable != null) return;
                    userDao.insertUsers(users);
                })
        ;
    }

    public void setSelectedUser(User user){
        this.selectedUser = user;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }
}
