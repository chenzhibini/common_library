package com.hdyg.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import com.hdyg.common.R;
import java.text.DecimalFormat;


/**
 * Desc:
 * Auther: Chen_Baiyi
 * Date: 2018/12/3.
 */
@SuppressLint("NewApi")
public class ProgressDialogUtil {
    private AlertDialog dialog = null;
    private TextView tvProgress;
    private Context mContext;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ProgressBar mProgress;
    private long total;

    public ProgressDialogUtil(Context mContext) {
        this.mContext = mContext;
        dialog = new AlertDialog.Builder(mContext).create();
    }

    /**
     * 进度弹窗
     */
    public void init(final float progress, long total) {
        this.total = total;
        View mDialogView = View.inflate(mContext, R.layout.dialog_loading, null);   // 得到加载 view
        LinearLayout layout = mDialogView.findViewById(R.id.dialog_view);  // 加载布局
        tvProgress = mDialogView.findViewById(R.id.tipTextView);
        ImageView spaceshipImage = mDialogView.findViewById(R.id.img);

        DecimalFormat format = new DecimalFormat("##.00");
        final String proStr = format.format(progress * 100 / total) + "%";
        // 赋值
        mHandler.post(() -> tvProgress.setText(proStr));

        // 按返回键是否取消
        dialog.setCancelable(false);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(false);
        // 显示
        if (!((Activity) mContext).isFinishing()) {
            dialog.show();
            // 加载布局
            dialog.setContentView(mDialogView);
            /**【注意】 Dialog自定义宽度和高度要放在setContentView以后，否则没有效果！*/
            // 获取 dialog 的 window
            Window window = dialog.getWindow();
            // 背景变暗
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                // 设置居中
                window.setGravity(Gravity.CENTER);
            }
//        // 设置宽度
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = Utils.getWindowWidth(mContext) * 75 / 100;
//        window.setAttributes(lp);
            // 加载动画
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.loading_animation);
            // 使用ImageView显示动画
            spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        }
    }

    /**
     * 进度弹窗2
     */
    public void init2(final float progress, long total) {
        this.total = total;
        View mDialogView = View.inflate(mContext, R.layout.dialog_progress, null);   // 得到加载 view
        mProgress = mDialogView.findViewById(R.id.progress);
        // 进度
        final int pro = (int) ((progress / total) * 100);
        // 赋值
        mHandler.post(() -> mProgress.setProgress(pro));

        // 按返回键是否取消
        dialog.setCancelable(false);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(false);
        // 显示
        if (!((Activity) mContext).isFinishing()) {
            dialog.show();
            // 加载布局
            dialog.setContentView(mDialogView);
            /**【注意】 Dialog自定义宽度和高度要放在setContentView以后，否则没有效果！*/
            // 获取 dialog 的 window
            Window window = dialog.getWindow();
            // 背景变暗
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                // 设置居中
                window.setGravity(Gravity.CENTER);
            }
            // 设置宽度
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = StringUtil.getWindowWidth(mContext) * 75 / 100;
            window.setAttributes(lp);
            // 加载动画
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.loading_animation);
            // 使用ImageView显示动画
//        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        }
    }

    public void show(final float progress) {
        DecimalFormat format = new DecimalFormat("##.00");
        final String proStr = format.format(progress * 100 / total) + "%";
        // 更新进度
        mHandler.post(() -> tvProgress.setText(proStr));
        if (dialog != null && !((Activity) mContext).isFinishing()) {
            dialog.show();
        }
    }

    public void show2(final float progress) {
        final int pro = (int) (((float) progress / total) * 100);
//        LogUtil.d(HttpConstant.TAG, "ProgressDialogUtil 进度 -> " + pro);
        mHandler.post(() -> mProgress.setProgress(pro));
        if (dialog != null && !((Activity) mContext).isFinishing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void cancel() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    public boolean isShowing() {
        if (dialog.isShowing())
            return true;
        else
            return false;
    }
}
