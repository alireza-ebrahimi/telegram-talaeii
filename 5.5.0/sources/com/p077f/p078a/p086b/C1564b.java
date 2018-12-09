package com.p077f.p078a.p086b;

import android.graphics.Bitmap;
import com.p077f.p078a.p086b.p087a.C1554f;
import com.p077f.p078a.p086b.p090c.C1567a;
import com.p077f.p078a.p086b.p092e.C1580a;
import com.p077f.p078a.p086b.p093f.C1586a;
import com.p077f.p078a.p095c.C1602c;

/* renamed from: com.f.a.b.b */
final class C1564b implements Runnable {
    /* renamed from: a */
    private final Bitmap f4737a;
    /* renamed from: b */
    private final String f4738b;
    /* renamed from: c */
    private final C1580a f4739c;
    /* renamed from: d */
    private final String f4740d;
    /* renamed from: e */
    private final C1567a f4741e;
    /* renamed from: f */
    private final C1586a f4742f;
    /* renamed from: g */
    private final C1589f f4743g;
    /* renamed from: h */
    private final C1554f f4744h;

    public C1564b(Bitmap bitmap, C1591g c1591g, C1589f c1589f, C1554f c1554f) {
        this.f4737a = bitmap;
        this.f4738b = c1591g.f4871a;
        this.f4739c = c1591g.f4873c;
        this.f4740d = c1591g.f4872b;
        this.f4741e = c1591g.f4875e.m7785q();
        this.f4742f = c1591g.f4876f;
        this.f4743g = c1589f;
        this.f4744h = c1554f;
    }

    /* renamed from: a */
    private boolean m7712a() {
        return !this.f4740d.equals(this.f4743g.m7889a(this.f4739c));
    }

    public void run() {
        if (this.f4739c.mo1234e()) {
            C1602c.m7936a("ImageAware was collected by GC. Task is cancelled. [%s]", this.f4740d);
            this.f4742f.onLoadingCancelled(this.f4738b, this.f4739c.mo1233d());
        } else if (m7712a()) {
            C1602c.m7936a("ImageAware is reused for another image. Task is cancelled. [%s]", this.f4740d);
            this.f4742f.onLoadingCancelled(this.f4738b, this.f4739c.mo1233d());
        } else {
            C1602c.m7936a("Display image in ImageAware (loaded from %1$s) [%2$s]", this.f4744h, this.f4740d);
            this.f4741e.mo1226a(this.f4737a, this.f4739c, this.f4744h);
            this.f4743g.m7897b(this.f4739c);
            this.f4742f.onLoadingComplete(this.f4738b, this.f4739c.mo1233d(), this.f4737a);
        }
    }
}
