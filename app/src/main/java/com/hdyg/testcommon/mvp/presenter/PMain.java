package com.hdyg.testcommon.mvp.presenter;

import com.hdyg.testcommon.bean.VersionBean;
import com.hdyg.testcommon.mvp.contract.CMain;
import com.hdyg.common.bean.BaseBean;
import com.hdyg.common.common.BasePresenter;
import com.hdyg.common.httpUtil.HttpCallback;
import java.util.Map;

/**
 * @author CZB
 * @describe  首页MainActivity
 * @time 2019/8/16 16:04
 */

public class PMain extends BasePresenter<CMain.IVMain> implements CMain.IPMain {

    public PMain(CMain.IVMain mView) {
        super(mView);
    }

    @Override
    public void pGetVersion(String path, Map<String, String> params) {
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

}
