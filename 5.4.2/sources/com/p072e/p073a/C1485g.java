package com.p072e.p073a;

import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p072e.p073a.C1489f.C1490a;
import com.p072e.p073a.C1489f.C1491b;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: com.e.a.g */
class C1485g {
    /* renamed from: a */
    int f4498a;
    /* renamed from: b */
    C1489f f4499b;
    /* renamed from: c */
    C1489f f4500c;
    /* renamed from: d */
    Interpolator f4501d;
    /* renamed from: e */
    ArrayList<C1489f> f4502e = new ArrayList();
    /* renamed from: f */
    C1483k f4503f;

    public C1485g(C1489f... c1489fArr) {
        this.f4498a = c1489fArr.length;
        this.f4502e.addAll(Arrays.asList(c1489fArr));
        this.f4499b = (C1489f) this.f4502e.get(0);
        this.f4500c = (C1489f) this.f4502e.get(this.f4498a - 1);
        this.f4501d = this.f4500c.m7366d();
    }

    /* renamed from: a */
    public static C1485g m7342a(float... fArr) {
        int i = 1;
        int length = fArr.length;
        C1490a[] c1490aArr = new C1490a[Math.max(length, 2)];
        if (length == 1) {
            c1490aArr[0] = (C1490a) C1489f.m7360b(BitmapDescriptorFactory.HUE_RED);
            c1490aArr[1] = (C1490a) C1489f.m7358a(1.0f, fArr[0]);
        } else {
            c1490aArr[0] = (C1490a) C1489f.m7358a((float) BitmapDescriptorFactory.HUE_RED, fArr[0]);
            while (i < length) {
                c1490aArr[i] = (C1490a) C1489f.m7358a(((float) i) / ((float) (length - 1)), fArr[i]);
                i++;
            }
        }
        return new C1486c(c1490aArr);
    }

    /* renamed from: a */
    public static C1485g m7343a(int... iArr) {
        int i = 1;
        int length = iArr.length;
        C1491b[] c1491bArr = new C1491b[Math.max(length, 2)];
        if (length == 1) {
            c1491bArr[0] = (C1491b) C1489f.m7357a((float) BitmapDescriptorFactory.HUE_RED);
            c1491bArr[1] = (C1491b) C1489f.m7359a(1.0f, iArr[0]);
        } else {
            c1491bArr[0] = (C1491b) C1489f.m7359a((float) BitmapDescriptorFactory.HUE_RED, iArr[0]);
            while (i < length) {
                c1491bArr[i] = (C1491b) C1489f.m7359a(((float) i) / ((float) (length - 1)), iArr[i]);
                i++;
            }
        }
        return new C1488e(c1491bArr);
    }

    /* renamed from: a */
    public Object mo1181a(float f) {
        if (this.f4498a == 2) {
            if (this.f4501d != null) {
                f = this.f4501d.getInterpolation(f);
            }
            return this.f4503f.mo1180a(f, this.f4499b.mo1185b(), this.f4500c.mo1185b());
        } else if (f <= BitmapDescriptorFactory.HUE_RED) {
            r0 = (C1489f) this.f4502e.get(1);
            r1 = r0.m7366d();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r1 = this.f4499b.m7365c();
            return this.f4503f.mo1180a((f - r1) / (r0.m7365c() - r1), this.f4499b.mo1185b(), r0.mo1185b());
        } else if (f >= 1.0f) {
            r0 = (C1489f) this.f4502e.get(this.f4498a - 2);
            r1 = this.f4500c.m7366d();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r1 = r0.m7365c();
            return this.f4503f.mo1180a((f - r1) / (this.f4500c.m7365c() - r1), r0.mo1185b(), this.f4500c.mo1185b());
        } else {
            C1489f c1489f = this.f4499b;
            int i = 1;
            while (i < this.f4498a) {
                r0 = (C1489f) this.f4502e.get(i);
                if (f < r0.m7365c()) {
                    r1 = r0.m7366d();
                    if (r1 != null) {
                        f = r1.getInterpolation(f);
                    }
                    r1 = c1489f.m7365c();
                    return this.f4503f.mo1180a((f - r1) / (r0.m7365c() - r1), c1489f.mo1185b(), r0.mo1185b());
                }
                i++;
                c1489f = r0;
            }
            return this.f4500c.mo1185b();
        }
    }

    /* renamed from: a */
    public void m7345a(C1483k c1483k) {
        this.f4503f = c1483k;
    }

    /* renamed from: b */
    public C1485g mo1182b() {
        ArrayList arrayList = this.f4502e;
        int size = this.f4502e.size();
        C1489f[] c1489fArr = new C1489f[size];
        for (int i = 0; i < size; i++) {
            c1489fArr[i] = ((C1489f) arrayList.get(i)).mo1187e();
        }
        return new C1485g(c1489fArr);
    }

    public /* synthetic */ Object clone() {
        return mo1182b();
    }

    public String toString() {
        String str = " ";
        int i = 0;
        while (i < this.f4498a) {
            String str2 = str + ((C1489f) this.f4502e.get(i)).mo1185b() + "  ";
            i++;
            str = str2;
        }
        return str;
    }
}
