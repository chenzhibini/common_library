package com.hdyg.common.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.hdyg.common.R;
import com.hdyg.common.util.MD5.Md5Encrypt;
import com.hdyg.common.util.MD5.ParameterUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtil {

    private static DecimalFormat sDecimalFormat;
    private static DecimalFormat sDecimalFormat2;
    private static Pattern sIntPattern;

    static {
        sDecimalFormat = new DecimalFormat("#.#");
        sDecimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        sDecimalFormat2 = new DecimalFormat("#.##");
        sDecimalFormat2.setRoundingMode(RoundingMode.DOWN);
        //sPattern = Pattern.compile("[\u4e00-\u9fa5]");
        sIntPattern = Pattern.compile("^[-\\+]?[\\d]*$");
    }

    /**
     * MD5加密
     *
     * @param resultMap map
     * @param key       key
     * @return
     */
    public static String Md5Str(Map<String, String> resultMap, String key) {
        String responseString = ParameterUtils.getRequestQueryString(resultMap, key);
        String sign = Md5Encrypt.md5(responseString);
        return sign;
    }

    /**
     * 获取随机数
     *
     * @return
     */
    public static String random() {

        String time = String.valueOf(System.currentTimeMillis() / 1000);
        return time;
    }

    /**
     * 二进制转字符串
     *
     * @param src
     * @return
     */
    public static String byte2hex(byte[] src) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(src.length * 2);
        for (byte b : src) {
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            sb.append(HEX.charAt(b & 0x0f));
        }
        return sb.toString();
    }

    /**
     * 线性布局生成bitmap的方法
     *
     * @param linearLayout 布局
     * @return bitmap
     */
    public static Bitmap buildBitmapImg(View linearLayout) {
        linearLayout.setDrawingCacheEnabled(true);
        linearLayout.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(linearLayout.getDrawingCache());
        linearLayout.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 保存图片到本地的方法
     *
     * @param context 上下文
     * @param bmp     图片
     * @param id      res下的图片ID
     * @return 图片存放地址
     */
    public static String saveImageToGallery(Context context, Bitmap bmp, int id) {
        // 首先保存图片
        String filePack = "SCAFile"; //文件夹名字
        File appDir = new File(Environment.getExternalStorageDirectory(), filePack);//存放的文件夹
        String fileName = System.currentTimeMillis() / 1000 + ".jpg";//存放的文件名
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //判断bmp是否为空  是空的话 就执行本地图片转bitmap
            if (bmp == null) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } else {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }

            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Uri uri = Uri.fromFile(file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        String path = Environment.getExternalStorageDirectory() + "/" + filePack + "/" + fileName;
        ToastUtil.show(R.string.save_success);
        LogUtils.d("图片存放地址============" + path);
        return path;

    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    //清除缓存调用的子方法
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * dpתpx
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getWindowHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingPhone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }

    /**
     * 复制文本到剪贴板
     *
     * @param txt     文本内容
     * @param context 上下文对象
     */
    public static void copyTextMethod(Context context, String txt) {
        if (!TextUtils.isEmpty(txt)) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(txt);
            ToastUtil.show(R.string.copy_success);
        } else {
            ToastUtil.show(R.string.copy_fail);
        }
    }

    /**
     * 跳转到拨打电话 并传递电话号码
     *
     * @param context 上下文对象
     * @param number  电话号码
     */
    public static void intentCallPhone(Context context, String number) {
        if (!TextUtils.isEmpty(number)) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));//跳转到拨号界面，同时传递电话号码
            context.startActivity(dialIntent);
        }
    }

    /**
     * 文字变色
     *
     * @param textView view
     * @param text     显示的文字
     * @param color    需要变色的颜色
     * @param length   从什么长度开始变色
     */
    public static void setTextView(TextView textView, String text, int color, int length) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), length, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    /**
     * 文件转base64字符串
     *
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
            base64 = "data:image/png;base64," + base64;
        } catch (FileNotFoundException e) {
            LogUtils.e("文件异常");
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.e("文件异常");
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LogUtils.e("文件异常");
                e.printStackTrace();
            }
        }
        return base64;
    }

    /**
     * 删除指定字符串
     *
     * @param s  字符串
     * @param s1 需要删除的字符串
     * @return
     */
    public static String cutStr(String s, String s1) {

        int postion = s.indexOf(s1);
        int length = s1.length();
        int Length = s.length();
        String newString = s.substring(0, postion) + s.substring(postion + length, Length);
        return newString;//返回已经删除好的字符串
    }

    /**
     * 获取百分比
     *
     * @param current 除数
     * @param all     被除数
     * @return
     */
    public static String getPercentage(float current, float all) {
        String str = "";
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        float baifen = (float) current / all;
        str = nt.format(baifen);
        return str;
    }

    /**
     * 截取字符串后几位
     *
     * @param str 原字符串
     * @param pos 后几位
     * @return 返回新字符串
     */
    public static String getInterceptStr(String str, int pos) {
        String newStr = "";
        if (str.length() >= pos) {
            //第一个参数开始的位置  第二个参数 结束的位置
            newStr = str.substring(str.length() - pos, str.length());
        }
        return newStr;
    }

    /**
     * 获取URL中的参数
     *
     * @param url url
     * @param key 对应的key
     * @return
     */
    public static String getUrlParam(String url, String key) {
        String type = "";
        Uri uri = Uri.parse(url);
        type = uri.getQueryParameter(key);
        return type;
    }

    /**
     * 截取特定字符串后的字符
     *
     * @param str    字符
     * @param cutStr 关键字符
     * @return
     */
    public static String getSpitRight(String str, String cutStr) {
        String userIdJiequ = "";
        userIdJiequ = str.substring(str.indexOf(cutStr));
        userIdJiequ = userIdJiequ.replace(cutStr, "");
        return userIdJiequ;
    }

    /**
     * 截取特定字符串前的字符
     *
     * @param str    字符
     * @param cutStr 关键字符
     * @return
     */
    public static String getInterceptLeft(String str, String cutStr) {
        String userIdJiequ = "";
        userIdJiequ = str.substring(0, str.indexOf(cutStr));
        return userIdJiequ;
    }

    /**
     * 获取布局的 View
     *
     * @param layoutRes
     * @return
     */
    public static final View getView(int layoutRes, Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(layoutRes, null);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        return view;
    }

    /**
     * 获取空布局
     *
     * @param mContext
     * @return
     */
    public static final View getEmptyView(Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_empty_view, null);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        return view;
    }

    //防止按钮重复点击   3秒
    private static long lastClickTime;
    private final static long CLICK_TIME = 3000;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < CLICK_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 保留小数点后四位
     *
     * @param allCount
     * @return
     */
    public static String dealFloatStr(double allCount) {
        DecimalFormat df = new DecimalFormat("0.0000");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(allCount);
    }

    public static String dealFloatStrTwo(double allCount) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(allCount);
    }

    //保留指定长度，长度后用。。。代替
    public static String cutLentgthReplace(String oldStr, int length, String replaceStr) {
        String newOld = "";
        if (oldStr.length() <= length) {
            newOld = oldStr;
        } else {
            newOld = oldStr.substring(0, length) + replaceStr;
        }
        return newOld;
    }

    /**
     * 把数字转化成多少万
     */
    public static String toWan(long num) {
        if (num < 10000) {
            return String.valueOf(num);
        }
        return sDecimalFormat.format(num / 10000d) + "W";
    }

    /**
     * 把数字转化成多少万
     */
    public static String toWan2(long num) {
        if (num < 10000) {
            return String.valueOf(num);
        }
        return sDecimalFormat2.format(num / 10000d) + "w";
    }

    /**
     * 把一个long类型的总毫秒数转成时长
     */
    public static String getDurationText(long mms) {
        int hours = (int) (mms / (1000 * 60 * 60));
        int minutes = (int) ((mms % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((mms % (1000 * 60)) / 1000);
        String s = "";
        if (hours > 0) {
            if (hours < 10) {
                s += "0" + hours + ":";
            } else {
                s += hours + ":";
            }
        }
        if (minutes > 0) {
            if (minutes < 10) {
                s += "0" + minutes + ":";
            } else {
                s += minutes + ":";
            }
        } else {
            s += "00" + ":";
        }
        if (seconds > 0) {
            if (seconds < 10) {
                s += "0" + seconds;
            } else {
                s += seconds;
            }
        } else {
            s += "00";
        }
        return s;
    }

    /**
     * 包名
     */
    public interface PackName{
        String QQ = "com.tencent.mobileqq";
        String WECHAT = "com.tencent.mm";
        String WHATSAPP = "com.whatsapp";
    }
    /**
     * 判断某APP是否安装
     */
    public static boolean isAppExist(Context mContext, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            PackageManager manager = mContext.getPackageManager();
            List<PackageInfo> list = manager.getInstalledPackages(0);
            for (PackageInfo info : list) {
                if (packageName.equalsIgnoreCase(info.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前语言环境是否是中文环境
     */
    public static boolean isZh(Context mContext) {
        Locale locale = mContext.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 网络资源图片转字节
     * @param url 网络资源
     * @return byte
     */
    public static byte[] getHtmlByteArray(final String url) {
        URL htmlUrl = null;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            int responseCode = httpConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                inStream = httpConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = inputStreamToByte(inStream);

        return data;
    }
    public static byte[] inputStreamToByte(InputStream is) {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                baos.write(ch);
            }
            byte imgdata[] = baos.toByteArray();
            baos.close();
            return imgdata;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


}
