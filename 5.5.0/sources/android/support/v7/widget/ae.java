package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.C0955v;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

class ae {
    /* renamed from: a */
    final C0921b f2787a;
    /* renamed from: b */
    final C0998a f2788b = new C0998a();
    /* renamed from: c */
    final List<View> f2789c = new ArrayList();

    /* renamed from: android.support.v7.widget.ae$b */
    interface C0921b {
        /* renamed from: a */
        int mo850a();

        /* renamed from: a */
        int mo851a(View view);

        /* renamed from: a */
        void mo852a(int i);

        /* renamed from: a */
        void mo853a(View view, int i);

        /* renamed from: a */
        void mo854a(View view, int i, LayoutParams layoutParams);

        /* renamed from: b */
        C0955v mo855b(View view);

        /* renamed from: b */
        View mo856b(int i);

        /* renamed from: b */
        void mo857b();

        /* renamed from: c */
        void mo858c(int i);

        /* renamed from: c */
        void mo859c(View view);

        /* renamed from: d */
        void mo860d(View view);
    }

    /* renamed from: android.support.v7.widget.ae$a */
    static class C0998a {
        /* renamed from: a */
        long f2785a = 0;
        /* renamed from: b */
        C0998a f2786b;

        C0998a() {
        }

        /* renamed from: b */
        private void m5327b() {
            if (this.f2786b == null) {
                this.f2786b = new C0998a();
            }
        }

        /* renamed from: a */
        void m5328a() {
            this.f2785a = 0;
            if (this.f2786b != null) {
                this.f2786b.m5328a();
            }
        }

        /* renamed from: a */
        void m5329a(int i) {
            if (i >= 64) {
                m5327b();
                this.f2786b.m5329a(i - 64);
                return;
            }
            this.f2785a |= 1 << i;
        }

        /* renamed from: a */
        void m5330a(int i, boolean z) {
            if (i >= 64) {
                m5327b();
                this.f2786b.m5330a(i - 64, z);
                return;
            }
            boolean z2 = (this.f2785a & Long.MIN_VALUE) != 0;
            long j = (1 << i) - 1;
            this.f2785a = (((j ^ -1) & this.f2785a) << 1) | (this.f2785a & j);
            if (z) {
                m5329a(i);
            } else {
                m5331b(i);
            }
            if (z2 || this.f2786b != null) {
                m5327b();
                this.f2786b.m5330a(0, z2);
            }
        }

        /* renamed from: b */
        void m5331b(int i) {
            if (i < 64) {
                this.f2785a &= (1 << i) ^ -1;
            } else if (this.f2786b != null) {
                this.f2786b.m5331b(i - 64);
            }
        }

        /* renamed from: c */
        boolean m5332c(int i) {
            if (i < 64) {
                return (this.f2785a & (1 << i)) != 0;
            } else {
                m5327b();
                return this.f2786b.m5332c(i - 64);
            }
        }

        /* renamed from: d */
        boolean m5333d(int i) {
            if (i >= 64) {
                m5327b();
                return this.f2786b.m5333d(i - 64);
            }
            long j = 1 << i;
            boolean z = (this.f2785a & j) != 0;
            this.f2785a &= j ^ -1;
            j--;
            this.f2785a = Long.rotateRight((j ^ -1) & this.f2785a, 1) | (this.f2785a & j);
            if (this.f2786b == null) {
                return z;
            }
            if (this.f2786b.m5332c(0)) {
                m5329a(63);
            }
            this.f2786b.m5333d(0);
            return z;
        }

        /* renamed from: e */
        int m5334e(int i) {
            return this.f2786b == null ? i >= 64 ? Long.bitCount(this.f2785a) : Long.bitCount(this.f2785a & ((1 << i) - 1)) : i < 64 ? Long.bitCount(this.f2785a & ((1 << i) - 1)) : this.f2786b.m5334e(i - 64) + Long.bitCount(this.f2785a);
        }

        public String toString() {
            return this.f2786b == null ? Long.toBinaryString(this.f2785a) : this.f2786b.toString() + "xx" + Long.toBinaryString(this.f2785a);
        }
    }

    ae(C0921b c0921b) {
        this.f2787a = c0921b;
    }

