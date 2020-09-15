package com.hdyg.testcommon.mvp.view.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import com.hdyg.testcommon.BuildConfig;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.adapter.MainMenuAdapter;
import com.hdyg.testcommon.adapter.base.MyFragmentAdapter;
import com.hdyg.testcommon.bean.NativeGuideBean;
import com.hdyg.testcommon.bean.VersionBean;
import com.hdyg.testcommon.mvp.view.fragment.EmptyFragment;
import com.hdyg.testcommon.mvp.contract.CMain;
import com.hdyg.testcommon.mvp.presenter.PMain;
import com.hdyg.testcommon.util.DataCenter;
import com.hdyg.testcommon.util.versionUtil.AppDownloadManager;
import com.hdyg.common.common.AppManager;
import com.hdyg.common.common.BaseActivity;
import com.hdyg.common.common.BaseFragment;
import com.hdyg.common.widget.NoPreloadViewPager;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;


/**
 * @author CZB
 * @describe 首页
 * @time 2019/4/3 11:30
 */
public class MainActivity extends BaseActivity<PMain> implements CMain.IVMain {

    @BindView(R.id.view_page)
    NoPreloadViewPager viewPage;
    @BindView(R.id.rv_bottom)
    RecyclerView rvBottom;

    private List<NativeGuideBean> bottomDatas;
    private MainMenuAdapter bottomAdapter;

    private List<BaseFragment> pageDatas;
    private MyFragmentAdapter pageAdapter;
    private long mExitTime;
    private int versionCode;
    private AppDownloadManager mDownLoadManage;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        EventBus.getDefault().register(this);
        mDownLoadManage = new AppDownloadManager(this);
        versionCode = BuildConfig.VERSION_CODE;
        bottomDatas = DataCenter.getBottomData();
        rvBottom.setLayoutManager(new GridLayoutManager(mContext, bottomDatas.size()));
        initTabAdapter();
        initPage();
    }

    @Override
    protected void initData() {
//        mPresenter.pGetVersion(RequestMethod.VERSION_URL, GetParamUtil.getVersionParam());
    }

    @Override
    protected void createPresenter() {
//        mPresenter = new PMain(this);
    }

    //初始化底部
    private void initTabAdapter() {
        bottomAdapter = new MainMenuAdapter(R.layout.item_main_menu, bottomDatas);
        rvBottom.setAdapter(bottomAdapter);
        bottomAdapter.setOnItemClickListener((adapter, view, position) -> {
            changeBottomUI(position);
        });
    }

    private void changeBottomUI(int position) {
        for (int i = 0; i < bottomDatas.size(); i++) {
            if (i == position) {
                bottomDatas.get(i).setSelect(true);
            } else {
                bottomDatas.get(i).setSelect(false);
            }
        }
        bottomAdapter.replaceData(bottomDatas);
        bottomAdapter.notifyDataSetChanged();
        viewPage.setCurrentItem(position);
    }

    //初始化首页fragment
    private void initPage() {
        if (pageDatas == null) {
            pageDatas = new ArrayList<>();
        } else {
            pageDatas.clear();
        }
        pageDatas.add(new EmptyFragment());
        pageDatas.add(new EmptyFragment());
        pageDatas.add(new EmptyFragment());
        pageDatas.add(new EmptyFragment());
//        pageDatas.add(new MineFragment());

        pageAdapter = new MyFragmentAdapter(getSupportFragmentManager(), pageDatas);
        viewPage.setAdapter(pageAdapter);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void EventBusChangeTab(String pos){
//        changeBottomUI(Integer.valueOf(pos));
//    }

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

    @Override
    protected void onResume() {
        super.onResume();
        if (mDownLoadManage != null) {
            mDownLoadManage.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDownLoadManage != null) {
            mDownLoadManage.onPause();
        }
    }

    @Override
    public void vGetVersionSuccess(VersionBean dataBean) {
        try {
            if (versionCode < Integer.valueOf(dataBean.getVersion())) {
                mDownLoadManage.showNoticeDialog(dataBean.getUrl(), getResources().getString(R.string.sys_version_title2), dataBean.getNote());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
