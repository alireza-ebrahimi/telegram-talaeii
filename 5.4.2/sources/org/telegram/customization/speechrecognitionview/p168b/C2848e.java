package org.telegram.customization.speechrecognitionview.p168b;

import android.graphics.Point;
import android.view.animation.AccelerateDecelerateInterpolator;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.speechrecognitionview.C2843a;

/* renamed from: org.telegram.customization.speechrecognitionview.b.e */
public class C2848e implements C2844a {
    /* renamed from: a */
    private long f9361a;
    /* renamed from: b */
    private boolean f9362b;
    /* renamed from: c */
    private final int f9363c;
    /* renamed from: d */
    private final int f9364d;
    /* renamed from: e */
    private final List<Point> f9365e = new ArrayList();
    /* renamed from: f */
    private final List<C2843a> f9366f;

    public C2848e(List<C2843a> list, int i, int i2) {
        this.f9363c = i;
        this.f9364d = i2;
        this.f9366f = list;
        for (C2843a c2843a : list) {
            this.f9365e.add(new Point(c2843a.m13218b(), c2843a.m13220c()));
        }
    }

    /* renamed from: a */
    private float m13249a(long j, int i) {
        return ((-new AccelerateDecelerateInterpolator().getInterpolation(((float) (j - 1000)) / 1000.0f)) * (((float) i) * 40.0f)) + (((float) i) * 40.0f);
    }

    /* renamed from: a */
    private void m13250a(C2843a c2843a, double d, Point point) {
        double toRadians = Math.toRadians(d);
        int cos = this.f9363c + ((int) ((((double) (point.x - this.f9363c)) * Math.cos(toRadians)) - (((double) (point.y - this.f9364d)) * Math.sin(toRadians))));
        int cos2 = ((int) ((Math.cos(toRadians) * ((double) (point.y - this.f9364d))) + (((double) (point.x - this.f9363c)) * Math.sin(toRadians)))) + this.f9364d;
        c2843a.m13217a(cos);
        c2843a.m13219b(cos2);
        c2843a.m13216a();
    }

    /* renamed from: b */
    private float m13251b(long j, int i) {
        return new AccelerateDecelerateInterpolator().getInterpolation(((float) j) / 1000.0f) * (40.0f * ((float) i));
    }

    /* renamed from: a */
    public void mo3493a() {
        this.f9362b = true;
        this.f9361a = System.currentTimeMillis();
    }

    /* renamed from: b */
    public void mo3494b() {
        this.f9362b = false;
    }

    /* renamed from: c */
    public void mo3495c() {
        if (this.f9362b) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.f9361a > 2000) {
                this.f9361a += 2000;
            }
            long j = currentTimeMillis - this.f9361a;
            float f = (((float) j) / 2000.0f) * 720.0f;
            int i = 0;
            for (C2843a c2843a : this.f9366f) {
                float b = (i <= 0 || j <= 1000) ? i > 0 ? m13251b(j, this.f9366f.size() - i) + f : f : m13249a(j, this.f9366f.size() - i) + f;
                m13250a(c2843a, (double) b, (Point) this.f9365e.get(i));
                i++;
            }
        }
    }
}
