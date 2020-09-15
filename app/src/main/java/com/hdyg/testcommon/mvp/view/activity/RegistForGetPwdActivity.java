package com.hdyg.testcommon.mvp.view.activity;

import android.graphics.Paint;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.allen.library.SuperTextView;
import com.hdyg.common.util.LogUtils;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.adapter.AreaCodeAdapter;
import com.hdyg.testcommon.mvp.contract.CRegist;
import com.hdyg.testcommon.mvp.presenter.PRegist;
import com.hdyg.common.common.AppManager;
import com.hdyg.common.common.BaseActivity;
import com.hdyg.common.common.SpMsg;
import com.hdyg.common.util.CountDownTimerUtil;
import com.hdyg.common.util.PopWindowUtil;
import com.hdyg.common.util.SPUtils;
import java.util.List;
import butterknife.BindView;

/**
 * @author CZB
 * @describe 注册/忘记密码
 * @time 2019/10/29 11:12
 */
public class RegistForGetPwdActivity extends BaseActivity<PRegist> implements CRegist.IVRegist {

    public static final int REGIST_CODE = 0x0001;               //注册
    public static final int FORGET_LOGINPWD_CODE = 0x0002;      //忘记登录密码
    public static final int UPDATE_TRADPWD_CODE = 0x0003;       //修改交易密码
    public static final int UPDATE_LOGINPWD_CODE = 0x0004;      //修改登录密码

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.ll_login_pwd)
    LinearLayout llLoginPwd;
    @BindView(R.id.et_re_pwd)
    EditText etRePwd;
    @BindView(R.id.ll_re_login_pwd)
    LinearLayout llReLoginPwd;
    @BindView(R.id.et_invite)
    EditText etInvite;
    @BindView(R.id.ll_invite)
    LinearLayout llInvite;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.et_trad_pwd)
    EditText etTradPwd;
    @BindView(R.id.ll_trad_pwd)
    LinearLayout llTradPwd;
    @BindView(R.id.et_re_trad_pwd)
    EditText etReTradPwd;
    @BindView(R.id.ll_re_trad_pwd)
    LinearLayout llReTradPwd;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_nick)
    LinearLayout llNick;
    @BindView(R.id.et_nick)
    EditText etNick;
    @BindView(R.id.sv_quhao)
    SuperTextView svQuhao;


    private int intentType;
    private String username, passs, repasss, trpasss, retrpasss, invite_code, code, truename, areaCode = "86";
    private List<String> areaCodeDatas;
    private String codeType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist_forgetpwd;
    }

    @Override
    protected void initView() {
        tvSend.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  //画下划线
        tvLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  //画下划线
        intentType = getIntent().getIntExtra(SpMsg.INTENT_CODE, REGIST_CODE);
        LogUtils.d("接收到的intentType===>"+intentType);
        changeUI();
        onClickListener();
    }

    private void onClickListener() {
        tvLogin.setOnClickListener(view -> finish());
        svQuhao.setOnSuperTextViewClickListener(superTextView -> showAreaPopWindow());
        //发送短信
        tvSend.setOnClickListener(view -> {
            if (intentType == REGIST_CODE || intentType == FORGET_LOGINPWD_CODE) {
                username = etAccount.getText().toString().trim();   //手机号
            } else {
                username = SPUtils.get(SpMsg.USERNAME, "");
            }

            if (TextUtils.isEmpty(areaCode)) {
                toastNotifyShort(R.string.regist_area_code);
                return;
            }
            if (TextUtils.isEmpty(username)) {
                toastNotifyShort(R.string.login_phone_hint);
                return;
            }
//            mPresenter.pGetSendCode(RequestMethod.SEND_CODE_URL, GetParamUtil.getSendCodeParam(username, codeType, areaCode));
        });
        //提交
        btSure.setOnClickListener(view -> {
            truename = etNick.getText().toString().trim();      //昵称
            username = etAccount.getText().toString().trim();   //手机号
            passs = etPwd.getText().toString().trim();          //登录密码
            repasss = etRePwd.getText().toString().trim();      //确认登录密码
            trpasss = etTradPwd.getText().toString().trim();    //交易密码
            retrpasss = etReTradPwd.getText().toString().trim();//确认交易密码
            code = etCode.getText().toString().trim();          //验证码
            invite_code = etInvite.getText().toString().trim(); //邀请码
            registForgetPwdMethod();

        });
    }

    private void changeUI() {
        switch (intentType) {
            case REGIST_CODE:   //注册
                setTopTitle(R.string.regist_title);
                btSure.setText(R.string.regist_title);
                codeType = "1";
                break;
            case FORGET_LOGINPWD_CODE:   //忘记登录密码
                setTopTitle(R.string.forget_pwd_title);
                btSure.setText(R.string.forget_sure);
                llNick.setVisibility(View.GONE);
                llTradPwd.setVisibility(View.GONE);
                llReTradPwd.setVisibility(View.GONE);
                llInvite.setVisibility(View.GONE);
                tvLogin.setVisibility(View.GONE);
                codeType = "2";
                break;
            case UPDATE_TRADPWD_CODE:   //修改交易密码
                setTopTitle(R.string.update_tradpwd_title);
                btSure.setText(R.string.forget_sure);
                llNick.setVisibility(View.GONE);
                llLoginPwd.setVisibility(View.GONE);
                llReLoginPwd.setVisibility(View.GONE);
                llInvite.setVisibility(View.GONE);
                tvLogin.setVisibility(View.GONE);
                llPhone.setVisibility(View.GONE);
                codeType = "3";
                break;
            case UPDATE_LOGINPWD_CODE:   //修改登录密码
                setTopTitle(R.string.update_loginpwd_title);
                btSure.setText(R.string.forget_sure);
                llNick.setVisibility(View.GONE);
                llTradPwd.setVisibility(View.GONE);
                llReTradPwd.setVisibility(View.GONE);
                llInvite.setVisibility(View.GONE);
                tvLogin.setVisibility(View.GONE);
                llPhone.setVisibility(View.GONE);
                codeType = "3";
                break;
        }
    }

    private void registForgetPwdMethod() {
        switch (intentType) {
            case REGIST_CODE:
                if (TextUtils.isEmpty(truename)) {
                    toastNotifyShort(R.string.regist_nick_hint);
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    toastNotifyShort(R.string.login_phone_hint);
                    return;
                }
                if (TextUtils.isEmpty(passs)) {
                    toastNotifyShort(R.string.login_pwd_hint);
                    return;
                }
                if (!repasss.equals(passs)) {
                    toastNotifyShort(R.string.toast_login_pwd);
                    return;
                }
                if (TextUtils.isEmpty(trpasss)) {
                    toastNotifyShort(R.string.regist_trad_pwd_hint);
                    return;
                }
                if (!trpasss.equals(retrpasss)) {
                    toastNotifyShort(R.string.toast_trad_pwd);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    toastNotifyShort(R.string.regist_code_hint);
                    return;
                }
                if (TextUtils.isEmpty(invite_code)) {
                    toastNotifyShort(R.string.regist_invite_hint);
                    return;
                }
//                mPresenter.pGetSubmit(RequestMethod.REGIST_URL, GetParamUtil.getRegistParam(username, passs, trpasss, code, invite_code, truename, areaCode));
                break;
            case FORGET_LOGINPWD_CODE:
                if (TextUtils.isEmpty(username)) {
                    toastNotifyShort(R.string.login_phone_hint);
                    return;
                }
                if (TextUtils.isEmpty(passs)) {
                    toastNotifyShort(R.string.login_pwd_hint);
                    return;
                }
                if (!repasss.equals(passs)) {
                    toastNotifyShort(R.string.toast_login_pwd);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    toastNotifyShort(R.string.regist_code_hint);
                    return;
                }
//                mPresenter.pGetSubmit(RequestMethod.FORGET_PWD_URL, GetParamUtil.getForgetPwdParam(username, passs, repasss, code, intentType));
                break;
            case UPDATE_TRADPWD_CODE:
                if (TextUtils.isEmpty(trpasss)) {
                    toastNotifyShort(R.string.regist_trad_pwd_hint);
                    return;
                }
                if (!trpasss.equals(retrpasss)) {
                    toastNotifyShort(R.string.toast_trad_pwd);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    toastNotifyShort(R.string.regist_code_hint);
                    return;
                }
//                mPresenter.pGetSubmit(RequestMethod.UPDATE_PWD_URL, GetParamUtil.getForgetPwdParam("", trpasss, retrpasss, code, intentType));
                break;
            case UPDATE_LOGINPWD_CODE:
                if (TextUtils.isEmpty(passs)) {
                    toastNotifyShort(R.string.login_pwd_hint);
                    return;
                }
                if (!repasss.equals(passs)) {
                    toastNotifyShort(R.string.toast_login_pwd);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    toastNotifyShort(R.string.regist_code_hint);
                    return;
                }
//                mPresenter.pGetSubmit(RequestMethod.UPDATE_PWD_URL, GetParamUtil.getForgetPwdParam("", passs, repasss, code, intentType));
                break;
        }

    }


    @Override
    protected void initData() {
        if (intentType == REGIST_CODE || intentType == FORGET_LOGINPWD_CODE) {
//            mPresenter.pGetAreaCode(RequestMethod.AREA_URL, GetParamUtil.getAreaCodeParam());
        }
    }

    @Override
    protected void createPresenter() {
        mPresenter = new PRegist(this);
    }

    private void showAreaPopWindow() {
        View view = View.inflate(mContext, R.layout.pop_area_code, null);

        RecyclerView rvArea = view.findViewById(R.id.rv_area);
        rvArea.setLayoutManager(new LinearLayoutManager(mContext));
        AreaCodeAdapter adapter = new AreaCodeAdapter(R.layout.item_area_code, areaCodeDatas);
        rvArea.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            areaCode = adapter.getItem(position);
            svQuhao.setCenterString("+" + areaCode);
            mPopupWindow.dismiss();
        });
        mPopupWindow = PopWindowUtil.getInstance().makePopupWindow(mContext, view, ViewGroup.LayoutParams.MATCH_PARENT, true)
                .showAsDropDown(mContext, llPhone, 0);
    }

    @Override
    public void vGetSendCodeSuccess(String dataBean) {
        toastNotifyShort(dataBean);
        CountDownTimerUtil.getInstance().startTimer(tvSend, 60);
    }

    @Override
    public void vGetSubmitSuccess(String dataBean) {
        toastNotifyShort(dataBean);
        switch (intentType) {
            case REGIST_CODE://注册
                SPUtils.put(SpMsg.USERNAME, username);
                SPUtils.put(SpMsg.USERPASSWORD, passs);
                setResult(RESULT_OK);
                break;
            case FORGET_LOGINPWD_CODE://忘记登录密码
                SPUtils.put(SpMsg.USERNAME, username);
                SPUtils.put(SpMsg.USERPASSWORD, passs);
                AppManager.getAppManager().finishAllActivity();
                UIHelper.showLogin(mContext);
                break;
            case UPDATE_TRADPWD_CODE://修改交易密码
                break;
            case UPDATE_LOGINPWD_CODE://修改登录密码
                SPUtils.put(SpMsg.USERPASSWORD, passs);
                AppManager.getAppManager().finishAllActivity();
                UIHelper.showLogin(mContext);
                break;
        }
        finish();
    }

    @Override
    public void vGetAreaCodeSuccess(List<String> dataBean) {
        areaCodeDatas = dataBean;
    }

}
