package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.C0201w.C0127d;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Interpolator;

/* renamed from: android.support.design.widget.j */
abstract class C0160j {
    /* renamed from: b */
    static final Interpolator f536b = C0126a.f428c;
    /* renamed from: j */
    static final int[] f537j = new int[]{16842919, 16842910};
    /* renamed from: k */
    static final int[] f538k = new int[]{16842908, 16842910};
    /* renamed from: l */
    static final int[] f539l = new int[]{16842910};
    /* renamed from: m */
    static final int[] f540m = new int[0];
    /* renamed from: a */
    private final Rect f541a = new Rect();
    /* renamed from: c */
    int f542c = 0;
    /* renamed from: d */
    Drawable f543d;
    /* renamed from: e */
    Drawable f544e;
    /* renamed from: f */
    C0148d f545f;
    /* renamed from: g */
    Drawable f546g;
    /* renamed from: h */
    float f547h;
    /* renamed from: i */
    float f548i;
    /* renamed from: n */
    final af f549n;
    /* renamed from: o */
    final C0111p f550o;
    /* renamed from: p */
    final C0127d f551p;
    /* renamed from: q */
    private OnPreDrawListener f552q;

    /* renamed from: android.support.design.widget.j$a */
    interface C0108a {
        /* renamed from: a */
        void mo108a();

        /* renamed from: b */
        void mo109b();
    }

    /* renamed from: android.support.design.widget.j$1 */
    class C01651 implements OnPreDrawListener {
        /* renamed from: a */
        final /* synthetic */ C0160j f563a;

        C01651(C0160j c0160j) {
            this.f563a = c0160j;
        }

        public boolean onPreDraw() {
            this.f563a.mo157e();
            return true;
        }
    }

    C0160j(af afVar, C0111p c0111p, C0127d c0127d) {
        this.f549n = afVar;
        this.f550o = c0111p;
        this.f551p = c0127d;
    }

    /* renamed from: l */
    private void m758l() {
        if (this.f552q == null) {
            this.f552q = new C01651(this);
        }
    }

    /* renamed from: a */
    abstract float mo145a();

    /* renamed from: a */
    final void m760a(float f) {
        if (this.f547h != f) {
            this.f547h = f;
            mo146a(f, this.f548i);
        }
    }

    /* renamed from: a */
    abstract void mo146a(float f, float f2);

    /* renamed from: a */
    abstract void mo147a(int i);

    /* renamed from: a */
    abstract void mo148a(ColorStateList colorStateList);

    /* renamed from: a */
    abstract void mo149a(Mode mode);

    /* renamed from: a */
    abstract void mo150a(Rect rect);

    /* renamed from: a */
    abstract void mo151a(C0108a c0108a, boolean z);

    /* renamed from: a */
    abstract void mo152a(int[] iArr);

    /* renamed from: b */
    abstract void mo153b();

    /* renamed from: b */
    void mo158b(Rect rect) {
    }

    /* renamed from: b */
    abstract void mo154b(C0108a c0108a, boolean z);

    /* renamed from: c */
    abstract void mo155c();

    /* renamed from: d */
    boolean mo156d() {
        return false;
    }

    /* renamed from: e */
    void mo157e() {
    }

    /* renamed from: f */
    final Drawable m774f() {
        return this.f546g;
    }

    /* renamed from: g */
    final void m775g() {
        Rect rect = this.f541a;
        mo150a(rect);
        mo158b(rect);
        this.f550o.mo113a(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* renamed from: h */
    void m776h() {
        if (mo156d()) {
            m758l();
            this.f549n.getViewTreeObserver().addOnPreDrawListener(this.f552q);
        }
    }

    /* renamed from: i */
    void m777i() {
        if (this.f552q != null) {
            this.f549n.getViewTreeObserver().removeOnPreDrawListener(this.f552q);
            this.f552q = null;
        }
    }

    /* renamed from: j */
    boolean m778j() {
        return this.f549n.getVisibility() != 0 ? this.f542c == 2 : this.f542c != 1;
    }

    /* renamed from: k */
    boolean m779k() {
        return this.f549n.getVisibility() == 0 ? this.f542c == 1 : this.f542c != 2;
    }
}
