package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

@TargetApi(9)
/* renamed from: android.support.v4.b.a.c */
class C0377c {
    /* renamed from: a */
    public static Drawable m1786a(Drawable drawable) {
        return !(drawable instanceof C0006m) ? new C0385i(drawable) : drawable;
    }

    /* renamed from: a */
    public static void m1787a(Drawable drawable, int i) {
        if (drawable instanceof C0006m) {
            ((C0006m) drawable).setTint(i);
        }
    }

    /* renamed from: a */
    public static void m1788a(Drawable drawable, ColorStateList colorStateList) {
        if (drawable instanceof C0006m) {
            ((C0006m) drawable).setTintList(colorStateList);
        }
    }

    /* renamed from: a */
    public static void m1789a(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        drawable.inflate(resources, xmlPullParser, attributeSet);
    }

    /* renamed from: a */
    public static void m1790a(Drawable drawable, Mode mode) {
        if (drawable instanceof C0006m) {
            ((C0006m) drawable).setTintMode(mode);
        }
    }
}
