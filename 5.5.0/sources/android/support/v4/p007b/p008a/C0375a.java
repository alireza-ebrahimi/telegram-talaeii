package android.support.v4.p007b.p008a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

/* renamed from: android.support.v4.b.a.a */
public final class C0375a {
    /* renamed from: a */
    static final C0368b f1142a;

    /* renamed from: android.support.v4.b.a.a$b */
    interface C0368b {
        /* renamed from: a */
        void mo290a(Drawable drawable);

        /* renamed from: a */
        void mo291a(Drawable drawable, float f, float f2);

        /* renamed from: a */
        void mo292a(Drawable drawable, int i);

        /* renamed from: a */
        void mo293a(Drawable drawable, int i, int i2, int i3, int i4);

        /* renamed from: a */
        void mo294a(Drawable drawable, ColorStateList colorStateList);

        /* renamed from: a */
        void mo295a(Drawable drawable, Theme theme);

        /* renamed from: a */
        void mo296a(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme);

        /* renamed from: a */
        void mo297a(Drawable drawable, Mode mode);

        /* renamed from: a */
        void mo298a(Drawable drawable, boolean z);

        /* renamed from: b */
        boolean mo299b(Drawable drawable);

        /* renamed from: b */
        boolean mo300b(Drawable drawable, int i);

        /* renamed from: c */
        Drawable mo301c(Drawable drawable);

        /* renamed from: d */
        int mo302d(Drawable drawable);

        /* renamed from: e */
        boolean mo303e(Drawable drawable);

        /* renamed from: f */
        ColorFilter mo304f(Drawable drawable);

        /* renamed from: g */
        void mo305g(Drawable drawable);
    }

    /* renamed from: android.support.v4.b.a.a$a */
    static class C0369a implements C0368b {
        C0369a() {
        }

        /* renamed from: a */
        public void mo290a(Drawable drawable) {
        }

        /* renamed from: a */
        public void mo291a(Drawable drawable, float f, float f2) {
        }

        /* renamed from: a */
        public void mo292a(Drawable drawable, int i) {
            C0377c.m1787a(drawable, i);
        }

        /* renamed from: a */
        public void mo293a(Drawable drawable, int i, int i2, int i3, int i4) {
        }

        /* renamed from: a */
        public void mo294a(Drawable drawable, ColorStateList colorStateList) {
            C0377c.m1788a(drawable, colorStateList);
        }

        /* renamed from: a */
        public void mo295a(Drawable drawable, Theme theme) {
        }

        /* renamed from: a */
        public void mo296a(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
            C0377c.m1789a(drawable, resources, xmlPullParser, attributeSet, theme);
        }

        /* renamed from: a */
        public void mo297a(Drawable drawable, Mode mode) {
            C0377c.m1790a(drawable, mode);
        }

        /* renamed from: a */
        public void mo298a(Drawable drawable, boolean z) {
        }

        /* renamed from: b */
        public boolean mo299b(Drawable drawable) {
            return false;
        }

        /* renamed from: b */
        public boolean mo300b(Drawable drawable, int i) {
            return false;
        }

        /* renamed from: c */
        public Drawable mo301c(Drawable drawable) {
            return C0377c.m1786a(drawable);
        }

        /* renamed from: d */
        public int mo302d(Drawable drawable) {
            return 0;
        }

        /* renamed from: e */
        public boolean mo303e(Drawable drawable) {
            return false;
        }

        /* renamed from: f */
        public ColorFilter mo304f(Drawable drawable) {
            return null;
        }

        /* renamed from: g */
        public void mo305g(Drawable drawable) {
            drawable.clearColorFilter();
        }
    }

    /* renamed from: android.support.v4.b.a.a$c */
    static class C0370c extends C0369a {
        C0370c() {
        }

        /* renamed from: a */
        public void mo290a(Drawable drawable) {
            C0378d.m1791a(drawable);
        }

        /* renamed from: c */
        public Drawable mo301c(Drawable drawable) {
            return C0378d.m1792b(drawable);
        }
    }

    /* renamed from: android.support.v4.b.a.a$d */
    static class C0371d extends C0370c {
        C0371d() {
        }

        /* renamed from: b */
        public boolean mo300b(Drawable drawable, int i) {
            return C0379e.m1793a(drawable, i);
        }
    }

    /* renamed from: android.support.v4.b.a.a$e */
    static class C0372e extends C0371d {
        C0372e() {
        }

        /* renamed from: a */
        public void mo298a(Drawable drawable, boolean z) {
            C0380f.m1794a(drawable, z);
        }

        /* renamed from: b */
        public boolean mo299b(Drawable drawable) {
            return C0380f.m1795a(drawable);
        }

        /* renamed from: c */
        public Drawable mo301c(Drawable drawable) {
            return C0380f.m1796b(drawable);
        }

        /* renamed from: d */
        public int mo302d(Drawable drawable) {
            return C0380f.m1797c(drawable);
        }
    }

