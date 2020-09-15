package com.hdyg.testcommon.bean;

/**
 * @author CZB
 * @describe 版本更新
 * @time 2019/5/15 18:04
 */
public class VersionBean {
    private String version; //版本号
    private String url;     //下载地址
    private String note;    //更新描述

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
