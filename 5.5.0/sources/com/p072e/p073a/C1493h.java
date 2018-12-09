package com.p072e.p073a;

import android.view.View;
import com.p072e.p074b.C1494c;
import com.p072e.p075c.p076a.C1518a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.e.a.h */
public final class C1493h extends C1492l {
    /* renamed from: h */
    private static final Map<String, C1494c> f4547h = new HashMap();
    /* renamed from: i */
    private Object f4548i;
    /* renamed from: j */
    private String f4549j;
    /* renamed from: k */
    private C1494c f4550k;

    static {
        f4547h.put("alpha", C1506i.f4553a);
        f4547h.put("pivotX", C1506i.f4554b);
        f4547h.put("pivotY", C1506i.f4555c);
        f4547h.put("translationX", C1506i.f4556d);
        f4547h.put("translationY", C1506i.f4557e);
        f4547h.put("rotation", C1506i.f4558f);
        f4547h.put("rotationX", C1506i.f4559g);
        f4547h.put("rotationY", C1506i.f4560h);
        f4547h.put("scaleX", C1506i.f4561i);
        f4547h.put("scaleY", C1506i.f4562j);
        f4547h.put("scrollX", C1506i.f4563k);
        f4547h.put("scrollY", C1506i.f4564l);
        f4547h.put("x", C1506i.f4565m);
        f4547h.put("y", C1506i.f4566n);
    }

    private C1493h(Object obj, String str) {
        this.f4548i = obj;
        m7418a(str);
    }

    /* renamed from: a */
    public static C1493h m7412a(Object obj, String str, float... fArr) {
        C1493h c1493h = new C1493h(obj, str);
        c1493h.mo1194a(fArr);
        return c1493h;
    }

    /* renamed from: a */
    public static C1493h m7413a(Object obj, String str, int... iArr) {
        C1493h c1493h = new C1493h(obj, str);
        c1493h.mo1195a(iArr);
        return c1493h;
    }

    /* renamed from: a */
    public C1493h mo1192a(long j) {
        super.mo1196b(j);
        return this;
    }

    /* renamed from: a */
    public void mo1188a() {
        super.mo1188a();
    }

    /* renamed from: a */
    void mo1193a(float f) {
        super.mo1193a(f);
        for (C1508j b : this.f) {
            b.mo1209b(this.f4548i);
        }
    }

    /* renamed from: a */
    public void m7417a(C1494c c1494c) {
        if (this.f != null) {
            C1508j c1508j = this.f[0];
            String c = c1508j.m7509c();
            c1508j.m7501a(c1494c);
            this.g.remove(c);
            this.g.put(this.f4549j, c1508j);
        }
        if (this.f4550k != null) {
            this.f4549j = c1494c.m7427a();
        }
        this.f4550k = c1494c;
        this.e = false;
    }

    /* renamed from: a */
    public void m7418a(String str) {
        if (this.f != null) {
            C1508j c1508j = this.f[0];
            String c = c1508j.m7509c();
            c1508j.m7504a(str);
            this.g.remove(c);
            this.g.put(str, c1508j);
        }
        this.f4549j = str;
        this.e = false;
    }

    /* renamed from: a */
    public void mo1194a(float... fArr) {
        if (this.f != null && this.f.length != 0) {
            super.mo1194a(fArr);
        } else if (this.f4550k != null) {
            m7400a(C1508j.m7491a(this.f4550k, fArr));
        } else {
            m7400a(C1508j.m7493a(this.f4549j, fArr));
        }
    }

    /* renamed from: a */
    public void mo1195a(int... iArr) {
        if (this.f != null && this.f.length != 0) {
            super.mo1195a(iArr);
        } else if (this.f4550k != null) {
            m7400a(C1508j.m7492a(this.f4550k, iArr));
        } else {
            m7400a(C1508j.m7494a(this.f4549j, iArr));
        }
    }

    /* renamed from: b */
    public /* synthetic */ C1492l mo1196b(long j) {
        return mo1192a(j);
    }

    /* renamed from: c */
    public /* synthetic */ C1482a mo1190c() {
        return mo1198e();
    }

    public /* synthetic */ Object clone() {
        return mo1198e();
    }

    /* renamed from: d */
    void mo1197d() {
        if (!this.e) {
            if (this.f4550k == null && C1518a.f4590a && (this.f4548i instanceof View) && f4547h.containsKey(this.f4549j)) {
                m7417a((C1494c) f4547h.get(this.f4549j));
            }
            for (C1508j a : this.f) {
                a.m7503a(this.f4548i);
            }
            super.mo1197d();
        }
    }

    /* renamed from: e */
    public C1493h mo1198e() {
        return (C1493h) super.mo1199f();
    }

    /* renamed from: f */
    public /* synthetic */ C1492l mo1199f() {
        return mo1198e();
    }

    public String toString() {
        String str = "ObjectAnimator@" + Integer.toHexString(hashCode()) + ", target " + this.f4548i;
        if (this.f != null) {
            for (C1508j c1508j : this.f) {
                str = str + "\n    " + c1508j.toString();
            }
        }
        return str;
    }
}
