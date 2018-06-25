package android.support.v7.widget;

import android.support.v4.p022f.C0481j.C0478a;
import android.support.v4.p022f.C0481j.C0479b;
import android.support.v7.widget.RecyclerView.C0955v;
import android.support.v7.widget.at.C1026a;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v7.widget.f */
class C1060f implements C1026a {
    /* renamed from: a */
    final ArrayList<C1059b> f3141a;
    /* renamed from: b */
    final ArrayList<C1059b> f3142b;
    /* renamed from: c */
    final C0923a f3143c;
    /* renamed from: d */
    Runnable f3144d;
    /* renamed from: e */
    final boolean f3145e;
    /* renamed from: f */
    final at f3146f;
    /* renamed from: g */
    private C0478a<C1059b> f3147g;
    /* renamed from: h */
    private int f3148h;

    /* renamed from: android.support.v7.widget.f$a */
    interface C0923a {
        /* renamed from: a */
        C0955v mo861a(int i);

        /* renamed from: a */
        void mo862a(int i, int i2);

        /* renamed from: a */
        void mo863a(int i, int i2, Object obj);

        /* renamed from: a */
        void mo864a(C1059b c1059b);

        /* renamed from: b */
        void mo865b(int i, int i2);

        /* renamed from: b */
        void mo866b(C1059b c1059b);

        /* renamed from: c */
        void mo867c(int i, int i2);

        /* renamed from: d */
        void mo868d(int i, int i2);
    }

    /* renamed from: android.support.v7.widget.f$b */
    static class C1059b {
        /* renamed from: a */
        int f3137a;
        /* renamed from: b */
        int f3138b;
        /* renamed from: c */
        Object f3139c;
        /* renamed from: d */
        int f3140d;

        C1059b(int i, int i2, int i3, Object obj) {
            this.f3137a = i;
            this.f3138b = i2;
            this.f3140d = i3;
            this.f3139c = obj;
        }

        /* renamed from: a */
        String m5807a() {
            switch (this.f3137a) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                case 4:
                    return "up";
                case 8:
                    return "mv";
                default:
                    return "??";
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            C1059b c1059b = (C1059b) obj;
            return this.f3137a != c1059b.f3137a ? false : (this.f3137a == 8 && Math.abs(this.f3140d - this.f3138b) == 1 && this.f3140d == c1059b.f3138b && this.f3138b == c1059b.f3140d) ? true : this.f3140d != c1059b.f3140d ? false : this.f3138b != c1059b.f3138b ? false : this.f3139c != null ? this.f3139c.equals(c1059b.f3139c) : c1059b.f3139c == null;
        }

        public int hashCode() {
            return (((this.f3137a * 31) + this.f3138b) * 31) + this.f3140d;
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + m5807a() + ",s:" + this.f3138b + "c:" + this.f3140d + ",p:" + this.f3139c + "]";
        }
    }

    C1060f(C0923a c0923a) {
        this(c0923a, false);
    }

    C1060f(C0923a c0923a, boolean z) {
        this.f3147g = new C0479b(30);
        this.f3141a = new ArrayList();
        this.f3142b = new ArrayList();
        this.f3148h = 0;
        this.f3143c = c0923a;
        this.f3145e = z;
        this.f3146f = new at(this);
    }

    /* renamed from: b */
    private void m5808b(C1059b c1059b) {
        m5815g(c1059b);
    }

    /* renamed from: c */
    private void m5809c(C1059b c1059b) {
        int i = c1059b.f3138b;
        int i2 = c1059b.f3138b + c1059b.f3140d;
        Object obj = -1;
        int i3 = c1059b.f3138b;
        int i4 = 0;
        while (i3 < i2) {
            Object obj2;
            int i5;
            if (this.f3143c.mo861a(i3) != null || m5812d(i3)) {
                if (obj == null) {
                    m5813e(mo1004a(2, i, i4, null));
                    obj2 = 1;
                } else {
                    obj2 = null;
                }
                obj = 1;
            } else {
                if (obj == 1) {
                    m5815g(mo1004a(2, i, i4, null));
                    obj2 = 1;
                } else {
                    obj2 = null;
                }
                obj = null;
            }
            if (obj2 != null) {
                i5 = i3 - i4;
                i3 = i2 - i4;
                i2 = 1;
            } else {
                int i6 = i3;
                i3 = i2;
                i2 = i4 + 1;
                i5 = i6;
            }
            i4 = i2;
            i2 = i3;
            i3 = i5 + 1;
        }
        if (i4 != c1059b.f3140d) {
            mo1005a(c1059b);
            c1059b = mo1004a(2, i, i4, null);
        }
        if (obj == null) {
            m5813e(c1059b);
        } else {
            m5815g(c1059b);
        }
    }

