package org.telegram.customization.speechrecognitionview;

import android.graphics.RectF;

/* renamed from: org.telegram.customization.speechrecognitionview.a */
public class C2843a {
    /* renamed from: a */
    private int f9342a;
    /* renamed from: b */
    private int f9343b;
    /* renamed from: c */
    private int f9344c;
    /* renamed from: d */
    private int f9345d;
    /* renamed from: e */
    private final int f9346e;
    /* renamed from: f */
    private final int f9347f;
    /* renamed from: g */
    private final int f9348g;
    /* renamed from: h */
    private final RectF f9349h;

    public C2843a(int i, int i2, int i3, int i4, int i5) {
        this.f9342a = i;
        this.f9343b = i2;
        this.f9344c = i5;
        this.f9347f = i;
        this.f9348g = i2;
        this.f9345d = i3;
        this.f9346e = i4;
        this.f9349h = new RectF((float) (i - i5), (float) (i2 - (i3 / 2)), (float) (i + i5), (float) ((i3 / 2) + i2));
    }

    /* renamed from: a */
    public void m13216a() {
        this.f9349h.set((float) (this.f9342a - this.f9344c), (float) (this.f9343b - (this.f9345d / 2)), (float) (this.f9342a + this.f9344c), (float) (this.f9343b + (this.f9345d / 2)));
    }

    /* renamed from: a */
    public void m13217a(int i) {
        this.f9342a = i;
    }

    /* renamed from: b */
    public int m13218b() {
        return this.f9342a;
    }

    /* renamed from: b */
    public void m13219b(int i) {
        this.f9343b = i;
    }

    /* renamed from: c */
    public int m13220c() {
        return this.f9343b;
    }

    /* renamed from: c */
    public void m13221c(int i) {
        this.f9345d = i;
    }

    /* renamed from: d */
    public int m13222d() {
        return this.f9345d;
    }

    /* renamed from: e */
    public int m13223e() {
        return this.f9346e;
    }

    /* renamed from: f */
    public int m13224f() {
        return this.f9347f;
    }

    /* renamed from: g */
    public int m13225g() {
        return this.f9348g;
    }

    /* renamed from: h */
    public RectF m13226h() {
        return this.f9349h;
    }

    /* renamed from: i */
    public int m13227i() {
        return this.f9344c;
    }
}
