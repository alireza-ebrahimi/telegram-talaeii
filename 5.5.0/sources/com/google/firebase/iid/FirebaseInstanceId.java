package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Looper;
import android.support.annotation.Keep;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.C1897b;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.concurrent.GuardedBy;

public class FirebaseInstanceId {
    /* renamed from: a */
    static final Executor f5647a = ah.f5691a;
    /* renamed from: b */
    private static final long f5648b = TimeUnit.HOURS.toSeconds(8);
    /* renamed from: c */
    private static C1946r f5649c;
    /* renamed from: d */
    private static final Executor f5650d = Executors.newCachedThreadPool();
    @GuardedBy("FirebaseInstanceId.class")
    @VisibleForTesting
    /* renamed from: e */
    private static ScheduledThreadPoolExecutor f5651e;
    /* renamed from: f */
    private static final Executor f5652f = new ThreadPoolExecutor(0, 1, 30, TimeUnit.SECONDS, new LinkedBlockingQueue());
    /* renamed from: g */
    private final C1897b f5653g;
    /* renamed from: h */
    private final C1938i f5654h;
    /* renamed from: i */
    private IRpc f5655i;
    /* renamed from: j */
    private final C1941l f5656j;
    /* renamed from: k */
    private final C1950v f5657k;
    @GuardedBy("this")
    /* renamed from: l */
    private boolean f5658l;
    @GuardedBy("this")
    /* renamed from: m */
    private boolean f5659m;

    FirebaseInstanceId(C1897b c1897b) {
        this(c1897b, new C1938i(c1897b.m8690a()));
    }

    private FirebaseInstanceId(C1897b c1897b, C1938i c1938i) {
        this.f5656j = new C1941l();
        this.f5658l = false;
        if (C1938i.m8851a(c1897b) == null) {
            throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
        }
        synchronized (FirebaseInstanceId.class) {
            if (f5649c == null) {
                f5649c = new C1946r(c1897b.m8690a());
            }
        }
        this.f5653g = c1897b;
        this.f5654h = c1938i;
        if (this.f5655i == null) {
            IRpc iRpc = (IRpc) c1897b.m8691a(IRpc.class);
            if (iRpc != null) {
                this.f5655i = iRpc;
            } else {
                this.f5655i = new ai(c1897b, c1938i, f5652f);
            }
        }
        this.f5655i = this.f5655i;
        this.f5657k = new C1950v(f5649c);
        this.f5659m = m8764n();
        if (m8781j()) {
            m8761k();
        }
    }

    /* renamed from: a */
    public static FirebaseInstanceId m8755a() {
        return getInstance(C1897b.m8684d());
    }

