# 更新说明

#### 依赖使用说明 版本1.0.8
- gradle安装说明
```java
implementation 'com.github.chenzhibini:common_library:1.1.0'
```
- 初始化,在application中添加
```java
CommonModule.init(this,true);
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
需要导入支付宝sdk
需要在build.gradle添加
 aspectjx {
        exclude 'com.alipay'
    }
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
//微信支付
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
#### 拍照工具
- 权限申请,再AndroidManifest.xml文件中添加以下代码
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
- 代码调用
```java
//照片单选裁剪
TakePhotoUtil.getInstance().selectPhotoCropSingle(mContext, obj -> ToastUtil.show("照片路径-->" + obj.toString()));
//照片多选
TakePhotoUtil.getInstance().selectPhotoMult(mContext, list -> {
                    for (int i=0; i<list.size(); i++){
                        LogUtils.d("图片path---->"+list.get(i).getOriginalPath());
                    }
                });
//拍照并裁剪
TakePhotoUtil.getInstance().openZKCameraCrop(this,obj -> ToastUtil.show("照片path-->" + obj.toString()));
//自定义照片多选
int max = 9;
TakePhotoUtil.getInstance().sustomPhotoMult(mContext,max,list -> {
                    for (int i=0; i<list.size(); i++){
                        LogUtils.d("path---->"+list.get(i).getOriginalPath());
                    }
                });
//查看大图
TakePhotoUtil.getInstance().selectLargerImage(mContext,paths,0);
//视频单选
TakePhotoUtil.getInstance().selectVideoSingle(mContext,obj -> ToastUtil.show("视频路径-->" + obj.toString()));
//视频多选
TakePhotoUtil.getInstance().selectVideoMult(mContext,list -> {
                    for (int i=0; i<list.size(); i++){
                        LogUtils.d("视频path---->"+list.get(i).getOriginalPath());
                    }
                });
// onActivityResult回调中添加
  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SimpleRxGalleryFinal.get().onActivityResult(requestCode, resultCode, data);
    }
```
#### 使用跳转第三方地图方法
```java
//跳转高德地图 传入目的地经纬度和目的地名字
IntentMapUtil.openGaoDeMap(mContext, 24.48263, 118.148286, "忠仑公园", WordsUtil.getString(R.string.app_name));
//跳转腾讯地图 传入目的地经纬度和目的地名字
IntentMapUtil.openTencentMap(mContext, 24.48263, 118.148286, "忠仑公园");
//跳转百度地图 传入目的地经纬度和目的地名字
IntentMapUtil.openBaiduMap(mContext, 24.48263, 118.148286, "忠仑公园");
```
#### 隐私协议窗口使用
```java
               //显示文本可自行编辑
               new JDialog.Builder(mContext, JDialogType.PROTOCOL)
                                   .setCancelable(false)
                                   .setCanceledOnTouchOutside(false)
                                   .setOnClickListener(new JDialogLsitener.OnClickListener() {
                                       @Override
                                       public void onOkClick(String s) {
                                           SPUtils.put(SpMsg.SHOW_PROTOCOL, true);
                                           requestPermission();
                                       }

                                       @Override
                                       public void onCancelClick() {
                                           finish();
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
                                   .setProtocol(WordsUtil.getString(R.string.start_proto),WordsUtil.getString(R.string.user_proto),WordsUtil.getString(R.string.yinsi_proto),WordsUtil.getString(R.string.end_proto))
                                   .build()
                                   .show();

```
#### 状态栏工具 设置系统状态栏字体颜色
```java
SysStyleUtil.setStatusBarLightMode(this, android.R.color.transparent, false);
```
#### 系统分享工具使用
```java
// 分享文本
ShareFileUtils.shareUrl(mContext,path);
// 分享文件
ShareFileUtils.shareFile(mContext,path);
// 分享单图
ShareFileUtils.shareImage(mContext,path);
// 分享多图
ShareFileUtils.shareImage(mContext,paths);
// 分享到微信好友(单图)
ShareFileUtils.shareImageToWeChat(mContext,path);
// 分享到微信好友(多图)
ShareFileUtils.shareImageToWeChat(mContext,paths);
// 分享到微信朋友圈(单图)
ShareFileUtils.shareImageToWeChatFriend(mContext,path);
// 分享到微信朋友圈(多图)
ShareFileUtils.shareImageToWeChatFriend(mContext,paths);
// 分享到QQ好友(单图)
ShareFileUtils.shareImageToQQ(mContext,path);
// 分享到QQ好友(多图)
ShareFileUtils.shareImageToQQ(mContext,paths);
// 分享到QQ空间(单图)
ShareFileUtils.shareImageToQZone(mContext,path);
// 分享到QQ空间(多图)
ShareFileUtils.shareImageToQZone(mContext,paths);
// 分享到微博(单图)
ShareFileUtils.shareImageToWeibo(mContext,path);
// 分享到微博(多图)
ShareFileUtils.shareImageToWeibo(mContext,paths);
```
#### 时间工具使用 DateUtil.java  具体查看注释
#### RSA工具使用
```java
1、私钥加密
byte[] aaa = RSA.encryptByPrivateKey(params, RSA.PRI_KEY);
String encodeStr = Base64Utils.encode(aaa);
2、公钥解密
byte[] bbb = RSA.decryptByPublicKey(aaa,RSA.PUB_KEY);
String s = new String(bbb);
3、验签
//签名串
String sign = RSA.sign(params);
//验签
byte[] data = RSA.getParam(params);
boolean verify = RSA.verify(data,Base64.decode(sign, Base64.DEFAULT));
```

