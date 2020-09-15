# 更新说明

#### 依赖使用说明 版本1.0.0
- gradle安装说明
```java
implementation 'com.github.chenzhibini:common_module:1.0.0'
```
- 初始化,在application中添加
```java
CommonModule.init(this);
```

#### 微信、支付宝工具使用方法

- 添加支持文本展开/收起的自定义控件
用法:

```java
 ExpandableTextView tvContent = findViewById(R.id.tv_expandable);
 tvContent.setMaxLine(6);
 tvContent.setTextColor(WordsUtil.getColor(R.color.main_line),WordsUtil.getColor(R.color.white));
 tvContent.setText("测试文本");
```

- 添加微信、支付宝工具
AliPayBuilder使用：
```java
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
```
WxPayBuilder使用：
```java
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
        }).setPayParam(dataBean.getPay_data())
        .pay();
```

#### 2020.09.03

- 优化common library
