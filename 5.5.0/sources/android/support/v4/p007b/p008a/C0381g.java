package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

@TargetApi(21)
/* renamed from: android.support.v4.b.a.g */
class C0381g {
    /* renamed from: a */
    public static Drawable m1798a(Drawable drawable) {
        return !(drawable instanceof C0006m) ? new C0391l(drawable) : drawable;
    }

    /* renamed from: a */
    public static void m1799a(Drawable drawable, float f, float f2) {
        drawable.setHotspot(f, f2);
    }

    /* renamed from: a */
    public static void m1800a(Drawable drawable, int i) {
        drawable.setTint(i);
    }

    /* renamed from: a */
    public static void m1801a(Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setHotspotBounds(i, i2, i3, i4);
    }

    /* renamed from: a */
    public static void m1802a(Drawable drawable, ColorStateList colorStateList) {
        drawable.setTintList(colorStateList);
    }

    /* renamed from: a */
    public static void m1803a(Drawable drawable, Theme theme) {
        drawable.applyTheme(theme);
    }

    /* renamed from: a */
    public static void m1804a(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        drawable.inflate(resources, xmlPullParser, attributeSet, theme);
    }

    /* renamed from: a */
    public static void m1805a(Drawable drawable, Mode mode) {
        drawable.setTintMode(mode);
    }

    /* renamed from: b */
    public static boolean m1806b(Drawable drawable) {
        return drawable.canApplyTheme();
    }

    /* renamed from: c */
    public static ColorFilter m1807c(Drawable drawable) {
        return drawable.getColorFilter();
    }

    /* renamed from: d */
    public static void m1808d(Drawable drawable) {
        drawable.clearColorFilter();
        if (drawable instanceof InsetDrawable) {
            C0381g.m1808d(((InsetDrawable) drawable).getDrawable());
        } else if (drawable instanceof C0382h) {
            C0381g.m1808d(((C0382h) drawable).mo307a());
        } else if (drawable instanceof DrawableContainer) {
            DrawableContainerState drawableContainerState = (DrawableContainerState) ((DrawableContainer) drawable).getConstantState();
            if (drawableContainerState != null) {
                int childCount = drawableContainerState.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    Drawable child = drawableContainerState.getChild(i);
                    if (child != null) {
                        C0381g.m1808d(child);
                    }
                }
            }
        }
    }
}
