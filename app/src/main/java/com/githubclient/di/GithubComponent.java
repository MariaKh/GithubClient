package com.githubclient.di;

import com.githubclient.Application;
import com.githubclient.db.dao.RepoDao;
import com.githubclient.ui.details.DetailsActivity;
import com.githubclient.ui.details.DetailsViewModel;
import com.githubclient.ui.search.MainActivity;
import com.githubclient.ui.search.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 1 on 3/27/2018.
 */
@Singleton
@Component(modules = {NetworkModule.class, PicassoModule.class, ContextModule.class, DatabaseModule.class})
public interface GithubComponent {
    void inject(MainActivity mainActivity);

    void inject(Application application);

    void inject(MainViewModel mainViewModel);

    void inject(DetailsActivity detailsActivity);

    void inject(DetailsViewModel detailsViewModel);
}
