package com.persianswitch.sdk.base;

import android.content.Context;
import com.persianswitch.sdk.base.db.BaseDatabase;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.preference.IPreference;
import com.persianswitch.sdk.base.preference.SqlitePreference;
import com.persianswitch.sdk.base.preference.SqliteSecurePreference;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class BaseSetting {
    /* renamed from: a */
    private static IPreference f6993a;
    /* renamed from: b */
    private static IPreference f6994b;
    /* renamed from: c */
    private static String f6995c;

    /* renamed from: a */
    public static IPreference m10452a(Context context) {
        if (f6994b == null) {
            String n = m10477n(context);
            if (StringUtils.m10803a(n)) {
                n = SqliteSecurePreference.m10705f();
                m10469f(context, n);
            }
            f6994b = new SqliteSecurePreference(f6995c, n, new BaseDatabase(context), "secure_pref");
        }
        return f6994b;
    }

    /* renamed from: a */
    public static void m10453a(Context context, int i) {
        m10452a(context).mo3256b("host_id", i);
    }

    /* renamed from: a */
    public static void m10454a(Context context, long j) {
        m10452a(context).mo3257b("application_id", j);
    }

    /* renamed from: a */
    public static void m10455a(Context context, String str) {
        m10452a(context).mo3258b("imei", str);
    }

    /* renamed from: a */
    public static void m10456a(Context context, boolean z) {
        m10476m(context).mo3259b("need_re_verification", z);
    }

    /* renamed from: a */
    public static void m10457a(String str) {
        f6995c = str;
        f6993a = null;
        f6994b = null;
    }

    /* renamed from: b */
    public static synchronized long m10458b(Context context) {
        long a;
        synchronized (BaseSetting.class) {
            a = m10476m(context).mo3253a("last_tran_id", 1);
            m10476m(context).mo3257b("last_tran_id", 1 + a);
        }
        return a;
    }

    /* renamed from: b */
    public static void m10459b(Context context, long j) {
        m10476m(context).mo3257b("last_time_cards_synced", j);
    }

    /* renamed from: b */
    public static void m10460b(Context context, String str) {
        m10452a(context).mo3258b("mac", str);
    }

    /* renamed from: c */
    public static long m10461c(Context context) {
        return m10452a(context).mo3253a("application_id", 0);
    }

    /* renamed from: c */
    public static void m10462c(Context context, long j) {
        m10476m(context).mo3257b("server_time_diff", j);
    }

    /* renamed from: c */
    public static void m10463c(Context context, String str) {
        m10452a(context).mo3258b("application_token", str);
    }

    /* renamed from: d */
    public static String m10464d(Context context) {
        return m10452a(context).mo3254a("imei", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: d */
    public static void m10465d(Context context, String str) {
        m10452a(context).mo3258b("mobile_number", str);
    }

    /* renamed from: e */
    public static String m10466e(Context context) {
        return m10452a(context).mo3254a("mac", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: e */
    public static void m10467e(Context context, String str) {
        m10476m(context).mo3258b("language", str);
    }

    /* renamed from: f */
    public static String m10468f(Context context) {
        return m10452a(context).mo3254a("application_token", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: f */
    private static void m10469f(Context context, String str) {
        m10476m(context).mo3258b("salt", str);
    }

    /* renamed from: g */
    public static int m10470g(Context context) {
        return m10452a(context).mo3252a("host_id", 0);
    }

    /* renamed from: h */
    public static String m10471h(Context context) {
        return m10452a(context).mo3254a("mobile_number", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: i */
    public static String m10472i(Context context) {
        return m10476m(context).mo3254a("language", LanguageManager.m10670a());
    }

    /* renamed from: j */
    public static long m10473j(Context context) {
        return m10476m(context).mo3253a("last_time_cards_synced", 0);
    }

    /* renamed from: k */
    public static long m10474k(Context context) {
        return m10476m(context).mo3253a("server_time_diff", 0);
    }

    /* renamed from: l */
    public static boolean m10475l(Context context) {
        return m10476m(context).mo3255a("need_re_verification", false);
    }

    /* renamed from: m */
    private static IPreference m10476m(Context context) {
        if (f6993a == null) {
            f6993a = new SqlitePreference(new BaseDatabase(context), "pref");
        }
        return f6993a;
    }

    /* renamed from: n */
    private static String m10477n(Context context) {
        return m10476m(context).mo3254a("salt", null);
    }
}
