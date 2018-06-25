package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.C0947o;
import android.support.v7.widget.RecyclerView.C0952s;
import android.view.View;

class an {
    /* renamed from: a */
    boolean f2873a = true;
    /* renamed from: b */
    int f2874b;
    /* renamed from: c */
    int f2875c;
    /* renamed from: d */
    int f2876d;
    /* renamed from: e */
    int f2877e;
    /* renamed from: f */
    int f2878f = 0;
    /* renamed from: g */
    int f2879g = 0;
    /* renamed from: h */
    boolean f2880h;
    /* renamed from: i */
    boolean f2881i;

    an() {
    }

    /* renamed from: a */
    View m5464a(C0947o c0947o) {
        View c = c0947o.m4913c(this.f2875c);
        this.f2875c += this.f2876d;
        return c;
    }

    /* renamed from: a */
    boolean m5465a(C0952s c0952s) {
        return this.f2875c >= 0 && this.f2875c < c0952s.m4959e();
    }

    public String toString() {
        return "LayoutState{mAvailable=" + this.f2874b + ", mCurrentPosition=" + this.f2875c + ", mItemDirection=" + this.f2876d + ", mLayoutDirection=" + this.f2877e + ", mStartLine=" + this.f2878f + ", mEndLine=" + this.f2879g + '}';
    }
}
