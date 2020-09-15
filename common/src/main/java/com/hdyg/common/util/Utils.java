package com.hdyg.common.util;

import android.app.Application;
import com.hdyg.common.BuildConfig;

/**
 * <p>Utils初始化相关 </p>
 */
public class Utils {

    private static Application application;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param application 上下文
     */
    public static void init(Application application) {
        Utils.application = application;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Application getApp() {
        if (application != null) return application;
        throw new NullPointerException("u should init first");
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return BuildConfig.DEBUG;
    }


}