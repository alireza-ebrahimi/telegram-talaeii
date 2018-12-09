package com.p077f.p078a.p086b;

import android.graphics.Bitmap;
import android.os.Handler;
import com.p077f.p078a.p086b.p087a.C1554f;
import com.p077f.p078a.p095c.C1602c;

/* renamed from: com.f.a.b.i */
final class C1598i implements Runnable {
    /* renamed from: a */
    private final C1589f f4904a;
    /* renamed from: b */
    private final Bitmap f4905b;
    /* renamed from: c */
    private final C1591g f4906c;
    /* renamed from: d */
    private final Handler f4907d;

    public C1598i(C1589f c1589f, Bitmap bitmap, C1591g c1591g, Handler handler) {
        this.f4904a = c1589f;
        this.f4905b = bitmap;
        this.f4906c = c1591g;
        this.f4907d = handler;
    }

    public void run() {
        C1602c.m7936a("PostProcess image before displaying [%s]", this.f4906c.f4872b);
        C1597h.m7905a(new C1564b(this.f4906c.f4875e.m7784p().m7900a(this.f4905b), this.f4906c, this.f4904a, C1554f.MEMORY_CACHE), this.f4906c.f4875e.m7787s(), this.f4907d, this.f4904a);
    }
}
