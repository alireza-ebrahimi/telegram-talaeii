package com.p077f.p078a.p086b;

import android.content.res.Resources;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import com.p077f.p078a.p086b.p087a.C1552d;
import com.p077f.p078a.p086b.p090c.C1567a;
import com.p077f.p078a.p086b.p094g.C1590a;

/* renamed from: com.f.a.b.c */
public final class C1570c {
    /* renamed from: a */
    private final int f4768a;
    /* renamed from: b */
    private final int f4769b;
    /* renamed from: c */
    private final int f4770c;
    /* renamed from: d */
    private final Drawable f4771d;
    /* renamed from: e */
    private final Drawable f4772e;
    /* renamed from: f */
    private final Drawable f4773f;
    /* renamed from: g */
    private final boolean f4774g;
    /* renamed from: h */
    private final boolean f4775h;
    /* renamed from: i */
    private final boolean f4776i;
    /* renamed from: j */
    private final C1552d f4777j;
    /* renamed from: k */
    private final Options f4778k;
    /* renamed from: l */
    private final int f4779l;
    /* renamed from: m */
    private final boolean f4780m;
    /* renamed from: n */
    private final Object f4781n;
    /* renamed from: o */
    private final C1590a f4782o;
    /* renamed from: p */
    private final C1590a f4783p;
    /* renamed from: q */
    private final C1567a f4784q;
    /* renamed from: r */
    private final Handler f4785r;
    /* renamed from: s */
    private final boolean f4786s;

    /* renamed from: com.f.a.b.c$a */
    public static class C1566a {
        /* renamed from: a */
        private int f4745a = 0;
        /* renamed from: b */
        private int f4746b = 0;
        /* renamed from: c */
        private int f4747c = 0;
        /* renamed from: d */
        private Drawable f4748d = null;
        /* renamed from: e */
        private Drawable f4749e = null;
        /* renamed from: f */
        private Drawable f4750f = null;
        /* renamed from: g */
        private boolean f4751g = false;
        /* renamed from: h */
        private boolean f4752h = false;
        /* renamed from: i */
        private boolean f4753i = false;
        /* renamed from: j */
        private C1552d f4754j = C1552d.IN_SAMPLE_POWER_OF_2;
        /* renamed from: k */
        private Options f4755k = new Options();
        /* renamed from: l */
        private int f4756l = 0;
        /* renamed from: m */
        private boolean f4757m = false;
        /* renamed from: n */
        private Object f4758n = null;
        /* renamed from: o */
        private C1590a f4759o = null;
        /* renamed from: p */
        private C1590a f4760p = null;
        /* renamed from: q */
        private C1567a f4761q = C1558a.m7688c();
        /* renamed from: r */
        private Handler f4762r = null;
        /* renamed from: s */
        private boolean f4763s = false;

        /* renamed from: a */
        public C1566a m7732a(int i) {
            this.f4745a = i;
            return this;
        }

        /* renamed from: a */
        public C1566a m7733a(Config config) {
            if (config == null) {
                throw new IllegalArgumentException("bitmapConfig can't be null");
            }
            this.f4755k.inPreferredConfig = config;
            return this;
        }

        /* renamed from: a */
        public C1566a m7734a(C1552d c1552d) {
            this.f4754j = c1552d;
            return this;
        }

        /* renamed from: a */
        public C1566a m7735a(C1570c c1570c) {
            this.f4745a = c1570c.f4768a;
            this.f4746b = c1570c.f4769b;
            this.f4747c = c1570c.f4770c;
            this.f4748d = c1570c.f4771d;
            this.f4749e = c1570c.f4772e;
            this.f4750f = c1570c.f4773f;
            this.f4751g = c1570c.f4774g;
            this.f4752h = c1570c.f4775h;
            this.f4753i = c1570c.f4776i;
            this.f4754j = c1570c.f4777j;
            this.f4755k = c1570c.f4778k;
            this.f4756l = c1570c.f4779l;
            this.f4757m = c1570c.f4780m;
            this.f4758n = c1570c.f4781n;
            this.f4759o = c1570c.f4782o;
            this.f4760p = c1570c.f4783p;
            this.f4761q = c1570c.f4784q;
            this.f4762r = c1570c.f4785r;
            this.f4763s = c1570c.f4786s;
            return this;
        }

