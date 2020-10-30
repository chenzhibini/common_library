package com.hdyg.testcommon.app;

import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.multidex.MultiDexApplication;

import com.hdyg.testcommon.BuildConfig;
import com.hdyg.common.common.AppConfig;
import com.hdyg.common.common.CommonModule;
import com.hdyg.common.util.LangUtil.MultiLanguageUtil;
import com.hdyg.common.util.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import cn.com.superLei.aoparms.AopArms;

/**
 * Created by CZB on 2018/3/26
 * 修改.
 */

public class MvpApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonModule.init(this, BuildConfig.ENABLE_LOG);
        //多语言
        languageWork();
        AopArms.init(this);
        checkLogin();
        //bugly异常捕获
        CrashReport.initCrashReport(getApplicationContext(), AppConfig.BUGLY_APPID, Utils.isAppDebug());//上线时false
    }

    //检测是否有登录
    private void checkLogin() {
//        AopArms.setInterceptor((key, methodName) -> {
//            if (AppConfig.Interceptor.IS_LOGIN.equals(key)){
//                if (TextUtils.isEmpty(SPUtils.get(SpMsg.TOKEN,""))){
//                    if (!methodName.equals("nextDoing")){
//                        ToastUtil.show(R.string.sys_device);
//                    }else {
//                        AppManager.getAppManager().finishActivity(WelcomeAty.class);
//                    }
//                    UIHelper.showLogin(this);
//                    return true;//代表拦截
//                }
//            }
//            return false;//放行
//        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        languageWork();
        if (newConfig.fontScale != 1)//非默认值
            getResources();
    }
    private void languageWork() {
        //自己写的工具包（如下）
        MultiLanguageUtil.autoUpdateLanguageEnviroment(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

}
