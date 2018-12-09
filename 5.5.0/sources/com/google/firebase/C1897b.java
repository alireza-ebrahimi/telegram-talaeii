package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.C0235a;
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.firebase.components.C1902a;
import com.google.firebase.components.C1912k;
import com.google.firebase.components.C1913l;
import com.google.firebase.p106a.C1810c;
import com.google.firebase.p107c.C1822a;
import com.google.firebase.p107c.C1899b;
import com.google.firebase.p107c.C1900c;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.GuardedBy;

/* renamed from: com.google.firebase.b */
public class C1897b {
    @GuardedBy("LOCK")
    /* renamed from: a */
    static final Map<String, C1897b> f5578a = new C0464a();
    /* renamed from: b */
    private static final List<String> f5579b = Arrays.asList(new String[]{"com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId"});
    /* renamed from: c */
    private static final List<String> f5580c = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
    /* renamed from: d */
    private static final List<String> f5581d = Arrays.asList(new String[]{"com.google.android.gms.measurement.AppMeasurement"});
    /* renamed from: e */
    private static final List<String> f5582e = Arrays.asList(new String[0]);
    /* renamed from: f */
    private static final Set<String> f5583f = Collections.emptySet();
    /* renamed from: g */
    private static final Object f5584g = new Object();
    /* renamed from: h */
    private static final Executor f5585h = new C1894d();
    /* renamed from: i */
    private final Context f5586i;
    /* renamed from: j */
    private final String f5587j;
    /* renamed from: k */
    private final C1924f f5588k;
    /* renamed from: l */
    private final C1913l f5589l;
    /* renamed from: m */
    private final SharedPreferences f5590m;
    /* renamed from: n */
    private final C1810c f5591n;
    /* renamed from: o */
    private final AtomicBoolean f5592o = new AtomicBoolean(false);
    /* renamed from: p */
    private final AtomicBoolean f5593p = new AtomicBoolean();
    /* renamed from: q */
    private final AtomicBoolean f5594q;
    /* renamed from: r */
    private final List<C1893b> f5595r = new CopyOnWriteArrayList();
    /* renamed from: s */
    private final List<C1892a> f5596s = new CopyOnWriteArrayList();
    /* renamed from: t */
    private final List<Object> f5597t = new CopyOnWriteArrayList();
    /* renamed from: u */
    private C1822a f5598u;
    /* renamed from: v */
    private C1863c f5599v;

    @KeepForSdk
    /* renamed from: com.google.firebase.b$c */
    public interface C1863c {
        /* renamed from: a */
        void mo3025a(int i);
    }

    /* renamed from: com.google.firebase.b$1 */
    class C18911 implements BackgroundStateChangeListener {
        C18911() {
        }

        public final void onBackgroundStateChanged(boolean z) {
            C1897b.m8682a(z);
        }
    }

    @KeepForSdk
    /* renamed from: com.google.firebase.b$a */
    public interface C1892a {
        /* renamed from: a */
        void m8672a(boolean z);
    }

    @KeepForSdk
    /* renamed from: com.google.firebase.b$b */
    public interface C1893b {
        /* renamed from: a */
        void m8673a(C1899b c1899b);
    }

    /* renamed from: com.google.firebase.b$d */
    static class C1894d implements Executor {
        /* renamed from: a */
        private static final Handler f5575a = new Handler(Looper.getMainLooper());

        private C1894d() {
        }

        public final void execute(Runnable runnable) {
            f5575a.post(runnable);
        }
    }

    @TargetApi(24)
    /* renamed from: com.google.firebase.b$e */
    static class C1895e extends BroadcastReceiver {
        /* renamed from: a */
        private static AtomicReference<C1895e> f5576a = new AtomicReference();
        /* renamed from: b */
        private final Context f5577b;

        private C1895e(Context context) {
            this.f5577b = context;
        }

