package com.hdyg.testcommon.mvp.view.activity;

import android.os.Handler;
import android.text.InputType;
import android.view.View;

import com.hdyg.common.common.AppConfig;
import com.hdyg.common.common.BaseActivity;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.SPUtils;
import com.hdyg.common.util.ThreadUtil;
import com.hdyg.common.util.ToastUtil;
import com.hdyg.common.util.WordsUtil;
import com.hdyg.common.util.dialog.JDialog;
import com.hdyg.common.util.dialog.JDialogLsitener;
import com.hdyg.common.util.dialog.JDialogType;
import com.hdyg.testcommon.R;

import butterknife.OnClick;

/**
 * @author CZB
 * @describe
 * @time 2020/9/18 10:50
 */
public class DialogTestActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_test;
    }

    @Override
    protected void initView() {
        setTopTitle("弹窗展示");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void createPresenter() {

    }


    @OnClick({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                new JDialog.Builder(mContext, JDialogType.CHOOSE)
                        .setOnClickListener(new JDialogLsitener.OnClickListener() {
                            @Override
                            public void onOkClick(String s) {

                            }

                            @Override
                            public void onCancelClick() {

                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.tv_2:
                new JDialog.Builder(mContext, JDialogType.TIP)
                        .setContent("提示提示")
                        .build()
                        .show();
                break;
            case R.id.tv_3:
                new JDialog.Builder(mContext, JDialogType.EDIT)
                        .setHint("编辑编辑")
                        .setOnClickListener(new JDialogLsitener.OnClickListener() {
                            @Override
                            public void onOkClick(String s) {
                                ToastUtil.show(s);
                            }

                            @Override
                            public void onCancelClick() {

                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.tv_4:
                new JDialog.Builder(mContext, JDialogType.PASSWORD)
                        .setContent("密码密码")
                        .setPasswordCallBack(s -> ToastUtil.show(s))
                        .build()
                        .show();
                break;
            case R.id.tv_5:
                new JDialog.Builder(mContext, JDialogType.PROTOCOL)
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setContent(WordsUtil.getString(R.string.sys_proto))
                        .setOnClickListener(new JDialogLsitener.OnClickListener() {
                            @Override
                            public void onOkClick(String s) {
                                SPUtils.put(SpMsg.SHOW_PROTOCOL, true);
                                ToastUtil.show("同意协议");
                            }

                            @Override
                            public void onCancelClick() {
                                ToastUtil.show("不同意协议");
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
                        .build()
                        .show();
                break;
            case R.id.tv_6:
                showLoading();
                new Handler().postDelayed(() -> hideLoading(),2000);
                break;

        }
    }
}
