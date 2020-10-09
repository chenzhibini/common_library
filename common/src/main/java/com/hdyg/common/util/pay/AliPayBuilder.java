package com.hdyg.common.util.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.hdyg.common.bean.PayResult;
import com.hdyg.common.util.ToastUtil;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author CZB
 * @describe 支付宝工具
 * @time 2020/4/9 11:42
 */
public class AliPayBuilder {

    private Context mContext;
    private PayHandler mPayHandler;
    private String mAlipayParam;
    private final static int SDK_PAY_FLAG = 0x0001;

    public AliPayBuilder(Context context) {
        mContext = new WeakReference<>(context).get();
    }

    public AliPayBuilder setParam(String orderInfo) {
        mAlipayParam = orderInfo;
        return this;
    }

    public AliPayBuilder setPayCallback(PayCallback callback) {
        mPayHandler = new PayHandler(callback);
        return this;
    }


    /**
     * 从服务器端获取订单号,即下单
     */
    public void pay() {
        if (TextUtils.isEmpty(mAlipayParam)) {
            ToastUtil.show("参数不能为空");
            return;
        }
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(mAlipayParam, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mPayHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static class PayHandler extends Handler {
        private PayCallback mPayCallback;

        public PayHandler(PayCallback payCallback) {
            mPayCallback = new WeakReference<>(payCallback).get();
        }

        @Override
        public void handleMessage(Message msg) {
            if (mPayCallback != null) {
                switch (msg.what) {
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            ToastUtil.show("支付成功");
                            mPayCallback.onSuccess();
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                            ToastUtil.show("支付失败");
                            mPayCallback.onFailed("支付失败");
                        }
                        break;
                }
                mPayCallback = null;
            }

        }
    }

}
