package org.telegram.customization.fetch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.C0424l;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.telegram.customization.fetch.C2754d.C2742a;
import org.telegram.customization.fetch.p164b.C2739b;
import org.telegram.customization.fetch.p165c.C2747a;
import org.telegram.customization.fetch.p166d.C2752b;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: org.telegram.customization.fetch.c */
public final class C2748c {
    /* renamed from: a */
    private static final Handler f9043a = new Handler(Looper.getMainLooper());
    /* renamed from: b */
    private static final ConcurrentMap<C2752b, C2754d> f9044b = new ConcurrentHashMap();
    /* renamed from: h */
    private static final C2742a f9045h = new C27431();
    /* renamed from: c */
    private final Context f9046c;
    /* renamed from: d */
    private final C0424l f9047d;
    /* renamed from: e */
    private final List<C2747a> f9048e = new ArrayList();
    /* renamed from: f */
    private final C2737a f9049f;
    /* renamed from: g */
    private volatile boolean f9050g = false;
    /* renamed from: i */
    private final BroadcastReceiver f9051i = new C27442(this);
    /* renamed from: j */
    private final BroadcastReceiver f9052j = new C27453(this);

    /* renamed from: org.telegram.customization.fetch.c$1 */
    static class C27431 implements C2742a {
        C27431() {
        }

        /* renamed from: a */
        public void mo3477a(C2752b c2752b) {
            C2748c.f9044b.remove(c2752b);
        }
    }

    /* renamed from: org.telegram.customization.fetch.c$2 */
    class C27442 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ C2748c f9033a;
        /* renamed from: b */
        private long f9034b;
        /* renamed from: c */
        private int f9035c;
        /* renamed from: d */
        private int f9036d;
        /* renamed from: e */
        private long f9037e;
        /* renamed from: f */
        private long f9038f;
        /* renamed from: g */
        private int f9039g;

        C27442(C2748c c2748c) {
            this.f9033a = c2748c;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                this.f9034b = intent.getLongExtra("com.tonyodev.fetch.extra_id", -1);
                this.f9035c = intent.getIntExtra("com.tonyodev.fetch.extra_status", -1);
                this.f9036d = intent.getIntExtra("com.tonyodev.fetch.extra_progress", -1);
                this.f9037e = intent.getLongExtra("com.tonyodev.fetch.extra_downloaded_bytes", -1);
                this.f9038f = intent.getLongExtra("com.tonyodev.fetch.extra_file_size", -1);
                this.f9039g = intent.getIntExtra("com.tonyodev.fetch.extra_error", -1);
                try {
                    for (C2747a onUpdate : this.f9033a.f9048e) {
                        onUpdate.onUpdate(this.f9034b, this.f9035c, this.f9036d, this.f9037e, this.f9038f, this.f9039g);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: org.telegram.customization.fetch.c$3 */
    class C27453 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ C2748c f9040a;

        C27453(C2748c c2748c) {
            this.f9040a = c2748c;
        }

        public void onReceive(Context context, Intent intent) {
            FetchService.m12645a(context);
        }
    }

    /* renamed from: org.telegram.customization.fetch.c$a */
    public static class C2746a {
        /* renamed from: a */
        private final Context f9041a;
        /* renamed from: b */
        private final List<Bundle> f9042b = new ArrayList();

        public C2746a(Context context) {
            if (context == null) {
                throw new NullPointerException("Context cannot be null");
            }
            this.f9041a = context;
        }

        /* renamed from: a */
        public C2746a m12729a(int i) {
            int i2 = 201;
            if (i != 201) {
                i2 = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("com.tonyodev.fetch.action_type", 314);
            bundle.putInt("com.tonyodev.fetch.extra_network_id", i2);
            this.f9042b.add(bundle);
            return this;
        }

        /* renamed from: a */
        public C2746a m12730a(boolean z) {
            Bundle bundle = new Bundle();
            bundle.putInt("com.tonyodev.fetch.action_type", 320);
            bundle.putBoolean("com.tonyodev.fetch.extra_logging_id", z);
            this.f9042b.add(bundle);
            return this;
        }

        /* renamed from: a */
        public void m12731a() {
            for (Bundle a : this.f9042b) {
                FetchService.m12646a(this.f9041a, a);
            }
        }

        /* renamed from: b */
        public C2746a m12732b(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("com.tonyodev.fetch.action_type", 321);
            bundle.putInt("com.tonyodev.fetch.extra_concurrent_download_limit", i);
            this.f9042b.add(bundle);
            return this;
        }
    }

    private C2748c(Context context) {
        this.f9046c = context.getApplicationContext();
        this.f9047d = C0424l.m1899a(this.f9046c);
        this.f9049f = C2737a.m12701a(this.f9046c);
        this.f9049f.m12704a(m12737d());
        this.f9047d.m1903a(this.f9051i, FetchService.m12637a());
        this.f9046c.registerReceiver(this.f9052j, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        C2748c.m12734a(this.f9046c);
    }

    /* renamed from: a */
    public static void m12734a(Context context) {
        FetchService.m12645a(context);
    }

    /* renamed from: b */
    public static C2748c m12735b(Context context) {
        if (context != null) {
            return new C2748c(context);
        }
        throw new NullPointerException("Context cannot be null");
    }

    /* renamed from: d */
    private boolean m12737d() {
        return FetchService.m12666b(this.f9046c);
    }

    /* renamed from: a */
    public long m12738a(C2752b c2752b, long j) {
        C2756f.m12786a(this);
        if (c2752b == null) {
            throw new NullPointerException("Request cannot be null");
        }
        long a = C2756f.m12780a();
        try {
            String a2 = c2752b.m12746a();
            String b = c2752b.m12748b();
            int d = c2752b.m12750d();
            String a3 = C2756f.m12781a(c2752b.m12749c(), m12737d());
            long j2 = 0;
            File f = C2756f.m12799f(b);
            if (f.exists()) {
                j2 = f.length();
            }
            if (this.f9049f.m12710a(a, a2, b, 900, a3, j2, j, d, -1)) {
                C2748c.m12734a(this.f9046c);
                return a;
            }
            throw new C2739b("could not insert request", -117);
        } catch (C2739b e) {
            if (m12737d()) {
                e.printStackTrace();
            }
            return -1;
        }
    }

    /* renamed from: a */
    public void m12739a() {
        C2756f.m12786a(this);
        Bundle bundle = new Bundle();
        bundle.putInt("com.tonyodev.fetch.action_type", 325);
        FetchService.m12646a(this.f9046c, bundle);
    }

    /* renamed from: a */
    public void m12740a(long j) {
        C2756f.m12786a(this);
        Bundle bundle = new Bundle();
        bundle.putInt("com.tonyodev.fetch.action_type", 324);
        bundle.putLong("com.tonyodev.fetch.extra_id", j);
        FetchService.m12646a(this.f9046c, bundle);
    }

    /* renamed from: a */
    public void m12741a(C2747a c2747a) {
        C2756f.m12786a(this);
        if (c2747a == null) {
            throw new NullPointerException("fetchListener cannot be null");
        } else if (!this.f9048e.contains(c2747a)) {
            this.f9048e.add(c2747a);
        }
    }

    /* renamed from: b */
    public void m12742b(C2747a c2747a) {
        C2756f.m12786a(this);
        if (c2747a != null) {
            this.f9048e.remove(c2747a);
        }
    }

    /* renamed from: b */
    boolean m12743b() {
        return this.f9050g;
    }
}
