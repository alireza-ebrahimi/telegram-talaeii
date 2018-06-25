package android.support.v7.widget;

import android.support.v4.p022f.C0464a;
import android.support.v4.p022f.C0470f;
import android.support.v4.p022f.C0481j.C0478a;
import android.support.v4.p022f.C0481j.C0479b;
import android.support.v7.widget.RecyclerView.C0933e.C0932c;
import android.support.v7.widget.RecyclerView.C0955v;

class bo {
    /* renamed from: a */
    final C0464a<C0955v, C1043a> f3080a = new C0464a();
    /* renamed from: b */
    final C0470f<C0955v> f3081b = new C0470f();

    /* renamed from: android.support.v7.widget.bo$b */
    interface C0919b {
        /* renamed from: a */
        void mo846a(C0955v c0955v);

        /* renamed from: a */
        void mo847a(C0955v c0955v, C0932c c0932c, C0932c c0932c2);

        /* renamed from: b */
        void mo848b(C0955v c0955v, C0932c c0932c, C0932c c0932c2);

        /* renamed from: c */
        void mo849c(C0955v c0955v, C0932c c0932c, C0932c c0932c2);
    }

    /* renamed from: android.support.v7.widget.bo$a */
    static class C1043a {
        /* renamed from: d */
        static C0478a<C1043a> f3076d = new C0479b(20);
        /* renamed from: a */
        int f3077a;
        /* renamed from: b */
        C0932c f3078b;
        /* renamed from: c */
        C0932c f3079c;

        private C1043a() {
        }

        /* renamed from: a */
        static C1043a m5725a() {
            C1043a c1043a = (C1043a) f3076d.mo332a();
            return c1043a == null ? new C1043a() : c1043a;
        }

        /* renamed from: a */
        static void m5726a(C1043a c1043a) {
            c1043a.f3077a = 0;
            c1043a.f3078b = null;
            c1043a.f3079c = null;
            f3076d.mo333a(c1043a);
        }

        /* renamed from: b */
        static void m5727b() {
            do {
            } while (f3076d.mo332a() != null);
        }
    }

    bo() {
    }

    /* renamed from: a */
    private C0932c m5728a(C0955v c0955v, int i) {
        C0932c c0932c = null;
        int a = this.f3080a.m1980a((Object) c0955v);
        if (a >= 0) {
            C1043a c1043a = (C1043a) this.f3080a.m1986c(a);
            if (!(c1043a == null || (c1043a.f3077a & i) == 0)) {
                c1043a.f3077a &= i ^ -1;
                if (i == 4) {
                    c0932c = c1043a.f3078b;
                } else if (i == 8) {
                    c0932c = c1043a.f3079c;
                } else {
                    throw new IllegalArgumentException("Must provide flag PRE or POST");
                }
                if ((c1043a.f3077a & 12) == 0) {
                    this.f3080a.m1987d(a);
                    C1043a.m5726a(c1043a);
                }
            }
        }
        return c0932c;
    }

    /* renamed from: a */
    C0955v m5729a(long j) {
        return (C0955v) this.f3081b.m2018a(j);
    }

    /* renamed from: a */
    void m5730a() {
        this.f3080a.clear();
        this.f3081b.m2026c();
    }

    /* renamed from: a */
    void m5731a(long j, C0955v c0955v) {
        this.f3081b.m2024b(j, c0955v);
    }

    /* renamed from: a */
    void m5732a(C0955v c0955v, C0932c c0932c) {
        C1043a c1043a = (C1043a) this.f3080a.get(c0955v);
        if (c1043a == null) {
            c1043a = C1043a.m5725a();
            this.f3080a.put(c0955v, c1043a);
        }
        c1043a.f3078b = c0932c;
        c1043a.f3077a |= 4;
    }

