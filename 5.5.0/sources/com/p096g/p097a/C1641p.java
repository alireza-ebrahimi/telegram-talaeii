package com.p096g.p097a;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.p096g.p097a.C1636m.C1632b;
import com.p096g.p097a.C1640o.C1639a;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.g.a.p */
public class C1641p {
    /* renamed from: a */
    private static final AtomicInteger f5043a = new AtomicInteger();
    /* renamed from: b */
    private final C1636m f5044b;
    /* renamed from: c */
    private final C1639a f5045c;
    /* renamed from: d */
    private boolean f5046d;
    /* renamed from: e */
    private boolean f5047e;
    /* renamed from: f */
    private boolean f5048f;
    /* renamed from: g */
    private int f5049g;
    /* renamed from: h */
    private int f5050h;
    /* renamed from: i */
    private int f5051i;
    /* renamed from: j */
    private int f5052j;
    /* renamed from: k */
    private Drawable f5053k;
    /* renamed from: l */
    private Drawable f5054l;
    /* renamed from: m */
    private Object f5055m;

    /* renamed from: a */
    private C1640o m8035a(long j) {
        int andIncrement = f5043a.getAndIncrement();
        C1640o c = this.f5045c.m8027c();
        c.f5025a = andIncrement;
        c.f5026b = j;
        boolean z = this.f5044b.f4998k;
        if (z) {
            C1648v.m8064a("Main", "created", c.m8029b(), c.toString());
        }
        C1640o a = this.f5044b.m8013a(c);
        if (a != c) {
            a.f5025a = andIncrement;
            a.f5026b = j;
            if (z) {
                C1648v.m8064a("Main", "changed", a.m8028a(), "into " + a);
            }
        }
        return a;
    }

    /* renamed from: b */
    private Drawable m8036b() {
        return this.f5049g != 0 ? this.f5044b.f4990c.getResources().getDrawable(this.f5049g) : this.f5053k;
    }

    /* renamed from: a */
    C1641p m8037a() {
        this.f5047e = false;
        return this;
    }

    /* renamed from: a */
    public C1641p m8038a(int i, int i2) {
        this.f5045c.m8024a(i, i2);
        return this;
    }

    /* renamed from: a */
    public void m8039a(ImageView imageView, C1618d c1618d) {
        long nanoTime = System.nanoTime();
        C1648v.m8061a();
        if (imageView == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (this.f5045c.m8025a()) {
            if (this.f5047e) {
                if (this.f5045c.m8026b()) {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                if (width == 0 || height == 0) {
                    if (this.f5048f) {
                        C1637n.m8023a(imageView, m8036b());
                    }
                    this.f5044b.m8015a(imageView, new C1619e(this, imageView, c1618d));
                    return;
                }
                this.f5045c.m8024a(width, height);
            }
            C1640o a = m8035a(nanoTime);
            String a2 = C1648v.m8059a(a);
            if (C1626j.m8004a(this.f5051i)) {
                Bitmap a3 = this.f5044b.m8012a(a2);
                if (a3 != null) {
                    this.f5044b.m8014a(imageView);
                    C1637n.m8022a(imageView, this.f5044b.f4990c, a3, C1632b.MEMORY, this.f5046d, this.f5044b.f4997j);
                    if (this.f5044b.f4998k) {
                        C1648v.m8064a("Main", "completed", a.m8029b(), "from " + C1632b.MEMORY);
                    }
                    if (c1618d != null) {
                        c1618d.m7985a();
                        return;
                    }
                    return;
                }
            }
            if (this.f5048f) {
                C1637n.m8023a(imageView, m8036b());
            }
            this.f5044b.m8016a(new C1624h(this.f5044b, imageView, a, this.f5051i, this.f5052j, this.f5050h, this.f5054l, a2, this.f5055m, c1618d, this.f5046d));
        } else {
            this.f5044b.m8014a(imageView);
            if (this.f5048f) {
                C1637n.m8023a(imageView, m8036b());
            }
        }
    }
}
