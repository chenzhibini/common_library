package com.hdyg.common.httpUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 请求代理接口类
 *
 * @author CZB
 * @time 2018/7/5 17:21
 */
public interface IHttpProxy {

    // 网络访问：post、get、del、update、put
    void get(String path, Map<String, String> params, ICallback callback);

    void executePost(String path, Map<String, String> params, ICallback callback);

    void formPost(String path, Map<String, String> params, ICallback callback);

    void downloadFile(String path, ICallback callback);

    void uploadFile(String path, String fileMediaType, List<String> filePaths, ICallback callback);

    void uploadFile(String path, File file, String fileKey, Map<String, String> paramsMap, ICallback callback);

    void uploadMultFile(String path, List<File> files, String fileKey, Map<String, String> paramsMap, ICallback callback);

    // 取消请求
    void onDestory();
}
