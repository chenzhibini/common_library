# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Users\Administrator\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 指定代码的压缩级别0-7
-optimizationpasses 5
# 混淆后类型都为小写
-dontusemixedcaseclassnames
# 不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 # 优化  不优化输入的类文件
-dontoptimize
 # 预校验
-dontpreverify
 # 混淆时是否记录日志
-verbose
# 忽略警告
-ignorewarning
# 保护注解
-keepattributes *Annotation*
# 保持四大组件不混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
# 保持枚举enum类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class **.R$* {
    *;
}
-keep class * extends android.view.View{*;}
-keep class * extends android.app.Dialog{*;}
-keep class * implements java.io.Serializable{*;}
# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
# okhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.**{*;}
-dontwarn okio.**
# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#避免混淆bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
#地区3级联动选择器
-keep class com.lljjcoder.**{
	*;
}
# 拼音4j
-dontwarn demo.**
-keep class demo.**{*;}
-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.**{*;}
-keep class net.sourceforge.pinyin4j.format.**{*;}
-keep class net.sourceforge.pinyin4j.format.exception.**{*;}
# gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# 保持实体类不混淆
-keep class com.hdyg.lbs.entry.** { *; }
# AndroidX 防止混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}
# EventBus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
# AopArms
-keepclassmembers class * {
    @cn.com.superLei.aoparms.annotation.Callback <methods>;
}


#3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}

#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
-keep   class com.amap.api.services.**{*;}

#微信混淆
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** { *;}
-keep class com.tencent.mm.sdk.** { *;}


