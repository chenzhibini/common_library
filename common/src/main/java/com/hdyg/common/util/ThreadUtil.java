package com.hdyg.common.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author CZB
 * @describe 用于主线程和子线程切换的工具类
 * @time 2020/7/2 15:13
 */
public class ThreadUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    // 当前是单一线程池,如需多个线程池改变接口的实现类即可
    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    /**
     * 描述：运行在子线程
     */
    public static void runOnSubThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    /**
     * 描述：运行在主线程
     */
    public static void runOnMainThread(Runnable runnable) {
        sHandler.post(runnable);
    }
}
