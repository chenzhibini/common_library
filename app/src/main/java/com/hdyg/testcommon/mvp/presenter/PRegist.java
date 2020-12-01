package com.hdyg.testcommon.mvp.presenter;

import com.hdyg.testcommon.app.BaseBean;
import com.hdyg.testcommon.app.HttpCallback;
import com.hdyg.testcommon.mvp.contract.CRegist;
import com.hdyg.common.common.BasePresenter;
import java.util.List;
import java.util.Map;

/**
 * @author CZB
 * @describe  注册/忘记密码
 * @time 2019/5/15 11:51
 */

public class PRegist extends BasePresenter<CRegist.IVRegist> implements CRegist.IPRegist {

    public PRegist(CRegist.IVRegist mView) {
        super(mView);
    }

    @Override
    public void pGetSendCode(String path, Map<String, String> params) {
        mView.showLoading();
        mModel.post(path, params, new HttpCallback<BaseBean>() {
            @Override
            public void onSuccess(BaseBean dataBean) {
                mView.hideLoading();
                if (isViewAttached()) mView.vGetSendCodeSuccess(dataBean.getMessage());
            }

            @Override
            public void onFail(String code, String error) {
                mView.hideLoading();
                mView.onError(code, error);
            }
        });
    }

    @Override
    public void pGetSubmit(String path, Map<String, String> params) {
        mView.showLoading();
        mModel.post(path, params, new HttpCallback<BaseBean>() {
            @Override
            public void onSuccess(BaseBean dataBean) {
                mView.hideLoading();
                if (isViewAttached()) mView.vGetSubmitSuccess(dataBean.getMessage());
            }

            @Override
            public void onFail(String code, String error) {
                mView.hideLoading();
                mView.onError(code, error);
            }
        });
    }

    @Override
    public void pGetAreaCode(String path, Map<String, String> params) {
        mView.showLoading();
        mModel.post(path, params, new HttpCallback<BaseBean<List<String>>>() {
            @Override
            public void onSuccess(BaseBean<List<String>> dataBean) {
                mView.hideLoading();
                if (isViewAttached()) mView.vGetAreaCodeSuccess(dataBean.getData());
            }

            @Override
            public void onFail(String code, String error) {
                mView.hideLoading();
                mView.onError(code, error);
            }
        });
    }

}
