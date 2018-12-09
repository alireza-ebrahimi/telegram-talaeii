package p033b.p034a.p035a.p036a.p037a.p039b;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: b.a.a.a.a.b.n */
public final class C1119n {
    /* renamed from: a */
    public static ExecutorService m6044a(String str) {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor(C1119n.m6048c(str));
        C1119n.m6045a(str, newSingleThreadExecutor);
        return newSingleThreadExecutor;
    }

    /* renamed from: a */
    private static final void m6045a(String str, ExecutorService executorService) {
        C1119n.m6046a(str, executorService, 2, TimeUnit.SECONDS);
    }

    /* renamed from: a */
    public static final void m6046a(String str, ExecutorService executorService, long j, TimeUnit timeUnit) {
        final String str2 = str;
        final ExecutorService executorService2 = executorService;
        final long j2 = j;
        final TimeUnit timeUnit2 = timeUnit;
        Runtime.getRuntime().addShutdownHook(new Thread(new C1098h() {
            /* renamed from: a */
            public void mo1020a() {
                try {
                    C1230c.m6414h().mo1062a("Fabric", "Executing shutdown hook for " + str2);
                    executorService2.shutdown();
                    if (!executorService2.awaitTermination(j2, timeUnit2)) {
                        C1230c.m6414h().mo1062a("Fabric", str2 + " did not shut down in the allocated time. Requesting immediate shutdown.");
                        executorService2.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    C1230c.m6414h().mo1062a("Fabric", String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[]{str2}));
                    executorService2.shutdownNow();
                }
            }
        }, "Crashlytics Shutdown Hook for " + str));
    }

    /* renamed from: b */
    public static ScheduledExecutorService m6047b(String str) {
        Object newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(C1119n.m6048c(str));
        C1119n.m6045a(str, newSingleThreadScheduledExecutor);
        return newSingleThreadScheduledExecutor;
    }

    /* renamed from: c */
    public static final ThreadFactory m6048c(final String str) {
        final AtomicLong atomicLong = new AtomicLong(1);
        return new ThreadFactory() {
            public Thread newThread(final Runnable runnable) {
                Thread newThread = Executors.defaultThreadFactory().newThread(new C1098h(this) {
                    /* renamed from: b */
                    final /* synthetic */ C11171 f3278b;

                    /* renamed from: a */
                    public void mo1020a() {
                        runnable.run();
                    }
                });
                newThread.setName(str + atomicLong.getAndIncrement());
                return newThread;
            }
        };
    }
}
