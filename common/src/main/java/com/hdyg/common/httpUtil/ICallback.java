package com.hdyg.common.httpUtil;

/**
 * @author CZB
 * @time 2018/7/5 14:46
 */
public interface ICallback {

    void onSuccess(String result);

    void onFail(String error);

    /**
     * 进度
     *
     * @param progress 当前进度
     * @param total    总进度
     */
    void onProgress(float progress, long total);

}
