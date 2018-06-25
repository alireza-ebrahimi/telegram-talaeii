package com.google.firebase.auth.internal;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.firebase.C1897b;
import com.google.firebase.C1897b.C1863c;

/* renamed from: com.google.firebase.auth.internal.e */
public final class C1864e implements C1863c {
    /* renamed from: a */
    private volatile int f5519a;
    /* renamed from: b */
    private volatile int f5520b;
    /* renamed from: c */
    private final C1875o f5521c;
    /* renamed from: d */
    private volatile boolean f5522d;

    @VisibleForTesting
    private C1864e(Context context, C1875o c1875o) {
        this.f5522d = false;
        this.f5519a = 0;
        this.f5520b = 0;
        this.f5521c = c1875o;
        BackgroundDetector.initialize((Application) context.getApplicationContext());
        BackgroundDetector.getInstance().addListener(new C1865f(this));
    }

    public C1864e(C1897b c1897b) {
        this(c1897b.m8690a(), new C1875o(c1897b));
    }

    /* renamed from: b */
    private final boolean m8619b() {
        return this.f5519a + this.f5520b > 0 && !this.f5522d;
    }

    /* renamed from: a */
    public final void m8620a() {
        this.f5521c.m8631c();
    }

    /* renamed from: a */
    public final void mo3025a(int i) {
        if (i > 0 && this.f5519a == 0 && this.f5520b == 0) {
            this.f5519a = i;
            if (m8619b()) {
                this.f5521c.m8629a();
            }
        } else if (i == 0 && this.f5519a != 0 && this.f5520b == 0) {
            this.f5521c.m8631c();
        }
        this.f5519a = i;
    }

    /* renamed from: a */
    public final void m8622a(zzao zzao) {
        if (zzao != null) {
            long zzaq = zzao.zzaq();
            if (zzaq <= 0) {
                zzaq = 3600;
            }
            zzaq = (zzaq * 1000) + zzao.zzay();
            C1875o c1875o = this.f5521c;
            c1875o.f5529a = zzaq;
            c1875o.f5530b = -1;
            if (m8619b()) {
                this.f5521c.m8629a();
            }
        }
    }
}
