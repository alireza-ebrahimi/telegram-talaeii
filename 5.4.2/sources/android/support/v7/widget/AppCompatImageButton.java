package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ae;
import android.support.v7.p025a.C0748a.C0738a;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class AppCompatImageButton extends ImageButton implements ae {
    /* renamed from: a */
    private C1061h f285a;
    /* renamed from: b */
    private C1070n f286b;

    public AppCompatImageButton(Context context) {
        this(context, null);
    }

    public AppCompatImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.imageButtonStyle);
    }

    public AppCompatImageButton(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        this.f285a = new C1061h(this);
        this.f285a.m5841a(attributeSet, i);
        this.f286b = new C1070n(this);
        this.f286b.m5889a(attributeSet, i);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f285a != null) {
            this.f285a.m5844c();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.f285a != null ? this.f285a.m5836a() : null;
    }

    public Mode getSupportBackgroundTintMode() {
        return this.f285a != null ? this.f285a.m5842b() : null;
    }

    public boolean hasOverlappingRendering() {
        return this.f286b.m5890a() && super.hasOverlappingRendering();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f285a != null) {
            this.f285a.m5840a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.f285a != null) {
            this.f285a.m5837a(i);
        }
    }

    public void setImageResource(int i) {
        this.f286b.m5888a(i);
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.f285a != null) {
            this.f285a.m5838a(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(Mode mode) {
        if (this.f285a != null) {
            this.f285a.m5839a(mode);
        }
    }
}
