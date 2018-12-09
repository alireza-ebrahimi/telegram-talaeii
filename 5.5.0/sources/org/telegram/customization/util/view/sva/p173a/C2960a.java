package org.telegram.customization.util.view.sva.p173a;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;

/* renamed from: org.telegram.customization.util.view.sva.a.a */
public abstract class C2960a {
    /* renamed from: a */
    protected float f9821a = -1.0f;
    /* renamed from: b */
    protected float[] f9822b = new float[2];
    /* renamed from: c */
    protected int f9823c;
    /* renamed from: d */
    protected int f9824d;
    /* renamed from: e */
    protected float f9825e;
    /* renamed from: f */
    protected float f9826f;
    /* renamed from: g */
    protected int f9827g = 0;
    /* renamed from: h */
    private WeakReference<View> f9828h;

    /* renamed from: org.telegram.customization.util.view.sva.a.a$2 */
    class C29592 extends AnimatorListenerAdapter {
        /* renamed from: a */
        final /* synthetic */ C2960a f9820a;

        C29592(C2960a c2960a) {
            this.f9820a = c2960a;
        }

        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
        }
    }

    /* renamed from: a */
    public ValueAnimator m13650a(float f, float f2, long j) {
        return m13651a(f, f2, j, null);
    }

    /* renamed from: a */
    public ValueAnimator m13651a(float f, float f2, long j, final PathMeasure pathMeasure) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f, f2});
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addUpdateListener(new AnimatorUpdateListener(this) {
            /* renamed from: b */
            final /* synthetic */ C2960a f9819b;

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f9819b.f9821a = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (pathMeasure != null) {
                    pathMeasure.getPosTan(this.f9819b.f9821a, this.f9819b.f9822b, null);
                }
                this.f9819b.m13652a().invalidate();
            }
        });
        ofFloat.addListener(new C29592(this));
        if (!ofFloat.isRunning()) {
            ofFloat.start();
        }
        this.f9821a = BitmapDescriptorFactory.HUE_RED;
        return ofFloat;
    }

    /* renamed from: a */
    public View m13652a() {
        return this.f9828h != null ? (View) this.f9828h.get() : null;
    }

    /* renamed from: a */
    public void m13653a(float f) {
        this.f9825e = f;
    }

    /* renamed from: a */
    public void m13654a(int i) {
        this.f9823c = i;
    }

    /* renamed from: a */
    public abstract void mo3517a(Canvas canvas, Paint paint);

    /* renamed from: a */
    public void m13656a(View view) {
        this.f9828h = new WeakReference(view);
    }

    /* renamed from: b */
    public int m13657b() {
        return m13652a() != null ? m13652a().getWidth() : 0;
    }

    /* renamed from: b */
    public void m13658b(float f) {
        this.f9826f = f;
    }

    /* renamed from: b */
    public void m13659b(int i) {
        this.f9824d = i;
    }

    /* renamed from: c */
    public int m13660c() {
        return m13652a() != null ? m13652a().getHeight() : 0;
    }

    /* renamed from: d */
    public void mo3518d() {
    }

    /* renamed from: e */
    public void mo3519e() {
    }

    /* renamed from: f */
    public ValueAnimator m13663f() {
        return m13650a(BitmapDescriptorFactory.HUE_RED, 1.0f, 500);
    }
}
