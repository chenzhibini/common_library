package com.hdyg.testcommon.mvp.view.activity;

import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import com.hdyg.common.util.ToastUtil;
import com.hdyg.common.util.rsa.Base64Utils;
import com.hdyg.common.util.rsa.RSA;
import com.hdyg.testcommon.R;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CZB
 * @describe RSA加密工具使用
 * @time 2020/10/9 9:30
 */
public class RSATestActivity extends BaseActivity {

    @BindView(R.id.tv_txt)
    TextView tvTxt;

    private Map<String, String> params = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_rsa;
    }

    @Override
    protected void initView() {
        setTopTitle("RSA工具");
        params.put("aaa", "aaaaaaaa");
        params.put("bbb", "bbbbbbbb");
        params.put("ccc", "cccccccc");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void createPresenter() {

    }

    byte[] aaa;
    String sign;
    @OnClick({R.id.bt_jiami, R.id.bt_jiemi})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_jiami:
                aaa = RSA.encryptByPrivateKey(params, RSA.PRI_KEY);
                String encodeStr = Base64Utils.encode(aaa);
                sign = RSA.sign(params);
                tvTxt.setText("加密串--->" + encodeStr + "\nsign--->" + sign);
                break;
            case R.id.bt_jiemi:
                if (aaa == null){
                    ToastUtil.show("先加密");
                    return;
                }
                byte[] bbb = RSA.decryptByPublicKey(aaa,RSA.PUB_KEY);
                String s = new String(bbb);
                byte[] data = RSA.getParam(params);
                boolean verify = RSA.verify(data,Base64.decode(sign, Base64.DEFAULT));
                tvTxt.setText("解密串--->"+s+"\n验签是否成功--->"+verify);
                break;
        }
    }

}
