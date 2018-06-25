package org.telegram.ui.ActionBar;

import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;

class Theme$1 extends StateListDrawable {
    Theme$1() {
    }

    public boolean selectDrawable(int index) {
        if (VERSION.SDK_INT >= 21) {
            return super.selectDrawable(index);
        }
        Drawable drawable = Theme.access$000(this, index);
        ColorFilter colorFilter = null;
        if (drawable instanceof BitmapDrawable) {
            colorFilter = ((BitmapDrawable) drawable).getPaint().getColorFilter();
        } else if (drawable instanceof NinePatchDrawable) {
            colorFilter = ((NinePatchDrawable) drawable).getPaint().getColorFilter();
        }
        boolean result = super.selectDrawable(index);
        if (colorFilter == null) {
            return result;
        }
        drawable.setColorFilter(colorFilter);
        return result;
    }
}
