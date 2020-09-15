package com.hdyg.testcommon.mvp.contract;

import com.hdyg.common.common.IBasePresenter;
import com.hdyg.common.common.IBaseView;
import java.util.List;
import java.util.Map;

/**
 * @author CZB
 * @describe    注册/忘记密码
 * @time 2019/5/15 11:51
 */

public interface CRegist {

    interface IVRegist extends IBaseView {
        void vGetSendCodeSuccess(String dataBean);  //发送验证码
        void vGetSubmitSuccess(String dataBean);    //注册/忘记登录密码
        void vGetAreaCodeSuccess(List<String> dataBean);    //获取区号

    }
    interface IPRegist extends IBasePresenter {
        void pGetSendCode(String path, Map<String, String> params);
        void pGetSubmit(String path, Map<String, String> params);
        void pGetAreaCode(String path, Map<String, String> params);
    }
}
