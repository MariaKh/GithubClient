package com.githubclient.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by 1 on 3/27/2018.
 */

public class ImageBindingAdapter {

    private final Picasso picasso;

    public ImageBindingAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    @BindingAdapter("android:src")
    public void loadImage(ImageView view, String url) {
        picasso.load(url).fit().into(view);
    }
}
