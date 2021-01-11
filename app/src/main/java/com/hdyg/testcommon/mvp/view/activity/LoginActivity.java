package com.hdyg.testcommon.mvp.view.activity;

import android.content.Intent;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import com.hdyg.common.util.ShareFileUtils;
import com.hdyg.testcommon.BuildConfig;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.bean.LoginBean;
import com.hdyg.testcommon.bean.VersionBean;
import com.hdyg.testcommon.mvp.contract.CLogin;
import com.hdyg.testcommon.mvp.presenter.PLogin;
import com.hdyg.testcommon.util.versionUtil.AppDownloadManager;
import com.hdyg.common.common.AppManager;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.LangUtil.MultiLanguageType;
import com.hdyg.common.util.LangUtil.MultiLanguageUtil;
import com.hdyg.common.util.SPUtils;
import butterknife.OnClick;

/**
 * @author CZB
 * @describe 登录页
 * @time 2019/5/9 17:59
 */
public class LoginActivity extends BaseActivity<PLogin> implements CLogin.IVLogin {

    private AppDownloadManager mDownLoadManage;
    private int versionCode;
    private EditText etAddress;



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
        etAddress = findViewById(R.id.et_address);

    }

    @OnClick({R.id.tv_time,R.id.tv_cus_text,R.id.tv_tree,R.id.tv_dialog,R.id.tv_img,R.id.tv_map,
            R.id.tv_rsa,R.id.tv_game})
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
            case R.id.tv_game:    //跳转王者荣耀

                String address = etAddress.getText().toString().trim();
//                ShareFileUtils.shareUrl(mContext,"https://game.weixin.qq.com/cgi-bin/h5/static/gamecenter/gamelauncher.html?isFromWeappEntry=1&ssid=29&appid=wx95a3a4d7c627e07d&message=ShareTeam_1983707871_3272_2302500565_336331268_6915326844899748_5_1_1_1_20011_2_8_1_5_0_0_0_0_0_0&join=1#wechat_redirect");
//                ShareFileUtils.shareUrl(mContext,"https://game.weixin.qq.com/cgi-bin/h5/static/gamecenter/gamelauncher.html?isFromWeappEntry=1&ssid=29&appid=wx95a3a4d7c627e07d&message=ShareTeam_1983707871_3120_2303497006_336331268_6915326844899748_5_1_1_1_20011_2_8_1_5_0_0_0_0_0_0&join=1#wechat_redirect");
//                ShareFileUtils.shareUrl(mContext,"https://game.weixin.qq.com/cgi-bin/h5/static/gamecenter/gamelauncher.html?isFromWeappEntry=1&ssid=29&appid=wx95a3a4d7c627e07d&message=ShareTeam_1983707871_3077_2303613379_336331268_6915326844899748_5_1_1_1_20011_2_8_1_5_0_0_0_0_0_0&join=1#wechat_redirect");
                ShareFileUtils.shareUrl(mContext,address);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                mContext.startActivity(intent);
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
