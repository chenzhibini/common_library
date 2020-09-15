package com.hdyg.common.util.ImageLoad.glide.listener;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Administrator on 2018/3/11.
 */

public class FitxyListener implements RequestListener<String, GlideDrawable> {
    ImageView imageView;

    public FitxyListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        if (imageView == null) {
            return false;
        }
        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
        float scale = (float) vw / (float) resource.getIntrinsicWidth();
        int vh = Math.round((float) resource.getIntrinsicHeight() * scale);
        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
        imageView.setLayoutParams(params);
        return false;
    }
}
