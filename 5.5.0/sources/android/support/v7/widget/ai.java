package android.support.v7.widget;

import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build.VERSION;
import android.support.v4.p007b.p008a.C0382h;
import android.support.v7.p015d.p016a.C0169a;

public class ai {
    /* renamed from: a */
    public static final Rect f2837a = new Rect();
    /* renamed from: b */
    private static Class<?> f2838b;

    static {
        if (VERSION.SDK_INT >= 18) {
            try {
                f2838b = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException e) {
            }
        }
    }

    /* renamed from: a */
    static Mode m5430a(int i, Mode mode) {
        switch (i) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return VERSION.SDK_INT >= 11 ? Mode.valueOf("ADD") : mode;
            default:
                return mode;
        }
    }

    /* renamed from: a */
    static void m5431a(Drawable drawable) {
        if (VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName())) {
            m5433c(drawable);
        }
    }

    /* renamed from: b */
    public static boolean m5432b(Drawable drawable) {
        if (VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (drawable instanceof DrawableContainer) {
            ConstantState constantState = drawable.getConstantState();
            if (constantState instanceof DrawableContainerState) {
                for (Drawable b : ((DrawableContainerState) constantState).getChildren()) {
                    if (!m5432b(b)) {
                        return false;
                    }
                }
            }
        } else if (drawable instanceof C0382h) {
            return m5432b(((C0382h) drawable).mo307a());
        } else {
            if (drawable instanceof C0169a) {
                return m5432b(((C0169a) drawable).m811b());
            }
            if (drawable instanceof ScaleDrawable) {
                return m5432b(((ScaleDrawable) drawable).getDrawable());
            }
        }
        return true;
    }

    /* renamed from: c */
    private static void m5433c(Drawable drawable) {
        int[] state = drawable.getState();
        if (state == null || state.length == 0) {
            drawable.setState(bf.f3029e);
        } else {
            drawable.setState(bf.f3032h);
        }
        drawable.setState(state);
    }
}
