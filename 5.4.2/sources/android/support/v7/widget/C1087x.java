package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.widget.TextView;

@TargetApi(17)
/* renamed from: android.support.v7.widget.x */
class C1087x extends C1086w {
    /* renamed from: b */
    private bi f3228b;
    /* renamed from: c */
    private bi f3229c;

    C1087x(TextView textView) {
        super(textView);
    }

    /* renamed from: a */
    void mo1015a() {
        super.mo1015a();
        if (this.f3228b != null || this.f3229c != null) {
            Drawable[] compoundDrawablesRelative = this.a.getCompoundDrawablesRelative();
            m5919a(compoundDrawablesRelative[0], this.f3228b);
            m5919a(compoundDrawablesRelative[2], this.f3229c);
        }
    }

    /* renamed from: a */
    void mo1016a(AttributeSet attributeSet, int i) {
        super.mo1016a(attributeSet, i);
        Context context = this.a.getContext();
        C1069l a = C1069l.m5865a();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.AppCompatTextHelper, i, 0);
        if (obtainStyledAttributes.hasValue(C0747j.AppCompatTextHelper_android_drawableStart)) {
            this.f3228b = C1086w.m5915a(context, a, obtainStyledAttributes.getResourceId(C0747j.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (obtainStyledAttributes.hasValue(C0747j.AppCompatTextHelper_android_drawableEnd)) {
            this.f3229c = C1086w.m5915a(context, a, obtainStyledAttributes.getResourceId(C0747j.AppCompatTextHelper_android_drawableEnd, 0));
        }
        obtainStyledAttributes.recycle();
    }
}
