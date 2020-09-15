package com.hdyg.common.util.ImageLoad;

/**
 * Created by Administrator on 2018/3/11.
 */

public class ImageLoadProxy implements IImagLoadProxy {

    private static IImagLoadProxy mIImagLoadProxy;
    private static ImageLoadProxy _instance;

    private ImageLoadProxy() {
    }

    public static ImageLoadProxy obtain() {
        synchronized (ImageLoadProxy.class) {
            if (_instance == null) {
                _instance = new ImageLoadProxy();
            }
        }
        return _instance;
    }

    /**
     * 初始化
     *
     * @param imagLoadProxy
     */
    public static void init(IImagLoadProxy imagLoadProxy) {
        // 初始化代理对象
        mIImagLoadProxy = imagLoadProxy;
    }


    @Override
    public void load(ImageLoadConfiguration imageLoadConfiguration) {
        if (mIImagLoadProxy == null || imageLoadConfiguration == null) return;
        mIImagLoadProxy.load(imageLoadConfiguration);
    }
}
