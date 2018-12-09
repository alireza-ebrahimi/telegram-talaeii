package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;

@TargetApi(9)
/* renamed from: android.support.v4.b.a.i */
class C0385i extends Drawable implements Callback, C0382h, C0006m {
    /* renamed from: a */
    static final Mode f1149a = Mode.SRC_IN;
    /* renamed from: b */
    C0383a f1150b;
    /* renamed from: c */
    Drawable f1151c;
    /* renamed from: d */
    private int f1152d;
    /* renamed from: e */
    private Mode f1153e;
    /* renamed from: f */
    private boolean f1154f;
    /* renamed from: g */
    private boolean f1155g;

    /* renamed from: android.support.v4.b.a.i$a */
    protected static abstract class C0383a extends ConstantState {
        /* renamed from: a */
        int f1145a;
        /* renamed from: b */
        ConstantState f1146b;
        /* renamed from: c */
        ColorStateList f1147c = null;
        /* renamed from: d */
        Mode f1148d = C0385i.f1149a;

        C0383a(C0383a c0383a, Resources resources) {
            if (c0383a != null) {
                this.f1145a = c0383a.f1145a;
                this.f1146b = c0383a.f1146b;
                this.f1147c = c0383a.f1147c;
                this.f1148d = c0383a.f1148d;
            }
        }

        /* renamed from: a */
        boolean m1811a() {
            return this.f1146b != null;
        }

        public int getChangingConfigurations() {
            return (this.f1146b != null ? this.f1146b.getChangingConfigurations() : 0) | this.f1145a;
        }

        public Drawable newDrawable() {
            return newDrawable(null);
        }

        public abstract Drawable newDrawable(Resources resources);
    }

    /* renamed from: android.support.v4.b.a.i$b */
    private static class C0384b extends C0383a {
        C0384b(C0383a c0383a, Resources resources) {
            super(c0383a, resources);
        }

        public Drawable newDrawable(Resources resources) {
            return new C0385i(this, resources);
        }
    }

    C0385i(Drawable drawable) {
        this.f1150b = mo309b();
        mo308a(drawable);
    }

    C0385i(C0383a c0383a, Resources resources) {
        this.f1150b = c0383a;
        m1812a(resources);
    }

    /* renamed from: a */
    private void m1812a(Resources resources) {
        if (this.f1150b != null && this.f1150b.f1146b != null) {
            mo308a(m1815a(this.f1150b.f1146b, resources));
        }
    }

    /* renamed from: a */
    private boolean m1813a(int[] iArr) {
        if (!mo310c()) {
            return false;
        }
        ColorStateList colorStateList = this.f1150b.f1147c;
        Mode mode = this.f1150b.f1148d;
        if (colorStateList == null || mode == null) {
            this.f1154f = false;
            clearColorFilter();
            return false;
        }
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        if (this.f1154f && colorForState == this.f1152d && mode == this.f1153e) {
            return false;
        }
        setColorFilter(colorForState, mode);
        this.f1152d = colorForState;
        this.f1153e = mode;
        this.f1154f = true;
        return true;
    }

    /* renamed from: a */
    public final Drawable mo307a() {
        return this.f1151c;
    }

    /* renamed from: a */
    protected Drawable m1815a(ConstantState constantState, Resources resources) {
        return constantState.newDrawable(resources);
    }

    /* renamed from: a */
    public final void mo308a(Drawable drawable) {
        if (this.f1151c != null) {
            this.f1151c.setCallback(null);
        }
        this.f1151c = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            setVisible(drawable.isVisible(), true);
            setState(drawable.getState());
            setLevel(drawable.getLevel());
            setBounds(drawable.getBounds());
            if (this.f1150b != null) {
                this.f1150b.f1146b = drawable.getConstantState();
            }
        }
        invalidateSelf();
    }

    /* renamed from: b */
    C0383a mo309b() {
        return new C0384b(this.f1150b, null);
    }

    /* renamed from: c */
    protected boolean mo310c() {
        return true;
    }

    public void draw(Canvas canvas) {
        this.f1151c.draw(canvas);
    }

    public int getChangingConfigurations() {
        return ((this.f1150b != null ? this.f1150b.getChangingConfigurations() : 0) | super.getChangingConfigurations()) | this.f1151c.getChangingConfigurations();
    }

    public ConstantState getConstantState() {
        if (this.f1150b == null || !this.f1150b.m1811a()) {
            return null;
        }
        this.f1150b.f1145a = getChangingConfigurations();
        return this.f1150b;
    }

    public Drawable getCurrent() {
        return this.f1151c.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.f1151c.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.f1151c.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.f1151c.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.f1151c.getMinimumWidth();
    }

    public int getOpacity() {
        return this.f1151c.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.f1151c.getPadding(rect);
    }

    public int[] getState() {
        return this.f1151c.getState();
    }

    public Region getTransparentRegion() {
        return this.f1151c.getTransparentRegion();
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public boolean isStateful() {
        ColorStateList colorStateList = (!mo310c() || this.f1150b == null) ? null : this.f1150b.f1147c;
        return (colorStateList != null && colorStateList.isStateful()) || this.f1151c.isStateful();
    }

    public Drawable mutate() {
        if (!this.f1155g && super.mutate() == this) {
            this.f1150b = mo309b();
            if (this.f1151c != null) {
                this.f1151c.mutate();
            }
            if (this.f1150b != null) {
                this.f1150b.f1146b = this.f1151c != null ? this.f1151c.getConstantState() : null;
            }
            this.f1155g = true;
        }
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        if (this.f1151c != null) {
            this.f1151c.setBounds(rect);
        }
    }

    protected boolean onLevelChange(int i) {
        return this.f1151c.setLevel(i);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void setAlpha(int i) {
        this.f1151c.setAlpha(i);
    }

    public void setChangingConfigurations(int i) {
        this.f1151c.setChangingConfigurations(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f1151c.setColorFilter(colorFilter);
    }

    public void setDither(boolean z) {
        this.f1151c.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.f1151c.setFilterBitmap(z);
    }

    public boolean setState(int[] iArr) {
        return m1813a(iArr) || this.f1151c.setState(iArr);
    }

    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    public void setTintList(ColorStateList colorStateList) {
        this.f1150b.f1147c = colorStateList;
        m1813a(getState());
    }

    public void setTintMode(Mode mode) {
        this.f1150b.f1148d = mode;
        m1813a(getState());
    }

    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.f1151c.setVisible(z, z2);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
