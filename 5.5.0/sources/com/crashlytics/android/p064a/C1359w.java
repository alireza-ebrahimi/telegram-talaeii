package com.crashlytics.android.p064a;

import java.util.Random;
import p033b.p034a.p035a.p036a.p037a.p040c.p041a.C1143a;

/* renamed from: com.crashlytics.android.a.w */
class C1359w implements C1143a {
    /* renamed from: a */
    final C1143a f4119a;
    /* renamed from: b */
    final Random f4120b;
    /* renamed from: c */
    final double f4121c;

    public C1359w(C1143a c1143a, double d) {
        this(c1143a, d, new Random());
    }

    public C1359w(C1143a c1143a, double d, Random random) {
        if (d < 0.0d || d > 1.0d) {
            throw new IllegalArgumentException("jitterPercent must be between 0.0 and 1.0");
        } else if (c1143a == null) {
            throw new NullPointerException("backoff must not be null");
        } else if (random == null) {
            throw new NullPointerException("random must not be null");
        } else {
            this.f4119a = c1143a;
            this.f4121c = d;
            this.f4120b = random;
        }
    }

    /* renamed from: a */
    double m6911a() {
        double d = 1.0d - this.f4121c;
        return d + (((this.f4121c + 1.0d) - d) * this.f4120b.nextDouble());
    }

    /* renamed from: a */
    public long mo1025a(int i) {
        return (long) (m6911a() * ((double) this.f4119a.mo1025a(i)));
    }
}
