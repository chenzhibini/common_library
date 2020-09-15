//package com.hdyg.common.widget.contact;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.TextView;
//
//import com.hdyg.basedemo.R;
//import com.hdyg.basedemo.activity.common.BaseActivity;
//import com.hdyg.basedemo.util.ToastUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
///**
// * @author CZB
// * @describe
// * @time 2020/7/15 9:57
// */
//public class ContactActivity extends BaseActivity {
//    @BindView(R.id.rv_contact)
//    RecyclerView rvContact;
//    @BindView(R.id.indexBar)
//    IndexBar mIndexBar;
//    @BindView(R.id.tvSideBarHint)
//    TextView mTvSideBarHint;
//
//    private ContactAdapter mAdapter;
//    private List<CityBean> mDatas;
//
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.contact_activity_contact;
//    }
//
//    @Override
//    protected void initView() {
//        setTopTitle("测试通讯录");
//        rvContact.setLayoutManager(new LinearLayoutManager(mContext));
//    }
//
//    @Override
//    protected void initData() {
//        getData();
//    }
//
//    @Override
//    protected void createPresenter() {
//
//    }
//
//    private void getData() {
//        mDatas = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            CityBean cityBean = new CityBean();
//            if (i < 5) {
//                cityBean.setCity("连扳" + i);//设置城市名称
//            } else if (i < 7) {
//                cityBean.setCity("白城" + i);
//            } else if (i < 10) {
//                cityBean.setCity("厦门" + i);
//            } else if (i < 15) {
//                cityBean.setCity("漳州" + i);
//            } else {
//                cityBean.setCity("AAAAA" + i);
//            }
//            mDatas.add(cityBean);
//        }
//        initAdapter();
//    }
//
//    private void initAdapter() {
//        mAdapter = new ContactAdapter(R.layout.contact_item_contact, mDatas);
//        rvContact.setAdapter(mAdapter);
//        rvContact.addItemDecoration(new TitleItemDecoration(this, mDatas));
//        //如果add两个，那么按照先后顺序，依次渲染。
//        rvContact.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
//        //使用indexBar
//        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
//                .setNeedRealIndex(false)//设置需要真实的索引
//                .setmLayoutManager(new LinearLayoutManager(mContext))//设置RecyclerView的LayoutManager
//                .setmSourceDatas(mDatas);//设置数据源
//
//        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            ToastUtil.show("点击了" + mAdapter.getItem(position).getCity());
//        });
//    }
//
//}
