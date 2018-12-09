package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ae;
import android.support.v7.p025a.C0748a.C0738a;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/* renamed from: android.support.v7.widget.m */
public class C0193m extends EditText implements ae {
    /* renamed from: a */
    private C1061h f675a;
    /* renamed from: b */
    private C1086w f676b;

    public C0193m(Context context) {
        this(context, null);
    }

    public C0193m(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.editTextStyle);
    }

    public C0193m(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        this.f675a = new C1061h(this);
        this.f675a.m5841a(attributeSet, i);
        this.f676b = C1086w.m5916a((TextView) this);
        this.f676b.mo1016a(attributeSet, i);
        this.f676b.mo1015a();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f675a != null) {
            this.f675a.m5844c();
        }
        if (this.f676b != null) {
            this.f676b.mo1015a();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.f675a != null ? this.f675a.m5836a() : null;
    }

    public Mode getSupportBackgroundTintMode() {
        return this.f675a != null ? this.f675a.m5842b() : null;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f675a != null) {
            this.f675a.m5840a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.f675a != null) {
            this.f675a.m5837a(i);
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.f675a != null) {
            this.f675a.m5838a(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(Mode mode) {
        if (this.f675a != null) {
            this.f675a.m5839a(mode);
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.f676b != null) {
            this.f676b.m5918a(context, i);
        }
    }
}
