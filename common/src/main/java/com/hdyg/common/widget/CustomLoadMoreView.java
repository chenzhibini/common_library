package com.hdyg.common.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.hdyg.common.R;

/**
 * @author CZB
 * @describe 自定义的加载更多布局
 * @time 2019/4/23 15:37
 */
public class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        // 该布局文件中同时指定了三种情况下的视图样式
        return R.layout.view_load_more;
    }


    @Override
    protected int getLoadingViewId() {
        // 指定加载更多时的视图样式
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        // 指定加载更多失败时的视图样式
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        // 指定没有更多数据时的视图样式
        return R.id.load_more_no_data;
    }

}
