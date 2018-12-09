package android.support.design.widget;

import android.view.animation.Interpolator;

/* renamed from: android.support.design.widget.w */
class C0201w {
    /* renamed from: a */
    private final C0200e f682a;

    /* renamed from: android.support.design.widget.w$c */
    interface C0083c {
        /* renamed from: a */
        void mo58a(C0201w c0201w);
    }

    /* renamed from: android.support.design.widget.w$d */
    interface C0127d {
        /* renamed from: a */
        C0201w mo123a();
    }

    /* renamed from: android.support.design.widget.w$a */
    interface C0154a {
        /* renamed from: b */
        void mo141b(C0201w c0201w);

        /* renamed from: c */
        void mo142c(C0201w c0201w);

        /* renamed from: d */
        void mo143d(C0201w c0201w);
    }

    /* renamed from: android.support.design.widget.w$b */
    static class C0155b implements C0154a {
        C0155b() {
        }

        /* renamed from: b */
        public void mo141b(C0201w c0201w) {
        }

        /* renamed from: c */
        public void mo142c(C0201w c0201w) {
        }

        /* renamed from: d */
        public void mo143d(C0201w c0201w) {
        }
    }

    /* renamed from: android.support.design.widget.w$e */
    static abstract class C0200e {

        /* renamed from: android.support.design.widget.w$e$b */
        interface C0196b {
            /* renamed from: a */
            void mo171a();
        }

        /* renamed from: android.support.design.widget.w$e$a */
        interface C0198a {
            /* renamed from: a */
            void mo172a();

            /* renamed from: b */
            void mo173b();

            /* renamed from: c */
            void mo174c();
        }

        C0200e() {
        }

        /* renamed from: a */
        abstract void mo175a();

        /* renamed from: a */
        abstract void mo176a(float f, float f2);

        /* renamed from: a */
        abstract void mo177a(int i, int i2);

        /* renamed from: a */
        abstract void mo178a(long j);

        /* renamed from: a */
        abstract void mo179a(C0198a c0198a);

        /* renamed from: a */
        abstract void mo180a(C0196b c0196b);

        /* renamed from: a */
        abstract void mo181a(Interpolator interpolator);

        /* renamed from: b */
        abstract boolean mo182b();

        /* renamed from: c */
        abstract int mo183c();

        /* renamed from: d */
        abstract float mo184d();

        /* renamed from: e */
        abstract void mo185e();

        /* renamed from: f */
        abstract float mo186f();

        /* renamed from: g */
        abstract void mo187g();

        /* renamed from: h */
        abstract long mo188h();
    }

    C0201w(C0200e c0200e) {
        this.f682a = c0200e;
    }

    /* renamed from: a */
    public void m939a() {
        this.f682a.mo175a();
    }

    /* renamed from: a */
    public void m940a(float f, float f2) {
        this.f682a.mo176a(f, f2);
    }

    /* renamed from: a */
    public void m941a(int i, int i2) {
        this.f682a.mo177a(i, i2);
    }

    /* renamed from: a */
    public void m942a(long j) {
        this.f682a.mo178a(j);
    }

    /* renamed from: a */
    public void m943a(final C0154a c0154a) {
        if (c0154a != null) {
            this.f682a.mo179a(new C0198a(this) {
                /* renamed from: b */
                final /* synthetic */ C0201w f681b;

                /* renamed from: a */
                public void mo172a() {
                    c0154a.mo142c(this.f681b);
                }

                /* renamed from: b */
                public void mo173b() {
                    c0154a.mo141b(this.f681b);
                }

                /* renamed from: c */
                public void mo174c() {
                    c0154a.mo143d(this.f681b);
                }
            });
        } else {
            this.f682a.mo179a(null);
        }
    }

    /* renamed from: a */
    public void m944a(final C0083c c0083c) {
        if (c0083c != null) {
            this.f682a.mo180a(new C0196b(this) {
                /* renamed from: b */
                final /* synthetic */ C0201w f679b;

                /* renamed from: a */
                public void mo171a() {
                    c0083c.mo58a(this.f679b);
                }
            });
        } else {
            this.f682a.mo180a(null);
        }
    }

    /* renamed from: a */
    public void m945a(Interpolator interpolator) {
        this.f682a.mo181a(interpolator);
    }

    /* renamed from: b */
    public boolean m946b() {
        return this.f682a.mo182b();
    }

    /* renamed from: c */
    public int m947c() {
        return this.f682a.mo183c();
    }

    /* renamed from: d */
    public float m948d() {
        return this.f682a.mo184d();
    }

    /* renamed from: e */
    public void m949e() {
        this.f682a.mo185e();
    }

    /* renamed from: f */
    public float m950f() {
        return this.f682a.mo186f();
    }

    /* renamed from: g */
    public void m951g() {
        this.f682a.mo187g();
    }

    /* renamed from: h */
    public long m952h() {
        return this.f682a.mo188h();
    }
}
