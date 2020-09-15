package com.hdyg.common.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.SPUtils;
import java.util.Stack;

/**
 * Activity管理类
 *
 * @author Donkor
 */
@SuppressWarnings("unchecked")
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishActivity(int length) {
        for (int i = 0; i < length; i++) {
            activityStack.get(activityStack.size() - i - 1).finish();
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * recreate所有的activity
     *
     * @param activity
     */
    public void recreateAllOtherActivity(Activity activity) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && activityStack.get(i) != activity) {
                activityStack.get(i).recreate();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staLoginActivity() {
        SPUtils.put(SpMsg.TOKEN, "");    //清除token
        Intent intent = new Intent();
        intent.setAction("com.hdyg.android.login");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        currentActivity().startActivity(intent);
        finishAllActivity();
    }

    /**
     * activity 跳转[无参]
     *
     * @param className
     */
    public void startActivity(Class<?> className) {
        startActivity(className, null);
    }

    /**
     * activity 跳转[有参]
     *
     * @param className
     * @param bundle
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent mIntent = new Intent();
        mIntent.setClass(currentActivity(), className);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        if (currentActivity().getPackageManager().resolveActivity(mIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            currentActivity().startActivity(mIntent);
        } else {
            LogUtils.e("activity not found for " + className.getSimpleName());
        }
    }
}