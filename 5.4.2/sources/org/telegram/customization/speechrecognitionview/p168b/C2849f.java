package org.telegram.customization.speechrecognitionview.p168b;

import android.graphics.Point;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.speechrecognitionview.C2843a;

/* renamed from: org.telegram.customization.speechrecognitionview.b.f */
public class C2849f implements C2844a {
    /* renamed from: a */
    private long f9367a;
    /* renamed from: b */
    private boolean f9368b;
    /* renamed from: c */
    private C2841a f9369c;
    /* renamed from: d */
    private final int f9370d;
    /* renamed from: e */
    private final int f9371e;
    /* renamed from: f */
    private final int f9372f;
    /* renamed from: g */
    private final List<Point> f9373g = new ArrayList();
    /* renamed from: h */
    private final List<C2843a> f9374h;

    /* renamed from: org.telegram.customization.speechrecognitionview.b.f$a */
    public interface C2841a {
        /* renamed from: a */
        void mo3492a();
    }

    public C2849f(List<C2843a> list, int i, int i2, int i3) {
        this.f9371e = i;
        this.f9372f = i2;
        this.f9374h = list;
        this.f9370d = i3;
    }

    /* renamed from: a */
    private void m13255a(double d, Point point) {
        double toRadians = Math.toRadians(d);
        int cos = this.f9371e + ((int) ((((double) (point.x - this.f9371e)) * Math.cos(toRadians)) - (((double) (point.y - this.f9372f)) * Math.sin(toRadians))));
        int cos2 = ((int) ((Math.cos(toRadians) * ((double) (point.y - this.f9372f))) + (((double) (point.x - this.f9371e)) * Math.sin(toRadians)))) + this.f9372f;
        point.x = cos;
        point.y = cos2;
    }

    /* renamed from: d */
    private void m13256d() {
        Point point = new Point();
        point.x = this.f9371e;
        point.y = this.f9372f - this.f9370d;
        for (int i = 0; i < 5; i++) {
            Point point2 = new Point(point);
            m13255a(72.0d * ((double) i), point2);
            this.f9373g.add(point2);
        }
    }

    /* renamed from: a */
    public void mo3493a() {
        this.f9368b = true;
        this.f9367a = System.currentTimeMillis();
        m13256d();
    }

    /* renamed from: a */
    public void m13258a(C2841a c2841a) {
        this.f9369c = c2841a;
    }

    /* renamed from: b */
    public void mo3494b() {
        this.f9368b = false;
        if (this.f9369c != null) {
            this.f9369c.mo3492a();
        }
    }

    /* renamed from: c */
    public void mo3495c() {
        if (this.f9368b) {
            long currentTimeMillis = System.currentTimeMillis() - this.f9367a;
            long j = currentTimeMillis > 300 ? 300 : currentTimeMillis;
            for (int i = 0; i < this.f9374h.size(); i++) {
                C2843a c2843a = (C2843a) this.f9374h.get(i);
                int g = ((int) (((float) (((Point) this.f9373g.get(i)).y - c2843a.m13225g())) * (((float) j) / 300.0f))) + c2843a.m13225g();
                c2843a.m13217a(c2843a.m13224f() + ((int) (((float) (((Point) this.f9373g.get(i)).x - c2843a.m13224f())) * (((float) j) / 300.0f))));
                c2843a.m13219b(g);
                c2843a.m13216a();
            }
            if (j == 300) {
                mo3494b();
            }
        }
    }
}
