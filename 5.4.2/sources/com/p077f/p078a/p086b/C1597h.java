package com.p077f.p078a.p086b;

import android.graphics.Bitmap;
import android.os.Handler;
import com.p077f.p078a.p086b.C1570c.C1566a;
import com.p077f.p078a.p086b.p087a.C1550b;
import com.p077f.p078a.p086b.p087a.C1550b.C1549a;
import com.p077f.p078a.p086b.p087a.C1552d;
import com.p077f.p078a.p086b.p087a.C1553e;
import com.p077f.p078a.p086b.p087a.C1554f;
import com.p077f.p078a.p086b.p087a.C1557h;
import com.p077f.p078a.p086b.p089b.C1561b;
import com.p077f.p078a.p086b.p089b.C1563c;
import com.p077f.p078a.p086b.p091d.C1572b;
import com.p077f.p078a.p086b.p091d.C1572b.C1574a;
import com.p077f.p078a.p086b.p092e.C1580a;
import com.p077f.p078a.p086b.p093f.C1586a;
import com.p077f.p078a.p086b.p093f.C1587b;
import com.p077f.p078a.p095c.C1601b;
import com.p077f.p078a.p095c.C1601b.C1596a;
import com.p077f.p078a.p095c.C1602c;
import java.io.Closeable;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.f.a.b.h */
final class C1597h implements C1596a, Runnable {
    /* renamed from: a */
    final String f4887a;
    /* renamed from: b */
    final C1580a f4888b;
    /* renamed from: c */
    final C1570c f4889c;
    /* renamed from: d */
    final C1586a f4890d;
    /* renamed from: e */
    final C1587b f4891e;
    /* renamed from: f */
    private final C1589f f4892f;
    /* renamed from: g */
    private final C1591g f4893g;
    /* renamed from: h */
    private final Handler f4894h;
    /* renamed from: i */
    private final C1584e f4895i;
    /* renamed from: j */
    private final C1572b f4896j;
    /* renamed from: k */
    private final C1572b f4897k;
    /* renamed from: l */
    private final C1572b f4898l;
    /* renamed from: m */
    private final C1561b f4899m;
    /* renamed from: n */
    private final String f4900n;
    /* renamed from: o */
    private final C1553e f4901o;
    /* renamed from: p */
    private final boolean f4902p;
    /* renamed from: q */
    private C1554f f4903q = C1554f.NETWORK;

    /* renamed from: com.f.a.b.h$3 */
    class C15943 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C1597h f4885a;

        C15943(C1597h c1597h) {
            this.f4885a = c1597h;
        }

