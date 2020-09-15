package com.hdyg.common.httpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理类
 *
 * @author CZB
 * @time 2018/7/5 17:26
 */
public class HttpProxy implements IHttpProxy {
    // 声明IHttpProcessor对象
    private static IHttpProxy mIHttpProcessor = null;
    // 声明代理对象 HttpProxy
    private static HttpProxy _instance;
    // 声明参数
    private Map<String, String> mParams;

    private HttpProxy() {
        mParams = new HashMap<String, String>();
    }

    /**
     * 单例
     * 提供给外面调用的方法
     *
     * @return 该代理对象_instance
     */
    public static HttpProxy obtain() {
        synchronized (HttpProxy.class) {
            if (_instance == null) {
                _instance = new HttpProxy();
            }
        }
        return _instance;
    }

    /**
     * 初始化代理对象
     *
     * @param httpProcessor
     */
    public static void init(IHttpProxy httpProcessor) {
        // 获取代理对象
        mIHttpProcessor = httpProcessor;
    }

    /**
     * POST
     *
     * @param path
     * @param params
     * @param callback
     */
    @Override
    public void formPost(String path, Map<String, String> params, ICallback callback) {
        mIHttpProcessor.formPost(path, params, callback);
    }

    /**
     * GET
     *
     * @param path
     * @param params
     * @param callback
     */
    @Override
    public void get(String path, Map<String, String> params, ICallback callback) {
        mIHttpProcessor.get(path, params, callback);
    }

    /**
     * post 请求
     *
     * @param path
     * @param params
     * @param callback
     */
    @Override
    public void executePost(String path, Map<String, String> params, ICallback callback) {
        mIHttpProcessor.executePost(path, params, callback);
    }

    /**
     * 下载
     *
     * @param path
     * @param callback
     */
    @Override
    public void downloadFile(String path, ICallback callback) {
        mIHttpProcessor.downloadFile(path, callback);
    }

    /**
     * 文件上传
     *
     * @param path
     * @param filePaths
     * @param callback
     */
    @Override
    public void uploadFile(String path, String fileMediaType, List<String> filePaths, ICallback callback) {
        mIHttpProcessor.uploadFile(path, fileMediaType, filePaths, callback);
    }

    /**
     * 上传单个文件
     *
     * @param path
     * @param file
     * @param fileKey
     * @param paramsMap
     * @param callback
     */
    @Override
    public void uploadFile(String path, File file, String fileKey, Map<String, String> paramsMap, ICallback callback) {
        mIHttpProcessor.uploadFile(path, file, fileKey, paramsMap, callback);
    }

    /**
     * 上传多个文件
     * @param path
     * @param files
     * @param fileKey
     * @param paramsMap
     * @param callback
     */
    @Override
    public void uploadMultFile(String path, List<File> files, String fileKey, Map<String, String> paramsMap, ICallback callback) {
        mIHttpProcessor.uploadMultFile(path, files, fileKey, paramsMap, callback);
    }

    /**
     * 取消请求
     */
    @Override
    public void onDestory() {
        mIHttpProcessor.onDestory();
    }
}
