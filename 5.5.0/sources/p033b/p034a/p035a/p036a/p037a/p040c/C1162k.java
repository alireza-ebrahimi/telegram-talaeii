package p033b.p034a.p035a.p036a.p037a.p040c;

import android.annotation.TargetApi;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: b.a.a.a.a.c.k */
public class C1162k extends ThreadPoolExecutor {
    /* renamed from: a */
    private static final int f3383a = Runtime.getRuntime().availableProcessors();
    /* renamed from: b */
    private static final int f3384b = (f3383a + 1);
    /* renamed from: c */
    private static final int f3385c = ((f3383a * 2) + 1);

    /* renamed from: b.a.a.a.a.c.k$a */
    protected static final class C1161a implements ThreadFactory {
        /* renamed from: a */
        private final int f3382a;

        public C1161a(int i) {
            this.f3382a = i;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setPriority(this.f3382a);
            thread.setName("Queue");
            return thread;
        }
    }

    <T extends Runnable & C1149b & C1154l & C1153i> C1162k(int i, int i2, long j, TimeUnit timeUnit, C1150c<T> c1150c, ThreadFactory threadFactory) {
        super(i, i2, j, timeUnit, c1150c, threadFactory);
        prestartAllCoreThreads();
    }

    /* renamed from: a */
    public static C1162k m6179a() {
        return C1162k.m6180a(f3384b, f3385c);
    }

    /* renamed from: a */
    public static <T extends Runnable & C1149b & C1154l & C1153i> C1162k m6180a(int i, int i2) {
        return new C1162k(i, i2, 1, TimeUnit.SECONDS, new C1150c(), new C1161a(10));
    }

    protected void afterExecute(Runnable runnable, Throwable th) {
        C1154l c1154l = (C1154l) runnable;
        c1154l.mo1028b(true);
        c1154l.mo1026a(th);
        m6181b().m6141d();
        super.afterExecute(runnable, th);
    }

    /* renamed from: b */
    public C1150c m6181b() {
        return (C1150c) super.getQueue();
    }

    @TargetApi(9)
    public void execute(Runnable runnable) {
        if (C1159j.m6170a((Object) runnable)) {
            super.execute(runnable);
        } else {
            super.execute(newTaskFor(runnable, null));
        }
    }

    public /* synthetic */ BlockingQueue getQueue() {
        return m6181b();
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new C1155h(runnable, t);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new C1155h(callable);
    }
}
