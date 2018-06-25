package android.support.design.widget;

import android.support.v4.view.p024b.C0603a;
import android.support.v4.view.p024b.C0604b;
import android.support.v4.view.p024b.C0605c;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/* renamed from: android.support.design.widget.a */
class C0126a {
    /* renamed from: a */
    static final Interpolator f426a = new LinearInterpolator();
    /* renamed from: b */
    static final Interpolator f427b = new C0604b();
    /* renamed from: c */
    static final Interpolator f428c = new C0603a();
    /* renamed from: d */
    static final Interpolator f429d = new C0605c();
    /* renamed from: e */
    static final Interpolator f430e = new DecelerateInterpolator();

    /* renamed from: android.support.design.widget.a$a */
    static class C0125a implements AnimationListener {
        C0125a() {
        }

        public void onAnimationEnd(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: a */
    static float m647a(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    /* renamed from: a */
    static int m648a(int i, int i2, float f) {
        return Math.round(((float) (i2 - i)) * f) + i;
    }
}
