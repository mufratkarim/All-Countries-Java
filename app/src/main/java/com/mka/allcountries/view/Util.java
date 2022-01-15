package com.mka.allcountries.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Util {
    public static void loadImage(ImageView view, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(android.R.drawable.stat_sys_download)
                .error(android.R.drawable.stat_notify_error);
        Glide.with(view.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(view);
    }
}
