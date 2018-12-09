package com.crashlytics.android.p066c;

import java.io.File;
import java.io.InputStream;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1129r;
import p033b.p034a.p035a.p036a.p037a.p039b.C1129r.C1125c;

/* renamed from: com.crashlytics.android.c.ac */
class ac implements C1388s {
    /* renamed from: a */
    private final File f4201a;
    /* renamed from: b */
    private final int f4202b;
    /* renamed from: c */
    private C1129r f4203c;

    public ac(File file, int i) {
        this.f4201a = file;
        this.f4202b = i;
    }

    /* renamed from: d */
    private void m6971d() {
        if (this.f4203c == null) {
            try {
                this.f4203c = new C1129r(this.f4201a);
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("CrashlyticsCore", "Could not open log file: " + this.f4201a, e);
            }
        }
    }

    /* renamed from: a */
    public C1395b mo1149a() {
        if (!this.f4201a.exists()) {
            return null;
        }
        m6971d();
        if (this.f4203c == null) {
            return null;
        }
        final int[] iArr = new int[]{0};
        final byte[] bArr = new byte[this.f4203c.m6097a()];
        try {
            this.f4203c.m6098a(new C1125c(this) {
                /* renamed from: c */
                final /* synthetic */ ac f4200c;

                /* renamed from: a */
                public void mo1023a(InputStream inputStream, int i) {
                    try {
                        inputStream.read(bArr, iArr[0], i);
                        int[] iArr = iArr;
                        iArr[0] = iArr[0] + i;
                    } finally {
                        inputStream.close();
                    }
                }
            });
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("CrashlyticsCore", "A problem occurred while reading the Crashlytics log file.", e);
        }
        return C1395b.m7040a(bArr, 0, iArr[0]);
    }

    /* renamed from: b */
    public void mo1150b() {
        C1110i.m6011a(this.f4203c, "There was a problem closing the Crashlytics log file.");
        this.f4203c = null;
    }

    /* renamed from: c */
    public void mo1151c() {
        mo1150b();
        this.f4201a.delete();
    }
}
