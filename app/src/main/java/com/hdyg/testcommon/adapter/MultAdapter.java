package com.hdyg.testcommon.adapter;

import androidx.annotation.Nullable;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.bean.MultBean;

import java.util.List;


/**
 * @author CZB
 * @describe 需求市场
 * @time 2021/1/18 14:22
 */
public class MultAdapter extends BaseQuickAdapter<MultBean, BaseViewHolder> {

    public MultAdapter(@Nullable List<MultBean> data) {
        super(data);
        // 第一步：动态判断
        setMultiTypeDelegate(new MultiTypeDelegate<MultBean>() {
            @Override
            protected int getItemType(MultBean entity) {
                //根据你的实体类来判断布局类型
                return entity.type;

            }
        });

        // 第二步：设置type和layout的对应关系
        getMultiTypeDelegate()
                .registerItemType(MultBean.TITLE, R.layout.item_take_photo)
                .registerItemType(MultBean.CONTENT, R.layout.item_expandable_lv0);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultBean item) {

        // 第三步：设置不同布局下的组件数据
        switch (helper.getItemViewType()) {
            case MultBean.TITLE:

                break;
            case MultBean.CONTENT:

                break;
        }
    }
}
