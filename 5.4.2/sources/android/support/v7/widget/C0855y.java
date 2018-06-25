package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ae;
import android.util.AttributeSet;
import android.widget.TextView;

/* renamed from: android.support.v7.widget.y */
public class C0855y extends TextView implements ae {
    /* renamed from: a */
    private C1061h f2063a;
    /* renamed from: b */
    private C1086w f2064b;

    public C0855y(Context context) {
        this(context, null);
    }

    public C0855y(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public C0855y(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        this.f2063a = new C1061h(this);
        this.f2063a.m5841a(attributeSet, i);
        this.f2064b = C1086w.m5916a((TextView) this);
        this.f2064b.mo1016a(attributeSet, i);
        this.f2064b.mo1015a();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f2063a != null) {
            this.f2063a.m5844c();
        }
        if (this.f2064b != null) {
            this.f2064b.mo1015a();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.f2063a != null ? this.f2063a.m5836a() : null;
    }

    public Mode getSupportBackgroundTintMode() {
        return this.f2063a != null ? this.f2063a.m5842b() : null;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f2063a != null) {
            this.f2063a.m5840a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.f2063a != null) {
            this.f2063a.m5837a(i);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.f2063a != null) {
            this.f2063a.m5838a(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(Mode mode) {
        if (this.f2063a != null) {
            this.f2063a.m5839a(mode);
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.f2064b != null) {
            this.f2064b.m5918a(context, i);
        }
    }
}
