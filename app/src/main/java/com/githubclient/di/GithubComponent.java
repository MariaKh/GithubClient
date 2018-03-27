package com.githubclient.di;

import com.githubclient.Application;
import com.githubclient.ImageBindingAdapter;
import com.githubclient.ui.GithubViewModel;
import com.githubclient.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 1 on 3/27/2018.
 */
@Singleton
@Component(modules = {NetworkModule.class, PicassoModule.class, ContextModule.class})
public interface GithubComponent {
    void inject(MainActivity mainActivity);

    void inject(Application application);

    void inject(GithubViewModel githubViewModel);
}
