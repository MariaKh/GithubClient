package com.githubclient.di;

import com.githubclient.binding.ImageBindingAdapter;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 1 on 3/27/2018.
 */
@Module
public class BindingModule {

    @Provides
    ImageBindingAdapter provideImageBindingAdapter(Picasso picasso) {
        return new ImageBindingAdapter(picasso);
    }
}
