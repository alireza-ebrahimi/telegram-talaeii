package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ae;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p027c.p028a.C0825b;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

/* renamed from: android.support.v7.widget.g */
public class C0957g extends AutoCompleteTextView implements ae {
    /* renamed from: a */
    private static final int[] f2550a = new int[]{16843126};
    /* renamed from: b */
    private C1061h f2551b;
    /* renamed from: c */
    private C1086w f2552c;

    public C0957g(Context context) {
        this(context, null);
    }

    public C0957g(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0738a.autoCompleteTextViewStyle);
    }

    public C0957g(Context context, AttributeSet attributeSet, int i) {
        super(bh.m5649a(context), attributeSet, i);
        bk a = bk.m5654a(getContext(), attributeSet, f2550a, i, 0);
        if (a.m5671g(0)) {
            setDropDownBackgroundDrawable(a.m5657a(0));
        }
        a.m5658a();
        this.f2551b = new C1061h(this);
        this.f2551b.m5841a(attributeSet, i);
        this.f2552c = C1086w.m5916a((TextView) this);
        this.f2552c.mo1016a(attributeSet, i);
        this.f2552c.mo1015a();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.f2551b != null) {
            this.f2551b.m5844c();
        }
        if (this.f2552c != null) {
            this.f2552c.mo1015a();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        return this.f2551b != null ? this.f2551b.m5836a() : null;
    }

    public Mode getSupportBackgroundTintMode() {
        return this.f2551b != null ? this.f2551b.m5842b() : null;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.f2551b != null) {
            this.f2551b.m5840a(drawable);
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.f2551b != null) {
            this.f2551b.m5837a(i);
        }
    }

    public void setDropDownBackgroundResource(int i) {
        setDropDownBackgroundDrawable(C0825b.m3939b(getContext(), i));
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.f2551b != null) {
            this.f2551b.m5838a(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(Mode mode) {
        if (this.f2551b != null) {
            this.f2551b.m5839a(mode);
        }
    }

    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.f2552c != null) {
            this.f2552c.m5918a(context, i);
        }
    }
}
