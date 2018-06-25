package org.telegram.customization.speechrecognitionview.p168b;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.Random;
import org.telegram.customization.speechrecognitionview.C2843a;

/* renamed from: org.telegram.customization.speechrecognitionview.b.b */
public class C2845b implements C2844a {
    /* renamed from: a */
    private final C2843a f9350a;
    /* renamed from: b */
    private float f9351b;
    /* renamed from: c */
    private float f9352c;
    /* renamed from: d */
    private long f9353d;
    /* renamed from: e */
    private boolean f9354e;
    /* renamed from: f */
    private boolean f9355f;

    public C2845b(C2843a c2843a) {
        this.f9350a = c2843a;
    }

    /* renamed from: a */
    private void m13231a(long j) {
        int e = (int) (this.f9351b * ((float) this.f9350a.m13223e()));
        int e2 = (int) (((float) this.f9350a.m13223e()) * this.f9352c);
        e += (int) (new AccelerateInterpolator().getInterpolation(((float) j) / 130.0f) * ((float) (e2 - e)));
        if (e >= this.f9350a.m13222d()) {
            boolean z;
            if (e >= e2) {
                z = true;
            } else {
                e2 = e;
                z = false;
            }
            this.f9350a.m13221c(e2);
            this.f9350a.m13216a();
            if (z) {
                this.f9355f = false;
                this.f9353d = System.currentTimeMillis();
            }
        }
    }

    /* renamed from: b */
    private void m13232b(long j) {
        int i = this.f9350a.m13227i() * 2;
        float e = (float) (((int) (((float) this.f9350a.m13223e()) * this.f9352c)) - i);
        int interpolation = ((int) (e * (1.0f - new DecelerateInterpolator().getInterpolation(((float) j) / 500.0f)))) + i;
        if (interpolation <= this.f9350a.m13222d()) {
            if (interpolation <= i) {
                m13235e();
                return;
            }
            this.f9350a.m13221c(interpolation);
            this.f9350a.m13216a();
        }
    }

    /* renamed from: b */
    private boolean m13233b(float f) {
        return ((float) this.f9350a.m13222d()) / ((float) this.f9350a.m13223e()) > f;
    }

    /* renamed from: d */
    private void m13234d() {
        long currentTimeMillis = System.currentTimeMillis() - this.f9353d;
        if (this.f9355f) {
            m13231a(currentTimeMillis);
        } else {
            m13232b(currentTimeMillis);
        }
    }

    /* renamed from: e */
    private void m13235e() {
        this.f9350a.m13221c(this.f9350a.m13227i() * 2);
        this.f9350a.m13216a();
        this.f9354e = false;
    }

    /* renamed from: a */
    public void mo3493a() {
        this.f9354e = true;
    }

    /* renamed from: a */
    public void m13237a(float f) {
        float f2 = 0.6f;
        if (f < 2.0f) {
            f2 = 0.2f;
        } else if (f < 2.0f || f > 5.5f) {
            f2 = 0.7f + new Random().nextFloat();
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
        } else {
            float nextFloat = 0.3f + new Random().nextFloat();
            if (nextFloat <= 0.6f) {
                f2 = nextFloat;
            }
        }
        if (!m13233b(f2)) {
            this.f9351b = ((float) this.f9350a.m13222d()) / ((float) this.f9350a.m13223e());
            this.f9352c = f2;
            this.f9353d = System.currentTimeMillis();
            this.f9355f = true;
            this.f9354e = true;
        }
    }

    /* renamed from: b */
    public void mo3494b() {
        this.f9354e = false;
    }

    /* renamed from: c */
    public void mo3495c() {
        if (this.f9354e) {
            m13234d();
        }
    }
}
