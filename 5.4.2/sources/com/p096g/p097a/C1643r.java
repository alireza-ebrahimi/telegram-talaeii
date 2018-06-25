package com.p096g.p097a;

import android.graphics.Bitmap;
import android.os.Handler;

/* renamed from: com.g.a.r */
class C1643r {
    /* renamed from: a */
    final C1616c f5060a;
    /* renamed from: b */
    final Handler f5061b;
    /* renamed from: c */
    long f5062c;
    /* renamed from: d */
    long f5063d;
    /* renamed from: e */
    long f5064e;
    /* renamed from: f */
    long f5065f;
    /* renamed from: g */
    long f5066g;
    /* renamed from: h */
    long f5067h;
    /* renamed from: i */
    long f5068i;
    /* renamed from: j */
    long f5069j;
    /* renamed from: k */
    int f5070k;
    /* renamed from: l */
    int f5071l;
    /* renamed from: m */
    int f5072m;

    /* renamed from: a */
    private void m8044a(Bitmap bitmap, int i) {
        this.f5061b.sendMessage(this.f5061b.obtainMessage(i, C1648v.m8055a(bitmap), 0));
    }

    /* renamed from: a */
    void m8045a() {
        this.f5061b.sendEmptyMessage(0);
    }

    /* renamed from: a */
    void m8046a(long j) {
        this.f5061b.sendMessage(this.f5061b.obtainMessage(4, Long.valueOf(j)));
    }

    /* renamed from: a */
    void m8047a(Bitmap bitmap) {
        m8044a(bitmap, 2);
    }

    /* renamed from: b */
    void m8048b() {
        this.f5061b.sendEmptyMessage(1);
    }

    /* renamed from: b */
    void m8049b(Bitmap bitmap) {
        m8044a(bitmap, 3);
    }

    /* renamed from: c */
    C1644s m8050c() {
        return new C1644s(this.f5060a.mo1246b(), this.f5060a.mo1244a(), this.f5062c, this.f5063d, this.f5064e, this.f5065f, this.f5066g, this.f5067h, this.f5068i, this.f5069j, this.f5070k, this.f5071l, this.f5072m, System.currentTimeMillis());
    }
}
