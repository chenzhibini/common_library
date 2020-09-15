package com.hdyg.common.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;


/**
 * 获取手机设备信息
 */
public class DeviceUtils {
    public static String getIMEI(Context context) {
        String deviceId = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                deviceId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else {
                // request old storage permission
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null;
                }
                deviceId = tm.getDeviceId();
            }
            if (deviceId == null || "".equals(deviceId)) {
                return getLocalMacAddress(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (deviceId == null || "".equals(deviceId)) {
                return getLocalMacAddress(context);
            }
        }

        return deviceId;
    }

    @SuppressLint({"WifiManagerPotentialLeak", "HardwareIds"})
    private static String getLocalMacAddress(Context context) {
        String macAddress = null; WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());

        assert wifiManager != null;
        if (!wifiManager.isWifiEnabled()) {
            //必须先打开，才能获取到MAC地址
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(false);
        }
        if (null != info) {
            macAddress = info.getMacAddress();
        }
        return macAddress;
    }
}
