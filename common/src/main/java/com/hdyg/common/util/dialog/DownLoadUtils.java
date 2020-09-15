package com.hdyg.common.util.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.finalteam.rxgalleryfinal.utils.StorageUtils;

public class DownLoadUtils {
    private String TAG = "download";
    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K

    private String urlStr;
    private Context context;
    private WeakReference<ProgressBar> weakReference;

    public DownLoadUtils(Context context, String urlStr, ProgressBar mProgress) {
        this.context = context;
        this.urlStr = urlStr;
        weakReference = new WeakReference<>(mProgress);
        downloadApk();
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadApk() {
        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                InputStream in = null;
                FileOutputStream out = null;
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(false);
                    urlConnection.setConnectTimeout(10 * 1000);
                    urlConnection.setReadTimeout(10 * 1000);
                    urlConnection.setRequestProperty("Connection", "Keep-Alive");
                    urlConnection.setRequestProperty("Charset", "UTF-8");
                    urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");

                    urlConnection.connect();
                    long bytetotal = urlConnection.getContentLength();
                    long bytesum = 0;
                    int byteread = 0;
                    in = urlConnection.getInputStream();
                    File dir = StorageUtils.getCacheDirectory(context);
                    String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1);
                    File apkFile = new File(dir, apkName);
                    out = new FileOutputStream(apkFile);
                    byte[] buffer = new byte[BUFFER_SIZE];

                    int oldProgress = 0;

                    while ((byteread = in.read(buffer)) != -1) {
                        bytesum += byteread;
                        out.write(buffer, 0, byteread);

                        int progress = (int) (bytesum * 100L / bytetotal);
                        // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                        if (progress != oldProgress) {
                            publishProgress(progress);
                        }
                        oldProgress = progress;
                    }
                    // 下载完成
                    ApkUtils.installAPk(context, apkFile);
                } catch (Exception e) {
                    Log.e(TAG, "download apk file error:" + e.getMessage());
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ignored) {

                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ignored) {

                        }
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                if (weakReference != null && weakReference.get() != null) {
                    weakReference.get().setProgress(values[0]);
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {

            }
        }.execute();
    }
}
