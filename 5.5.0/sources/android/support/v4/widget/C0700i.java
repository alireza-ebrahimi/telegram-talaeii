package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.widget.i */
public final class C0700i {
    /* renamed from: b */
    private static final C0696c f1561b;
    /* renamed from: a */
    private Object f1562a;

    /* renamed from: android.support.v4.widget.i$c */
    interface C0696c {
        /* renamed from: a */
        Object mo574a(Context context);

        /* renamed from: a */
        void mo575a(Object obj, int i, int i2);

        /* renamed from: a */
        boolean mo576a(Object obj);

        /* renamed from: a */
        boolean mo577a(Object obj, float f);

        /* renamed from: a */
        boolean mo578a(Object obj, float f, float f2);

        /* renamed from: a */
        boolean mo579a(Object obj, int i);

        /* renamed from: a */
        boolean mo580a(Object obj, Canvas canvas);

        /* renamed from: b */
        void mo581b(Object obj);

        /* renamed from: c */
        boolean mo582c(Object obj);
    }

    /* renamed from: android.support.v4.widget.i$a */
    static class C0697a implements C0696c {
        C0697a() {
        }

        /* renamed from: a */
        public Object mo574a(Context context) {
            return null;
        }

        /* renamed from: a */
        public void mo575a(Object obj, int i, int i2) {
        }

        /* renamed from: a */
        public boolean mo576a(Object obj) {
            return true;
        }

        /* renamed from: a */
        public boolean mo577a(Object obj, float f) {
            return false;
        }

        /* renamed from: a */
        public boolean mo578a(Object obj, float f, float f2) {
            return false;
        }

        /* renamed from: a */
        public boolean mo579a(Object obj, int i) {
            return false;
        }

        /* renamed from: a */
        public boolean mo580a(Object obj, Canvas canvas) {
            return false;
        }

        /* renamed from: b */
        public void mo581b(Object obj) {
        }

        /* renamed from: c */
        public boolean mo582c(Object obj) {
            return false;
        }
    }

    /* renamed from: android.support.v4.widget.i$b */
    static class C0698b implements C0696c {
        C0698b() {
        }

        /* renamed from: a */
        public Object mo574a(Context context) {
            return C0701j.m3427a(context);
        }

        /* renamed from: a */
        public void mo575a(Object obj, int i, int i2) {
            C0701j.m3428a(obj, i, i2);
        }

        /* renamed from: a */
        public boolean mo576a(Object obj) {
            return C0701j.m3429a(obj);
        }

        /* renamed from: a */
        public boolean mo577a(Object obj, float f) {
            return C0701j.m3430a(obj, f);
        }

        /* renamed from: a */
        public boolean mo578a(Object obj, float f, float f2) {
            return C0701j.m3430a(obj, f);
        }

        /* renamed from: a */
        public boolean mo579a(Object obj, int i) {
            return C0701j.m3431a(obj, i);
        }

        /* renamed from: a */
        public boolean mo580a(Object obj, Canvas canvas) {
            return C0701j.m3432a(obj, canvas);
        }

        /* renamed from: b */
        public void mo581b(Object obj) {
            C0701j.m3433b(obj);
        }

        /* renamed from: c */
        public boolean mo582c(Object obj) {
            return C0701j.m3434c(obj);
        }
    }

    /* renamed from: android.support.v4.widget.i$d */
    static class C0699d extends C0698b {
        C0699d() {
        }

        /* renamed from: a */
        public boolean mo578a(Object obj, float f, float f2) {
            return C0702k.m3435a(obj, f, f2);
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            f1561b = new C0699d();
        } else if (VERSION.SDK_INT >= 14) {
            f1561b = new C0698b();
        } else {
            f1561b = new C0697a();
        }
    }

    public C0700i(Context context) {
        this.f1562a = f1561b.mo574a(context);
    }

    /* renamed from: a */
    public void m3419a(int i, int i2) {
        f1561b.mo575a(this.f1562a, i, i2);
    }

    /* renamed from: a */
    public boolean m3420a() {
        return f1561b.mo576a(this.f1562a);
    }

    @Deprecated
    /* renamed from: a */
    public boolean m3421a(float f) {
        return f1561b.mo577a(this.f1562a, f);
    }

    /* renamed from: a */
    public boolean m3422a(float f, float f2) {
        return f1561b.mo578a(this.f1562a, f, f2);
    }

    /* renamed from: a */
    public boolean m3423a(int i) {
        return f1561b.mo579a(this.f1562a, i);
    }

    /* renamed from: a */
    public boolean m3424a(Canvas canvas) {
        return f1561b.mo580a(this.f1562a, canvas);
    }

    /* renamed from: b */
    public void m3425b() {
        f1561b.mo581b(this.f1562a);
    }

    /* renamed from: c */
    public boolean m3426c() {
        return f1561b.mo582c(this.f1562a);
    }
}
