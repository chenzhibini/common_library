package com.hdyg.testcommon.mvp.contract;

import com.hdyg.common.common.IBasePresenter;
import com.hdyg.common.common.IBaseView;
import java.util.List;
import java.util.Map;

/**
 * @author CZB
 * @describe    启动页
 * @time 2019/4/10 11:51
 */

public interface CWelcome {

    interface IVWelcome extends IBaseView {

        void vGetAreaCodeSuccess(List<String> dataBeans);  //获取区域编码

    }
    interface IPWelcome extends IBasePresenter {
        void pGetAreaCode(String path, Map<String, String> params);
    }
}
