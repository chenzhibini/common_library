package com.hdyg.testcommon.mvp.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.hdyg.testcommon.R;
import com.hdyg.common.common.AppManager;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import com.hdyg.common.common.CommonModule;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.JavaScriptinterface;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.TakePhotoUtil;
import com.hdyg.common.util.dialog.JDialog;
import com.hdyg.common.util.dialog.JDialogType;
import com.hdyg.common.widget.MyWebView;
import com.hdyg.testcommon.app.AppConfig;

import java.io.File;
import butterknife.BindView;
import okhttp3.Cookie;

/**
 * @author CZB
 * @describe 网页
 * @time 2019/4/19 10:34
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.web_view)
    MyWebView webView;
//    @BindView(R.id.srl_refresh)
//    SmartRefreshLayout srlRefresh;

    private String URL;//传递的URL
    private final int scanQrCodeRequestCode = 0x1100;//扫描回调
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private String desCpt, failUrl;
    private boolean SELECT_IMG = false; //是否选择图片 默认不选择

    /**
     * 跳转
     *
     * @param mContext 上下文
     * @param url      跳转地址
     */
    public static void start(Context mContext, String url) {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra(SpMsg.URL, url);
        mContext.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        setLeftColor(getResources().getColor(R.color.white));
        LogUtils.d("进来了");
        URL = getIntent().getStringExtra(SpMsg.URL);
//        WebSettings settings = webView.getSettings();
//        settings.setUserAgentString(settings.getUserAgentString() + ";isapp");
        synCookies(URL);
        webView.loadUrl(URL);
        initWebView();
        webView.addJavascriptInterface(new JavaScriptinterface(mContext, webView), "android");
        onreFresh();
    }

    private void onreFresh() {
//        srlRefresh.setOnRefreshListener(refreshLayout -> {
//            srlRefresh.finishRefresh(2000);//传入false表示刷新失败
//            if (webView != null) {
//                webView.reload();
//            }
//        });
    }


    /**
     * 同步一下cookie
     */
    public void synCookies(String url) {
        Cookie sessionCookie = CommonModule.getCookies();    //这里的cookie就是上面保存的cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        if (sessionCookie != null) {
            cookieManager.removeSessionCookie();
            String cookieString = sessionCookie.name() + "=" + sessionCookie.value() + "; domain=" + sessionCookie.domain();
            cookieManager.setCookie(url, cookieString);
            LogUtils.d("cookie==>" + cookieString);
            CookieSyncManager.getInstance().sync();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(view, request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.d("url====>" + url);
                synCookies(url);
                if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("HTTP:") || url.startsWith("HTTPS:")) {
                    if (url.contains("qr.alipay.com")) {
                        mBundle = new Bundle();
                        mBundle.putString(SpMsg.URL, url);
                        startActivity(WebActivity.class, mBundle);
                    } else {
                        view.loadUrl(url);
                    }
                    return false;
                } else if (url.equals(AppConfig.LOGOUT)) {
                    loginOutMethod(getResources().getString(R.string.sys_device));
                    return true;
                } else if (url.equals(AppConfig.LOGOUT2)) {
                    loginOutMethod(getResources().getString(R.string.sys_updatepwd));
                    return true;
                } else if (url.equals(AppConfig.BACK)) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        setResult(RESULT_OK);
                        finish();
                    }
                    return true;
                } else if (url.contains("tel:")) {
                    //拨打电话
                    Uri uri = Uri.parse(url);
                    Intent mIntent = new Intent(Intent.ACTION_DIAL, uri);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(mIntent);
                        return true;
                    } else {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        return true;
                    }
                } else if (url.startsWith("weixin://") || url.startsWith("alipays://") || url.startsWith("intent://") || url.startsWith("mqqapi://")) {
                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        if (url.startsWith("alipays://")) {
                            new JDialog.Builder(mContext, JDialogType.CHOOSE)
                                    .setContent("未检测到支付宝客户端，请请安装后重试!")
                                    .setOnOkClickListener(() -> {
                                        // 点击按钮所调用的方法
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        mContext.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    })
                                    .build()
                                    .show();
                        }
                        return false;
                    }
                    return true;
                } else {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
                    return true;
                }
            }

            //网络请求部分  去广告
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                url = url.toLowerCase();
//                for (int i=0;i<AppConfig.domainRules.length;i++){
//                    if (url.contains(AppConfig.domainRules[i])){
//                        return super.shouldInterceptRequest(view, url);
//                    }
//                }
//                //去掉广告
//                return new WebResourceResponse(null, null, null);
//            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                view.loadUrl("javascript:chaoS('" + desCpt + "','" + failUrl + "')");
            }

//            // TODO: 2018/4/23 加载出错时显示
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//                view.loadUrl(AppConfig.NET_FAIL_URL);
//                desCpt = description;
//                failUrl = failingUrl;
//            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            CustomViewCallback callback;

            // /////////////////////////////////////////////////////////
            //

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.frame_web_video);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            // ======================== 自定义的window alert ============================
            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     final JsResult arg3) {
                toastNotifyShort(arg2);
                arg3.cancel();
                return true;
            }
            // =============================== 弹窗 =======================================

            // =============================== 文件上传 ====================================


            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                SELECT_IMG = true;
                mUploadCallbackAboveL = filePathCallback;
                TakePhotoUtil.getInstance().selectPhotoCropSingle(mContext, new TakePhotoUtil.OnTakePhotoListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        SELECT_IMG = false;
                        File file = new File(obj.toString());
                        Uri result = Uri.fromFile(file);
                        if (mUploadMessage != null) {
                            mUploadMessage.onReceiveValue(Uri.parse(obj.toString()));
                            mUploadMessage = null;
                        }
                        if (mUploadCallbackAboveL != null) {
                            mUploadCallbackAboveL.onReceiveValue(new Uri[]{result});
                            mUploadCallbackAboveL = null;
                        }
                    }
                });
                return true;
            }

            /**
             * 获取标题的方法
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    setLeftText(title);
                }
            }

            // ======================================================================

        });

    }

    //退出到登录页面
    private void loginOutMethod(String msg) {
        new JDialog.Builder(mContext, JDialogType.TIP)
                .setContent(msg)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setOnOkClickListener(() -> AppManager.getAppManager().staLoginActivity())
                .build()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
//            case scanQrCodeRequestCode://扫描回调
//                //处理扫描结果（在界面上显示）
//                if (null != data) {
//                    Bundle bundle = data.getExtras();
//                    if (bundle == null) {
//                        return;
//                    }
//                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                        String scanQrCodeStr = data.getStringExtra(CodeUtils.RESULT_STRING);//扫码回调结果
//                        LogUtils.d("扫描结果1==" + scanQrCodeStr);
//                        scanQrCodeStr += "&&token=" + SPUtils.get("token", "");
//                        LogUtils.d("扫描结果2==" + scanQrCodeStr);
//                        Bundle b = new Bundle();
//                        b.putString("url", scanQrCodeStr);
//                        startActivity(WebActivity.class, b);
//
//                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                        Toast.makeText(mContext, R.string.scan_qrcode_fail, Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SELECT_IMG) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void createPresenter() {

    }

    /**
     * 系统返回键回退处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.d("keycode==>" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                setResult(RESULT_OK);
                finish();
            }
            return true;
        } else {
            super.onKeyDown(keyCode, event);
            return false;
        }
    }

}
