package com.githubclient.di;

import android.content.Context;
import android.databinding.DataBindingComponent;

import com.githubclient.Application;
import com.githubclient.ImageBindingAdapter;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 1 on 3/27/2018.
 */

@DataBinding
@Singleton
@Component(modules = {BindingModule.class,NetworkModule.class, PicassoModule.class, ContextModule.class})
public interface BindingComponent extends android.databinding.DataBindingComponent {

    void inject(ImageBindingAdapter imageBindingAdapter);

    void inject(Application application);
}
