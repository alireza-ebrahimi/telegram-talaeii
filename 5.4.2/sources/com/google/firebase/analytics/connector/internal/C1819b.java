package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurement.UserProperty;
import com.google.firebase.analytics.FirebaseAnalytics.C1796a;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.google.firebase.analytics.connector.internal.b */
public final class C1819b {
    /* renamed from: a */
    private static final List<String> f5418a = Arrays.asList(new String[]{"_e", "_f", "_iap", "_s", "_au", "_ui", "_cd", C1796a.APP_OPEN});
    /* renamed from: b */
    private static final List<String> f5419b = Arrays.asList(new String[]{"auto", "app", "am"});
    /* renamed from: c */
    private static final List<String> f5420c = Arrays.asList(new String[]{"_r", "_dbg"});
    /* renamed from: d */
    private static final List<String> f5421d = Arrays.asList((String[]) ArrayUtils.concat(UserProperty.zzadb, UserProperty.zzadc));
    /* renamed from: e */
    private static final List<String> f5422e = Arrays.asList(new String[]{"^_ltv_[A-Z]{3}$", "^_cc[1-5]{1}$"});

    /* renamed from: a */
    public static boolean m8476a(String str) {
        return !f5419b.contains(str);
    }

    /* renamed from: a */
    public static boolean m8477a(String str, Bundle bundle) {
        if (f5418a.contains(str)) {
            return false;
        }
        if (bundle != null) {
            for (String containsKey : f5420c) {
                if (bundle.containsKey(containsKey)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* renamed from: a */
    public static boolean m8478a(String str, String str2, Bundle bundle) {
        if (!"_cmp".equals(str2)) {
            return true;
        }
        if (!C1819b.m8476a(str)) {
            return false;
        }
        if (bundle == null) {
            return false;
        }
        for (String containsKey : f5420c) {
            if (bundle.containsKey(containsKey)) {
                return false;
            }
        }
        Object obj = -1;
        switch (str.hashCode()) {
            case 101200:
                if (str.equals(AppMeasurement.FCM_ORIGIN)) {
                    obj = null;
                    break;
                }
                break;
            case 101230:
                if (str.equals("fdl")) {
                    int i = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                bundle.putString("_cis", "fcm_integration");
                return true;
            case 1:
                bundle.putString("_cis", "fdl_integration");
                return true;
            default:
                return false;
        }
    }

    /* renamed from: b */
    public static boolean m8479b(String str) {
        if (f5421d.contains(str)) {
            return false;
        }
        for (String matches : f5422e) {
            if (str.matches(matches)) {
                return false;
            }
        }
        return true;
    }
}
