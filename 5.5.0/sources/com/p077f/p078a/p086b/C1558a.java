package com.p077f.p078a.p086b;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.p077f.p078a.p079a.p080a.C1526a;
import com.p077f.p078a.p079a.p080a.p081a.C1532b;
import com.p077f.p078a.p079a.p080a.p081a.p082a.C1527b;
import com.p077f.p078a.p079a.p080a.p083b.C1533a;
import com.p077f.p078a.p079a.p080a.p083b.C1534b;
import com.p077f.p078a.p079a.p084b.C1536a;
import com.p077f.p078a.p079a.p084b.p085a.C1538b;
import com.p077f.p078a.p086b.p087a.C1555g;
import com.p077f.p078a.p086b.p087a.p088a.C1543c;
import com.p077f.p078a.p086b.p089b.C1561b;
import com.p077f.p078a.p086b.p089b.C1562a;
import com.p077f.p078a.p086b.p090c.C1567a;
import com.p077f.p078a.p086b.p090c.C1569c;
import com.p077f.p078a.p086b.p091d.C1572b;
import com.p077f.p078a.p086b.p091d.C1573a;
import com.p077f.p078a.p095c.C1602c;
import com.p077f.p078a.p095c.C1605e;
import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;

/* renamed from: com.f.a.b.a */
public class C1558a {

    /* renamed from: com.f.a.b.a$a */
    private static class C1539a implements ThreadFactory {
        /* renamed from: a */
        private static final AtomicInteger f4672a = new AtomicInteger(1);
        /* renamed from: b */
        private final ThreadGroup f4673b;
        /* renamed from: c */
        private final AtomicInteger f4674c = new AtomicInteger(1);
        /* renamed from: d */
        private final String f4675d;
        /* renamed from: e */
        private final int f4676e;

        C1539a(int i, String str) {
            this.f4676e = i;
            this.f4673b = Thread.currentThread().getThreadGroup();
            this.f4675d = str + f4672a.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.f4673b, runnable, this.f4675d + this.f4674c.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            thread.setPriority(this.f4676e);
            return thread;
        }
    }

    @TargetApi(11)
    /* renamed from: a */
    private static int m7678a(ActivityManager activityManager) {
        return activityManager.getLargeMemoryClass();
    }

    /* renamed from: a */
    public static C1526a m7679a(Context context, C1533a c1533a, long j, int i) {
        File b = C1558a.m7687b(context);
        if (j > 0 || i > 0) {
            try {
                return new C1527b(C1605e.m7949b(context), b, c1533a, j, i);
            } catch (Throwable e) {
                C1602c.m7937a(e);
            }
        }
        return new C1532b(C1605e.m7946a(context), b, c1533a);
    }

    /* renamed from: a */
    public static C1536a m7680a(Context context, int i) {
        if (i == 0) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int a = (C1558a.m7690d() && C1558a.m7689c(context)) ? C1558a.m7678a(activityManager) : activityManager.getMemoryClass();
            i = (a * ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES) / 8;
        }
        return new C1538b(i);
    }

    /* renamed from: a */
    public static C1561b m7681a(boolean z) {
        return new C1562a(z);
    }

    /* renamed from: a */
    public static C1572b m7682a(Context context) {
        return new C1573a(context);
    }

    /* renamed from: a */
    public static Executor m7683a() {
        return Executors.newCachedThreadPool(C1558a.m7685a(5, "uil-pool-d-"));
    }

    /* renamed from: a */
    public static Executor m7684a(int i, int i2, C1555g c1555g) {
        return new ThreadPoolExecutor(i, i, 0, TimeUnit.MILLISECONDS, (c1555g == C1555g.LIFO ? 1 : null) != null ? new C1543c() : new LinkedBlockingQueue(), C1558a.m7685a(i2, "uil-pool-"));
    }

    /* renamed from: a */
    private static ThreadFactory m7685a(int i, String str) {
        return new C1539a(i, str);
    }

    /* renamed from: b */
    public static C1533a m7686b() {
        return new C1534b();
    }

    /* renamed from: b */
    private static File m7687b(Context context) {
        File a = C1605e.m7948a(context, false);
        File file = new File(a, "uil-images");
        return (file.exists() || file.mkdir()) ? file : a;
    }

    /* renamed from: c */
    public static C1567a m7688c() {
        return new C1569c();
    }

    @TargetApi(11)
    /* renamed from: c */
    private static boolean m7689c(Context context) {
        return (context.getApplicationInfo().flags & ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES) != 0;
    }

    /* renamed from: d */
    private static boolean m7690d() {
        return VERSION.SDK_INT >= 11;
    }
}
