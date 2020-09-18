package com.hdyg.common.util.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.hdyg.common.R;
import com.hdyg.common.util.KeyboardUtils;
import com.hdyg.common.util.SpannableStringUtils;
import com.hdyg.common.util.WordsUtil;
import com.hdyg.common.widget.ProtocolClickableSpan;

import java.util.Objects;

public class JDialog {
    // 上下文对象
    private Context context;
    // dialog 类型
    private JDialogType jDialogType;
    // 主题样式
    private int themeResId;
    // View
    private View view;
    // 相对显示的位置的控件
    private View oView;
    // 标题
    private CharSequence title;
    // 内容
    private CharSequence content;
    private View contentView;
    // 确认键
    private CharSequence ok;
    // 返回键
    private CharSequence cancel;
    // 输入框暗示文字
    private CharSequence hint;
    // 输入框类型
    private int inputType;
    // 下载链接
    private String downloadUrl;

    // 宽度
    private int width;
    // 高度
    private int height;
    // 动画
    private int anim;
    // 位置【默认居中显示】
    private int gravity = Gravity.CENTER;
    // 默认点击返回键取消
    private boolean cancelable = true;
    // 默认点击 Dialog 外围取消
    private boolean canceledOnTouchOutside = true;
    // 是否背景变暗
    private boolean isDarken = true;

    // 事件
    private JDialogLsitener.OnDismissListener mOnDismissListener;
    private JDialogLsitener.OnShowLsitener mOnShowListener;
    private JDialogLsitener.OnClickListener mOnClickListener;
    private JDialogLsitener.OnOkClickListener mOnOkClickListener;
    private JDialogLsitener.PasswordCallback mPasswordCallback;
    private JDialogLsitener.ProtocolListener mProtocolCallback;
    private boolean hasButton;      // password dialog 是否有确认按钮，默认有
    private boolean isClose;        // 是否显示 close 按钮

    // dialog
    public AlertDialog dialog = null;

    private JDialog(Builder builder) {
        this.context = builder.context;
        this.jDialogType = builder.jDialogType;
        this.themeResId = builder.themeResId;
        this.view = builder.view;
        this.title = builder.title;
        this.content = builder.content;
        this.contentView = builder.contentView;
        this.ok = builder.ok;
        this.cancel = builder.cancel;
        this.hint = builder.hint;
        this.inputType = builder.inputType;
        this.downloadUrl = builder.downloadUrl;
        this.width = builder.width;
        this.height = builder.height;
        this.anim = builder.anim;
        this.cancelable = builder.cancelable;
        this.canceledOnTouchOutside = builder.canceledOnTouchOutside;
        this.isDarken = builder.isDarken;
        this.mOnDismissListener = builder.mOnDismissListener;
        this.mOnShowListener = builder.mOnShowListener;
        this.mOnClickListener = builder.mOnClickListener;
        this.mOnOkClickListener = builder.mOnOkClickListener;
        this.mPasswordCallback = builder.mPasswordCallback;
        this.mProtocolCallback = builder.mProtocolCallback;
        this.hasButton = builder.hasButton;
        this.isClose = builder.isClose;
        init();
    }

