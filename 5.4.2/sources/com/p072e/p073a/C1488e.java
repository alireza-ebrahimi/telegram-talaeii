package com.p072e.p073a;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p072e.p073a.C1489f.C1491b;
import java.util.ArrayList;

/* renamed from: com.e.a.e */
class C1488e extends C1485g {
    /* renamed from: g */
    private int f4508g;
    /* renamed from: h */
    private int f4509h;
    /* renamed from: i */
    private int f4510i;
    /* renamed from: j */
    private boolean f4511j = true;

    public C1488e(C1491b... c1491bArr) {
        super(c1491bArr);
    }

    /* renamed from: a */
    public C1488e m7353a() {
        ArrayList arrayList = this.e;
        int size = this.e.size();
        C1491b[] c1491bArr = new C1491b[size];
        for (int i = 0; i < size; i++) {
            c1491bArr[i] = (C1491b) ((C1489f) arrayList.get(i)).mo1187e();
        }
        return new C1488e(c1491bArr);
    }

    /* renamed from: a */
    public Object mo1181a(float f) {
        return Integer.valueOf(m7355b(f));
    }

    /* renamed from: b */
    public int m7355b(float f) {
        int i = 1;
        if (this.a == 2) {
            if (this.f4511j) {
                this.f4511j = false;
                this.f4508g = ((C1491b) this.e.get(0)).m7376f();
                this.f4509h = ((C1491b) this.e.get(1)).m7376f();
                this.f4510i = this.f4509h - this.f4508g;
            }
            if (this.d != null) {
                f = this.d.getInterpolation(f);
            }
            return this.f == null ? this.f4508g + ((int) (((float) this.f4510i) * f)) : ((Number) this.f.mo1180a(f, Integer.valueOf(this.f4508g), Integer.valueOf(this.f4509h))).intValue();
        } else if (f <= BitmapDescriptorFactory.HUE_RED) {
            r0 = (C1491b) this.e.get(0);
            r1 = (C1491b) this.e.get(1);
            r2 = r0.m7376f();
            r3 = r1.m7376f();
            r0 = r0.m7365c();
            r4 = r1.m7365c();
            r1 = r1.m7366d();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.f == null ? ((int) (r0 * ((float) (r3 - r2)))) + r2 : ((Number) this.f.mo1180a(r0, Integer.valueOf(r2), Integer.valueOf(r3))).intValue();
        } else if (f >= 1.0f) {
            r0 = (C1491b) this.e.get(this.a - 2);
            r1 = (C1491b) this.e.get(this.a - 1);
            r2 = r0.m7376f();
            r3 = r1.m7376f();
            r0 = r0.m7365c();
            r4 = r1.m7365c();
            r1 = r1.m7366d();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.f == null ? ((int) (r0 * ((float) (r3 - r2)))) + r2 : ((Number) this.f.mo1180a(r0, Integer.valueOf(r2), Integer.valueOf(r3))).intValue();
        } else {
            C1491b c1491b = (C1491b) this.e.get(0);
            while (i < this.a) {
                r0 = (C1491b) this.e.get(i);
                if (f < r0.m7365c()) {
                    r1 = r0.m7366d();
                    if (r1 != null) {
                        f = r1.getInterpolation(f);
                    }
                    float c = (f - c1491b.m7365c()) / (r0.m7365c() - c1491b.m7365c());
                    r2 = c1491b.m7376f();
                    int f2 = r0.m7376f();
                    return this.f == null ? ((int) (((float) (f2 - r2)) * c)) + r2 : ((Number) this.f.mo1180a(c, Integer.valueOf(r2), Integer.valueOf(f2))).intValue();
                } else {
                    i++;
                    c1491b = r0;
                }
            }
            return ((Number) ((C1489f) this.e.get(this.a - 1)).mo1185b()).intValue();
        }
    }

    /* renamed from: b */
    public /* synthetic */ C1485g mo1182b() {
        return m7353a();
    }

    public /* synthetic */ Object clone() {
        return m7353a();
    }
}
