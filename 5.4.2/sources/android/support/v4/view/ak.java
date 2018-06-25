package android.support.v4.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewParent;

@TargetApi(11)
class ak {
    /* renamed from: a */
    public static float m2849a(View view) {
        return view.getAlpha();
    }

    /* renamed from: a */
    public static int m2850a(int i, int i2) {
        return View.combineMeasuredStates(i, i2);
    }

    /* renamed from: a */
    public static int m2851a(int i, int i2, int i3) {
        return View.resolveSizeAndState(i, i2, i3);
    }

    /* renamed from: a */
    static long m2852a() {
        return ValueAnimator.getFrameDelay();
    }

    /* renamed from: a */
    public static void m2853a(View view, float f) {
        view.setTranslationX(f);
    }

    /* renamed from: a */
    static void m2854a(View view, int i) {
        view.offsetTopAndBottom(i);
        if (view.getVisibility() == 0) {
            m2878l(view);
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                m2878l((View) parent);
            }
        }
    }

    /* renamed from: a */
    public static void m2855a(View view, int i, Paint paint) {
        view.setLayerType(i, paint);
    }

    /* renamed from: a */
    public static void m2856a(View view, boolean z) {
        view.setSaveFromParentEnabled(z);
    }

    /* renamed from: b */
    public static int m2857b(View view) {
        return view.getLayerType();
    }

    /* renamed from: b */
    public static void m2858b(View view, float f) {
        view.setTranslationY(f);
    }

    /* renamed from: b */
    static void m2859b(View view, int i) {
        view.offsetLeftAndRight(i);
        if (view.getVisibility() == 0) {
            m2878l(view);
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                m2878l((View) parent);
            }
        }
    }

    /* renamed from: b */
    public static void m2860b(View view, boolean z) {
        view.setActivated(z);
    }

    /* renamed from: c */
    public static int m2861c(View view) {
        return view.getMeasuredWidthAndState();
    }

    /* renamed from: c */
    public static void m2862c(View view, float f) {
        view.setAlpha(f);
    }

    /* renamed from: d */
    public static int m2863d(View view) {
        return view.getMeasuredState();
    }

    /* renamed from: d */
    public static void m2864d(View view, float f) {
        view.setRotation(f);
    }

    /* renamed from: e */
    public static float m2865e(View view) {
        return view.getTranslationX();
    }

    /* renamed from: e */
    public static void m2866e(View view, float f) {
        view.setRotationX(f);
    }

    /* renamed from: f */
    public static float m2867f(View view) {
        return view.getTranslationY();
    }

    /* renamed from: f */
    public static void m2868f(View view, float f) {
        view.setRotationY(f);
    }

    /* renamed from: g */
    public static float m2869g(View view) {
        return view.getX();
    }

    /* renamed from: g */
    public static void m2870g(View view, float f) {
        view.setScaleX(f);
    }

    /* renamed from: h */
    public static float m2871h(View view) {
        return view.getY();
    }

    /* renamed from: h */
    public static void m2872h(View view, float f) {
        view.setScaleY(f);
    }

    /* renamed from: i */
    public static float m2873i(View view) {
        return view.getScaleX();
    }

    /* renamed from: i */
    public static void m2874i(View view, float f) {
        view.setPivotX(f);
    }

    /* renamed from: j */
    public static Matrix m2875j(View view) {
        return view.getMatrix();
    }

    /* renamed from: j */
    public static void m2876j(View view, float f) {
        view.setPivotY(f);
    }

    /* renamed from: k */
    public static void m2877k(View view) {
        view.jumpDrawablesToCurrentState();
    }

    /* renamed from: l */
    private static void m2878l(View view) {
        float translationY = view.getTranslationY();
        view.setTranslationY(1.0f + translationY);
        view.setTranslationY(translationY);
    }
}
