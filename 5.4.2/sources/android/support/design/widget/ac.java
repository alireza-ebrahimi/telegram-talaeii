package android.support.design.widget;

import android.support.v4.view.ah;
import android.view.View;

class ac {
    /* renamed from: a */
    private final View f433a;
    /* renamed from: b */
    private int f434b;
    /* renamed from: c */
    private int f435c;
    /* renamed from: d */
    private int f436d;
    /* renamed from: e */
    private int f437e;

    public ac(View view) {
        this.f433a = view;
    }

    /* renamed from: c */
    private void m651c() {
        ah.m2808e(this.f433a, this.f436d - (this.f433a.getTop() - this.f434b));
        ah.m2811f(this.f433a, this.f437e - (this.f433a.getLeft() - this.f435c));
    }

    /* renamed from: a */
    public void m652a() {
        this.f434b = this.f433a.getTop();
        this.f435c = this.f433a.getLeft();
        m651c();
    }

    /* renamed from: a */
    public boolean m653a(int i) {
        if (this.f436d == i) {
            return false;
        }
        this.f436d = i;
        m651c();
        return true;
    }

    /* renamed from: b */
    public int m654b() {
        return this.f436d;
    }

    /* renamed from: b */
    public boolean m655b(int i) {
        if (this.f437e == i) {
            return false;
        }
        this.f437e = i;
        m651c();
        return true;
    }
}
