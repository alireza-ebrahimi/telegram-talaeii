package org.telegram.customization.speechrecognitionview.p168b;

import java.util.List;
import org.telegram.customization.speechrecognitionview.C2843a;

/* renamed from: org.telegram.customization.speechrecognitionview.b.c */
public class C2846c implements C2844a {
    /* renamed from: a */
    private long f9356a;
    /* renamed from: b */
    private boolean f9357b;
    /* renamed from: c */
    private final int f9358c;
    /* renamed from: d */
    private final List<C2843a> f9359d;

    public C2846c(List<C2843a> list, int i) {
        this.f9358c = i;
        this.f9359d = list;
    }

    /* renamed from: a */
    private void m13240a(C2843a c2843a, long j, int i) {
        c2843a.m13219b(((int) (Math.sin(Math.toRadians((double) (((((float) j) / 1500.0f) * 360.0f) + (120.0f * ((float) i))))) * ((double) this.f9358c))) + c2843a.m13225g());
        c2843a.m13216a();
    }

    /* renamed from: a */
    public void mo3493a() {
        this.f9357b = true;
        this.f9356a = System.currentTimeMillis();
    }

    /* renamed from: a */
    public void m13242a(List<C2843a> list) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f9356a > 1500) {
            this.f9356a += 1500;
        }
        long j = currentTimeMillis - this.f9356a;
        int i = 0;
        for (C2843a a : list) {
            m13240a(a, j, i);
            i++;
        }
    }

    /* renamed from: b */
    public void mo3494b() {
        this.f9357b = false;
    }

    /* renamed from: c */
    public void mo3495c() {
        if (this.f9357b) {
            m13242a(this.f9359d);
        }
    }
}
