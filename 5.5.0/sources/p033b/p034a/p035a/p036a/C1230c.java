package p033b.p034a.p035a.p036a;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import p033b.p034a.p035a.p036a.C1223a.C1091b;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p;
import p033b.p034a.p035a.p036a.p037a.p040c.C1151d;
import p033b.p034a.p035a.p036a.p037a.p040c.C1162k;
import p033b.p034a.p035a.p036a.p037a.p040c.C1163m;

/* renamed from: b.a.a.a.c */
public class C1230c {
    /* renamed from: a */
    static volatile C1230c f3559a;
    /* renamed from: b */
    static final C1224l f3560b = new C1225b();
    /* renamed from: c */
    final C1224l f3561c;
    /* renamed from: d */
    final boolean f3562d;
    /* renamed from: e */
    private final Context f3563e;
    /* renamed from: f */
    private final Map<Class<? extends C1237i>, C1237i> f3564f;
    /* renamed from: g */
    private final ExecutorService f3565g;
    /* renamed from: h */
    private final Handler f3566h;
    /* renamed from: i */
    private final C1227f<C1230c> f3567i;
    /* renamed from: j */
    private final C1227f<?> f3568j;
    /* renamed from: k */
    private final C1122p f3569k;
    /* renamed from: l */
    private C1223a f3570l;
    /* renamed from: m */
    private WeakReference<Activity> f3571m;
    /* renamed from: n */
    private AtomicBoolean f3572n = new AtomicBoolean(false);

    /* renamed from: b.a.a.a.c$1 */
    class C12261 extends C1091b {
        /* renamed from: a */
        final /* synthetic */ C1230c f3545a;

        C12261(C1230c c1230c) {
            this.f3545a = c1230c;
        }

        /* renamed from: a */
        public void mo1071a(Activity activity) {
            this.f3545a.m6417a(activity);
        }

        /* renamed from: a */
        public void mo1072a(Activity activity, Bundle bundle) {
            this.f3545a.m6417a(activity);
        }

        /* renamed from: b */
        public void mo1073b(Activity activity) {
            this.f3545a.m6417a(activity);
        }
    }

    /* renamed from: b.a.a.a.c$a */
    public static class C1229a {
        /* renamed from: a */
        private final Context f3550a;
        /* renamed from: b */
        private C1237i[] f3551b;
        /* renamed from: c */
        private C1162k f3552c;
        /* renamed from: d */
        private Handler f3553d;
        /* renamed from: e */
        private C1224l f3554e;
        /* renamed from: f */
        private boolean f3555f;
        /* renamed from: g */
        private String f3556g;
        /* renamed from: h */
        private String f3557h;
        /* renamed from: i */
        private C1227f<C1230c> f3558i;

        public C1229a(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.f3550a = context;
        }

        /* renamed from: a */
        public C1229a m6401a(C1237i... c1237iArr) {
            if (this.f3551b != null) {
                throw new IllegalStateException("Kits already set.");
            }
            this.f3551b = c1237iArr;
            return this;
        }

        /* renamed from: a */
        public C1230c m6402a() {
            if (this.f3552c == null) {
                this.f3552c = C1162k.m6179a();
            }
            if (this.f3553d == null) {
                this.f3553d = new Handler(Looper.getMainLooper());
            }
            if (this.f3554e == null) {
                if (this.f3555f) {
                    this.f3554e = new C1225b(3);
                } else {
                    this.f3554e = new C1225b();
                }
            }
            if (this.f3557h == null) {
                this.f3557h = this.f3550a.getPackageName();
            }
            if (this.f3558i == null) {
                this.f3558i = C1227f.f3546d;
            }
            Map hashMap = this.f3551b == null ? new HashMap() : C1230c.m6410b(Arrays.asList(this.f3551b));
            Context applicationContext = this.f3550a.getApplicationContext();
            return new C1230c(applicationContext, hashMap, this.f3552c, this.f3553d, this.f3554e, this.f3555f, this.f3558i, new C1122p(applicationContext, this.f3557h, this.f3556g, hashMap.values()), C1230c.m6413d(this.f3550a));
        }
    }

    C1230c(Context context, Map<Class<? extends C1237i>, C1237i> map, C1162k c1162k, Handler handler, C1224l c1224l, boolean z, C1227f c1227f, C1122p c1122p, Activity activity) {
        this.f3563e = context;
        this.f3564f = map;
        this.f3565g = c1162k;
        this.f3566h = handler;
        this.f3561c = c1224l;
        this.f3562d = z;
        this.f3567i = c1227f;
        this.f3568j = m6418a(map.size());
        this.f3569k = c1122p;
        m6417a(activity);
    }

    /* renamed from: a */
    static C1230c m6403a() {
        if (f3559a != null) {
            return f3559a;
        }
        throw new IllegalStateException("Must Initialize Fabric before using singleton()");
    }

    /* renamed from: a */
    public static C1230c m6404a(Context context, C1237i... c1237iArr) {
        if (f3559a == null) {
            synchronized (C1230c.class) {
                if (f3559a == null) {
                    C1230c.m6412c(new C1229a(context).m6401a(c1237iArr).m6402a());
                }
            }
        }
        return f3559a;
    }

