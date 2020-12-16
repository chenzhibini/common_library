package com.hdyg.testcommon.mvp.view.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.hdyg.common.common.AppManager;
import com.hdyg.common.common.BasePresenter;
import com.hdyg.common.common.CommonModule;
import com.hdyg.common.common.IBaseView;
import com.hdyg.common.util.CountDownTimerUtil;
import com.hdyg.common.util.LangUtil.MultiLanguageUtil;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.SysStyleUtil;
import com.hdyg.common.util.ToastUtil;
import com.hdyg.common.util.dialog.JDialog;
import com.hdyg.common.util.dialog.JDialogType;
import com.hdyg.testcommon.R;
import org.greenrobot.eventbus.EventBus;
import butterknife.ButterKnife;


/**
 * 基类  用于所有的activity继承
 *
 * @author
 * @time 2018/3/8 11:30
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    private ForceOffLineReceiver receive;
    public static final String UN_TOKEN_BROCAST = CommonModule.getAppContext().getPackageName() + ".OFF";
    protected Context mContext;
    protected Bundle mBundle;
    protected T mPresenter; // Presenter 对象
    protected InputMethodManager inputMethodManager;
    protected PopupWindow mPopupWindow;
    protected static final int REQUEST_CODE = 0x0001;
    protected String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setNoTitile();//取消标题的方法
        mContext = this;
        try {
            int layoutResID = getLayoutId();
            if (layoutResID != 0) {
                inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // 沉浸状态栏
                steepStatusBar();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏显示
                setContentView(getLayoutId());

                if (savedInstanceState == null) {
                    // 绑定 ButterKnife
                    ButterKnife.bind(this);
                    createPresenter();
                    AppManager.getAppManager().addActivity(this);
                    if (mPresenter != null) {
                        mPresenter.attachView(this);
                    }
                    initView();
                    initData();
                    //如果要使用 Eventbus 请将此方法返回 true
                    if (useEventBus()) {
                        //注册 Eventbus
                        EventBus.getDefault().register(this);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG + ",onCreate Exception=>" + e.getMessage());
        }
    }

    /**
     * 子类Activity要使用EventBus只需要重写此方法返回true即可
     *
     * @return true/false
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 发送EvenBus消息
     */
    protected void sendEvenbusMessage(Object object) {
        EventBus.getDefault().post(object);
    }

    // api = 26时  兼容  ======== 开 始 =========
    @Override
    protected void attachBaseContext(Context newBase) {
        MultiLanguageUtil.autoUpdateLanguageEnviroment(newBase);
        super.attachBaseContext(newBase);
    }
    // ====================    结 束     ==================

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UN_TOKEN_BROCAST);
        receive = new ForceOffLineReceiver();
        registerReceiver(receive, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receive != null) {
            unregisterReceiver(receive);
            receive = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if (!this.isFinishing()) {
            hideLoading();
        }
        //如果要使用 Eventbus 请将此方法返回 true
        if (useEventBus()) {
            //解除注册 Eventbus
            EventBus.getDefault().unregister(this);
        }
        CountDownTimerUtil.getInstance().stopTimer();
        AppManager.getAppManager().finishActivity(this);
    }

    //设置沉浸式状态栏
    private void steepStatusBar() {
        SysStyleUtil.setStatusBarLightMode(this, android.R.color.transparent, false);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 创建 Presenter 对象
     *
     * @return
     */
    protected abstract void createPresenter();

    public void setNoTitile() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected void setTopBarBg(int resid) {
        LinearLayout llTop = findViewById(R.id.ll_top_bar_bg);
        if (llTop != null) {
            llTop.setBackgroundResource(resid);
        }
    }

    protected void setTopTitle(Object title) {
        TextView titleView = findViewById(R.id.tvTopTitle);
        if (titleView != null) {
            if (title instanceof String) {
                titleView.setText((String) title);
            }
            if (title instanceof Integer) {
                titleView.setText((Integer) title);
            }
        }
    }

    protected void setTopTitleColor(int color) {
        TextView titleView = findViewById(R.id.tvTopTitle);
        if (titleView != null)
            titleView.setTextColor(color);
    }

    public void backClick(View v) {
        if (v.getId() == R.id.ll_top_left) {
            onBackPressed();
        }
    }

    protected void setLeftVisible(boolean visible) {
        ImageView leftImg = findViewById(R.id.iv_top_left);
        if (leftImg != null)
            leftImg.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    protected void setLeftImg(int leftIcon) {
        ImageView leftImg = findViewById(R.id.iv_top_left);
        if (leftImg != null)
            leftImg.setImageResource(leftIcon);
    }

    protected void setLeftColor(int color) {
        TextView View = findViewById(R.id.tv_top_left);
        if (View != null)
            View.setTextColor(color);
    }

    protected void setLeftText(Object txt) {
        TextView View = findViewById(R.id.tv_top_left);
        if (View != null) {
            if (txt instanceof String) {
                View.setText((String) txt);
            }
            if (txt instanceof Integer) {
                View.setText((Integer) txt);
            }
        }
    }

    protected void setRightImg(int Icon) {
        ImageView rightImg = findViewById(R.id.iv_top_right);
        if (rightImg != null) {
            rightImg.setVisibility(View.VISIBLE);
            rightImg.setImageResource(Icon);
        }
    }

    protected void setRightText(Object txt) {
        TextView View = findViewById(R.id.tv_top_right);
        if (View != null) {
            if (txt instanceof String) {
                View.setText((String) txt);
            }
            if (txt instanceof Integer) {
                View.setText((Integer) txt);
            }
        }
    }

    protected void setRightTextColor(int color) {
        TextView View = findViewById(R.id.tv_top_right);
        if (View != null)
            View.setTextColor(color);
    }

    /**
     * 初始化加载动画视图
     */
    private JDialog dialog;

    private void initLoadingView() {
        if (dialog == null || dialog.dialog == null) {
            dialog = new JDialog.Builder(mContext, JDialogType.PROGRESS).build();
        }
    }

    @Override
    public void showLoading() {
        if (!this.isFinishing()) {
            initLoadingView();
            if (dialog != null)
                dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null && !this.isFinishing()) {
            dialog.dismiss();
            dialog.dialog = null;
            dialog = null;
        }
    }

    @Override
    public void onError(String code, String msg) {
        toastNotifyShort(msg == null ? "" : msg);
    }


    /**
     * 封装toast
     */
    public void toastNotifyShort(Object notify) {
        String str = null;
        if (notify instanceof String)
            str = notify.toString();
        else if (notify instanceof Integer)
            str = getResources().getString((Integer) notify);
        ToastUtil.show(str);
    }

    /**
     * activity 跳转[无参]
     *
     * @param className
     */
    public void startActivity(Class<?> className) {
        startActivity(className, null);
    }

    /**
     * activity 跳转[有参]
     *
     * @param className
     * @param bundle
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent mIntent = new Intent();
        mIntent.setClass(this, className);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        if (getPackageManager().resolveActivity(mIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            this.startActivity(mIntent);
        } else {
            LogUtils.e("activity not found for " + className.getSimpleName());
        }
    }

    class ForceOffLineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.sys_caveat);
            builder.setMessage(R.string.sys_device);
            builder.setCancelable(false); // 将对话框设置为不可取消
            // 给按钮添加注册监听
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 点击按钮所调用的方法
                    AppManager.getAppManager().staLoginActivity();
                }
            });
            builder.show();
        }
    }
}
