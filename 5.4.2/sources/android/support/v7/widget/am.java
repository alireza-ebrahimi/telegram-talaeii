package android.support.v7.widget;

import android.support.v4.p014d.C0440j;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0910h.C0939a;
import android.support.v7.widget.RecyclerView.C0947o;
import android.support.v7.widget.RecyclerView.C0955v;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class am implements Runnable {
    /* renamed from: a */
    static final ThreadLocal<am> f2867a = new ThreadLocal();
    /* renamed from: e */
    static Comparator<C1016b> f2868e = new C10141();
    /* renamed from: b */
    ArrayList<RecyclerView> f2869b = new ArrayList();
    /* renamed from: c */
    long f2870c;
    /* renamed from: d */
    long f2871d;
    /* renamed from: f */
    private ArrayList<C1016b> f2872f = new ArrayList();

    /* renamed from: android.support.v7.widget.am$1 */
    static class C10141 implements Comparator<C1016b> {
        C10141() {
        }

        /* renamed from: a */
        public int m5447a(C1016b c1016b, C1016b c1016b2) {
            int i = -1;
            if ((c1016b.f2865d == null ? 1 : 0) != (c1016b2.f2865d == null ? 1 : 0)) {
                return c1016b.f2865d == null ? 1 : -1;
            } else {
                if (c1016b.f2862a != c1016b2.f2862a) {
                    if (!c1016b.f2862a) {
                        i = 1;
                    }
                    return i;
                }
                int i2 = c1016b2.f2863b - c1016b.f2863b;
                if (i2 != 0) {
                    return i2;
                }
                i2 = c1016b.f2864c - c1016b2.f2864c;
                return i2 == 0 ? 0 : i2;
            }
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m5447a((C1016b) obj, (C1016b) obj2);
        }
    }

    /* renamed from: android.support.v7.widget.am$a */
    static class C1015a implements C0939a {
        /* renamed from: a */
        int f2858a;
        /* renamed from: b */
        int f2859b;
        /* renamed from: c */
        int[] f2860c;
        /* renamed from: d */
        int f2861d;

        C1015a() {
        }

        /* renamed from: a */
        void m5448a() {
            if (this.f2860c != null) {
                Arrays.fill(this.f2860c, -1);
            }
            this.f2861d = 0;
        }

        /* renamed from: a */
        void m5449a(int i, int i2) {
            this.f2858a = i;
            this.f2859b = i2;
        }

        /* renamed from: a */
        void m5450a(RecyclerView recyclerView, boolean z) {
            this.f2861d = 0;
            if (this.f2860c != null) {
                Arrays.fill(this.f2860c, -1);
            }
            C0910h c0910h = recyclerView.f194m;
            if (recyclerView.f193l != null && c0910h != null && c0910h.m4609q()) {
                if (z) {
                    if (!recyclerView.f186e.m5831d()) {
                        c0910h.mo806a(recyclerView.f193l.getItemCount(), (C0939a) this);
                    }
                } else if (!recyclerView.m303v()) {
                    c0910h.mo805a(this.f2858a, this.f2859b, recyclerView.f169A, (C0939a) this);
                }
                if (this.f2861d > c0910h.f2425x) {
                    c0910h.f2425x = this.f2861d;
                    c0910h.f2426y = z;
                    recyclerView.f185d.m4909b();
                }
            }
        }

        /* renamed from: a */
        boolean m5451a(int i) {
            if (this.f2860c == null) {
                return false;
            }
            int i2 = this.f2861d * 2;
            for (int i3 = 0; i3 < i2; i3 += 2) {
                if (this.f2860c[i3] == i) {
                    return true;
                }
            }
            return false;
        }

        /* renamed from: b */
        public void mo932b(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            } else if (i2 < 0) {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            } else {
                int i3 = this.f2861d * 2;
                if (this.f2860c == null) {
                    this.f2860c = new int[4];
                    Arrays.fill(this.f2860c, -1);
                } else if (i3 >= this.f2860c.length) {
                    Object obj = this.f2860c;
                    this.f2860c = new int[(i3 * 2)];
                    System.arraycopy(obj, 0, this.f2860c, 0, obj.length);
                }
                this.f2860c[i3] = i;
                this.f2860c[i3 + 1] = i2;
                this.f2861d++;
            }
        }
    }

    /* renamed from: android.support.v7.widget.am$b */
    static class C1016b {
        /* renamed from: a */
        public boolean f2862a;
        /* renamed from: b */
        public int f2863b;
        /* renamed from: c */
        public int f2864c;
        /* renamed from: d */
        public RecyclerView f2865d;
        /* renamed from: e */
        public int f2866e;

        C1016b() {
        }

        /* renamed from: a */
        public void m5453a() {
            this.f2862a = false;
            this.f2863b = 0;
            this.f2864c = 0;
            this.f2865d = null;
            this.f2866e = 0;
        }
    }

    am() {
    }

    /* renamed from: a */
    private C0955v m5454a(RecyclerView recyclerView, int i, long j) {
        if (m5458a(recyclerView, i)) {
            return null;
        }
        C0947o c0947o = recyclerView.f185d;
        C0955v a = c0947o.m4894a(i, false, j);
        if (a == null) {
            return a;
        }
        if (a.isBound()) {
            c0947o.m4905a(a.itemView);
            return a;
        }
        c0947o.m4904a(a, false);
        return a;
    }

    /* renamed from: a */
    private void m5455a() {
        int size = this.f2869b.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            int i3;
            RecyclerView recyclerView = (RecyclerView) this.f2869b.get(i);
            if (recyclerView.getWindowVisibility() == 0) {
                recyclerView.f207z.m5450a(recyclerView, false);
                i3 = recyclerView.f207z.f2861d + i2;
            } else {
                i3 = i2;
            }
            i++;
            i2 = i3;
        }
        this.f2872f.ensureCapacity(i2);
        boolean z = false;
        for (int i4 = 0; i4 < size; i4++) {
            recyclerView = (RecyclerView) this.f2869b.get(i4);
            if (recyclerView.getWindowVisibility() == 0) {
                C1015a c1015a = recyclerView.f207z;
                int abs = Math.abs(c1015a.f2858a) + Math.abs(c1015a.f2859b);
                boolean z2 = z;
                for (i = 0; i < c1015a.f2861d * 2; i += 2) {
                    C1016b c1016b;
                    if (z2 >= this.f2872f.size()) {
                        c1016b = new C1016b();
                        this.f2872f.add(c1016b);
                    } else {
                        c1016b = (C1016b) this.f2872f.get(z2);
                    }
                    int i5 = c1015a.f2860c[i + 1];
                    c1016b.f2862a = i5 <= abs;
                    c1016b.f2863b = abs;
                    c1016b.f2864c = i5;
                    c1016b.f2865d = recyclerView;
                    c1016b.f2866e = c1015a.f2860c[i];
                    z2++;
                }
                z = z2;
            }
        }
        Collections.sort(this.f2872f, f2868e);
    }

    /* renamed from: a */
    private void m5456a(RecyclerView recyclerView, long j) {
        if (recyclerView != null) {
            if (recyclerView.f203v && recyclerView.f187f.m5347c() != 0) {
                recyclerView.m253b();
            }
            C1015a c1015a = recyclerView.f207z;
            c1015a.m5450a(recyclerView, true);
            if (c1015a.f2861d != 0) {
                try {
                    C0440j.m1922a("RV Nested Prefetch");
                    recyclerView.f169A.m4954a(recyclerView.f193l);
                    for (int i = 0; i < c1015a.f2861d * 2; i += 2) {
                        m5454a(recyclerView, c1015a.f2860c[i], j);
                    }
                } finally {
                    C0440j.m1921a();
                }
            }
        }
    }

    /* renamed from: a */
    private void m5457a(C1016b c1016b, long j) {
        C0955v a = m5454a(c1016b.f2865d, c1016b.f2866e, c1016b.f2862a ? Long.MAX_VALUE : j);
        if (a != null && a.mNestedRecyclerView != null) {
            m5456a((RecyclerView) a.mNestedRecyclerView.get(), j);
        }
    }

    /* renamed from: a */
    static boolean m5458a(RecyclerView recyclerView, int i) {
        int c = recyclerView.f187f.m5347c();
        for (int i2 = 0; i2 < c; i2++) {
            C0955v e = RecyclerView.m222e(recyclerView.f187f.m5350d(i2));
            if (e.mPosition == i && !e.isInvalid()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private void m5459b(long j) {
        int i = 0;
        while (i < this.f2872f.size()) {
            C1016b c1016b = (C1016b) this.f2872f.get(i);
            if (c1016b.f2865d != null) {
                m5457a(c1016b, j);
                c1016b.m5453a();
                i++;
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    void m5460a(long j) {
        m5455a();
        m5459b(j);
    }

    /* renamed from: a */
    public void m5461a(RecyclerView recyclerView) {
        this.f2869b.add(recyclerView);
    }

    /* renamed from: a */
    void m5462a(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow() && this.f2870c == 0) {
            this.f2870c = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.f207z.m5449a(i, i2);
    }

    /* renamed from: b */
    public void m5463b(RecyclerView recyclerView) {
        this.f2869b.remove(recyclerView);
    }

    public void run() {
        try {
            C0440j.m1922a("RV Prefetch");
            if (!this.f2869b.isEmpty()) {
                int size = this.f2869b.size();
                int i = 0;
                long j = 0;
                while (i < size) {
                    RecyclerView recyclerView = (RecyclerView) this.f2869b.get(i);
                    i++;
                    j = recyclerView.getWindowVisibility() == 0 ? Math.max(recyclerView.getDrawingTime(), j) : j;
                }
                if (j == 0) {
                    this.f2870c = 0;
                    C0440j.m1921a();
                    return;
                }
                m5460a(TimeUnit.MILLISECONDS.toNanos(j) + this.f2871d);
                this.f2870c = 0;
                C0440j.m1921a();
            }
        } finally {
            this.f2870c = 0;
            C0440j.m1921a();
        }
    }
}
