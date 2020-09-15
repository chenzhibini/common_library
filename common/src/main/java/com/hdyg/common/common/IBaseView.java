package com.hdyg.common.common;

/**
 * V 层接口基类
 *
 * @author CZB
 * @time 2018/3/10 10:51
 */
public interface IBaseView {

    void showLoading();

    void hideLoading();

    void onError(String code, String msg);
}
