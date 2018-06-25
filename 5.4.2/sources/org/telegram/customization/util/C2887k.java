package org.telegram.customization.util;

import android.app.Activity;
import android.graphics.PorterDuff.Mode;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;
import java.math.BigInteger;
import java.util.regex.Pattern;
import org.ir.talaeii.R;

/* renamed from: org.telegram.customization.util.k */
public class C2887k {
    /* renamed from: a */
    public static boolean m13402a(EditText editText, TextInputLayout textInputLayout, int i, Activity activity) {
        String obj = editText.getText().toString();
        try {
            if (!TextUtils.isEmpty(obj) && TextUtils.isDigitsOnly(obj) && Long.parseLong(obj) > 0 && obj.length() == 10 && obj.length() < 11) {
                int i2;
                long j = 0;
                for (i2 = 1; i2 <= 9; i2++) {
                    j += Long.parseLong(obj.substring(i2 - 1, i2)) * ((long) (11 - i2));
                }
                j %= 11;
                i2 = Integer.parseInt(obj.substring(9, 10));
                if (((long) i2) == 11 - j || ((j == 0 && i2 == 0) || (j == 1 && i2 == 1))) {
                    textInputLayout.setErrorEnabled(false);
                    editText.getBackground().setColorFilter(activity.getResources().getColor(R.color.red), Mode.SRC_ATOP);
                    return true;
                }
            }
            textInputLayout.setError(activity.getString(i));
            editText.getBackground().setColorFilter(activity.getResources().getColor(R.color.colorPrimary), Mode.SRC_ATOP);
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m13403a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String toUpperCase = str.replace(" ", TtmlNode.ANONYMOUS_REGION_ID).replace("-", TtmlNode.ANONYMOUS_REGION_ID).toUpperCase();
        Pattern compile = Pattern.compile("IR[0-9]{24}");
        if (toUpperCase.length() != 26 || !compile.matcher(toUpperCase).find()) {
            return false;
        }
        return C2887k.m13404b(new StringBuilder().append(toUpperCase.substring(4)).append(String.valueOf((toUpperCase.charAt(0) + -65) + 10)).append(String.valueOf((toUpperCase.charAt(1) + -65) + 10)).append(toUpperCase.substring(2, 4)).toString()) == 1;
    }

    /* renamed from: b */
    private static int m13404b(String str) {
        String str2 = TtmlNode.ANONYMOUS_REGION_ID;
        String str3 = str;
        while (str3.length() > 2) {
            str2 = str3.length() >= 9 ? str3.substring(0, 9) : str3;
            str3 = new BigInteger(str2).remainder(new BigInteger("97")).intValue() + str3.substring(str2.length());
        }
        return new BigInteger(str3).remainder(new BigInteger("97")).intValue();
    }
}
