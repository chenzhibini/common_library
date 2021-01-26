package com.hdyg.testcommon.bean;


/**
 * @author CZB
 * @describe 多布局实体类
 * @time 2021/1/18 10:01
 */
public class MultBean {

    public static final int TITLE = 0x0001;         // 标题
    public static final int CONTENT = 0x0002;  // 分类

    public int type;


    public MultBean(int type) {
        this.type = type;
    }


}
