package com.persianswitch.sdk.base.utils.strings;

import android.content.Context;
import com.persianswitch.sdk.C0770R;

public class StringFormatter {
    public static String formatPrice(String postFix, String price) {
        return separatePriceByComma(price) + " " + postFix;
    }

    public static String formatPrice(Context context, String price) {
        return formatPrice(context.getString(C0770R.string.asanpardakht_amount_unit_irr), price);
    }

    private static String separatePriceByComma(String number) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < number.length()) {
            if (!Character.isDigit(number.charAt(i))) {
                sb.append(i);
            } else if ((number.length() - i) % 3 != 0 || i <= 0 || i == number.length() - 1) {
                sb.append(number.charAt(i));
            } else {
                sb.append(",").append(number.charAt(i));
            }
            i++;
        }
        return sb.toString();
    }
}
