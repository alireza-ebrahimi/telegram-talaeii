package android.support.v7.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.p023a.C0510a;
import android.support.v4.view.p023a.C0556o;
import android.support.v7.widget.RecyclerView.C0908i;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0910h.C0939a;
import android.support.v7.widget.RecyclerView.C0910h.C0940b;
import android.support.v7.widget.RecyclerView.C0947o;
import android.support.v7.widget.RecyclerView.C0952s;
import android.support.v7.widget.RecyclerView.C0955v;
import android.support.v7.widget.p032a.C0989a.C0911d;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public class LinearLayoutManager extends C0910h implements C0911d {
    /* renamed from: a */
    private C0915c f2427a;
    /* renamed from: b */
    private boolean f2428b;
    /* renamed from: c */
    private boolean f2429c;
    /* renamed from: d */
    private boolean f2430d;
    /* renamed from: e */
    private boolean f2431e;
    /* renamed from: f */
    private boolean f2432f;
    /* renamed from: g */
    private final C0914b f2433g;
    /* renamed from: h */
    private int f2434h;
    /* renamed from: i */
    int f2435i;
    /* renamed from: j */
    au f2436j;
    /* renamed from: k */
    boolean f2437k;
    /* renamed from: l */
    int f2438l;
    /* renamed from: m */
    int f2439m;
    /* renamed from: n */
    SavedState f2440n;
    /* renamed from: o */
    final C0913a f2441o;

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C09121();
        /* renamed from: a */
        int f2450a;
        /* renamed from: b */
        int f2451b;
        /* renamed from: c */
        boolean f2452c;

        /* renamed from: android.support.v7.widget.LinearLayoutManager$SavedState$1 */
        static class C09121 implements Creator<SavedState> {
            C09121() {
            }

            /* renamed from: a */
            public SavedState m4740a(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] m4741a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m4740a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m4741a(i);
            }
        }

        SavedState(Parcel parcel) {
            boolean z = true;
            this.f2450a = parcel.readInt();
            this.f2451b = parcel.readInt();
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.f2452c = z;
        }

        public SavedState(SavedState savedState) {
            this.f2450a = savedState.f2450a;
            this.f2451b = savedState.f2451b;
            this.f2452c = savedState.f2452c;
        }

        /* renamed from: a */
        boolean m4742a() {
            return this.f2450a >= 0;
        }

        /* renamed from: b */
        void m4743b() {
            this.f2450a = -1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.f2450a);
            parcel.writeInt(this.f2451b);
            parcel.writeInt(this.f2452c ? 1 : 0);
        }
    }

    /* renamed from: android.support.v7.widget.LinearLayoutManager$a */
    class C0913a {
        /* renamed from: a */
        int f2453a;
        /* renamed from: b */
        int f2454b;
        /* renamed from: c */
        boolean f2455c;
        /* renamed from: d */
        boolean f2456d;
        /* renamed from: e */
        final /* synthetic */ LinearLayoutManager f2457e;

        C0913a(LinearLayoutManager linearLayoutManager) {
            this.f2457e = linearLayoutManager;
            m4744a();
        }

        /* renamed from: a */
        void m4744a() {
            this.f2453a = -1;
            this.f2454b = Integer.MIN_VALUE;
            this.f2455c = false;
            this.f2456d = false;
        }

        /* renamed from: a */
        public void m4745a(View view) {
            int b = this.f2457e.f2436j.m5522b();
            if (b >= 0) {
                m4748b(view);
                return;
            }
            this.f2453a = this.f2457e.m4573d(view);
            int e;
            if (this.f2455c) {
                b = (this.f2457e.f2436j.mo949d() - b) - this.f2457e.f2436j.mo946b(view);
                this.f2454b = this.f2457e.f2436j.mo949d() - b;
                if (b > 0) {
                    e = this.f2454b - this.f2457e.f2436j.mo952e(view);
                    int c = this.f2457e.f2436j.mo947c();
                    e -= c + Math.min(this.f2457e.f2436j.mo944a(view) - c, 0);
                    if (e < 0) {
                        this.f2454b = Math.min(b, -e) + this.f2454b;
                        return;
                    }
                    return;
                }
                return;
            }
            e = this.f2457e.f2436j.mo944a(view);
            c = e - this.f2457e.f2436j.mo947c();
            this.f2454b = e;
            if (c > 0) {
                b = (this.f2457e.f2436j.mo949d() - Math.min(0, (this.f2457e.f2436j.mo949d() - b) - this.f2457e.f2436j.mo946b(view))) - (e + this.f2457e.f2436j.mo952e(view));
                if (b < 0) {
                    this.f2454b -= Math.min(c, -b);
                }
            }
        }

        /* renamed from: a */
        boolean m4746a(View view, C0952s c0952s) {
            C0908i c0908i = (C0908i) view.getLayoutParams();
            return !c0908i.m4474d() && c0908i.m4476f() >= 0 && c0908i.m4476f() < c0952s.m4959e();
        }

        /* renamed from: b */
        void m4747b() {
            this.f2454b = this.f2455c ? this.f2457e.f2436j.mo949d() : this.f2457e.f2436j.mo947c();
        }

        /* renamed from: b */
        public void m4748b(View view) {
            if (this.f2455c) {
                this.f2454b = this.f2457e.f2436j.mo946b(view) + this.f2457e.f2436j.m5522b();
            } else {
                this.f2454b = this.f2457e.f2436j.mo944a(view);
            }
            this.f2453a = this.f2457e.m4573d(view);
        }

        public String toString() {
            return "AnchorInfo{mPosition=" + this.f2453a + ", mCoordinate=" + this.f2454b + ", mLayoutFromEnd=" + this.f2455c + ", mValid=" + this.f2456d + '}';
        }
    }

    /* renamed from: android.support.v7.widget.LinearLayoutManager$b */
    protected static class C0914b {
        /* renamed from: a */
        public int f2458a;
        /* renamed from: b */
        public boolean f2459b;
        /* renamed from: c */
        public boolean f2460c;
        /* renamed from: d */
        public boolean f2461d;

        protected C0914b() {
        }

        /* renamed from: a */
        void m4749a() {
            this.f2458a = 0;
            this.f2459b = false;
            this.f2460c = false;
            this.f2461d = false;
        }
    }

    /* renamed from: android.support.v7.widget.LinearLayoutManager$c */
    static class C0915c {
        /* renamed from: a */
        boolean f2462a = true;
        /* renamed from: b */
        int f2463b;
        /* renamed from: c */
        int f2464c;
        /* renamed from: d */
        int f2465d;
        /* renamed from: e */
        int f2466e;
        /* renamed from: f */
        int f2467f;
        /* renamed from: g */
        int f2468g;
        /* renamed from: h */
        int f2469h = 0;
        /* renamed from: i */
        boolean f2470i = false;
        /* renamed from: j */
        int f2471j;
        /* renamed from: k */
        List<C0955v> f2472k = null;
        /* renamed from: l */
        boolean f2473l;

        C0915c() {
        }

        /* renamed from: b */
        private View m4750b() {
            int size = this.f2472k.size();
            for (int i = 0; i < size; i++) {
                View view = ((C0955v) this.f2472k.get(i)).itemView;
                C0908i c0908i = (C0908i) view.getLayoutParams();
                if (!c0908i.m4474d() && this.f2465d == c0908i.m4476f()) {
                    m4753a(view);
                    return view;
                }
            }
            return null;
        }

        /* renamed from: a */
        View m4751a(C0947o c0947o) {
            if (this.f2472k != null) {
                return m4750b();
            }
            View c = c0947o.m4913c(this.f2465d);
            this.f2465d += this.f2466e;
            return c;
        }

        /* renamed from: a */
        public void m4752a() {
            m4753a(null);
        }

        /* renamed from: a */
        public void m4753a(View view) {
            View b = m4755b(view);
            if (b == null) {
                this.f2465d = -1;
            } else {
                this.f2465d = ((C0908i) b.getLayoutParams()).m4476f();
            }
        }

        /* renamed from: a */
        boolean m4754a(C0952s c0952s) {
            return this.f2465d >= 0 && this.f2465d < c0952s.m4959e();
        }

        /* renamed from: b */
        public View m4755b(View view) {
            int size = this.f2472k.size();
            View view2 = null;
            int i = Integer.MAX_VALUE;
            int i2 = 0;
            while (i2 < size) {
                int i3;
                View view3;
                View view4 = ((C0955v) this.f2472k.get(i2)).itemView;
                C0908i c0908i = (C0908i) view4.getLayoutParams();
                if (view4 != view) {
                    if (c0908i.m4474d()) {
                        i3 = i;
                        view3 = view2;
                    } else {
                        i3 = (c0908i.m4476f() - this.f2465d) * this.f2466e;
                        if (i3 < 0) {
                            i3 = i;
                            view3 = view2;
                        } else if (i3 < i) {
                            if (i3 == 0) {
                                return view4;
                            }
                            view3 = view4;
                        }
                    }
                    i2++;
                    view2 = view3;
                    i = i3;
                }
                i3 = i;
                view3 = view2;
                i2++;
                view2 = view3;
                i = i3;
            }
            return view2;
        }
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int i, boolean z) {
        this.f2429c = false;
        this.f2437k = false;
        this.f2430d = false;
        this.f2431e = true;
        this.f2438l = -1;
        this.f2439m = Integer.MIN_VALUE;
        this.f2440n = null;
        this.f2441o = new C0913a(this);
        this.f2433g = new C0914b();
        this.f2434h = 2;
        m4671b(i);
        m4673b(z);
        m4570c(true);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.f2429c = false;
        this.f2437k = false;
        this.f2430d = false;
        this.f2431e = true;
        this.f2438l = -1;
        this.f2439m = Integer.MIN_VALUE;
        this.f2440n = null;
        this.f2441o = new C0913a(this);
        this.f2433g = new C0914b();
        this.f2434h = 2;
        C0940b a = C0910h.m4481a(context, attributeSet, i, i2);
        m4671b(a.f2493a);
        m4673b(a.f2495c);
        mo842a(a.f2496d);
        m4570c(true);
    }

    /* renamed from: M */
    private void m4620M() {
        boolean z = true;
        if (this.f2435i == 1 || !m4690g()) {
            this.f2437k = this.f2429c;
            return;
        }
        if (this.f2429c) {
            z = false;
        }
        this.f2437k = z;
    }

    /* renamed from: N */
    private View m4621N() {
        return m4596h(this.f2437k ? m4615w() - 1 : 0);
    }

    /* renamed from: O */
    private View m4622O() {
        return m4596h(this.f2437k ? 0 : m4615w() - 1);
    }

    /* renamed from: a */
    private int m4623a(int i, C0947o c0947o, C0952s c0952s, boolean z) {
        int d = this.f2436j.mo949d() - i;
        if (d <= 0) {
            return 0;
        }
        d = -m4675c(-d, c0947o, c0952s);
        int i2 = i + d;
        if (!z) {
            return d;
        }
        i2 = this.f2436j.mo949d() - i2;
        if (i2 <= 0) {
            return d;
        }
        this.f2436j.mo945a(i2);
        return d + i2;
    }

    /* renamed from: a */
    private View m4624a(boolean z, boolean z2) {
        return this.f2437k ? m4654a(m4615w() - 1, -1, z, z2) : m4654a(0, m4615w(), z, z2);
    }

    /* renamed from: a */
    private void mo828a(int i, int i2) {
        this.f2427a.f2464c = this.f2436j.mo949d() - i2;
        this.f2427a.f2466e = this.f2437k ? -1 : 1;
        this.f2427a.f2465d = i;
        this.f2427a.f2467f = 1;
        this.f2427a.f2463b = i2;
        this.f2427a.f2468g = Integer.MIN_VALUE;
    }

    /* renamed from: a */
    private void m4626a(int i, int i2, boolean z, C0952s c0952s) {
        int i3 = -1;
        int i4 = 1;
        this.f2427a.f2473l = m4694j();
        this.f2427a.f2469h = m4670b(c0952s);
        this.f2427a.f2467f = i;
        View O;
        C0915c c0915c;
        if (i == 1) {
            C0915c c0915c2 = this.f2427a;
            c0915c2.f2469h += this.f2436j.mo955g();
            O = m4622O();
            c0915c = this.f2427a;
            if (!this.f2437k) {
                i3 = 1;
            }
            c0915c.f2466e = i3;
            this.f2427a.f2465d = m4573d(O) + this.f2427a.f2466e;
            this.f2427a.f2463b = this.f2436j.mo946b(O);
            i3 = this.f2436j.mo946b(O) - this.f2436j.mo949d();
        } else {
            O = m4621N();
            c0915c = this.f2427a;
            c0915c.f2469h += this.f2436j.mo947c();
            c0915c = this.f2427a;
            if (!this.f2437k) {
                i4 = -1;
            }
            c0915c.f2466e = i4;
            this.f2427a.f2465d = m4573d(O) + this.f2427a.f2466e;
            this.f2427a.f2463b = this.f2436j.mo944a(O);
            i3 = (-this.f2436j.mo944a(O)) + this.f2436j.mo947c();
        }
        this.f2427a.f2464c = i2;
        if (z) {
            C0915c c0915c3 = this.f2427a;
            c0915c3.f2464c -= i3;
        }
        this.f2427a.f2468g = i3;
    }

    /* renamed from: a */
    private void m4627a(C0913a c0913a) {
        mo828a(c0913a.f2453a, c0913a.f2454b);
    }

    /* renamed from: a */
    private void m4628a(C0947o c0947o, int i) {
        if (i >= 0) {
            int w = m4615w();
            int i2;
            if (this.f2437k) {
                for (i2 = w - 1; i2 >= 0; i2--) {
                    View h = m4596h(i2);
                    if (this.f2436j.mo946b(h) > i || this.f2436j.mo948c(h) > i) {
                        m4629a(c0947o, w - 1, i2);
                        return;
                    }
                }
                return;
            }
            for (i2 = 0; i2 < w; i2++) {
                View h2 = m4596h(i2);
                if (this.f2436j.mo946b(h2) > i || this.f2436j.mo948c(h2) > i) {
                    m4629a(c0947o, 0, i2);
                    return;
                }
            }
        }
    }

    /* renamed from: a */
    private void m4629a(C0947o c0947o, int i, int i2) {
        if (i != i2) {
            if (i2 > i) {
                for (int i3 = i2 - 1; i3 >= i; i3--) {
                    m4510a(i3, c0947o);
                }
                return;
            }
            while (i > i2) {
                m4510a(i, c0947o);
                i--;
            }
        }
    }

    /* renamed from: a */
    private void m4630a(C0947o c0947o, C0915c c0915c) {
        if (c0915c.f2462a && !c0915c.f2473l) {
            if (c0915c.f2467f == -1) {
                m4636b(c0947o, c0915c.f2468g);
            } else {
                m4628a(c0947o, c0915c.f2468g);
            }
        }
    }

    /* renamed from: a */
    private void m4631a(C0947o c0947o, C0952s c0952s, C0913a c0913a) {
        if (!m4632a(c0952s, c0913a) && !m4638b(c0947o, c0952s, c0913a)) {
            c0913a.m4747b();
            c0913a.f2453a = this.f2430d ? c0952s.m4959e() - 1 : 0;
        }
    }

    /* renamed from: a */
    private boolean m4632a(C0952s c0952s, C0913a c0913a) {
        boolean z = false;
        if (c0952s.m4955a() || this.f2438l == -1) {
            return false;
        }
        if (this.f2438l < 0 || this.f2438l >= c0952s.m4959e()) {
            this.f2438l = -1;
            this.f2439m = Integer.MIN_VALUE;
            return false;
        }
        c0913a.f2453a = this.f2438l;
        if (this.f2440n != null && this.f2440n.m4742a()) {
            c0913a.f2455c = this.f2440n.f2452c;
            if (c0913a.f2455c) {
                c0913a.f2454b = this.f2436j.mo949d() - this.f2440n.f2451b;
                return true;
            }
            c0913a.f2454b = this.f2436j.mo947c() + this.f2440n.f2451b;
            return true;
        } else if (this.f2439m == Integer.MIN_VALUE) {
            View c = mo817c(this.f2438l);
            if (c == null) {
                if (m4615w() > 0) {
                    if ((this.f2438l < m4573d(m4596h(0))) == this.f2437k) {
                        z = true;
                    }
                    c0913a.f2455c = z;
                }
                c0913a.m4747b();
                return true;
            } else if (this.f2436j.mo952e(c) > this.f2436j.mo953f()) {
                c0913a.m4747b();
                return true;
            } else if (this.f2436j.mo944a(c) - this.f2436j.mo947c() < 0) {
                c0913a.f2454b = this.f2436j.mo947c();
                c0913a.f2455c = false;
                return true;
            } else if (this.f2436j.mo949d() - this.f2436j.mo946b(c) < 0) {
                c0913a.f2454b = this.f2436j.mo949d();
                c0913a.f2455c = true;
                return true;
            } else {
                c0913a.f2454b = c0913a.f2455c ? this.f2436j.mo946b(c) + this.f2436j.m5522b() : this.f2436j.mo944a(c);
                return true;
            }
        } else {
            c0913a.f2455c = this.f2437k;
            if (this.f2437k) {
                c0913a.f2454b = this.f2436j.mo949d() - this.f2439m;
                return true;
            }
            c0913a.f2454b = this.f2436j.mo947c() + this.f2439m;
            return true;
        }
    }

    /* renamed from: b */
    private int m4633b(int i, C0947o c0947o, C0952s c0952s, boolean z) {
        int c = i - this.f2436j.mo947c();
        if (c <= 0) {
            return 0;
        }
        c = -m4675c(c, c0947o, c0952s);
        int i2 = i + c;
        if (!z) {
            return c;
        }
        i2 -= this.f2436j.mo947c();
        if (i2 <= 0) {
            return c;
        }
        this.f2436j.mo945a(-i2);
        return c - i2;
    }

    /* renamed from: b */
    private View m4634b(boolean z, boolean z2) {
        return this.f2437k ? m4654a(0, m4615w(), z, z2) : m4654a(m4615w() - 1, -1, z, z2);
    }

    /* renamed from: b */
    private void m4635b(C0913a c0913a) {
        m4642h(c0913a.f2453a, c0913a.f2454b);
    }

    /* renamed from: b */
    private void m4636b(C0947o c0947o, int i) {
        int w = m4615w();
        if (i >= 0) {
            int e = this.f2436j.mo951e() - i;
            int i2;
            if (this.f2437k) {
                for (i2 = 0; i2 < w; i2++) {
                    View h = m4596h(i2);
                    if (this.f2436j.mo944a(h) < e || this.f2436j.mo950d(h) < e) {
                        m4629a(c0947o, 0, i2);
                        return;
                    }
                }
                return;
            }
            for (i2 = w - 1; i2 >= 0; i2--) {
                View h2 = m4596h(i2);
                if (this.f2436j.mo944a(h2) < e || this.f2436j.mo950d(h2) < e) {
                    m4629a(c0947o, w - 1, i2);
                    return;
                }
            }
        }
    }

    /* renamed from: b */
    private void m4637b(C0947o c0947o, C0952s c0952s, int i, int i2) {
        if (c0952s.m4956b() && m4615w() != 0 && !c0952s.m4955a() && mo814b()) {
            int i3 = 0;
            int i4 = 0;
            List c = c0947o.m4914c();
            int size = c.size();
            int d = m4573d(m4596h(0));
            int i5 = 0;
            while (i5 < size) {
                int i6;
                int i7;
                C0955v c0955v = (C0955v) c.get(i5);
                if (c0955v.isRemoved()) {
                    i6 = i4;
                    i7 = i3;
                } else {
                    if (((c0955v.getLayoutPosition() < d) != this.f2437k ? -1 : 1) == -1) {
                        i7 = this.f2436j.mo952e(c0955v.itemView) + i3;
                        i6 = i4;
                    } else {
                        i6 = this.f2436j.mo952e(c0955v.itemView) + i4;
                        i7 = i3;
                    }
                }
                i5++;
                i3 = i7;
                i4 = i6;
            }
            this.f2427a.f2472k = c;
            if (i3 > 0) {
                m4642h(m4573d(m4621N()), i);
                this.f2427a.f2469h = i3;
                this.f2427a.f2464c = 0;
                this.f2427a.m4752a();
                m4652a(c0947o, this.f2427a, c0952s, false);
            }
            if (i4 > 0) {
                mo828a(m4573d(m4622O()), i2);
                this.f2427a.f2469h = i4;
                this.f2427a.f2464c = 0;
                this.f2427a.m4752a();
                m4652a(c0947o, this.f2427a, c0952s, false);
            }
            this.f2427a.f2472k = null;
        }
    }

    /* renamed from: b */
    private boolean m4638b(C0947o c0947o, C0952s c0952s, C0913a c0913a) {
        boolean z = false;
        if (m4615w() == 0) {
            return false;
        }
        View F = m4495F();
        if (F != null && c0913a.m4746a(F, c0952s)) {
            c0913a.m4745a(F);
            return true;
        } else if (this.f2428b != this.f2430d) {
            return false;
        } else {
            F = c0913a.f2455c ? m4639f(c0947o, c0952s) : m4640g(c0947o, c0952s);
            if (F == null) {
                return false;
            }
            c0913a.m4748b(F);
            if (!c0952s.m4955a() && mo814b()) {
                if (this.f2436j.mo944a(F) >= this.f2436j.mo949d() || this.f2436j.mo946b(F) < this.f2436j.mo947c()) {
                    z = true;
                }
                if (z) {
                    c0913a.f2454b = c0913a.f2455c ? this.f2436j.mo949d() : this.f2436j.mo947c();
                }
            }
            return true;
        }
    }

    /* renamed from: f */
    private View m4639f(C0947o c0947o, C0952s c0952s) {
        return this.f2437k ? m4641h(c0947o, c0952s) : m4644i(c0947o, c0952s);
    }

    /* renamed from: g */
    private View m4640g(C0947o c0947o, C0952s c0952s) {
        return this.f2437k ? m4644i(c0947o, c0952s) : m4641h(c0947o, c0952s);
    }

    /* renamed from: h */
    private View m4641h(C0947o c0947o, C0952s c0952s) {
        return mo832a(c0947o, c0952s, 0, m4615w(), c0952s.m4959e());
    }

    /* renamed from: h */
    private void m4642h(int i, int i2) {
        this.f2427a.f2464c = i2 - this.f2436j.mo947c();
        this.f2427a.f2465d = i;
        this.f2427a.f2466e = this.f2437k ? 1 : -1;
        this.f2427a.f2467f = -1;
        this.f2427a.f2463b = i2;
        this.f2427a.f2468g = Integer.MIN_VALUE;
    }

    /* renamed from: i */
    private int m4643i(C0952s c0952s) {
        boolean z = false;
        if (m4615w() == 0) {
            return 0;
        }
        m4692h();
        au auVar = this.f2436j;
        View a = m4624a(!this.f2431e, true);
        if (!this.f2431e) {
            z = true;
        }
        return bb.m5608a(c0952s, auVar, a, m4634b(z, true), this, this.f2431e, this.f2437k);
    }

    /* renamed from: i */
    private View m4644i(C0947o c0947o, C0952s c0952s) {
        return mo832a(c0947o, c0952s, m4615w() - 1, -1, c0952s.m4959e());
    }

    /* renamed from: j */
    private int m4645j(C0952s c0952s) {
        boolean z = false;
        if (m4615w() == 0) {
            return 0;
        }
        m4692h();
        au auVar = this.f2436j;
        View a = m4624a(!this.f2431e, true);
        if (!this.f2431e) {
            z = true;
        }
        return bb.m5607a(c0952s, auVar, a, m4634b(z, true), this, this.f2431e);
    }

    /* renamed from: j */
    private View m4646j(C0947o c0947o, C0952s c0952s) {
        return this.f2437k ? m4649l(c0947o, c0952s) : m4650m(c0947o, c0952s);
    }

    /* renamed from: k */
    private int m4647k(C0952s c0952s) {
        boolean z = false;
        if (m4615w() == 0) {
            return 0;
        }
        m4692h();
        au auVar = this.f2436j;
        View a = m4624a(!this.f2431e, true);
        if (!this.f2431e) {
            z = true;
        }
        return bb.m5609b(c0952s, auVar, a, m4634b(z, true), this, this.f2431e);
    }

    /* renamed from: k */
    private View m4648k(C0947o c0947o, C0952s c0952s) {
        return this.f2437k ? m4650m(c0947o, c0952s) : m4649l(c0947o, c0952s);
    }

    /* renamed from: l */
    private View m4649l(C0947o c0947o, C0952s c0952s) {
        return m4679c(0, m4615w());
    }

    /* renamed from: m */
    private View m4650m(C0947o c0947o, C0952s c0952s) {
        return m4679c(m4615w() - 1, -1);
    }

    /* renamed from: a */
    public int mo802a(int i, C0947o c0947o, C0952s c0952s) {
        return this.f2435i == 1 ? 0 : m4675c(i, c0947o, c0952s);
    }

    /* renamed from: a */
    int m4652a(C0947o c0947o, C0915c c0915c, C0952s c0952s, boolean z) {
        int i = c0915c.f2464c;
        if (c0915c.f2468g != Integer.MIN_VALUE) {
            if (c0915c.f2464c < 0) {
                c0915c.f2468g += c0915c.f2464c;
            }
            m4630a(c0947o, c0915c);
        }
        int i2 = c0915c.f2464c + c0915c.f2469h;
        C0914b c0914b = this.f2433g;
        while (true) {
            if ((!c0915c.f2473l && i2 <= 0) || !c0915c.m4754a(c0952s)) {
                break;
            }
            c0914b.m4749a();
            mo835a(c0947o, c0952s, c0915c, c0914b);
            if (!c0914b.f2459b) {
                c0915c.f2463b += c0914b.f2458a * c0915c.f2467f;
                if (!(c0914b.f2460c && this.f2427a.f2472k == null && c0952s.m4955a())) {
                    c0915c.f2464c -= c0914b.f2458a;
                    i2 -= c0914b.f2458a;
                }
                if (c0915c.f2468g != Integer.MIN_VALUE) {
                    c0915c.f2468g += c0914b.f2458a;
                    if (c0915c.f2464c < 0) {
                        c0915c.f2468g += c0915c.f2464c;
                    }
                    m4630a(c0947o, c0915c);
                }
                if (z && c0914b.f2461d) {
                    break;
                }
            } else {
                break;
            }
        }
        return i - c0915c.f2464c;
    }

    /* renamed from: a */
    public C0908i mo803a() {
        return new C0908i(-2, -2);
    }

    /* renamed from: a */
    View m4654a(int i, int i2, boolean z, boolean z2) {
        int i3 = 320;
        m4692h();
        int i4 = z ? 24579 : 320;
        if (!z2) {
            i3 = 0;
        }
        return this.f2435i == 0 ? this.r.m5723a(i, i2, i4, i3) : this.s.m5723a(i, i2, i4, i3);
    }

    /* renamed from: a */
    View mo832a(C0947o c0947o, C0952s c0952s, int i, int i2, int i3) {
        View view = null;
        m4692h();
        int c = this.f2436j.mo947c();
        int d = this.f2436j.mo949d();
        int i4 = i2 > i ? 1 : -1;
        View view2 = null;
        while (i != i2) {
            View view3;
            View h = m4596h(i);
            int d2 = m4573d(h);
            if (d2 >= 0 && d2 < i3) {
                if (((C0908i) h.getLayoutParams()).m4474d()) {
                    if (view2 == null) {
                        view3 = view;
                        i += i4;
                        view = view3;
                        view2 = h;
                    }
                } else if (this.f2436j.mo944a(h) < d && this.f2436j.mo946b(h) >= c) {
                    return h;
                } else {
                    if (view == null) {
                        view3 = h;
                        h = view2;
                        i += i4;
                        view = view3;
                        view2 = h;
                    }
                }
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
        m4620M();
        if (m4615w() == 0) {
            return null;
        }
        int e = m4684e(i);
        if (e == Integer.MIN_VALUE) {
            return null;
        }
        m4692h();
        m4692h();
        m4626a(e, (int) (0.33333334f * ((float) this.f2436j.mo953f())), false, c0952s);
        this.f2427a.f2468g = Integer.MIN_VALUE;
        this.f2427a.f2462a = false;
        m4652a(c0947o, this.f2427a, c0952s, true);
        View k = e == -1 ? m4648k(c0947o, c0952s) : m4646j(c0947o, c0952s);
        View N = e == -1 ? m4621N() : m4622O();
        return N.hasFocusable() ? k == null ? null : N : k;
    }

    /* renamed from: a */
    public void mo805a(int i, int i2, C0952s c0952s, C0939a c0939a) {
        if (this.f2435i != 0) {
            i = i2;
        }
        if (m4615w() != 0 && i != 0) {
            m4626a(i > 0 ? 1 : -1, Math.abs(i), true, c0952s);
            mo837a(c0952s, this.f2427a, c0939a);
        }
    }

    /* renamed from: a */
    public void mo806a(int i, C0939a c0939a) {
        int i2;
        boolean z;
        if (this.f2440n == null || !this.f2440n.m4742a()) {
            m4620M();
            boolean z2 = this.f2437k;
            if (this.f2438l == -1) {
                i2 = z2 ? i - 1 : 0;
                z = z2;
            } else {
                i2 = this.f2438l;
                z = z2;
            }
        } else {
            z = this.f2440n.f2452c;
            i2 = this.f2440n.f2450a;
        }
        int i3 = z ? -1 : 1;
        for (int i4 = 0; i4 < this.f2434h && i2 >= 0 && i2 < i; i4++) {
            c0939a.mo932b(i2, 0);
            i2 += i3;
        }
    }

    /* renamed from: a */
    public void mo807a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.f2440n = (SavedState) parcelable;
            m4608p();
        }
    }

    /* renamed from: a */
    void mo834a(C0947o c0947o, C0952s c0952s, C0913a c0913a, int i) {
    }

    /* renamed from: a */
    void mo835a(C0947o c0947o, C0952s c0952s, C0915c c0915c, C0914b c0914b) {
        View a = c0915c.m4751a(c0947o);
        if (a == null) {
            c0914b.f2459b = true;
            return;
        }
        int f;
        int i;
        int i2;
        int i3;
        C0908i c0908i = (C0908i) a.getLayoutParams();
        if (c0915c.f2472k == null) {
            if (this.f2437k == (c0915c.f2467f == -1)) {
                m4556b(a);
            } else {
                m4557b(a, 0);
            }
        } else {
            if (this.f2437k == (c0915c.f2467f == -1)) {
                m4526a(a);
            } else {
                m4527a(a, 0);
            }
        }
        m4528a(a, 0, 0);
        c0914b.f2458a = this.f2436j.mo952e(a);
        if (this.f2435i == 1) {
            int z;
            if (m4690g()) {
                z = m4618z() - m4493D();
                f = z - this.f2436j.mo954f(a);
            } else {
                f = m4491B();
                z = this.f2436j.mo954f(a) + f;
            }
            if (c0915c.f2467f == -1) {
                i = c0915c.f2463b;
                i2 = c0915c.f2463b - c0914b.f2458a;
                i3 = z;
            } else {
                i2 = c0915c.f2463b;
                i = c0914b.f2458a + c0915c.f2463b;
                i3 = z;
            }
        } else {
            i2 = m4492C();
            i = i2 + this.f2436j.mo954f(a);
            if (c0915c.f2467f == -1) {
                f = c0915c.f2463b - c0914b.f2458a;
                i3 = c0915c.f2463b;
            } else {
                f = c0915c.f2463b;
                i3 = c0915c.f2463b + c0914b.f2458a;
            }
        }
        m4529a(a, f, i2, i3, i);
        if (c0908i.m4474d() || c0908i.m4475e()) {
            c0914b.f2460c = true;
        }
        c0914b.f2461d = a.hasFocusable();
    }

    /* renamed from: a */
    public void mo808a(C0952s c0952s) {
        super.mo808a(c0952s);
        this.f2440n = null;
        this.f2438l = -1;
        this.f2439m = Integer.MIN_VALUE;
        this.f2441o.m4744a();
    }

    /* renamed from: a */
    void mo837a(C0952s c0952s, C0915c c0915c, C0939a c0939a) {
        int i = c0915c.f2465d;
        if (i >= 0 && i < c0952s.m4959e()) {
            c0939a.mo932b(i, Math.max(0, c0915c.f2468g));
        }
    }

    /* renamed from: a */
    public void mo809a(RecyclerView recyclerView, C0947o c0947o) {
        super.mo809a(recyclerView, c0947o);
        if (this.f2432f) {
            m4564c(c0947o);
            c0947o.m4897a();
        }
    }

    /* renamed from: a */
    public void mo810a(View view, View view2, int i, int i2) {
        mo812a("Cannot drop a view during a scroll or layout calculation");
        m4692h();
        m4620M();
        int d = m4573d(view);
        int d2 = m4573d(view2);
        if (d < d2) {
            Object obj = 1;
        } else {
            d = -1;
        }
        if (this.f2437k) {
            if (obj == 1) {
                m4672b(d2, this.f2436j.mo949d() - (this.f2436j.mo944a(view2) + this.f2436j.mo952e(view)));
            } else {
                m4672b(d2, this.f2436j.mo949d() - this.f2436j.mo946b(view2));
            }
        } else if (obj == -1) {
            m4672b(d2, this.f2436j.mo944a(view2));
        } else {
            m4672b(d2, this.f2436j.mo946b(view2) - this.f2436j.mo952e(view));
        }
    }

    /* renamed from: a */
    public void mo811a(AccessibilityEvent accessibilityEvent) {
        super.mo811a(accessibilityEvent);
        if (m4615w() > 0) {
            C0556o a = C0510a.m2132a(accessibilityEvent);
            a.m2479b(m4696l());
            a.m2482c(m4698n());
        }
    }

    /* renamed from: a */
    public void mo812a(String str) {
        if (this.f2440n == null) {
            super.mo812a(str);
        }
    }

    /* renamed from: a */
    public void mo842a(boolean z) {
        mo812a(null);
        if (this.f2430d != z) {
            this.f2430d = z;
            m4608p();
        }
    }

    /* renamed from: b */
    public int mo813b(int i, C0947o c0947o, C0952s c0952s) {
        return this.f2435i == 0 ? 0 : m4675c(i, c0947o, c0952s);
    }

    /* renamed from: b */
    protected int m4670b(C0952s c0952s) {
        return c0952s.m4958d() ? this.f2436j.mo953f() : 0;
    }

    /* renamed from: b */
    public void m4671b(int i) {
        if (i == 0 || i == 1) {
            mo812a(null);
            if (i != this.f2435i) {
                this.f2435i = i;
                this.f2436j = null;
                m4608p();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + i);
    }

    /* renamed from: b */
    public void m4672b(int i, int i2) {
        this.f2438l = i;
        this.f2439m = i2;
        if (this.f2440n != null) {
            this.f2440n.m4743b();
        }
        m4608p();
    }

    /* renamed from: b */
    public void m4673b(boolean z) {
        mo812a(null);
        if (z != this.f2429c) {
            this.f2429c = z;
            m4608p();
        }
    }

    /* renamed from: b */
    public boolean mo814b() {
        return this.f2440n == null && this.f2428b == this.f2430d;
    }

    /* renamed from: c */
    int m4675c(int i, C0947o c0947o, C0952s c0952s) {
        if (m4615w() == 0 || i == 0) {
            return 0;
        }
        this.f2427a.f2462a = true;
        m4692h();
        int i2 = i > 0 ? 1 : -1;
        int abs = Math.abs(i);
        m4626a(i2, abs, true, c0952s);
        int a = this.f2427a.f2468g + m4652a(c0947o, this.f2427a, c0952s, false);
        if (a < 0) {
            return 0;
        }
        if (abs > a) {
            i = i2 * a;
        }
        this.f2436j.mo945a(-i);
        this.f2427a.f2471j = i;
        return i;
    }

    /* renamed from: c */
    public int mo815c(C0952s c0952s) {
        return m4643i(c0952s);
    }

    /* renamed from: c */
    public Parcelable mo816c() {
        if (this.f2440n != null) {
            return new SavedState(this.f2440n);
        }
        Parcelable savedState = new SavedState();
        if (m4615w() > 0) {
            m4692h();
            boolean z = this.f2428b ^ this.f2437k;
            savedState.f2452c = z;
            View O;
            if (z) {
                O = m4622O();
                savedState.f2451b = this.f2436j.mo949d() - this.f2436j.mo946b(O);
                savedState.f2450a = m4573d(O);
                return savedState;
            }
            O = m4621N();
            savedState.f2450a = m4573d(O);
            savedState.f2451b = this.f2436j.mo944a(O) - this.f2436j.mo947c();
            return savedState;
        }
        savedState.m4743b();
        return savedState;
    }

    /* renamed from: c */
    public View mo817c(int i) {
        int w = m4615w();
        if (w == 0) {
            return null;
        }
        int d = i - m4573d(m4596h(0));
        if (d >= 0 && d < w) {
            View h = m4596h(d);
            if (m4573d(h) == i) {
                return h;
            }
        }
        return super.mo817c(i);
    }

    /* renamed from: c */
    View m4679c(int i, int i2) {
        m4692h();
        Object obj = i2 > i ? 1 : i2 < i ? -1 : null;
        if (obj == null) {
            return m4596h(i);
        }
        int i3;
        int i4;
        if (this.f2436j.mo944a(m4596h(i)) < this.f2436j.mo947c()) {
            i3 = 16644;
            i4 = 16388;
        } else {
            i3 = 4161;
            i4 = 4097;
        }
        return this.f2435i == 0 ? this.r.m5723a(i, i2, i3, i4) : this.s.m5723a(i, i2, i3, i4);
    }

    /* renamed from: c */
    public void mo818c(C0947o c0947o, C0952s c0952s) {
        int i = -1;
        if (!(this.f2440n == null && this.f2438l == -1) && c0952s.m4959e() == 0) {
            m4564c(c0947o);
            return;
        }
        int i2;
        int d;
        if (this.f2440n != null && this.f2440n.m4742a()) {
            this.f2438l = this.f2440n.f2450a;
        }
        m4692h();
        this.f2427a.f2462a = false;
        m4620M();
        if (!(this.f2441o.f2456d && this.f2438l == -1 && this.f2440n == null)) {
            this.f2441o.m4744a();
            this.f2441o.f2455c = this.f2437k ^ this.f2430d;
            m4631a(c0947o, c0952s, this.f2441o);
            this.f2441o.f2456d = true;
        }
        int b = m4670b(c0952s);
        if (this.f2427a.f2471j >= 0) {
            i2 = 0;
        } else {
            i2 = b;
            b = 0;
        }
        i2 += this.f2436j.mo947c();
        b += this.f2436j.mo955g();
        if (!(!c0952s.m4955a() || this.f2438l == -1 || this.f2439m == Integer.MIN_VALUE)) {
            View c = mo817c(this.f2438l);
            if (c != null) {
                if (this.f2437k) {
                    d = (this.f2436j.mo949d() - this.f2436j.mo946b(c)) - this.f2439m;
                } else {
                    d = this.f2439m - (this.f2436j.mo944a(c) - this.f2436j.mo947c());
                }
                if (d > 0) {
                    i2 += d;
                } else {
                    b -= d;
                }
            }
        }
        if (this.f2441o.f2455c) {
            if (this.f2437k) {
                i = 1;
            }
        } else if (!this.f2437k) {
            i = 1;
        }
        mo834a(c0947o, c0952s, this.f2441o, i);
        m4515a(c0947o);
        this.f2427a.f2473l = m4694j();
        this.f2427a.f2470i = c0952s.m4955a();
        if (this.f2441o.f2455c) {
            m4635b(this.f2441o);
            this.f2427a.f2469h = i2;
            m4652a(c0947o, this.f2427a, c0952s, false);
            i2 = this.f2427a.f2463b;
            d = this.f2427a.f2465d;
            if (this.f2427a.f2464c > 0) {
                b += this.f2427a.f2464c;
            }
            m4627a(this.f2441o);
            this.f2427a.f2469h = b;
            C0915c c0915c = this.f2427a;
            c0915c.f2465d += this.f2427a.f2466e;
            m4652a(c0947o, this.f2427a, c0952s, false);
            i = this.f2427a.f2463b;
            if (this.f2427a.f2464c > 0) {
                b = this.f2427a.f2464c;
                m4642h(d, i2);
                this.f2427a.f2469h = b;
                m4652a(c0947o, this.f2427a, c0952s, false);
                b = this.f2427a.f2463b;
            } else {
                b = i2;
            }
            i2 = b;
            b = i;
        } else {
            m4627a(this.f2441o);
            this.f2427a.f2469h = b;
            m4652a(c0947o, this.f2427a, c0952s, false);
            b = this.f2427a.f2463b;
            i = this.f2427a.f2465d;
            if (this.f2427a.f2464c > 0) {
                i2 += this.f2427a.f2464c;
            }
            m4635b(this.f2441o);
            this.f2427a.f2469h = i2;
            C0915c c0915c2 = this.f2427a;
            c0915c2.f2465d += this.f2427a.f2466e;
            m4652a(c0947o, this.f2427a, c0952s, false);
            i2 = this.f2427a.f2463b;
            if (this.f2427a.f2464c > 0) {
                d = this.f2427a.f2464c;
                mo828a(i, b);
                this.f2427a.f2469h = d;
                m4652a(c0947o, this.f2427a, c0952s, false);
                b = this.f2427a.f2463b;
            }
        }
        if (m4615w() > 0) {
            int b2;
            if ((this.f2437k ^ this.f2430d) != 0) {
                i = m4623a(b, c0947o, c0952s, true);
                i2 += i;
                b += i;
                b2 = m4633b(i2, c0947o, c0952s, false);
                i2 += b2;
                b += b2;
            } else {
                i = m4633b(i2, c0947o, c0952s, true);
                i2 += i;
                b += i;
                b2 = m4623a(b, c0947o, c0952s, false);
                i2 += b2;
                b += b2;
            }
        }
        m4637b(c0947o, c0952s, i2, b);
        if (c0952s.m4955a()) {
            this.f2441o.m4744a();
        } else {
            this.f2436j.m5520a();
        }
        this.f2428b = this.f2430d;
    }

    /* renamed from: d */
    public int mo819d(C0952s c0952s) {
        return m4643i(c0952s);
    }

    /* renamed from: d */
    public void mo820d(int i) {
        this.f2438l = i;
        this.f2439m = Integer.MIN_VALUE;
        if (this.f2440n != null) {
            this.f2440n.m4743b();
        }
        m4608p();
    }

    /* renamed from: d */
    public boolean mo821d() {
        return this.f2435i == 0;
    }

    /* renamed from: e */
    int m4684e(int i) {
        int i2 = Integer.MIN_VALUE;
        int i3 = 1;
        switch (i) {
            case 1:
                return (this.f2435i == 1 || !m4690g()) ? -1 : 1;
            case 2:
                return this.f2435i == 1 ? 1 : !m4690g() ? 1 : -1;
            case 17:
                return this.f2435i != 0 ? Integer.MIN_VALUE : -1;
            case 33:
                return this.f2435i != 1 ? Integer.MIN_VALUE : -1;
            case 66:
                if (this.f2435i != 0) {
                    i3 = Integer.MIN_VALUE;
                }
                return i3;
            case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /*130*/:
                if (this.f2435i == 1) {
                    i2 = 1;
                }
                return i2;
            default:
                return Integer.MIN_VALUE;
        }
    }

    /* renamed from: e */
    public int mo822e(C0952s c0952s) {
        return m4645j(c0952s);
    }

    /* renamed from: e */
    public boolean mo823e() {
        return this.f2435i == 1;
    }

    /* renamed from: f */
    public int m4687f() {
        return this.f2435i;
    }

    /* renamed from: f */
    public int mo824f(C0952s c0952s) {
        return m4645j(c0952s);
    }

    /* renamed from: g */
    public int mo825g(C0952s c0952s) {
        return m4647k(c0952s);
    }

    /* renamed from: g */
    protected boolean m4690g() {
        return m4613u() == 1;
    }

    /* renamed from: h */
    public int mo826h(C0952s c0952s) {
        return m4647k(c0952s);
    }

    /* renamed from: h */
    void m4692h() {
        if (this.f2427a == null) {
            this.f2427a = m4693i();
        }
        if (this.f2436j == null) {
            this.f2436j = au.m5517a(this, this.f2435i);
        }
    }

    /* renamed from: i */
    C0915c m4693i() {
        return new C0915c();
    }

    /* renamed from: j */
    boolean m4694j() {
        return this.f2436j.mo956h() == 0 && this.f2436j.mo951e() == 0;
    }

    /* renamed from: k */
    boolean mo827k() {
        return (m4617y() == 1073741824 || m4616x() == 1073741824 || !m4501L()) ? false : true;
    }

    /* renamed from: l */
    public int m4696l() {
        View a = m4654a(0, m4615w(), false, true);
        return a == null ? -1 : m4573d(a);
    }

    /* renamed from: m */
    public int m4697m() {
        View a = m4654a(0, m4615w(), true, false);
        return a == null ? -1 : m4573d(a);
    }

    /* renamed from: n */
    public int m4698n() {
        View a = m4654a(m4615w() - 1, -1, false, true);
        return a == null ? -1 : m4573d(a);
    }

    /* renamed from: o */
    public int m4699o() {
        View a = m4654a(m4615w() - 1, -1, true, false);
        return a == null ? -1 : m4573d(a);
    }
}
