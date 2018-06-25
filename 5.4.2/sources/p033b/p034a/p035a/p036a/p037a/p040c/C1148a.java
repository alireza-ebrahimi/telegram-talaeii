package p033b.p034a.p035a.p036a.p037a.p040c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: b.a.a.a.a.c.a */
public abstract class C1148a<Params, Progress, Result> {
    /* renamed from: a */
    private static final int f3353a = Runtime.getRuntime().availableProcessors();
    /* renamed from: b */
    public static final Executor f3354b = new ThreadPoolExecutor(f3356d, f3357e, 1, TimeUnit.SECONDS, f3359g, f3358f);
    /* renamed from: c */
    public static final Executor f3355c = new C1141c();
    /* renamed from: d */
    private static final int f3356d = (f3353a + 1);
    /* renamed from: e */
    private static final int f3357e = ((f3353a * 2) + 1);
    /* renamed from: f */
    private static final ThreadFactory f3358f = new C11331();
    /* renamed from: g */
    private static final BlockingQueue<Runnable> f3359g = new LinkedBlockingQueue(128);
    /* renamed from: h */
    private static final C1139b f3360h = new C1139b();
    /* renamed from: i */
    private static volatile Executor f3361i = f3355c;
    /* renamed from: j */
    private final C1134e<Params, Result> f3362j = new C11352(this);
    /* renamed from: k */
    private final FutureTask<Result> f3363k = new FutureTask<Result>(this, this.f3362j) {
        /* renamed from: a */
        final /* synthetic */ C1148a f3335a;

        protected void done() {
            try {
                this.f3335a.m6118d(get());
            } catch (Throwable e) {
                Log.w("AsyncTask", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            } catch (CancellationException e3) {
                this.f3335a.m6118d(null);
            }
        }
    };
    /* renamed from: l */
    private volatile C1142d f3364l = C1142d.PENDING;
    /* renamed from: m */
    private final AtomicBoolean f3365m = new AtomicBoolean();
    /* renamed from: n */
    private final AtomicBoolean f3366n = new AtomicBoolean();

    /* renamed from: b.a.a.a.a.c.a$1 */
    static class C11331 implements ThreadFactory {
        /* renamed from: a */
        private final AtomicInteger f3332a = new AtomicInteger(1);

        C11331() {
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AsyncTask #" + this.f3332a.getAndIncrement());
        }
    }

    /* renamed from: b.a.a.a.a.c.a$e */
    private static abstract class C1134e<Params, Result> implements Callable<Result> {
        /* renamed from: b */
        Params[] f3333b;

        private C1134e() {
        }
    }

    /* renamed from: b.a.a.a.a.c.a$2 */
    class C11352 extends C1134e<Params, Result> {
        /* renamed from: a */
        final /* synthetic */ C1148a f3334a;

        C11352(C1148a c1148a) {
            this.f3334a = c1148a;
            super();
        }

        public Result call() {
            this.f3334a.f3366n.set(true);
            Process.setThreadPriority(10);
            return this.f3334a.m6119e(this.f3334a.mo1076a(this.b));
        }
    }

    /* renamed from: b.a.a.a.a.c.a$a */
    private static class C1138a<Data> {
        /* renamed from: a */
        final C1148a f3337a;
        /* renamed from: b */
        final Data[] f3338b;

        C1138a(C1148a c1148a, Data... dataArr) {
            this.f3337a = c1148a;
            this.f3338b = dataArr;
        }
    }

    /* renamed from: b.a.a.a.a.c.a$b */
    private static class C1139b extends Handler {
        public C1139b() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            C1138a c1138a = (C1138a) message.obj;
            switch (message.what) {
                case 1:
                    c1138a.f3337a.m6120f(c1138a.f3338b[0]);
                    return;
                case 2:
                    c1138a.f3337a.m6127b(c1138a.f3338b);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: b.a.a.a.a.c.a$c */
    private static class C1141c implements Executor {
        /* renamed from: a */
        final LinkedList<Runnable> f3341a;
        /* renamed from: b */
        Runnable f3342b;

        private C1141c() {
            this.f3341a = new LinkedList();
        }

        /* renamed from: a */
        protected synchronized void m6108a() {
            Runnable runnable = (Runnable) this.f3341a.poll();
            this.f3342b = runnable;
            if (runnable != null) {
                C1148a.f3354b.execute(this.f3342b);
            }
        }

        public synchronized void execute(final Runnable runnable) {
            this.f3341a.offer(new Runnable(this) {
                /* renamed from: b */
                final /* synthetic */ C1141c f3340b;

                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        this.f3340b.m6108a();
                    }
                }
            });
            if (this.f3342b == null) {
                m6108a();
            }
        }
    }

    /* renamed from: b.a.a.a.a.c.a$d */
    public enum C1142d {
        PENDING,
        RUNNING,
        FINISHED
    }

    /* renamed from: d */
    private void m6118d(Result result) {
        if (!this.f3366n.get()) {
            m6119e(result);
        }
    }

    /* renamed from: e */
    private Result m6119e(Result result) {
        f3360h.obtainMessage(1, new C1138a(this, result)).sendToTarget();
        return result;
    }

    /* renamed from: f */
    private void m6120f(Result result) {
        if (m6128e()) {
            mo1079b((Object) result);
        } else {
            mo1078a((Object) result);
        }
        this.f3364l = C1142d.FINISHED;
    }

    /* renamed from: a */
    public final C1148a<Params, Progress, Result> m6121a(Executor executor, Params... paramsArr) {
        if (this.f3364l != C1142d.PENDING) {
            switch (this.f3364l) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.f3364l = C1142d.RUNNING;
        mo1077a();
        this.f3362j.f3333b = paramsArr;
        executor.execute(this.f3363k);
        return this;
    }

    /* renamed from: a */
    protected abstract Result mo1076a(Params... paramsArr);

    /* renamed from: a */
    protected void mo1077a() {
    }

    /* renamed from: a */
    protected void mo1078a(Result result) {
    }

    /* renamed from: a */
    public final boolean m6125a(boolean z) {
        this.f3365m.set(true);
        return this.f3363k.cancel(z);
    }

    /* renamed from: b */
    protected void mo1079b(Result result) {
        i_();
    }

    /* renamed from: b */
    protected void m6127b(Progress... progressArr) {
    }

    /* renamed from: e */
    public final boolean m6128e() {
        return this.f3365m.get();
    }

    public final C1142d h_() {
        return this.f3364l;
    }

    protected void i_() {
    }
}
