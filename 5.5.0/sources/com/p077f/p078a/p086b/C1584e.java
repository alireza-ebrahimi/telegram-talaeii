package com.p077f.p078a.p086b;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.p077f.p078a.p079a.p080a.C1526a;
import com.p077f.p078a.p079a.p080a.p083b.C1533a;
import com.p077f.p078a.p079a.p084b.C1536a;
import com.p077f.p078a.p079a.p084b.p085a.C1537a;
import com.p077f.p078a.p086b.p087a.C1551c;
import com.p077f.p078a.p086b.p087a.C1553e;
import com.p077f.p078a.p086b.p087a.C1555g;
import com.p077f.p078a.p086b.p089b.C1561b;
import com.p077f.p078a.p086b.p091d.C1572b;
import com.p077f.p078a.p086b.p091d.C1572b.C1574a;
import com.p077f.p078a.p086b.p094g.C1590a;
import com.p077f.p078a.p095c.C1602c;
import com.p077f.p078a.p095c.C1604d;
import java.io.InputStream;
import java.util.concurrent.Executor;

/* renamed from: com.f.a.b.e */
public final class C1584e {
    /* renamed from: a */
    final Resources f4839a;
    /* renamed from: b */
    final int f4840b;
    /* renamed from: c */
    final int f4841c;
    /* renamed from: d */
    final int f4842d;
    /* renamed from: e */
    final int f4843e;
    /* renamed from: f */
    final C1590a f4844f;
    /* renamed from: g */
    final Executor f4845g;
    /* renamed from: h */
    final Executor f4846h;
    /* renamed from: i */
    final boolean f4847i;
    /* renamed from: j */
    final boolean f4848j;
    /* renamed from: k */
    final int f4849k;
    /* renamed from: l */
    final int f4850l;
    /* renamed from: m */
    final C1555g f4851m;
    /* renamed from: n */
    final C1536a f4852n;
    /* renamed from: o */
    final C1526a f4853o;
    /* renamed from: p */
    final C1572b f4854p;
    /* renamed from: q */
    final C1561b f4855q;
    /* renamed from: r */
    final C1570c f4856r;
    /* renamed from: s */
    final C1572b f4857s;
    /* renamed from: t */
    final C1572b f4858t;

    /* renamed from: com.f.a.b.e$a */
    public static class C1577a {
        /* renamed from: a */
        public static final C1555g f4807a = C1555g.FIFO;
        /* renamed from: b */
        private Context f4808b;
        /* renamed from: c */
        private int f4809c = 0;
        /* renamed from: d */
        private int f4810d = 0;
        /* renamed from: e */
        private int f4811e = 0;
        /* renamed from: f */
        private int f4812f = 0;
        /* renamed from: g */
        private C1590a f4813g = null;
        /* renamed from: h */
        private Executor f4814h = null;
        /* renamed from: i */
        private Executor f4815i = null;
        /* renamed from: j */
        private boolean f4816j = false;
        /* renamed from: k */
        private boolean f4817k = false;
        /* renamed from: l */
        private int f4818l = 3;
        /* renamed from: m */
        private int f4819m = 3;
        /* renamed from: n */
        private boolean f4820n = false;
        /* renamed from: o */
        private C1555g f4821o = f4807a;
        /* renamed from: p */
        private int f4822p = 0;
        /* renamed from: q */
        private long f4823q = 0;
        /* renamed from: r */
        private int f4824r = 0;
        /* renamed from: s */
        private C1536a f4825s = null;
        /* renamed from: t */
        private C1526a f4826t = null;
        /* renamed from: u */
        private C1533a f4827u = null;
        /* renamed from: v */
        private C1572b f4828v = null;
        /* renamed from: w */
        private C1561b f4829w;
        /* renamed from: x */
        private C1570c f4830x = null;
        /* renamed from: y */
        private boolean f4831y = false;

        public C1577a(Context context) {
            this.f4808b = context.getApplicationContext();
        }

        /* renamed from: d */
        private void m7823d() {
            if (this.f4814h == null) {
                this.f4814h = C1558a.m7684a(this.f4818l, this.f4819m, this.f4821o);
            } else {
                this.f4816j = true;
            }
            if (this.f4815i == null) {
                this.f4815i = C1558a.m7684a(this.f4818l, this.f4819m, this.f4821o);
            } else {
                this.f4817k = true;
            }
            if (this.f4826t == null) {
                if (this.f4827u == null) {
                    this.f4827u = C1558a.m7686b();
                }
                this.f4826t = C1558a.m7679a(this.f4808b, this.f4827u, this.f4823q, this.f4824r);
            }
            if (this.f4825s == null) {
                this.f4825s = C1558a.m7680a(this.f4808b, this.f4822p);
            }
            if (this.f4820n) {
                this.f4825s = new C1537a(this.f4825s, C1604d.m7945a());
            }
            if (this.f4828v == null) {
                this.f4828v = C1558a.m7682a(this.f4808b);
            }
            if (this.f4829w == null) {
                this.f4829w = C1558a.m7681a(this.f4831y);
            }
            if (this.f4830x == null) {
                this.f4830x = C1570c.m7765t();
            }
        }

