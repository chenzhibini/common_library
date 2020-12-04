package com.hdyg.testcommon.mvp.view.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.hdyg.common.common.BasePresenter;
import com.hdyg.common.common.IBaseView;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.ToastUtil;
import com.hdyg.common.util.dialog.JDialog;
import com.hdyg.common.util.dialog.JDialogType;
import butterknife.ButterKnife;

/**
 * Author
 * Time   2017/6/19
 * 当前类注释：所有fragment类的基类
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IBaseView {

    protected T mPresenter;
    protected Context mContext;
    protected Bundle mBundle;
    protected InputMethodManager inputMethodManager;
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getActivity(), getLayoutId(), null);
            inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            ButterKnife.bind(this, view);
            mContext = getActivity();
            createPresenter();
            if (mPresenter != null) {
                mPresenter.attachView(this);
            }
            initView();
            initData();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
        if (mPresenter != null)
            mPresenter.onDestroy();
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected abstract void createPresenter();

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 初始化加载动画视图
     */
    private JDialog dialog;
    private void initLoadingView() {
        if (dialog == null) {
            dialog = new JDialog.Builder(mContext, JDialogType.PROGRESS).build();
        }
    }

    @Override
    public void showLoading() {
        if (getActivity()!= null && !getActivity().isFinishing()) {
            initLoadingView();
            if (!dialog.dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null && getActivity()!= null && !getActivity().isFinishing())
            dialog.dismiss();
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
     * activity跳转（无参数）
     *
     * @param className
     */

    public void startActivity(Class<?> className) {
        startActivity(className, null);
    }

    /**
     * activity跳转（有参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent mIntent = new Intent();
        mIntent.setClass(mContext, className);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        if (mContext.getPackageManager().resolveActivity(mIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            this.startActivity(mIntent);
        } else {
            LogUtils.e("activity not found for " + className.getSimpleName());
        }
    }

    /**
     * activity 跳转带回调[无参]
     *
     * @param className
     * @param requestCode
     */
    public void startActivityForResult(Class<?> className, int requestCode) {
        startActivityForResult(className, null, requestCode);
    }

    /**
     * activity 跳转带回调[有参]
     *
     * @param className
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> className, Bundle bundle,
                                       int requestCode) {
        Intent mIntent = new Intent();
        mIntent.setClass(mContext, className);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        if (mContext.getPackageManager().resolveActivity(mIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            startActivityForResult(mIntent, requestCode);
        } else {
            LogUtils.e("activity not found for " + className.getSimpleName());
        }
    }

}