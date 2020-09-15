package com.hdyg.common.util.LangUtil;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.SPUtils;
import java.util.Locale;

/**
 * @author CZB
 * @describe 多语言工具类
 * @time 2018/12/26 15:35
 */

public class MultiLanguageUtil {

    /**
     * 获取系统语言类型
     */
    public static MultiLanguageType getSystemLanguageType() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        if (locale.getLanguage().equals("zh")) {
            return MultiLanguageType.ZH;
        } else if (locale.getLanguage().equals("en")) {
            return MultiLanguageType.EN;
        }
        return MultiLanguageType.ZH;
    }

    /**
     * 更新本地语言环境
     *
     * @param context 应用上下文 (在Android 8.0 以上不能是 ApplicationContext)
     */
    public static void autoUpdateLanguageEnviroment(Context context) {
        //语言环境适配顺序 ：用户自定义配置 - app配置 - 系统配置
        MultiLanguageType type = null;
        String lang = SPUtils.get(SpMsg.LANG, MultiLanguageType.ZH.typeKey);//默认中文
        type = MultiLanguageType.match(lang);
        updateLanguageEnviroment(context, type);
    }

    /**
     * 更新语言环境
     *
     * @param context      应用上下文 (在Android 8.0 以上不能是 ApplicationContext)
     * @param languageType 语言类型
     */
    public static void updateLanguageEnviroment(Context context, MultiLanguageType languageType) {
        if (languageType == null || context == null) {
            return;
        }
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();

        Locale locale;
        switch (languageType) {

            case EN:
                locale = Locale.ENGLISH;
                break;
            case ZH:
                locale = Locale.CHINESE;
                break;
            default:
                locale = Locale.CHINESE;
                languageType = MultiLanguageType.ZH;
                break;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            config.setLocales(new LocaleList(locale));
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, dm);
        //同步更新 - app语言环境配置记录更新
        SPUtils.put(SpMsg.LANG, languageType.typeKey);
    }

    /**
     * 判断是否需要更新语言
     * @param pContext 上下文
     * @param languageType 语言类型
     * @return
     */
    public static boolean needUpdateLocale(Context pContext, MultiLanguageType languageType) {
        Locale locale;
        switch (languageType) {
            case EN:
                locale = Locale.ENGLISH;
                break;
            case ZH:
                locale = Locale.CHINESE;
                break;
            default:
                locale = Locale.CHINESE;
                break;
        }
        return locale != null && !getCurrentLocale(pContext).equals(locale);
    }

    /**
     * 获取当前语言
     * @param context 上下文
     * @return
     */
    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

}
