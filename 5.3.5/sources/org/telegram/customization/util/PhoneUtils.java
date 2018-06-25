package org.telegram.customization.util;

public class PhoneUtils {
    public static String normalize(String phone) {
        if (phone == null || phone.length() < 7) {
            return phone;
        }
        String ans = phone;
        if (ans.startsWith("00") && ans.length() > 2) {
            ans = ans.substring(2);
        }
        if (ans.startsWith("+") && ans.length() > 1) {
            ans = ans.substring(1);
        }
        if (ans.startsWith("98") && ans.length() > 2) {
            ans = ans.substring(2);
        }
        if (ans.startsWith("0")) {
            return ans;
        }
        return "0" + ans;
    }
}