    /* renamed from: a */
    private final <T> T m8756a(Task<T> task) {
        try {
            return Tasks.await(task, 30000, TimeUnit.MILLISECONDS);
        } catch (Throwable e) {
            Throwable th = e;
            Throwable e2 = th.getCause();
            if (e2 instanceof IOException) {
                if ("INSTANCE_ID_RESET".equals(e2.getMessage())) {
                    m8779h();
                }
                throw ((IOException) e2);
            } else if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            } else {
                throw new IOException(th);
            }
        } catch (InterruptedException e3) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        } catch (TimeoutException e4) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
    }

    /* renamed from: a */
    static void m8758a(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (f5651e == null) {
                f5651e = new ScheduledThreadPoolExecutor(1);
            }
            f5651e.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    /* renamed from: c */
    private static String m8759c(String str) {
        return (str.isEmpty() || str.equalsIgnoreCase(AppMeasurement.FCM_ORIGIN) || str.equalsIgnoreCase(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) ? "*" : str;
    }

    /* renamed from: g */
    static boolean m8760g() {
        return Log.isLoggable("FirebaseInstanceId", 3) || (VERSION.SDK_INT == 23 && Log.isLoggable("FirebaseInstanceId", 3));
    }

    @Keep
    public static synchronized FirebaseInstanceId getInstance(C1897b c1897b) {
        FirebaseInstanceId firebaseInstanceId;
        synchronized (FirebaseInstanceId.class) {
            firebaseInstanceId = (FirebaseInstanceId) c1897b.m8691a(FirebaseInstanceId.class);
        }
        return firebaseInstanceId;
    }

    /* renamed from: k */
    private final void m8761k() {
        C1947s e = m8777e();
        if (e == null || e.m8887b(this.f5654h.m8855b()) || this.f5657k.m8895a()) {
            m8762l();
        }
    }

    /* renamed from: l */
    private final synchronized void m8762l() {
        if (!this.f5658l) {
            m8768a(0);
        }
    }

    /* renamed from: m */
    private static String m8763m() {
        return C1938i.m8852a(f5649c.m8882b(TtmlNode.ANONYMOUS_REGION_ID).m8825a());
    }

    /* renamed from: n */
    private final boolean m8764n() {
        Context a = this.f5653g.m8690a();
        SharedPreferences sharedPreferences = a.getSharedPreferences("com.google.firebase.messaging", 0);
        if (sharedPreferences.contains("auto_init")) {
            return sharedPreferences.getBoolean("auto_init", true);
        }
        try {
            PackageManager packageManager = a.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(a.getPackageName(), 128);
                if (!(applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("firebase_messaging_auto_init_enabled"))) {
                    return applicationInfo.metaData.getBoolean("firebase_messaging_auto_init_enabled");
                }
            }
        } catch (NameNotFoundException e) {
        }
        return m8765o();
    }

    /* renamed from: o */
    private final boolean m8765o() {
        try {
            Class.forName("com.google.firebase.messaging.a");
            return true;
        } catch (ClassNotFoundException e) {
            Context a = this.f5653g.m8690a();
            Intent intent = new Intent("com.google.firebase.MESSAGING_EVENT");
            intent.setPackage(a.getPackageName());
            ResolveInfo resolveService = a.getPackageManager().resolveService(intent, 0);
            return (resolveService == null || resolveService.serviceInfo == null) ? false : true;
        }
    }

    /* renamed from: a */
    final /* synthetic */ Task m8766a(String str, String str2, String str3) {
        return this.f5655i.getToken(str, str2, str3);
    }

    /* renamed from: a */
    public String m8767a(String str, String str2) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        String c = m8759c(str2);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        f5650d.execute(new ae(this, str, str2, taskCompletionSource, c));
        return (String) m8756a(taskCompletionSource.getTask());
    }

    /* renamed from: a */
    final synchronized void m8768a(long j) {
        m8758a(new C1948t(this, this.f5654h, this.f5657k, Math.min(Math.max(30, j << 1), f5648b)), j);
        this.f5658l = true;
    }

    /* renamed from: a */
    final void m8769a(String str) {
        C1947s e = m8777e();
        if (e == null || e.m8887b(this.f5654h.m8855b())) {
            throw new IOException("token not available");
        }
        m8756a(this.f5655i.subscribeToTopic(m8763m(), e.f5754a, str));
    }

    /* renamed from: a */
    final /* synthetic */ void m8770a(String str, String str2, TaskCompletionSource taskCompletionSource, Task task) {
        if (task.isSuccessful()) {
            String str3 = (String) task.getResult();
            f5649c.m8881a(TtmlNode.ANONYMOUS_REGION_ID, str, str2, str3, this.f5654h.m8855b());
            taskCompletionSource.setResult(str3);
            return;
        }
        taskCompletionSource.setException(task.getException());
    }

    /* renamed from: a */
    final /* synthetic */ void m8771a(String str, String str2, TaskCompletionSource taskCompletionSource, String str3) {
        C1947s a = f5649c.m8878a(TtmlNode.ANONYMOUS_REGION_ID, str, str2);
        if (a == null || a.m8887b(this.f5654h.m8855b())) {
            this.f5656j.m8861a(str, str3, new af(this, m8763m(), str, str3)).addOnCompleteListener(f5650d, new ag(this, str, str3, taskCompletionSource));
            return;
        }
        taskCompletionSource.setResult(a.f5754a);
    }

    /* renamed from: a */
    final synchronized void m8772a(boolean z) {
        this.f5658l = z;
    }

    /* renamed from: b */
    final C1897b m8773b() {
        return this.f5653g;
    }

    /* renamed from: b */
    final void m8774b(String str) {
        C1947s e = m8777e();
        if (e == null || e.m8887b(this.f5654h.m8855b())) {
            throw new IOException("token not available");
        }
        m8756a(this.f5655i.unsubscribeFromTopic(m8763m(), e.f5754a, str));
    }

    /* renamed from: c */
    public String m8775c() {
        m8761k();
        return m8763m();
    }

    /* renamed from: d */
    public String m8776d() {
        C1947s e = m8777e();
        if (e == null || e.m8887b(this.f5654h.m8855b())) {
            m8762l();
        }
        return e != null ? e.f5754a : null;
    }

    /* renamed from: e */
    final C1947s m8777e() {
        return f5649c.m8878a(TtmlNode.ANONYMOUS_REGION_ID, C1938i.m8851a(this.f5653g), "*");
    }

    /* renamed from: f */
    final String m8778f() {
        return m8767a(C1938i.m8851a(this.f5653g), "*");
    }

    /* renamed from: h */
    final synchronized void m8779h() {
        f5649c.m8883b();
        if (m8781j()) {
            m8762l();
        }
    }

    /* renamed from: i */
    final void m8780i() {
        f5649c.m8884c(TtmlNode.ANONYMOUS_REGION_ID);
        m8762l();
    }

    @VisibleForTesting
    /* renamed from: j */
    public final synchronized boolean m8781j() {
        return this.f5659m;
    }
}
