package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.af;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p027c.p028a.C0825b;
import android.util.AttributeSet;
import android.widget.CheckBox;

/* renamed from: android.support.v7.widget.i */
public class C1062i extends CheckBox implements af {
    /* renamed from: a */
    private C1064k f3155a;

    public C1062i(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.checkboxStyle);
    }

    public C1062i(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        this.f3155a = new C1064k(this);
        this.f3155a.m5849a(attributeSet, i);
    }

    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        return this.f3155a != null ? this.f3155a.m5845a(compoundPaddingLeft) : compoundPaddingLeft;
    }

    public ColorStateList getSupportButtonTintList() {
        return this.f3155a != null ? this.f3155a.m5846a() : null;
    }

    public Mode getSupportButtonTintMode() {
        return this.f3155a != null ? this.f3155a.m5850b() : null;
    }

    public void setButtonDrawable(int i) {
        setButtonDrawable(C0825b.m3939b(getContext(), i));
    }

    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        if (this.f3155a != null) {
            this.f3155a.m5851c();
        }
    }

    public void setSupportButtonTintList(ColorStateList colorStateList) {
        if (this.f3155a != null) {
            this.f3155a.m5847a(colorStateList);
        }
    }

    public void setSupportButtonTintMode(Mode mode) {
        if (this.f3155a != null) {
            this.f3155a.m5848a(mode);
        }
    }
}
