package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.support.design.widget.C0201w.C0200e;
import android.support.design.widget.C0201w.C0200e.C0196b;
import android.support.design.widget.C0201w.C0200e.C0198a;
import android.view.animation.Interpolator;

@TargetApi(12)
/* renamed from: android.support.design.widget.y */
class C0206y extends C0200e {
    /* renamed from: a */
    private final ValueAnimator f699a = new ValueAnimator();

    C0206y() {
    }

    /* renamed from: a */
    public void mo175a() {
        this.f699a.start();
    }

    /* renamed from: a */
    public void mo176a(float f, float f2) {
        this.f699a.setFloatValues(new float[]{f, f2});
    }

    /* renamed from: a */
    public void mo177a(int i, int i2) {
        this.f699a.setIntValues(new int[]{i, i2});
    }

    /* renamed from: a */
    public void mo178a(long j) {
        this.f699a.setDuration(j);
    }

    /* renamed from: a */
    public void mo179a(final C0198a c0198a) {
        this.f699a.addListener(new AnimatorListenerAdapter(this) {
            /* renamed from: b */
            final /* synthetic */ C0206y f698b;

            public void onAnimationCancel(Animator animator) {
                c0198a.mo174c();
            }

            public void onAnimationEnd(Animator animator) {
                c0198a.mo173b();
            }

            public void onAnimationStart(Animator animator) {
                c0198a.mo172a();
            }
        });
    }

    /* renamed from: a */
    public void mo180a(final C0196b c0196b) {
        this.f699a.addUpdateListener(new AnimatorUpdateListener(this) {
            /* renamed from: b */
            final /* synthetic */ C0206y f696b;

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                c0196b.mo171a();
            }
        });
    }

    /* renamed from: a */
    public void mo181a(Interpolator interpolator) {
        this.f699a.setInterpolator(interpolator);
    }

    /* renamed from: b */
    public boolean mo182b() {
        return this.f699a.isRunning();
    }

    /* renamed from: c */
    public int mo183c() {
        return ((Integer) this.f699a.getAnimatedValue()).intValue();
    }

    /* renamed from: d */
    public float mo184d() {
        return ((Float) this.f699a.getAnimatedValue()).floatValue();
    }

    /* renamed from: e */
    public void mo185e() {
        this.f699a.cancel();
    }

    /* renamed from: f */
    public float mo186f() {
        return this.f699a.getAnimatedFraction();
    }

    /* renamed from: g */
    public void mo187g() {
        this.f699a.end();
    }

    /* renamed from: h */
    public long mo188h() {
        return this.f699a.getDuration();
    }
}
