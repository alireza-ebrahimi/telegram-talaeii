package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;

public final class zzbge extends Drawable implements Callback {
    private int mAlpha;
    private int mFrom;
    private long zzebf;
    private boolean zzgdt;
    private int zzgdy;
    private int zzgdz;
    private int zzgea;
    private int zzgeb;
    private boolean zzgec;
    private zzbgi zzged;
    private Drawable zzgee;
    private Drawable zzgef;
    private boolean zzgeg;
    private boolean zzgeh;
    private boolean zzgei;
    private int zzgej;

    public zzbge(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = zzbgg.zzgek;
        }
        this.zzgee = drawable;
        drawable.setCallback(this);
        zzbgi zzbgi = this.zzged;
        zzbgi.zzgem |= drawable.getChangingConfigurations();
        if (drawable2 == null) {
            drawable2 = zzbgg.zzgek;
        }
        this.zzgef = drawable2;
        drawable2.setCallback(this);
        zzbgi = this.zzged;
        zzbgi.zzgem |= drawable2.getChangingConfigurations();
    }

    zzbge(zzbgi zzbgi) {
        this.zzgdy = 0;
        this.zzgea = 255;
        this.mAlpha = 0;
        this.zzgdt = true;
        this.zzged = new zzbgi(zzbgi);
    }

    private final boolean canConstantState() {
        if (!this.zzgeg) {
            boolean z = (this.zzgee.getConstantState() == null || this.zzgef.getConstantState() == null) ? false : true;
            this.zzgeh = z;
            this.zzgeg = true;
        }
        return this.zzgeh;
    }

    public final void draw(Canvas canvas) {
        int i = 1;
        int i2 = 0;
        switch (this.zzgdy) {
            case 1:
                this.zzebf = SystemClock.uptimeMillis();
                this.zzgdy = 2;
                break;
            case 2:
                if (this.zzebf >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zzebf)) / ((float) this.zzgeb);
                    if (uptimeMillis < 1.0f) {
                        i = 0;
                    }
                    if (i != 0) {
                        this.zzgdy = 0;
                    }
                    this.mAlpha = (int) ((Math.min(uptimeMillis, 1.0f) * ((float) this.zzgdz)) + 0.0f);
                    break;
                }
                break;
        }
        i2 = i;
        i = this.mAlpha;
        boolean z = this.zzgdt;
        Drawable drawable = this.zzgee;
        Drawable drawable2 = this.zzgef;
        if (i2 != 0) {
            if (!z || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.zzgea) {
                drawable2.setAlpha(this.zzgea);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z) {
            drawable.setAlpha(this.zzgea - i);
        }
        drawable.draw(canvas);
        if (z) {
            drawable.setAlpha(this.zzgea);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zzgea);
        }
        invalidateSelf();
    }

    public final int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.zzged.mChangingConfigurations) | this.zzged.zzgem;
    }

    public final ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zzged.mChangingConfigurations = getChangingConfigurations();
        return this.zzged;
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zzgee.getIntrinsicHeight(), this.zzgef.getIntrinsicHeight());
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zzgee.getIntrinsicWidth(), this.zzgef.getIntrinsicWidth());
    }

    public final int getOpacity() {
        if (!this.zzgei) {
            this.zzgej = Drawable.resolveOpacity(this.zzgee.getOpacity(), this.zzgef.getOpacity());
            this.zzgei = true;
        }
        return this.zzgej;
    }

    public final void invalidateDrawable(Drawable drawable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final Drawable mutate() {
        if (!this.zzgec && super.mutate() == this) {
            if (canConstantState()) {
                this.zzgee.mutate();
                this.zzgef.mutate();
                this.zzgec = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    protected final void onBoundsChange(Rect rect) {
        this.zzgee.setBounds(rect);
        this.zzgef.setBounds(rect);
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void setAlpha(int i) {
        if (this.mAlpha == this.zzgea) {
            this.mAlpha = i;
        }
        this.zzgea = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zzgee.setColorFilter(colorFilter);
        this.zzgef.setColorFilter(colorFilter);
    }

    public final void startTransition(int i) {
        this.mFrom = 0;
        this.zzgdz = this.zzgea;
        this.mAlpha = 0;
        this.zzgeb = 250;
        this.zzgdy = 1;
        invalidateSelf();
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final Drawable zzaln() {
        return this.zzgef;
    }
}