    private void init() {
        if (context == null) {
            throw new NullPointerException("Context cannot be empty.");
        }
        dialog = new AlertDialog.Builder(context, themeResId).create();

        // 按返回键是否取消【默认取消】
        dialog.setCancelable(cancelable);
        // 点击 dialog 外围是否取消【默认取消】
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    public void show() {
        this.show(null, gravity);
    }

    public void show(int gravity) {
        this.show(null, gravity);
    }

    private void show(View v) {
        this.show(v, Gravity.BOTTOM);
    }

    private void show(View v, int gravity) {
        this.gravity = gravity;
        this.oView = v;
        if (dialog != null && !dialog.isShowing()) {
            try {
                dialog.show();
                // 加载布局
                if (jDialogType == JDialogType.PASSWORD) {
                    initPwdView();
                } else if (jDialogType == JDialogType.SYSYTEM) {
                    initSystemView();
                } else if (jDialogType == JDialogType.FROZEN) {
                    initFrozenView();
                } else {
                    initView();
                }
                // window
                initWindow();
                // 监听
                initListener();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 监听
     */
    private void initListener() {
        dialog.setOnDismissListener(dialogInterface -> {
            if (mOnDismissListener != null) mOnDismissListener.onDismiss();
            if (view != null) {
                KeyboardUtils.hideSoftInput(view);
            }
            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }
        });
        dialog.setOnShowListener(dialogInterface -> {
            if (mOnShowListener != null) mOnShowListener.onShow();
        });
    }

    /**
     * 布局
     */
    private void initView() {
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        } else {
            view = View.inflate(context, R.layout.sys_dialog_common, null);
            // 提示
            LinearLayout fromTip = view.findViewById(R.id.from_dialog_tip);
            TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
            LinearLayout llContent = view.findViewById(R.id.ll_dialog_content);
            TextView tvContent = view.findViewById(R.id.tv_dialog_content);
            EditText etEdit = view.findViewById(R.id.et_dialog_edit);
            ImageView btnClose = view.findViewById(R.id.btn_dialog_close);
            ProgressBar pbUpdate = view.findViewById(R.id.pb_dialog);
            LinearLayout llOk = view.findViewById(R.id.ll_dialog_ok);
            Button btnOk = view.findViewById(R.id.btn_dialog_ok);
            LinearLayout llCancel = view.findViewById(R.id.ll_dialog_cancel);
            Button btnCancel = view.findViewById(R.id.btn_dialog_cancel);
            View cutOfLine = view.findViewById(R.id.btn_dialog_line);
            // 进度
            LinearLayout fromProgress = view.findViewById(R.id.from_dialog_progress);
            ImageView ivProgress = view.findViewById(R.id.iv_dialog_progress);
            TextView ivProgressTip = view.findViewById(R.id.tv_dialog_progress_tip);
            if (jDialogType == JDialogType.PROGRESS) {
                fromTip.setVisibility(View.GONE);
                tvTitle.setVisibility(View.GONE);
                fromProgress.setVisibility(View.VISIBLE);
                // 加载动画
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
                // 使用ImageView显示动画
                ivProgress.startAnimation(animation);
            } else {
                fromTip.setVisibility(View.VISIBLE);
                fromProgress.setVisibility(View.GONE);
                // 标题
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(title);
                }
                // 内容
                tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
                if (contentView != null) {
                    llContent.setVisibility(View.VISIBLE);
                    tvContent.setVisibility(View.GONE);
                    llContent.addView(contentView);
                }
                if (!TextUtils.isEmpty(content)) {
                    tvContent.setText(Html.fromHtml(String.valueOf(content)));
                }

                // 是否是编辑方式
                switch (jDialogType) {
                    case TIP:       // 提示
                        if (isClose) {
                            btnClose.setVisibility(View.VISIBLE);
                        }
                        break;
                    case CHOOSE:    // 选择
                        llCancel.setVisibility(View.VISIBLE);
                        cutOfLine.setVisibility(View.VISIBLE);
                        break;
                    case EDIT:      // 编辑
                        llCancel.setVisibility(View.VISIBLE);
                        cutOfLine.setVisibility(View.VISIBLE);
                        tvContent.setVisibility(View.GONE);
                        etEdit.setVisibility(View.VISIBLE);
                        etEdit.setMaxLines(Integer.MAX_VALUE);
                        if (inputType != -1) {
                            etEdit.setInputType(inputType);
                        }
                        if (!TextUtils.isEmpty(hint)) {
                            etEdit.setHint(hint);
                        }
                        break;
                    case DOWNLOAD:  // 下载
                        llCancel.setVisibility(View.VISIBLE);
                        cutOfLine.setVisibility(View.VISIBLE);
                        btnOk.setText(R.string.download);
                        if (TextUtils.isEmpty(downloadUrl)) {
                            throw new NullPointerException("Download link cannot be empty.");
                        }
                        break;
                    case PROTOCOL:    // 隐私协议弹框
                        llCancel.setVisibility(View.VISIBLE);
                        cutOfLine.setVisibility(View.VISIBLE);
                        int start1 = content.toString().indexOf(WordsUtil.getString(R.string.sys_user_proto));
                        int start2 = content.toString().indexOf(WordsUtil.getString(R.string.sys_yinsi_proto));
                        int[] start = {start1, start2};
                        int[] lenth = {WordsUtil.getString(R.string.sys_user_proto).length(), WordsUtil.getString(R.string.sys_yinsi_proto).length()};
                        String[] tag = {ProtocolClickableSpan.TAG_PROTOCOL_USER, ProtocolClickableSpan.TAG_PROTOCOL_YINSI};
                        StringBuilder stringBuffer = new StringBuilder(content);
                        SpannableStringUtils.setText(mProtocolCallback, context, tvContent, stringBuffer,
                                SpannableStringUtils.SPAN_TYPE_CLICK,
                                R.color.colorAccent,
                                start, lenth, tag);
                        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
                }

                // 确定
                if (!TextUtils.isEmpty(ok)) {
                    btnOk.setText(ok);
                }
                // 取消
                if (!TextUtils.isEmpty(cancel)) {
                    btnCancel.setText(cancel);
                }
                btnClose.setOnClickListener(v -> {
                    if (dialog != null && dialog.isShowing()) dialog.dismiss();
                });
                // 确认
                btnOk.setOnClickListener(v -> {
                    if (KeyboardUtils.isSoftInputVisible((Activity) context)) {
                        KeyboardUtils.hideSoftInput((Activity) context);
                    }
                    if (jDialogType == JDialogType.DOWNLOAD) {
                        pbUpdate.setVisibility(View.VISIBLE);
                        llOk.setVisibility(View.GONE);
                        cutOfLine.setVisibility(View.GONE);
                        new DownLoadUtils(context, downloadUrl, pbUpdate);
                    } else {
                        if (mOnClickListener != null)
                            mOnClickListener.onOkClick(etEdit.getText().toString().trim());
                        if (mOnOkClickListener != null) mOnOkClickListener.onClick();
                        if (dialog != null && dialog.isShowing()) dialog.dismiss();
                    }
                });
                // 取消
                btnCancel.setOnClickListener(v -> {
                    if (KeyboardUtils.isSoftInputVisible((Activity) context)) {
                        KeyboardUtils.hideSoftInput((Activity) context);
                    }
                    if (mOnClickListener != null) mOnClickListener.onCancelClick();
                    if (dialog != null && dialog.isShowing()) dialog.dismiss();
                });
            }
        }
        dialog.setContentView(view);
    }

    //系统公告
    private void initSystemView() {
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        } else {
            view = View.inflate(context, R.layout.sys_dialog_txt, null);
            // 提示
            LinearLayout fromTip = view.findViewById(R.id.from_dialog_tip);
            fromTip.setVisibility(View.VISIBLE);
            TextView tvContent = view.findViewById(R.id.tv_dialog_content);
            // 内容
            tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
            if (!TextUtils.isEmpty(content)) {
                tvContent.setText(Html.fromHtml(String.valueOf(content)));
            }
            dialog.setContentView(view);
        }
    }

