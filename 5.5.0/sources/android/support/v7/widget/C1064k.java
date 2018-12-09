package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.widget.C0687c;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.p027c.p028a.C0825b;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/* renamed from: android.support.v7.widget.k */
class C1064k {
    /* renamed from: a */
    private final CompoundButton f3158a;
    /* renamed from: b */
    private ColorStateList f3159b = null;
    /* renamed from: c */
    private Mode f3160c = null;
    /* renamed from: d */
    private boolean f3161d = false;
    /* renamed from: e */
    private boolean f3162e = false;
    /* renamed from: f */
    private boolean f3163f;

    C1064k(CompoundButton compoundButton) {
        this.f3158a = compoundButton;
    }

    /* renamed from: a */
    int m5845a(int i) {
        if (VERSION.SDK_INT >= 17) {
            return i;
        }
        Drawable a = C0687c.m3368a(this.f3158a);
        return a != null ? i + a.getIntrinsicWidth() : i;
    }

    /* renamed from: a */
    ColorStateList m5846a() {
        return this.f3159b;
    }

    /* renamed from: a */
    void m5847a(ColorStateList colorStateList) {
        this.f3159b = colorStateList;
        this.f3161d = true;
        m5852d();
    }

    /* renamed from: a */
    void m5848a(Mode mode) {
        this.f3160c = mode;
        this.f3162e = true;
        m5852d();
    }

    /* renamed from: a */
    void m5849a(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = this.f3158a.getContext().obtainStyledAttributes(attributeSet, C0747j.CompoundButton, i, 0);
        try {
            if (obtainStyledAttributes.hasValue(C0747j.CompoundButton_android_button)) {
                int resourceId = obtainStyledAttributes.getResourceId(C0747j.CompoundButton_android_button, 0);
                if (resourceId != 0) {
                    this.f3158a.setButtonDrawable(C0825b.m3939b(this.f3158a.getContext(), resourceId));
                }
            }
            if (obtainStyledAttributes.hasValue(C0747j.CompoundButton_buttonTint)) {
                C0687c.m3369a(this.f3158a, obtainStyledAttributes.getColorStateList(C0747j.CompoundButton_buttonTint));
            }
            if (obtainStyledAttributes.hasValue(C0747j.CompoundButton_buttonTintMode)) {
                C0687c.m3370a(this.f3158a, ai.m5430a(obtainStyledAttributes.getInt(C0747j.CompoundButton_buttonTintMode, -1), null));
            }
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: b */
    Mode m5850b() {
        return this.f3160c;
    }

    /* renamed from: c */
    void m5851c() {
        if (this.f3163f) {
            this.f3163f = false;
            return;
        }
        this.f3163f = true;
        m5852d();
    }

    /* renamed from: d */
    void m5852d() {
        Drawable a = C0687c.m3368a(this.f3158a);
        if (a == null) {
            return;
        }
        if (this.f3161d || this.f3162e) {
            a = C0375a.m1784g(a).mutate();
            if (this.f3161d) {
                C0375a.m1773a(a, this.f3159b);
            }
            if (this.f3162e) {
                C0375a.m1776a(a, this.f3160c);
            }
            if (a.isStateful()) {
                a.setState(this.f3158a.getDrawableState());
            }
            this.f3158a.setButtonDrawable(a);
        }
    }
}
