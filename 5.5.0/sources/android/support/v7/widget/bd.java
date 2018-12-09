package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.C0933e;
import android.support.v7.widget.RecyclerView.C0933e.C0932c;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.View;

public abstract class bd extends C0933e {
    /* renamed from: h */
    boolean f2825h = true;

    /* renamed from: a */
    public final void m5386a(C0955v c0955v, boolean z) {
        m5397d(c0955v, z);
        m4840f(c0955v);
    }

    /* renamed from: a */
    public abstract boolean mo923a(C0955v c0955v);

    /* renamed from: a */
    public abstract boolean mo924a(C0955v c0955v, int i, int i2, int i3, int i4);

    /* renamed from: a */
    public boolean mo917a(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
        int i = c0932c.f2480a;
        int i2 = c0932c.f2481b;
        View view = c0955v.itemView;
        int left = c0932c2 == null ? view.getLeft() : c0932c2.f2480a;
        int top = c0932c2 == null ? view.getTop() : c0932c2.f2481b;
        if (c0955v.isRemoved() || (i == left && i2 == top)) {
            return mo923a(c0955v);
        }
        view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
        return mo924a(c0955v, i, i2, left, top);
    }

    /* renamed from: a */
    public abstract boolean mo925a(C0955v c0955v, C0955v c0955v2, int i, int i2, int i3, int i4);

    /* renamed from: a */
    public boolean mo918a(C0955v c0955v, C0955v c0955v2, C0932c c0932c, C0932c c0932c2) {
        int i;
        int i2;
        int i3 = c0932c.f2480a;
        int i4 = c0932c.f2481b;
        if (c0955v2.shouldIgnore()) {
            i = c0932c.f2480a;
            i2 = c0932c.f2481b;
        } else {
            i = c0932c2.f2480a;
            i2 = c0932c2.f2481b;
        }
        return mo925a(c0955v, c0955v2, i3, i4, i, i2);
    }

    /* renamed from: b */
    public final void m5392b(C0955v c0955v, boolean z) {
        m5395c(c0955v, z);
    }

    /* renamed from: b */
    public abstract boolean mo928b(C0955v c0955v);

    /* renamed from: b */
    public boolean mo919b(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
        if (c0932c == null || (c0932c.f2480a == c0932c2.f2480a && c0932c.f2481b == c0932c2.f2481b)) {
            return mo928b(c0955v);
        }
        return mo924a(c0955v, c0932c.f2480a, c0932c.f2481b, c0932c2.f2480a, c0932c2.f2481b);
    }

    /* renamed from: c */
    public void m5395c(C0955v c0955v, boolean z) {
    }

    /* renamed from: c */
    public boolean mo920c(C0955v c0955v, C0932c c0932c, C0932c c0932c2) {
        if (c0932c.f2480a == c0932c2.f2480a && c0932c.f2481b == c0932c2.f2481b) {
            m5400j(c0955v);
            return false;
        }
        return mo924a(c0955v, c0932c.f2480a, c0932c.f2481b, c0932c2.f2480a, c0932c2.f2481b);
    }

    /* renamed from: d */
    public void m5397d(C0955v c0955v, boolean z) {
    }

    /* renamed from: h */
    public boolean mo921h(C0955v c0955v) {
        return !this.f2825h || c0955v.isInvalid();
    }

    /* renamed from: i */
    public final void m5399i(C0955v c0955v) {
        m5406p(c0955v);
        m4840f(c0955v);
    }

    /* renamed from: j */
    public final void m5400j(C0955v c0955v) {
        m5410t(c0955v);
        m4840f(c0955v);
    }

    /* renamed from: k */
    public final void m5401k(C0955v c0955v) {
        m5408r(c0955v);
        m4840f(c0955v);
    }

    /* renamed from: l */
    public final void m5402l(C0955v c0955v) {
        m5405o(c0955v);
    }

    /* renamed from: m */
    public final void m5403m(C0955v c0955v) {
        m5409s(c0955v);
    }

    /* renamed from: n */
    public final void m5404n(C0955v c0955v) {
        m5407q(c0955v);
    }

    /* renamed from: o */
    public void m5405o(C0955v c0955v) {
    }

    /* renamed from: p */
    public void m5406p(C0955v c0955v) {
    }

    /* renamed from: q */
    public void m5407q(C0955v c0955v) {
    }

    /* renamed from: r */
    public void m5408r(C0955v c0955v) {
    }

    /* renamed from: s */
    public void m5409s(C0955v c0955v) {
    }

    /* renamed from: t */
    public void m5410t(C0955v c0955v) {
    }
}
