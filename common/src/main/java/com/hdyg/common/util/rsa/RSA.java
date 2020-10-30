package com.hdyg.common.util.rsa;

import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.hdyg.common.util.LogUtils;
import com.hdyg.common.util.MD5.MapKeyComparator;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author CZB
 * @describe RSA工具
 * @time 2020/9/28 15:20
 */
public class RSA {

    public static final String TAG = "RSAUtil";

    /**
     * 公钥
     */
    public static String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSYuXSOeV8AxL3GV0Z4E5ts+2E\n" +
            "dVOko3QKeIuJ6ZNdP/TrOUomW0ME+PLh7ei8MnienqEQxFeBBlRQj9ZgHlAtGm0e\n" +
            "ElqhIyCnaCYKD7DDhAeyKubk9NFAP7E9Qq2+D3MHOF+kKiEygULy/BsThHHuhz9O\n" +
            "V15+0ibpE3+nDKTLtwIDAQAB";
    /**
     * 私钥
     */
    public static String PRI_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANJi5dI55XwDEvcZ\n" +
            "XRngTm2z7YR1U6SjdAp4i4npk10/9Os5SiZbQwT48uHt6LwyeJ6eoRDEV4EGVFCP\n" +
            "1mAeUC0abR4SWqEjIKdoJgoPsMOEB7Iq5uT00UA/sT1Crb4Pcwc4X6QqITKBQvL8\n" +
            "GxOEce6HP05XXn7SJukTf6cMpMu3AgMBAAECgYEAuv5gGuyOxhzNXIdUss0lqGgG\n" +
            "Mnc98tW9LBlcnSXYb0eLXw86ksEG3oKPDrSw6aVKhGGl8wswY2PGyRwvhJss0mxF\n" +
            "4K4GmJ+jCEzKHumSLVIaKcP5OuPcae6FG8OiZnD+YOC/tAOkUGvBlmuWtg6d9LgD\n" +
            "uZC+vb4hMWEtrHo9TTECQQD5rVVmQATgSPY4qoj/a2E/9AH4DNqSIwBFOCRlHrVs\n" +
            "HmmYUNPkzJPvMfDw0fB6vWlEh9euJZck1GA8V6RkznxjAkEA17bXLztbo2Ge0NeG\n" +
            "WzdMgFvjL+TdnV9fP4C1L9+gTFOUQB2hL/Gaaaj/qmsREOCjk4/25pKyy1yKFAL0\n" +
            "WuthnQJBAJcKMqVeiRSdTsZM8/+AGB62IBLMw9Sv89Pr+KhrgVjctQh9rRG2aK/E\n" +
            "yHsGpbG6r9nTS7h/6QVQrL8UTdDl0P0CQHRCNb1d92iZv1d9IQteRVvUqh/w8EPV\n" +
            "1+bIvl4b3y/jx726eln1k6woigpWA++dsmtgJshj/fq+WCiq4vlxbL0CQFIVDeKM\n" +
            "wbVQ6qfowwr9cTet6NAzloHAHp2JKjrE9CIauwtmepZ4rHCk2v8KZY9ni/YL9mY4\n" +
            "v0APYcpp70iLEdo=";

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * 加密方式，标准jdk的
     */
    private static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 设置公钥、私钥
     * @param privateKey 私钥
     * @param publicKey 公钥
     */
    public void setKey(String privateKey, String publicKey) {
        this.PUB_KEY = publicKey;
        this.PRI_KEY = privateKey;
    }

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 使用 Map按key进行排序
     */
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(
                new MapKeyComparator());
        sortMap.putAll(map);
//        String result = new Gson().toJson(sortMap); //转json
        return sortMap;
    }

    /**
     * 先排序，再拼接(key=value&key=value)，再转字节
     */
    public static byte[] getParam(Map<String, String> params) {
        Map<String, String> resultMap = sortMapByKey(params);    //按Key进行排序
        if (resultMap == null)
            return null;
        StringBuffer buffer = new StringBuffer();
        for (Iterator<String> it = resultMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            //当jsonp跨域访问时会出现该参数
            if (TextUtils.equals("SIGN", key) || TextUtils.equals("callback", key.toLowerCase())) {
                continue;
            }
            if (TextUtils.isEmpty(resultMap.get(key))) {
                continue;
            }
            buffer.append(key + "=" + resultMap.get(key) + "&");
        }
        String param = buffer.substring(0, buffer.length() - 1);
        LogUtils.d(TAG, "params----->" + param);
        return param.getBytes(UTF_8);
    }

    /**
     * 获取公钥
     *
     * @param key pkcs#8
     */
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] keyBytes;
            keyBytes = Base64.decode(key, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            LogUtils.e(TAG, "获取公钥异常-----");
            return null;
        }
    }

    /**
     * 获取私钥
     *
     * @param key pkcs#8
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] keyBytes;
            keyBytes = Base64.decode(key, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            LogUtils.e(TAG, "获取私钥异常-----");
            return null;
        }
    }

    /**
     * 使用私钥对数据进行签名
     *
     * @param map 请求参数
     * @return 结果
     */
    public static String sign(Map<String, String> map) {
        try {
            byte[] data = getParam(map);
            PrivateKey priKey = getPrivateKey(PRI_KEY);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data);
            byte[] signed = signature.sign();
            return Base64.encodeToString(signed, Base64.DEFAULT);
        } catch (Exception e) {
            LogUtils.e(TAG, "RSA签名报错----" + e.getMessage());
            return null;
        }
    }

    /**
     * 验签
     *
     * @param data 请求数据
     * @param sign 验签字段
     * @return true/false
     */
    public static boolean verify(byte[] data, byte[] sign) {
        if (data == null || data.length == 0) {
            LogUtils.e(TAG, "data is empty.");
            return false;
        }
        try {
            PublicKey pubKey = getPublicKey(PUB_KEY);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (Exception e) {
            LogUtils.e(TAG, "RSA验签报错----" + e.getMessage());
            return false;
        }
    }


    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decode(privateKey, 0);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        try {
            byte[] keyBytes = Base64.decode(publicKey, 0);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, publicK);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            LogUtils.e(TAG, "解密失败");
            return null;
        }

    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decode(publicKey, 0);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param params     源数据
     * @param privateKey 私钥(BASE64编码)
     * @return byte[]
     */
    public static byte[] encryptByPrivateKey(Map<String, String> params, String privateKey) {
        try {
            byte[] data = getParam(params);
            byte[] keyBytes = Base64.decode(privateKey, 0);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, privateK);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            LogUtils.e(TAG, "RSA异常");
            return null;
        }

    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeToString(key.getEncoded(), 0);
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeToString(key.getEncoded(), 0);
    }
}
