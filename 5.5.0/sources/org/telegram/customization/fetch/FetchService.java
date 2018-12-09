package org.telegram.customization.fetch;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.C0424l;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.telegram.customization.fetch.p164b.C2739b;
import org.telegram.customization.fetch.p166d.C2753c;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

public final class FetchService extends Service {
    /* renamed from: a */
    private Context f9013a;
    /* renamed from: b */
    private C2737a f9014b;
    /* renamed from: c */
    private C0424l f9015c;
    /* renamed from: d */
    private SharedPreferences f9016d;
    /* renamed from: e */
    private volatile boolean f9017e = false;
    /* renamed from: f */
    private volatile boolean f9018f = false;
    /* renamed from: g */
    private int f9019g = 1;
    /* renamed from: h */
    private boolean f9020h = true;
    /* renamed from: i */
    private long f9021i = 2000;
    /* renamed from: j */
    private int f9022j = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    /* renamed from: k */
    private final ExecutorService f9023k = Executors.newSingleThreadExecutor();
    /* renamed from: l */
    private final List<BroadcastReceiver> f9024l = new ArrayList();
    /* renamed from: m */
    private final ConcurrentHashMap<Long, C2755e> f9025m = new ConcurrentHashMap();
    /* renamed from: n */
    private final BroadcastReceiver f9026n = new C27359(this);

    /* renamed from: org.telegram.customization.fetch.FetchService$1 */
    class C27271 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ FetchService f8998a;

        C27271(FetchService fetchService) {
            this.f8998a = fetchService;
        }

