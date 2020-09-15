package com.hdyg.testcommon.mvp.presenter;

import com.hdyg.testcommon.bean.LoginBean;
import com.hdyg.testcommon.bean.VersionBean;
import com.hdyg.testcommon.mvp.contract.CLogin;
import com.hdyg.common.bean.BaseBean;
import com.hdyg.common.common.BasePresenter;
import com.hdyg.common.httpUtil.HttpCallback;
import java.util.Map;

/**
 * @author CZB
 * @describe    登录
 * @time 2019/5/15 11:51
 */

public class PLogin extends BasePresenter<CLogin.IVLogin> implements CLogin.IPLogin {


    public PLogin(CLogin.IVLogin mView) {
        super(mView);
    }

    @Override
    public void pGetVersion(String path, Map<String, String> params) {
        mView.showLoading();
        mModel.post(path, params, new HttpCallback<BaseBean<VersionBean>>() {
            @Override
            public void onSuccess(BaseBean<VersionBean> dataBean) {
                mView.hideLoading();
                if (isViewAttached()) mView.vGetVersionSuccess(dataBean.getData());
            }

            @Override
            public void onFail(String code, String error) {
                mView.hideLoading();
                mView.onError(code, error);
            }
        });
    }

    @Override
    public void pGetLogin(String path, Map<String, String> params) {
        mView.showLoading();
        mModel.post(path, params, new HttpCallback<BaseBean<LoginBean>>() {
            @Override
            public void onSuccess(BaseBean<LoginBean> dataBean) {
                mView.hideLoading();
                if (isViewAttached()) mView.vGetLoginSuccess(dataBean.getData());
            }

            @Override
            public void onFail(String code, String error) {
                mView.hideLoading();
                mView.onError(code, error);
            }
        });
    }
}
