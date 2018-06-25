package android.support.v4.p007b.p008a;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.support.v4.p007b.p008a.C0385i.C0383a;

@TargetApi(21)
/* renamed from: android.support.v4.b.a.l */
class C0391l extends C0389k {

    /* renamed from: android.support.v4.b.a.l$a */
    private static class C0390a extends C0383a {
        C0390a(C0383a c0383a, Resources resources) {
            super(c0383a, resources);
        }

        public Drawable newDrawable(Resources resources) {
            return new C0391l(this, resources);
        }
    }

    C0391l(Drawable drawable) {
        super(drawable);
    }

    C0391l(C0383a c0383a, Resources resources) {
        super(c0383a, resources);
    }

    /* renamed from: b */
    C0383a mo309b() {
        return new C0390a(this.b, null);
    }

    /* renamed from: c */
    protected boolean mo310c() {
        if (VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable drawable = this.c;
        return (drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer) || (drawable instanceof InsetDrawable);
    }

    public Rect getDirtyBounds() {
        return this.c.getDirtyBounds();
    }

    public void getOutline(Outline outline) {
        this.c.getOutline(outline);
    }

    public void setHotspot(float f, float f2) {
        this.c.setHotspot(f, f2);
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.c.setHotspotBounds(i, i2, i3, i4);
    }

    public boolean setState(int[] iArr) {
        if (!super.setState(iArr)) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    public void setTint(int i) {
        if (mo310c()) {
            super.setTint(i);
        } else {
            this.c.setTint(i);
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        if (mo310c()) {
            super.setTintList(colorStateList);
        } else {
            this.c.setTintList(colorStateList);
        }
    }

    public void setTintMode(Mode mode) {
        if (mo310c()) {
            super.setTintMode(mode);
        } else {
            this.c.setTintMode(mode);
        }
    }
}
