package com.google.android.gms.common.images.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class CrossFadingDrawable extends Drawable implements Callback {
    private int mAlpha;
    private int mFrom;
    private boolean zzpl;
    private int zzpw;
    private long zzpx;
    private int zzpy;
    private int zzpz;
    private int zzqa;
    private boolean zzqb;
    private zzb zzqc;
    private Drawable zzqd;
    private Drawable zzqe;
    private boolean zzqf;
    private boolean zzqg;
    private boolean zzqh;
    private int zzqi;

    private static final class zza extends Drawable {
        private static final zza zzqj = new zza();
        private static final zzb zzqk = new zzb();

        private zza() {
        }

        public final void draw(Canvas canvas) {
        }

        public final ConstantState getConstantState() {
            return zzqk;
        }

        public final int getOpacity() {
            return -2;
        }

        public final void setAlpha(int i) {
        }

        public final void setColorFilter(ColorFilter colorFilter) {
        }
    }

    static final class zzb extends ConstantState {
        int mChangingConfigurations;
        int zzql;

        zzb(zzb zzb) {
            if (zzb != null) {
                this.mChangingConfigurations = zzb.mChangingConfigurations;
                this.zzql = zzb.zzql;
            }
        }

        public final int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public final Drawable newDrawable() {
            return new CrossFadingDrawable(this);
        }
    }

    public CrossFadingDrawable(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = zza.zzqj;
        }
        this.zzqd = drawable;
        drawable.setCallback(this);
        zzb zzb = this.zzqc;
        zzb.zzql |= drawable.getChangingConfigurations();
        if (drawable2 == null) {
            drawable2 = zza.zzqj;
        }
        this.zzqe = drawable2;
        drawable2.setCallback(this);
        zzb = this.zzqc;
        zzb.zzql |= drawable2.getChangingConfigurations();
    }

    CrossFadingDrawable(zzb zzb) {
        this.zzpw = 0;
        this.zzpz = 255;
        this.mAlpha = 0;
        this.zzpl = true;
        this.zzqc = new zzb(zzb);
    }

    public final boolean canConstantState() {
        if (!this.zzqf) {
            boolean z = (this.zzqd.getConstantState() == null || this.zzqe.getConstantState() == null) ? false : true;
            this.zzqg = z;
            this.zzqf = true;
        }
        return this.zzqg;
    }

    public final void draw(Canvas canvas) {
        int i = 1;
        int i2 = 0;
        switch (this.zzpw) {
            case 1:
                this.zzpx = SystemClock.uptimeMillis();
                this.zzpw = 2;
                break;
            case 2:
                if (this.zzpx >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zzpx)) / ((float) this.zzqa);
                    if (uptimeMillis < 1.0f) {
                        i = 0;
                    }
                    if (i != 0) {
                        this.zzpw = 0;
                    }
                    this.mAlpha = (int) ((Math.min(uptimeMillis, 1.0f) * ((float) this.zzpy)) + BitmapDescriptorFactory.HUE_RED);
                    break;
                }
                break;
        }
        i2 = i;
        i = this.mAlpha;
        boolean z = this.zzpl;
        Drawable drawable = this.zzqd;
        Drawable drawable2 = this.zzqe;
        if (i2 != 0) {
            if (!z || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.zzpz) {
                drawable2.setAlpha(this.zzpz);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z) {
            drawable.setAlpha(this.zzpz - i);
        }
        drawable.draw(canvas);
        if (z) {
            drawable.setAlpha(this.zzpz);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zzpz);
        }
        invalidateSelf();
    }

    public final int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.zzqc.mChangingConfigurations) | this.zzqc.zzql;
    }

    public final ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zzqc.mChangingConfigurations = getChangingConfigurations();
        return this.zzqc;
    }

    public final Drawable getEndDrawable() {
        return this.zzqe;
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zzqd.getIntrinsicHeight(), this.zzqe.getIntrinsicHeight());
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zzqd.getIntrinsicWidth(), this.zzqe.getIntrinsicWidth());
    }

    public final int getOpacity() {
        if (!this.zzqh) {
            this.zzqi = Drawable.resolveOpacity(this.zzqd.getOpacity(), this.zzqe.getOpacity());
            this.zzqh = true;
        }
        return this.zzqi;
    }

    public final Drawable getStartDrawable() {
        return this.zzqd;
    }

    public final void invalidateDrawable(Drawable drawable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final Drawable mutate() {
        if (!this.zzqb && super.mutate() == this) {
            if (canConstantState()) {
                this.zzqd.mutate();
                this.zzqe.mutate();
                this.zzqb = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    protected final void onBoundsChange(Rect rect) {
        this.zzqd.setBounds(rect);
        this.zzqe.setBounds(rect);
    }

    public final void resetTransition() {
        this.mAlpha = 0;
        this.zzpw = 0;
        invalidateSelf();
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void setAlpha(int i) {
        if (this.mAlpha == this.zzpz) {
            this.mAlpha = i;
        }
        this.zzpz = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zzqd.setColorFilter(colorFilter);
        this.zzqe.setColorFilter(colorFilter);
    }

    public final void setCrossFadeEnabled(boolean z) {
        this.zzpl = z;
    }

    public final void startTransition(int i) {
        this.mFrom = 0;
        this.zzpy = this.zzpz;
        this.mAlpha = 0;
        this.zzqa = i;
        this.zzpw = 1;
        invalidateSelf();
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }
}
