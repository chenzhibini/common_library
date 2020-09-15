package com.hdyg.testcommon.mvp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.LogUtils;

/**
 * @author CZB
 * @describe 跳转统一类
 * @time 2019/10/25 14:28
 */
public class UIHelper {

    //不回调跳转
    private static void startActivity(Context mContext, Class<?> className, Bundle bundle) {
        Intent mIntent = new Intent();
        mIntent.setClass(mContext, className);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        if (mContext.getPackageManager().resolveActivity(mIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            mContext.startActivity(mIntent);
        } else {
            LogUtils.e("activity not found for " + className.getSimpleName());
        }
    }

    //回调跳转
    private static void startActivityForResult(Context mContext, Class<?> className, Bundle bundle,
                                               int requestCode) {
        Intent mIntent = new Intent();
        mIntent.setClass(mContext, className);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        if (mContext.getPackageManager().resolveActivity(mIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            ((Activity) mContext).startActivityForResult(mIntent, requestCode);
        } else {
            LogUtils.e("activity not found for " + className.getSimpleName());
        }
    }

    public static void showTreeAdapter(Context mContext) {
        startActivity(mContext, TreeAdapterActivity.class, null);
    }

    //跳转登录页
    public static void showLogin(Context mContext) {
        startActivity(mContext, LoginActivity.class, null);
    }

    //跳转首页
    public static void showMain(Context mContext) {
        startActivity(mContext, MainActivity.class, null);
    }

    /**
     * 跳转注册/忘记密码页面
     *
     * @param mContext   上下文
     * @param intentType 跳转类型
     */
    public static void showRegistForgetPwd(Context mContext, int intentType, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(SpMsg.INTENT_CODE, intentType);
        startActivityForResult(mContext, RegistForGetPwdActivity.class, bundle, requestCode);
    }

    //跳转通讯录
    public static void showContact(Context mContext) {
//        startActivity(mContext, ContactActivity.class, null);
    }

    //跳转网页
    public static void showWeb(Context mContext, String url) {
        WebActivity.start(mContext,url);
    }

}
