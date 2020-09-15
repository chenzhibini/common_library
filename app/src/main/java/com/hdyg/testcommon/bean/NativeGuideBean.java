package com.hdyg.testcommon.bean;

/**
 * @author CZB
 * @describe 引导实体类
 * @time 2019/3/21 11:22
 */
public class NativeGuideBean {

    public static final int TYPE_1 = 1; //跳转本地类
    public static final int TYPE_2 = 2; //
    public static final int TYPE_3 = 3; //申请商家判断
    public static final int TYPE_4 = 4; //我的店铺判断
    public static final int TYPE_5 = 5; //切换fragment


    private int selectImg;  //选择的图片
    private int unSelectImg;    //未选中显示图片
    private int text;
    private boolean isSelect;   //是否选中
    private int type;
    private Class mClass;
    private String url;

    /**
     *
     * @param selectImg 选中图标
     * @param unSelectImg 未选中图标
     * @param text 文字
     * @param isSelect 是否选中
     */
    public NativeGuideBean(int selectImg, int unSelectImg, int text, boolean isSelect) {
        this.selectImg = selectImg;
        this.unSelectImg = unSelectImg;
        this.text = text;
        this.isSelect = isSelect;
    }

    /**
     * 首页本地数据实体
     * @param selectImg 图片
     * @param text 文字
     * @param type 类型 1跳转对象 2跳转外链 3暂未开放 4需要传值
     * @param mClass 跳转的对象
     * @param url 跳转的URL地址
     */
    public NativeGuideBean(int selectImg, int text, int type, Class mClass, String url) {
        this.selectImg = selectImg;
        this.text = text;
        this.type = type;
        this.mClass = mClass;
        this.url = url;
    }

    /**
     * 红包导航栏实体
     * @param text 文本
     * @param isSelect 是否选中
     */
    public NativeGuideBean(int text, boolean isSelect) {
        this.text = text;
        this.isSelect = isSelect;
    }
    public NativeGuideBean(int text, int type, boolean isSelect) {
        this.text = text;
        this.type = type;
        this.isSelect = isSelect;
    }
    public NativeGuideBean(int text, Class mClass) {
        this.text = text;
        this.mClass = mClass;
    }

    public Class getmClass() {
        return mClass;
    }

    public void setmClass(Class mClass) {
        this.mClass = mClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getSelectImg() {
        return selectImg;
    }

    public void setSelectImg(int selectImg) {
        this.selectImg = selectImg;
    }

    public int getUnSelectImg() {
        return unSelectImg;
    }

    public void setUnSelectImg(int unSelectImg) {
        this.unSelectImg = unSelectImg;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
