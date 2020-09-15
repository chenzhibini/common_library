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

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.sv_regist_forget)
    SuperTextView svRegistForget;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.ll_language)
    LinearLayout llLanguage;
    @BindView(R.id.tv_language)
    TextView tvLanguage;

    private AppDownloadManager mDownLoadManage;
    private int versionCode;
    private String username, passs;
    private boolean need;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        etAccount.setText(SPUtils.get(SpMsg.USERNAME, ""));
        etPwd.setText(SPUtils.get(SpMsg.USERPASSWORD, ""));
        onClickListener();
        versionCode = BuildConfig.VERSION_CODE;
        mDownLoadManage = new AppDownloadManager(this);

    }

    @OnClick({R.id.bt_login,R.id.ll_language})
    @SingleClick(R.id.bt_login)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.ll_language:
                showPopWindow();
                break;
            case R.id.bt_login:
                username = etAccount.getText().toString().trim();
                passs = etPwd.getText().toString().trim();
//            if (TextUtils.isEmpty(username)) {
//                toastNotifyShort(R.string.login_phone_hint);
//                return;
//            }
//            if (TextUtils.isEmpty(passs)) {
//                toastNotifyShort(R.string.login_pwd_hint);
//                return;
//            }
//            if (StringUtil.isFastDoubleClick()) {
//                return;
//            }
//            mPresenter.pGetLogin(RequestMethod.LOGIN_URL, GetParamUtil.getLoginParam(username, passs));
//                ToastUtil.show("自定义提示框");
//                new JDialog.Builder(mContext, JDialogType.CHOOSE)
//                        .build().show();
//                TakePhotoUtil.getInstance().selectPhotoCropSingle(mContext,obj -> {});
//            UIHelper.showRegistForgetPwd(mContext,RegistForGetPwdActivity.REGIST_CODE,REQUEST_CODE);
                startActivity(ExpandableActivity.class);
//            UIHelper.showMain(mContext);
//                TakePhotoUtil.getInstance().selectPhotoMult(mContext,list -> {});
                break;
        }
    }

    private void onClickListener() {
        //立即注册/忘记密码
        svRegistForget
                .setLeftTvClickListener(() -> UIHelper.showRegistForgetPwd(mContext, RegistForGetPwdActivity.REGIST_CODE, REQUEST_CODE))
                .setRightTvClickListener(() -> UIHelper.showRegistForgetPwd(mContext, RegistForGetPwdActivity.FORGET_LOGINPWD_CODE, REQUEST_CODE));
    }

    private void showPopWindow() {
        View view = View.inflate(mContext, R.layout.pop_lang, null);

        LinearLayout ll1 = view.findViewById(R.id.ll_en1);
        LinearLayout ll2 = view.findViewById(R.id.ll_en2);

        ll1.setOnClickListener(view1 -> {
            need = MultiLanguageUtil.needUpdateLocale(mContext, MultiLanguageType.ZH);
            transLanguage(need, MultiLanguageType.ZH);
            mPopupWindow.dismiss();
        });
        ll2.setOnClickListener(view1 -> {
            need = MultiLanguageUtil.needUpdateLocale(mContext, MultiLanguageType.EN);
            transLanguage(need, MultiLanguageType.EN);
            mPopupWindow.dismiss();
        });

        mPopupWindow = PopWindowUtil.getInstance().makePopupWindow(mContext, view, ViewGroup.LayoutParams.WRAP_CONTENT, true)
                .showAsDropDown(mContext, llLanguage, 0);
    }

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
                etAccount.setText(SPUtils.get(SpMsg.USERNAME, ""));
                etPwd.setText(SPUtils.get(SpMsg.USERPASSWORD, ""));
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
        SPUtils.put(SpMsg.USERNAME, username);
        SPUtils.put(SpMsg.USERPASSWORD, passs);
        UIHelper.showMain(mContext);
        finish();
    }

}
