package com.hdyg.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

@SuppressWarnings("unchecked")
public class PopWindowUtil {

    private static PopWindowUtil instance;

    private PopupWindow mPopupWindow;
    private boolean b = true;

    // 私有化构造方法，变成单例模式
    private PopWindowUtil() {

    }

    // 对外提供一个该类的实例，考虑多线程问题，进行同步操作
    public static PopWindowUtil getInstance() {
        if (instance == null) {
            synchronized (PopWindowUtil.class) {
                if (instance == null) {
                    instance = new PopWindowUtil();
                }
            }
        }
        return instance;
    }

    /**
     * @param cx   activity
     * @param view 传入内容的view
     * @return
     */
    public PopWindowUtil makePopupWindow(Context cx, View view) {
        int withdParams = ViewGroup.LayoutParams.MATCH_PARENT;
        int heightParams = ViewGroup.LayoutParams.MATCH_PARENT;
        return makePopupWindow(cx, view, withdParams, heightParams, true);
    }

    /**
     * @param cx   activity
     * @param view 传入内容的view
     * @return
     */
    public PopWindowUtil makePopupWindow(Context cx, View view, int withdParams) {
        int heightParams = ViewGroup.LayoutParams.WRAP_CONTENT;
        return makePopupWindow(cx, view, withdParams, heightParams, true);
    }
    public PopWindowUtil makePopupWindow(Context cx, View view, int withdParams,boolean isClose) {
        int heightParams = ViewGroup.LayoutParams.WRAP_CONTENT;
        return makePopupWindow(cx, view, withdParams, heightParams, isClose);
    }

    /**
     * @param cx   activity
     * @param view 传入内容的view
     * @return
     */
    public PopWindowUtil makePopupWindow(Context cx, View view, int withdParams, int heightParams) {
        return makePopupWindow(cx, view, withdParams, heightParams, true);
    }

    /**
     * 创建 PopupWindow
     *
     * @param cx               上下文对象
     * @param view             传入内容的 View
     * @param outsideTouchable 外部是否可点击
     * @return
     */
    public PopWindowUtil makePopupWindow(Context cx, View view, int withdParams, int heightParams, boolean outsideTouchable) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wmManager = (WindowManager) cx.getSystemService(Context.WINDOW_SERVICE);
        wmManager.getDefaultDisplay().getMetrics(dm);


        mPopupWindow = new PopupWindow(view, withdParams, heightParams);
        if (outsideTouchable) {
            // 实例化一个ColorDrawable颜色为透明，不设置为半透明是因为带圆角
            ColorDrawable dw = new ColorDrawable(cx.getResources().getColor(android.R.color.transparent));
            mPopupWindow.setBackgroundDrawable(dw);
        }
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.setFocusable(true);        // 设置PopupWindow可获得焦点
        mPopupWindow.setTouchable(true);        // 设置PopupWindow可触摸
        mPopupWindow.setOutsideTouchable(outsideTouchable); // 设置非PopupWindow区域可触摸

        return instance;
    }


    /**
     * 背景是否透明
     *
     * @param b
     */
    public PopWindowUtil isTransitionBg(boolean b) {
        this.b = b;
        return instance;
    }

    /**
     * 背景透明度
     *
     * @param cx
     */
    private void initBgAlpha(Context cx) {
        if (b) {
            // 弹出PopupWindow时让后面的界面变暗
            WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
            parms.alpha = 0.5f;
            ((Activity) cx).getWindow().setAttributes(parms);
        }
    }

    /**
     * 监听
     *
     * @param cx
     */
    private void initListener(final Context cx) {
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // PopupWindow消失后让后面的界面变亮
                WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
                parms.alpha = 1.0f;
                ((Activity) cx).getWindow().setAttributes(parms);
                if (mListener != null) {
                    mListener.dissmiss();
                }
            }
        });
    }

    /**
     * @param cx      此处必须为Activity的实例
     * @param view    显示在该控件
     * @param gravity 方位 Gravity.
     * @param xOff    距离view的x轴偏移量
     * @param yOff    距离view的y轴偏移量
     * @param anim    弹出及消失动画
     * @return
     */
    public PopupWindow showLocation(final Context cx, View view, int gravity, int xOff, int yOff, int anim) {
        // 弹出动画
//        mPopupWindow.setAnimationStyle(anim);
        // 背景
        initBgAlpha(cx);

        int[] positon = new int[2];
        view.getLocationOnScreen(positon);
        // 弹窗的出现位置，在指定 view 的 gravity 方位
        mPopupWindow.showAtLocation(view, gravity, positon[0] + xOff, positon[1] + yOff);
        // 监听
        initListener(cx);
        return mPopupWindow;
    }

    /**
     * @param cx   此处必须为Activity的实例
     * @param view 显示在控件下方
     * @param anim 弹出及消失动画
     * @return
     */
    public PopupWindow showAsDropDown(final Context cx, View view, int anim) {
        // 弹出动画
        if (anim != 0){
            mPopupWindow.setAnimationStyle(anim);
        }
        // 背景
        initBgAlpha(cx);
        // 弹窗的出现位置，控件下方
        mPopupWindow.showAsDropDown(view);
        // 监听
        initListener(cx);
        return mPopupWindow;
    }

    private interface OnDissmissListener {
        void dissmiss();
    }

    private OnDissmissListener mListener;

    public void setOnDissmissListener(OnDissmissListener listener) {
        mListener = listener;
    }
}