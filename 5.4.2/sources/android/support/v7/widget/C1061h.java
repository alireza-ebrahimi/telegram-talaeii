package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0747j;
import android.util.AttributeSet;
import android.view.View;

/* renamed from: android.support.v7.widget.h */
class C1061h {
    /* renamed from: a */
    private final View f3149a;
    /* renamed from: b */
    private final C1069l f3150b;
    /* renamed from: c */
    private int f3151c = -1;
    /* renamed from: d */
    private bi f3152d;
    /* renamed from: e */
    private bi f3153e;
    /* renamed from: f */
    private bi f3154f;

    C1061h(View view) {
        this.f3149a = view;
        this.f3150b = C1069l.m5865a();
    }

    /* renamed from: b */
    private boolean m5834b(Drawable drawable) {
        if (this.f3154f == null) {
            this.f3154f = new bi();
        }
        bi biVar = this.f3154f;
        biVar.m5651a();
        ColorStateList C = ah.m2763C(this.f3149a);
        if (C != null) {
            biVar.f3042d = true;
            biVar.f3039a = C;
        }
        Mode D = ah.m2764D(this.f3149a);
        if (D != null) {
            biVar.f3041c = true;
            biVar.f3040b = D;
        }
        if (!biVar.f3042d && !biVar.f3041c) {
            return false;
        }
        C1069l.m5868a(drawable, biVar, this.f3149a.getDrawableState());
        return true;
    }

    /* renamed from: d */
    private boolean m5835d() {
        int i = VERSION.SDK_INT;
        return i < 21 ? false : i == 21 || this.f3152d != null;
    }

    /* renamed from: a */
    ColorStateList m5836a() {
        return this.f3153e != null ? this.f3153e.f3039a : null;
    }

    /* renamed from: a */
    void m5837a(int i) {
        this.f3151c = i;
        m5843b(this.f3150b != null ? this.f3150b.m5887b(this.f3149a.getContext(), i) : null);
        m5844c();
    }

    /* renamed from: a */
    void m5838a(ColorStateList colorStateList) {
        if (this.f3153e == null) {
            this.f3153e = new bi();
        }
        this.f3153e.f3039a = colorStateList;
        this.f3153e.f3042d = true;
        m5844c();
    }

    /* renamed from: a */
    void m5839a(Mode mode) {
        if (this.f3153e == null) {
            this.f3153e = new bi();
        }
        this.f3153e.f3040b = mode;
        this.f3153e.f3041c = true;
        m5844c();
    }

    /* renamed from: a */
    void m5840a(Drawable drawable) {
        this.f3151c = -1;
        m5843b(null);
        m5844c();
    }

    /* renamed from: a */
    void m5841a(AttributeSet attributeSet, int i) {
        bk a = bk.m5654a(this.f3149a.getContext(), attributeSet, C0747j.ViewBackgroundHelper, i, 0);
        try {
            if (a.m5671g(C0747j.ViewBackgroundHelper_android_background)) {
                this.f3151c = a.m5670g(C0747j.ViewBackgroundHelper_android_background, -1);
                ColorStateList b = this.f3150b.m5887b(this.f3149a.getContext(), this.f3151c);
                if (b != null) {
                    m5843b(b);
                }
            }
            if (a.m5671g(C0747j.ViewBackgroundHelper_backgroundTint)) {
                ah.m2779a(this.f3149a, a.m5667e(C0747j.ViewBackgroundHelper_backgroundTint));
            }
            if (a.m5671g(C0747j.ViewBackgroundHelper_backgroundTintMode)) {
                ah.m2780a(this.f3149a, ai.m5430a(a.m5656a(C0747j.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
            a.m5658a();
        } catch (Throwable th) {
            a.m5658a();
        }
    }

    /* renamed from: b */
    Mode m5842b() {
        return this.f3153e != null ? this.f3153e.f3040b : null;
    }

    /* renamed from: b */
    void m5843b(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.f3152d == null) {
                this.f3152d = new bi();
            }
            this.f3152d.f3039a = colorStateList;
            this.f3152d.f3042d = true;
        } else {
            this.f3152d = null;
        }
        m5844c();
    }

    /* renamed from: c */
    void m5844c() {
        Drawable background = this.f3149a.getBackground();
        if (background == null) {
            return;
        }
        if (!m5835d() || !m5834b(background)) {
            if (this.f3153e != null) {
                C1069l.m5868a(background, this.f3153e, this.f3149a.getDrawableState());
            } else if (this.f3152d != null) {
                C1069l.m5868a(background, this.f3152d, this.f3149a.getDrawableState());
            }
        }
    }
}