    /* renamed from: d */
    private int m5810d(int i, int i2) {
        int i3;
        int i4 = i;
        for (int size = this.f3142b.size() - 1; size >= 0; size--) {
            C1059b c1059b = (C1059b) this.f3142b.get(size);
            if (c1059b.f3137a == 8) {
                int i5;
                int i6;
                if (c1059b.f3138b < c1059b.f3140d) {
                    i5 = c1059b.f3138b;
                    i3 = c1059b.f3140d;
                } else {
                    i5 = c1059b.f3140d;
                    i3 = c1059b.f3138b;
                }
                if (i4 < i5 || i4 > r2) {
                    if (i4 < c1059b.f3138b) {
                        if (i2 == 1) {
                            c1059b.f3138b++;
                            c1059b.f3140d++;
                            i6 = i4;
                        } else if (i2 == 2) {
                            c1059b.f3138b--;
                            c1059b.f3140d--;
                        }
                    }
                    i6 = i4;
                } else if (i5 == c1059b.f3138b) {
                    if (i2 == 1) {
                        c1059b.f3140d++;
                    } else if (i2 == 2) {
                        c1059b.f3140d--;
                    }
                    i6 = i4 + 1;
                } else {
                    if (i2 == 1) {
                        c1059b.f3138b++;
                    } else if (i2 == 2) {
                        c1059b.f3138b--;
                    }
                    i6 = i4 - 1;
                }
                i4 = i6;
            } else if (c1059b.f3138b <= i4) {
                if (c1059b.f3137a == 1) {
                    i4 -= c1059b.f3140d;
                } else if (c1059b.f3137a == 2) {
                    i4 += c1059b.f3140d;
                }
            } else if (i2 == 1) {
                c1059b.f3138b++;
            } else if (i2 == 2) {
                c1059b.f3138b--;
            }
        }
        for (i3 = this.f3142b.size() - 1; i3 >= 0; i3--) {
            c1059b = (C1059b) this.f3142b.get(i3);
            if (c1059b.f3137a == 8) {
                if (c1059b.f3140d == c1059b.f3138b || c1059b.f3140d < 0) {
                    this.f3142b.remove(i3);
                    mo1005a(c1059b);
                }
            } else if (c1059b.f3140d <= 0) {
                this.f3142b.remove(i3);
                mo1005a(c1059b);
            }
        }
        return i4;
    }

    /* renamed from: d */
    private void m5811d(C1059b c1059b) {
        int i = c1059b.f3138b;
        int i2 = c1059b.f3138b + c1059b.f3140d;
        int i3 = c1059b.f3138b;
        Object obj = -1;
        int i4 = 0;
        while (i3 < i2) {
            int i5;
            Object obj2;
            if (this.f3143c.mo861a(i3) != null || m5812d(i3)) {
                if (obj == null) {
                    m5813e(mo1004a(4, i, i4, c1059b.f3139c));
                    i4 = 0;
                    i = i3;
                }
                i5 = i;
                i = i4;
                obj2 = 1;
            } else {
                if (obj == 1) {
                    m5815g(mo1004a(4, i, i4, c1059b.f3139c));
                    i4 = 0;
                    i = i3;
                }
                i5 = i;
                i = i4;
                obj2 = null;
            }
            i3++;
            Object obj3 = obj2;
            i4 = i + 1;
            i = i5;
            obj = obj3;
        }
        if (i4 != c1059b.f3140d) {
            Object obj4 = c1059b.f3139c;
            mo1005a(c1059b);
            c1059b = mo1004a(4, i, i4, obj4);
        }
        if (obj == null) {
            m5813e(c1059b);
        } else {
            m5815g(c1059b);
        }
    }

