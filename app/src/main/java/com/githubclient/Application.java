package com.githubclient;

import android.databinding.DataBindingUtil;

import com.githubclient.di.ContextModule;
import com.githubclient.di.DaggerGithubComponent;
import com.githubclient.di.GithubComponent;

/**
 * Created by 1 on 3/27/2018.
 */

public class Application extends android.app.Application {

    private static GithubComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerGithubComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        DataBindingUtil.setDefaultComponent(appComponent);
        appComponent.inject(this);
    }

    public static GithubComponent getAppComponent() {
        return appComponent;
    }
}
