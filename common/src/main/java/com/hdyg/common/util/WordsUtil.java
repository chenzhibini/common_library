package com.hdyg.common.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import com.hdyg.common.common.CommonModule;

/**
 * @author CZB
 * @describe 获取string.xml中的字
 * @time 2020/6/30 15:40
 */
public class WordsUtil {
    private static Resources sResources;

    static {
        sResources = CommonModule.getAppContext().getResources();
    }

    public static String getString(int res) {
        return sResources.getString(res);
    }

    public static String getString(int res, String a) {
        return String.format(getString(res), a);
    }

    public static String getString(int res, String a, String b) {
        return String.format(getString(res), a, b);
    }

    public static int getColor(int res) {
        return sResources.getColor(res);
    }

    /**
     * 获取清单文件中的key对应的value
     *
     * @param key 键名 eg：BUGLY_APPID
     * @return value
     */
    public static String getMetaDataString(String key) {
        String res = null;
        try {
            ApplicationInfo appInfo = CommonModule.getAppContext().getPackageManager().getApplicationInfo(CommonModule.getAppContext().getPackageName(), PackageManager.GET_META_DATA);
            res = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}