    /* renamed from: d */
    private boolean m5812d(int i) {
        int size = this.f3142b.size();
        for (int i2 = 0; i2 < size; i2++) {
            C1059b c1059b = (C1059b) this.f3142b.get(i2);
            if (c1059b.f3137a == 8) {
                if (m5816a(c1059b.f3140d, i2 + 1) == i) {
                    return true;
                }
            } else if (c1059b.f3137a == 1) {
                int i3 = c1059b.f3138b + c1059b.f3140d;
                for (int i4 = c1059b.f3138b; i4 < i3; i4++) {
                    if (m5816a(i4, i2 + 1) == i) {
                        return true;
                    }
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    /* renamed from: e */
    private void m5813e(C1059b c1059b) {
        if (c1059b.f3137a == 1 || c1059b.f3137a == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int i;
        int d = m5810d(c1059b.f3138b, c1059b.f3137a);
        int i2 = c1059b.f3138b;
        switch (c1059b.f3137a) {
            case 2:
                i = 0;
                break;
            case 4:
                i = 1;
                break;
            default:
                throw new IllegalArgumentException("op should be remove or update." + c1059b);
        }
        int i3 = 1;
        int i4 = d;
        d = i2;
        for (i2 = 1; i2 < c1059b.f3140d; i2++) {
            Object obj;
            int d2 = m5810d(c1059b.f3138b + (i * i2), c1059b.f3137a);
            int i5;
            switch (c1059b.f3137a) {
                case 2:
                    if (d2 != i4) {
                        obj = null;
                        break;
                    } else {
                        i5 = 1;
                        break;
                    }
                case 4:
                    if (d2 != i4 + 1) {
                        obj = null;
                        break;
                    } else {
                        i5 = 1;
                        break;
                    }
                default:
                    obj = null;
                    break;
            }
            if (obj != null) {
                i3++;
            } else {
                C1059b a = mo1004a(c1059b.f3137a, i4, i3, c1059b.f3139c);
                m5820a(a, d);
                mo1005a(a);
                if (c1059b.f3137a == 4) {
                    d += i3;
                }
                i3 = 1;
                i4 = d2;
            }
        }
        Object obj2 = c1059b.f3139c;
        mo1005a(c1059b);
        if (i3 > 0) {
            C1059b a2 = mo1004a(c1059b.f3137a, i4, i3, obj2);
            m5820a(a2, d);
            mo1005a(a2);
        }
    }

    /* renamed from: f */
    private void m5814f(C1059b c1059b) {
        m5815g(c1059b);
    }

    /* renamed from: g */
    private void m5815g(C1059b c1059b) {
        this.f3142b.add(c1059b);
        switch (c1059b.f3137a) {
            case 1:
                this.f3143c.mo867c(c1059b.f3138b, c1059b.f3140d);
                return;
            case 2:
                this.f3143c.mo865b(c1059b.f3138b, c1059b.f3140d);
                return;
            case 4:
                this.f3143c.mo863a(c1059b.f3138b, c1059b.f3140d, c1059b.f3139c);
                return;
            case 8:
                this.f3143c.mo868d(c1059b.f3138b, c1059b.f3140d);
                return;
            default:
                throw new IllegalArgumentException("Unknown update op type for " + c1059b);
        }
    }

    /* renamed from: a */
    int m5816a(int i, int i2) {
        int size = this.f3142b.size();
        int i3 = i;
        while (i2 < size) {
            C1059b c1059b = (C1059b) this.f3142b.get(i2);
            if (c1059b.f3137a == 8) {
                if (c1059b.f3138b == i3) {
                    i3 = c1059b.f3140d;
                } else {
                    if (c1059b.f3138b < i3) {
                        i3--;
                    }
                    if (c1059b.f3140d <= i3) {
                        i3++;
                    }
                }
            } else if (c1059b.f3138b > i3) {
                continue;
            } else if (c1059b.f3137a == 2) {
                if (i3 < c1059b.f3138b + c1059b.f3140d) {
                    return -1;
                }
                i3 -= c1059b.f3140d;
            } else if (c1059b.f3137a == 1) {
                i3 += c1059b.f3140d;
            }
            i2++;
        }
        return i3;
    }

    /* renamed from: a */
    public C1059b mo1004a(int i, int i2, int i3, Object obj) {
        C1059b c1059b = (C1059b) this.f3147g.mo332a();
        if (c1059b == null) {
            return new C1059b(i, i2, i3, obj);
        }
        c1059b.f3137a = i;
        c1059b.f3138b = i2;
        c1059b.f3140d = i3;
        c1059b.f3139c = obj;
        return c1059b;
    }

    /* renamed from: a */
    void m5818a() {
        m5821a(this.f3141a);
        m5821a(this.f3142b);
        this.f3148h = 0;
    }

    /* renamed from: a */
    public void mo1005a(C1059b c1059b) {
        if (!this.f3145e) {
            c1059b.f3139c = null;
            this.f3147g.mo333a(c1059b);
        }
    }

    /* renamed from: a */
    void m5820a(C1059b c1059b, int i) {
        this.f3143c.mo864a(c1059b);
        switch (c1059b.f3137a) {
            case 2:
                this.f3143c.mo862a(i, c1059b.f3140d);
                return;
            case 4:
                this.f3143c.mo863a(i, c1059b.f3140d, c1059b.f3139c);
                return;
            default:
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    /* renamed from: a */
    void m5821a(List<C1059b> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            mo1005a((C1059b) list.get(i));
        }
        list.clear();
    }

    /* renamed from: a */
    boolean m5822a(int i) {
        return (this.f3148h & i) != 0;
    }

    /* renamed from: a */
    boolean m5823a(int i, int i2, int i3) {
        boolean z = true;
        if (i == i2) {
            return false;
        }
        if (i3 != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.f3141a.add(mo1004a(8, i, i2, null));
        this.f3148h |= 8;
        if (this.f3141a.size() != 1) {
            z = false;
        }
        return z;
    }

    /* renamed from: a */
    boolean m5824a(int i, int i2, Object obj) {
        boolean z = true;
        if (i2 < 1) {
            return false;
        }
        this.f3141a.add(mo1004a(4, i, i2, obj));
        this.f3148h |= 4;
        if (this.f3141a.size() != 1) {
            z = false;
        }
        return z;
    }

    /* renamed from: b */
    int m5825b(int i) {
        return m5816a(i, 0);
    }

    /* renamed from: b */
    void m5826b() {
        this.f3146f.m5513a(this.f3141a);
        int size = this.f3141a.size();
        for (int i = 0; i < size; i++) {
            C1059b c1059b = (C1059b) this.f3141a.get(i);
            switch (c1059b.f3137a) {
                case 1:
                    m5814f(c1059b);
                    break;
                case 2:
                    m5809c(c1059b);
                    break;
                case 4:
                    m5811d(c1059b);
                    break;
                case 8:
                    m5808b(c1059b);
                    break;
            }
            if (this.f3144d != null) {
                this.f3144d.run();
            }
        }
        this.f3141a.clear();
    }

    /* renamed from: b */
    boolean m5827b(int i, int i2) {
        boolean z = true;
        if (i2 < 1) {
            return false;
        }
        this.f3141a.add(mo1004a(1, i, i2, null));
        this.f3148h |= 1;
        if (this.f3141a.size() != 1) {
            z = false;
        }
        return z;
    }

    /* renamed from: c */
    public int m5828c(int i) {
        int size = this.f3141a.size();
        int i2 = i;
        for (int i3 = 0; i3 < size; i3++) {
            C1059b c1059b = (C1059b) this.f3141a.get(i3);
            switch (c1059b.f3137a) {
                case 1:
                    if (c1059b.f3138b > i2) {
                        break;
                    }
                    i2 += c1059b.f3140d;
                    break;
                case 2:
                    if (c1059b.f3138b <= i2) {
                        if (c1059b.f3138b + c1059b.f3140d <= i2) {
                            i2 -= c1059b.f3140d;
                            break;
                        }
                        return -1;
                    }
                    continue;
                case 8:
                    if (c1059b.f3138b != i2) {
                        if (c1059b.f3138b < i2) {
                            i2--;
                        }
                        if (c1059b.f3140d > i2) {
                            break;
                        }
                        i2++;
                        break;
                    }
                    i2 = c1059b.f3140d;
                    break;
                default:
                    break;
            }
        }
        return i2;
    }

    /* renamed from: c */
    void m5829c() {
        int size = this.f3142b.size();
        for (int i = 0; i < size; i++) {
            this.f3143c.mo866b((C1059b) this.f3142b.get(i));
        }
        m5821a(this.f3142b);
        this.f3148h = 0;
    }

    /* renamed from: c */
    boolean m5830c(int i, int i2) {
        boolean z = true;
        if (i2 < 1) {
            return false;
        }
        this.f3141a.add(mo1004a(2, i, i2, null));
        this.f3148h |= 2;
        if (this.f3141a.size() != 1) {
            z = false;
        }
        return z;
    }

    /* renamed from: d */
    boolean m5831d() {
        return this.f3141a.size() > 0;
    }

    /* renamed from: e */
    void m5832e() {
        m5829c();
        int size = this.f3141a.size();
        for (int i = 0; i < size; i++) {
            C1059b c1059b = (C1059b) this.f3141a.get(i);
            switch (c1059b.f3137a) {
                case 1:
                    this.f3143c.mo866b(c1059b);
                    this.f3143c.mo867c(c1059b.f3138b, c1059b.f3140d);
                    break;
                case 2:
                    this.f3143c.mo866b(c1059b);
                    this.f3143c.mo862a(c1059b.f3138b, c1059b.f3140d);
                    break;
                case 4:
                    this.f3143c.mo866b(c1059b);
                    this.f3143c.mo863a(c1059b.f3138b, c1059b.f3140d, c1059b.f3139c);
                    break;
                case 8:
                    this.f3143c.mo866b(c1059b);
                    this.f3143c.mo868d(c1059b.f3138b, c1059b.f3140d);
                    break;
            }
            if (this.f3144d != null) {
                this.f3144d.run();
            }
        }
        m5821a(this.f3141a);
        this.f3148h = 0;
    }

    /* renamed from: f */
    boolean m5833f() {
        return (this.f3142b.isEmpty() || this.f3141a.isEmpty()) ? false : true;
    }
}
