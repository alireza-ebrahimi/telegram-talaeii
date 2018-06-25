package android.support.v4.content;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: android.support.v4.content.m */
abstract class C0426m<Params, Progress, Result> {
    /* renamed from: a */
    public static final Executor f1187a = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, f1189c, f1188b);
    /* renamed from: b */
    private static final ThreadFactory f1188b = new C04251();
    /* renamed from: c */
    private static final BlockingQueue<Runnable> f1189c = new LinkedBlockingQueue(10);
    /* renamed from: d */
    private static volatile Executor f1190d = f1187a;

    /* renamed from: android.support.v4.content.m$1 */
    static class C04251 implements ThreadFactory {
        /* renamed from: a */
        private final AtomicInteger f1186a = new AtomicInteger(1);

        C04251() {
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "ModernAsyncTask #" + this.f1186a.getAndIncrement());
        }
    }
}
