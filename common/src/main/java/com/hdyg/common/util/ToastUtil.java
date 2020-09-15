package com.hdyg.common.util;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.hdyg.common.R;
import com.hdyg.common.common.CommonModule;


/**
 * Toast
 *
 * @author CZB
 * @time 2018/7/5 18:06
 */
public class ToastUtil {
    private static Context context = CommonModule.getAppContext();
    private static Toast toast;

    static {
        toast = makeToast();
    }

    private static Toast makeToast() {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        toast.setView(view);
        return toast;
    }

    public static void show(@StringRes int resId) {
        show(context.getResources().getString(resId));
    }

    // Toast.LENGTH_SHORT 2秒，Toast.LENGTH_LONG 3秒
    public static void show(CharSequence text) {
        try {
            if (toast != null) {
                toast.setText(text);
            } else {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            // 解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
            Looper.loop();
        }
    }
}