        /* renamed from: a */
        public C1577a m7839a() {
            this.f4820n = true;
            return this;
        }

        /* renamed from: a */
        public C1577a m7840a(int i) {
            if (!(this.f4814h == null && this.f4815i == null)) {
                C1602c.m7941c("threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.", new Object[0]);
            }
            if (i < 1) {
                this.f4819m = 1;
            } else if (i > 10) {
                this.f4819m = 10;
            } else {
                this.f4819m = i;
            }
            return this;
        }

        /* renamed from: a */
        public C1577a m7841a(C1533a c1533a) {
            if (this.f4826t != null) {
                C1602c.m7941c("diskCache() and diskCacheFileNameGenerator() calls overlap each other", new Object[0]);
            }
            this.f4827u = c1533a;
            return this;
        }

        /* renamed from: a */
        public C1577a m7842a(C1555g c1555g) {
            if (!(this.f4814h == null && this.f4815i == null)) {
                C1602c.m7941c("threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.", new Object[0]);
            }
            this.f4821o = c1555g;
            return this;
        }

        /* renamed from: b */
        public C1577a m7843b() {
            this.f4831y = true;
            return this;
        }

        /* renamed from: b */
        public C1577a m7844b(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("maxCacheSize must be a positive number");
            }
            if (this.f4826t != null) {
                C1602c.m7941c("diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other", new Object[0]);
            }
            this.f4823q = (long) i;
            return this;
        }

        /* renamed from: c */
        public C1584e m7845c() {
            m7823d();
            return new C1584e();
        }
    }

    /* renamed from: com.f.a.b.e$b */
    private static class C1578b implements C1572b {
        /* renamed from: a */
        private final C1572b f4832a;

        public C1578b(C1572b c1572b) {
            this.f4832a = c1572b;
        }

        /* renamed from: a */
        public InputStream mo1227a(String str, Object obj) {
            switch (C1574a.m7802a(str)) {
                case HTTP:
                case HTTPS:
                    throw new IllegalStateException();
                default:
                    return this.f4832a.mo1227a(str, obj);
            }
        }
    }

    /* renamed from: com.f.a.b.e$c */
    private static class C1579c implements C1572b {
        /* renamed from: a */
        private final C1572b f4833a;

        public C1579c(C1572b c1572b) {
            this.f4833a = c1572b;
        }

        /* renamed from: a */
        public InputStream mo1227a(String str, Object obj) {
            InputStream a = this.f4833a.mo1227a(str, obj);
            switch (C1574a.m7802a(str)) {
                case HTTP:
                case HTTPS:
                    return new C1551c(a);
                default:
                    return a;
            }
        }
    }

    private C1584e(C1577a c1577a) {
        this.f4839a = c1577a.f4808b.getResources();
        this.f4840b = c1577a.f4809c;
        this.f4841c = c1577a.f4810d;
        this.f4842d = c1577a.f4811e;
        this.f4843e = c1577a.f4812f;
        this.f4844f = c1577a.f4813g;
        this.f4845g = c1577a.f4814h;
        this.f4846h = c1577a.f4815i;
        this.f4849k = c1577a.f4818l;
        this.f4850l = c1577a.f4819m;
        this.f4851m = c1577a.f4821o;
        this.f4853o = c1577a.f4826t;
        this.f4852n = c1577a.f4825s;
        this.f4856r = c1577a.f4830x;
        this.f4854p = c1577a.f4828v;
        this.f4855q = c1577a.f4829w;
        this.f4847i = c1577a.f4816j;
        this.f4848j = c1577a.f4817k;
        this.f4857s = new C1578b(this.f4854p);
        this.f4858t = new C1579c(this.f4854p);
        C1602c.m7938a(c1577a.f4831y);
    }

    /* renamed from: a */
    C1553e m7882a() {
        DisplayMetrics displayMetrics = this.f4839a.getDisplayMetrics();
        int i = this.f4840b;
        if (i <= 0) {
            i = displayMetrics.widthPixels;
        }
        int i2 = this.f4841c;
        if (i2 <= 0) {
            i2 = displayMetrics.heightPixels;
        }
        return new C1553e(i, i2);
    }
}
