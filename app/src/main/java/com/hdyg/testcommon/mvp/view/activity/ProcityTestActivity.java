package com.hdyg.testcommon.mvp.view.activity;

import android.view.View;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.ProCityUtil;
import com.hdyg.testcommon.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * @author CZB
 * @describe
 * @time 2020/9/16 15:23
 */
public class ProcityTestActivity extends BaseActivity {

    ProCityUtil proCityUtil;
    private List<String> data1, data2, data3;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pro_test;
    }

    @Override
    protected void initView() {
        setTopTitle("选择器展示");
        proCityUtil = new ProCityUtil(mContext);
        //无关联数据
        data1 = new ArrayList<>();
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data1.add("one-->" + i);
        }
        for (int i = 0; i < 5; i++) {
            data2.add("two-->" + i);
        }
        for (int i = 0; i < 5; i++) {
            data3.add("three-->" + i);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void createPresenter() {

    }

    @OnClick({R.id.tv_time, R.id.tv_pro, R.id.tv_cus})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:
                proCityUtil
                        .setTimeType(true, true, true, true, true, true)
                        .setTimeParam(0, 0, null, 0, 0, null, 0, 0, null, 0, 0, 0, true, false)
                        .setTimeListener(date -> {
                            LogUtils.d("" + date.getTime());
                        })
                        .showTimePicker();
                break;
            case R.id.tv_pro:
                proCityUtil
                        .setProCityAreaParam(null, 0, null, null, null, 0, null, null, 0, null, null, null, null, null)
                        .setProCityAreaListener((province, city, district) -> {
                            toastNotifyShort(province.getName() + "," + city.getName() + "," + district.getName());
                        })
                        .showProCityPicker();

                break;
            case R.id.tv_cus:
                proCityUtil
                        .setCustomParam(0, 0, 0, false)
                        .setCustomDatas(ProCityUtil.LEVEL_3, data1, data2, data3)
                        .setCustomListener((options1, options2, options3) -> {
                            toastNotifyShort(data1.get(options1) + "," + data2.get(options2) + "," + data3.get(options3));
                        })
                        .showCustomBuilder();
                break;
        }
    }
}
