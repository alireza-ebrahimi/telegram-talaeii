package com.persianswitch.sdk.base.utils.strings;

import android.content.Context;
import com.persianswitch.sdk.C2262R;

public class StringFormatter {
    /* renamed from: a */
    public static String m10796a(Context context, String str) {
        return m10798a(context.getString(C2262R.string.asanpardakht_amount_unit_irr), str);
    }

    /* renamed from: a */
    private static String m10797a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            if (!Character.isDigit(str.charAt(i))) {
                stringBuilder.append(i);
            } else if ((str.length() - i) % 3 != 0 || i <= 0 || i == str.length() - 1) {
                stringBuilder.append(str.charAt(i));
            } else {
                stringBuilder.append(",").append(str.charAt(i));
            }
            i++;
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public static String m10798a(String str, String str2) {
        return m10797a(str2) + " " + str;
    }
}
