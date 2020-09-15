package com.hdyg.common.util;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * @author CZB
 * @describe js 访问 android JavaScriptinterface类
 * @time 2018/12/14 17:54
 */

@SuppressWarnings("unused")
public class JavaScriptinterface {

    Context mContext;
    WebView mWebView;

    public JavaScriptinterface(Context mContext, WebView mWebView) {
        this.mContext = mContext;
        this.mWebView = mWebView;
    }

    /**
     * 分享
     *
     * @param shareUrl    分享链接
     * @param title       标题
     * @param description 描述
     * @param imgUrl      图片地址
     */
    @JavascriptInterface
    public void shareWebMethod(String shareUrl, String title, String description, String imgUrl) {
        LogUtils.d("调用了分享");

    }

    /**
     * 获取缓存大小 eg:1M
     *
     * @return 缓存大小
     */
    @JavascriptInterface
    public String getCacheSize() {
        String cache = "";
        try {
            cache = StringUtil.getTotalCacheSize(mContext);
        } catch (Exception e) {
            LogUtils.e("获取缓存异常==>" + e.getMessage());
        }
        return cache;
    }

    /**
     * 清除缓存
     */
    @JavascriptInterface
    public void clearCache() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringUtil.clearAllCache(mContext);
            }
        });
    }

}
