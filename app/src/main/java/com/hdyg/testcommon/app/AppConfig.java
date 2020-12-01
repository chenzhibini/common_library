package com.hdyg.testcommon.app;

/**
 * @author CZB
 * @describe 配置类
 * @time 2020/12/1 19:14
 */
public class AppConfig {

    public final static String NET_FAIL_URL = "file:///android_asset/404.html"; //报错加载网页
    public final static String LOGOUT = "app://logout";                         //退出登录 token失效 等
    public final static String LOGOUT2 = "app://updpsd";                        //修改密码成功退出登录
    public final static String BACK = "app://goback";                           //后退
    public final static String KEY = "853sfY9el7ZGuqpakZc8amR2";                //签名的KEY
    public final static String SUCCESS = "1";                                   // 请求返回状态 成功
    public final static String FAIL = "0";                                      // 请求返回状态 失败
    public final static String UN_TOKEN = "2";                                  // TOKEN 不符（抢登）
    public final static String COMPLETE_INFO = "0004";                          // 程序报错
    public final static int IMG_SIZE_LIMIT = 3;                                 // 图片大小限制 3M
    public final static int REFRESH_TIME = 2000;                                // 刷新时间
    // bugly appid
    public static final String BUGLY_APPID = "cb7988d404";
    // 以下域名不拦截  其他域名不允许访问
    public static final String[] domainRules = {
            "http://tmbjijin.com",
            "http://tmbjijin.com"
    };
    //拦截器 需要拦截的key
    public interface Interceptor{
        String IS_LOGIN = "isLogin";//判断是否登录
    }
    //协议
    public interface Protocol{
        String USER_PROTOCOL = "https://mall.yqlsc.com/web/pages/store_agreement.html?id=47";
        String YINSI_PROTOCOL = "http://www.baidu.com";
    }
}
