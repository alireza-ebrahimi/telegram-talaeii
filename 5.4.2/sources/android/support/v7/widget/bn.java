package android.support.v7.widget;

import android.view.View;

class bn {
    /* renamed from: a */
    final C0936b f3074a;
    /* renamed from: b */
    C1042a f3075b = new C1042a();

    /* renamed from: android.support.v7.widget.bn$b */
    interface C0936b {
        /* renamed from: a */
        int mo870a();

        /* renamed from: a */
        int mo871a(View view);

        /* renamed from: a */
        View mo872a(int i);

        /* renamed from: b */
        int mo873b();

        /* renamed from: b */
        int mo874b(View view);
    }

    /* renamed from: android.support.v7.widget.bn$a */
    static class C1042a {
        /* renamed from: a */
        int f3069a = 0;
        /* renamed from: b */
        int f3070b;
        /* renamed from: c */
        int f3071c;
        /* renamed from: d */
        int f3072d;
        /* renamed from: e */
        int f3073e;

        C1042a() {
        }

        /* renamed from: a */
        int m5718a(int i, int i2) {
            return i > i2 ? 1 : i == i2 ? 2 : 4;
        }

        /* renamed from: a */
        void m5719a() {
            this.f3069a = 0;
        }

        /* renamed from: a */
        void m5720a(int i) {
            this.f3069a |= i;
        }

        /* renamed from: a */
        void m5721a(int i, int i2, int i3, int i4) {
            this.f3070b = i;
            this.f3071c = i2;
            this.f3072d = i3;
            this.f3073e = i4;
        }

        /* renamed from: b */
        boolean m5722b() {
            return ((this.f3069a & 7) == 0 || (this.f3069a & (m5718a(this.f3072d, this.f3070b) << 0)) != 0) ? ((this.f3069a & 112) == 0 || (this.f3069a & (m5718a(this.f3072d, this.f3071c) << 4)) != 0) ? ((this.f3069a & 1792) == 0 || (this.f3069a & (m5718a(this.f3073e, this.f3070b) << 8)) != 0) ? (this.f3069a & 28672) == 0 || (this.f3069a & (m5718a(this.f3073e, this.f3071c) << 12)) != 0 : false : false : false;
        }
    }

    bn(C0936b c0936b) {
        this.f3074a = c0936b;
    }

    /* renamed from: a */
    View m5723a(int i, int i2, int i3, int i4) {
        int a = this.f3074a.mo870a();
        int b = this.f3074a.mo873b();
        int i5 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            View a2 = this.f3074a.mo872a(i);
            this.f3075b.m5721a(a, b, this.f3074a.mo871a(a2), this.f3074a.mo874b(a2));
            if (i3 != 0) {
                this.f3075b.m5719a();
                this.f3075b.m5720a(i3);
                if (this.f3075b.m5722b()) {
                    return a2;
                }
            }
            if (i4 != 0) {
                this.f3075b.m5719a();
                this.f3075b.m5720a(i4);
                if (this.f3075b.m5722b()) {
                    i += i5;
                    view = a2;
                }
            }
            a2 = view;
            i += i5;
            view = a2;
        }
        return view;
    }

    /* renamed from: a */
    boolean m5724a(View view, int i) {
        this.f3075b.m5721a(this.f3074a.mo870a(), this.f3074a.mo873b(), this.f3074a.mo871a(view), this.f3074a.mo874b(view));
        if (i == 0) {
            return false;
        }
        this.f3075b.m5719a();
        this.f3075b.m5720a(i);
        return this.f3075b.m5722b();
    }
}
