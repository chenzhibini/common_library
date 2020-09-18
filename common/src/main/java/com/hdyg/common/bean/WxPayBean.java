package com.hdyg.common.bean;

/**
 * @author CZB
 * @describe 微信支付参数
 * @time 2020/4/22 16:32
 */
public class WxPayBean {
    public String appid;
    public String noncestr;
    public String packageStr;
    public String partnerid;
    public String prepayid;
    public String timestamp;
    public String sign;

    public WxPayBean(String appid, String noncestr, String packageStr, String partnerid, String prepayid, String timestamp, String sign) {
        this.appid = appid;
        this.noncestr = noncestr;
        this.packageStr = packageStr;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.timestamp = timestamp;
        this.sign = sign;
    }
}