    /* renamed from: android.support.v4.b.a.a$f */
    static class C0373f extends C0372e {
        C0373f() {
        }

        /* renamed from: a */
        public void mo291a(Drawable drawable, float f, float f2) {
            C0381g.m1799a(drawable, f, f2);
        }

        /* renamed from: a */
        public void mo292a(Drawable drawable, int i) {
            C0381g.m1800a(drawable, i);
        }

        /* renamed from: a */
        public void mo293a(Drawable drawable, int i, int i2, int i3, int i4) {
            C0381g.m1801a(drawable, i, i2, i3, i4);
        }

        /* renamed from: a */
        public void mo294a(Drawable drawable, ColorStateList colorStateList) {
            C0381g.m1802a(drawable, colorStateList);
        }

        /* renamed from: a */
        public void mo295a(Drawable drawable, Theme theme) {
            C0381g.m1803a(drawable, theme);
        }

        /* renamed from: a */
        public void mo296a(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
            C0381g.m1804a(drawable, resources, xmlPullParser, attributeSet, theme);
        }

        /* renamed from: a */
        public void mo297a(Drawable drawable, Mode mode) {
            C0381g.m1805a(drawable, mode);
        }

        /* renamed from: c */
        public Drawable mo301c(Drawable drawable) {
            return C0381g.m1798a(drawable);
        }

        /* renamed from: e */
        public boolean mo303e(Drawable drawable) {
            return C0381g.m1806b(drawable);
        }

        /* renamed from: f */
        public ColorFilter mo304f(Drawable drawable) {
            return C0381g.m1807c(drawable);
        }

        /* renamed from: g */
        public void mo305g(Drawable drawable) {
            C0381g.m1808d(drawable);
        }
    }

    /* renamed from: android.support.v4.b.a.a$g */
    static class C0374g extends C0373f {
        C0374g() {
        }

        /* renamed from: b */
        public boolean mo300b(Drawable drawable, int i) {
            return C0376b.m1785a(drawable, i);
        }

        /* renamed from: c */
        public Drawable mo301c(Drawable drawable) {
            return drawable;
        }

        /* renamed from: g */
        public void mo305g(Drawable drawable) {
            drawable.clearColorFilter();
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            f1142a = new C0374g();
        } else if (i >= 21) {
            f1142a = new C0373f();
        } else if (i >= 19) {
            f1142a = new C0372e();
        } else if (i >= 17) {
            f1142a = new C0371d();
        } else if (i >= 11) {
            f1142a = new C0370c();
        } else {
            f1142a = new C0369a();
        }
    }

    /* renamed from: a */
    public static void m1769a(Drawable drawable) {
        f1142a.mo290a(drawable);
    }

    /* renamed from: a */
    public static void m1770a(Drawable drawable, float f, float f2) {
        f1142a.mo291a(drawable, f, f2);
    }

    /* renamed from: a */
    public static void m1771a(Drawable drawable, int i) {
        f1142a.mo292a(drawable, i);
    }

    /* renamed from: a */
    public static void m1772a(Drawable drawable, int i, int i2, int i3, int i4) {
        f1142a.mo293a(drawable, i, i2, i3, i4);
    }

    /* renamed from: a */
    public static void m1773a(Drawable drawable, ColorStateList colorStateList) {
        f1142a.mo294a(drawable, colorStateList);
    }

    /* renamed from: a */
    public static void m1774a(Drawable drawable, Theme theme) {
        f1142a.mo295a(drawable, theme);
    }

    /* renamed from: a */
    public static void m1775a(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Theme theme) {
        f1142a.mo296a(drawable, resources, xmlPullParser, attributeSet, theme);
    }

    /* renamed from: a */
    public static void m1776a(Drawable drawable, Mode mode) {
        f1142a.mo297a(drawable, mode);
    }

    /* renamed from: a */
    public static void m1777a(Drawable drawable, boolean z) {
        f1142a.mo298a(drawable, z);
    }

    /* renamed from: b */
    public static boolean m1778b(Drawable drawable) {
        return f1142a.mo299b(drawable);
    }

    /* renamed from: b */
    public static boolean m1779b(Drawable drawable, int i) {
        return f1142a.mo300b(drawable, i);
    }

    /* renamed from: c */
    public static int m1780c(Drawable drawable) {
        return f1142a.mo302d(drawable);
    }

    /* renamed from: d */
    public static boolean m1781d(Drawable drawable) {
        return f1142a.mo303e(drawable);
    }

    /* renamed from: e */
    public static ColorFilter m1782e(Drawable drawable) {
        return f1142a.mo304f(drawable);
    }

    /* renamed from: f */
    public static void m1783f(Drawable drawable) {
        f1142a.mo305g(drawable);
    }

    /* renamed from: g */
    public static Drawable m1784g(Drawable drawable) {
        return f1142a.mo301c(drawable);
    }
}
