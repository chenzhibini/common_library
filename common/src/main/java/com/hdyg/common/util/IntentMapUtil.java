package com.hdyg.common.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

import java.util.List;

/**
 * @author CZB
 * @describe 跳转地图工具
 * @time 2020/5/26 17:00
 */
public class IntentMapUtil {

    /**
     * 打开高德地图 （公交出行，起点位置使用地图当前位置）
     * t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
     *
     * @param mContext 上下文
     * @param dlat     终点纬度
     * @param dlon     终点经度
     * @param address  终点名称
     */
    public static void openGaoDeMap(Context mContext, double dlat, double dlon, String address, String appName) {
        // 是否安装了高德地图
        LogUtils.d("点击了跳转高德地图");
        if (isInstallApk(mContext, "com.autonavi.minimap")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.autonavi.minimap");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("androidamap://route?sourceApplication=" + appName
                    + "&sname=我的位置&dlat=" + dlat
                    + "&dlon=" + dlon
                    + "&dname=" + address
                    + "&dev=0&m=0&t=1"));
            mContext.startActivity(intent);
        } else {// 未安装
            ToastUtil.show("您尚未安装高德地图");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }


    /**
     * 打开百度地图
     * mode = transit（公交）、driving（驾车）、walking（步行）和riding（骑行）. 默认:driving
     * 当 mode=transit 时 ： sy = 0：推荐路线 、 2：少换乘 、 3：少步行 、 4：不坐地铁 、 5：时间短 、 6：地铁优先
     *
     * @param mContext 上下文
     * @param dlat     终点纬度
     * @param dlon     终点经度
     * @param dname    终点名称
     */
    public static void openBaiduMap(Context mContext, double dlat, double dlon, String dname) {
        if (isInstallApk(mContext, "com.baidu.BaiduMap")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&destination=name:"
                    + dname
                    + "|latlng:" + dlat + "," + dlon
                    + "&mode=transit&sy=3&index=0&target=1"));
            mContext.startActivity(intent);
        } else {
            ToastUtil.show("百度地图未安装");
        }
    }

    /**
     * 打开腾讯地图（公交出行，起点位置使用地图当前位置）
     * 公交：type=bus，policy有以下取值
     * 0：较快捷 、 1：少换乘 、 2：少步行 、 3：不坐地铁
     * 驾车：type=drive，policy有以下取值
     * 0：较快捷 、 1：无高速 、 2：距离短
     * policy的取值缺省为0
     *
     * @param mContext 上下文
     * @param dlat     终点纬度
     * @param dlon     终点经度
     * @param dname    终点名称
     */
    public static void openTencentMap(Context mContext, double dlat, double dlon, String dname) {
        if (isInstallApk(mContext, "com.tencent.map")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("qqmap://map/routeplan?type=bus&from=我的位置&fromcoord=0,0"
                    + "&to=" + dname
                    + "&tocoord=" + dlat + "," + dlon
                    + "&policy=1&referer=myapp"));
            mContext.startActivity(intent);
        } else {
            ToastUtil.show("腾讯地图未安装");
        }
    }


    /**
     * 判断手机中是否安装指定包名的软件
     */
    public static boolean isInstallApk(Context context, String name) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(name)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

}
