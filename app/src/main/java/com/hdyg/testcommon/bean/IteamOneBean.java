package com.hdyg.testcommon.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hdyg.testcommon.adapter.ExpandableItemAdapter;

/**
 * @author CZB
 * @describe
 * @time 2020/4/1 20:49
 */
public class IteamOneBean extends AbstractExpandableItem implements MultiItemEntity {

    private String title;
    private String subTitle;

    public IteamOneBean( String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }
}
