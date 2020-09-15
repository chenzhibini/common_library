package com.hdyg.common.util.ImageLoad;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by Administrator on 2018/3/11.
 */
@SuppressWarnings("unchecked")
public class ImageLoadConfiguration {

    public final Context context;
    public ImageView imageView = null;
    public final Object url;
    public final int defaultImageResId;
    public final boolean isCircle;
    public final int round;
    public final boolean isBlur;
    public final boolean isGray;
    public final int imageWidth;
    public final int imageHeight;
    public final int imageMaxWidth;
    public final int imageMaxHeight;

    public final boolean isBitmap;       // 是否为静态图
    public final boolean isGif;          // 是否为动态图
    public final Object placeholder;        // 占位图，加载时显示的图片
    public final Object error;              // 加载失败时显示的图片
    public boolean skipMemoryCache = false;    // 跳过内存缓存
    public DiskCacheStrategy diskCacheStrategy = null;   // 磁盘缓存
    public final boolean centerCrop;     //
    public final boolean fitCenter;      //
    public Priority priority = Priority.NORMAL;      // 优先级
    public BitmapTransformation[] transform = null; // 转换
    public final float thumbnailF;       // 缩略图一，参数[0,1]
    public DrawableRequestBuilder<String> thumbnailUrl = null;    // 缩略图二
    public final int animateInt;         // int 类型的动画资源
    public ViewPropertyAnimation.Animator animator = null;    // Animator 类型的属性动画
    public final boolean dontAnimate;     // 是否取消动画
    public SimpleTarget simpleTarget = null;  //
    public ViewTarget viewTarget = null;
    public RequestListener listener = null;   // 监听
    public boolean pauseRequests;      // 停止加载图片
    public boolean resumeRequests;     // 继续加载图片
    public boolean clearMemory;        // 是否清理内存缓存
    public boolean clearDiskCache;     // 是否清理磁盘缓存
//    public final DiskLruCacheFactory diskCache = null;    //
//    public final DecodeFormat decodeFormat = null;  // 编码格式
//    public final LruBitmapPool lruBitmapPool = null;    //
//    public final ExecutorService diskCacheService = null;
//    public final ExecutorService resizeService = null;

    public ImageLoadConfiguration(Builder builder) {
        context = builder.context;
        imageView = builder.imageView;
        url = builder.url;
        defaultImageResId = builder.defaultImageResId;
        isCircle = builder.isCircle;
        isGray = builder.isGray;
        isBlur = builder.isBlur;
        round = builder.round;
        imageWidth = builder.imageWidth;
        imageHeight = builder.imageHeight;
        imageMaxWidth = builder.imageMaxWidth;
        imageMaxHeight = builder.imageMaxHeight;
        isBitmap = builder.isBitmap;
        isGif = builder.isGif;
        placeholder = builder.placeholder;
        error = builder.error;
        skipMemoryCache = builder.skipMemoryCache;
        diskCacheStrategy = builder.diskCacheStrategy;
        centerCrop = builder.centerCrop;
        fitCenter = builder.fitCenter;
        priority = builder.priority;
        transform = builder.transform;
        thumbnailF = builder.thumbnailF;
        thumbnailUrl = builder.thumbnailUrl;
        animateInt = builder.animateInt;
        animator = builder.animator;
        dontAnimate = builder.dontAnimate;
        simpleTarget = builder.simpleTarget;
        viewTarget = builder.viewTarget;
        listener = builder.listener;
        pauseRequests = builder.pauseRequests;
        resumeRequests = builder.resumeRequests;
        clearMemory = builder.clearMemory;
        clearDiskCache = builder.clearDiskCache;
//        diskCache = builder.diskCache;
//        decodeFormat = builder.decodeFormat;
//        lruBitmapPool = builder.lruBitmapPool;
//        diskCacheService = builder.diskCacheService;
//        resizeService = builder.resizeService;
    }


    public static final class Builder {

        private Context context;        // 上下文对象
        private ImageView imageView;    // ImageView
        private Object url;             // 加载地址
        private int defaultImageResId;  //
        private boolean isCircle;       // 是否是圆形图
        private int round;              // 圆角图片
        private boolean isBlur;         // 模糊处理
        private boolean isGray;         // 是否是灰度图
        private int imageWidth;         // 图片宽度
        private int imageHeight;        // 图片高度
        private int imageMaxWidth;      // 最大宽度
        private int imageMaxHeight;     // 最大高度

