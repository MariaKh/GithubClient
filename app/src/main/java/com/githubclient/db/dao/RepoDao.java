package com.githubclient.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.githubclient.model.Repo;

import java.util.List;

/**
 * Created by 1 on 3/28/2018.
 */
@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(List<Repo> repositories);

    @Query("SELECT * FROM repo WHERE owner_login = :login")
    LiveData<List<Repo>> getUserRepos(String login);
}
