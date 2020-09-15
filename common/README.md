# 更新说明

#### 2020.09.02

1、添加支持文本展开/收起的自定义控件
用法:
 ExpandableTextView tvContent = findViewById(R.id.tv_expandable);
 tvContent.setMaxLine(6);
 tvContent.setTextColor(WordsUtil.getColor(R.color.main_line),WordsUtil.getColor(R.color.white));
 tvContent.setText("测试文本");

2、添加微信、支付宝工具
AliPayBuilder使用：
AliPayBuilder builder = new AliPayBuilder(mContext);
        builder.setParam(orderInfo)
                .setPayCallback(new PayCallback() {
                    @Override
                    public void onSuccess() {
                        //支付成功逻辑
                    }

                    @Override
                    public void onFailed(String msg) {
                        //支付失败逻辑
                    }
                }).pay();
WxPayBuilder使用：
WxPayBuilder builder = new WxPayBuilder(mContext, dataBean.getPay_data().getAppid());
        builder.setPayCallback(new PayCallback() {
            @Override
            public void onSuccess() {
                //支付成功逻辑
            }

            @Override
            public void onFailed(String msg) {
                //支付失败逻辑
            }
        }).setPayParam(dataBean.getPay_data()).pay();


#### 2020.09.03

1、OkhttpUtil工具类添加请求参数打印
2、优化3DES工具类
