package android.support.v7.p015d.p016a;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.support.v4.p007b.p008a.C0375a;

/* renamed from: android.support.v7.d.a.a */
public class C0169a extends Drawable implements Callback {
    /* renamed from: a */
    private Drawable f568a;

    public C0169a(Drawable drawable) {
        m810a(drawable);
    }

    /* renamed from: a */
    public void m810a(Drawable drawable) {
        if (this.f568a != null) {
            this.f568a.setCallback(null);
        }
        this.f568a = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    /* renamed from: b */
    public Drawable m811b() {
        return this.f568a;
    }

    public void draw(Canvas canvas) {
        this.f568a.draw(canvas);
    }

    public int getChangingConfigurations() {
        return this.f568a.getChangingConfigurations();
    }

    public Drawable getCurrent() {
        return this.f568a.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.f568a.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.f568a.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.f568a.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.f568a.getMinimumWidth();
    }

    public int getOpacity() {
        return this.f568a.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.f568a.getPadding(rect);
    }

    public int[] getState() {
        return this.f568a.getState();
    }

    public Region getTransparentRegion() {
        return this.f568a.getTransparentRegion();
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public boolean isAutoMirrored() {
        return C0375a.m1778b(this.f568a);
    }

    public boolean isStateful() {
        return this.f568a.isStateful();
    }

    public void jumpToCurrentState() {
        C0375a.m1769a(this.f568a);
    }

    protected void onBoundsChange(Rect rect) {
        this.f568a.setBounds(rect);
    }

    protected boolean onLevelChange(int i) {
        return this.f568a.setLevel(i);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.f568a.setAlpha(i);
    }

    public void setAutoMirrored(boolean z) {
        C0375a.m1777a(this.f568a, z);
    }

    public void setChangingConfigurations(int i) {
        this.f568a.setChangingConfigurations(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f568a.setColorFilter(colorFilter);
    }

    public void setDither(boolean z) {
        this.f568a.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.f568a.setFilterBitmap(z);
    }

    public void setHotspot(float f, float f2) {
        C0375a.m1770a(this.f568a, f, f2);
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        C0375a.m1772a(this.f568a, i, i2, i3, i4);
    }

    public boolean setState(int[] iArr) {
        return this.f568a.setState(iArr);
    }

    public void setTint(int i) {
        C0375a.m1771a(this.f568a, i);
    }

    public void setTintList(ColorStateList colorStateList) {
        C0375a.m1773a(this.f568a, colorStateList);
    }

    public void setTintMode(Mode mode) {
        C0375a.m1776a(this.f568a, mode);
    }

    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.f568a.setVisible(z, z2);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
