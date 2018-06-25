package com.crashlytics.android.p064a;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.a.l */
class C1350l {
    /* renamed from: a */
    final AtomicReference<ScheduledFuture<?>> f4091a = new AtomicReference();
    /* renamed from: b */
    boolean f4092b = true;
    /* renamed from: c */
    private final ScheduledExecutorService f4093c;
    /* renamed from: d */
    private final List<C1329a> f4094d = new ArrayList();
    /* renamed from: e */
    private volatile boolean f4095e = true;

    /* renamed from: com.crashlytics.android.a.l$a */
    public interface C1329a {
        /* renamed from: a */
        void mo1126a();
    }

    /* renamed from: com.crashlytics.android.a.l$1 */
    class C13491 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1350l f4090a;

        C13491(C1350l c1350l) {
            this.f4090a = c1350l;
        }

        public void run() {
            this.f4090a.f4091a.set(null);
            this.f4090a.m6872c();
        }
    }

    public C1350l(ScheduledExecutorService scheduledExecutorService) {
        this.f4093c = scheduledExecutorService;
    }

    /* renamed from: c */
    private void m6872c() {
        for (C1329a a : this.f4094d) {
            a.mo1126a();
        }
    }

    /* renamed from: a */
    public void m6873a() {
        this.f4092b = false;
        ScheduledFuture scheduledFuture = (ScheduledFuture) this.f4091a.getAndSet(null);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    /* renamed from: a */
    public void m6874a(C1329a c1329a) {
        this.f4094d.add(c1329a);
    }

    /* renamed from: a */
    public void m6875a(boolean z) {
        this.f4095e = z;
    }

    /* renamed from: b */
    public void m6876b() {
        if (this.f4095e && !this.f4092b) {
            this.f4092b = true;
            try {
                this.f4091a.compareAndSet(null, this.f4093c.schedule(new C13491(this), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, TimeUnit.MILLISECONDS));
            } catch (Throwable e) {
                C1230c.m6414h().mo1063a("Answers", "Failed to schedule background detector", e);
            }
        }
    }
}
