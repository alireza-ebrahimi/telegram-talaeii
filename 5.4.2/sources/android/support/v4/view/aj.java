package android.support.v4.view;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.view.Display;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import java.lang.reflect.Field;

@TargetApi(9)
class aj {
    /* renamed from: a */
    private static Field f1324a;
    /* renamed from: b */
    private static boolean f1325b;
    /* renamed from: c */
    private static Field f1326c;
    /* renamed from: d */
    private static boolean f1327d;

    /* renamed from: a */
    static ColorStateList m2838a(View view) {
        return view instanceof ae ? ((ae) view).getSupportBackgroundTintList() : null;
    }

    /* renamed from: a */
    static void m2839a(View view, int i) {
        int top = view.getTop();
        view.offsetTopAndBottom(i);
        if (i != 0) {
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                int abs = Math.abs(i);
                ((View) parent).invalidate(view.getLeft(), top - abs, view.getRight(), (top + view.getHeight()) + abs);
                return;
            }
            view.invalidate();
        }
    }

    /* renamed from: a */
    static void m2840a(View view, ColorStateList colorStateList) {
        if (view instanceof ae) {
            ((ae) view).setSupportBackgroundTintList(colorStateList);
        }
    }

    /* renamed from: a */
    static void m2841a(View view, Mode mode) {
        if (view instanceof ae) {
            ((ae) view).setSupportBackgroundTintMode(mode);
        }
    }

    /* renamed from: b */
    static Mode m2842b(View view) {
        return view instanceof ae ? ((ae) view).getSupportBackgroundTintMode() : null;
    }

    /* renamed from: b */
    static void m2843b(View view, int i) {
        int left = view.getLeft();
        view.offsetLeftAndRight(i);
        if (i != 0) {
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                int abs = Math.abs(i);
                ((View) parent).invalidate(left - abs, view.getTop(), (left + view.getWidth()) + abs, view.getBottom());
                return;
            }
            view.invalidate();
        }
    }

    /* renamed from: c */
    static boolean m2844c(View view) {
        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    /* renamed from: d */
    static int m2845d(View view) {
        if (!f1325b) {
            try {
                f1324a = View.class.getDeclaredField("mMinWidth");
                f1324a.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            f1325b = true;
        }
        if (f1324a != null) {
            try {
                return ((Integer) f1324a.get(view)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    /* renamed from: e */
    static int m2846e(View view) {
        if (!f1327d) {
            try {
                f1326c = View.class.getDeclaredField("mMinHeight");
                f1326c.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            f1327d = true;
        }
        if (f1326c != null) {
            try {
                return ((Integer) f1326c.get(view)).intValue();
            } catch (Exception e2) {
            }
        }
        return 0;
    }

    /* renamed from: f */
    static boolean m2847f(View view) {
        return view.getWindowToken() != null;
    }

    /* renamed from: g */
    static Display m2848g(View view) {
        return m2847f(view) ? ((WindowManager) view.getContext().getSystemService("window")).getDefaultDisplay() : null;
    }
}
