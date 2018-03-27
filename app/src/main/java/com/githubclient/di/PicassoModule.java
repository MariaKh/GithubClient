package com.githubclient.di;


import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 1 on 3/27/2018.
 */
@Singleton
@Module
public class PicassoModule {
    @Provides
    public Picasso picasso(Context context){
        return new Picasso.Builder(context).
                build();
    }
}
