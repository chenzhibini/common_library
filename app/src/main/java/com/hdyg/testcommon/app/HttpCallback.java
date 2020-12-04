package com.hdyg.testcommon.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.google.gson.JsonSyntaxException;
import com.hdyg.common.common.AppManager;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import com.hdyg.common.httpUtil.ICallback;
import com.hdyg.common.httpUtil.RxException;
import com.hdyg.common.util.JsonUtil;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.ProgressDialogUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 回调
 *
 * @author CZB
 * @time 2020/12/1 14:55
 */
public abstract class HttpCallback<Result> implements ICallback {
    private ProgressDialogUtil dialogUtil = null;
    private Activity activity;

    /**
     * 无参构造函数
     */
    protected HttpCallback() {
        activity = AppManager.getAppManager().currentActivity();
    }

    /**
     * 泛型 Result 解析成功时回调
     *
     * @param result 泛型 回调的实体
     */
    public abstract void onSuccess(Result result);

    /**
     * 请求失败回调
     *
     * @param code  错误码
     * @param error 错误消息
     */
    public abstract void onFail(String code, String error);


    /**
     * 失败回调
     *
     * @param error
     */
    @Override
    public void onFail(String error) {
        hideProgress();
        LogUtils.e("####### onError：" + error);
        //
        onFail(null, error);
    }

    /**
     * 成功回调
     *
     * @param result
     */
    @Override
    public void onSuccess(String result) {
        hideProgress();
        BaseBean baseBean = null;
        LogUtils.d("####### onSuccess：" + result);
        // 解析 BaseBean
        try {
            baseBean = JsonUtil.parseJsonWithGson(result, BaseBean.class);
        } catch (JsonSyntaxException e) {
            String error = RxException.handleException(e).getMessage();
            onFail(error);
        }
        if (baseBean == null || baseBean.getStatus() == null) return;
        // 根据回调的状态处理
        switch (baseBean.getStatus()) {
            case AppConfig.SUCCESS:   // 成功获取
                Result data = null;
                try {
                    Type type = getClass().getGenericSuperclass();
                    Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                    data = JsonUtil.createGson().fromJson(result, types[0]);
                    onSuccess(data);
                } catch (Exception e) {
                    onFail(e.getMessage());
                }
                break;
            case AppConfig.UN_TOKEN:  // token 失效(抢登)
                // 发送广播
                if (activity != null && !activity.isFinishing()){
                    Intent intent = new Intent(BaseActivity.UN_TOKEN_BROCAST);
                    activity.sendBroadcast(intent);
                }
                break;
            case AppConfig.FAIL:  // 获取失败
            default:
                onFail(baseBean.getStatus(), baseBean.getMessage());
                break;
        }
    }

    /**
     * 加载进度
     *
     * @param progress 当前进度
     * @param total    总进度
     */
    @Override
    public void onProgress(float progress, long total) {

        if (dialogUtil == null) {
            dialogUtil = new ProgressDialogUtil(activity);
            dialogUtil.init2(progress, total);
        } else {
            dialogUtil.show2(progress);
        }
    }

    /**
     * 关闭进度条
     */
    @SuppressLint("NewApi")
    public void hideProgress() {
        if (dialogUtil != null) {
            try {
                dialogUtil.cancel();
            } catch (Exception e) {
            } finally {
                dialogUtil = null;
            }
        }
    }
}
