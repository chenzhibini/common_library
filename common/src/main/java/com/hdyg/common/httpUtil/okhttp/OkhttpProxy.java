package com.hdyg.common.httpUtil.okhttp;

import com.hdyg.common.httpUtil.ICallback;
import com.hdyg.common.httpUtil.IHttpProxy;
import com.hdyg.common.httpUtil.RxException;
import java.io.File;
import java.util.List;
import java.util.Map;
import okhttp3.Call;

/**
 * okhttp 3.0 请求代理
 *
 * @author CZB
 * @time 2018/8/21 9:33
 */
public class OkhttpProxy implements IHttpProxy {
    @Override
    public void get(String path, Map<String, String> params, ICallback callback) {

    }

    @Override
    public void executePost(String path, Map<String, String> params, ICallback callback) {

    }

    @Override
    public void formPost(String path, Map<String, String> params, final ICallback callback) {
        OkhttpUtil.okHttpPost(path, params, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                String error = RxException.handleException(e).getMessage();
                callback.onFail(error);
            }

            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        });
    }

    @Override
    public void downloadFile(String path, ICallback callback) {

    }

    @Override
    public void uploadFile(String path, String fileMediaType, List<String> filePaths, ICallback callback) {

    }

    @Override
    public void uploadFile(String path, File file, String fileKey, Map<String, String> paramsMap, final ICallback callback) {
        OkhttpUtil.okHttpUploadFile(path, file,fileKey,"image/jpeg",paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                callback.onFail(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        });
    }

    @Override
    public void uploadMultFile(String path, List<File> files, String fileKey, Map<String, String> paramsMap, final ICallback callback) {
        OkhttpUtil.okHttpUploadListFile(path, paramsMap,files,fileKey,"image/*", new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                callback.onFail(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        });
    }

    @Override
    public void onDestory() {

    }
}