    /* renamed from: a */
    public static <T extends C1237i> T m6405a(Class<T> cls) {
        return (C1237i) C1230c.m6403a().f3564f.get(cls);
    }

    /* renamed from: a */
    private static void m6408a(Map<Class<? extends C1237i>, C1237i> map, Collection<? extends C1237i> collection) {
        for (C1237i c1237i : collection) {
            map.put(c1237i.getClass(), c1237i);
            if (c1237i instanceof C1238j) {
                C1230c.m6408a((Map) map, ((C1238j) c1237i).mo1145c());
            }
        }
    }

    /* renamed from: b */
    private static Map<Class<? extends C1237i>, C1237i> m6410b(Collection<? extends C1237i> collection) {
        Map hashMap = new HashMap(collection.size());
        C1230c.m6408a(hashMap, (Collection) collection);
        return hashMap;
    }

    /* renamed from: c */
    private static void m6412c(C1230c c1230c) {
        f3559a = c1230c;
        c1230c.m6416j();
    }

    /* renamed from: d */
    private static Activity m6413d(Context context) {
        return context instanceof Activity ? (Activity) context : null;
    }

    /* renamed from: h */
    public static C1224l m6414h() {
        return f3559a == null ? f3560b : f3559a.f3561c;
    }

    /* renamed from: i */
    public static boolean m6415i() {
        return f3559a == null ? false : f3559a.f3562d;
    }

    /* renamed from: j */
    private void m6416j() {
        this.f3570l = new C1223a(this.f3563e);
        this.f3570l.m6370a(new C12261(this));
        m6419a(this.f3563e);
    }

    /* renamed from: a */
    public C1230c m6417a(Activity activity) {
        this.f3571m = new WeakReference(activity);
        return this;
    }

    /* renamed from: a */
    C1227f<?> m6418a(final int i) {
        return new C1227f(this) {
            /* renamed from: a */
            final CountDownLatch f3547a = new CountDownLatch(i);
            /* renamed from: c */
            final /* synthetic */ C1230c f3549c;

            /* renamed from: a */
            public void mo1074a(Exception exception) {
                this.f3549c.f3567i.mo1074a(exception);
            }

            /* renamed from: a */
            public void mo1075a(Object obj) {
                this.f3547a.countDown();
                if (this.f3547a.getCount() == 0) {
                    this.f3549c.f3572n.set(true);
                    this.f3549c.f3567i.mo1075a(this.f3549c);
                }
            }
        };
    }

    /* renamed from: a */
    void m6419a(Context context) {
        Future b = m6422b(context);
        Collection g = m6427g();
        C1240m c1240m = new C1240m(b, g);
        List<C1237i> arrayList = new ArrayList(g);
        Collections.sort(arrayList);
        c1240m.m6444a(context, this, C1227f.f3546d, this.f3569k);
        for (C1237i a : arrayList) {
            a.m6444a(context, this, this.f3568j, this.f3569k);
        }
        c1240m.m6450o();
        StringBuilder append = C1230c.m6414h().mo1064a("Fabric", 3) ? new StringBuilder("Initializing ").append(m6424d()).append(" [Version: ").append(m6423c()).append("], with the following kits:\n") : null;
        for (C1237i a2 : arrayList) {
            a2.f3578f.m6160a(c1240m.f);
            m6420a(this.f3564f, a2);
            a2.m6450o();
            if (append != null) {
                append.append(a2.mo1081b()).append(" [Version: ").append(a2.mo1080a()).append("]\n");
            }
        }
        if (append != null) {
            C1230c.m6414h().mo1062a("Fabric", append.toString());
        }
    }

    /* renamed from: a */
    void m6420a(Map<Class<? extends C1237i>, C1237i> map, C1237i c1237i) {
        C1151d c1151d = c1237i.f3582j;
        if (c1151d != null) {
            for (Class cls : c1151d.m6142a()) {
                if (cls.isInterface()) {
                    for (C1237i c1237i2 : map.values()) {
                        if (cls.isAssignableFrom(c1237i2.getClass())) {
                            c1237i.f3578f.m6160a(c1237i2.f3578f);
                        }
                    }
                } else if (((C1237i) map.get(cls)) == null) {
                    throw new C1163m("Referenced Kit was null, does the kit exist?");
                } else {
                    c1237i.f3578f.m6160a(((C1237i) map.get(cls)).f3578f);
                }
            }
        }
    }

    /* renamed from: b */
    public Activity m6421b() {
        return this.f3571m != null ? (Activity) this.f3571m.get() : null;
    }

    /* renamed from: b */
    Future<Map<String, C1239k>> m6422b(Context context) {
        return m6426f().submit(new C1232e(context.getPackageCodePath()));
    }

    /* renamed from: c */
    public String m6423c() {
        return "1.4.1.19";
    }

    /* renamed from: d */
    public String m6424d() {
        return "io.fabric.sdk.android:fabric";
    }

    /* renamed from: e */
    public C1223a m6425e() {
        return this.f3570l;
    }

    /* renamed from: f */
    public ExecutorService m6426f() {
        return this.f3565g;
    }

    /* renamed from: g */
    public Collection<C1237i> m6427g() {
        return this.f3564f.values();
    }
}
