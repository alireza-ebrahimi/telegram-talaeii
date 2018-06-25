package com.p072e.p073a;

import android.view.animation.Interpolator;

/* renamed from: com.e.a.f */
public abstract class C1489f implements Cloneable {
    /* renamed from: a */
    float f4512a;
    /* renamed from: b */
    Class f4513b;
    /* renamed from: c */
    boolean f4514c = false;
    /* renamed from: d */
    private Interpolator f4515d = null;

    /* renamed from: com.e.a.f$a */
    static class C1490a extends C1489f {
        /* renamed from: d */
        float f4516d;

        C1490a(float f) {
            this.a = f;
            this.b = Float.TYPE;
        }

        C1490a(float f, float f2) {
            this.a = f;
            this.f4516d = f2;
            this.b = Float.TYPE;
            this.c = true;
        }

        /* renamed from: a */
        public void mo1184a(Object obj) {
            if (obj != null && obj.getClass() == Float.class) {
                this.f4516d = ((Float) obj).floatValue();
                this.c = true;
            }
        }

        /* renamed from: b */
        public Object mo1185b() {
            return Float.valueOf(this.f4516d);
        }

        public /* synthetic */ Object clone() {
            return m7372g();
        }

        /* renamed from: e */
        public /* synthetic */ C1489f mo1187e() {
            return m7372g();
        }

        /* renamed from: f */
        public float m7371f() {
            return this.f4516d;
        }

        /* renamed from: g */
        public C1490a m7372g() {
            C1490a c1490a = new C1490a(m7365c(), this.f4516d);
            c1490a.m7361a(m7366d());
            return c1490a;
        }
    }

    /* renamed from: com.e.a.f$b */
    static class C1491b extends C1489f {
        /* renamed from: d */
        int f4517d;

        C1491b(float f) {
            this.a = f;
            this.b = Integer.TYPE;
        }

        C1491b(float f, int i) {
            this.a = f;
            this.f4517d = i;
            this.b = Integer.TYPE;
            this.c = true;
        }

        /* renamed from: a */
        public void mo1184a(Object obj) {
            if (obj != null && obj.getClass() == Integer.class) {
                this.f4517d = ((Integer) obj).intValue();
                this.c = true;
            }
        }

        /* renamed from: b */
        public Object mo1185b() {
            return Integer.valueOf(this.f4517d);
        }

        public /* synthetic */ Object clone() {
            return m7377g();
        }

        /* renamed from: e */
        public /* synthetic */ C1489f mo1187e() {
            return m7377g();
        }

        /* renamed from: f */
        public int m7376f() {
            return this.f4517d;
        }

        /* renamed from: g */
        public C1491b m7377g() {
            C1491b c1491b = new C1491b(m7365c(), this.f4517d);
            c1491b.m7361a(m7366d());
            return c1491b;
        }
    }

    /* renamed from: a */
    public static C1489f m7357a(float f) {
        return new C1491b(f);
    }

    /* renamed from: a */
    public static C1489f m7358a(float f, float f2) {
        return new C1490a(f, f2);
    }

    /* renamed from: a */
    public static C1489f m7359a(float f, int i) {
        return new C1491b(f, i);
    }

    /* renamed from: b */
    public static C1489f m7360b(float f) {
        return new C1490a(f);
    }

    /* renamed from: a */
    public void m7361a(Interpolator interpolator) {
        this.f4515d = interpolator;
    }

    /* renamed from: a */
    public abstract void mo1184a(Object obj);

    /* renamed from: a */
    public boolean m7363a() {
        return this.f4514c;
    }

    /* renamed from: b */
    public abstract Object mo1185b();

    /* renamed from: c */
    public float m7365c() {
        return this.f4512a;
    }

    public /* synthetic */ Object clone() {
        return mo1187e();
    }

    /* renamed from: d */
    public Interpolator m7366d() {
        return this.f4515d;
    }

    /* renamed from: e */
    public abstract C1489f mo1187e();
}
