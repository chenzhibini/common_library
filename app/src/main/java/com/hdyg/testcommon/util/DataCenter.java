package com.hdyg.testcommon.util;

import com.hdyg.testcommon.R;
import com.hdyg.testcommon.bean.NativeGuideBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CZB
 * @describe 静态数据类
 * @time 2019/4/3 11:26
 */
public class DataCenter {

    /**
     * 获取底部菜单栏
     *
     * @return list
     */
    public static List<NativeGuideBean> getBottomData() {
        List<NativeGuideBean> list = new ArrayList<>();
        list.add(new NativeGuideBean(R.mipmap.tab_1, R.mipmap.tab_1_gray, R.string.tab_1, true));
        list.add(new NativeGuideBean(R.mipmap.tab_2, R.mipmap.tab_2_gray, R.string.tab_2, false));
        list.add(new NativeGuideBean(R.mipmap.tab_3, R.mipmap.tab_3_gray, R.string.tab_3, false));
        list.add(new NativeGuideBean(R.mipmap.tab_4, R.mipmap.tab_4_gray, R.string.tab_4, false));
        return list;
    }



}
