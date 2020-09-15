package com.hdyg.common.common;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 代理类 基类
 *
 * @author CZB
 * @time 2018/2/3 14:28
 */
public abstract class BasePresenter<V extends IBaseView> {
    protected V mView;
    protected BaseModel mModel;
    private Reference<V> mViewRef; // View 接口类型弱引用

    public BasePresenter(V mView) {
        this.mView = mView;
        mModel = new BaseModel();
    }

    public BasePresenter() {
        mModel = new BaseModel();
    }

    /**
     * 建立关联
     *
     * @param view
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);  // 建立关联
    }

    /**
     * 获取View
     *
     * @return
     */
    protected V getView() {
        return mViewRef.get();
    }

    /**
     * 判断是否与View 建立关联
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解除关联
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public void onDestroy() {
        detachView();
    }

}
