package com.hdyg.testcommon.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hdyg.testcommon.R;
import com.hdyg.testcommon.bean.IteamOneBean;
import com.hdyg.testcommon.bean.IteamThreeBean;
import com.hdyg.testcommon.bean.IteamTwoBean;
import com.hdyg.common.util.LogUtils;

import java.util.List;

/**
 * @author CZB
 * @describe 折叠适配器  三级
 * @time 2020/4/1 20:52
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with * some initialization data. * * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv0);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv0);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        LogUtils.d("类型==>"+holder.getItemViewType());
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final IteamOneBean lv0 = (IteamOneBean) item;
//                LogUtils.d("json==>"+new Gson().toJson(lv0));
                holder.setText(R.id.tv_left, lv0.getTitle());
                holder.setBackgroundRes(R.id.ll_line, R.color.liji_material_red_500);
//                LogUtils.d("111111111111111");
//                switch (holder.getLayoutPosition() % 3) {
//                    case 0:
//                        holder.setBackgroundRes(R.id.ll_line, R.color.liji_material_red_500);
//                        break;
//                    case 1:
//                        holder.setBackgroundRes(R.id.ll_line, R.color.black);
//                        break;
//                    case 2:
//                        holder.setBackgroundRes(R.id.ll_line, R.color.gray_80);
//                        break;
//                }
//                        .setText(R.id.sub_title, lv0.subTitle)
//                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);//判断折叠状态 动态修改图标
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        LogUtils.d("Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final IteamTwoBean lv1 = (IteamTwoBean) item;
                holder.setBackgroundRes(R.id.ll_line, R.color.black);
                holder.setText(R.id.tv_left, lv1.getTitle());
//                        .setText(R.id.sub_title, lv1.subTitle)
//                        .setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        LogUtils.d("Level 1 item pos: " + pos);
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                final IteamThreeBean person = (IteamThreeBean) item;
                holder.setBackgroundRes(R.id.ll_line, R.color.gray_80);
                holder.setText(R.id.tv_left, person.getTitle() + " parent pos: " + getParentPosition(person));
                break;
        }
    }
}
