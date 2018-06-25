package com.persianswitch.sdk.base.security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import java.util.Locale;

public class DeviceInfo {
    /* renamed from: a */
    public static int m10719a(Context context, String str) {
        int i = 0;
        String a = StringUtils.m10802a(TtmlNode.ANONYMOUS_REGION_ID, m10724b(context), str);
        int i2 = 0;
        while (i < a.length()) {
            i2 += a.charAt(i) * i;
            i++;
        }
        return i2;
    }

    /* renamed from: a */
    public static String m10720a() {
        return String.format(Locale.US, "%s(%s)", new Object[]{Build.MODEL, Build.MANUFACTURER});
    }

    /* renamed from: a */
    public static String m10721a(Context context, Config config) {
        try {
            String str = ";";
            Object[] objArr = new Object[4];
            objArr[0] = LanguageManager.m10669a(context).m10677b() ? "1" : "2";
            objArr[1] = config.mo3292b();
            objArr[2] = config.mo3293c();
            objArr[3] = config.mo3294d();
            return StringUtils.m10802a(str, objArr);
        } catch (Exception e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: a */
    public static String m10722a(Context context, String str, String str2) {
        return StringUtils.m10802a("|", m10725c(context), str, str2);
    }

    /* renamed from: a */
    public static boolean m10723a(Context context) {
        return ((context.getResources().getConfiguration().screenLayout & 15) == 4) || ((context.getResources().getConfiguration().screenLayout & 15) == 3);
    }

    /* renamed from: b */
    public static String m10724b(Context context) {
        return m10722a(context, BaseSetting.m10464d(context), BaseSetting.m10466e(context));
    }

    @SuppressLint({"HardwareIds"})
    /* renamed from: c */
    public static String m10725c(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }
}
