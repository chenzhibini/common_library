package com.hdyg.testcommon.mvp.contract;

import com.hdyg.testcommon.bean.LoginBean;
import com.hdyg.testcommon.bean.VersionBean;
import com.hdyg.common.common.IBasePresenter;
import com.hdyg.common.common.IBaseView;
import java.util.Map;

/**
 * @author CZB
 * @describe    登录
 * @time 2019/5/15 11:51
 */

public interface CLogin {

    interface IVLogin extends IBaseView {
        void vGetVersionSuccess(VersionBean dataBean);   //获取版本号
        void vGetLoginSuccess(LoginBean dataBean);        //登录

    }
    interface IPLogin extends IBasePresenter {
        void pGetVersion(String path, Map<String, String> params);
        void pGetLogin(String path, Map<String, String> params);
    }
}
