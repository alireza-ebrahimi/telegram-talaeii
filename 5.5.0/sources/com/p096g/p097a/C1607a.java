package com.p096g.p097a;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.p096g.p097a.C1636m.C1632b;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* renamed from: com.g.a.a */
abstract class C1607a<T> {
    /* renamed from: a */
    final C1636m f4913a;
    /* renamed from: b */
    final C1640o f4914b;
    /* renamed from: c */
    final WeakReference<T> f4915c;
    /* renamed from: d */
    final boolean f4916d;
    /* renamed from: e */
    final int f4917e;
    /* renamed from: f */
    final int f4918f;
    /* renamed from: g */
    final int f4919g;
    /* renamed from: h */
    final Drawable f4920h;
    /* renamed from: i */
    final String f4921i;
    /* renamed from: j */
    final Object f4922j;
    /* renamed from: k */
    boolean f4923k;
    /* renamed from: l */
    boolean f4924l;

    /* renamed from: com.g.a.a$a */
    static class C1606a<M> extends WeakReference<M> {
        /* renamed from: a */
        final C1607a f4912a;

        public C1606a(C1607a c1607a, M m, ReferenceQueue<? super M> referenceQueue) {
            super(m, referenceQueue);
            this.f4912a = c1607a;
        }
    }

    C1607a(C1636m c1636m, T t, C1640o c1640o, int i, int i2, int i3, Drawable drawable, String str, Object obj, boolean z) {
        this.f4913a = c1636m;
        this.f4914b = c1640o;
        this.f4915c = t == null ? null : new C1606a(this, t, c1636m.f4996i);
        this.f4917e = i;
        this.f4918f = i2;
        this.f4916d = z;
        this.f4919g = i3;
        this.f4920h = drawable;
        this.f4921i = str;
        if (obj == null) {
            C1607a c1607a = this;
        }
        this.f4922j = obj;
    }

    /* renamed from: a */
    abstract void mo1247a();

    /* renamed from: a */
    abstract void mo1248a(Bitmap bitmap, C1632b c1632b);

    /* renamed from: b */
    void mo1249b() {
        this.f4924l = true;
    }

    /* renamed from: c */
    T m7955c() {
        return this.f4915c == null ? null : this.f4915c.get();
    }

    /* renamed from: d */
    String m7956d() {
        return this.f4921i;
    }

    /* renamed from: e */
    boolean m7957e() {
        return this.f4924l;
    }

    /* renamed from: f */
    boolean m7958f() {
        return this.f4923k;
    }

    /* renamed from: g */
    C1636m m7959g() {
        return this.f4913a;
    }
}