        /* renamed from: a */
        static /* synthetic */ void m8674a(Context context) {
            if (f5576a.get() == null) {
                BroadcastReceiver c1895e = new C1895e(context);
                if (f5576a.compareAndSet(null, c1895e)) {
                    context.registerReceiver(c1895e, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        public final void onReceive(Context context, Intent intent) {
            synchronized (C1897b.f5584g) {
                for (C1897b a : C1897b.f5578a.values()) {
                    a.m8689k();
                }
            }
            this.f5577b.unregisterReceiver(this);
        }
    }

    protected C1897b(Context context, String str, C1924f c1924f) {
        this.f5586i = (Context) Preconditions.checkNotNull(context);
        this.f5587j = Preconditions.checkNotEmpty(str);
        this.f5588k = (C1924f) Preconditions.checkNotNull(c1924f);
        this.f5599v = new C1900c();
        this.f5590m = context.getSharedPreferences("com.google.firebase.common.prefs", 0);
        this.f5594q = new AtomicBoolean(m8686h());
        Iterable a = new C1912k(context).m8727a();
        this.f5589l = new C1913l(f5585h, a, C1902a.m8707a(context, Context.class, new Class[0]), C1902a.m8707a(this, C1897b.class, new Class[0]), C1902a.m8707a(c1924f, C1924f.class, new Class[0]));
        this.f5591n = (C1810c) this.f5589l.mo3042a(C1810c.class);
    }

    /* renamed from: a */
    public static C1897b m8676a(Context context) {
        C1897b d;
        synchronized (f5584g) {
            if (f5578a.containsKey("[DEFAULT]")) {
                d = C1897b.m8684d();
            } else {
                C1924f a = C1924f.m8751a(context);
                if (a == null) {
                    d = null;
                } else {
                    d = C1897b.m8677a(context, a);
                }
            }
        }
        return d;
    }

    /* renamed from: a */
    public static C1897b m8677a(Context context, C1924f c1924f) {
        return C1897b.m8678a(context, c1924f, "[DEFAULT]");
    }

    /* renamed from: a */
    public static C1897b m8678a(Context context, C1924f c1924f, String str) {
        C1897b c1897b;
        if (PlatformVersion.isAtLeastIceCreamSandwich() && (context.getApplicationContext() instanceof Application)) {
            BackgroundDetector.initialize((Application) context.getApplicationContext());
            BackgroundDetector.getInstance().addListener(new C18911());
        }
        String trim = str.trim();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (f5584g) {
            Preconditions.checkState(!f5578a.containsKey(trim), "FirebaseApp name " + trim + " already exists!");
            Preconditions.checkNotNull(context, "Application context cannot be null.");
            c1897b = new C1897b(context, trim, c1924f);
            f5578a.put(trim, c1897b);
        }
        c1897b.m8689k();
        return c1897b;
    }

    /* renamed from: a */
    public static C1897b m8679a(String str) {
        C1897b c1897b;
        synchronized (f5584g) {
            c1897b = (C1897b) f5578a.get(str.trim());
            if (c1897b != null) {
            } else {
                Iterable j = C1897b.m8688j();
                String str2 = j.isEmpty() ? TtmlNode.ANONYMOUS_REGION_ID : "Available app names: " + TextUtils.join(", ", j);
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", new Object[]{str, str2}));
            }
        }
        return c1897b;
    }

    /* renamed from: a */
    private static <T> void m8681a(Class<T> cls, T t, Iterable<String> iterable, boolean z) {
        for (String str : iterable) {
            if (z) {
                try {
                    if (!f5582e.contains(str)) {
                    }
                } catch (ClassNotFoundException e) {
                    if (f5583f.contains(str)) {
                        throw new IllegalStateException(str + " is missing, but is required. Check if it has been removed by Proguard.");
                    }
                    Log.d("FirebaseApp", str + " is not linked. Skipping initialization.");
                } catch (NoSuchMethodException e2) {
                    throw new IllegalStateException(str + "#getInstance has been removed by Proguard. Add keep rule to prevent it.");
                } catch (Throwable e3) {
                    Log.wtf("FirebaseApp", "Firebase API initialization failure.", e3);
                } catch (Throwable e4) {
                    Log.wtf("FirebaseApp", "Failed to initialize " + str, e4);
                }
            }
            Method method = Class.forName(str).getMethod("getInstance", new Class[]{cls});
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, new Object[]{t});
            }
        }
    }

    /* renamed from: a */
    public static void m8682a(boolean z) {
        synchronized (f5584g) {
            Iterator it = new ArrayList(f5578a.values()).iterator();
            while (it.hasNext()) {
                C1897b c1897b = (C1897b) it.next();
                if (c1897b.f5592o.get()) {
                    c1897b.m8683b(z);
                }
            }
        }
    }

    /* renamed from: b */
    private void m8683b(boolean z) {
        Log.d("FirebaseApp", "Notifying background state change listeners.");
        for (C1892a a : this.f5596s) {
            a.m8672a(z);
        }
    }

    /* renamed from: d */
    public static C1897b m8684d() {
        C1897b c1897b;
        synchronized (f5584g) {
            c1897b = (C1897b) f5578a.get("[DEFAULT]");
            if (c1897b == null) {
                throw new IllegalStateException("Default FirebaseApp is not initialized in this process " + ProcessUtils.getMyProcessName() + ". Make sure to call FirebaseApp.initializeApp(Context) first.");
            }
        }
        return c1897b;
    }

    /* renamed from: h */
    private boolean m8686h() {
        if (this.f5590m.contains("firebase_automatic_data_collection_enabled")) {
            return this.f5590m.getBoolean("firebase_automatic_data_collection_enabled", true);
        }
        try {
            PackageManager packageManager = this.f5586i.getPackageManager();
            if (packageManager == null) {
                return true;
            }
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.f5586i.getPackageName(), 128);
            return (applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("firebase_automatic_data_collection_enabled")) ? true : applicationInfo.metaData.getBoolean("firebase_automatic_data_collection_enabled");
        } catch (NameNotFoundException e) {
            return true;
        }
    }

