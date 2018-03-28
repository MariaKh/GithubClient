package com.githubclient;

import android.databinding.DataBindingUtil;

import com.githubclient.di.BindingComponent;
import com.githubclient.di.ContextModule;
import com.githubclient.di.DaggerBindingComponent;
import com.githubclient.di.DaggerGithubComponent;
import com.githubclient.di.DatabaseModule;
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
        BindingComponent bindingComponent = DaggerBindingComponent.builder()
                .contextModule(new ContextModule(this))
                //.applicationComponent(appComponent)
                .build();
        DataBindingUtil.setDefaultComponent(bindingComponent);
        bindingComponent.inject(this);
        appComponent.inject(this);
    }

    public static GithubComponent getAppComponent() {
        return appComponent;
    }
}
