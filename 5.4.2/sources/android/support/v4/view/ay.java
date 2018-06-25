package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.view.View;
import android.view.animation.Interpolator;

@TargetApi(14)
class ay {
    /* renamed from: a */
    public static long m3032a(View view) {
        return view.animate().getDuration();
    }

    /* renamed from: a */
    public static void m3033a(View view, float f) {
        view.animate().alpha(f);
    }

    /* renamed from: a */
    public static void m3034a(View view, long j) {
        view.animate().setDuration(j);
    }

    /* renamed from: a */
    public static void m3035a(final View view, final bb bbVar) {
        if (bbVar != null) {
            view.animate().setListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    bbVar.onAnimationCancel(view);
                }

                public void onAnimationEnd(Animator animator) {
                    bbVar.onAnimationEnd(view);
                }

                public void onAnimationStart(Animator animator) {
                    bbVar.onAnimationStart(view);
                }
            });
        } else {
            view.animate().setListener(null);
        }
    }

    /* renamed from: a */
    public static void m3036a(View view, Interpolator interpolator) {
        view.animate().setInterpolator(interpolator);
    }

    /* renamed from: b */
    public static void m3037b(View view) {
        view.animate().cancel();
    }

    /* renamed from: b */
    public static void m3038b(View view, float f) {
        view.animate().translationX(f);
    }

    /* renamed from: b */
    public static void m3039b(View view, long j) {
        view.animate().setStartDelay(j);
    }

    /* renamed from: c */
    public static void m3040c(View view) {
        view.animate().start();
    }

    /* renamed from: c */
    public static void m3041c(View view, float f) {
        view.animate().translationY(f);
    }

    /* renamed from: d */
    public static void m3042d(View view, float f) {
        view.animate().scaleX(f);
    }

    /* renamed from: e */
    public static void m3043e(View view, float f) {
        view.animate().scaleY(f);
    }
}
