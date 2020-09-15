package com.hdyg.common.common;

import com.hdyg.common.httpUtil.HttpProxy;
import com.hdyg.common.httpUtil.ICallback;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * model 层
 *
 * @author CZB
 * @time 2018/8/21 11:22
 */
public class BaseModel {
    private HttpProxy _obtain;

    public BaseModel() {
        _obtain = HttpProxy.obtain();
    }

    public void onDestory() {
        if (_obtain != null) {
            _obtain.onDestory();
        }
    }

    public void post(String path, Map<String, String> params, ICallback callback) {
        _obtain.formPost(path, params, callback);
    }

    public void postImg(String path, Map<String, String> params, String fileKey, File file, ICallback callback) {
        _obtain.uploadFile(path, file, fileKey, params, callback);
    }

    public void postMultImg(String path, Map<String, String> params, String fileKey, List<File> files, ICallback callback) {
        _obtain.uploadMultFile(path, files, fileKey, params, callback);
    }
}
