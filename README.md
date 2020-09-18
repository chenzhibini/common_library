# 更新说明

#### 依赖使用说明 版本1.0.0
- gradle安装说明
```java
implementation 'com.github.chenzhibini:common_library:1.0.2'
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
ProCityUtil使用:
```java
ProCityUtil proCityUtil = new ProCityUtil(mContext);
//时间选择器
            proCityUtil
                        .setTimeType(true, true, true, true, true, true)
                        .setTimeParam(0, 0, null, 0, 0, null, 0, 0, null, 0, 0, 0, true, false)
                        .setTimeListener(date -> {
                            LogUtils.d("" + date.getTime());
                        })
                        .showTimePicker();
//省市区选择器
            proCityUtil
                        .setProCityAreaParam(null, 0, null, null, null, 0, null, null, 0, null, null, null, null, null)
                        .setProCityAreaListener((province, city, district) -> {
                            toastNotifyShort(province.getName() + "," + city.getName() + "," + district.getName());
                        })
                        .showProCityPicker();
//自定义数据 无联动
            proCityUtil
                        .setCustomParam(0, 0, 0, false)
                        .setCustomDatas(ProCityUtil.LEVEL_3, data1, data2, data3)
                        .setCustomListener((options1, options2, options3) -> {
                            toastNotifyShort(data1.get(options1) + "," + data2.get(options2) + "," + data3.get(options3));
                        })
                        .showCustomBuilder();
```
- 使用拍照工具，再AndroidManifest.xml文件中添加以下代码
```java
  <!--图片选择框架权限-->
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.CAMERA" />
  <activity
            android:name="cn.finalteam.rxgalleryfinal.ui.activity.MediaActivity"
            android:screenOrientation="portrait"
            android:exported="true" />
  <activity android:name="com.yalantis.ucrop.UCropActivity"
              android:screenOrientation="portrait" />
```
- 使用跳转第三方地图方法
```java
//跳转高德地图 传入目的地经纬度和目的地名字
IntentMapUtil.openGaoDeMap(mContext, 24.48263, 118.148286, "忠仑公园", WordsUtil.getString(R.string.app_name));
//跳转腾讯地图 传入目的地经纬度和目的地名字
IntentMapUtil.openTencentMap(mContext, 24.48263, 118.148286, "忠仑公园");
//跳转百度地图 传入目的地经纬度和目的地名字
IntentMapUtil.openBaiduMap(mContext, 24.48263, 118.148286, "忠仑公园");
```
- 隐私协议窗口使用
```java
                //显示文本可自行编辑，但必须包含  《用户服务协议》《隐私政策》
                new JDialog.Builder(mContext, JDialogType.PROTOCOL)
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setContent(WordsUtil.getString(R.string.sys_proto))
                        .setOnClickListener(new JDialogLsitener.OnClickListener() {
                            @Override
                            public void onOkClick(String s) {
                                SPUtils.put(SpMsg.SHOW_PROTOCOL, true);
                                ToastUtil.show("同意协议");
                            }

                            @Override
                            public void onCancelClick() {
                                ToastUtil.show("不同意协议");
                            }
                        })
                        .setProtocolCallBack(new JDialogLsitener.ProtocolListener() {
                            @Override
                            public void userProtocolListener() {
                                UIHelper.showWeb(mContext, AppConfig.Protocol.USER_PROTOCOL);
                            }

                            @Override
                            public void yinsiProtocolListener() {
                                UIHelper.showWeb(mContext, AppConfig.Protocol.YINSI_PROTOCOL);
                            }
                        })
                        .build()
                        .show();

```

