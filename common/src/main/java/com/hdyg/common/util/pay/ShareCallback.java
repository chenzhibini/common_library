package com.hdyg.common.util.pay;

/**
 * @author CZB
 * @describe 分享回调
 * @time 2020/4/9 11:42
 */
public interface ShareCallback {
    void onShareSuccess();

    void onShareFailed(String msg);

    void onShareCancle(String msg);
}
