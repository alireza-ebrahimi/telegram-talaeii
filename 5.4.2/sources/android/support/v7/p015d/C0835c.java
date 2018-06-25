package android.support.v7.p015d;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v7.d.c */
public final class C0835c {
    /* renamed from: a */
    public static final C0835c f1981a = new C0835c();
    /* renamed from: b */
    public static final C0835c f1982b = new C0835c();
    /* renamed from: c */
    public static final C0835c f1983c = new C0835c();
    /* renamed from: d */
    public static final C0835c f1984d = new C0835c();
    /* renamed from: e */
    public static final C0835c f1985e = new C0835c();
    /* renamed from: f */
    public static final C0835c f1986f = new C0835c();
    /* renamed from: g */
    final float[] f1987g = new float[3];
    /* renamed from: h */
    final float[] f1988h = new float[3];
    /* renamed from: i */
    final float[] f1989i = new float[3];
    /* renamed from: j */
    boolean f1990j = true;

    static {
        C0835c.m3997c(f1981a);
        C0835c.m3998d(f1981a);
        C0835c.m3996b(f1982b);
        C0835c.m3998d(f1982b);
        C0835c.m3994a(f1983c);
        C0835c.m3998d(f1983c);
        C0835c.m3997c(f1984d);
        C0835c.m3999e(f1984d);
        C0835c.m3996b(f1985e);
        C0835c.m3999e(f1985e);
        C0835c.m3994a(f1986f);
        C0835c.m3999e(f1986f);
    }

    C0835c() {
        C0835c.m3995a(this.f1987g);
        C0835c.m3995a(this.f1988h);
        m4000l();
    }

    /* renamed from: a */
    private static void m3994a(C0835c c0835c) {
        c0835c.f1988h[1] = 0.26f;
        c0835c.f1988h[2] = 0.45f;
    }

    /* renamed from: a */
    private static void m3995a(float[] fArr) {
        fArr[0] = BitmapDescriptorFactory.HUE_RED;
        fArr[1] = 0.5f;
        fArr[2] = 1.0f;
    }

    /* renamed from: b */
    private static void m3996b(C0835c c0835c) {
        c0835c.f1988h[0] = 0.3f;
        c0835c.f1988h[1] = 0.5f;
        c0835c.f1988h[2] = 0.7f;
    }

    /* renamed from: c */
    private static void m3997c(C0835c c0835c) {
        c0835c.f1988h[0] = 0.55f;
        c0835c.f1988h[1] = 0.74f;
    }

    /* renamed from: d */
    private static void m3998d(C0835c c0835c) {
        c0835c.f1987g[0] = 0.35f;
        c0835c.f1987g[1] = 1.0f;
    }

    /* renamed from: e */
    private static void m3999e(C0835c c0835c) {
        c0835c.f1987g[1] = 0.3f;
        c0835c.f1987g[2] = 0.4f;
    }

    /* renamed from: l */
    private void m4000l() {
        this.f1989i[0] = 0.24f;
        this.f1989i[1] = 0.52f;
        this.f1989i[2] = 0.24f;
    }

    /* renamed from: a */
    public float m4001a() {
        return this.f1987g[0];
    }

    /* renamed from: b */
    public float m4002b() {
        return this.f1987g[1];
    }

    /* renamed from: c */
    public float m4003c() {
        return this.f1987g[2];
    }

    /* renamed from: d */
    public float m4004d() {
        return this.f1988h[0];
    }

    /* renamed from: e */
    public float m4005e() {
        return this.f1988h[1];
    }

    /* renamed from: f */
    public float m4006f() {
        return this.f1988h[2];
    }

    /* renamed from: g */
    public float m4007g() {
        return this.f1989i[0];
    }

    /* renamed from: h */
    public float m4008h() {
        return this.f1989i[1];
    }

    /* renamed from: i */
    public float m4009i() {
        return this.f1989i[2];
    }

    /* renamed from: j */
    public boolean m4010j() {
        return this.f1990j;
    }

    /* renamed from: k */
    void m4011k() {
        int length;
        int i = 0;
        float f = BitmapDescriptorFactory.HUE_RED;
        for (float f2 : this.f1989i) {
            if (f2 > BitmapDescriptorFactory.HUE_RED) {
                f += f2;
            }
        }
        if (f != BitmapDescriptorFactory.HUE_RED) {
            length = this.f1989i.length;
            while (i < length) {
                if (this.f1989i[i] > BitmapDescriptorFactory.HUE_RED) {
                    float[] fArr = this.f1989i;
                    fArr[i] = fArr[i] / f;
                }
                i++;
            }
        }
    }
}
