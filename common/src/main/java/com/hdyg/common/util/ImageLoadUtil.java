package com.hdyg.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.hdyg.common.R;
import com.hdyg.common.util.ImageLoad.ImageLoadConfiguration;
import com.hdyg.common.util.ImageLoad.ImageLoadProxy;
import com.hdyg.common.util.ImageLoad.glide.listener.FitxyListener;
import com.hdyg.common.util.ImageLoad.glide.transform.GlideCircleTransform;
import com.hdyg.common.util.ImageLoad.glide.transform.GlideRoundTransform;
import java.io.File;
import java.io.InputStream;

/**
 * 图片加载工具
 *
 * @author
 * @time 2018/3/11 18:59
 */
public class ImageLoadUtil {
    /**
     * 图片加载
     *
     * @param mContext
     * @param url
     * @param imageView
     * @param imageType
     */
    public static void imageLoad(Context mContext, String url, ImageView imageView, ImageType imageType) {
        if (((Activity) mContext).isDestroyed()) {
            LogUtils.e("glide异常====mContext已被销毁");
        } else {
            ImageLoadConfiguration.Builder builder = new ImageLoadConfiguration.Builder(mContext)
                    .url(url)
                    .dontAnimate()
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(imageView);
            if (imageType != null) {
                if (imageType.equals(ImageType.CIRCLE)) {
                    // 圆图
                    builder.isCircle(true);
                } else if (imageType.equals(ImageType.BLUR)) {
                    // 模糊
                    builder.isBlur(true);
                } else if (imageType.equals(ImageType.FITXY)) {
                    // 铺满 FIT_XY 模式
                    builder.listener(new FitxyListener(imageView));
                }
            }
            ImageLoadProxy.obtain().load(builder.build());
        }
    }

    /**
     * 图片加载
     *
     * @param mContext
     * @param url
     * @param imageView
     * @param imageType
     */
    public static void imageLoadNoDefault(Context mContext, String url, ImageView imageView, ImageType imageType) {
        if (((Activity) mContext).isDestroyed()) {
            LogUtils.e("glide异常====mContext已被销毁");
        } else {
            ImageLoadConfiguration.Builder builder = new ImageLoadConfiguration.Builder(mContext)
                    .url(url)
                    .dontAnimate()
                    .into(imageView);
            if (imageType != null) {
                if (imageType.equals(ImageType.CIRCLE)) {
                    // 圆图
                    builder.isCircle(true);
                } else if (imageType.equals(ImageType.BLUR)) {
                    // 模糊
                    builder.isBlur(true);
                } else if (imageType.equals(ImageType.FITXY)) {
                    // 铺满 FIT_XY 模式
                    builder.listener(new FitxyListener(imageView));
                }
            }
            ImageLoadProxy.obtain().load(builder.build());
        }
    }

    /**
     * 加载圆角图片
     *
     * @param mContext
     * @param url
     * @param imageView
     * @param roundNum
     */
    public static void imageLoad(Context mContext, Object url, ImageView imageView, int roundNum) {
        if (((Activity) mContext).isDestroyed()) {
            LogUtils.e("glide异常====mContext已被销毁");
        } else {
            ImageLoadConfiguration.Builder builder = new ImageLoadConfiguration.Builder(mContext)
                    .url(url)
                    .dontAnimate()
                    .round(roundNum)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(imageView);
            ImageLoadProxy.obtain().load(builder.build());
        }
    }
    /**
     * 加载圆角图片
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void imageLoad(Context mContext, Object url, ImageView imageView) {
        if (((Activity) mContext).isDestroyed()) {
            LogUtils.e("glide异常====mContext已被销毁");
        } else {
            ImageLoadConfiguration.Builder builder = new ImageLoadConfiguration.Builder(mContext)
                    .url(url)
                    .dontAnimate()
                    .into(imageView);
            ImageLoadProxy.obtain().load(builder.build());
        }
    }

    /**
     * 加载LinearLayout背景图
     *
     * @param context
     * @param url
     * @param linearLayout
     */
    public static void loadLinearLayoutBg(Context context, String url, LinearLayout linearLayout) {
        Glide.with(context).load(url)
                .into(new ViewTarget<View, GlideDrawable>(linearLayout) {
                    //括号里为需要加载的控件
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            this.view.setBackground(resource.getCurrent());
                        }
                    }
                });
    }

    /**
     * 加载圆角图片
     *
     * @param context   上下文
     * @param object    图片地址
     * @param imageView 图片控件
     */
    public static void loadRoundImage(Context context, Object object, ImageView imageView) {
        if (((Activity) context).isDestroyed()) {
            LogUtils.e("glide异常====mContext已被销毁");
        } else {
            Glide.with(context)
                    .load(object)
                    .transform(new CenterCrop(context), new GlideRoundTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .into(imageView);
        }
    }

    /**
     * 设置圆形图片
     *
     * @param object        图片{地址、资源等}
     * @param defaultImgRes 默认图片
     * @param imageView     ImageView 控件
     */
    public static void loadCircleImage(Context mContext, Object object, int defaultImgRes, ImageView imageView) {
        if (((Activity) mContext).isDestroyed()) {
            LogUtils.e("glide异常====mContext已被销毁");
        } else {
            // 将图片资源转换为 Bitmap 格式
            Bitmap bitmap = resToBitmap(mContext, defaultImgRes);
            // 获取原图
            Bitmap roundBitmap = getRoundBitmap(bitmap);
            // 将 Biemap 格式图片转换为 BitmapDrawable 格式
            BitmapDrawable bitmapDrawable = (BitmapDrawable) bitmapToDrawable(roundBitmap);

            ImageLoadProxy.obtain().load(new ImageLoadConfiguration.Builder(mContext)
                    .url(object)
                    .isBitmap(true)
                    .skipMemoryCache(true) // 不使用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                    .error(bitmapDrawable)
                    .transform(new GlideCircleTransform(mContext))
                    .into(imageView)
                    .build());
        }
    }

    /**
     * 加载本地图片
     *
     * @param mContext  上下文
     * @param file      文件
     * @param imageView 视图
     */
    public static void loadNativeImage(Context mContext, File file, ImageView imageView) {
        Glide.with(mContext)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载gif图
     * @param mContext
     * @param object
     * @param imageView
     */
    public static void loadGif(Context mContext,Object object,ImageView imageView){
        if (((Activity) mContext).isDestroyed()) {
            LogUtils.e("glide异常====mContext已被销毁");
        }else {
            Glide.with(mContext)
                    .load(object)
                    .asGif()
                    .crossFade()
                    .placeholder(R.drawable.logo)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.logo)
                    .into(imageView);
        }
    }


    /**
     * 资源图片转换为 Bitmap 格式图片
     *
     * @param mContext
     * @param imgRes   图片资源
     * @return
     */
    public static Bitmap resToBitmap(Context mContext, int imgRes) {
        InputStream is = mContext.getResources().openRawResource(imgRes);
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 获取圆图
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        //取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        //构建一个bitmap
        Bitmap roundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(roundBitmap);
        Paint p = new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return roundBitmap;
    }

    /**
     * bitmap 格式图片转 drawable 资源
     *
     * @param bitmap
     * @return
     */
    public static BitmapDrawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }


}
