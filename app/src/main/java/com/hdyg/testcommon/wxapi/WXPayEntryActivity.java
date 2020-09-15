//package com.hdyg.lbs.wxapi;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import com.hdyg.lbs.mvp.base.BaseUrl;
//import com.hdyg.lbs.util.LogUtils;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//import org.greenrobot.eventbus.EventBus;
//
//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//
//    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
//
//    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        api = WXAPIFactory.createWXAPI(this, BaseUrl.WX_APP_ID,false);
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//        LogUtils.e("微信 onReq回调==="+req.toString());
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        LogUtils.e("onPayFinish, errCode = " + resp.errCode);
//        EventBus.getDefault().post(resp);
//        finish();
//    }
//}