package com.persianswitch.sdk.base.utils;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;

public class DrawableUtils {
    /* renamed from: a */
    public static void m10760a(View view, Drawable drawable, boolean z) {
        if (z) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        if (VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }
}