        private boolean isBitmap;       // 是否为静态图
        private boolean isGif;          // 是否为动态图
        private Object placeholder;        // 占位图，加载时显示的图片
        private Object error;              // 加载失败时显示的图片
        private boolean skipMemoryCache;    // 是否使用内存缓存
        private DiskCacheStrategy diskCacheStrategy;
        private boolean centerCrop;     //
        private boolean fitCenter;      //
        private Priority priority;      // 优先级
        private BitmapTransformation[] transform; // 转换
        private float thumbnailF;       // 缩略图一，参数[0,1]
        private DrawableRequestBuilder<String> thumbnailUrl;    // 缩略图二
        private int animateInt;         // int 类型的动画资源
        private ViewPropertyAnimation.Animator animator;    // Animator 类型的属性动画
        private boolean dontAnimate;
        private SimpleTarget simpleTarget;  //
        private ViewTarget viewTarget;
        private RequestListener listener;   // 监听
        private boolean pauseRequests;      // 停止加载图片
        private boolean resumeRequests;     // 继续加载图片
        private boolean clearMemory;        // 是否清理内存缓存
        private boolean clearDiskCache;     // 是否清理磁盘缓存
//        private DiskCache.Factory diskCache;    //
//        private DecodeFormat decodeFormat;  // 编码格式
//        private LruBitmapPool lruBitmapPool;    //
//        private ExecutorService diskCacheService;
//        private ExecutorService resizeService;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 地址
         *
         * @param url
         * @return
         */
        public Builder url(Object url) {
            this.url = url;
            return this;
        }

        public Builder defaultImageResId(int defaultImageResId) {
            this.defaultImageResId = defaultImageResId;
            return this;
        }

        /**
         * 是否圆形图片
         *
         * @param isCircle
         * @return
         */
        public Builder isCircle(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }

        /**
         * 圆角图片
         *
         * @param round
         * @return
         */
        public Builder round(int round) {
            this.round = round;
            return this;
        }

        /**
         * 模糊处理
         *
         * @param isBlur
         * @return
         */
        public Builder isBlur(boolean isBlur) {
            this.isBlur = isBlur;
            return this;
        }

        /**
         * 是否为灰度图片
         *
         * @param isGray
         * @return
         */
        public Builder isGray(boolean isGray) {
            this.isGray = isGray;
            return this;
        }

        public Builder imageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public Builder imageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public Builder imageMaxWidth(int imageMaxWidth) {
            this.imageMaxWidth = imageMaxWidth;
            return this;
        }

        public Builder imageMaxHeight(int imageMaxHeight) {
            this.imageMaxHeight = imageMaxHeight;
            return this;
        }

        public Builder into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        /**
         * 是否为静态图
         *
         * @param isBitmap
         * @return
         */
        public Builder isBitmap(boolean isBitmap) {
            this.isBitmap = isBitmap;
            return this;
        }

        /**
         * 是否为动态图
         *
         * @param isGif
         * @return
         */
        public Builder isGif(boolean isGif) {
            this.isGif = isGif;
            return this;
        }

        /**
         * 占位图，加载时显示的图片
         *
         * @param placeholder
         * @return
         */
        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }
        /**
         * 占位图，加载时显示的图片
         *
         * @param placeholder
         * @return
         */
        public Builder placeholder(Drawable placeholder) {
            this.placeholder = placeholder;
            return this;
        }
        /**
         * 加载失败显示的图片
         *
         * @param error
         * @return
         */
        public Builder error(int error) {
            this.error = error;
            return this;
        }
        /**
         * 加载失败显示的图片
         *
         * @param error
         * @return
         */
        public Builder error(Drawable error) {
            this.error = error;
            return this;
        }
        /**
         * 跳过内存缓存
         *
         * @param skipMemoryCache
         * @return
         */
        public Builder skipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        /**
         * 磁盘缓存模式
         * 缓存参数
         *
         * @param diskCacheStrategy ALL：缓存源资源和转换后的资源（即缓存所有版本图像，默认行为）
         *                          NONE：不作任何磁盘缓存，然而默认的它将仍然使用内存缓存
         *                          SOURCE：仅缓存源资源（原来的全分辨率的图像），上面例子里的1000x1000像素的图片
         * @return
         */
        public Builder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        /**
         * 设置图片尺寸
         *
         * @param imageWidth
         * @param imageHeight
         * @return
         */
        public Builder override(int imageWidth, int imageHeight) {
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            return this;
        }

        /**
         * 它是一个裁剪技术，即缩放图像让它填充到ImageView界限内并且裁剪额外的部分，
         * ImageView可能会完全填充，但图像可能不会完整显示
         *
         * @return
         */
        public Builder centerCrop() {
            this.centerCrop = true;
            return this;
        }