        /* renamed from: a */
        public C1566a m7736a(boolean z) {
            this.f4752h = z;
            return this;
        }

        /* renamed from: a */
        public C1570c m7737a() {
            return new C1570c();
        }

        /* renamed from: b */
        public C1566a m7738b(int i) {
            this.f4746b = i;
            return this;
        }

        /* renamed from: b */
        public C1566a m7739b(boolean z) {
            this.f4753i = z;
            return this;
        }

        /* renamed from: c */
        public C1566a m7740c(int i) {
            this.f4747c = i;
            return this;
        }

        /* renamed from: c */
        public C1566a m7741c(boolean z) {
            this.f4757m = z;
            return this;
        }
    }

    private C1570c(C1566a c1566a) {
        this.f4768a = c1566a.f4745a;
        this.f4769b = c1566a.f4746b;
        this.f4770c = c1566a.f4747c;
        this.f4771d = c1566a.f4748d;
        this.f4772e = c1566a.f4749e;
        this.f4773f = c1566a.f4750f;
        this.f4774g = c1566a.f4751g;
        this.f4775h = c1566a.f4752h;
        this.f4776i = c1566a.f4753i;
        this.f4777j = c1566a.f4754j;
        this.f4778k = c1566a.f4755k;
        this.f4779l = c1566a.f4756l;
        this.f4780m = c1566a.f4757m;
        this.f4781n = c1566a.f4758n;
        this.f4782o = c1566a.f4759o;
        this.f4783p = c1566a.f4760p;
        this.f4784q = c1566a.f4761q;
        this.f4785r = c1566a.f4762r;
        this.f4786s = c1566a.f4763s;
    }

    /* renamed from: t */
    public static C1570c m7765t() {
        return new C1566a().m7737a();
    }

    /* renamed from: a */
    public Drawable m7766a(Resources resources) {
        return this.f4768a != 0 ? resources.getDrawable(this.f4768a) : this.f4771d;
    }

    /* renamed from: a */
    public boolean m7767a() {
        return (this.f4771d == null && this.f4768a == 0) ? false : true;
    }

    /* renamed from: b */
    public Drawable m7768b(Resources resources) {
        return this.f4769b != 0 ? resources.getDrawable(this.f4769b) : this.f4772e;
    }

    /* renamed from: b */
    public boolean m7769b() {
        return (this.f4772e == null && this.f4769b == 0) ? false : true;
    }

    /* renamed from: c */
    public Drawable m7770c(Resources resources) {
        return this.f4770c != 0 ? resources.getDrawable(this.f4770c) : this.f4773f;
    }

    /* renamed from: c */
    public boolean m7771c() {
        return (this.f4773f == null && this.f4770c == 0) ? false : true;
    }

    /* renamed from: d */
    public boolean m7772d() {
        return this.f4782o != null;
    }

    /* renamed from: e */
    public boolean m7773e() {
        return this.f4783p != null;
    }

    /* renamed from: f */
    public boolean m7774f() {
        return this.f4779l > 0;
    }

    /* renamed from: g */
    public boolean m7775g() {
        return this.f4774g;
    }

    /* renamed from: h */
    public boolean m7776h() {
        return this.f4775h;
    }

    /* renamed from: i */
    public boolean m7777i() {
        return this.f4776i;
    }

    /* renamed from: j */
    public C1552d m7778j() {
        return this.f4777j;
    }

    /* renamed from: k */
    public Options m7779k() {
        return this.f4778k;
    }

    /* renamed from: l */
    public int m7780l() {
        return this.f4779l;
    }

    /* renamed from: m */
    public boolean m7781m() {
        return this.f4780m;
    }

    /* renamed from: n */
    public Object m7782n() {
        return this.f4781n;
    }

    /* renamed from: o */
    public C1590a m7783o() {
        return this.f4782o;
    }

    /* renamed from: p */
    public C1590a m7784p() {
        return this.f4783p;
    }

    /* renamed from: q */
    public C1567a m7785q() {
        return this.f4784q;
    }

    /* renamed from: r */
    public Handler m7786r() {
        return this.f4785r;
    }

    /* renamed from: s */
    boolean m7787s() {
        return this.f4786s;
    }
}
