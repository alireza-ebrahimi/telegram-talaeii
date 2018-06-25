package com.p072e.p073a;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p072e.p073a.C1489f.C1490a;
import java.util.ArrayList;

/* renamed from: com.e.a.c */
class C1486c extends C1485g {
    /* renamed from: g */
    private float f4504g;
    /* renamed from: h */
    private float f4505h;
    /* renamed from: i */
    private float f4506i;
    /* renamed from: j */
    private boolean f4507j = true;

    public C1486c(C1490a... c1490aArr) {
        super(c1490aArr);
    }

    /* renamed from: a */
    public C1486c m7347a() {
        ArrayList arrayList = this.e;
        int size = this.e.size();
        C1490a[] c1490aArr = new C1490a[size];
        for (int i = 0; i < size; i++) {
            c1490aArr[i] = (C1490a) ((C1489f) arrayList.get(i)).mo1187e();
        }
        return new C1486c(c1490aArr);
    }

    /* renamed from: a */
    public Object mo1181a(float f) {
        return Float.valueOf(m7349b(f));
    }

    /* renamed from: b */
    public float m7349b(float f) {
        int i = 1;
        if (this.a == 2) {
            if (this.f4507j) {
                this.f4507j = false;
                this.f4504g = ((C1490a) this.e.get(0)).m7371f();
                this.f4505h = ((C1490a) this.e.get(1)).m7371f();
                this.f4506i = this.f4505h - this.f4504g;
            }
            if (this.d != null) {
                f = this.d.getInterpolation(f);
            }
            return this.f == null ? this.f4504g + (this.f4506i * f) : ((Number) this.f.mo1180a(f, Float.valueOf(this.f4504g), Float.valueOf(this.f4505h))).floatValue();
        } else if (f <= BitmapDescriptorFactory.HUE_RED) {
            r0 = (C1490a) this.e.get(0);
            r1 = (C1490a) this.e.get(1);
            r2 = r0.m7371f();
            r3 = r1.m7371f();
            r0 = r0.m7365c();
            r4 = r1.m7365c();
            r1 = r1.m7366d();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.f == null ? (r0 * (r3 - r2)) + r2 : ((Number) this.f.mo1180a(r0, Float.valueOf(r2), Float.valueOf(r3))).floatValue();
        } else if (f >= 1.0f) {
            r0 = (C1490a) this.e.get(this.a - 2);
            r1 = (C1490a) this.e.get(this.a - 1);
            r2 = r0.m7371f();
            r3 = r1.m7371f();
            r0 = r0.m7365c();
            r4 = r1.m7365c();
            r1 = r1.m7366d();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.f == null ? (r0 * (r3 - r2)) + r2 : ((Number) this.f.mo1180a(r0, Float.valueOf(r2), Float.valueOf(r3))).floatValue();
        } else {
            C1490a c1490a = (C1490a) this.e.get(0);
            while (i < this.a) {
                r0 = (C1490a) this.e.get(i);
                if (f < r0.m7365c()) {
                    r1 = r0.m7366d();
                    if (r1 != null) {
                        f = r1.getInterpolation(f);
                    }
                    float c = (f - c1490a.m7365c()) / (r0.m7365c() - c1490a.m7365c());
                    r2 = c1490a.m7371f();
                    r0 = r0.m7371f();
                    return this.f == null ? ((r0 - r2) * c) + r2 : ((Number) this.f.mo1180a(c, Float.valueOf(r2), Float.valueOf(r0))).floatValue();
                } else {
                    i++;
                    c1490a = r0;
                }
            }
            return ((Number) ((C1489f) this.e.get(this.a - 1)).mo1185b()).floatValue();
        }
    }

    /* renamed from: b */
    public /* synthetic */ C1485g mo1182b() {
        return m7347a();
    }

    public /* synthetic */ Object clone() {
        return m7347a();
    }
}
