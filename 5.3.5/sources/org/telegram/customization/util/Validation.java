package org.telegram.customization.util;

import android.app.Activity;
import android.graphics.PorterDuff.Mode;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;
import java.math.BigInteger;
import java.util.regex.Pattern;
import org.ir.talaeii.R;

public class Validation {
    public static boolean isValidNationalCode(EditText et, TextInputLayout til, int errorMsg, Activity activity) {
        String nationalCode = et.getText().toString();
        try {
            if (!TextUtils.isEmpty(nationalCode) && TextUtils.isDigitsOnly(nationalCode) && Long.parseLong(nationalCode) > 0 && nationalCode.length() == 10 && nationalCode.length() < 11) {
                long sum = 0;
                for (int i = 1; i <= 9; i++) {
                    sum += Long.parseLong(nationalCode.substring(i - 1, i)) * ((long) (11 - i));
                }
                long num3 = sum % 11;
                int num4 = Integer.parseInt(nationalCode.substring(9, 10));
                if (((long) num4) == 11 - num3 || ((num3 == 0 && num4 == 0) || (num3 == 1 && num4 == 1))) {
                    til.setErrorEnabled(false);
                    et.getBackground().setColorFilter(activity.getResources().getColor(R.color.red), Mode.SRC_ATOP);
                    return true;
                }
            }
            til.setError(activity.getString(errorMsg));
            et.getBackground().setColorFilter(activity.getResources().getColor(R.color.colorPrimary), Mode.SRC_ATOP);
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private static int iso7064Mod97_10(String str) {
        String remainder = str;
        String block = "";
        while (remainder.length() > 2) {
            if (remainder.length() >= 9) {
                block = remainder.substring(0, 9);
            } else {
                block = remainder;
            }
            remainder = new BigInteger(block).remainder(new BigInteger("97")).intValue() + remainder.substring(block.length());
        }
        return new BigInteger(remainder).remainder(new BigInteger("97")).intValue();
    }

    public static boolean isValidShaba(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        str = str.replace(" ", "").replace("-", "").toUpperCase();
        Pattern pattern = Pattern.compile("IR[0-9]{24}");
        if (str.length() != 26 || !pattern.matcher(str).find()) {
            return false;
        }
        if (iso7064Mod97_10(str.substring(4) + String.valueOf((str.charAt(0) - 65) + 10) + String.valueOf((str.charAt(1) - 65) + 10) + str.substring(2, 4)) == 1) {
            return true;
        }
        return false;
    }
}
