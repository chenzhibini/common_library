package com.hdyg.testcommon.mvp.view.activity;

import android.view.View;

import com.hdyg.common.common.BaseActivity;
import com.hdyg.common.util.IntentMapUtil;
import com.hdyg.common.util.WordsUtil;
import com.hdyg.testcommon.R;

import butterknife.OnClick;

/**
 * @author CZB
 * @describe
 * @time 2020/9/18 16:03
 */
public class IntentMapActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.intentmap_test;
    }

    @Override
    protected void initView() {
        setTopTitle("跳转第三方地图");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void createPresenter() {

    }

    @OnClick({R.id.tv_gaode, R.id.tv_tengxun, R.id.tv_baidu})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gaode:
                IntentMapUtil.openGaoDeMap(mContext, 24.48263, 118.148286, "忠仑公园", WordsUtil.getString(R.string.app_name));
                break;
            case R.id.tv_tengxun:
                IntentMapUtil.openTencentMap(mContext, 24.48263, 118.148286, "忠仑公园");
                break;
            case R.id.tv_baidu:
                IntentMapUtil.openBaiduMap(mContext, 24.48263, 118.148286, "忠仑公园");
                break;
        }
    }
}
