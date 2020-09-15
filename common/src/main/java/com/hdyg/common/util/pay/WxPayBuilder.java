package com.hdyg.common.util.pay;//package com.hdyg.basedemo.util.pay;
//
//import android.content.Context;
//
//import com.hdyg.lbs.entry.WxPayBean;
//import com.hdyg.lbs.entry.WxShareBean;
//import com.hdyg.lbs.util.LogUtils;
//import com.hdyg.lbs.util.StringUtil;
//import com.hdyg.lbs.util.ThreadUtil;
//import com.hdyg.lbs.util.ToastUtil;
//import com.tencent.mm.opensdk.constants.ConstantsAPI;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.modelmsg.SendAuth;
//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.lang.ref.WeakReference;
//
///**
// * @author CZB
// * @describe 微信工具
// * @time 2020/4/9 11:42
// */
//public class WxPayBuilder {
//
//    private Context mContext;
//    private String mAppId;
//    private WxPayBean.DataBean mBean;
//    private WxShareBean mShareBean;
//    private PayCallback mPayCallback;
//    private ShareCallback mShareCallback;
//    private AuthCallback mAuthCallback;
//    //SendMessageToWX.Req.WXSceneSession    对话
//    //SendMessageToWX.Req.WXSceneTimeline   朋友圈
//    //SendMessageToWX.Req.WXSceneFavorite   收藏
//    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;//默认分享到对话
//
//    public WxPayBuilder(Context context, String appId) {
//        mContext = context;
//        mAppId = appId;
//        WxApiWrapper.getInstance().setAppID(appId);
//        EventBus.getDefault().register(this);
//    }
//
//    public WxPayBuilder setPayParam(WxPayBean.DataBean bean) {
//        mBean = bean;
//        return this;
//    }
//
//    public WxPayBuilder setShareParam(WxShareBean bean) {
//        mShareBean = bean;
//        return this;
//    }
//
//    public WxPayBuilder setShareType(int shareType) {
//        mTargetScene = shareType;
//        return this;
//    }
//
//    public WxPayBuilder setPayCallback(PayCallback callback) {
//        mPayCallback = new WeakReference<>(callback).get();
//        return this;
//    }
//
//    public WxPayBuilder setShareCallback(ShareCallback callback) {
//        mShareCallback = new WeakReference<>(callback).get();
//        return this;
//    }
//
//    public WxPayBuilder setAuthCallback(AuthCallback callback) {
//        mAuthCallback = new WeakReference<>(callback).get();
//        return this;
//    }
//
//    /**
//     * 授权
//     */
//    public void auth() {
//        SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "lbs_pay";
//        IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
//        if (wxApi == null) {
//            ToastUtil.show("授权失败");
//            return;
//        }
//        boolean result = wxApi.sendReq(req);
//        if (!result) {
//            ToastUtil.show("授权失败");
//        }
//    }
//
//    /**
//     * 支付
//     */
//    public void pay() {
//        PayReq request = new PayReq();
//        LogUtils.d("发送支付的appid==>" + mBean.getAppid());
//        request.appId = mBean.getAppid();
//        request.partnerId = mBean.getPartnerid();
//        request.prepayId = mBean.getPrepayid();
//        request.packageValue = mBean.getPackageStr();
//        request.nonceStr = mBean.getNoncestr();
//        request.timeStamp = mBean.getTimestamp();
//        request.sign = mBean.getSign();
//        IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
//        if (wxApi == null) {
//            ToastUtil.show("支付失败");
//            return;
//        }
//        boolean result = wxApi.sendReq(request);
//        if (!result) {
//            ToastUtil.show("支付失败");
//        }
//    }
//
//    /**
//     * 分享
//     */
//    public void share() {
//        ThreadUtil.runOnSubThread(() -> {
//            //初始化一个WXWebpageObject，填写url
//            WXWebpageObject webpage = new WXWebpageObject();
//            webpage.webpageUrl = mShareBean.url;
//            //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
//            WXMediaMessage msg = new WXMediaMessage(webpage);
//            msg.title = mShareBean.title;
//            msg.description = mShareBean.des;
//            msg.thumbData = StringUtil.getHtmlByteArray(mShareBean.logo);
//            //构造一个Req
//            SendMessageToWX.Req req = new SendMessageToWX.Req();
//            req.transaction = buildTransaction("webpage");
//            req.message = msg;
//            req.scene = mTargetScene;
//            req.userOpenId = mShareBean.openid;
//            //调用api接口，发送数据到微信
//            IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
//            wxApi.sendReq(req);
//        });
//
//    }
//
//    private String buildTransaction(final String type) {
//        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onPayResponse(BaseResp resp) {
//        LogUtils.e("resp---微信回调---->" + resp.errCode);
//        String result;
//        if (mPayCallback != null) {
//            //支付
//            if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//                switch (resp.errCode) {
//                    case BaseResp.ErrCode.ERR_OK://成功
//                        result = "支付成功";
//                        mPayCallback.onSuccess();
//                        break;
//                    case BaseResp.ErrCode.ERR_COMM://签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
//                        result = "签名错误";
//                        mPayCallback.onFailed(result);
//                        break;
//                    case BaseResp.ErrCode.ERR_USER_CANCEL:
//                        result = "支付取消";
//                        mPayCallback.onFailed(result);
//                        break;
//                    default:
//                        result = "支付失败";
//                        mPayCallback.onFailed(result);
//                        break;
//                }
//                mPayCallback = null;
//            }
//        }
//        if (mAuthCallback != null) {
//            //授权
//            if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
//                switch (resp.errCode) {
//                    case BaseResp.ErrCode.ERR_OK://成功
//                        result = "授权成功";
//                        SendAuth.Resp authResp = (SendAuth.Resp) resp;
//                        final String code = authResp.code;
//                        mAuthCallback.onSuccess(code);
//                        break;
//                    case BaseResp.ErrCode.ERR_COMM://签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
//                        result = "签名错误";
//                        mAuthCallback.onFailed(result);
//                        break;
//                    case BaseResp.ErrCode.ERR_USER_CANCEL:
//                        result = "授权取消";
//                        mAuthCallback.onFailed(result);
//                        break;
//                    default:
//                        result = "授权失败";
//                        mAuthCallback.onFailed(result);
//                        break;
//                }
//                mAuthCallback = null;
//            }
//        }
//        if (mShareCallback != null) {
//            //分享
//            if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
//                switch (resp.errCode) {
//                    case BaseResp.ErrCode.ERR_OK://成功
//                        result = "分享成功";
//                        mShareCallback.onShareSuccess();
//                        break;
//                    case BaseResp.ErrCode.ERR_COMM://签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
//                        result = "签名错误";
//                        mShareCallback.onShareFailed(result);
//                        break;
//                    case BaseResp.ErrCode.ERR_USER_CANCEL:
//                        result = "分享取消";
//                        mShareCallback.onShareCancle(result);
//                        break;
//                    default:
//                        result = "分享失败";
//                        mShareCallback.onShareFailed(result);
//                        break;
//                }
//                mShareCallback = null;
//            }
//        }
//        mContext = null;
//        EventBus.getDefault().unregister(this);
//    }
//
//
//}
