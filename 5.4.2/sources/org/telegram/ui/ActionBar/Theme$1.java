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

    public boolean selectDrawable(int i) {
        if (VERSION.SDK_INT >= 21) {
            return super.selectDrawable(i);
        }
        Drawable access$000 = Theme.access$000(this, i);
        ColorFilter colorFilter = null;
        if (access$000 instanceof BitmapDrawable) {
            colorFilter = ((BitmapDrawable) access$000).getPaint().getColorFilter();
        } else if (access$000 instanceof NinePatchDrawable) {
            colorFilter = ((NinePatchDrawable) access$000).getPaint().getColorFilter();
        }
        boolean selectDrawable = super.selectDrawable(i);
        if (colorFilter != null) {
            access$000.setColorFilter(colorFilter);
        }
        return selectDrawable;
    }
}
