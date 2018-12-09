package com.crashlytics.android.p064a;

import java.util.Locale;
import java.util.Map;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.a.e */
class C1335e {
    /* renamed from: a */
    final int f4060a;
    /* renamed from: b */
    final int f4061b;
    /* renamed from: c */
    boolean f4062c;

    public C1335e(int i, int i2, boolean z) {
        this.f4060a = i;
        this.f4061b = i2;
        this.f4062c = z;
    }

    /* renamed from: a */
    private void m6829a(RuntimeException runtimeException) {
        if (this.f4062c) {
            throw runtimeException;
        }
        C1230c.m6414h().mo1070e("Answers", "Invalid user input detected", runtimeException);
    }

    /* renamed from: a */
    public String m6830a(String str) {
        if (str.length() <= this.f4061b) {
            return str;
        }
        m6829a(new IllegalArgumentException(String.format(Locale.US, "String is too long, truncating to %d characters", new Object[]{Integer.valueOf(this.f4061b)})));
        return str.substring(0, this.f4061b);
    }

    /* renamed from: a */
    public boolean m6831a(Object obj, String str) {
        if (obj != null) {
            return false;
        }
        m6829a(new NullPointerException(str + " must not be null"));
        return true;
    }

    /* renamed from: a */
    public boolean m6832a(Map<String, Object> map, String str) {
        if (map.size() < this.f4060a || map.containsKey(str)) {
            return false;
        }
        m6829a(new IllegalArgumentException(String.format(Locale.US, "Limit of %d attributes reached, skipping attribute", new Object[]{Integer.valueOf(this.f4060a)})));
        return true;
    }
}
