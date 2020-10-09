package com.hdyg.testcommon.mvp.view.activity;

import android.content.Intent;

import androidx.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.hdyg.common.util.dialog.JDialog;
import com.hdyg.common.util.dialog.JDialogType;
import com.hdyg.testcommon.BuildConfig;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.bean.LoginBean;
import com.hdyg.testcommon.bean.VersionBean;
import com.hdyg.testcommon.mvp.contract.CLogin;
import com.hdyg.testcommon.mvp.presenter.PLogin;
import com.hdyg.testcommon.util.versionUtil.AppDownloadManager;
import com.hdyg.common.common.AppManager;
import com.hdyg.common.common.BaseActivity;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.LangUtil.MultiLanguageType;
import com.hdyg.common.util.LangUtil.MultiLanguageUtil;
import com.hdyg.common.util.PopWindowUtil;
import com.hdyg.common.util.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.superLei.aoparms.annotation.SingleClick;

/**
 * @author CZB
 * @describe 登录页
 * @time 2019/5/9 17:59
 */
public class LoginActivity extends BaseActivity<PLogin> implements CLogin.IVLogin {

    private AppDownloadManager mDownLoadManage;
    private int versionCode;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTopTitle("功能列表");
        setLeftVisible(false);
        versionCode = BuildConfig.VERSION_CODE;
        mDownLoadManage = new AppDownloadManager(this);

    }

    @OnClick({R.id.tv_time,R.id.tv_cus_text,R.id.tv_tree,R.id.tv_dialog,R.id.tv_img,R.id.tv_map,
            R.id.tv_rsa})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.tv_time:  //选择器工具
                startActivity(ProcityTestActivity.class);
                break;
            case R.id.tv_cus_text:  //自定义文本收起/全文
                startActivity(ExpandableActivity.class);
                break;
            case R.id.tv_tree:  //三级列表
                startActivity(TreeAdapterActivity.class);
                break;
            case R.id.tv_dialog:    //dialog测试
                startActivity(DialogTestActivity.class);
                break;
            case R.id.tv_img:    //照片选择器
                startActivity(ImgTestActivity.class);
                break;
            case R.id.tv_map:    //跳转第三方地图(高德、百度、腾讯地图)
                startActivity(IntentMapActivity.class);
                break;
            case R.id.tv_rsa:    //RSA工具
                startActivity(RSATestActivity.class);
                break;
        }
    }

//    private void showPopWindow() {
//        View view = View.inflate(mContext, R.layout.pop_lang, null);
//
//        LinearLayout ll1 = view.findViewById(R.id.ll_en1);
//        LinearLayout ll2 = view.findViewById(R.id.ll_en2);
//
//        ll1.setOnClickListener(view1 -> {
//            need = MultiLanguageUtil.needUpdateLocale(mContext, MultiLanguageType.ZH);
//            transLanguage(need, MultiLanguageType.ZH);
//            mPopupWindow.dismiss();
//        });
//        ll2.setOnClickListener(view1 -> {
//            need = MultiLanguageUtil.needUpdateLocale(mContext, MultiLanguageType.EN);
//            transLanguage(need, MultiLanguageType.EN);
//            mPopupWindow.dismiss();
//        });
//
//        mPopupWindow = PopWindowUtil.getInstance().makePopupWindow(mContext, view, ViewGroup.LayoutParams.WRAP_CONTENT, true)
//                .showAsDropDown(mContext, llLanguage, 0);
//    }

    private void transLanguage(boolean need, MultiLanguageType type) {
        if (need) {
            //自己写的常用activity管理工具
            MultiLanguageUtil.updateLanguageEnviroment(mContext, type);
            AppManager.getAppManager().recreateAllOtherActivity(this);
            finish();
            startActivity(LoginActivity.class);
        }
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }


    @Override
    protected void initData() {
//        mPresenter.pGetVersion(RequestMethod.VERSION_URL, GetParamUtil.getVersionParam());
    }


    @Override
    protected void createPresenter() {
        mPresenter = new PLogin(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:
//                etAccount.setText(SPUtils.get(SpMsg.USERNAME, ""));
//                etPwd.setText(SPUtils.get(SpMsg.USERPASSWORD, ""));
                break;
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void vGetLoginSuccess(LoginBean dataBean) {
        SPUtils.put(SpMsg.TOKEN, dataBean.getToken());
//        SPUtils.put(SpMsg.USERNAME, username);
//        SPUtils.put(SpMsg.USERPASSWORD, passs);
        UIHelper.showMain(mContext);
        finish();
    }

}
