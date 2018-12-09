package com.persianswitch.sdk.payment;

import android.content.Context;
import com.persianswitch.sdk.base.preference.IPreference;
import com.persianswitch.sdk.base.preference.SqlitePreference;
import com.persianswitch.sdk.base.preference.SqliteSecurePreference;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.payment.database.SDKDatabase;

public final class SDKSetting {
    /* renamed from: a */
    private static SqlitePreference f7370a;
    /* renamed from: b */
    private static SqlitePreference f7371b;
    /* renamed from: c */
    private static String f7372c;

    /* renamed from: a */
    public static IPreference m11037a(Context context) {
        if (f7371b == null) {
            String e = m11045e(context);
            if (StringUtils.m10803a(e)) {
                e = SqliteSecurePreference.m10705f();
                m11038a(context, e);
            }
            f7371b = new SqliteSecurePreference(f7372c, e, new SDKDatabase(context), "secure_pref");
        }
        return f7371b;
    }

    /* renamed from: a */
    private static void m11038a(Context context, String str) {
        m11044d(context).mo3258b("salt", str);
    }

    /* renamed from: a */
    public static void m11039a(Context context, boolean z) {
        m11037a(context).mo3259b("rootWarningShowed", z);
    }

    /* renamed from: a */
    public static void m11040a(String str) {
        f7372c = str;
        f7370a = null;
        f7371b = null;
    }

    /* renamed from: b */
    public static void m11041b(Context context, boolean z) {
        m11037a(context).mo3259b("numberValidatedIsShowed", z);
    }

    /* renamed from: b */
    public static boolean m11042b(Context context) {
        return m11037a(context).mo3255a("rootWarningShowed", false);
    }

    /* renamed from: c */
    public static boolean m11043c(Context context) {
        return m11037a(context).mo3255a("numberValidatedIsShowed", false);
    }

    /* renamed from: d */
    private static IPreference m11044d(Context context) {
        if (f7370a == null) {
            f7370a = new SqlitePreference(new SDKDatabase(context), "pref");
        }
        return f7370a;
    }

    /* renamed from: e */
    private static String m11045e(Context context) {
        return m11044d(context).mo3254a("salt", null);
    }
}
