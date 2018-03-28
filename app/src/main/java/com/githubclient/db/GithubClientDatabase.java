package com.githubclient.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.githubclient.db.dao.RepoDao;
import com.githubclient.db.dao.UserDao;
import com.githubclient.model.Repo;
import com.githubclient.model.User;

/**
 * Created by 1 on 3/28/2018.
 */
@Database(entities = {User.class, Repo.class}, version = 1)
public abstract class GithubClientDatabase extends RoomDatabase {

    abstract public UserDao userDao();

    abstract public RepoDao repoDao();

}
