package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.CompoundButton;

/* renamed from: android.support.v4.widget.c */
public final class C0687c {
    /* renamed from: a */
    private static final C0683c f1546a;

    /* renamed from: android.support.v4.widget.c$c */
    interface C0683c {
        /* renamed from: a */
        Drawable mo567a(CompoundButton compoundButton);

        /* renamed from: a */
        void mo568a(CompoundButton compoundButton, ColorStateList colorStateList);

        /* renamed from: a */
        void mo569a(CompoundButton compoundButton, Mode mode);
    }

    /* renamed from: android.support.v4.widget.c$b */
    static class C0684b implements C0683c {
        C0684b() {
        }

        /* renamed from: a */
        public Drawable mo567a(CompoundButton compoundButton) {
            return C0689e.m3372a(compoundButton);
        }

        /* renamed from: a */
        public void mo568a(CompoundButton compoundButton, ColorStateList colorStateList) {
            C0689e.m3373a(compoundButton, colorStateList);
        }

        /* renamed from: a */
        public void mo569a(CompoundButton compoundButton, Mode mode) {
            C0689e.m3374a(compoundButton, mode);
        }
    }

    /* renamed from: android.support.v4.widget.c$d */
    static class C0685d extends C0684b {
        C0685d() {
        }

        /* renamed from: a */
        public void mo568a(CompoundButton compoundButton, ColorStateList colorStateList) {
            C0690f.m3375a(compoundButton, colorStateList);
        }

        /* renamed from: a */
        public void mo569a(CompoundButton compoundButton, Mode mode) {
            C0690f.m3376a(compoundButton, mode);
        }
    }

    /* renamed from: android.support.v4.widget.c$a */
    static class C0686a extends C0685d {
        C0686a() {
        }

        /* renamed from: a */
        public Drawable mo567a(CompoundButton compoundButton) {
            return C0688d.m3371a(compoundButton);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            f1546a = new C0686a();
        } else if (i >= 21) {
            f1546a = new C0685d();
        } else {
            f1546a = new C0684b();
        }
    }

    /* renamed from: a */
    public static Drawable m3368a(CompoundButton compoundButton) {
        return f1546a.mo567a(compoundButton);
    }

    /* renamed from: a */
    public static void m3369a(CompoundButton compoundButton, ColorStateList colorStateList) {
        f1546a.mo568a(compoundButton, colorStateList);
    }

    /* renamed from: a */
    public static void m3370a(CompoundButton compoundButton, Mode mode) {
        f1546a.mo569a(compoundButton, mode);
    }
}
