package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.view.p023a.C0531e.C0530m;
import android.support.v7.widget.LinearLayoutManager.C0913a;
import android.support.v7.widget.LinearLayoutManager.C0914b;
import android.support.v7.widget.LinearLayoutManager.C0915c;
import android.support.v7.widget.RecyclerView.C0908i;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0910h.C0939a;
import android.support.v7.widget.RecyclerView.C0947o;
import android.support.v7.widget.RecyclerView.C0952s;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {
    /* renamed from: a */
    boolean f2442a = false;
    /* renamed from: b */
    int f2443b = -1;
    /* renamed from: c */
    int[] f2444c;
    /* renamed from: d */
    View[] f2445d;
    /* renamed from: e */
    final SparseIntArray f2446e = new SparseIntArray();
    /* renamed from: f */
    final SparseIntArray f2447f = new SparseIntArray();
    /* renamed from: g */
    C0906c f2448g = new C0907a();
    /* renamed from: h */
    final Rect f2449h = new Rect();

    /* renamed from: android.support.v7.widget.GridLayoutManager$c */
    public static abstract class C0906c {
        /* renamed from: a */
        final SparseIntArray f2401a = new SparseIntArray();
        /* renamed from: b */
        private boolean f2402b = false;

        /* renamed from: a */
        public abstract int mo800a(int i);

        /* renamed from: a */
        public int mo801a(int i, int i2) {
            int a = mo800a(i);
            if (a == i2) {
                return 0;
            }
            int b;
            int a2;
            int i3;
            if (this.f2402b && this.f2401a.size() > 0) {
                b = m4468b(i);
                if (b >= 0) {
                    a2 = this.f2401a.get(b) + mo800a(b);
                    b++;
                    i3 = b;
                    while (i3 < i) {
                        b = mo800a(i3);
                        a2 += b;
                        if (a2 == i2) {
                            b = 0;
                        } else if (a2 <= i2) {
                            b = a2;
                        }
                        i3++;
                        a2 = b;
                    }
                    return a2 + a > i2 ? a2 : 0;
                }
            }
            b = 0;
            a2 = 0;
            i3 = b;
            while (i3 < i) {
                b = mo800a(i3);
                a2 += b;
                if (a2 == i2) {
                    b = 0;
                } else if (a2 <= i2) {
                    b = a2;
                }
                i3++;
                a2 = b;
            }
            if (a2 + a > i2) {
            }
        }

        /* renamed from: a */
        public void m4467a() {
            this.f2401a.clear();
        }

        /* renamed from: b */
        int m4468b(int i) {
            int i2 = 0;
            int size = this.f2401a.size() - 1;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (this.f2401a.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            size = i2 - 1;
            return (size < 0 || size >= this.f2401a.size()) ? -1 : this.f2401a.keyAt(size);
        }

        /* renamed from: b */
        int m4469b(int i, int i2) {
            if (!this.f2402b) {
                return mo801a(i, i2);
            }
            int i3 = this.f2401a.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            i3 = mo801a(i, i2);
            this.f2401a.put(i, i3);
            return i3;
        }

        /* renamed from: c */
        public int m4470c(int i, int i2) {
            int a = mo800a(i);
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i3 < i) {
                int a2 = mo800a(i3);
                i5 += a2;
                if (i5 == i2) {
                    i4++;
                    a2 = 0;
                } else if (i5 > i2) {
                    i4++;
                } else {
                    a2 = i5;
                }
                i3++;
                i5 = a2;
            }
            return i5 + a > i2 ? i4 + 1 : i4;
        }
    }

    /* renamed from: android.support.v7.widget.GridLayoutManager$a */
    public static final class C0907a extends C0906c {
        /* renamed from: a */
        public int mo800a(int i) {
            return 1;
        }

        /* renamed from: a */
        public int mo801a(int i, int i2) {
            return i % i2;
        }
    }

    /* renamed from: android.support.v7.widget.GridLayoutManager$b */
    public static class C0909b extends C0908i {
        /* renamed from: a */
        int f2407a = -1;
        /* renamed from: b */
        int f2408b = 0;

        public C0909b(int i, int i2) {
            super(i, i2);
        }

        public C0909b(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public C0909b(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public C0909b(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        /* renamed from: a */
        public int m4477a() {
            return this.f2407a;
        }

        /* renamed from: b */
        public int m4478b() {
            return this.f2408b;
        }
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        m4722a(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        m4722a(i);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        m4722a(C0910h.m4481a(context, attributeSet, i, i2).f2494b);
    }

    /* renamed from: M */
    private void m4700M() {
        this.f2446e.clear();
        this.f2447f.clear();
    }

    /* renamed from: N */
    private void m4701N() {
        int w = m4615w();
        for (int i = 0; i < w; i++) {
            C0909b c0909b = (C0909b) m4596h(i).getLayoutParams();
            int f = c0909b.m4476f();
            this.f2446e.put(f, c0909b.m4478b());
            this.f2447f.put(f, c0909b.m4477a());
        }
    }

    /* renamed from: O */
    private void m4702O() {
        m4713l(m4687f() == 1 ? (m4618z() - m4493D()) - m4491B() : (m4490A() - m4494E()) - m4492C());
    }

    /* renamed from: P */
    private void m4703P() {
        if (this.f2445d == null || this.f2445d.length != this.f2443b) {
            this.f2445d = new View[this.f2443b];
        }
    }

    /* renamed from: a */
    private int m4704a(C0947o c0947o, C0952s c0952s, int i) {
        if (!c0952s.m4955a()) {
            return this.f2448g.m4470c(i, this.f2443b);
        }
        int b = c0947o.m4907b(i);
        if (b != -1) {
            return this.f2448g.m4470c(b, this.f2443b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
        return 0;
    }

    /* renamed from: a */
    private void m4705a(float f, int i) {
        m4713l(Math.max(Math.round(((float) this.f2443b) * f), i));
    }

    /* renamed from: a */
    private void m4706a(C0947o c0947o, C0952s c0952s, int i, int i2, boolean z) {
        int i3;
        int i4;
        if (z) {
            i3 = 1;
            i4 = 0;
        } else {
            i3 = i - 1;
            i = -1;
            i4 = i3;
            i3 = -1;
        }
        int i5 = 0;
        for (int i6 = i4; i6 != i; i6 += i3) {
            View view = this.f2445d[i6];
            C0909b c0909b = (C0909b) view.getLayoutParams();
            c0909b.f2408b = m4712c(c0947o, c0952s, m4573d(view));
            c0909b.f2407a = i5;
            i5 += c0909b.f2408b;
        }
    }

    /* renamed from: a */
    private void m4707a(View view, int i, int i2, boolean z) {
        C0908i c0908i = (C0908i) view.getLayoutParams();
        if (z ? m4546a(view, i, i2, c0908i) : m4560b(view, i, i2, c0908i)) {
            view.measure(i, i2);
        }
    }

    /* renamed from: a */
    private void m4708a(View view, int i, boolean z) {
        int a;
        C0909b c0909b = (C0909b) view.getLayoutParams();
        Rect rect = c0909b.d;
        int i2 = ((rect.top + rect.bottom) + c0909b.topMargin) + c0909b.bottomMargin;
        int i3 = c0909b.rightMargin + ((rect.right + rect.left) + c0909b.leftMargin);
        int a2 = mo828a(c0909b.f2407a, c0909b.f2408b);
        if (this.i == 1) {
            a2 = C0910h.m4480a(a2, i, i3, c0909b.width, false);
            a = C0910h.m4480a(this.j.mo953f(), m4617y(), i2, c0909b.height, true);
        } else {
            int a3 = C0910h.m4480a(a2, i, i2, c0909b.height, false);
            a2 = C0910h.m4480a(this.j.mo953f(), m4616x(), i3, c0909b.width, true);
            a = a3;
        }
        m4707a(view, a2, a, z);
    }

    /* renamed from: a */
    static int[] m4709a(int[] iArr, int i, int i2) {
        int i3 = 0;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        iArr[0] = 0;
        int i4 = i2 / i;
        int i5 = i2 % i;
        int i6 = 0;
        for (int i7 = 1; i7 <= i; i7++) {
            int i8;
            i3 += i5;
            if (i3 <= 0 || i - i3 >= i5) {
                i8 = i4;
            } else {
                i8 = i4 + 1;
                i3 -= i;
            }
            i6 += i8;
            iArr[i7] = i6;
        }
        return iArr;
    }

    /* renamed from: b */
    private int m4710b(C0947o c0947o, C0952s c0952s, int i) {
        if (!c0952s.m4955a()) {
            return this.f2448g.m4469b(i, this.f2443b);
        }
        int i2 = this.f2447f.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        i2 = c0947o.m4907b(i);
        if (i2 != -1) {
            return this.f2448g.m4469b(i2, this.f2443b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 0;
    }

    /* renamed from: b */
    private void m4711b(C0947o c0947o, C0952s c0952s, C0913a c0913a, int i) {
        Object obj = 1;
        if (i != 1) {
            obj = null;
        }
        int b = m4710b(c0947o, c0952s, c0913a.f2453a);
        if (obj != null) {
            while (b > 0 && c0913a.f2453a > 0) {
                c0913a.f2453a--;
                b = m4710b(c0947o, c0952s, c0913a.f2453a);
            }
            return;
        }
        int e = c0952s.m4959e() - 1;
        int i2 = c0913a.f2453a;
        int i3 = b;
        while (i2 < e) {
            b = m4710b(c0947o, c0952s, i2 + 1);
            if (b <= i3) {
                break;
            }
            i2++;
            i3 = b;
        }
        c0913a.f2453a = i2;
    }

    /* renamed from: c */
    private int m4712c(C0947o c0947o, C0952s c0952s, int i) {
        if (!c0952s.m4955a()) {
            return this.f2448g.mo800a(i);
        }
        int i2 = this.f2446e.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        i2 = c0947o.m4907b(i);
        if (i2 != -1) {
            return this.f2448g.mo800a(i2);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 1;
    }

    /* renamed from: l */
    private void m4713l(int i) {
        this.f2444c = m4709a(this.f2444c, this.f2443b, i);
    }

    /* renamed from: a */
    int mo828a(int i, int i2) {
        return (this.i == 1 && m4690g()) ? this.f2444c[this.f2443b - i] - this.f2444c[(this.f2443b - i) - i2] : this.f2444c[i + i2] - this.f2444c[i];
    }

    /* renamed from: a */
    public int mo802a(int i, C0947o c0947o, C0952s c0952s) {
        m4702O();
        m4703P();
        return super.mo802a(i, c0947o, c0952s);
    }

    /* renamed from: a */
    public int mo829a(C0947o c0947o, C0952s c0952s) {
        return this.i == 0 ? this.f2443b : c0952s.m4959e() < 1 ? 0 : m4704a(c0947o, c0952s, c0952s.m4959e() - 1) + 1;
    }

    /* renamed from: a */
    public C0908i mo803a() {
        return this.i == 0 ? new C0909b(-2, -1) : new C0909b(-1, -2);
    }

    /* renamed from: a */
    public C0908i mo830a(Context context, AttributeSet attributeSet) {
        return new C0909b(context, attributeSet);
    }

    /* renamed from: a */
    public C0908i mo831a(LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams ? new C0909b((MarginLayoutParams) layoutParams) : new C0909b(layoutParams);
    }

    /* renamed from: a */
    View mo832a(C0947o c0947o, C0952s c0952s, int i, int i2, int i3) {
        View view = null;
        m4692h();
        int c = this.j.mo947c();
        int d = this.j.mo949d();
        int i4 = i2 > i ? 1 : -1;
        View view2 = null;
        while (i != i2) {
            View view3;
            View h = m4596h(i);
            int d2 = m4573d(h);
            if (d2 >= 0 && d2 < i3) {
                if (m4710b(c0947o, c0952s, d2) != 0) {
                    view3 = view;
                    h = view2;
                } else if (((C0908i) h.getLayoutParams()).m4474d()) {
                    if (view2 == null) {
                        view3 = view;
                    }
                } else if (this.j.mo944a(h) < d && this.j.mo946b(h) >= c) {
                    return h;
                } else {
                    if (view == null) {
                        view3 = h;
                        h = view2;
                    }
                }
                i += i4;
                view = view3;
                view2 = h;
            }
            view3 = view;
            h = view2;
            i += i4;
            view = view3;
            view2 = h;
        }
        if (view == null) {
            view = view2;
        }
        return view;
    }

    /* renamed from: a */
    public View mo804a(View view, int i, C0947o c0947o, C0952s c0952s) {
        View e = m4580e(view);
        if (e == null) {
            return null;
        }
        C0909b c0909b = (C0909b) e.getLayoutParams();
        int i2 = c0909b.f2407a;
        int i3 = c0909b.f2407a + c0909b.f2408b;
        if (super.mo804a(view, i, c0947o, c0952s) == null) {
            return null;
        }
        int w;
        int i4;
        int i5;
        if (((m4684e(i) == 1) != this.k ? 1 : null) != null) {
            w = m4615w() - 1;
            i4 = -1;
            i5 = -1;
        } else {
            w = 0;
            i4 = 1;
            i5 = m4615w();
        }
        Object obj = (this.i == 1 && m4690g()) ? 1 : null;
        View view2 = null;
        int i6 = -1;
        int i7 = 0;
        View view3 = null;
        int i8 = -1;
        int i9 = 0;
        int a = m4704a(c0947o, c0952s, w);
        int i10 = w;
        while (i10 != i5) {
            w = m4704a(c0947o, c0952s, i10);
            View h = m4596h(i10);
            if (h == e) {
                break;
            }
            View view4;
            int min;
            View view5;
            int i11;
            if (h.hasFocusable() && w != a) {
                if (view2 != null) {
                    break;
                }
            }
            c0909b = (C0909b) h.getLayoutParams();
            int i12 = c0909b.f2407a;
            int i13 = c0909b.f2407a + c0909b.f2408b;
            if (h.hasFocusable() && i12 == i2 && i13 == i3) {
                return h;
            }
            Object obj2 = null;
            if (!(h.hasFocusable() && view2 == null) && (h.hasFocusable() || view3 != null)) {
                int min2 = Math.min(i13, i3) - Math.max(i12, i2);
                if (h.hasFocusable()) {
                    if (min2 > i7) {
                        obj2 = 1;
                    } else if (min2 == i7) {
                        if (obj == (i12 > i6 ? 1 : null)) {
                            obj2 = 1;
                        }
                    }
                } else if (view2 == null && m4548a(h, false, true)) {
                    if (min2 > i9) {
                        obj2 = 1;
                    } else if (min2 == i9) {
                        if (obj == (i12 > i8 ? 1 : null)) {
                            obj2 = 1;
                        }
                    }
                }
            } else {
                obj2 = 1;
            }
            if (obj2 != null) {
                if (h.hasFocusable()) {
                    i7 = c0909b.f2407a;
                    int i14 = i9;
                    i9 = i8;
                    view4 = view3;
                    min = Math.min(i13, i3) - Math.max(i12, i2);
                    w = i14;
                    int i15 = i7;
                    view5 = h;
                    i11 = i15;
                } else {
                    i9 = c0909b.f2407a;
                    w = Math.min(i13, i3) - Math.max(i12, i2);
                    view4 = h;
                    min = i7;
                    i11 = i6;
                    view5 = view2;
                }
                i10 += i4;
                view2 = view5;
                i7 = min;
                i6 = i11;
                view3 = view4;
                i8 = i9;
                i9 = w;
            }
            w = i9;
            i11 = i6;
            i9 = i8;
            view4 = view3;
            min = i7;
            view5 = view2;
            i10 += i4;
            view2 = view5;
            i7 = min;
            i6 = i11;
            view3 = view4;
            i8 = i9;
            i9 = w;
        }
        if (view2 == null) {
            view2 = view3;
        }
        return view2;
    }

    /* renamed from: a */
    public void m4722a(int i) {
        if (i != this.f2443b) {
            this.f2442a = true;
            if (i < 1) {
                throw new IllegalArgumentException("Span count should be at least 1. Provided " + i);
            }
            this.f2443b = i;
            this.f2448g.m4467a();
            m4608p();
        }
    }

    /* renamed from: a */
    public void mo833a(Rect rect, int i, int i2) {
        if (this.f2444c == null) {
            super.mo833a(rect, i, i2);
        }
        int D = m4493D() + m4491B();
        int C = m4492C() + m4494E();
        if (this.i == 1) {
            C = C0910h.m4479a(i2, C + rect.height(), m4498I());
            D = C0910h.m4479a(i, D + this.f2444c[this.f2444c.length - 1], m4497H());
        } else {
            D = C0910h.m4479a(i, D + rect.width(), m4497H());
            C = C0910h.m4479a(i2, C + this.f2444c[this.f2444c.length - 1], m4498I());
        }
        m4593g(D, C);
    }

    /* renamed from: a */
    void mo834a(C0947o c0947o, C0952s c0952s, C0913a c0913a, int i) {
        super.mo834a(c0947o, c0952s, c0913a, i);
        m4702O();
        if (c0952s.m4959e() > 0 && !c0952s.m4955a()) {
            m4711b(c0947o, c0952s, c0913a, i);
        }
        m4703P();
    }

    /* renamed from: a */
    void mo835a(C0947o c0947o, C0952s c0952s, C0915c c0915c, C0914b c0914b) {
        int i = this.j.mo957i();
        Object obj = i != 1073741824 ? 1 : null;
        int i2 = m4615w() > 0 ? this.f2444c[this.f2443b] : 0;
        if (obj != null) {
            m4702O();
        }
        boolean z = c0915c.f2466e == 1;
        int i3 = 0;
        int i4 = 0;
        int i5 = this.f2443b;
        if (!z) {
            i5 = m4710b(c0947o, c0952s, c0915c.f2465d) + m4712c(c0947o, c0952s, c0915c.f2465d);
        }
        while (i3 < this.f2443b && c0915c.m4754a(c0952s) && i5 > 0) {
            int i6 = c0915c.f2465d;
            int c = m4712c(c0947o, c0952s, i6);
            if (c <= this.f2443b) {
                i5 -= c;
                if (i5 >= 0) {
                    View a = c0915c.m4751a(c0947o);
                    if (a == null) {
                        break;
                    }
                    i4 += c;
                    this.f2445d[i3] = a;
                    i3++;
                } else {
                    break;
                }
            }
            throw new IllegalArgumentException("Item at position " + i6 + " requires " + c + " spans but GridLayoutManager has only " + this.f2443b + " spans.");
        }
        if (i3 == 0) {
            c0914b.f2459b = true;
            return;
        }
        C0909b c0909b;
        int i7;
        int a2;
        m4706a(c0947o, c0952s, i3, i4, z);
        i4 = 0;
        float f = BitmapDescriptorFactory.HUE_RED;
        i6 = 0;
        while (i4 < i3) {
            View view = this.f2445d[i4];
            if (c0915c.f2472k == null) {
                if (z) {
                    m4556b(view);
                } else {
                    m4557b(view, 0);
                }
            } else if (z) {
                m4526a(view);
            } else {
                m4527a(view, 0);
            }
            m4558b(view, this.f2449h);
            m4708a(view, i, false);
            i5 = this.j.mo952e(view);
            if (i5 > i6) {
                i6 = i5;
            }
            float f2 = (((float) this.j.mo954f(view)) * 1.0f) / ((float) ((C0909b) view.getLayoutParams()).f2408b);
            if (f2 <= f) {
                f2 = f;
            }
            i4++;
            f = f2;
        }
        if (obj != null) {
            m4705a(f, i2);
            i6 = 0;
            c = 0;
            while (c < i3) {
                View view2 = this.f2445d[c];
                m4708a(view2, 1073741824, true);
                i5 = this.j.mo952e(view2);
                if (i5 <= i6) {
                    i5 = i6;
                }
                c++;
                i6 = i5;
            }
        }
        for (i4 = 0; i4 < i3; i4++) {
            View view3 = this.f2445d[i4];
            if (this.j.mo952e(view3) != i6) {
                c0909b = (C0909b) view3.getLayoutParams();
                Rect rect = c0909b.d;
                i7 = ((rect.top + rect.bottom) + c0909b.topMargin) + c0909b.bottomMargin;
                c = ((rect.right + rect.left) + c0909b.leftMargin) + c0909b.rightMargin;
                a2 = mo828a(c0909b.f2407a, c0909b.f2408b);
                if (this.i == 1) {
                    c = C0910h.m4480a(a2, 1073741824, c, c0909b.width, false);
                    i5 = MeasureSpec.makeMeasureSpec(i6 - i7, 1073741824);
                } else {
                    c = MeasureSpec.makeMeasureSpec(i6 - c, 1073741824);
                    i5 = C0910h.m4480a(a2, 1073741824, i7, c0909b.height, false);
                }
                m4707a(view3, c, i5, true);
            }
        }
        c0914b.f2458a = i6;
        i5 = 0;
        if (this.i == 1) {
            if (c0915c.f2467f == -1) {
                i5 = c0915c.f2463b;
                i6 = i5 - i6;
                c = 0;
                i4 = 0;
            } else {
                c = c0915c.f2463b;
                i5 = c + i6;
                i6 = c;
                c = 0;
                i4 = 0;
            }
        } else if (c0915c.f2467f == -1) {
            i4 = c0915c.f2463b;
            c = i4;
            i4 -= i6;
            i6 = 0;
        } else {
            i4 = c0915c.f2463b;
            c = i6 + i4;
            i6 = 0;
        }
        i2 = i5;
        a2 = i6;
        int i8 = c;
        i7 = i4;
        for (i6 = 0; i6 < i3; i6++) {
            view3 = this.f2445d[i6];
            c0909b = (C0909b) view3.getLayoutParams();
            if (this.i != 1) {
                a2 = m4492C() + this.f2444c[c0909b.f2407a];
                i2 = a2 + this.j.mo954f(view3);
            } else if (m4690g()) {
                i8 = m4491B() + this.f2444c[this.f2443b - c0909b.f2407a];
                i7 = i8 - this.j.mo954f(view3);
            } else {
                i7 = m4491B() + this.f2444c[c0909b.f2407a];
                i8 = i7 + this.j.mo954f(view3);
            }
            m4529a(view3, i7, a2, i8, i2);
            if (c0909b.m4474d() || c0909b.m4475e()) {
                c0914b.f2460c = true;
            }
            c0914b.f2461d |= view3.hasFocusable();
        }
        Arrays.fill(this.f2445d, null);
    }

    /* renamed from: a */
    public void mo836a(C0947o c0947o, C0952s c0952s, View view, C0531e c0531e) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof C0909b) {
            C0909b c0909b = (C0909b) layoutParams;
            int a = m4704a(c0947o, c0952s, c0909b.m4476f());
            if (this.i == 0) {
                int a2 = c0909b.m4477a();
                int b = c0909b.m4478b();
                boolean z = this.f2443b > 1 && c0909b.m4478b() == this.f2443b;
                c0531e.m2321c(C0530m.m2298a(a2, b, a, 1, z, false));
                return;
            }
            int a3 = c0909b.m4477a();
            int b2 = c0909b.m4478b();
            boolean z2 = this.f2443b > 1 && c0909b.m4478b() == this.f2443b;
            c0531e.m2321c(C0530m.m2298a(a, 1, a3, b2, z2, false));
            return;
        }
        super.m4532a(view, c0531e);
    }

    /* renamed from: a */
    public void mo808a(C0952s c0952s) {
        super.mo808a(c0952s);
        this.f2442a = false;
    }

    /* renamed from: a */
    void mo837a(C0952s c0952s, C0915c c0915c, C0939a c0939a) {
        int i = this.f2443b;
        for (int i2 = 0; i2 < this.f2443b && c0915c.m4754a(c0952s) && i > 0; i2++) {
            int i3 = c0915c.f2465d;
            c0939a.mo932b(i3, Math.max(0, c0915c.f2468g));
            i -= this.f2448g.mo800a(i3);
            c0915c.f2465d += c0915c.f2466e;
        }
    }

    /* renamed from: a */
    public void mo838a(RecyclerView recyclerView) {
        this.f2448g.m4467a();
    }

    /* renamed from: a */
    public void mo839a(RecyclerView recyclerView, int i, int i2) {
        this.f2448g.m4467a();
    }

    /* renamed from: a */
    public void mo840a(RecyclerView recyclerView, int i, int i2, int i3) {
        this.f2448g.m4467a();
    }

    /* renamed from: a */
    public void mo841a(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.f2448g.m4467a();
    }

    /* renamed from: a */
    public void mo842a(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.mo842a(false);
    }

    /* renamed from: a */
    public boolean mo843a(C0908i c0908i) {
        return c0908i instanceof C0909b;
    }

    /* renamed from: b */
    public int mo813b(int i, C0947o c0947o, C0952s c0952s) {
        m4702O();
        m4703P();
        return super.mo813b(i, c0947o, c0952s);
    }

    /* renamed from: b */
    public int mo844b(C0947o c0947o, C0952s c0952s) {
        return this.i == 1 ? this.f2443b : c0952s.m4959e() < 1 ? 0 : m4704a(c0947o, c0952s, c0952s.m4959e() - 1) + 1;
    }

    /* renamed from: b */
    public void mo845b(RecyclerView recyclerView, int i, int i2) {
        this.f2448g.m4467a();
    }

    /* renamed from: b */
    public boolean mo814b() {
        return this.n == null && !this.f2442a;
    }

    /* renamed from: c */
    public void mo818c(C0947o c0947o, C0952s c0952s) {
        if (c0952s.m4955a()) {
            m4701N();
        }
        super.mo818c(c0947o, c0952s);
        m4700M();
    }
}
