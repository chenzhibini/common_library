package com.hdyg.testcommon.mvp.presenter;

import com.hdyg.testcommon.mvp.contract.CWelcome;
import com.hdyg.common.bean.BaseBean;
import com.hdyg.common.common.BasePresenter;
import com.hdyg.common.httpUtil.HttpCallback;
import java.util.List;
import java.util.Map;

/**
 * @author CZB
 * @describe    启动页
 * @time 2019/4/10 11:51
 */

public class PWelcome extends BasePresenter<CWelcome.IVWelcome> implements CWelcome.IPWelcome {

    public PWelcome(CWelcome.IVWelcome mView) {
        super(mView);
    }

    @Override
    public void pGetAreaCode(String path, Map<String, String> params) {
        mModel.post(path, params, new HttpCallback<BaseBean<List<String>>>() {
            @Override
            public void onSuccess(BaseBean<List<String>> dataBean) {
                if (isViewAttached()) mView.vGetAreaCodeSuccess(dataBean.getData());
            }

            @Override
            public void onFail(String code, String error) {
                mView.onError(code, error);
            }
        });
    }
}
