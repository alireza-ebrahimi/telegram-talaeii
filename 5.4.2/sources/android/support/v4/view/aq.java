package android.support.v4.view;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewParent;
import android.view.WindowInsets;

@TargetApi(21)
class aq {
    /* renamed from: a */
    private static ThreadLocal<Rect> f1329a;

    /* renamed from: android.support.v4.view.aq$a */
    public interface C0578a {
        /* renamed from: a */
        Object mo519a(View view, Object obj);
    }

    /* renamed from: a */
    private static Rect m2909a() {
        if (f1329a == null) {
            f1329a = new ThreadLocal();
        }
        Rect rect = (Rect) f1329a.get();
        if (rect == null) {
            rect = new Rect();
            f1329a.set(rect);
        }
        rect.setEmpty();
        return rect;
    }

    /* renamed from: a */
    public static Object m2910a(View view, Object obj) {
        WindowInsets windowInsets = (WindowInsets) obj;
        WindowInsets onApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        return onApplyWindowInsets != windowInsets ? new WindowInsets(onApplyWindowInsets) : obj;
    }

    /* renamed from: a */
    public static String m2911a(View view) {
        return view.getTransitionName();
    }

    /* renamed from: a */
    public static void m2912a(View view, float f) {
        view.setElevation(f);
    }

    /* renamed from: a */
    static void m2913a(View view, int i) {
        Object obj;
        Rect a = m2909a();
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            View view2 = (View) parent;
            a.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
            obj = !a.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()) ? 1 : null;
        } else {
            obj = null;
        }
        ak.m2854a(view, i);
        if (obj != null && a.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
            ((View) parent).invalidate(a);
        }
    }

    /* renamed from: a */
    static void m2914a(View view, ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
        if (VERSION.SDK_INT == 21) {
            Drawable background = view.getBackground();
            Object obj = (view.getBackgroundTintList() == null || view.getBackgroundTintMode() == null) ? null : 1;
            if (background != null && obj != null) {
                if (background.isStateful()) {
                    background.setState(view.getDrawableState());
                }
                view.setBackground(background);
            }
        }
    }

    /* renamed from: a */
    static void m2915a(View view, Mode mode) {
        view.setBackgroundTintMode(mode);
        if (VERSION.SDK_INT == 21) {
            Drawable background = view.getBackground();
            Object obj = (view.getBackgroundTintList() == null || view.getBackgroundTintMode() == null) ? null : 1;
            if (background != null && obj != null) {
                if (background.isStateful()) {
                    background.setState(view.getDrawableState());
                }
                view.setBackground(background);
            }
        }
    }

    /* renamed from: a */
    public static void m2916a(View view, final C0578a c0578a) {
        if (c0578a == null) {
            view.setOnApplyWindowInsetsListener(null);
        } else {
            view.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    return (WindowInsets) c0578a.mo519a(view, windowInsets);
                }
            });
        }
    }

    /* renamed from: b */
    public static Object m2917b(View view, Object obj) {
        WindowInsets windowInsets = (WindowInsets) obj;
        WindowInsets dispatchApplyWindowInsets = view.dispatchApplyWindowInsets(windowInsets);
        return dispatchApplyWindowInsets != windowInsets ? new WindowInsets(dispatchApplyWindowInsets) : obj;
    }

    /* renamed from: b */
    public static void m2918b(View view) {
        view.requestApplyInsets();
    }

    /* renamed from: b */
    static void m2919b(View view, int i) {
        Object obj;
        Rect a = m2909a();
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            View view2 = (View) parent;
            a.set(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
            obj = !a.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()) ? 1 : null;
        } else {
            obj = null;
        }
        ak.m2859b(view, i);
        if (obj != null && a.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
            ((View) parent).invalidate(a);
        }
    }

    /* renamed from: c */
    public static float m2920c(View view) {
        return view.getElevation();
    }

    /* renamed from: d */
    public static float m2921d(View view) {
        return view.getTranslationZ();
    }

    /* renamed from: e */
    static ColorStateList m2922e(View view) {
        return view.getBackgroundTintList();
    }

    /* renamed from: f */
    static Mode m2923f(View view) {
        return view.getBackgroundTintMode();
    }

    /* renamed from: g */
    public static boolean m2924g(View view) {
        return view.isNestedScrollingEnabled();
    }

    /* renamed from: h */
    public static void m2925h(View view) {
        view.stopNestedScroll();
    }

    /* renamed from: i */
    public static float m2926i(View view) {
        return view.getZ();
    }
}
