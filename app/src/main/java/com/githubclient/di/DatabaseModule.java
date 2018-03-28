package com.githubclient.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.githubclient.db.GithubClientDatabase;
import com.githubclient.db.dao.RepoDao;
import com.githubclient.db.dao.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 1 on 3/28/2018.
 */
@Singleton
@Module
public class DatabaseModule {

    @Singleton
    @Provides
    GithubClientDatabase provideDb(Context app) {
        return Room.databaseBuilder(app, GithubClientDatabase.class,"githubclient.db").build();
    }

    @Singleton
    @Provides
    UserDao provideUserDao(GithubClientDatabase db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    RepoDao provideRepoDao(GithubClientDatabase db) {
        return db.repoDao();
    }
}