    /* renamed from: a */
    void m5733a(C0919b c0919b) {
        for (int size = this.f3080a.size() - 1; size >= 0; size--) {
            C0955v c0955v = (C0955v) this.f3080a.m1985b(size);
            C1043a c1043a = (C1043a) this.f3080a.m1987d(size);
            if ((c1043a.f3077a & 3) == 3) {
                c0919b.mo846a(c0955v);
            } else if ((c1043a.f3077a & 1) != 0) {
                if (c1043a.f3078b == null) {
                    c0919b.mo846a(c0955v);
                } else {
                    c0919b.mo847a(c0955v, c1043a.f3078b, c1043a.f3079c);
                }
            } else if ((c1043a.f3077a & 14) == 14) {
                c0919b.mo848b(c0955v, c1043a.f3078b, c1043a.f3079c);
            } else if ((c1043a.f3077a & 12) == 12) {
                c0919b.mo849c(c0955v, c1043a.f3078b, c1043a.f3079c);
            } else if ((c1043a.f3077a & 4) != 0) {
                c0919b.mo847a(c0955v, c1043a.f3078b, null);
            } else if ((c1043a.f3077a & 8) != 0) {
                c0919b.mo848b(c0955v, c1043a.f3078b, c1043a.f3079c);
            } else if ((c1043a.f3077a & 2) != 0) {
            }
            C1043a.m5726a(c1043a);
        }
    }

    /* renamed from: a */
    boolean m5734a(C0955v c0955v) {
        C1043a c1043a = (C1043a) this.f3080a.get(c0955v);
        return (c1043a == null || (c1043a.f3077a & 1) == 0) ? false : true;
    }

    /* renamed from: b */
    C0932c m5735b(C0955v c0955v) {
        return m5728a(c0955v, 4);
    }

    /* renamed from: b */
    void m5736b() {
        C1043a.m5727b();
    }

    /* renamed from: b */
    void m5737b(C0955v c0955v, C0932c c0932c) {
        C1043a c1043a = (C1043a) this.f3080a.get(c0955v);
        if (c1043a == null) {
            c1043a = C1043a.m5725a();
            this.f3080a.put(c0955v, c1043a);
        }
        c1043a.f3077a |= 2;
        c1043a.f3078b = c0932c;
    }

    /* renamed from: c */
    C0932c m5738c(C0955v c0955v) {
        return m5728a(c0955v, 8);
    }

    /* renamed from: c */
    void m5739c(C0955v c0955v, C0932c c0932c) {
        C1043a c1043a = (C1043a) this.f3080a.get(c0955v);
        if (c1043a == null) {
            c1043a = C1043a.m5725a();
            this.f3080a.put(c0955v, c1043a);
        }
        c1043a.f3079c = c0932c;
        c1043a.f3077a |= 8;
    }

    /* renamed from: d */
    boolean m5740d(C0955v c0955v) {
        C1043a c1043a = (C1043a) this.f3080a.get(c0955v);
        return (c1043a == null || (c1043a.f3077a & 4) == 0) ? false : true;
    }

    /* renamed from: e */
    void m5741e(C0955v c0955v) {
        C1043a c1043a = (C1043a) this.f3080a.get(c0955v);
        if (c1043a == null) {
            c1043a = C1043a.m5725a();
            this.f3080a.put(c0955v, c1043a);
        }
        r0.f3077a |= 1;
    }

    /* renamed from: f */
    void m5742f(C0955v c0955v) {
        C1043a c1043a = (C1043a) this.f3080a.get(c0955v);
        if (c1043a != null) {
            c1043a.f3077a &= -2;
        }
    }

    /* renamed from: g */
    void m5743g(C0955v c0955v) {
        for (int b = this.f3081b.m2021b() - 1; b >= 0; b--) {
            if (c0955v == this.f3081b.m2025c(b)) {
                this.f3081b.m2020a(b);
                break;
            }
        }
        C1043a c1043a = (C1043a) this.f3080a.remove(c0955v);
        if (c1043a != null) {
            C1043a.m5726a(c1043a);
        }
    }

    /* renamed from: h */
    public void m5744h(C0955v c0955v) {
        m5742f(c0955v);
    }
}
