package com.hdyg.common.util;

import com.google.gson.Gson;
import com.hdyg.common.util.MD5.MapKeyComparator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密工具类
 */
public class DES3 {
    // 密钥
    private final static String SECRET_KEY = "15H2VOsmdNXuwW4XD0BEBkB7";
    // 向量
    private final static String IV = "EXu5O7Bw";
    // 加解密统一使用的编码方式
    private final static String ENCODING = "utf-8";
    // 加解密算法/工作模式/填充方式
    private final static String ALGORITHM_DES = "desede/CBC/PKCS5Padding";
    private final static String DESDE = "desede";


    /**
     * map转json字符串
     *
     * @param params 参数
     * @return result
     */
    public static String getMapToJsonStr(Map<String, String> params) {

        Map<String, String> resultMap = sortMapByKey(params);    //按Key进行排序
        Gson gson = new Gson();
        String result = gson.toJson(resultMap);
        return result;
    }

    /**
     * 使用 Map按key进行排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 3DES加密
     *
     * @param params 加密参数
     * @return s
     */
    public static String encode(Map<String, String> params) {

        try {
            Gson gson = new Gson();
            String result = gson.toJson(params);
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DESDE);
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(result.getBytes(ENCODING));
            return Base64.encode(encryptData);
        } catch (Exception e) {
            LogUtils.e("3DES加密异常==>" + e.getMessage());
        }
        return "";
    }

    /**
     * 3DES解密
     *
     * @param encryptText 解密文本
     * @return s
     */
    public static String decode(String encryptText) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(SECRET_KEY.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DESDE);
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
            return new String(decryptData, ENCODING);
        } catch (Exception e) {
            LogUtils.e("3DES解密异常==>" + e.getMessage());
        }
        return null;
    }

    public static String padding(String str) {
        byte[] oldByteArray;
        oldByteArray = str.getBytes(StandardCharsets.UTF_8);
        int numberToPad = 8 - oldByteArray.length % 8;
        byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
//        System.arraycopy(oldByteArray, 0, newByteArray, 0,
//                oldByteArray.length);
        for (int i = oldByteArray.length; i < newByteArray.length; ++i) {
            newByteArray[i] = 0;
        }
        return new String(newByteArray, StandardCharsets.UTF_8);
    }

    /**
     * Base64编码工具类
     */
    public static class Base64 {
        private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

        public static String encode(byte[] data) {
            int start = 0;
            int len = data.length;
            StringBuffer buf = new StringBuffer(data.length * 3 / 2);

            int end = len - 3;
            int i = start;
            int n = 0;

            while (i <= end) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append(legalChars[(d >> 6) & 63]);
                buf.append(legalChars[d & 63]);

                i += 3;

                if (n++ >= 14) {
                    n = 0;
                    buf.append(" ");
                }
            }

            if (i == start + len - 2) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append(legalChars[(d >> 6) & 63]);
                buf.append("=");
            } else if (i == start + len - 1) {
                int d = (((int) data[i]) & 0x0ff) << 16;

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append("==");
            }

            return buf.toString();
        }

        private static int decode(char c) {
            if (c >= 'A' && c <= 'Z')
                return ((int) c) - 65;
            else if (c >= 'a' && c <= 'z')
                return ((int) c) - 97 + 26;
            else if (c >= '0' && c <= '9')
                return ((int) c) - 48 + 26 + 26;
            else
                switch (c) {
                    case '+':
                        return 62;
                    case '/':
                        return 63;
                    case '=':
                        return 0;
                    default:
                        throw new RuntimeException("unexpected code: " + c);
                }
        }

        /**
         * Decodes the given Base64 encoded String to a new byte array. The byte array holding the decoded data is returned.
         */
        public static byte[] decode(String s) {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                decode(s, bos);
            } catch (IOException e) {
                throw new RuntimeException();
            }
            byte[] decodedBytes = bos.toByteArray();
            try {
                bos.close();
                bos = null;
            } catch (IOException ex) {
                System.err.println("Error while decoding BASE64: " + ex.toString());
            }
            return decodedBytes;
        }

        private static void decode(String s, OutputStream os) throws IOException {
            int i = 0;

            int len = s.length();

            while (true) {
                while (i < len && s.charAt(i) <= ' ')
                    i++;

                if (i == len)
                    break;

                int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6) + (decode(s.charAt(i + 3)));

                os.write((tri >> 16) & 255);
                if (s.charAt(i + 2) == '=')
                    break;
                os.write((tri >> 8) & 255);
                if (s.charAt(i + 3) == '=')
                    break;
                os.write(tri & 255);

                i += 4;
            }
        }
    }
} 