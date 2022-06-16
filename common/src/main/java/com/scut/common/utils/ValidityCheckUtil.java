package com.scut.common.utils;

import java.util.regex.Pattern;

public class ValidityCheckUtil {
    /**
     * 对email的格式正确性进行判断
     * 不能以点，减号，下划线开头
     * @ 前不允许是点、减号
     * @ 后面不能马上跟.
     * 不能以点，减号，下划线开头
     * */
    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^([A-Za-z0-9]+([-.]\\w+)*)@\\w+(\\.[a-zA-Z0-9_-]+)+$", email);
        }
        return false;
    }

    /**
     * 对password的格式正确性进行判断
     * 长度必须为6~18
     * 密码只能由大小写字母、数字、下划线组成
     * */
    public static boolean isValidPassword(String password) {
        if ((password != null) && (!password.isEmpty())) {
            return Pattern.matches("\\w{6,18}", password);
        }
        return false;
    }
}
