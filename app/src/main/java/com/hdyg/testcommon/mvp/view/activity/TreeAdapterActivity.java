package com.hdyg.testcommon.mvp.view.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.adapter.ExpandableItemAdapter;
import com.hdyg.testcommon.bean.IteamOneBean;
import com.hdyg.testcommon.bean.IteamThreeBean;
import com.hdyg.testcommon.bean.IteamTwoBean;
import com.hdyg.common.common.BaseActivity;
import com.hdyg.common.util.LogUtils;
import java.util.ArrayList;
import java.util.Random;
import butterknife.BindView;

/**
 * @author CZB
 * @describe 三级列表  测试
 * @time 2020/4/1 20:43
 */
public class TreeAdapterActivity extends BaseActivity {

    @BindView(R.id.ll_top_left)
    LinearLayout llTopLeft;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private ExpandableItemAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        tvTopTitle.setText("测试三级列表");
        llTopLeft.setOnClickListener(v -> finish());
        rvMain.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    @Override
    protected void createPresenter() {

    }

    private ArrayList getData(){
        int lv0Count = 9;
        int lv1Count = 3;
        int personCount = 5;

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();
        ArrayList res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            IteamOneBean lv0 = new IteamOneBean("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                IteamTwoBean lv1 = new IteamTwoBean("Level 1 item: " + j, "(no animation)");
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new IteamThreeBean(nameList[k], random.nextInt(40)+""));
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

    private void initAdapter(){
        LogUtils.d(getData().size());
        mAdapter = new ExpandableItemAdapter(getData());
        rvMain.setAdapter(mAdapter);
    }
}
