package com.hdyg.common.bean;

/**
 * @author CZB
 * @describe 打开小程序实体类
 * @time 2020/9/18 15:35
 */
public class WxOpenBean {
    public String userName;
    public String path;
    public int miniprogramType;

    public WxOpenBean(String userName, String path, int miniprogramType) {
        this.userName = userName;
        this.path = path;
        this.miniprogramType = miniprogramType;
    }
}
