package com.hdyg.testcommon.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author CZB
 * @describe
 * @time 2019/9/17 16:03
 */
public class ImageBean {

    public static final int TYPE_1 = 1; //添加图片按钮
    public static final int TYPE_2 = 2; //网络图片
    public static final int TYPE_3 = 3; //查看图片

    @SerializedName("photo")
    private String url;
    private int type;

    public ImageBean(String url,int type){
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
