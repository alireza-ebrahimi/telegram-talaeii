package com.google.firebase.analytics.connector;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.connector.internal.C1819b;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.google.firebase.analytics.connector.b */
public class C1815b implements C1814a {
    /* renamed from: b */
    private static volatile C1814a f5414b;
    /* renamed from: a */
    private final AppMeasurement f5415a;
    @VisibleForTesting
    /* renamed from: c */
    private final Map<String, Object> f5416c = new ConcurrentHashMap();

    private C1815b(AppMeasurement appMeasurement) {
        Preconditions.checkNotNull(appMeasurement);
        this.f5415a = appMeasurement;
    }

    @KeepForSdk
    /* renamed from: a */
    public static C1814a m8471a(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (f5414b == null) {
            synchronized (C1814a.class) {
                if (f5414b == null) {
                    f5414b = new C1815b(AppMeasurement.getInstance(context));
                }
            }
        }
        return f5414b;
    }

    @KeepForSdk
    /* renamed from: a */
    public void mo2993a(String str, String str2, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (C1819b.m8476a(str) && C1819b.m8477a(str2, bundle) && C1819b.m8478a(str, str2, bundle)) {
            this.f5415a.logEventInternal(str, str2, bundle);
        }
    }

    @KeepForSdk
    /* renamed from: a */
    public void mo2994a(String str, String str2, Object obj) {
        if (!C1819b.m8476a(str) || !C1819b.m8479b(str2)) {
            return;
        }
        if ((!str2.equals("_ce1") && !str2.equals("_ce2")) || str.equals(AppMeasurement.FCM_ORIGIN) || str.equals("frc")) {
            this.f5415a.setUserPropertyInternal(str, str2, obj);
        }
    }
}
