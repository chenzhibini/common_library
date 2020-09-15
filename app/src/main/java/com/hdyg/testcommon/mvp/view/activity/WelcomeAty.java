package com.hdyg.testcommon.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.hdyg.testcommon.R;
import com.hdyg.common.common.AppConfig;
import com.hdyg.common.common.AppManager;
import com.hdyg.common.common.BaseActivity;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.SPUtils;
import com.hdyg.common.util.ToastUtil;
import com.hdyg.common.util.WordsUtil;
import com.hdyg.common.util.dialog.JDialog;
import com.hdyg.common.util.dialog.JDialogLsitener;
import com.hdyg.common.util.dialog.JDialogType;

import java.util.List;
import cn.com.superLei.aoparms.annotation.Permission;
import cn.com.superLei.aoparms.annotation.PermissionDenied;

/**
 * 欢迎页
 *
 * @author CZB
 * @time 2018/2/27 10:20
 */
public class WelcomeAty extends BaseActivity {

    private long mExitTime;
    private boolean isShowProtocolDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

        isShowProtocolDialog = SPUtils.get(SpMsg.SHOW_PROTOCOL, false);
        if (isShowProtocolDialog) {
            requestPermission();
        } else {
            new JDialog.Builder(mContext, JDialogType.PROTOCOL)
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setOnClickListener(new JDialogLsitener.OnClickListener() {
                        @Override
                        public void onOkClick(String s) {
                            SPUtils.put(SpMsg.SHOW_PROTOCOL, true);
                            requestPermission();
                        }

                        @Override
                        public void onCancelClick() {
                            finish();
                        }
                    })
                    .setProtocolCallBack(new JDialogLsitener.ProtocolListener() {
                        @Override
                        public void userProtocolListener() {
                            UIHelper.showWeb(mContext, AppConfig.Protocol.USER_PROTOCOL);
                        }

                        @Override
                        public void yinsiProtocolListener() {
                            UIHelper.showWeb(mContext, AppConfig.Protocol.YINSI_PROTOCOL);
                        }
                    })
                    .setContent(WordsUtil.getString(R.string.sys_proto))
                    .build()
                    .show();
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void createPresenter() {

    }

    /**
     * 有权限时执行下一步
     */
    private void nextDoing() {
        new Handler().postDelayed(() -> {
            if (TextUtils.isEmpty(SPUtils.get(SpMsg.TOKEN, ""))) {
                UIHelper.showLogin(mContext);
            } else {
                UIHelper.showMain(mContext);
            }
            WelcomeAty.this.finish();
        }, 500);
    }

    // 双击退出app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toastNotifyShort(R.string.sys_out_login);
                mExitTime = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().AppExit(mContext);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**************************权限适配****************************/
    /**
     * value 权限值
     * rationale 拒绝后的下一次提示(开启后，拒绝后，下一次会先提示该权限申请提示语)
     * requestCode 权限请求码标识
     */
    @Permission(value = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION})
    private void requestPermission() {
        nextDoing();
    }

    @PermissionDenied
    public void permissionDenied(int requestCode, List<String> denyList) {
        ToastUtil.show(R.string.sys_permission_fail);
        new JDialog.Builder(mContext, JDialogType.CHOOSE)
                .setContent(R.string.sys_permission_open)
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setOnClickListener(new JDialogLsitener.OnClickListener() {
                    @Override
                    public void onOkClick(String s) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.MAIN");
                        intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
                        startActivity(intent);
                        AppManager.getAppManager().AppExit(mContext);
                    }

                    @Override
                    public void onCancelClick() {
                        AppManager.getAppManager().AppExit(mContext);
                    }
                })
                .build()
                .show();
    }

}
