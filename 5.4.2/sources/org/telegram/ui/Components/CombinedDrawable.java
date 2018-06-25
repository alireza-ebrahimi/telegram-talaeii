package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;

public class CombinedDrawable extends Drawable implements Callback {
    private int backHeight;
    private int backWidth;
    private Drawable background;
    private boolean fullSize;
    private Drawable icon;
    private int iconHeight;
    private int iconWidth;
    private int left;
    private int top;

    public CombinedDrawable(Drawable drawable, Drawable drawable2) {
        this.background = drawable;
        this.icon = drawable2;
        drawable2.setCallback(this);
    }

    public CombinedDrawable(Drawable drawable, Drawable drawable2, int i, int i2) {
        this.background = drawable;
        this.icon = drawable2;
        this.left = i;
        this.top = i2;
        drawable2.setCallback(this);
    }

    public void draw(Canvas canvas) {
        this.background.setBounds(getBounds());
        this.background.draw(canvas);
        if (this.fullSize) {
            this.icon.setBounds(getBounds());
        } else if (this.iconWidth != 0) {
            r0 = (getBounds().centerX() - (this.iconWidth / 2)) + this.left;
            r1 = (getBounds().centerY() - (this.iconHeight / 2)) + this.top;
            this.icon.setBounds(r0, r1, this.iconWidth + r0, this.iconHeight + r1);
        } else {
            r0 = (getBounds().centerX() - (this.icon.getIntrinsicWidth() / 2)) + this.left;
            r1 = (getBounds().centerY() - (this.icon.getIntrinsicHeight() / 2)) + this.top;
            this.icon.setBounds(r0, r1, this.icon.getIntrinsicWidth() + r0, this.icon.getIntrinsicHeight() + r1);
        }
        this.icon.draw(canvas);
    }

    public Drawable getBackground() {
        return this.background;
    }

    public ConstantState getConstantState() {
        return this.icon.getConstantState();
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public int getIntrinsicHeight() {
        return this.backHeight != 0 ? this.backHeight : this.background.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.backWidth != 0 ? this.backWidth : this.background.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.backHeight != 0 ? this.backHeight : this.background.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.backWidth != 0 ? this.backWidth : this.background.getMinimumWidth();
    }

    public int getOpacity() {
        return this.icon.getOpacity();
    }

    public int[] getState() {
        return this.icon.getState();
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public boolean isStateful() {
        return this.icon.isStateful();
    }

    public void jumpToCurrentState() {
        this.icon.jumpToCurrentState();
    }

    protected boolean onStateChange(int[] iArr) {
        return true;
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.icon.setAlpha(i);
        this.background.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.icon.setColorFilter(colorFilter);
    }

    public void setCustomSize(int i, int i2) {
        this.backWidth = i;
        this.backHeight = i2;
    }

    public void setFullsize(boolean z) {
        this.fullSize = z;
    }

    public void setIconSize(int i, int i2) {
        this.iconWidth = i;
        this.iconHeight = i2;
    }

    public boolean setState(int[] iArr) {
        this.icon.setState(iArr);
        return true;
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
