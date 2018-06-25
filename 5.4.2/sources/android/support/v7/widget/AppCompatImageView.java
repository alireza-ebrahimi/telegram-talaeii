package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ae;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AppCompatImageView extends ImageView implements ae {
    /* renamed from: a */
    private C1061h f2384a;
    /* renamed from: b */
    private C1070n f2385b;

    public AppCompatImageView(Context context) {
        this(context, null);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        this.f2384a = new C1061h(this);
        this.f2384a.m5841a(attributeSet, i);
        this.f2385b = new C1070n(this);
        this.f2385b.m5889a(attributeSet, i);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f2384a != null) {
            this.f2384a.m5844c();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.f2384a != null ? this.f2384a.m5836a() : null;
    }

    public Mode getSupportBackgroundTintMode() {
        return this.f2384a != null ? this.f2384a.m5842b() : null;
    }

    public boolean hasOverlappingRendering() {
        return this.f2385b.m5890a() && super.hasOverlappingRendering();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f2384a != null) {
            this.f2384a.m5840a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.f2384a != null) {
            this.f2384a.m5837a(i);
        }
    }

    public void setImageResource(int i) {
        this.f2385b.m5888a(i);
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.f2384a != null) {
            this.f2384a.m5838a(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(Mode mode) {
        if (this.f2384a != null) {
            this.f2384a.m5839a(mode);
        }
    }
}
