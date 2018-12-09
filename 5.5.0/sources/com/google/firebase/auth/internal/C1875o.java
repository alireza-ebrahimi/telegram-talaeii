package com.google.firebase.auth.internal;

import android.os.Handler;
import android.os.HandlerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.firebase.C1897b;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

/* renamed from: com.google.firebase.auth.internal.o */
public final class C1875o {
    /* renamed from: c */
    private static Logger f5528c = new Logger("TokenRefresher", "FirebaseAuth:");
    @VisibleForTesting
    /* renamed from: a */
    volatile long f5529a;
    @VisibleForTesting
    /* renamed from: b */
    volatile long f5530b;
    /* renamed from: d */
    private final C1897b f5531d;
    @VisibleForTesting
    /* renamed from: e */
    private long f5532e;
    @VisibleForTesting
    /* renamed from: f */
    private HandlerThread f5533f = new HandlerThread("TokenRefresher", 10);
    @VisibleForTesting
    /* renamed from: g */
    private Handler f5534g;
    @VisibleForTesting
    /* renamed from: h */
    private Runnable f5535h;

    public C1875o(C1897b c1897b) {
        f5528c.m8461v("Initializing TokenRefresher", new Object[0]);
        this.f5531d = (C1897b) Preconditions.checkNotNull(c1897b);
        this.f5533f.start();
        this.f5534g = new Handler(this.f5533f.getLooper());
        this.f5535h = new C1876p(this, this.f5531d.m8695b());
        this.f5532e = 300000;
    }

    /* renamed from: a */
    public final void m8629a() {
        f5528c.m8461v("Scheduling refresh for " + (this.f5529a - this.f5532e), new Object[0]);
        m8631c();
        this.f5530b = Math.max((this.f5529a - DefaultClock.getInstance().currentTimeMillis()) - this.f5532e, 0) / 1000;
        this.f5534g.postDelayed(this.f5535h, this.f5530b * 1000);
    }

    /* renamed from: b */
    final void m8630b() {
        long j;
        switch ((int) this.f5530b) {
            case 30:
            case 60:
            case 120:
            case PsExtractor.VIDEO_STREAM_MASK /*240*/:
            case 480:
                j = 2 * this.f5530b;
                break;
            case 960:
                j = 960;
                break;
            default:
                j = 30;
                break;
        }
        this.f5530b = j;
        this.f5529a = DefaultClock.getInstance().currentTimeMillis() + (this.f5530b * 1000);
        f5528c.m8461v("Scheduling refresh for " + this.f5529a, new Object[0]);
        this.f5534g.postDelayed(this.f5535h, this.f5530b * 1000);
    }

    /* renamed from: c */
    public final void m8631c() {
        this.f5534g.removeCallbacks(this.f5535h);
    }
}
