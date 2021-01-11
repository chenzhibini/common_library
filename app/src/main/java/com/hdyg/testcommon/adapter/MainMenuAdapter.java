package com.hdyg.testcommon.adapter;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hdyg.common.util.GlideUtil;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.bean.NativeGuideBean;

import java.util.List;


/**
 * @author CZB
 * @describe 首页菜单适配器
 * @time 2019/3/20 14:22
 */
public class MainMenuAdapter extends BaseQuickAdapter<NativeGuideBean, BaseViewHolder> {

    public MainMenuAdapter(int layoutResId, @Nullable List<NativeGuideBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NativeGuideBean item) {
        if (item.isSelect()){
            helper.setTextColor(R.id.tv_tab,mContext.getResources().getColor(R.color.main_line));
            GlideUtil.loadImage(mContext,item.getSelectImg(),helper.getView(R.id.iv_tab));
        }else {
            helper.setTextColor(R.id.tv_tab,mContext.getResources().getColor(R.color.gray_80));
            GlideUtil.loadImage(mContext,item.getUnSelectImg(),helper.getView(R.id.iv_tab));
        }
        helper.setText(R.id.tv_tab,item.getText());
    }
}
