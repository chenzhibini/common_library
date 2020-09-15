package com.hdyg.testcommon.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hdyg.testcommon.R;

import java.util.List;


/**
 * @author CZB
 * @describe 区号适配器
 * @time 2020/3/16 14:22
 */
public class AreaCodeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AreaCodeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_area,item);
    }
}
