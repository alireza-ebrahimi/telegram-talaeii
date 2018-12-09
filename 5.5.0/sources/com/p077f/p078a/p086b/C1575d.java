package com.p077f.p078a.p086b;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;
import com.p077f.p078a.p086b.p087a.C1553e;
import com.p077f.p078a.p086b.p087a.C1554f;
import com.p077f.p078a.p086b.p087a.C1557h;
import com.p077f.p078a.p086b.p092e.C1580a;
import com.p077f.p078a.p086b.p092e.C1582b;
import com.p077f.p078a.p086b.p092e.C1583c;
import com.p077f.p078a.p086b.p093f.C1586a;
import com.p077f.p078a.p086b.p093f.C1587b;
import com.p077f.p078a.p086b.p093f.C1588c;
import com.p077f.p078a.p095c.C1600a;
import com.p077f.p078a.p095c.C1602c;
import com.p077f.p078a.p095c.C1604d;

/* renamed from: com.f.a.b.d */
public class C1575d {
    /* renamed from: a */
    public static final String f4801a = C1575d.class.getSimpleName();
    /* renamed from: e */
    private static volatile C1575d f4802e;
    /* renamed from: b */
    private C1584e f4803b;
    /* renamed from: c */
    private C1589f f4804c;
    /* renamed from: d */
    private C1586a f4805d = new C1588c();

    protected C1575d() {
    }

    /* renamed from: a */
    private static Handler m7806a(C1570c c1570c) {
        Handler r = c1570c.m7786r();
        return c1570c.m7787s() ? null : (r == null && Looper.myLooper() == Looper.getMainLooper()) ? new Handler() : r;
    }

    /* renamed from: a */
    public static C1575d m7807a() {
        if (f4802e == null) {
            synchronized (C1575d.class) {
                if (f4802e == null) {
                    f4802e = new C1575d();
                }
            }
        }
        return f4802e;
    }

    /* renamed from: b */
    private void m7808b() {
        if (this.f4803b == null) {
            throw new IllegalStateException("ImageLoader must be init with configuration before using");
        }
    }

    /* renamed from: a */
    public synchronized void m7809a(C1584e c1584e) {
        if (c1584e == null) {
            throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
        } else if (this.f4803b == null) {
            C1602c.m7936a("Initialize ImageLoader with configuration", new Object[0]);
            this.f4804c = new C1589f(c1584e);
            this.f4803b = c1584e;
        } else {
            C1602c.m7941c("Try to initialize ImageLoader which had already been initialized before. To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.", new Object[0]);
        }
    }

    /* renamed from: a */
    public void m7810a(String str, ImageView imageView) {
        m7817a(str, new C1582b(imageView), null, null, null);
    }

    /* renamed from: a */
    public void m7811a(String str, ImageView imageView, C1570c c1570c) {
        m7817a(str, new C1582b(imageView), c1570c, null, null);
    }

    /* renamed from: a */
    public void m7812a(String str, ImageView imageView, C1570c c1570c, C1586a c1586a) {
        m7813a(str, imageView, c1570c, c1586a, null);
    }

    /* renamed from: a */
    public void m7813a(String str, ImageView imageView, C1570c c1570c, C1586a c1586a, C1587b c1587b) {
        m7817a(str, new C1582b(imageView), c1570c, c1586a, c1587b);
    }

    /* renamed from: a */
    public void m7814a(String str, C1553e c1553e, C1570c c1570c, C1586a c1586a, C1587b c1587b) {
        m7808b();
        if (c1553e == null) {
            c1553e = this.f4803b.m7882a();
        }
        m7817a(str, new C1583c(str, c1553e, C1557h.CROP), c1570c == null ? this.f4803b.f4856r : c1570c, c1586a, c1587b);
    }

    /* renamed from: a */
    public void m7815a(String str, C1570c c1570c, C1586a c1586a) {
        m7814a(str, null, c1570c, c1586a, null);
    }

    /* renamed from: a */
    public void m7816a(String str, C1580a c1580a, C1570c c1570c, C1553e c1553e, C1586a c1586a, C1587b c1587b) {
        m7808b();
        if (c1580a == null) {
            throw new IllegalArgumentException("Wrong arguments were passed to displayImage() method (ImageView reference must not be null)");
        }
        C1586a c1586a2 = c1586a == null ? this.f4805d : c1586a;
        C1570c c1570c2 = c1570c == null ? this.f4803b.f4856r : c1570c;
        if (TextUtils.isEmpty(str)) {
            this.f4804c.m7897b(c1580a);
            c1586a2.onLoadingStarted(str, c1580a.mo1233d());
            if (c1570c2.m7769b()) {
                c1580a.mo1230a(c1570c2.m7768b(this.f4803b.f4839a));
            } else {
                c1580a.mo1230a(null);
            }
            c1586a2.onLoadingComplete(str, c1580a.mo1233d(), null);
            return;
        }
        C1553e a = c1553e == null ? C1600a.m7928a(c1580a, this.f4803b.m7882a()) : c1553e;
        String a2 = C1604d.m7944a(str, a);
        this.f4804c.m7892a(c1580a, a2);
        c1586a2.onLoadingStarted(str, c1580a.mo1233d());
        Bitmap a3 = this.f4803b.f4852n.mo1217a(a2);
        if (a3 == null || a3.isRecycled()) {
            if (c1570c2.m7767a()) {
                c1580a.mo1230a(c1570c2.m7766a(this.f4803b.f4839a));
            } else if (c1570c2.m7775g()) {
                c1580a.mo1230a(null);
            }
            C1597h c1597h = new C1597h(this.f4804c, new C1591g(str, c1580a, a, a2, c1570c2, c1586a2, c1587b, this.f4804c.m7891a(str)), C1575d.m7806a(c1570c2));
            if (c1570c2.m7787s()) {
                c1597h.run();
                return;
            } else {
                this.f4804c.m7893a(c1597h);
                return;
            }
        }
        C1602c.m7936a("Load image from memory cache [%s]", a2);
        if (c1570c2.m7773e()) {
            C1598i c1598i = new C1598i(this.f4804c, a3, new C1591g(str, c1580a, a, a2, c1570c2, c1586a2, c1587b, this.f4804c.m7891a(str)), C1575d.m7806a(c1570c2));
            if (c1570c2.m7787s()) {
                c1598i.run();
                return;
            } else {
                this.f4804c.m7894a(c1598i);
                return;
            }
        }
        c1570c2.m7785q().mo1226a(a3, c1580a, C1554f.MEMORY_CACHE);
        c1586a2.onLoadingComplete(str, c1580a.mo1233d(), a3);
    }

    /* renamed from: a */
    public void m7817a(String str, C1580a c1580a, C1570c c1570c, C1586a c1586a, C1587b c1587b) {
        m7816a(str, c1580a, c1570c, null, c1586a, c1587b);
    }

    /* renamed from: a */
    public void m7818a(String str, C1586a c1586a) {
        m7814a(str, null, null, c1586a, null);
    }
}
