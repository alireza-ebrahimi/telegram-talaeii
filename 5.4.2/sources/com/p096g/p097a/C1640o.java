package com.p096g.p097a;

import android.graphics.Bitmap.Config;
import android.net.Uri;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p096g.p097a.C1636m.C1633c;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.g.a.o */
public final class C1640o {
    /* renamed from: s */
    private static final long f5024s = TimeUnit.SECONDS.toNanos(5);
    /* renamed from: a */
    int f5025a;
    /* renamed from: b */
    long f5026b;
    /* renamed from: c */
    int f5027c;
    /* renamed from: d */
    public final Uri f5028d;
    /* renamed from: e */
    public final int f5029e;
    /* renamed from: f */
    public final String f5030f;
    /* renamed from: g */
    public final List<C1646u> f5031g;
    /* renamed from: h */
    public final int f5032h;
    /* renamed from: i */
    public final int f5033i;
    /* renamed from: j */
    public final boolean f5034j;
    /* renamed from: k */
    public final boolean f5035k;
    /* renamed from: l */
    public final boolean f5036l;
    /* renamed from: m */
    public final float f5037m;
    /* renamed from: n */
    public final float f5038n;
    /* renamed from: o */
    public final float f5039o;
    /* renamed from: p */
    public final boolean f5040p;
    /* renamed from: q */
    public final Config f5041q;
    /* renamed from: r */
    public final C1633c f5042r;

    /* renamed from: com.g.a.o$a */
    public static final class C1639a {
        /* renamed from: a */
        private Uri f5009a;
        /* renamed from: b */
        private int f5010b;
        /* renamed from: c */
        private String f5011c;
        /* renamed from: d */
        private int f5012d;
        /* renamed from: e */
        private int f5013e;
        /* renamed from: f */
        private boolean f5014f;
        /* renamed from: g */
        private boolean f5015g;
        /* renamed from: h */
        private boolean f5016h;
        /* renamed from: i */
        private float f5017i;
        /* renamed from: j */
        private float f5018j;
        /* renamed from: k */
        private float f5019k;
        /* renamed from: l */
        private boolean f5020l;
        /* renamed from: m */
        private List<C1646u> f5021m;
        /* renamed from: n */
        private Config f5022n;
        /* renamed from: o */
        private C1633c f5023o;

        /* renamed from: a */
        public C1639a m8024a(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Width must be positive number or 0.");
            } else if (i2 < 0) {
                throw new IllegalArgumentException("Height must be positive number or 0.");
            } else if (i2 == 0 && i == 0) {
                throw new IllegalArgumentException("At least one dimension has to be positive number.");
            } else {
                this.f5012d = i;
                this.f5013e = i2;
                return this;
            }
        }

        /* renamed from: a */
        boolean m8025a() {
            return (this.f5009a == null && this.f5010b == 0) ? false : true;
        }

        /* renamed from: b */
        boolean m8026b() {
            return (this.f5012d == 0 && this.f5013e == 0) ? false : true;
        }

        /* renamed from: c */
        public C1640o m8027c() {
            if (this.f5015g && this.f5014f) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            } else if (this.f5014f && this.f5012d == 0 && this.f5013e == 0) {
                throw new IllegalStateException("Center crop requires calling resize with positive width and height.");
            } else if (this.f5015g && this.f5012d == 0 && this.f5013e == 0) {
                throw new IllegalStateException("Center inside requires calling resize with positive width and height.");
            } else {
                if (this.f5023o == null) {
                    this.f5023o = C1633c.NORMAL;
                }
                return new C1640o(this.f5009a, this.f5010b, this.f5011c, this.f5021m, this.f5012d, this.f5013e, this.f5014f, this.f5015g, this.f5016h, this.f5017i, this.f5018j, this.f5019k, this.f5020l, this.f5022n, this.f5023o);
            }
        }
    }

    private C1640o(Uri uri, int i, String str, List<C1646u> list, int i2, int i3, boolean z, boolean z2, boolean z3, float f, float f2, float f3, boolean z4, Config config, C1633c c1633c) {
        this.f5028d = uri;
        this.f5029e = i;
        this.f5030f = str;
        if (list == null) {
            this.f5031g = null;
        } else {
            this.f5031g = Collections.unmodifiableList(list);
        }
        this.f5032h = i2;
        this.f5033i = i3;
        this.f5034j = z;
        this.f5035k = z2;
        this.f5036l = z3;
        this.f5037m = f;
        this.f5038n = f2;
        this.f5039o = f3;
        this.f5040p = z4;
        this.f5041q = config;
        this.f5042r = c1633c;
    }

    /* renamed from: a */
    String m8028a() {
        long nanoTime = System.nanoTime() - this.f5026b;
        return nanoTime > f5024s ? m8029b() + '+' + TimeUnit.NANOSECONDS.toSeconds(nanoTime) + 's' : m8029b() + '+' + TimeUnit.NANOSECONDS.toMillis(nanoTime) + "ms";
    }

    /* renamed from: b */
    String m8029b() {
        return "[R" + this.f5025a + ']';
    }

    /* renamed from: c */
    String m8030c() {
        return this.f5028d != null ? String.valueOf(this.f5028d.getPath()) : Integer.toHexString(this.f5029e);
    }

    /* renamed from: d */
    public boolean m8031d() {
        return (this.f5032h == 0 && this.f5033i == 0) ? false : true;
    }

    /* renamed from: e */
    boolean m8032e() {
        return m8033f() || m8034g();
    }

    /* renamed from: f */
    boolean m8033f() {
        return m8031d() || this.f5037m != BitmapDescriptorFactory.HUE_RED;
    }

    /* renamed from: g */
    boolean m8034g() {
        return this.f5031g != null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Request{");
        if (this.f5029e > 0) {
            stringBuilder.append(this.f5029e);
        } else {
            stringBuilder.append(this.f5028d);
        }
        if (!(this.f5031g == null || this.f5031g.isEmpty())) {
            for (C1646u a : this.f5031g) {
                stringBuilder.append(' ').append(a.m8053a());
            }
        }
        if (this.f5030f != null) {
            stringBuilder.append(" stableKey(").append(this.f5030f).append(')');
        }
        if (this.f5032h > 0) {
            stringBuilder.append(" resize(").append(this.f5032h).append(',').append(this.f5033i).append(')');
        }
        if (this.f5034j) {
            stringBuilder.append(" centerCrop");
        }
        if (this.f5035k) {
            stringBuilder.append(" centerInside");
        }
        if (this.f5037m != BitmapDescriptorFactory.HUE_RED) {
            stringBuilder.append(" rotation(").append(this.f5037m);
            if (this.f5040p) {
                stringBuilder.append(" @ ").append(this.f5038n).append(',').append(this.f5039o);
            }
            stringBuilder.append(')');
        }
        if (this.f5041q != null) {
            stringBuilder.append(' ').append(this.f5041q);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
