package com.hdyg.testcommon.mvp.view.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.adapter.MultAdapter;
import com.hdyg.testcommon.bean.MultBean;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * @author CZB
 * @describe 多布局
 * @time 2021/1/19 16:10
 */
public class MultActivity extends BaseActivity {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private MultAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        setTopTitle("adapter多布局");
        rvMain.setLayoutManager(new GridLayoutManager(mContext,4));
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    @Override
    protected void createPresenter() {

    }

    private void initAdapter(){
        List<MultBean> datas = new ArrayList<>();
        datas.add(new MultBean(MultBean.TITLE));
        datas.add(new MultBean(MultBean.TITLE));
        datas.add(new MultBean(MultBean.TITLE));
        datas.add(new MultBean(MultBean.CONTENT));
        datas.add(new MultBean(MultBean.CONTENT));
        datas.add(new MultBean(MultBean.CONTENT));
        datas.add(new MultBean(MultBean.CONTENT));
        mAdapter = new MultAdapter(datas);
        mAdapter.setSpanSizeLookup((gridLayoutManager, i) -> {
            if (datas.get(i).type == MultBean.TITLE) {
                return 1;
            } else {
                return 3;
            }
        });
        rvMain.setAdapter(mAdapter);
    }

}
