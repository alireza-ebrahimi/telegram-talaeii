package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurement.UserProperty;
import com.google.firebase.C1897b;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import com.google.firebase.analytics.connector.C1814a;

/* renamed from: com.google.firebase.messaging.e */
final class C1961e {
    /* renamed from: a */
    public static void m8930a(Context context, Intent intent) {
        C1961e.m8931a(context, "_nr", intent);
    }

    /* renamed from: a */
    private static void m8931a(Context context, String str, Intent intent) {
        Bundle bundle = new Bundle();
        String stringExtra = intent.getStringExtra("google.c.a.c_id");
        if (stringExtra != null) {
            bundle.putString("_nmid", stringExtra);
        }
        stringExtra = intent.getStringExtra("google.c.a.c_l");
        if (stringExtra != null) {
            bundle.putString("_nmn", stringExtra);
        }
        Object stringExtra2 = intent.getStringExtra("google.c.a.m_l");
        if (!TextUtils.isEmpty(stringExtra2)) {
            bundle.putString("label", stringExtra2);
        }
        stringExtra = intent.getStringExtra("from");
        if (stringExtra == null || !stringExtra.startsWith("/topics/")) {
            stringExtra = null;
        }
        if (stringExtra != null) {
            bundle.putString("_nt", stringExtra);
        }
        try {
            bundle.putInt("_nmt", Integer.parseInt(intent.getStringExtra("google.c.a.ts")));
        } catch (Throwable e) {
            Log.w("FirebaseMessaging", "Error while parsing timestamp in GCM event", e);
        }
        if (intent.hasExtra("google.c.a.udt")) {
            try {
                bundle.putInt("_ndt", Integer.parseInt(intent.getStringExtra("google.c.a.udt")));
            } catch (Throwable e2) {
                Log.w("FirebaseMessaging", "Error while parsing use_device_time in GCM event", e2);
            }
        }
        if (Log.isLoggable("FirebaseMessaging", 3)) {
            String valueOf = String.valueOf(bundle);
            Log.d("FirebaseMessaging", new StringBuilder((String.valueOf(str).length() + 22) + String.valueOf(valueOf).length()).append("Sending event=").append(str).append(" params=").append(valueOf).toString());
        }
        C1814a c1814a = (C1814a) C1897b.m8684d().m8691a(C1814a.class);
        if (c1814a != null) {
            c1814a.mo2993a(AppMeasurement.FCM_ORIGIN, str, bundle);
        } else {
            Log.w("FirebaseMessaging", "Unable to log event: analytics library is missing");
        }
    }

    /* renamed from: b */
    public static void m8932b(Context context, Intent intent) {
        if (intent != null) {
            if ("1".equals(intent.getStringExtra("google.c.a.tc"))) {
                C1814a c1814a = (C1814a) C1897b.m8684d().m8691a(C1814a.class);
                if (Log.isLoggable("FirebaseMessaging", 3)) {
                    Log.d("FirebaseMessaging", "Received event with track-conversion=true. Setting user property and reengagement event");
                }
                if (c1814a != null) {
                    Object stringExtra = intent.getStringExtra("google.c.a.c_id");
                    c1814a.mo2994a(AppMeasurement.FCM_ORIGIN, UserProperty.FIREBASE_LAST_NOTIFICATION, stringExtra);
                    Bundle bundle = new Bundle();
                    bundle.putString(C1797b.SOURCE, "Firebase");
                    bundle.putString(C1797b.MEDIUM, "notification");
                    bundle.putString(C1797b.CAMPAIGN, stringExtra);
                    c1814a.mo2993a(AppMeasurement.FCM_ORIGIN, "_cmp", bundle);
                } else {
                    Log.w("FirebaseMessaging", "Unable to set user property for conversion tracking:  analytics library is missing");
                }
            } else if (Log.isLoggable("FirebaseMessaging", 3)) {
                Log.d("FirebaseMessaging", "Received event with track-conversion=false. Do not set user property");
            }
        }
        C1961e.m8931a(context, "_no", intent);
    }

    /* renamed from: c */
    public static void m8933c(Context context, Intent intent) {
        C1961e.m8931a(context, "_nd", intent);
    }

    /* renamed from: d */
    public static void m8934d(Context context, Intent intent) {
        C1961e.m8931a(context, "_nf", intent);
    }
}
