package com.persianswitch.sdk.base.utils;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;

public class DrawableUtils {
    public static void setBackground(View view, Drawable drawable, boolean clone) {
        if (clone) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        if (VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }
}
