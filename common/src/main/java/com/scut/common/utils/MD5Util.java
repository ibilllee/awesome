package com.scut.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    private static String key = "dssas@^11vfis#$%d22u98-411ssqe1";
    // 带秘钥加密
    public static String convertMD5(String text) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text + key);
        System.out.println("MD5加密后的字符串为:" + md5str);
        return md5str;
    }

    // 不带秘钥加密
    public static String convertMD52(String text) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text);
        System.out.println("MD52加密后的字符串为:" + md5str + "\t长度：" + md5str.length());
        return md5str;
    }

    // 根据传入的密钥进行验证
    public static boolean verify(String text, String md5) throws Exception {
        String md5str = convertMD5(text);
        if (md5str.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }


}


