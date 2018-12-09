package org.telegram.customization.fetch;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.C0424l;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.fetch.p164b.C2738a;
import org.telegram.customization.fetch.p166d.C2751a;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: org.telegram.customization.fetch.e */
final class C2755e implements Runnable {
    /* renamed from: a */
    private final long f9081a;
    /* renamed from: b */
    private final String f9082b;
    /* renamed from: c */
    private final String f9083c;
    /* renamed from: d */
    private final List<C2751a> f9084d;
    /* renamed from: e */
    private final boolean f9085e;
    /* renamed from: f */
    private final long f9086f;
    /* renamed from: g */
    private final Context f9087g;
    /* renamed from: h */
    private final C0424l f9088h;
    /* renamed from: i */
    private final C2737a f9089i;
    /* renamed from: j */
    private volatile boolean f9090j = false;
    /* renamed from: k */
    private HttpURLConnection f9091k;
    /* renamed from: l */
    private BufferedInputStream f9092l;
    /* renamed from: m */
    private RandomAccessFile f9093m;
    /* renamed from: n */
    private int f9094n;
    /* renamed from: o */
    private long f9095o;
    /* renamed from: p */
    private long f9096p;

    C2755e(Context context, long j, String str, String str2, List<C2751a> list, long j2, boolean z, long j3) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        } else if (str == null) {
            throw new NullPointerException("Url cannot be null");
        } else if (str2 == null) {
            throw new NullPointerException("FilePath cannot be null");
        } else {
            if (list == null) {
                this.f9084d = new ArrayList();
            } else {
                this.f9084d = list;
            }
            this.f9081a = j;
            this.f9082b = str;
            this.f9083c = str2;
            this.f9096p = j2;
            this.f9087g = context.getApplicationContext();
            this.f9088h = C0424l.m1899a(this.f9087g);
            this.f9089i = C2737a.m12701a(this.f9087g);
            this.f9085e = z;
            this.f9086f = j3;
            this.f9089i.m12704a(z);
        }
    }

    /* renamed from: a */
    static long m12767a(Intent intent) {
        return intent == null ? -1 : intent.getLongExtra("com.tonyodev.fetch.extra_id", -1);
    }

    /* renamed from: a */
    static IntentFilter m12768a() {
        return new IntentFilter("com.tonyodev.fetch.action_done");
    }

    /* renamed from: a */
    private boolean m12769a(int i) {
        switch (i) {
            case Callback.DEFAULT_DRAG_ANIMATION_DURATION /*200*/:
            case 202:
            case 206:
                return true;
            default:
                return false;
        }
    }

    /* renamed from: b */
    private boolean m12770b(int i) {
        if (!C2756f.m12793b(this.f9087g)) {
            return true;
        }
        switch (i) {
            case -118:
            case -104:
            case -103:
                return true;
            default:
                return false;
        }
    }

    /* renamed from: d */
    private void m12771d() {
        this.f9091k = (HttpURLConnection) new URL(this.f9082b).openConnection();
        this.f9091k.setRequestMethod("GET");
        this.f9091k.setReadTimeout(20000);
        this.f9091k.setConnectTimeout(DefaultLoadControl.DEFAULT_MIN_BUFFER_MS);
        this.f9091k.setUseCaches(false);
        this.f9091k.setDefaultUseCaches(false);
        this.f9091k.setInstanceFollowRedirects(true);
        this.f9091k.setDoInput(true);
        for (C2751a c2751a : this.f9084d) {
            this.f9091k.addRequestProperty(c2751a.m12744a(), c2751a.m12745b());
        }
    }

    /* renamed from: e */
    private void m12772e() {
        try {
            this.f9096p = this.f9095o + Long.valueOf(this.f9091k.getHeaderField("Content-Length")).longValue();
        } catch (Exception e) {
            this.f9096p = -1;
        }
    }

    /* renamed from: f */
    private void m12773f() {
        byte[] bArr = new byte[1024];
        long nanoTime = System.nanoTime();
        while (true) {
            int read = this.f9092l.read(bArr, 0, 1024);
            if (read != -1 && !m12776i()) {
                this.f9093m.write(bArr, 0, read);
                this.f9095o = ((long) read) + this.f9095o;
                if (C2756f.m12787a(nanoTime, System.nanoTime(), this.f9086f) && !m12776i()) {
                    this.f9094n = C2756f.m12779a(this.f9095o, this.f9096p);
                    C2756f.m12785a(this.f9088h, this.f9081a, 901, this.f9094n, this.f9095o, this.f9096p, -1);
                    this.f9089i.m12708a(this.f9081a, this.f9095o, this.f9096p);
                    nanoTime = System.nanoTime();
                }
            } else {
                return;
            }
        }
    }

    /* renamed from: g */
    private void m12774g() {
        try {
            if (this.f9092l != null) {
                this.f9092l.close();
            }
        } catch (IOException e) {
            if (this.f9085e) {
                e.printStackTrace();
            }
        }
        try {
            if (this.f9093m != null) {
                this.f9093m.close();
            }
        } catch (IOException e2) {
            if (this.f9085e) {
                e2.printStackTrace();
            }
        }
        if (this.f9091k != null) {
            this.f9091k.disconnect();
        }
    }

    /* renamed from: h */
    private void m12775h() {
        Intent intent = new Intent("com.tonyodev.fetch.action_done");
        intent.putExtra("com.tonyodev.fetch.extra_id", this.f9081a);
        this.f9088h.m1904a(intent);
    }

    /* renamed from: i */
    private boolean m12776i() {
        return this.f9090j;
    }

    /* renamed from: b */
    synchronized void m12777b() {
        this.f9090j = true;
    }

    /* renamed from: c */
    synchronized long m12778c() {
        return this.f9081a;
    }

    public void run() {
        try {
            m12771d();
            C2756f.m12800g(this.f9083c);
            this.f9095o = C2756f.m12797d(this.f9083c);
            this.f9094n = C2756f.m12779a(this.f9095o, this.f9096p);
            this.f9089i.m12708a(this.f9081a, this.f9095o, this.f9096p);
            this.f9091k.setRequestProperty("Range", "bytes=" + this.f9095o + "-");
            if (m12776i()) {
                throw new C2738a("DIE", -118);
            }
            this.f9091k.connect();
            int responseCode = this.f9091k.getResponseCode();
            if (!m12769a(responseCode)) {
                throw new IllegalStateException("SSRV:" + responseCode);
            } else if (m12776i()) {
                throw new C2738a("DIE", -118);
            } else {
                if (this.f9096p < 1) {
                    m12772e();
                    this.f9089i.m12708a(this.f9081a, this.f9095o, this.f9096p);
                    this.f9094n = C2756f.m12779a(this.f9095o, this.f9096p);
                }
                this.f9093m = new RandomAccessFile(this.f9083c, "rw");
                if (responseCode == 206) {
                    this.f9093m.seek(this.f9095o);
                } else {
                    this.f9093m.seek(0);
                }
                this.f9092l = new BufferedInputStream(this.f9091k.getInputStream());
                m12773f();
                this.f9089i.m12708a(this.f9081a, this.f9095o, this.f9096p);
                if (m12776i()) {
                    throw new C2738a("DIE", -118);
                }
                if (this.f9095o >= this.f9096p && !m12776i()) {
                    if (this.f9096p < 1) {
                        this.f9096p = C2756f.m12797d(this.f9083c);
                        this.f9089i.m12708a(this.f9081a, this.f9095o, this.f9096p);
                        this.f9094n = C2756f.m12779a(this.f9095o, this.f9096p);
                    } else {
                        this.f9094n = C2756f.m12779a(this.f9095o, this.f9096p);
                    }
                    if (this.f9089i.m12707a(this.f9081a, 903, -1)) {
                        C2756f.m12785a(this.f9088h, this.f9081a, 903, this.f9094n, this.f9095o, this.f9096p, -1);
                    }
                }
                m12774g();
                m12775h();
            }
        } catch (Exception e) {
            if (this.f9085e) {
                e.printStackTrace();
            }
            int a = C2741b.m12726a(e.getMessage());
            if (m12770b(a)) {
                if (this.f9089i.m12707a(this.f9081a, 900, -1)) {
                    C2756f.m12785a(this.f9088h, this.f9081a, 900, this.f9094n, this.f9095o, this.f9096p, -1);
                }
            } else if (this.f9089i.m12707a(this.f9081a, 904, a)) {
                C2756f.m12785a(this.f9088h, this.f9081a, 904, this.f9094n, this.f9095o, this.f9096p, a);
            }
            m12774g();
            m12775h();
        } catch (Throwable th) {
            m12774g();
            m12775h();
        }
    }
}
