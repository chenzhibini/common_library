package com.hdyg.common.common;

/**
 * Created by CWJ on 2017/10/18.
 * 在Presenter层实现，给Model层回调，更改View层的状态，确保Model层不直接操作View层
 */

public interface OnCallBackListener<T> {
    /**
     * 成功时回调
     *
     * @param type
     * @param response
     */
    void onSuccess(String type, T response);

    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError(String erroCode);

}
