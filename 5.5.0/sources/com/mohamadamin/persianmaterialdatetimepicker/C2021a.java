package com.mohamadamin.persianmaterialdatetimepicker;

import android.content.Context;
import android.database.ContentObserver;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings.System;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.a */
public class C2021a {
    /* renamed from: a */
    private final Context f5946a;
    /* renamed from: b */
    private final ContentObserver f5947b = new ContentObserver(this, null) {
        /* renamed from: a */
        final /* synthetic */ C2021a f5939a;

        public void onChange(boolean z) {
            this.f5939a.f5949d = C2021a.m9109b(this.f5939a.f5946a);
        }
    };
    /* renamed from: c */
    private Vibrator f5948c;
    /* renamed from: d */
    private boolean f5949d;
    /* renamed from: e */
    private long f5950e;

    public C2021a(Context context) {
        this.f5946a = context;
    }

    /* renamed from: b */
    private static boolean m9109b(Context context) {
        return System.getInt(context.getContentResolver(), "haptic_feedback_enabled", 0) == 1;
    }

    /* renamed from: a */
    public void m9110a() {
        this.f5948c = (Vibrator) this.f5946a.getSystemService("vibrator");
        this.f5949d = C2021a.m9109b(this.f5946a);
        this.f5946a.getContentResolver().registerContentObserver(System.getUriFor("haptic_feedback_enabled"), false, this.f5947b);
    }

    /* renamed from: b */
    public void m9111b() {
        this.f5948c = null;
        this.f5946a.getContentResolver().unregisterContentObserver(this.f5947b);
    }

    /* renamed from: c */
    public void m9112c() {
        if (this.f5948c != null && this.f5949d) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis - this.f5950e >= 125) {
                this.f5948c.vibrate(5);
                this.f5950e = uptimeMillis;
            }
        }
    }
}
