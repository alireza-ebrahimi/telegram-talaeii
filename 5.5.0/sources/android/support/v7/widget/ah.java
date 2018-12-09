package android.support.v7.widget;

import android.support.v4.p017a.C0211a;
import android.support.v4.view.ax;
import android.support.v4.view.bb;
import android.support.v7.widget.RecyclerView.C0955v;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ah extends bd {
    /* renamed from: a */
    ArrayList<ArrayList<C0955v>> f2826a = new ArrayList();
    /* renamed from: b */
    ArrayList<ArrayList<C1009b>> f2827b = new ArrayList();
    /* renamed from: c */
    ArrayList<ArrayList<C1008a>> f2828c = new ArrayList();
    /* renamed from: d */
    ArrayList<C0955v> f2829d = new ArrayList();
    /* renamed from: e */
    ArrayList<C0955v> f2830e = new ArrayList();
    /* renamed from: f */
    ArrayList<C0955v> f2831f = new ArrayList();
    /* renamed from: g */
    ArrayList<C0955v> f2832g = new ArrayList();
    /* renamed from: i */
    private ArrayList<C0955v> f2833i = new ArrayList();
    /* renamed from: j */
    private ArrayList<C0955v> f2834j = new ArrayList();
    /* renamed from: k */
    private ArrayList<C1009b> f2835k = new ArrayList();
    /* renamed from: l */
    private ArrayList<C1008a> f2836l = new ArrayList();

    /* renamed from: android.support.v7.widget.ah$c */
    private static class C1002c implements bb {
        C1002c() {
        }

        public void onAnimationCancel(View view) {
        }

        public void onAnimationEnd(View view) {
        }

        public void onAnimationStart(View view) {
        }
    }

    /* renamed from: android.support.v7.widget.ah$a */
    private static class C1008a {
        /* renamed from: a */
        public C0955v f2814a;
        /* renamed from: b */
        public C0955v f2815b;
        /* renamed from: c */
        public int f2816c;
        /* renamed from: d */
        public int f2817d;
        /* renamed from: e */
        public int f2818e;
        /* renamed from: f */
        public int f2819f;

        private C1008a(C0955v c0955v, C0955v c0955v2) {
            this.f2814a = c0955v;
            this.f2815b = c0955v2;
        }

        C1008a(C0955v c0955v, C0955v c0955v2, int i, int i2, int i3, int i4) {
            this(c0955v, c0955v2);
            this.f2816c = i;
            this.f2817d = i2;
            this.f2818e = i3;
            this.f2819f = i4;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.f2814a + ", newHolder=" + this.f2815b + ", fromX=" + this.f2816c + ", fromY=" + this.f2817d + ", toX=" + this.f2818e + ", toY=" + this.f2819f + '}';
        }
    }

    /* renamed from: android.support.v7.widget.ah$b */
    private static class C1009b {
        /* renamed from: a */
        public C0955v f2820a;
        /* renamed from: b */
        public int f2821b;
        /* renamed from: c */
        public int f2822c;
        /* renamed from: d */
        public int f2823d;
        /* renamed from: e */
        public int f2824e;

        C1009b(C0955v c0955v, int i, int i2, int i3, int i4) {
            this.f2820a = c0955v;
            this.f2821b = i;
            this.f2822c = i2;
            this.f2823d = i3;
            this.f2824e = i4;
        }
    }

    /* renamed from: a */
    private void m5411a(List<C1008a> list, C0955v c0955v) {
        for (int size = list.size() - 1; size >= 0; size--) {
            C1008a c1008a = (C1008a) list.get(size);
            if (m5412a(c1008a, c0955v) && c1008a.f2814a == null && c1008a.f2815b == null) {
                list.remove(c1008a);
            }
        }
    }

    /* renamed from: a */
    private boolean m5412a(C1008a c1008a, C0955v c0955v) {
        boolean z = false;
        if (c1008a.f2815b == c0955v) {
            c1008a.f2815b = null;
        } else if (c1008a.f2814a != c0955v) {
            return false;
        } else {
            c1008a.f2814a = null;
            z = true;
        }
        android.support.v4.view.ah.m2800c(c0955v.itemView, 1.0f);
        android.support.v4.view.ah.m2775a(c0955v.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        android.support.v4.view.ah.m2795b(c0955v.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        m5386a(c0955v, z);
        return true;
    }

    /* renamed from: b */
    private void m5413b(C1008a c1008a) {
        if (c1008a.f2814a != null) {
            m5412a(c1008a, c1008a.f2814a);
        }
        if (c1008a.f2815b != null) {
            m5412a(c1008a, c1008a.f2815b);
        }
    }

    /* renamed from: u */
    private void m5414u(final C0955v c0955v) {
        final ax q = android.support.v4.view.ah.m2827q(c0955v.itemView);
        this.f2831f.add(c0955v);
        q.m3021a(m4841g()).m3020a((float) BitmapDescriptorFactory.HUE_RED).m3022a(new C1002c(this) {
            /* renamed from: c */
            final /* synthetic */ ah f2798c;

            public void onAnimationEnd(View view) {
                q.m3022a(null);
                android.support.v4.view.ah.m2800c(view, 1.0f);
                this.f2798c.m5399i(c0955v);
                this.f2798c.f2831f.remove(c0955v);
                this.f2798c.m5426c();
            }

            public void onAnimationStart(View view) {
                this.f2798c.m5402l(c0955v);
            }
        }).m3029c();
    }

    /* renamed from: v */
    private void m5415v(C0955v c0955v) {
        C0211a.m993a(c0955v.itemView);
        mo930d(c0955v);
    }

    /* renamed from: a */
    public void mo922a() {
        int i = !this.f2833i.isEmpty() ? 1 : 0;
        int i2 = !this.f2835k.isEmpty() ? 1 : 0;
        int i3 = !this.f2836l.isEmpty() ? 1 : 0;
        int i4 = !this.f2834j.isEmpty() ? 1 : 0;
        if (i != 0 || i2 != 0 || i4 != 0 || i3 != 0) {
            final ArrayList arrayList;
            Runnable c09991;
            Iterator it = this.f2833i.iterator();
            while (it.hasNext()) {
                m5414u((C0955v) it.next());
            }
            this.f2833i.clear();
            if (i2 != 0) {
                arrayList = new ArrayList();
                arrayList.addAll(this.f2835k);
                this.f2827b.add(arrayList);
                this.f2835k.clear();
                c09991 = new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ ah f2791b;

                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            C1009b c1009b = (C1009b) it.next();
                            this.f2791b.m5423b(c1009b.f2820a, c1009b.f2821b, c1009b.f2822c, c1009b.f2823d, c1009b.f2824e);
                        }
                        arrayList.clear();
                        this.f2791b.f2827b.remove(arrayList);
                    }
                };
                if (i != 0) {
                    android.support.v4.view.ah.m2788a(((C1009b) arrayList.get(0)).f2820a.itemView, c09991, m4841g());
                } else {
                    c09991.run();
                }
            }
            if (i3 != 0) {
                arrayList = new ArrayList();
                arrayList.addAll(this.f2836l);
                this.f2828c.add(arrayList);
                this.f2836l.clear();
                c09991 = new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ ah f2793b;

                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            this.f2793b.m5417a((C1008a) it.next());
                        }
                        arrayList.clear();
                        this.f2793b.f2828c.remove(arrayList);
                    }
                };
                if (i != 0) {
                    android.support.v4.view.ah.m2788a(((C1008a) arrayList.get(0)).f2814a.itemView, c09991, m4841g());
                } else {
                    c09991.run();
                }
            }
            if (i4 != 0) {
                final ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(this.f2834j);
                this.f2826a.add(arrayList2);
                this.f2834j.clear();
                Runnable c10013 = new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ ah f2795b;

                    public void run() {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            this.f2795b.m5427c((C0955v) it.next());
                        }
                        arrayList2.clear();
                        this.f2795b.f2826a.remove(arrayList2);
                    }
                };
                if (i == 0 && i2 == 0 && i3 == 0) {
                    c10013.run();
                } else {
                    android.support.v4.view.ah.m2788a(((C0955v) arrayList2.get(0)).itemView, c10013, (i != 0 ? m4841g() : 0) + Math.max(i2 != 0 ? m4838e() : 0, i3 != 0 ? m4843h() : 0));
                }
            }
        }
    }

    /* renamed from: a */
    void m5417a(final C1008a c1008a) {
        View view = null;
        C0955v c0955v = c1008a.f2814a;
        View view2 = c0955v == null ? null : c0955v.itemView;
        C0955v c0955v2 = c1008a.f2815b;
        if (c0955v2 != null) {
            view = c0955v2.itemView;
        }
        if (view2 != null) {
            final ax a = android.support.v4.view.ah.m2827q(view2).m3021a(m4843h());
            this.f2832g.add(c1008a.f2814a);
            a.m3025b((float) (c1008a.f2818e - c1008a.f2816c));
            a.m3028c((float) (c1008a.f2819f - c1008a.f2817d));
            a.m3020a((float) BitmapDescriptorFactory.HUE_RED).m3022a(new C1002c(this) {
                /* renamed from: c */
                final /* synthetic */ ah f2809c;

                public void onAnimationEnd(View view) {
                    a.m3022a(null);
                    android.support.v4.view.ah.m2800c(view, 1.0f);
                    android.support.v4.view.ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                    android.support.v4.view.ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                    this.f2809c.m5386a(c1008a.f2814a, true);
                    this.f2809c.f2832g.remove(c1008a.f2814a);
                    this.f2809c.m5426c();
                }

                public void onAnimationStart(View view) {
                    this.f2809c.m5392b(c1008a.f2814a, true);
                }
            }).m3029c();
        }
        if (view != null) {
            a = android.support.v4.view.ah.m2827q(view);
            this.f2832g.add(c1008a.f2815b);
            a.m3025b((float) BitmapDescriptorFactory.HUE_RED).m3028c(BitmapDescriptorFactory.HUE_RED).m3021a(m4843h()).m3020a(1.0f).m3022a(new C1002c(this) {
                /* renamed from: d */
                final /* synthetic */ ah f2813d;

                public void onAnimationEnd(View view) {
                    a.m3022a(null);
                    android.support.v4.view.ah.m2800c(view, 1.0f);
                    android.support.v4.view.ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                    android.support.v4.view.ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                    this.f2813d.m5386a(c1008a.f2815b, false);
                    this.f2813d.f2832g.remove(c1008a.f2815b);
                    this.f2813d.m5426c();
                }

                public void onAnimationStart(View view) {
                    this.f2813d.m5392b(c1008a.f2815b, false);
                }
            }).m3029c();
        }
    }

    /* renamed from: a */
    void m5418a(List<C0955v> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            android.support.v4.view.ah.m2827q(((C0955v) list.get(size)).itemView).m3027b();
        }
    }

    /* renamed from: a */
    public boolean mo923a(C0955v c0955v) {
        m5415v(c0955v);
        this.f2833i.add(c0955v);
        return true;
    }

    /* renamed from: a */
    public boolean mo924a(C0955v c0955v, int i, int i2, int i3, int i4) {
        View view = c0955v.itemView;
        int l = (int) (((float) i) + android.support.v4.view.ah.m2822l(c0955v.itemView));
        int m = (int) (((float) i2) + android.support.v4.view.ah.m2823m(c0955v.itemView));
        m5415v(c0955v);
        int i5 = i3 - l;
        int i6 = i4 - m;
        if (i5 == 0 && i6 == 0) {
            m5400j(c0955v);
            return false;
        }
        if (i5 != 0) {
            android.support.v4.view.ah.m2775a(view, (float) (-i5));
        }
        if (i6 != 0) {
            android.support.v4.view.ah.m2795b(view, (float) (-i6));
        }
        this.f2835k.add(new C1009b(c0955v, l, m, i3, i4));
        return true;
    }

    /* renamed from: a */
    public boolean mo925a(C0955v c0955v, C0955v c0955v2, int i, int i2, int i3, int i4) {
        if (c0955v == c0955v2) {
            return mo924a(c0955v, i, i2, i3, i4);
        }
        float l = android.support.v4.view.ah.m2822l(c0955v.itemView);
        float m = android.support.v4.view.ah.m2823m(c0955v.itemView);
        float e = android.support.v4.view.ah.m2806e(c0955v.itemView);
        m5415v(c0955v);
        int i5 = (int) (((float) (i3 - i)) - l);
        int i6 = (int) (((float) (i4 - i2)) - m);
        android.support.v4.view.ah.m2775a(c0955v.itemView, l);
        android.support.v4.view.ah.m2795b(c0955v.itemView, m);
        android.support.v4.view.ah.m2800c(c0955v.itemView, e);
        if (c0955v2 != null) {
            m5415v(c0955v2);
            android.support.v4.view.ah.m2775a(c0955v2.itemView, (float) (-i5));
            android.support.v4.view.ah.m2795b(c0955v2.itemView, (float) (-i6));
            android.support.v4.view.ah.m2800c(c0955v2.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        }
        this.f2836l.add(new C1008a(c0955v, c0955v2, i, i2, i3, i4));
        return true;
    }

    /* renamed from: a */
    public boolean mo926a(C0955v c0955v, List<Object> list) {
        return !list.isEmpty() || super.mo926a(c0955v, (List) list);
    }

    /* renamed from: b */
    void m5423b(C0955v c0955v, int i, int i2, int i3, int i4) {
        View view = c0955v.itemView;
        final int i5 = i3 - i;
        final int i6 = i4 - i2;
        if (i5 != 0) {
            android.support.v4.view.ah.m2827q(view).m3025b((float) BitmapDescriptorFactory.HUE_RED);
        }
        if (i6 != 0) {
            android.support.v4.view.ah.m2827q(view).m3028c(BitmapDescriptorFactory.HUE_RED);
        }
        final ax q = android.support.v4.view.ah.m2827q(view);
        this.f2830e.add(c0955v);
        final C0955v c0955v2 = c0955v;
        q.m3021a(m4838e()).m3022a(new C1002c(this) {
            /* renamed from: e */
            final /* synthetic */ ah f2806e;

            public void onAnimationCancel(View view) {
                if (i5 != 0) {
                    android.support.v4.view.ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                }
                if (i6 != 0) {
                    android.support.v4.view.ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                }
            }

            public void onAnimationEnd(View view) {
                q.m3022a(null);
                this.f2806e.m5400j(c0955v2);
                this.f2806e.f2830e.remove(c0955v2);
                this.f2806e.m5426c();
            }

            public void onAnimationStart(View view) {
                this.f2806e.m5403m(c0955v2);
            }
        }).m3029c();
    }

    /* renamed from: b */
    public boolean mo927b() {
        return (this.f2834j.isEmpty() && this.f2836l.isEmpty() && this.f2835k.isEmpty() && this.f2833i.isEmpty() && this.f2830e.isEmpty() && this.f2831f.isEmpty() && this.f2829d.isEmpty() && this.f2832g.isEmpty() && this.f2827b.isEmpty() && this.f2826a.isEmpty() && this.f2828c.isEmpty()) ? false : true;
    }

    /* renamed from: b */
    public boolean mo928b(C0955v c0955v) {
        m5415v(c0955v);
        android.support.v4.view.ah.m2800c(c0955v.itemView, (float) BitmapDescriptorFactory.HUE_RED);
        this.f2834j.add(c0955v);
        return true;
    }

    /* renamed from: c */
    void m5426c() {
        if (!mo927b()) {
            m4845i();
        }
    }

    /* renamed from: c */
    void m5427c(final C0955v c0955v) {
        final ax q = android.support.v4.view.ah.m2827q(c0955v.itemView);
        this.f2829d.add(c0955v);
        q.m3020a(1.0f).m3021a(m4839f()).m3022a(new C1002c(this) {
            /* renamed from: c */
            final /* synthetic */ ah f2801c;

            public void onAnimationCancel(View view) {
                android.support.v4.view.ah.m2800c(view, 1.0f);
            }

            public void onAnimationEnd(View view) {
                q.m3022a(null);
                this.f2801c.m5401k(c0955v);
                this.f2801c.f2829d.remove(c0955v);
                this.f2801c.m5426c();
            }

            public void onAnimationStart(View view) {
                this.f2801c.m5404n(c0955v);
            }
        }).m3029c();
    }

    /* renamed from: d */
    public void mo929d() {
        int size;
        for (size = this.f2835k.size() - 1; size >= 0; size--) {
            C1009b c1009b = (C1009b) this.f2835k.get(size);
            View view = c1009b.f2820a.itemView;
            android.support.v4.view.ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
            android.support.v4.view.ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
            m5400j(c1009b.f2820a);
            this.f2835k.remove(size);
        }
        for (size = this.f2833i.size() - 1; size >= 0; size--) {
            m5399i((C0955v) this.f2833i.get(size));
            this.f2833i.remove(size);
        }
        for (size = this.f2834j.size() - 1; size >= 0; size--) {
            C0955v c0955v = (C0955v) this.f2834j.get(size);
            android.support.v4.view.ah.m2800c(c0955v.itemView, 1.0f);
            m5401k(c0955v);
            this.f2834j.remove(size);
        }
        for (size = this.f2836l.size() - 1; size >= 0; size--) {
            m5413b((C1008a) this.f2836l.get(size));
        }
        this.f2836l.clear();
        if (mo927b()) {
            int size2;
            ArrayList arrayList;
            int size3;
            for (size2 = this.f2827b.size() - 1; size2 >= 0; size2--) {
                arrayList = (ArrayList) this.f2827b.get(size2);
                for (size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    C1009b c1009b2 = (C1009b) arrayList.get(size3);
                    View view2 = c1009b2.f2820a.itemView;
                    android.support.v4.view.ah.m2795b(view2, (float) BitmapDescriptorFactory.HUE_RED);
                    android.support.v4.view.ah.m2775a(view2, (float) BitmapDescriptorFactory.HUE_RED);
                    m5400j(c1009b2.f2820a);
                    arrayList.remove(size3);
                    if (arrayList.isEmpty()) {
                        this.f2827b.remove(arrayList);
                    }
                }
            }
            for (size2 = this.f2826a.size() - 1; size2 >= 0; size2--) {
                arrayList = (ArrayList) this.f2826a.get(size2);
                for (size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    C0955v c0955v2 = (C0955v) arrayList.get(size3);
                    android.support.v4.view.ah.m2800c(c0955v2.itemView, 1.0f);
                    m5401k(c0955v2);
                    arrayList.remove(size3);
                    if (arrayList.isEmpty()) {
                        this.f2826a.remove(arrayList);
                    }
                }
            }
            for (size2 = this.f2828c.size() - 1; size2 >= 0; size2--) {
                arrayList = (ArrayList) this.f2828c.get(size2);
                for (size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                    m5413b((C1008a) arrayList.get(size3));
                    if (arrayList.isEmpty()) {
                        this.f2828c.remove(arrayList);
                    }
                }
            }
            m5418a(this.f2831f);
            m5418a(this.f2830e);
            m5418a(this.f2829d);
            m5418a(this.f2832g);
            m4845i();
        }
    }

    /* renamed from: d */
    public void mo930d(C0955v c0955v) {
        int size;
        View view = c0955v.itemView;
        android.support.v4.view.ah.m2827q(view).m3027b();
        for (size = this.f2835k.size() - 1; size >= 0; size--) {
            if (((C1009b) this.f2835k.get(size)).f2820a == c0955v) {
                android.support.v4.view.ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                android.support.v4.view.ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                m5400j(c0955v);
                this.f2835k.remove(size);
            }
        }
        m5411a(this.f2836l, c0955v);
        if (this.f2833i.remove(c0955v)) {
            android.support.v4.view.ah.m2800c(view, 1.0f);
            m5399i(c0955v);
        }
        if (this.f2834j.remove(c0955v)) {
            android.support.v4.view.ah.m2800c(view, 1.0f);
            m5401k(c0955v);
        }
        for (size = this.f2828c.size() - 1; size >= 0; size--) {
            List list = (ArrayList) this.f2828c.get(size);
            m5411a(list, c0955v);
            if (list.isEmpty()) {
                this.f2828c.remove(size);
            }
        }
        for (int size2 = this.f2827b.size() - 1; size2 >= 0; size2--) {
            ArrayList arrayList = (ArrayList) this.f2827b.get(size2);
            int size3 = arrayList.size() - 1;
            while (size3 >= 0) {
                if (((C1009b) arrayList.get(size3)).f2820a == c0955v) {
                    android.support.v4.view.ah.m2795b(view, (float) BitmapDescriptorFactory.HUE_RED);
                    android.support.v4.view.ah.m2775a(view, (float) BitmapDescriptorFactory.HUE_RED);
                    m5400j(c0955v);
                    arrayList.remove(size3);
                    if (arrayList.isEmpty()) {
                        this.f2827b.remove(size2);
                    }
                } else {
                    size3--;
                }
            }
        }
        for (size = this.f2826a.size() - 1; size >= 0; size--) {
            arrayList = (ArrayList) this.f2826a.get(size);
            if (arrayList.remove(c0955v)) {
                android.support.v4.view.ah.m2800c(view, 1.0f);
                m5401k(c0955v);
                if (arrayList.isEmpty()) {
                    this.f2826a.remove(size);
                }
            }
        }
        if (this.f2831f.remove(c0955v)) {
        }
        if (this.f2829d.remove(c0955v)) {
        }
        if (this.f2832g.remove(c0955v)) {
        }
        if (this.f2830e.remove(c0955v)) {
            m5426c();
        } else {
            m5426c();
        }
    }
}
