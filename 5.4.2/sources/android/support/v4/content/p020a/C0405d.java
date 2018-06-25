package android.support.v4.content.p020a;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.content.a.d */
public final class C0405d {
    /* renamed from: a */
    public static Drawable m1869a(Resources resources, int i, Theme theme) {
        return VERSION.SDK_INT >= 21 ? C0406e.m1870a(resources, i, theme) : resources.getDrawable(i);
    }
}
