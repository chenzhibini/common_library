package com.hdyg.common.widget.contact;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class CityBean extends BaseIndexPinyinBean {

    private String city;//城市名字

    public CityBean() {
    }
    public CityBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    //需要被转化成拼音的字段
    @Override
    public String getTarget() {
        return city;
    }
}
