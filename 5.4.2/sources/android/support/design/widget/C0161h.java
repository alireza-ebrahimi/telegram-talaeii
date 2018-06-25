package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.support.design.C0073a.C0062a;
import android.support.design.widget.C0126a.C0125a;
import android.support.design.widget.C0160j.C0108a;
import android.support.design.widget.C0201w.C0083c;
import android.support.design.widget.C0201w.C0127d;
import android.support.design.widget.C0201w.C0154a;
import android.support.design.widget.C0201w.C0155b;
import android.support.v4.p007b.p008a.C0375a;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.design.widget.h */
class C0161h extends C0160j {
    /* renamed from: a */
    C0170o f553a;
    /* renamed from: q */
    private final C0177r f554q = new C0177r();

    /* renamed from: android.support.design.widget.h$d */
    private abstract class C0156d extends C0155b implements C0083c {
        /* renamed from: a */
        private boolean f529a;
        /* renamed from: b */
        final /* synthetic */ C0161h f530b;
        /* renamed from: c */
        private float f531c;
        /* renamed from: d */
        private float f532d;

        private C0156d(C0161h c0161h) {
            this.f530b = c0161h;
        }

        /* renamed from: a */
        protected abstract float mo144a();

        /* renamed from: a */
        public void mo58a(C0201w c0201w) {
            if (!this.f529a) {
                this.f531c = this.f530b.f553a.m818a();
                this.f532d = mo144a();
                this.f529a = true;
            }
            this.f530b.f553a.m821b(this.f531c + ((this.f532d - this.f531c) * c0201w.m950f()));
        }

        /* renamed from: b */
        public void mo141b(C0201w c0201w) {
            this.f530b.f553a.m821b(this.f532d);
            this.f529a = false;
        }
    }

    /* renamed from: android.support.design.widget.h$a */
    private class C0157a extends C0156d {
        /* renamed from: a */
        final /* synthetic */ C0161h f533a;

        C0157a(C0161h c0161h) {
            this.f533a = c0161h;
            super();
        }

        /* renamed from: a */
        protected float mo144a() {
            return BitmapDescriptorFactory.HUE_RED;
        }
    }

    /* renamed from: android.support.design.widget.h$b */
    private class C0158b extends C0156d {
        /* renamed from: a */
        final /* synthetic */ C0161h f534a;

        C0158b(C0161h c0161h) {
            this.f534a = c0161h;
            super();
        }

        /* renamed from: a */
        protected float mo144a() {
            return this.f534a.h + this.f534a.i;
        }
    }

    /* renamed from: android.support.design.widget.h$c */
    private class C0159c extends C0156d {
        /* renamed from: a */
        final /* synthetic */ C0161h f535a;

        C0159c(C0161h c0161h) {
            this.f535a = c0161h;
            super();
        }

        /* renamed from: a */
        protected float mo144a() {
            return this.f535a.h;
        }
    }

    C0161h(af afVar, C0111p c0111p, C0127d c0127d) {
        super(afVar, c0111p, c0127d);
        this.f554q.m843a(j, m780a(new C0158b(this)));
        this.f554q.m843a(k, m780a(new C0158b(this)));
        this.f554q.m843a(l, m780a(new C0159c(this)));
        this.f554q.m843a(m, m780a(new C0157a(this)));
    }

    /* renamed from: a */
    private C0201w m780a(C0156d c0156d) {
        C0201w a = this.p.mo123a();
        a.m945a(b);
        a.m942a(100);
        a.m943a((C0154a) c0156d);
        a.m944a((C0083c) c0156d);
        a.m940a((float) BitmapDescriptorFactory.HUE_RED, 1.0f);
        return a;
    }

    /* renamed from: b */
    private static ColorStateList m781b(int i) {
        r0 = new int[3][];
        int[] iArr = new int[]{k, i, j};
        iArr[1] = i;
        r0[2] = new int[0];
        iArr[2] = 0;
        return new ColorStateList(r0, iArr);
    }

    /* renamed from: a */
    float mo145a() {
        return this.h;
    }

    /* renamed from: a */
    void mo146a(float f, float f2) {
        if (this.f553a != null) {
            this.f553a.m820a(f, this.i + f);
            m775g();
        }
    }

    /* renamed from: a */
    void mo147a(int i) {
        if (this.e != null) {
            C0375a.m1773a(this.e, C0161h.m781b(i));
        }
    }

    /* renamed from: a */
    void mo148a(ColorStateList colorStateList) {
        if (this.d != null) {
            C0375a.m1773a(this.d, colorStateList);
        }
        if (this.f != null) {
            this.f.m693a(colorStateList);
        }
    }

    /* renamed from: a */
    void mo149a(Mode mode) {
        if (this.d != null) {
            C0375a.m1776a(this.d, mode);
        }
    }

    /* renamed from: a */
    void mo150a(Rect rect) {
        this.f553a.getPadding(rect);
    }

    /* renamed from: a */
    void mo151a(final C0108a c0108a, final boolean z) {
        if (!m779k()) {
            this.c = 1;
            Animation loadAnimation = AnimationUtils.loadAnimation(this.n.getContext(), C0062a.design_fab_out);
            loadAnimation.setInterpolator(C0126a.f428c);
            loadAnimation.setDuration(200);
            loadAnimation.setAnimationListener(new C0125a(this) {
                /* renamed from: c */
                final /* synthetic */ C0161h f526c;

                public void onAnimationEnd(Animation animation) {
                    this.f526c.c = 0;
                    this.f526c.n.m582a(z ? 8 : 4, z);
                    if (c0108a != null) {
                        c0108a.mo109b();
                    }
                }
            });
            this.n.startAnimation(loadAnimation);
        }
    }

    /* renamed from: a */
    void mo152a(int[] iArr) {
        this.f554q.m842a(iArr);
    }

    /* renamed from: b */
    void mo153b() {
        this.f554q.m841a();
    }

    /* renamed from: b */
    void mo154b(final C0108a c0108a, boolean z) {
        if (!m778j()) {
            this.c = 2;
            this.n.m582a(0, z);
            Animation loadAnimation = AnimationUtils.loadAnimation(this.n.getContext(), C0062a.design_fab_in);
            loadAnimation.setDuration(200);
            loadAnimation.setInterpolator(C0126a.f429d);
            loadAnimation.setAnimationListener(new C0125a(this) {
                /* renamed from: b */
                final /* synthetic */ C0161h f528b;

                public void onAnimationEnd(Animation animation) {
                    this.f528b.c = 0;
                    if (c0108a != null) {
                        c0108a.mo108a();
                    }
                }
            });
            this.n.startAnimation(loadAnimation);
        }
    }

    /* renamed from: c */
    void mo155c() {
    }
}
