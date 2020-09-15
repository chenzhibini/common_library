package com.hdyg.testcommon.bean;

/**
 * @author CZB
 * @describe
 * @time 2020/3/20 9:11
 */
public class LoginBean {

    private String token;
    private String uid;//UID
    private String username;//用户名
    private String phone;//手机号
    private String is_init;//是否激活  0否 需提示激活会员   1是
    private String area;//区号

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIs_init() {
        return is_init;
    }

    public void setIs_init(String is_init) {
        this.is_init = is_init;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
