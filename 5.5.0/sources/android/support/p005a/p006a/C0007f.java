package android.support.p005a.p006a;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v4.p007b.p008a.C0006m;
import android.support.v4.p007b.p008a.C0375a;
import android.util.AttributeSet;

/* renamed from: android.support.a.a.f */
abstract class C0007f extends Drawable implements C0006m {
    /* renamed from: b */
    Drawable f13b;

    C0007f() {
    }

    /* renamed from: a */
    protected static TypedArray m4a(Resources resources, Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    public void applyTheme(Theme theme) {
        if (this.f13b != null) {
            C0375a.m1774a(this.f13b, theme);
        }
    }

    public void clearColorFilter() {
        if (this.f13b != null) {
            this.f13b.clearColorFilter();
        } else {
            super.clearColorFilter();
        }
    }

    public ColorFilter getColorFilter() {
        return this.f13b != null ? C0375a.m1782e(this.f13b) : null;
    }

    public Drawable getCurrent() {
        return this.f13b != null ? this.f13b.getCurrent() : super.getCurrent();
    }

    public int getMinimumHeight() {
        return this.f13b != null ? this.f13b.getMinimumHeight() : super.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.f13b != null ? this.f13b.getMinimumWidth() : super.getMinimumWidth();
    }

    public boolean getPadding(Rect rect) {
        return this.f13b != null ? this.f13b.getPadding(rect) : super.getPadding(rect);
    }

    public int[] getState() {
        return this.f13b != null ? this.f13b.getState() : super.getState();
    }

    public Region getTransparentRegion() {
        return this.f13b != null ? this.f13b.getTransparentRegion() : super.getTransparentRegion();
    }

    public void jumpToCurrentState() {
        if (this.f13b != null) {
            C0375a.m1769a(this.f13b);
        }
    }

    protected void onBoundsChange(Rect rect) {
        if (this.f13b != null) {
            this.f13b.setBounds(rect);
        } else {
            super.onBoundsChange(rect);
        }
    }

    protected boolean onLevelChange(int i) {
        return this.f13b != null ? this.f13b.setLevel(i) : super.onLevelChange(i);
    }

    public void setChangingConfigurations(int i) {
        if (this.f13b != null) {
            this.f13b.setChangingConfigurations(i);
        } else {
            super.setChangingConfigurations(i);
        }
    }

    public void setColorFilter(int i, Mode mode) {
        if (this.f13b != null) {
            this.f13b.setColorFilter(i, mode);
        } else {
            super.setColorFilter(i, mode);
        }
    }

    public void setFilterBitmap(boolean z) {
        if (this.f13b != null) {
            this.f13b.setFilterBitmap(z);
        }
    }

    public void setHotspot(float f, float f2) {
        if (this.f13b != null) {
            C0375a.m1770a(this.f13b, f, f2);
        }
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        if (this.f13b != null) {
            C0375a.m1772a(this.f13b, i, i2, i3, i4);
        }
    }

    public boolean setState(int[] iArr) {
        return this.f13b != null ? this.f13b.setState(iArr) : super.setState(iArr);
    }
}
