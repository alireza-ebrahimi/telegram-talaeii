package net.hockeyapp.android.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;

public class ViewHelper {
    public static Drawable getGradient() {
        return new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{-16777216, 0});
    }
}