        public void run() {
            this.f4885a.f4890d.onLoadingCancelled(this.f4885a.f4887a, this.f4885a.f4888b.mo1233d());
        }
    }

    /* renamed from: com.f.a.b.h$a */
    class C1595a extends Exception {
        /* renamed from: a */
        final /* synthetic */ C1597h f4886a;

        C1595a(C1597h c1597h) {
            this.f4886a = c1597h;
        }
    }

    public C1597h(C1589f c1589f, C1591g c1591g, Handler handler) {
        this.f4892f = c1589f;
        this.f4893g = c1591g;
        this.f4894h = handler;
        this.f4895i = c1589f.f4861a;
        this.f4896j = this.f4895i.f4854p;
        this.f4897k = this.f4895i.f4857s;
        this.f4898l = this.f4895i.f4858t;
        this.f4899m = this.f4895i.f4855q;
        this.f4887a = c1591g.f4871a;
        this.f4900n = c1591g.f4872b;
        this.f4888b = c1591g.f4873c;
        this.f4901o = c1591g.f4874d;
        this.f4889c = c1591g.f4875e;
        this.f4890d = c1591g.f4876f;
        this.f4891e = c1591g.f4877g;
        this.f4902p = this.f4889c.m7787s();
    }

    /* renamed from: a */
    private Bitmap m7902a(String str) {
        String str2 = str;
        return this.f4899m.mo1225a(new C1563c(this.f4900n, str2, this.f4887a, this.f4901o, this.f4888b.mo1232c(), m7914h(), this.f4889c));
    }

    /* renamed from: a */
    private void m7904a(final C1549a c1549a, final Throwable th) {
        if (!this.f4902p && !m7922p() && !m7916j()) {
            C1597h.m7905a(new Runnable(this) {
                /* renamed from: c */
                final /* synthetic */ C1597h f4884c;

                public void run() {
                    if (this.f4884c.f4889c.m7771c()) {
                        this.f4884c.f4888b.mo1230a(this.f4884c.f4889c.m7770c(this.f4884c.f4895i.f4839a));
                    }
                    this.f4884c.f4890d.onLoadingFailed(this.f4884c.f4887a, this.f4884c.f4888b.mo1233d(), new C1550b(c1549a, th));
                }
            }, false, this.f4894h, this.f4892f);
        }
    }

    /* renamed from: a */
    static void m7905a(Runnable runnable, boolean z, Handler handler, C1589f c1589f) {
        if (z) {
            runnable.run();
        } else if (handler == null) {
            c1589f.m7895a(runnable);
        } else {
            handler.post(runnable);
        }
    }

    /* renamed from: b */
    private boolean m7906b() {
        AtomicBoolean a = this.f4892f.m7890a();
        if (a.get()) {
            synchronized (this.f4892f.m7896b()) {
                if (a.get()) {
                    C1602c.m7936a("ImageLoader is paused. Waiting...  [%s]", this.f4900n);
                    try {
                        this.f4892f.m7896b().wait();
                        C1602c.m7936a(".. Resume loading [%s]", this.f4900n);
                    } catch (InterruptedException e) {
                        C1602c.m7942d("Task was interrupted [%s]", this.f4900n);
                        return true;
                    }
                }
            }
        }
        return m7916j();
    }

    /* renamed from: b */
    private boolean m7907b(int i, int i2) {
        File a = this.f4895i.f4853o.mo1213a(this.f4887a);
        if (a != null && a.exists()) {
            Bitmap a2 = this.f4899m.mo1225a(new C1563c(this.f4900n, C1574a.FILE.m7804b(a.getAbsolutePath()), this.f4887a, new C1553e(i, i2), C1557h.FIT_INSIDE, m7914h(), new C1566a().m7735a(this.f4889c).m7734a(C1552d.IN_SAMPLE_INT).m7737a()));
            if (!(a2 == null || this.f4895i.f4844f == null)) {
                C1602c.m7936a("Process image before cache on disk [%s]", this.f4900n);
                a2 = this.f4895i.f4844f.m7900a(a2);
                if (a2 == null) {
                    C1602c.m7942d("Bitmap processor for disk cache returned null [%s]", this.f4900n);
                }
            }
            Bitmap bitmap = a2;
            if (bitmap != null) {
                boolean a3 = this.f4895i.f4853o.mo1214a(this.f4887a, bitmap);
                bitmap.recycle();
                return a3;
            }
        }
        return false;
    }

    /* renamed from: c */
    private boolean m7908c() {
        if (!this.f4889c.m7774f()) {
            return false;
        }
        C1602c.m7936a("Delay %d ms before loading...  [%s]", Integer.valueOf(this.f4889c.m7780l()), this.f4900n);
        try {
            Thread.sleep((long) this.f4889c.m7780l());
            return m7916j();
        } catch (InterruptedException e) {
            C1602c.m7942d("Task was interrupted [%s]", this.f4900n);
            return true;
        }
    }

    /* renamed from: c */
    private boolean m7909c(final int i, final int i2) {
        if (m7922p() || m7916j()) {
            return false;
        }
        if (this.f4891e != null) {
            C1597h.m7905a(new Runnable(this) {
                /* renamed from: c */
                final /* synthetic */ C1597h f4881c;

                public void run() {
                    this.f4881c.f4891e.mo3497a(this.f4881c.f4887a, this.f4881c.f4888b.mo1233d(), i, i2);
                }
            }, false, this.f4894h, this.f4892f);
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: d */
    private android.graphics.Bitmap m7910d() {
        /*
        r7 = this;
        r1 = 0;
        r0 = r7.f4895i;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r0 = r0.f4853o;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r2 = r7.f4887a;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r0 = r0.mo1213a(r2);	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        if (r0 == 0) goto L_0x00db;
    L_0x000d:
        r2 = r0.exists();	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        if (r2 == 0) goto L_0x00db;
    L_0x0013:
        r2 = r0.length();	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 <= 0) goto L_0x00db;
    L_0x001d:
        r2 = "Load image from disk cache [%s]";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r4 = 0;
        r5 = r7.f4900n;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r3[r4] = r5;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        com.p077f.p078a.p095c.C1602c.m7936a(r2, r3);	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r2 = com.p077f.p078a.p086b.p087a.C1554f.DISC_CACHE;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r7.f4903q = r2;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r7.m7915i();	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r2 = com.p077f.p078a.p086b.p091d.C1572b.C1574a.FILE;	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r0 = r0.getAbsolutePath();	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r0 = r2.m7804b(r0);	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
        r0 = r7.m7902a(r0);	 Catch:{ IllegalStateException -> 0x00a2, a -> 0x00aa, IOException -> 0x00ac, OutOfMemoryError -> 0x00b9, Throwable -> 0x00c6 }
    L_0x0040:
        if (r0 == 0) goto L_0x004e;
    L_0x0042:
        r2 = r0.getWidth();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r2 <= 0) goto L_0x004e;
    L_0x0048:
        r2 = r0.getHeight();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r2 > 0) goto L_0x00a1;
    L_0x004e:
        r2 = "Load image from network [%s]";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r4 = 0;
        r5 = r7.f4900n;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r3[r4] = r5;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        com.p077f.p078a.p095c.C1602c.m7936a(r2, r3);	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r2 = com.p077f.p078a.p086b.p087a.C1554f.NETWORK;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r7.f4903q = r2;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r2 = r7.f4887a;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r3 = r7.f4889c;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r3 = r3.m7777i();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r3 == 0) goto L_0x0086;
    L_0x006a:
        r3 = r7.m7911e();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r3 == 0) goto L_0x0086;
    L_0x0070:
        r3 = r7.f4895i;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r3 = r3.f4853o;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r4 = r7.f4887a;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r3 = r3.mo1213a(r4);	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r3 == 0) goto L_0x0086;
    L_0x007c:
        r2 = com.p077f.p078a.p086b.p091d.C1572b.C1574a.FILE;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r3 = r3.getAbsolutePath();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r2 = r2.m7804b(r3);	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
    L_0x0086:
        r7.m7915i();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r0 = r7.m7902a(r2);	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r0 == 0) goto L_0x009b;
    L_0x008f:
        r2 = r0.getWidth();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r2 <= 0) goto L_0x009b;
    L_0x0095:
        r2 = r0.getHeight();	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        if (r2 > 0) goto L_0x00a1;
    L_0x009b:
        r2 = com.p077f.p078a.p086b.p087a.C1550b.C1549a.DECODING_ERROR;	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
        r3 = 0;
        r7.m7904a(r2, r3);	 Catch:{ IllegalStateException -> 0x00d9, a -> 0x00aa, IOException -> 0x00d7, OutOfMemoryError -> 0x00d5, Throwable -> 0x00d3 }
    L_0x00a1:
        return r0;
    L_0x00a2:
        r0 = move-exception;
        r0 = r1;
    L_0x00a4:
        r2 = com.p077f.p078a.p086b.p087a.C1550b.C1549a.NETWORK_DENIED;
        r7.m7904a(r2, r1);
        goto L_0x00a1;
    L_0x00aa:
        r0 = move-exception;
        throw r0;
    L_0x00ac:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
    L_0x00b0:
        com.p077f.p078a.p095c.C1602c.m7937a(r1);
        r2 = com.p077f.p078a.p086b.p087a.C1550b.C1549a.IO_ERROR;
        r7.m7904a(r2, r1);
        goto L_0x00a1;
    L_0x00b9:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
    L_0x00bd:
        com.p077f.p078a.p095c.C1602c.m7937a(r1);
        r2 = com.p077f.p078a.p086b.p087a.C1550b.C1549a.OUT_OF_MEMORY;
        r7.m7904a(r2, r1);
        goto L_0x00a1;
    L_0x00c6:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
    L_0x00ca:
        com.p077f.p078a.p095c.C1602c.m7937a(r1);
        r2 = com.p077f.p078a.p086b.p087a.C1550b.C1549a.UNKNOWN;
        r7.m7904a(r2, r1);
        goto L_0x00a1;
    L_0x00d3:
        r1 = move-exception;
        goto L_0x00ca;
    L_0x00d5:
        r1 = move-exception;
        goto L_0x00bd;
    L_0x00d7:
        r1 = move-exception;
        goto L_0x00b0;
    L_0x00d9:
        r2 = move-exception;
        goto L_0x00a4;
    L_0x00db:
        r0 = r1;
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.f.a.b.h.d():android.graphics.Bitmap");
    }

    /* renamed from: e */
    private boolean m7911e() {
        C1602c.m7936a("Cache image on disk [%s]", this.f4900n);
        try {
            boolean f = m7912f();
            if (!f) {
                return f;
            }
            int i = this.f4895i.f4842d;
            int i2 = this.f4895i.f4843e;
            if (i <= 0 && i2 <= 0) {
                return f;
            }
            C1602c.m7936a("Resize image in disk cache [%s]", this.f4900n);
            m7907b(i, i2);
            return f;
        } catch (Throwable e) {
            C1602c.m7937a(e);
            return false;
        }
    }

    /* renamed from: f */
    private boolean m7912f() {
        boolean z = false;
        Closeable a = m7914h().mo1227a(this.f4887a, this.f4889c.m7782n());
        if (a == null) {
            C1602c.m7942d("No stream for image [%s]", this.f4900n);
        } else {
            try {
                z = this.f4895i.f4853o.mo1215a(this.f4887a, a, this);
            } finally {
                C1601b.m7930a(a);
            }
        }
        return z;
    }

    /* renamed from: g */
    private void m7913g() {
        if (!this.f4902p && !m7922p()) {
            C1597h.m7905a(new C15943(this), false, this.f4894h, this.f4892f);
        }
    }

    /* renamed from: h */
    private C1572b m7914h() {
        return this.f4892f.m7898c() ? this.f4897k : this.f4892f.m7899d() ? this.f4898l : this.f4896j;
    }

    /* renamed from: i */
    private void m7915i() {
        m7917k();
        m7919m();
    }

    /* renamed from: j */
    private boolean m7916j() {
        return m7918l() || m7920n();
    }

    /* renamed from: k */
    private void m7917k() {
        if (m7918l()) {
            throw new C1595a(this);
        }
    }

    /* renamed from: l */
    private boolean m7918l() {
        if (!this.f4888b.mo1234e()) {
            return false;
        }
        C1602c.m7936a("ImageAware was collected by GC. Task is cancelled. [%s]", this.f4900n);
        return true;
    }

    /* renamed from: m */
    private void m7919m() {
        if (m7920n()) {
            throw new C1595a(this);
        }
    }

    /* renamed from: n */
    private boolean m7920n() {
        if (!(!this.f4900n.equals(this.f4892f.m7889a(this.f4888b)))) {
            return false;
        }
        C1602c.m7936a("ImageAware is reused for another image. Task is cancelled. [%s]", this.f4900n);
        return true;
    }

    /* renamed from: o */
    private void m7921o() {
        if (m7922p()) {
            throw new C1595a(this);
        }
    }

    /* renamed from: p */
    private boolean m7922p() {
        if (!Thread.interrupted()) {
            return false;
        }
        C1602c.m7936a("Task was interrupted [%s]", this.f4900n);
        return true;
    }

    /* renamed from: a */
    String m7923a() {
        return this.f4887a;
    }

    /* renamed from: a */
    public boolean mo1242a(int i, int i2) {
        return this.f4902p || m7909c(i, i2);
    }

    public void run() {
        if (!m7906b() && !m7908c()) {
            ReentrantLock reentrantLock = this.f4893g.f4878h;
            C1602c.m7936a("Start display image task [%s]", this.f4900n);
            if (reentrantLock.isLocked()) {
                C1602c.m7936a("Image already is loading. Waiting... [%s]", this.f4900n);
            }
            reentrantLock.lock();
            try {
                m7915i();
                Bitmap a = this.f4895i.f4852n.mo1217a(this.f4900n);
                if (a == null || a.isRecycled()) {
                    a = m7910d();
                    if (a != null) {
                        m7915i();
                        m7921o();
                        if (this.f4889c.m7772d()) {
                            C1602c.m7936a("PreProcess image before caching in memory [%s]", this.f4900n);
                            a = this.f4889c.m7783o().m7900a(a);
                            if (a == null) {
                                C1602c.m7942d("Pre-processor returned null [%s]", this.f4900n);
                            }
                        }
                        if (a != null && this.f4889c.m7776h()) {
                            C1602c.m7936a("Cache image in memory [%s]", this.f4900n);
                            this.f4895i.f4852n.mo1219a(this.f4900n, a);
                        }
                    } else {
                        return;
                    }
                }
                this.f4903q = C1554f.MEMORY_CACHE;
                C1602c.m7936a("...Get cached bitmap from memory after waiting. [%s]", this.f4900n);
                if (a != null && this.f4889c.m7773e()) {
                    C1602c.m7936a("PostProcess image before displaying [%s]", this.f4900n);
                    a = this.f4889c.m7784p().m7900a(a);
                    if (a == null) {
                        C1602c.m7942d("Post-processor returned null [%s]", this.f4900n);
                    }
                }
                m7915i();
                m7921o();
                reentrantLock.unlock();
                C1597h.m7905a(new C1564b(a, this.f4893g, this.f4892f, this.f4903q), this.f4902p, this.f4894h, this.f4892f);
            } catch (C1595a e) {
                m7913g();
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}
