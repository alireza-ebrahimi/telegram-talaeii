package android.support.v4.view;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

@TargetApi(16)
class an {
    /* renamed from: a */
    public static void m2886a(View view, int i) {
        view.setImportantForAccessibility(i);
    }

    /* renamed from: a */
    public static void m2887a(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    /* renamed from: a */
    public static void m2888a(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    /* renamed from: a */
    public static void m2889a(View view, Runnable runnable, long j) {
        view.postOnAnimationDelayed(runnable, j);
    }

    /* renamed from: a */
    public static boolean m2890a(View view) {
        return view.hasTransientState();
    }

    /* renamed from: a */
    public static boolean m2891a(View view, int i, Bundle bundle) {
        return view.performAccessibilityAction(i, bundle);
    }

    /* renamed from: b */
    public static void m2892b(View view) {
        view.postInvalidateOnAnimation();
    }

    /* renamed from: c */
    public static int m2893c(View view) {
        return view.getImportantForAccessibility();
    }

    /* renamed from: d */
    public static int m2894d(View view) {
        return view.getMinimumWidth();
    }

    /* renamed from: e */
    public static int m2895e(View view) {
        return view.getMinimumHeight();
    }

    /* renamed from: f */
    public static void m2896f(View view) {
        view.requestFitSystemWindows();
    }

    /* renamed from: g */
    public static boolean m2897g(View view) {
        return view.getFitsSystemWindows();
    }

    /* renamed from: h */
    public static boolean m2898h(View view) {
        return view.hasOverlappingRendering();
    }
}
