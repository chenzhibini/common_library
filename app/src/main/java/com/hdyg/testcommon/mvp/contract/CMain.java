package com.hdyg.testcommon.mvp.contract;

import com.hdyg.testcommon.bean.VersionBean;
import com.hdyg.common.common.IBasePresenter;
import com.hdyg.common.common.IBaseView;
import java.util.Map;

/**
 * @author CZB
 * @describe  首页MainActivity
 * @time 2019/8/16 16:04
 */

public interface CMain {

    interface IVMain extends IBaseView {
        void vGetVersionSuccess(VersionBean dataBean);   //版本更新

    }
    interface IPMain extends IBasePresenter {
        void pGetVersion(String path, Map<String, String> params);
    }
}
