package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.TextView;

/* renamed from: android.support.v4.widget.z */
public final class C0737z {
    /* renamed from: a */
    static final C0731f f1630a;

    /* renamed from: android.support.v4.widget.z$f */
    interface C0731f {
        /* renamed from: a */
        int mo595a(TextView textView);

        /* renamed from: a */
        void mo596a(TextView textView, int i);

        /* renamed from: a */
        void mo597a(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4);

        /* renamed from: b */
        Drawable[] mo598b(TextView textView);
    }

    /* renamed from: android.support.v4.widget.z$b */
    static class C0732b implements C0731f {
        C0732b() {
        }

        /* renamed from: a */
        public int mo595a(TextView textView) {
            return ab.m3311a(textView);
        }

        /* renamed from: a */
        public void mo596a(TextView textView, int i) {
            ab.m3314a(textView, i);
        }

        /* renamed from: a */
        public void mo597a(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        }

        /* renamed from: b */
        public Drawable[] mo598b(TextView textView) {
            return ab.m3315b(textView);
        }
    }

    /* renamed from: android.support.v4.widget.z$e */
    static class C0733e extends C0732b {
        C0733e() {
        }

        /* renamed from: a */
        public int mo595a(TextView textView) {
            return ac.m3316a(textView);
        }
    }

    /* renamed from: android.support.v4.widget.z$c */
    static class C0734c extends C0733e {
        C0734c() {
        }

        /* renamed from: a */
        public void mo597a(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            ad.m3317a(textView, drawable, drawable2, drawable3, drawable4);
        }

        /* renamed from: b */
        public Drawable[] mo598b(TextView textView) {
            return ad.m3318a(textView);
        }
    }

    /* renamed from: android.support.v4.widget.z$d */
    static class C0735d extends C0734c {
        C0735d() {
        }

        /* renamed from: a */
        public void mo597a(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            ae.m3319a(textView, drawable, drawable2, drawable3, drawable4);
        }

        /* renamed from: b */
        public Drawable[] mo598b(TextView textView) {
            return ae.m3320a(textView);
        }
    }

    /* renamed from: android.support.v4.widget.z$a */
    static class C0736a extends C0735d {
        C0736a() {
        }

        /* renamed from: a */
        public void mo596a(TextView textView, int i) {
            aa.m3310a(textView, i);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            f1630a = new C0736a();
        } else if (i >= 18) {
            f1630a = new C0735d();
        } else if (i >= 17) {
            f1630a = new C0734c();
        } else if (i >= 16) {
            f1630a = new C0733e();
        } else {
            f1630a = new C0732b();
        }
    }

    /* renamed from: a */
    public static int m3571a(TextView textView) {
        return f1630a.mo595a(textView);
    }

    /* renamed from: a */
    public static void m3572a(TextView textView, int i) {
        f1630a.mo596a(textView, i);
    }

    /* renamed from: a */
    public static void m3573a(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        f1630a.mo597a(textView, drawable, drawable2, drawable3, drawable4);
    }

    /* renamed from: b */
    public static Drawable[] m3574b(TextView textView) {
        return f1630a.mo598b(textView);
    }
}