    /* renamed from: i */
    private void m8687i() {
        Preconditions.checkState(!this.f5593p.get(), "FirebaseApp was deleted");
    }

    /* renamed from: j */
    private static List<String> m8688j() {
        List<String> arrayList = new ArrayList();
        synchronized (f5584g) {
            for (C1897b b : f5578a.values()) {
                arrayList.add(b.m8695b());
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    /* renamed from: k */
    private void m8689k() {
        boolean c = C0235a.m1076c(this.f5586i);
        if (c) {
            C1895e.m8674a(this.f5586i);
        } else {
            this.f5589l.m8731a(m8697e());
        }
        C1897b.m8681a(C1897b.class, this, f5579b, c);
        if (m8697e()) {
            C1897b.m8681a(C1897b.class, this, f5580c, c);
            C1897b.m8681a(Context.class, this.f5586i, f5581d, c);
        }
    }

    /* renamed from: a */
    public Context m8690a() {
        m8687i();
        return this.f5586i;
    }

    /* renamed from: a */
    public <T> T m8691a(Class<T> cls) {
        m8687i();
        return this.f5589l.mo3042a((Class) cls);
    }

    /* renamed from: a */
    public void m8692a(C1863c c1863c) {
        this.f5599v = (C1863c) Preconditions.checkNotNull(c1863c);
        this.f5599v.mo3025a(this.f5595r.size());
    }

    /* renamed from: a */
    public void m8693a(C1822a c1822a) {
        this.f5598u = (C1822a) Preconditions.checkNotNull(c1822a);
    }

    /* renamed from: a */
    public void m8694a(C1899b c1899b) {
        Log.d("FirebaseApp", "Notifying auth state listeners.");
        int i = 0;
        for (C1893b a : this.f5595r) {
            a.m8673a(c1899b);
            i++;
        }
        Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", new Object[]{Integer.valueOf(i)}));
    }

    /* renamed from: b */
    public String m8695b() {
        m8687i();
        return this.f5587j;
    }

    /* renamed from: c */
    public C1924f m8696c() {
        m8687i();
        return this.f5588k;
    }

    /* renamed from: e */
    public boolean m8697e() {
        return "[DEFAULT]".equals(m8695b());
    }

    public boolean equals(Object obj) {
        return !(obj instanceof C1897b) ? false : this.f5587j.equals(((C1897b) obj).m8695b());
    }

    /* renamed from: f */
    public String m8698f() {
        return Base64Utils.encodeUrlSafeNoPadding(m8695b().getBytes()) + "+" + Base64Utils.encodeUrlSafeNoPadding(m8696c().m8753b().getBytes());
    }

    public int hashCode() {
        return this.f5587j.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", this.f5587j).add("options", this.f5588k).toString();
    }
}