    //冻结
    private void initFrozenView() {
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        } else {
            view = View.inflate(context, R.layout.sys_dialog_txt, null);
            // 提示
            LinearLayout fromTip = view.findViewById(R.id.from_dialog_frozentip);
            fromTip.setVisibility(View.VISIBLE);
            TextView tvContent = view.findViewById(R.id.tv_dialog_frozen_content);
            // 内容
            tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
            if (!TextUtils.isEmpty(content)) {
                tvContent.setText(Html.fromHtml(String.valueOf(content)));
            }
            dialog.setContentView(view);
        }
    }

    /**
     * 布局[密码]
     */
    private void initPwdView() {
        if (view != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        } else {
            view = View.inflate(context, R.layout.sys_dialog_password, null);
            TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
            ImageView btnClose = view.findViewById(R.id.btn_dialog_close);
            TextView tvContent = view.findViewById(R.id.tv_dialog_content);
            LinearLayout llContent = view.findViewById(R.id.ll_dialog_content);
            ImageView ivPwd1 = view.findViewById(R.id.et_dialog_pwd_1);
            ImageView ivPwd2 = view.findViewById(R.id.et_dialog_pwd_2);
            ImageView ivPwd3 = view.findViewById(R.id.et_dialog_pwd_3);
            ImageView ivPwd4 = view.findViewById(R.id.et_dialog_pwd_4);
            ImageView ivPwd5 = view.findViewById(R.id.et_dialog_pwd_5);
            ImageView ivPwd6 = view.findViewById(R.id.et_dialog_pwd_6);
            EditText etPwd = view.findViewById(R.id.et_dialog_pwd);
            Button btnConfirm = view.findViewById(R.id.btn_dialog_pwd_confirm);
            // 设置输入类型
            if (inputType != -1) {
                etPwd.setInputType(inputType);
            }
            etPwd.requestFocus();
            Objects.requireNonNull(dialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            etPwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    char[] array = s.toString().trim().toCharArray();
                    for (int i = 0; i < array.length; i++) {
                        //设置图片为黑色圆点
                        if (i == 0) {
                            ivPwd1.setImageResource(R.mipmap.ic_password_icon);
                        } else if (i == 1) {
                            ivPwd2.setImageResource(R.mipmap.ic_password_icon);
                        } else if (i == 2) {
                            ivPwd3.setImageResource(R.mipmap.ic_password_icon);
                        } else if (i == 3) {
                            ivPwd4.setImageResource(R.mipmap.ic_password_icon);
                        } else if (i == 4) {
                            ivPwd5.setImageResource(R.mipmap.ic_password_icon);
                        } else if (i == 5) {
                            ivPwd6.setImageResource(R.mipmap.ic_password_icon);
                        }
                    }
                    clearTextView(array.length, ivPwd1, ivPwd2, ivPwd3, ivPwd4, ivPwd5, ivPwd6);
                    //自动提交
                    if (!hasButton && array.length == 6) {
                        if (mPasswordCallback != null)
                            mPasswordCallback.callback(String.valueOf(array));
                        if (dialog != null && dialog.isShowing()) dialog.dismiss();
                    }
                }
            });
            // 标题
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
            }
            // 内容
            if (contentView != null) {
                llContent.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.GONE);
                llContent.addView(contentView);
            }
            if (!TextUtils.isEmpty(content)) {
                tvContent.setText(Html.fromHtml(String.valueOf(content)));
            }
            // 确认按钮
            if (!TextUtils.isEmpty(ok)) {
                btnConfirm.setText(ok);
            }
            if (!hasButton) {
                btnConfirm.setVisibility(View.GONE);
            }
            // 取消
            btnClose.setOnClickListener(v -> {
                if (KeyboardUtils.isSoftInputVisible((Activity) context)) {
                    KeyboardUtils.hideSoftInput((Activity) context);
                }
                if (mOnClickListener != null) mOnClickListener.onCancelClick();
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            });
            // 确定
            btnConfirm.setOnClickListener(v -> {
                if (KeyboardUtils.isSoftInputVisible((Activity) context)) {
                    KeyboardUtils.hideSoftInput((Activity) context);
                }
                if (mPasswordCallback != null)
                    mPasswordCallback.callback(etPwd.getText().toString().trim());
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            });
        }
        dialog.setContentView(view);
    }

    private void clearTextView(int length, ImageView... ivPwd) {
        for (int i = 0; i < 6; i++) {
            //比如当前密码长度为4位，当大于4的图标，就要还原成未输入情况
            if (i > length - 1) {
                ivPwd[i].setImageResource(0);
            }
        }
    }

    /**
     * 【注意】 Dialog自定义宽度和高度要放在setContentView以后，否则没有效果！
     */
    private void initWindow() {
        // 获取 Dialog 的 Window
        Window window = dialog.getWindow();
        if (window != null) {
            // 背景变暗
            if (isDarken) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }
            // 动画
            if (anim != -1) {
                window.setWindowAnimations(anim);
            }
            // 设置宽度高度
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width > 0
                    ? width
                    : jDialogType == JDialogType.PROGRESS
                    ? getWindowWidth(context) * 90 / 100
                    : getWindowWidth(context) * 90 / 100;
            lp.height = height > 0 ? height : WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            // 解决 dialog 中 EditText 控件无法输入
            // 清除 flags，获取焦点
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            // 弹出输入法
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            // 设置位置
            window.setGravity(gravity);
//            setLocation(window, view, oView, gravity);
        }
    }

    /**
     * 设置 dialog 显示的位置
     *
     * @param window     dialog 的 window 窗体
     * @param dView      dialog 的内容控件
     * @param attachView 依附的控件
     * @param gravity    相对于 attachView 控件的位置 上下左右中
     */
    // TODO: 2019/4/27 问题：dialog 控件的高度、标题栏高度
    private void setLocation(Window window, View dView, View attachView, int gravity) {
        /* 对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向下移动,负值向上移动.
         * gravity的默认值为Gravity.CENTER
         */
//        window.requestFeature(Window.);
        // 设置宽度【默认为屏幕宽度的80%】
        WindowManager.LayoutParams lp = window.getAttributes();
        if (jDialogType == JDialogType.PROGRESS) {
            lp.width = width > 0 ? width : WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = height > 0 ? height : WindowManager.LayoutParams.WRAP_CONTENT;
        } else {
            lp.width = width > 0 ? width : getWindowWidth(context) * 80 / 100;
            lp.height = height > 0 ? height : WindowManager.LayoutParams.WRAP_CONTENT;
        }
//        window.setAttributes(lp);
        if (attachView == null) {
            window.setGravity(this.gravity);
        } else {
            window.setGravity(Gravity.TOP | Gravity.LEFT);
            int titleHeight = 0;
            int mScreeWidth = getWindowWidth(context);     // 屏幕宽度
            int mScreeHeight = getWindowHeight(context);   // 屏幕高度
            int dx, dy, dw, dh; // dialog 控件的 x、y 坐标值，宽度、高度
            int ax, ay, aw, ah; // 依附的控件的 x、y 坐标值，宽度、高度
            int x = 0, y = 0;       // 计算后的位置xy
            // dialog 控件
            dx = lp.x;
            dy = lp.y;
            dw = lp.width;
            dh = lp.height;
            // 相对位置控件
            ax = (int) attachView.getX();
            ay = (int) attachView.getY();
            aw = attachView.getWidth();
            ah = attachView.getHeight();
            switch (gravity) {
                case Gravity.TOP:
                    // 上方
                    x = (ax + aw - dw) / 2;
                    y = ay + titleHeight - dh;
                    break;
                case Gravity.BOTTOM:
                    // 下方
                    x = (ax + aw - dw) / 2;
                    y = ay + (ah + titleHeight);
                    break;
                case Gravity.LEFT:
                    // 左边
                    x = ax - dw;
//                    y = ay + (ah + titleHeight);
                    y = ay - (ah - dh) / 2 + titleHeight;
                    break;
                case Gravity.RIGHT:
                    // 右边
                    x = ax + dw;
                    y = ay - (ah - dh) / 2 + titleHeight;
                    break;
                case Gravity.CENTER:
                    // 中间
                    x = (ax + aw) / 2;
                    y = (ay + ah) / 2;
                    break;
            }
            lp.x = x;
            lp.y = y;

            window.setAttributes(lp);
        }
    }

    /**
     * 设置控件所在的位置xy，并且不改变宽高，
     *
     * @param view 控件
     * @param x    x 坐标
     * @param y    y 坐标
     */

    /**
     * 获取屏幕宽度 (px)
     *
     * @param mContext 上下文对象
     * @return
     */
    private static int getWindowWidth(Context mContext) {
        int mWidth = 0;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        //display = getWindowManager().getDefaultDisplay();
        //display.getMetrics(dm)（把屏幕尺寸信息赋值给DisplayMetrics dm）;
        mWidth = dm.widthPixels;
        return mWidth;
    }

    /**
     * 获取屏幕高度 (px)
     *
     * @param mContext 上下文对象
     * @return
     */
    private static int getWindowHeight(Context mContext) {
        int mHeight = 0;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        //display = getWindowManager().getDefaultDisplay();
        //display.getMetrics(dm)（把屏幕尺寸信息赋值给DisplayMetrics dm）;
        mHeight = dm.heightPixels;
        return mHeight;
    }

    public static class Builder {
        // 上下文对象
        private Context context;
        // dialog 类型【默认提示框】
        private JDialogType jDialogType = JDialogType.TIP;
        // 主题样式【默认样式】
        private int themeResId = R.style.picDialog;
        // View
        private View view;
        // 标题
        private CharSequence title;
        // 内容
        private CharSequence content;
        private View contentView;
        // 确认键
        private CharSequence ok;
        // 返回键
        private CharSequence cancel;
        // 输入框暗示文字
        private CharSequence hint;
        // 输入框类型
        private int inputType = -1;
        // 下载链接
        private String downloadUrl;

        // 宽度、高度
        private int width = 0;
        private int height = 0;
        // 动画 【-1 表示没有设置动画】
        private int anim = -1;
        // 默认点击返回键取消
        private boolean cancelable = true;
        // 默认点击 Dialog 外围取消
        private boolean canceledOnTouchOutside = true;
        // 是否背景变暗
        private boolean isDarken = true;

        // 事件
        private JDialogLsitener.OnDismissListener mOnDismissListener;
        private JDialogLsitener.OnShowLsitener mOnShowListener;
        private JDialogLsitener.OnClickListener mOnClickListener;
        private JDialogLsitener.OnOkClickListener mOnOkClickListener;
        private JDialogLsitener.PasswordCallback mPasswordCallback;
        private JDialogLsitener.ProtocolListener mProtocolCallback;
        private boolean hasButton = true;   // password dialog 是否有确认按钮，默认有
        private boolean isClose;        // 是否显示 close 按钮

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, JDialogType jDialogType) {
            this.context = context;
            this.jDialogType = jDialogType;
        }

        public Builder setThemeResId(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int title) {
            setTitle(context.getText(title));
            return this;
        }

        public Builder setContent(CharSequence content) {
            this.content = content;
            return this;
        }

        public Builder setContent(int content) {
            setContent(context.getText(content));
            return this;
        }

        public Builder setContent(View view) {
            this.contentView = view;
            return this;
        }

        public Builder setOk(CharSequence ok) {
            this.ok = ok;
            return this;
        }

        public Builder setOk(int ok) {
            setOk(context.getText(ok));
            return this;
        }

        public Builder setCancel(CharSequence cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setCancel(int cancel) {
            setCancel(context.getText(cancel));
            return this;
        }

        public Builder setHint(CharSequence hint) {
            this.hint = hint;
            return this;
        }

        public Builder setHint(int hint) {
            setHint(context.getText(hint));
            return this;
        }

        public Builder setInputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        /**
         * @param downloadUrl 下载链接
         */
        public Builder setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setAnim(int anim) {
            this.anim = anim;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder isDarken(boolean isDarken) {
            this.isDarken = isDarken;
            return this;
        }

        public Builder setOnDismissListener(JDialogLsitener.OnDismissListener listener) {
            this.mOnDismissListener = listener;
            return this;
        }

        public Builder setOnShowListener(JDialogLsitener.OnShowLsitener listener) {
            this.mOnShowListener = listener;
            return this;
        }

        public Builder setOnClickListener(JDialogLsitener.OnClickListener listener) {
            this.mOnClickListener = listener;
            return this;
        }

        public Builder setOnOkClickListener(JDialogLsitener.OnOkClickListener listener) {
            this.mOnOkClickListener = listener;
            return this;
        }

        public Builder setOnOkClickListener(JDialogLsitener.OnOkClickListener listener, boolean isClose) {
            this.mOnOkClickListener = listener;
            this.isClose = isClose;
            return this;
        }

        public Builder setPasswordCallBack(JDialogLsitener.PasswordCallback callBack) {
            this.mPasswordCallback = callBack;
            return this;
        }

        public Builder setProtocolCallBack(JDialogLsitener.ProtocolListener callBack) {
            this.mProtocolCallback = callBack;
            return this;
        }

        public Builder setPasswordCallBack(JDialogLsitener.PasswordCallback callBack, boolean hasConfirmButton) {
            this.mPasswordCallback = callBack;
            this.hasButton = hasConfirmButton;
            return this;
        }

        public JDialog build() {
            return new JDialog(this);
        }
    }
}