    /* renamed from: f */
    private int m5335f(int i) {
        if (i < 0) {
            return -1;
        }
        int a = this.f2787a.mo850a();
        int i2 = i;
        while (i2 < a) {
            int e = i - (i2 - this.f2788b.m5334e(i2));
            if (e == 0) {
                while (this.f2788b.m5332c(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += e;
        }
        return -1;
    }

    /* renamed from: g */
    private void m5336g(View view) {
        this.f2789c.add(view);
        this.f2787a.mo859c(view);
    }

    /* renamed from: h */
    private boolean m5337h(View view) {
        if (!this.f2789c.remove(view)) {
            return false;
        }
        this.f2787a.mo860d(view);
        return true;
    }

    /* renamed from: a */
    void m5338a() {
        this.f2788b.m5328a();
        for (int size = this.f2789c.size() - 1; size >= 0; size--) {
            this.f2787a.mo860d((View) this.f2789c.get(size));
            this.f2789c.remove(size);
        }
        this.f2787a.mo857b();
    }

    /* renamed from: a */
    void m5339a(int i) {
        int f = m5335f(i);
        View b = this.f2787a.mo856b(f);
        if (b != null) {
            if (this.f2788b.m5333d(f)) {
                m5337h(b);
            }
            this.f2787a.mo852a(f);
        }
    }

    /* renamed from: a */
    void m5340a(View view) {
        int a = this.f2787a.mo851a(view);
        if (a >= 0) {
            if (this.f2788b.m5333d(a)) {
                m5337h(view);
            }
            this.f2787a.mo852a(a);
        }
    }

    /* renamed from: a */
    void m5341a(View view, int i, LayoutParams layoutParams, boolean z) {
        int a = i < 0 ? this.f2787a.mo850a() : m5335f(i);
        this.f2788b.m5330a(a, z);
        if (z) {
            m5336g(view);
        }
        this.f2787a.mo854a(view, a, layoutParams);
    }

    /* renamed from: a */
    void m5342a(View view, int i, boolean z) {
        int a = i < 0 ? this.f2787a.mo850a() : m5335f(i);
        this.f2788b.m5330a(a, z);
        if (z) {
            m5336g(view);
        }
        this.f2787a.mo853a(view, a);
    }

    /* renamed from: a */
    void m5343a(View view, boolean z) {
        m5342a(view, -1, z);
    }

    /* renamed from: b */
    int m5344b() {
        return this.f2787a.mo850a() - this.f2789c.size();
    }

    /* renamed from: b */
    int m5345b(View view) {
        int a = this.f2787a.mo851a(view);
        return (a == -1 || this.f2788b.m5332c(a)) ? -1 : a - this.f2788b.m5334e(a);
    }

    /* renamed from: b */
    View m5346b(int i) {
        return this.f2787a.mo856b(m5335f(i));
    }

    /* renamed from: c */
    int m5347c() {
        return this.f2787a.mo850a();
    }

    /* renamed from: c */
    View m5348c(int i) {
        int size = this.f2789c.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) this.f2789c.get(i2);
            C0955v b = this.f2787a.mo855b(view);
            if (b.getLayoutPosition() == i && !b.isInvalid() && !b.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    /* renamed from: c */
    boolean m5349c(View view) {
        return this.f2789c.contains(view);
    }

    /* renamed from: d */
    View m5350d(int i) {
        return this.f2787a.mo856b(i);
    }

    /* renamed from: d */
    void m5351d(View view) {
        int a = this.f2787a.mo851a(view);
        if (a < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        this.f2788b.m5329a(a);
        m5336g(view);
    }

    /* renamed from: e */
    void m5352e(int i) {
        int f = m5335f(i);
        this.f2788b.m5333d(f);
        this.f2787a.mo858c(f);
    }

    /* renamed from: e */
    void m5353e(View view) {
        int a = this.f2787a.mo851a(view);
        if (a < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        } else if (this.f2788b.m5332c(a)) {
            this.f2788b.m5331b(a);
            m5337h(view);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }

    /* renamed from: f */
    boolean m5354f(View view) {
        int a = this.f2787a.mo851a(view);
        if (a == -1) {
            return m5337h(view) ? true : true;
        } else {
            if (!this.f2788b.m5332c(a)) {
                return false;
            }
            this.f2788b.m5333d(a);
            if (m5337h(view)) {
                this.f2787a.mo852a(a);
            } else {
                this.f2787a.mo852a(a);
            }
            return true;
        }
    }

    public String toString() {
        return this.f2788b.toString() + ", hidden list:" + this.f2789c.size();
    }
}