        public void run() {
            this.f8998a.f9014b.m12724h();
            this.f8998a.f9014b.m12723g();
        }
    }

    /* renamed from: org.telegram.customization.fetch.FetchService$5 */
    class C27315 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ FetchService f9005a;

        C27315(FetchService fetchService) {
            this.f9005a = fetchService;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                this.f9005a.m12682f(C2755e.m12767a(intent));
            }
            if (this.f9005a.f9025m.size() == 0) {
                this.f9005a.m12677e();
                this.f9005a.f9015c.m1902a((BroadcastReceiver) this);
                this.f9005a.f9024l.remove(this);
                this.f9005a.f9017e = false;
                this.f9005a.m12658b();
            }
        }
    }

    /* renamed from: org.telegram.customization.fetch.FetchService$6 */
    class C27326 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ FetchService f9006a;

        C27326(FetchService fetchService) {
            this.f9006a = fetchService;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                this.f9006a.m12689h(C2755e.m12767a(intent));
            }
            if (this.f9006a.f9025m.size() == 0) {
                this.f9006a.m12685g();
                this.f9006a.f9015c.m1902a((BroadcastReceiver) this);
                this.f9006a.f9024l.remove(this);
                this.f9006a.f9017e = false;
                this.f9006a.m12658b();
            }
        }
    }

    /* renamed from: org.telegram.customization.fetch.FetchService$9 */
    class C27359 extends BroadcastReceiver {
        /* renamed from: a */
        final /* synthetic */ FetchService f9012a;

        C27359(FetchService fetchService) {
            this.f9012a = fetchService;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                long a = C2755e.m12767a(intent);
                if (this.f9012a.f9025m.containsKey(Long.valueOf(a))) {
                    this.f9012a.f9025m.remove(Long.valueOf(a));
                }
                this.f9012a.m12658b();
            }
        }
    }

    /* renamed from: a */
    public static IntentFilter m12637a() {
        return new IntentFilter("com.tonyodev.fetch.event_action_update");
    }

    /* renamed from: a */
    private void m12639a(int i) {
        this.f9022j = i;
        this.f9016d.edit().putInt("com.tonyodev.fetch.extra_network_id", i).apply();
        if (this.f9025m.size() > 0) {
            m12668c();
        }
        m12658b();
    }

    /* renamed from: a */
    private void m12640a(int i, long j, long j2, int i2) {
        Cursor e;
        switch (i) {
            case 480:
                e = this.f9014b.m12721e(j2);
                break;
            case 482:
                e = this.f9014b.m12702a(i2);
                break;
            default:
                e = this.f9014b.m12718d();
                break;
        }
        m12644a(j, C2756f.m12795c(e, true, this.f9020h));
        m12658b();
    }

    /* renamed from: a */
    private void m12641a(long j) {
        if (this.f9025m.containsKey(Long.valueOf(j))) {
            C2755e c2755e = (C2755e) this.f9025m.get(Long.valueOf(j));
            if (c2755e != null) {
                c2755e.m12777b();
            }
        }
    }

    /* renamed from: a */
    private void m12642a(long j, int i) {
        if (this.f9014b.m12706a(j, i) && this.f9025m.size() > 0) {
            m12668c();
        }
        m12658b();
    }

    /* renamed from: a */
    private void m12643a(final long j, final String str) {
        if (this.f9025m.containsKey(Long.valueOf(j))) {
            this.f9017e = true;
            BroadcastReceiver c27348 = new BroadcastReceiver(this) {
                /* renamed from: c */
                final /* synthetic */ FetchService f9011c;

                public void onReceive(Context context, Intent intent) {
                    if (C2755e.m12767a(intent) == j) {
                        this.f9011c.m12661b(j, str);
                        this.f9011c.f9015c.m1902a((BroadcastReceiver) this);
                        this.f9011c.f9024l.remove(this);
                        this.f9011c.f9017e = false;
                        this.f9011c.m12658b();
                    }
                }
            };
            this.f9024l.add(c27348);
            this.f9015c.m1903a(c27348, C2755e.m12768a());
            m12641a(j);
            return;
        }
        m12661b(j, str);
        m12658b();
    }

    /* renamed from: a */
    private void m12644a(long j, ArrayList<Bundle> arrayList) {
        Intent intent = new Intent("com.tonyodev.fetch.event_action_query");
        intent.putExtra("com.tonyodev.fetch.extra_query_id", j);
        intent.putExtra("com.tonyodev.fetch.extra_query_result", arrayList);
        this.f9015c.m1904a(intent);
    }

    /* renamed from: a */
    public static void m12645a(Context context) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        Intent intent = new Intent(context, FetchService.class);
        intent.putExtra("com.tonyodev.fetch.action_type", 315);
        context.startService(intent);
    }

    /* renamed from: a */
    public static void m12646a(Context context, Bundle bundle) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        Intent intent = new Intent(context, FetchService.class);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    /* renamed from: a */
    private void m12647a(final Intent intent) {
        if (intent != null && !this.f9023k.isShutdown()) {
            this.f9023k.execute(new Runnable(this) {
                /* renamed from: b */
                final /* synthetic */ FetchService f9000b;

                public void run() {
                    this.f9000b.f9014b.m12724h();
                    long longExtra = intent.getLongExtra("com.tonyodev.fetch.extra_id", -1);
                    switch (intent.getIntExtra("com.tonyodev.fetch.action_type", -1)) {
                        case 310:
                            this.f9000b.m12649a(intent.getStringExtra("com.tonyodev.fetch.extra_url"), intent.getStringExtra("com.tonyodev.fetch.extra_file_path"), intent.getParcelableArrayListExtra("com.tonyodev.fetch.extra_headers"), intent.getIntExtra("com.tonyodev.fetch.extra_priority", 600));
                            return;
                        case 311:
                            this.f9000b.m12669c(longExtra);
                            return;
                        case 312:
                            this.f9000b.m12660b(longExtra);
                            return;
                        case 313:
                            this.f9000b.m12678e(longExtra);
                            return;
                        case 314:
                            this.f9000b.m12639a(intent.getIntExtra("com.tonyodev.fetch.extra_network_id", Callback.DEFAULT_DRAG_ANIMATION_DURATION));
                            return;
                        case 315:
                            this.f9000b.m12658b();
                            return;
                        case 316:
                            long longExtra2 = intent.getLongExtra("com.tonyodev.fetch.extra_query_id", -1);
                            this.f9000b.m12640a(intent.getIntExtra("com.tonyodev.fetch.extra_query_type", 481), longExtra2, longExtra, intent.getIntExtra("com.tonyodev.fetch.extra_status", -1));
                            return;
                        case 317:
                            this.f9000b.m12642a(longExtra, intent.getIntExtra("com.tonyodev.fetch.extra_priority", 600));
                            return;
                        case 318:
                            this.f9000b.m12693i(longExtra);
                            return;
                        case 319:
                            this.f9000b.m12672d();
                            return;
                        case 320:
                            this.f9000b.m12657a(intent.getBooleanExtra("com.tonyodev.fetch.extra_logging_id", true));
                            return;
                        case 321:
                            this.f9000b.m12659b(intent.getIntExtra("com.tonyodev.fetch.extra_concurrent_download_limit", 1));
                            return;
                        case 322:
                            this.f9000b.m12643a(longExtra, intent.getStringExtra("com.tonyodev.fetch.extra_url"));
                            return;
                        case 323:
                            this.f9000b.m12696j(intent.getLongExtra("com.tonyodev.fetch.extra_on_update_interval", 2000));
                            return;
                        case 324:
                            this.f9000b.m12686g(longExtra);
                            return;
                        case 325:
                            this.f9000b.m12681f();
                            return;
                        default:
                            this.f9000b.m12658b();
                            return;
                    }
                }
            });
        }
    }

    /* renamed from: a */
    private void m12648a(String str, long j, String str2, String str3, int i, ArrayList<Bundle> arrayList, int i2, int i3) {
        Intent intent = new Intent(str);
        intent.putExtra("com.tonyodev.fetch.extra_id", j);
        intent.putExtra("com.tonyodev.fetch.extra_status", i);
        intent.putExtra("com.tonyodev.fetch.extra_url", str2);
        intent.putExtra("com.tonyodev.fetch.extra_file_path", str3);
        intent.putExtra("com.tonyodev.fetch.extra_headers", arrayList);
        intent.putExtra("com.tonyodev.fetch.extra_progress", 0);
        intent.putExtra("com.tonyodev.fetch.extra_file_size", 0);
        intent.putExtra("com.tonyodev.fetch.extra_error", i3);
        intent.putExtra("com.tonyodev.fetch.extra_priority", i2);
        this.f9015c.m1904a(intent);
    }

    /* renamed from: a */
    private void m12649a(String str, String str2, ArrayList<Bundle> arrayList, int i) {
        C2739b e;
        ArrayList arrayList2;
        if (str == null || str2 == null) {
            try {
                throw new C2739b("Request was not properly formatted. url:" + str + ", filePath:" + str2, -116);
            } catch (C2739b e2) {
                e = e2;
                arrayList2 = arrayList;
                try {
                    if (this.f9020h) {
                        e.printStackTrace();
                    }
                    m12648a("com.tonyodev.fetch.event_action_enqueue_failed", -1, str, str2, -900, arrayList2, i, e.m12725a());
                } finally {
                    m12658b();
                }
            }
        } else {
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            try {
                long a = C2756f.m12780a();
                String b = C2756f.m12790b((List) arrayList, this.f9020h);
                long j = 0;
                File f = C2756f.m12799f(str2);
                if (f.exists()) {
                    j = f.length();
                }
                if (this.f9014b.m12710a(a, str, str2, 900, b, j, 0, i, -1)) {
                    m12648a("com.tonyodev.fetch.event_action_enqueued", a, str, str2, 900, arrayList, i, -1);
                    m12658b();
                    return;
                }
                throw new C2739b("could not enqueue request", -117);
            } catch (C2739b e3) {
                e = e3;
                ArrayList<Bundle> arrayList3 = arrayList;
                if (this.f9020h) {
                    e.printStackTrace();
                }
                m12648a("com.tonyodev.fetch.event_action_enqueue_failed", -1, str, str2, -900, arrayList2, i, e.m12725a());
            }
        }
    }

    /* renamed from: a */
    private void m12657a(boolean z) {
        this.f9020h = z;
        this.f9016d.edit().putBoolean("com.tonyodev.fetch.extra_logging_id", z).apply();
        this.f9014b.m12704a(this.f9020h);
        m12658b();
    }

    /* renamed from: b */
    private synchronized void m12658b() {
        if (!this.f9018f) {
            boolean b = C2756f.m12793b(this.f9013a);
            boolean a = C2756f.m12788a(this.f9013a);
            if ((!b || (this.f9022j == 201 && !a)) && this.f9025m.size() > 0) {
                this.f9017e = true;
                m12668c();
                this.f9017e = false;
            } else {
                if (b) {
                    if (this.f9025m.size() < this.f9019g && this.f9014b.m12722f()) {
                        this.f9017e = true;
                        try {
                            Cursor e = this.f9014b.m12720e();
                            if (!(e == null || e.isClosed() || e.getCount() <= 0)) {
                                C2753c a2 = C2756f.m12784a(e, true, this.f9020h);
                                Runnable c2755e = new C2755e(this.f9013a, a2.m12751a(), a2.m12753c(), a2.m12754d(), a2.m12759i(), a2.m12757g(), this.f9020h, this.f9021i);
                                this.f9014b.m12707a(a2.m12751a(), 901, -1);
                                this.f9025m.put(Long.valueOf(c2755e.m12778c()), c2755e);
                                new Thread(c2755e).start();
                            }
                        } catch (Exception e2) {
                            if (this.f9020h) {
                                e2.printStackTrace();
                            }
                        }
                        this.f9017e = false;
                        if (this.f9025m.size() < this.f9019g && this.f9014b.m12722f()) {
                            m12658b();
                        }
                    }
                }
                if (!(this.f9017e || this.f9025m.size() != 0 || this.f9014b.m12722f())) {
                    this.f9018f = true;
                    stopSelf();
                }
            }
        }
    }

    /* renamed from: b */
    private void m12659b(int i) {
        if (i < 1) {
            i = 1;
        }
        this.f9019g = i;
        this.f9016d.edit().putInt("com.tonyodev.fetch.extra_concurrent_download_limit", i).apply();
        if (this.f9025m.size() > 0) {
            m12668c();
        }
        m12658b();
    }

    /* renamed from: b */
    private void m12660b(long j) {
        if (!this.f9025m.containsKey(Long.valueOf(j))) {
            if (this.f9014b.m12715b(j)) {
                C2753c a = C2756f.m12784a(this.f9014b.m12721e(j), true, this.f9020h);
                if (a != null) {
                    C2756f.m12785a(this.f9015c, a.m12751a(), a.m12752b(), a.m12755e(), a.m12756f(), a.m12757g(), a.m12758h());
                }
            }
            m12658b();
        }
    }

    /* renamed from: b */
    private void m12661b(long j, String str) {
        this.f9014b.m12709a(j, str);
        this.f9014b.m12719d(j);
    }

    /* renamed from: b */
    static boolean m12666b(Context context) {
        return context.getSharedPreferences("com.tonyodev.fetch.shared_preferences", 0).getBoolean("com.tonyodev.fetch.extra_logging_id", true);
    }

    /* renamed from: c */
    private void m12668c() {
        for (Long l : this.f9025m.keySet()) {
            C2755e c2755e = (C2755e) this.f9025m.get(l);
            if (c2755e != null) {
                c2755e.m12777b();
            }
        }
    }

    /* renamed from: c */
    private void m12669c(final long j) {
        if (this.f9025m.containsKey(Long.valueOf(j))) {
            this.f9017e = true;
            BroadcastReceiver c27293 = new BroadcastReceiver(this) {
                /* renamed from: b */
                final /* synthetic */ FetchService f9002b;

                public void onReceive(Context context, Intent intent) {
                    if (C2755e.m12767a(intent) == j) {
                        this.f9002b.m12673d(j);
                        this.f9002b.f9015c.m1902a((BroadcastReceiver) this);
                        this.f9002b.f9024l.remove(this);
                        this.f9002b.f9017e = false;
                        this.f9002b.m12658b();
                    }
                }
            };
            this.f9024l.add(c27293);
            this.f9015c.m1903a(c27293, C2755e.m12768a());
            m12641a(j);
            return;
        }
        m12673d(j);
        m12658b();
    }

    /* renamed from: d */
    private void m12672d() {
        if (this.f9025m.size() > 0) {
            this.f9017e = true;
            BroadcastReceiver c27315 = new C27315(this);
            this.f9024l.add(c27315);
            this.f9015c.m1903a(c27315, C2755e.m12768a());
            m12668c();
            return;
        }
        m12677e();
        m12658b();
    }

    /* renamed from: d */
    private void m12673d(long j) {
        if (this.f9014b.m12705a(j)) {
            C2753c a = C2756f.m12784a(this.f9014b.m12721e(j), true, this.f9020h);
            if (a != null) {
                C2756f.m12785a(this.f9015c, a.m12751a(), a.m12752b(), a.m12755e(), a.m12756f(), a.m12757g(), a.m12758h());
            }
        }
    }

    /* renamed from: e */
    private void m12677e() {
        List<C2753c> b = C2756f.m12792b(this.f9014b.m12718d(), true, this.f9020h);
        if (b != null && this.f9014b.m12716c()) {
            for (C2753c c2753c : b) {
                C2756f.m12796c(c2753c.m12754d());
                C2756f.m12785a(this.f9015c, c2753c.m12751a(), 905, 0, 0, 0, -1);
            }
        }
    }

    /* renamed from: e */
    private void m12678e(final long j) {
        if (this.f9025m.containsKey(Long.valueOf(j))) {
            this.f9017e = true;
            BroadcastReceiver c27304 = new BroadcastReceiver(this) {
                /* renamed from: b */
                final /* synthetic */ FetchService f9004b;

                public void onReceive(Context context, Intent intent) {
                    if (C2755e.m12767a(intent) == j) {
                        this.f9004b.m12682f(j);
                        this.f9004b.f9015c.m1902a((BroadcastReceiver) this);
                        this.f9004b.f9024l.remove(this);
                        this.f9004b.f9017e = false;
                        this.f9004b.m12658b();
                    }
                }
            };
            this.f9024l.add(c27304);
            this.f9015c.m1903a(c27304, C2755e.m12768a());
            m12641a(j);
            return;
        }
        m12682f(j);
        m12658b();
    }

    /* renamed from: f */
    private void m12681f() {
        if (this.f9025m.size() > 0) {
            this.f9017e = true;
            BroadcastReceiver c27326 = new C27326(this);
            this.f9024l.add(c27326);
            this.f9015c.m1903a(c27326, C2755e.m12768a());
            m12668c();
            return;
        }
        m12685g();
        m12658b();
    }

    /* renamed from: f */
    private void m12682f(long j) {
        if (C2756f.m12784a(this.f9014b.m12721e(j), true, this.f9020h) != null && this.f9014b.m12717c(j)) {
            C2756f.m12785a(this.f9015c, j, 905, 0, 0, 0, -1);
        }
    }

    /* renamed from: g */
    private void m12685g() {
        List<C2753c> b = C2756f.m12792b(this.f9014b.m12718d(), true, this.f9020h);
        if (b != null && this.f9014b.m12716c()) {
            for (C2753c c2753c : b) {
                C2756f.m12785a(this.f9015c, c2753c.m12751a(), 905, c2753c.m12755e(), c2753c.m12756f(), c2753c.m12757g(), -1);
            }
        }
    }

    /* renamed from: g */
    private void m12686g(final long j) {
        if (this.f9025m.containsKey(Long.valueOf(j))) {
            this.f9017e = true;
            BroadcastReceiver c27337 = new BroadcastReceiver(this) {
                /* renamed from: b */
                final /* synthetic */ FetchService f9008b;

                public void onReceive(Context context, Intent intent) {
                    if (C2755e.m12767a(intent) == j) {
                        this.f9008b.m12689h(j);
                        this.f9008b.f9015c.m1902a((BroadcastReceiver) this);
                        this.f9008b.f9024l.remove(this);
                        this.f9008b.f9017e = false;
                        this.f9008b.m12658b();
                    }
                }
            };
            this.f9024l.add(c27337);
            this.f9015c.m1903a(c27337, C2755e.m12768a());
            m12641a(j);
            return;
        }
        m12689h(j);
        m12658b();
    }

    /* renamed from: h */
    private int m12688h() {
        return this.f9016d.getInt("com.tonyodev.fetch.extra_network_id", Callback.DEFAULT_DRAG_ANIMATION_DURATION);
    }

    /* renamed from: h */
    private void m12689h(long j) {
        C2753c a = C2756f.m12784a(this.f9014b.m12721e(j), true, this.f9020h);
        if (a != null && this.f9014b.m12717c(j)) {
            C2756f.m12785a(this.f9015c, j, 905, a.m12755e(), a.m12756f(), a.m12757g(), -1);
        }
    }

    /* renamed from: i */
    private int m12692i() {
        return this.f9016d.getInt("com.tonyodev.fetch.extra_concurrent_download_limit", 1);
    }

    /* renamed from: i */
    private void m12693i(long j) {
        if (!this.f9025m.containsKey(Long.valueOf(j))) {
            if (this.f9014b.m12719d(j)) {
                C2753c a = C2756f.m12784a(this.f9014b.m12721e(j), true, this.f9020h);
                if (a != null) {
                    C2756f.m12785a(this.f9015c, a.m12751a(), a.m12752b(), a.m12755e(), a.m12756f(), a.m12757g(), a.m12758h());
                }
            }
            m12658b();
        }
    }

    /* renamed from: j */
    private void m12696j(long j) {
        this.f9021i = j;
        this.f9016d.edit().putLong("com.tonyodev.fetch.extra_on_update_interval", j).apply();
        if (this.f9025m.size() > 0) {
            m12668c();
        }
        m12658b();
    }

    /* renamed from: j */
    private boolean m12697j() {
        return this.f9016d.getBoolean("com.tonyodev.fetch.extra_logging_id", true);
    }

    /* renamed from: k */
    private long m12698k() {
        this.f9021i = this.f9016d.getLong("com.tonyodev.fetch.extra_on_update_interval", 2000);
        return this.f9021i;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.f9013a = getApplicationContext();
        this.f9015c = C0424l.m1899a(this.f9013a);
        this.f9016d = getSharedPreferences("com.tonyodev.fetch.shared_preferences", 0);
        this.f9014b = C2737a.m12701a(this.f9013a);
        this.f9015c.m1903a(this.f9026n, C2755e.m12768a());
        this.f9024l.add(this.f9026n);
        this.f9019g = m12692i();
        this.f9022j = m12688h();
        this.f9020h = m12697j();
        this.f9021i = m12698k();
        this.f9014b.m12704a(this.f9020h);
        if (!this.f9023k.isShutdown()) {
            this.f9023k.execute(new C27271(this));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.f9018f = true;
        if (!this.f9023k.isShutdown()) {
            this.f9023k.shutdown();
        }
        m12668c();
        for (BroadcastReceiver a : this.f9024l) {
            this.f9015c.m1902a(a);
        }
        this.f9024l.clear();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return super.onStartCommand(intent, i, i2);
        }
        m12647a(intent);
        return 1;
    }
}