        /**
         * 它是一个裁剪技术，即缩放图像让图像都测量出来等于或小于ImageView的边界范围，
         * 该图像将会完全显示，但可能不会填满整个ImageView
         *
         * @return
         */
        public Builder fitCenter() {
            this.fitCenter = true;
            return this;
        }

        /**
         * 设置资源加载优先级
         *
         * @param priority Priority.LOW、Priority.NORMAL、Priority.HIGH、Priority.IMMEDIATE
         * @return
         */
        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 转换
         *
         * @param transform
         * @return
         */
        public Builder transform(BitmapTransformation... transform) {
            this.transform = transform;
            return this;
        }

        /**
         * 设置缩略图一
         *
         * @param thumbnailF 系数需在(0,1)之间，这样会先加载缩略图然后加载全图
         * @return
         */
        public Builder thumbnail(float thumbnailF) {
            this.thumbnailF = thumbnailF;
            return this;
        }

        /**
         * 设置缩略图二
         *
         * @param thumbnailUrl 缩略图需要从网络加载同样全分辨率图片
         * @return
         */
        public Builder thumbnail(DrawableRequestBuilder<String> thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        /**
         * 设置动画
         *
         * @param animateInt Int 类型的动画资源
         * @return
         */
        public Builder animate(int animateInt) {
            this.animateInt = animateInt;
            return this;
        }

        /**
         * 设置动画
         *
         * @param animator Animator 类型的属性动画
         * @return
         */
        public Builder animate(ViewPropertyAnimation.Animator animator) {
            this.animator = animator;
            return this;
        }

        /**
         * 取消动画
         *
         * @return
         */
        public Builder dontAnimate() {
            this.dontAnimate = true;
            return this;
        }

        /**
         * 定制view中使用SimpleTarget
         *
         * @param simpleTarget
         * @return
         */
        public Builder into(SimpleTarget simpleTarget) {
            this.simpleTarget = simpleTarget;
            return this;
        }

        /**
         * 定制view中使用ViewTarget
         *
         * @param viewTarget
         * @return
         */
        public Builder into(ViewTarget viewTarget) {
            this.viewTarget = viewTarget;
            return this;
        }

        /**
         * 设置监听请求接口
         *
         * @param listener
         * @return
         */
        public Builder listener(RequestListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 停止加载图片
         *
         * @return
         */
        public Builder pauseRequests() {
            this.pauseRequests = true;
            return this;
        }

        /**
         * 继续加载图片
         *
         * @return
         */
        public Builder resumeRequests() {
            this.resumeRequests = true;
            return this;
        }

        /**
         * 清理内存缓存，可以在UI主线程中进行
         */
        public Builder clearMemory() {
            this.clearMemory = true;
            return this;
        }

        /**
         * 清理磁盘缓存，必须在子线程中进行
         *
         * @return
         */
        public Builder clearDiskCache() {
            this.clearDiskCache = true;
            return this;
        }

//        /**
//         * 设置磁盘缓存位置、大小
//         *
//         * @param diskCache
//         * @return
//         */
//        public Builder setDiskCache(DiskLruCacheFactory diskCache) {
//            this.diskCache = diskCache;
//            return this;
//        }
//
//        /**
//         * 设置编码格式
//         *
//         * @param decodeFormat
//         * @return
//         */
//        public Builder setDecodeFormat(DecodeFormat decodeFormat) {
//            this.decodeFormat = decodeFormat;
//            return this;
//        }
//
//        /**
//         * 设置BitmapPool缓存内存大小
//         *
//         * @param lruBitmapPool
//         * @return
//         */
//        public Builder setBitmapPool(LruBitmapPool lruBitmapPool) {
//            this.lruBitmapPool = lruBitmapPool;
//            return this;
//        }
//
//        /**
//         * 设置用来检索cache中没有的Resource的ExecutorService
//         *
//         * @param diskCacheService
//         * @return
//         */
//        public Builder setDiskCacheService(ExecutorService diskCacheService) {
//            this.diskCacheService = diskCacheService;
//            return this;
//        }
//
//        /**
//         * 设置用来检索cache中没有的Resource的ExecutorService
//         *
//         * @param resizeService
//         * @return
//         */
//        public Builder setResizeService(ExecutorService resizeService) {
//            this.resizeService = resizeService;
//            return this;
//        }

        public ImageLoadConfiguration build() {
            return new ImageLoadConfiguration(this);
        }

    }
}