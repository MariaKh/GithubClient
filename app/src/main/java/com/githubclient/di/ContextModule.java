package com.githubclient.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 1 on 3/27/2018.
 */

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    public Context context(){ return context.getApplicationContext(); }
}
