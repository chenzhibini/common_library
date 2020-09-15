package com.hdyg.common.util.pay;

/**
 * @author CZB
 * @describe 授权回调
 * @time 2020/4/9 11:42
 */
public interface AuthCallback {
    void onSuccess(String authCode);

    void onFailed(String msg);
}
