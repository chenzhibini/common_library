package com.hdyg.common.bean;

/**
 * @author CZB
 * @describe 微信支付参数
 * @time 2020/4/22 16:32
 */
public class WxPayBean {
    private DataBean pay_data;
    private String order_id;
    private String alipay_data;

    public String getAlipay_data() {
        return alipay_data;
    }

    public void setAlipay_data(String alipay_data) {
        this.alipay_data = alipay_data;
    }

    public DataBean getPay_data() {
        return pay_data;
    }

    public void setPay_data(DataBean pay_data) {
        this.pay_data = pay_data;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public class DataBean{
        private String appid;
        private String noncestr;
        private String packageStr;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageStr() {
            return packageStr;
        }

        public void setPackageStr(String packageStr) {
            this.packageStr = packageStr;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
