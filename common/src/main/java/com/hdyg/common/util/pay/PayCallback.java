package com.hdyg.common.util.pay;

/**
 * @author CZB
 * @describe 支付回调
 * @time 2020/4/9 11:42
 */
public interface PayCallback {
    void onSuccess();

    void onFailed(String msg);
}
