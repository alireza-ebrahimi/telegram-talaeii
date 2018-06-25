package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.p030f.C0840a;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

@TargetApi(9)
/* renamed from: android.support.v7.widget.w */
class C1086w {
    /* renamed from: a */
    final TextView f3223a;
    /* renamed from: b */
    private bi f3224b;
    /* renamed from: c */
    private bi f3225c;
    /* renamed from: d */
    private bi f3226d;
    /* renamed from: e */
    private bi f3227e;

    C1086w(TextView textView) {
        this.f3223a = textView;
    }

    /* renamed from: a */
    protected static bi m5915a(Context context, C1069l c1069l, int i) {
        ColorStateList b = c1069l.m5887b(context, i);
        if (b == null) {
            return null;
        }
        bi biVar = new bi();
        biVar.f3042d = true;
        biVar.f3039a = b;
        return biVar;
    }

    /* renamed from: a */
    static C1086w m5916a(TextView textView) {
        return VERSION.SDK_INT >= 17 ? new C1087x(textView) : new C1086w(textView);
    }

    /* renamed from: a */
    void mo1015a() {
        if (this.f3224b != null || this.f3225c != null || this.f3226d != null || this.f3227e != null) {
            Drawable[] compoundDrawables = this.f3223a.getCompoundDrawables();
            m5919a(compoundDrawables[0], this.f3224b);
            m5919a(compoundDrawables[1], this.f3225c);
            m5919a(compoundDrawables[2], this.f3226d);
            m5919a(compoundDrawables[3], this.f3227e);
        }
    }

    /* renamed from: a */
    void m5918a(Context context, int i) {
        bk a = bk.m5652a(context, i, C0747j.TextAppearance);
        if (a.m5671g(C0747j.TextAppearance_textAllCaps)) {
            m5921a(a.m5659a(C0747j.TextAppearance_textAllCaps, false));
        }
        if (VERSION.SDK_INT < 23 && a.m5671g(C0747j.TextAppearance_android_textColor)) {
            ColorStateList e = a.m5667e(C0747j.TextAppearance_android_textColor);
            if (e != null) {
                this.f3223a.setTextColor(e);
            }
        }
        a.m5658a();
    }

    /* renamed from: a */
    final void m5919a(Drawable drawable, bi biVar) {
        if (drawable != null && biVar != null) {
            C1069l.m5868a(drawable, biVar, this.f3223a.getDrawableState());
        }
    }

    /* renamed from: a */
    void mo1016a(AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        ColorStateList e;
        ColorStateList colorStateList = null;
        Context context = this.f3223a.getContext();
        C1069l a = C1069l.m5865a();
        bk a2 = bk.m5654a(context, attributeSet, C0747j.AppCompatTextHelper, i, 0);
        int g = a2.m5670g(C0747j.AppCompatTextHelper_android_textAppearance, -1);
        if (a2.m5671g(C0747j.AppCompatTextHelper_android_drawableLeft)) {
            this.f3224b = C1086w.m5915a(context, a, a2.m5670g(C0747j.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a2.m5671g(C0747j.AppCompatTextHelper_android_drawableTop)) {
            this.f3225c = C1086w.m5915a(context, a, a2.m5670g(C0747j.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a2.m5671g(C0747j.AppCompatTextHelper_android_drawableRight)) {
            this.f3226d = C1086w.m5915a(context, a, a2.m5670g(C0747j.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a2.m5671g(C0747j.AppCompatTextHelper_android_drawableBottom)) {
            this.f3227e = C1086w.m5915a(context, a, a2.m5670g(C0747j.AppCompatTextHelper_android_drawableBottom, 0));
        }
        a2.m5658a();
        boolean z3 = this.f3223a.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (g != -1) {
            bk a3 = bk.m5652a(context, g, C0747j.TextAppearance);
            if (z3 || !a3.m5671g(C0747j.TextAppearance_textAllCaps)) {
                z = false;
                z2 = false;
            } else {
                z2 = a3.m5659a(C0747j.TextAppearance_textAllCaps, false);
                z = true;
            }
            if (VERSION.SDK_INT < 23) {
                e = a3.m5671g(C0747j.TextAppearance_android_textColor) ? a3.m5667e(C0747j.TextAppearance_android_textColor) : null;
                if (a3.m5671g(C0747j.TextAppearance_android_textColorHint)) {
                    colorStateList = a3.m5667e(C0747j.TextAppearance_android_textColorHint);
                }
            } else {
                e = null;
            }
            a3.m5658a();
        } else {
            e = null;
            z = false;
            z2 = false;
        }
        bk a4 = bk.m5654a(context, attributeSet, C0747j.TextAppearance, i, 0);
        if (!z3 && a4.m5671g(C0747j.TextAppearance_textAllCaps)) {
            z2 = a4.m5659a(C0747j.TextAppearance_textAllCaps, false);
            z = true;
        }
        if (VERSION.SDK_INT < 23) {
            if (a4.m5671g(C0747j.TextAppearance_android_textColor)) {
                e = a4.m5667e(C0747j.TextAppearance_android_textColor);
            }
            if (a4.m5671g(C0747j.TextAppearance_android_textColorHint)) {
                colorStateList = a4.m5667e(C0747j.TextAppearance_android_textColorHint);
            }
        }
        a4.m5658a();
        if (e != null) {
            this.f3223a.setTextColor(e);
        }
        if (colorStateList != null) {
            this.f3223a.setHintTextColor(colorStateList);
        }
        if (!z3 && r0) {
            m5921a(z2);
        }
    }

    /* renamed from: a */
    void m5921a(boolean z) {
        this.f3223a.setTransformationMethod(z ? new C0840a(this.f3223a.getContext()) : null);
    }
}
