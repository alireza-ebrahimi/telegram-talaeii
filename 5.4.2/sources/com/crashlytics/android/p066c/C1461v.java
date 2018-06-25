package com.crashlytics.android.p066c;

import android.content.Context;
import android.os.Bundle;

/* renamed from: com.crashlytics.android.c.v */
class C1461v implements ak {
    /* renamed from: a */
    private final Context f4407a;
    /* renamed from: b */
    private final String f4408b;

    public C1461v(Context context, String str) {
        this.f4407a = context;
        this.f4408b = str;
    }

    /* renamed from: a */
    public String mo1175a() {
        String str = null;
        try {
            Bundle bundle = this.f4407a.getPackageManager().getApplicationInfo(this.f4408b, 128).metaData;
            if (bundle != null) {
                str = bundle.getString("io.fabric.unity.crashlytics.version");
            }
        } catch (Exception e) {
        }
        return str;
    }
}
