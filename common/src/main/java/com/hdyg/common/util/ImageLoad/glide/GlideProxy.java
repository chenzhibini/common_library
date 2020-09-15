package com.hdyg.common.util.ImageLoad.glide;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hdyg.common.util.ImageLoad.IImagLoadProxy;
import com.hdyg.common.util.ImageLoad.ImageLoadConfiguration;
import com.hdyg.common.util.ImageLoad.glide.transform.BlurTransformation;
import com.hdyg.common.util.ImageLoad.glide.transform.GlideCircleTransform;
import com.hdyg.common.util.ImageLoad.glide.transform.GlideRoundTransform;

/**
 * Created by Administrator on 2018/3/11.
 */
@SuppressWarnings("unchecked")
public class GlideProxy implements IImagLoadProxy {


    @Override
    public void load(ImageLoadConfiguration imageLoadCfg) {
        RequestManager glideRequest = Glide.with(imageLoadCfg.context);
        DrawableTypeRequest dtRequest = glideRequest.load(imageLoadCfg.url);
        if (imageLoadCfg.isCircle)
            dtRequest.bitmapTransform(new GlideCircleTransform(imageLoadCfg.context));
        if (imageLoadCfg.round != 0)
            dtRequest.bitmapTransform(new GlideRoundTransform(imageLoadCfg.context, imageLoadCfg.round));
        if (imageLoadCfg.isBlur)    // 模糊
            dtRequest.bitmapTransform(new BlurTransformation(imageLoadCfg.context));
//        if(imageLoadCfg.isGray) dtRequest.bitmapTransform(new GrayscaleTransformation(imageLoadCfg.context));

        if (imageLoadCfg.isBitmap) dtRequest.asBitmap();
        if (imageLoadCfg.isGif) dtRequest.asGif();

        if (imageLoadCfg.placeholder instanceof Integer && (int) imageLoadCfg.placeholder != 0)
            dtRequest.placeholder((int) imageLoadCfg.placeholder);
        else if (imageLoadCfg.placeholder instanceof Drawable && (Drawable) imageLoadCfg.placeholder != null)
            dtRequest.placeholder((Drawable) imageLoadCfg.placeholder);

        if (imageLoadCfg.error instanceof Integer && (int) imageLoadCfg.error != 0)
            dtRequest.placeholder((int) imageLoadCfg.error);
        else if (imageLoadCfg.error instanceof Drawable && (Drawable) imageLoadCfg.error != null)
            dtRequest.placeholder((Drawable) imageLoadCfg.error);

        dtRequest.skipMemoryCache(imageLoadCfg.skipMemoryCache);
        if (imageLoadCfg.diskCacheStrategy != null)
            dtRequest.diskCacheStrategy(imageLoadCfg.diskCacheStrategy);
        if (imageLoadCfg.centerCrop) dtRequest.centerCrop();
        if (imageLoadCfg.fitCenter) dtRequest.fitCenter();
        dtRequest.priority(imageLoadCfg.priority);
        if (imageLoadCfg.transform != null) dtRequest.transform(imageLoadCfg.transform);
        if (imageLoadCfg.thumbnailF != 0) dtRequest.thumbnail(imageLoadCfg.thumbnailF);
        if (imageLoadCfg.thumbnailUrl != null) dtRequest.thumbnail(imageLoadCfg.thumbnailUrl);
        if (imageLoadCfg.animateInt != 0) dtRequest.animate(imageLoadCfg.animateInt);
        if (imageLoadCfg.animator != null) dtRequest.animate(imageLoadCfg.animator);
        if (imageLoadCfg.dontAnimate) dtRequest.dontAnimate();

        if (imageLoadCfg.listener != null) dtRequest.listener(imageLoadCfg.listener);
        if (imageLoadCfg.pauseRequests) glideRequest.pauseRequests();
        if (imageLoadCfg.resumeRequests) glideRequest.resumeRequests();
        if (imageLoadCfg.clearMemory) Glide.get(imageLoadCfg.context).clearMemory();
        if (imageLoadCfg.clearDiskCache) Glide.get(imageLoadCfg.context).clearDiskCache();

//if (imageLoadCfg.lruBitmapPool!=null) Glide.get(imageLoadCfg.context).setMemoryCategory();
//        if (imageLoadCfg.diskCache!=null)
//        if (imageLoadCfg.decodeFormat != null) glideRequest (imageLoadCfg.decodeFormat);
//        if (imageLoadCfg.diskCacheService!=null)
//            if (imageLoadCfg.resizeService!=null)

        if (imageLoadCfg.imageWidth != 0 && imageLoadCfg.imageHeight != 0)
            dtRequest.override(imageLoadCfg.imageWidth, imageLoadCfg.imageHeight);

        if (imageLoadCfg.simpleTarget != null) dtRequest.into(imageLoadCfg.simpleTarget);
        if (imageLoadCfg.viewTarget != null) dtRequest.into(imageLoadCfg.viewTarget);
        if (imageLoadCfg.imageView != null) dtRequest.into(imageLoadCfg.imageView);
    }
}
