package android.support.v7.widget;

import android.support.v7.widget.C1060f.C1059b;
import java.util.List;

class at {
    /* renamed from: a */
    final C1026a f2930a;

    /* renamed from: android.support.v7.widget.at$a */
    interface C1026a {
        /* renamed from: a */
        C1059b mo1004a(int i, int i2, int i3, Object obj);

        /* renamed from: a */
        void mo1005a(C1059b c1059b);
    }

    public at(C1026a c1026a) {
        this.f2930a = c1026a;
    }

    /* renamed from: a */
    private void m5510a(List<C1059b> list, int i, int i2) {
        C1059b c1059b = (C1059b) list.get(i);
        C1059b c1059b2 = (C1059b) list.get(i2);
        switch (c1059b2.f3137a) {
            case 1:
                m5512c(list, i, c1059b, i2, c1059b2);
                return;
            case 2:
                m5514a(list, i, c1059b, i2, c1059b2);
                return;
            case 4:
                m5515b(list, i, c1059b, i2, c1059b2);
                return;
            default:
                return;
        }
    }

    /* renamed from: b */
    private int m5511b(List<C1059b> list) {
        Object obj = null;
        int size = list.size() - 1;
        while (size >= 0) {
            Object obj2;
            if (((C1059b) list.get(size)).f3137a != 8) {
                obj2 = 1;
            } else if (obj != null) {
                return size;
            } else {
                obj2 = obj;
            }
            size--;
            obj = obj2;
        }
        return -1;
    }

    /* renamed from: c */
    private void m5512c(List<C1059b> list, int i, C1059b c1059b, int i2, C1059b c1059b2) {
        int i3 = 0;
        if (c1059b.f3140d < c1059b2.f3138b) {
            i3 = -1;
        }
        if (c1059b.f3138b < c1059b2.f3138b) {
            i3++;
        }
        if (c1059b2.f3138b <= c1059b.f3138b) {
            c1059b.f3138b += c1059b2.f3140d;
        }
        if (c1059b2.f3138b <= c1059b.f3140d) {
            c1059b.f3140d += c1059b2.f3140d;
        }
        c1059b2.f3138b = i3 + c1059b2.f3138b;
        list.set(i, c1059b2);
        list.set(i2, c1059b);
    }

    /* renamed from: a */
    void m5513a(List<C1059b> list) {
        while (true) {
            int b = m5511b(list);
            if (b != -1) {
                m5510a(list, b, b + 1);
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    void m5514a(List<C1059b> list, int i, C1059b c1059b, int i2, C1059b c1059b2) {
        int i3;
        C1059b c1059b3;
        int i4 = 0;
        if (c1059b.f3138b < c1059b.f3140d) {
            i3 = (c1059b2.f3138b == c1059b.f3138b && c1059b2.f3140d == c1059b.f3140d - c1059b.f3138b) ? 1 : 0;
        } else if (c1059b2.f3138b == c1059b.f3140d + 1 && c1059b2.f3140d == c1059b.f3138b - c1059b.f3140d) {
            i4 = 1;
            i3 = 1;
        } else {
            i3 = 0;
            i4 = 1;
        }
        if (c1059b.f3140d < c1059b2.f3138b) {
            c1059b2.f3138b--;
        } else if (c1059b.f3140d < c1059b2.f3138b + c1059b2.f3140d) {
            c1059b2.f3140d--;
            c1059b.f3137a = 2;
            c1059b.f3140d = 1;
            if (c1059b2.f3140d == 0) {
                list.remove(i2);
                this.f2930a.mo1005a(c1059b2);
                return;
            }
            return;
        }
        if (c1059b.f3138b <= c1059b2.f3138b) {
            c1059b2.f3138b++;
            c1059b3 = null;
        } else if (c1059b.f3138b < c1059b2.f3138b + c1059b2.f3140d) {
            c1059b3 = this.f2930a.mo1004a(2, c1059b.f3138b + 1, (c1059b2.f3138b + c1059b2.f3140d) - c1059b.f3138b, null);
            c1059b2.f3140d = c1059b.f3138b - c1059b2.f3138b;
        } else {
            c1059b3 = null;
        }
        if (i3 != 0) {
            list.set(i, c1059b2);
            list.remove(i2);
            this.f2930a.mo1005a(c1059b);
            return;
        }
        if (i4 != 0) {
            if (c1059b3 != null) {
                if (c1059b.f3138b > c1059b3.f3138b) {
                    c1059b.f3138b -= c1059b3.f3140d;
                }
                if (c1059b.f3140d > c1059b3.f3138b) {
                    c1059b.f3140d -= c1059b3.f3140d;
                }
            }
            if (c1059b.f3138b > c1059b2.f3138b) {
                c1059b.f3138b -= c1059b2.f3140d;
            }
            if (c1059b.f3140d > c1059b2.f3138b) {
                c1059b.f3140d -= c1059b2.f3140d;
            }
        } else {
            if (c1059b3 != null) {
                if (c1059b.f3138b >= c1059b3.f3138b) {
                    c1059b.f3138b -= c1059b3.f3140d;
                }
                if (c1059b.f3140d >= c1059b3.f3138b) {
                    c1059b.f3140d -= c1059b3.f3140d;
                }
            }
            if (c1059b.f3138b >= c1059b2.f3138b) {
                c1059b.f3138b -= c1059b2.f3140d;
            }
            if (c1059b.f3140d >= c1059b2.f3138b) {
                c1059b.f3140d -= c1059b2.f3140d;
            }
        }
        list.set(i, c1059b2);
        if (c1059b.f3138b != c1059b.f3140d) {
            list.set(i2, c1059b);
        } else {
            list.remove(i2);
        }
        if (c1059b3 != null) {
            list.add(i, c1059b3);
        }
    }

    /* renamed from: b */
    void m5515b(List<C1059b> list, int i, C1059b c1059b, int i2, C1059b c1059b2) {
        Object obj;
        Object obj2 = null;
        if (c1059b.f3140d < c1059b2.f3138b) {
            c1059b2.f3138b--;
            obj = null;
        } else if (c1059b.f3140d < c1059b2.f3138b + c1059b2.f3140d) {
            c1059b2.f3140d--;
            obj = this.f2930a.mo1004a(4, c1059b.f3138b, 1, c1059b2.f3139c);
        } else {
            obj = null;
        }
        if (c1059b.f3138b <= c1059b2.f3138b) {
            c1059b2.f3138b++;
        } else if (c1059b.f3138b < c1059b2.f3138b + c1059b2.f3140d) {
            int i3 = (c1059b2.f3138b + c1059b2.f3140d) - c1059b.f3138b;
            obj2 = this.f2930a.mo1004a(4, c1059b.f3138b + 1, i3, c1059b2.f3139c);
            c1059b2.f3140d -= i3;
        }
        list.set(i2, c1059b);
        if (c1059b2.f3140d > 0) {
            list.set(i, c1059b2);
        } else {
            list.remove(i);
            this.f2930a.mo1005a(c1059b2);
        }
        if (obj != null) {
            list.add(i, obj);
        }
        if (obj2 != null) {
            list.add(i, obj2);
        }
    }
}
