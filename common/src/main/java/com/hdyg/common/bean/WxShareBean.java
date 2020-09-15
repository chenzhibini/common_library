package com.hdyg.common.bean;

/**
 * @author CZB
 * @describe 微信分享参数
 * @time 2020/8/27 16:22
 */
public class WxShareBean {
    public String url;
    public String title;
    public String des;
    public String openid;
    public String logo;

    public WxShareBean(String url, String title, String des, String openid, String logo) {
        this.url = url;
        this.title = title;
        this.des = des;
        this.openid = openid;
        this.logo = logo;
    }
}
