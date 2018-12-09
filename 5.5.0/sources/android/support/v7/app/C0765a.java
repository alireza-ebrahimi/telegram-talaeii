package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.C0814b;
import android.support.v7.view.C0814b.C0797a;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v7.app.a */
public abstract class C0765a {

    /* renamed from: android.support.v7.app.a$a */
    public static class C0762a extends MarginLayoutParams {
        /* renamed from: a */
        public int f1739a;

        public C0762a(int i, int i2) {
            super(i, i2);
            this.f1739a = 0;
            this.f1739a = 8388627;
        }

        public C0762a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f1739a = 0;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0747j.ActionBarLayout);
            this.f1739a = obtainStyledAttributes.getInt(C0747j.ActionBarLayout_android_layout_gravity, 0);
            obtainStyledAttributes.recycle();
        }

        public C0762a(C0762a c0762a) {
            super(c0762a);
            this.f1739a = 0;
            this.f1739a = c0762a.f1739a;
        }

        public C0762a(LayoutParams layoutParams) {
            super(layoutParams);
            this.f1739a = 0;
        }
    }

    /* renamed from: android.support.v7.app.a$b */
    public interface C0763b {
        /* renamed from: a */
        void m3605a(boolean z);
    }

    @Deprecated
    /* renamed from: android.support.v7.app.a$c */
    public static abstract class C0764c {
        /* renamed from: a */
        public abstract Drawable m3606a();

        /* renamed from: b */
        public abstract CharSequence m3607b();

        /* renamed from: c */
        public abstract View m3608c();

        /* renamed from: d */
        public abstract void m3609d();

        /* renamed from: e */
        public abstract CharSequence m3610e();
    }

    /* renamed from: a */
    public abstract int mo663a();

    /* renamed from: a */
    public C0814b mo693a(C0797a c0797a) {
        return null;
    }

    /* renamed from: a */
    public void mo664a(float f) {
        if (f != BitmapDescriptorFactory.HUE_RED) {
            throw new UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
        }
    }

    /* renamed from: a */
    public void mo665a(Configuration configuration) {
    }

    /* renamed from: a */
    public void mo666a(CharSequence charSequence) {
    }

    /* renamed from: a */
    public void mo667a(boolean z) {
    }

    /* renamed from: a */
    public boolean mo668a(int i, KeyEvent keyEvent) {
        return false;
    }

    /* renamed from: b */
    public void mo695b(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
        }
    }

    /* renamed from: b */
    public abstract boolean mo669b();

    /* renamed from: c */
    public Context mo670c() {
        return null;
    }

    /* renamed from: c */
    public void mo671c(boolean z) {
    }

    /* renamed from: d */
    public int mo696d() {
        return 0;
    }

    /* renamed from: d */
    public void mo672d(boolean z) {
    }

    /* renamed from: e */
    public void mo673e(boolean z) {
    }

    /* renamed from: e */
    public boolean mo674e() {
        return false;
    }

    /* renamed from: f */
    public boolean mo675f() {
        return false;
    }

    /* renamed from: g */
    boolean mo676g() {
        return false;
    }

    /* renamed from: h */
    void mo677h() {
    }
}
