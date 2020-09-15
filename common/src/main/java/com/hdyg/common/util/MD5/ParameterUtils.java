package com.hdyg.common.util.MD5;

import android.text.TextUtils;

import com.google.zxing.common.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class ParameterUtils {
	
	public static String KEY = "key";
	
	public static String getRequestQueryString(Map<String, String> params,String mkey) {

		Map<String, String> resultMap = sortMapByKey(params);    //按Key进行排序
		StringBuffer buffer = new StringBuffer();
		for (Iterator<String> it = resultMap.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			//当jsonp跨域访问时会出现该参数
			if (TextUtils.equals("SIGN", key) || TextUtils.equals("callback", key.toLowerCase())){
				continue;
			}
			if (TextUtils.isEmpty(resultMap.get(key))){
				continue;
			}
			buffer.append(key + "=" + resultMap.get(key) + "&");
		}
		return buffer.substring(0, buffer.length()-1) + mkey;
	}
	
	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
 
        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());
 
        sortMap.putAll(map);
 
        return sortMap;
    }

//    /**
//     * 去除value为空的
//     * @param params
//     * @return
//     */
//	public static Map<String, String> removeEmptyData(Map<String, String> params) {
//		Map<String,String> resultMap = new HashMap<String,String>();
//		for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
//			String key = it.next();
//			String value = params.get(key);
//			if(StringUtils.isEmpty(value)){
//				continue;
//			}
//			resultMap.put(key, value);
//		}
//
//		return resultMap;
//	}
//	public static Map<String, String> getRequestParameters(HttpServletRequest request) {
//		Map<String, String> map = new HashMap<String, String>();
//
//		Map<String, String[]> paramMap = request.getParameterMap();
//		for (Iterator<String> it = paramMap.keySet().iterator(); it.hasNext();) {
//			String key = it.next();
//			map.put(key, paramMap.get(key)[0]);
//		}
//		return map;
//	}
	
}