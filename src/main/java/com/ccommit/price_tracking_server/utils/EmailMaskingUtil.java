package com.ccommit.price_tracking_server.utils;

public class EmailMaskingUtil {
    public static String maskEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex > 0) {
            String localPart = email.substring(0, atIndex); // 이메일의 @ 앞부분
            String domain = email.substring(atIndex); // 이메일의 @ 뒷부분

            // 이메일 앞 3글자는 그대로 두고, 그 이후는 마스킹
            String maskedLocalPart = localPart.length() > 3
                    ? localPart.substring(0, 3) + "****"
                    : localPart; // 앞 3글자만 남기고 나머지 마스킹

            // 마스킹된 이메일 반환
            return maskedLocalPart + domain;
        }
        return email;
    }
}
