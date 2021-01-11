package com.hdyg.common.common;

import android.app.Application;
import android.content.Context;
import com.hdyg.common.httpUtil.HttpProxy;
import com.hdyg.common.httpUtil.okhttp.OkhttpProxy;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.ScreenAdaptationUtil;
import com.hdyg.common.util.Utils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import okhttp3.Cookie;

/**
 * @author CZB
 * @describe 获取主工程中的context
 * @time 2020/7/17 10:44
 * 主工程调用  CommonModule.init(this);
 */
public class CommonModule {
    private static Context sAppContext;
    public static Cookie cookies;

    /**
     * 子模块和主模块需要共享全局上下文，故需要在app module初始化时传入
     */
    public static void init(Context appContext,boolean isOpenLog) {
        if (sAppContext == null) {
            sAppContext = appContext.getApplicationContext();
            Utils.init((Application) appContext);
            //屏幕适配
            ScreenAdaptationUtil.setup((Application) appContext);
            ScreenAdaptationUtil.register((Application) appContext, 375, ScreenAdaptationUtil.MATCH_BASE_WIDTH, ScreenAdaptationUtil.MATCH_UNIT_DP);
            //初始化zxing包
            ZXingLibrary.initDisplayOpinion(sAppContext);
            // 日志工具实例化
            LogUtils.getBuilder(sAppContext)
                    .setLogSwitch(isOpenLog)
                    .setLog2FileSwitch(false)
                    .setTag("czb")
                    .create();
            // 初始化网络加载框架
            HttpProxy.init(new OkhttpProxy());
        }
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static void setCookies(Cookie cookie) {
        cookies = cookie;
    }

    public static Cookie getCookies() {
        return cookies;
    }
}
