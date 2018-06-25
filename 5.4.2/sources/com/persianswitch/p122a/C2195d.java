package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.p127b.C2135d;
import java.util.concurrent.TimeUnit;

/* renamed from: com.persianswitch.a.d */
public final class C2195d {
    /* renamed from: a */
    public static final C2195d f6669a = new C2194a().m9932a().m9935c();
    /* renamed from: b */
    public static final C2195d f6670b = new C2194a().m9934b().m9933a(Integer.MAX_VALUE, TimeUnit.SECONDS).m9935c();
    /* renamed from: c */
    String f6671c;
    /* renamed from: d */
    private final boolean f6672d;
    /* renamed from: e */
    private final boolean f6673e;
    /* renamed from: f */
    private final int f6674f;
    /* renamed from: g */
    private final int f6675g;
    /* renamed from: h */
    private final boolean f6676h;
    /* renamed from: i */
    private final boolean f6677i;
    /* renamed from: j */
    private final boolean f6678j;
    /* renamed from: k */
    private final int f6679k;
    /* renamed from: l */
    private final int f6680l;
    /* renamed from: m */
    private final boolean f6681m;
    /* renamed from: n */
    private final boolean f6682n;

    /* renamed from: com.persianswitch.a.d$a */
    public static final class C2194a {
        /* renamed from: a */
        boolean f6662a;
        /* renamed from: b */
        boolean f6663b;
        /* renamed from: c */
        int f6664c = -1;
        /* renamed from: d */
        int f6665d = -1;
        /* renamed from: e */
        int f6666e = -1;
        /* renamed from: f */
        boolean f6667f;
        /* renamed from: g */
        boolean f6668g;

        /* renamed from: a */
        public C2194a m9932a() {
            this.f6662a = true;
            return this;
        }

        /* renamed from: a */
        public C2194a m9933a(int i, TimeUnit timeUnit) {
            if (i < 0) {
                throw new IllegalArgumentException("maxStale < 0: " + i);
            }
            long toSeconds = timeUnit.toSeconds((long) i);
            this.f6665d = toSeconds > 2147483647L ? Integer.MAX_VALUE : (int) toSeconds;
            return this;
        }

        /* renamed from: b */
        public C2194a m9934b() {
            this.f6667f = true;
            return this;
        }

        /* renamed from: c */
        public C2195d m9935c() {
            return new C2195d();
        }
    }

    private C2195d(C2194a c2194a) {
        this.f6672d = c2194a.f6662a;
        this.f6673e = c2194a.f6663b;
        this.f6674f = c2194a.f6664c;
        this.f6675g = -1;
        this.f6676h = false;
        this.f6677i = false;
        this.f6678j = false;
        this.f6679k = c2194a.f6665d;
        this.f6680l = c2194a.f6666e;
        this.f6681m = c2194a.f6667f;
        this.f6682n = c2194a.f6668g;
    }

    private C2195d(boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, boolean z5, int i3, int i4, boolean z6, boolean z7, String str) {
        this.f6672d = z;
        this.f6673e = z2;
        this.f6674f = i;
        this.f6675g = i2;
        this.f6676h = z3;
        this.f6677i = z4;
        this.f6678j = z5;
        this.f6679k = i3;
        this.f6680l = i4;
        this.f6681m = z6;
        this.f6682n = z7;
        this.f6671c = str;
    }

    /* renamed from: a */
    public static C2195d m9936a(C2217q c2217q) {
        boolean z = false;
        int i = -1;
        int i2 = -1;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        int i3 = -1;
        int i4 = -1;
        boolean z5 = false;
        boolean z6 = false;
        Object obj = 1;
        int a = c2217q.m10023a();
        int i5 = 0;
        String str = null;
        boolean z7 = false;
        while (i5 < a) {
            boolean z8;
            String a2 = c2217q.m10024a(i5);
            String b = c2217q.m10027b(i5);
            if (a2.equalsIgnoreCase("Cache-Control")) {
                if (str != null) {
                    obj = null;
                } else {
                    str = b;
                }
            } else if (a2.equalsIgnoreCase("Pragma")) {
                obj = null;
            } else {
                z8 = z7;
                i5++;
                z7 = z8;
            }
            z8 = z7;
            int i6 = 0;
            while (i6 < b.length()) {
                String str2;
                int a3 = C2135d.m9642a(b, i6, "=,;");
                String trim = b.substring(i6, a3).trim();
                if (a3 == b.length() || b.charAt(a3) == ',' || b.charAt(a3) == ';') {
                    i6 = a3 + 1;
                    str2 = null;
                } else {
                    i6 = C2135d.m9641a(b, a3 + 1);
                    String trim2;
                    if (i6 >= b.length() || b.charAt(i6) != '\"') {
                        a3 = C2135d.m9642a(b, i6, ",;");
                        trim2 = b.substring(i6, a3).trim();
                        i6 = a3;
                        str2 = trim2;
                    } else {
                        i6++;
                        a3 = C2135d.m9642a(b, i6, "\"");
                        trim2 = b.substring(i6, a3);
                        i6 = a3 + 1;
                        str2 = trim2;
                    }
                }
                if ("no-cache".equalsIgnoreCase(trim)) {
                    z8 = true;
                } else if ("no-store".equalsIgnoreCase(trim)) {
                    z = true;
                } else if ("max-age".equalsIgnoreCase(trim)) {
                    i = C2135d.m9643b(str2, -1);
                } else if ("s-maxage".equalsIgnoreCase(trim)) {
                    i2 = C2135d.m9643b(str2, -1);
                } else if ("private".equalsIgnoreCase(trim)) {
                    z2 = true;
                } else if ("public".equalsIgnoreCase(trim)) {
                    z3 = true;
                } else if ("must-revalidate".equalsIgnoreCase(trim)) {
                    z4 = true;
                } else if ("max-stale".equalsIgnoreCase(trim)) {
                    i3 = C2135d.m9643b(str2, Integer.MAX_VALUE);
                } else if ("min-fresh".equalsIgnoreCase(trim)) {
                    i4 = C2135d.m9643b(str2, -1);
                } else if ("only-if-cached".equalsIgnoreCase(trim)) {
                    z5 = true;
                } else if ("no-transform".equalsIgnoreCase(trim)) {
                    z6 = true;
                }
            }
            i5++;
            z7 = z8;
        }
        return new C2195d(z7, z, i, i2, z2, z3, z4, i3, i4, z5, z6, obj == null ? null : str);
    }

    /* renamed from: j */
    private String m9937j() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.f6672d) {
            stringBuilder.append("no-cache, ");
        }
        if (this.f6673e) {
            stringBuilder.append("no-store, ");
        }
        if (this.f6674f != -1) {
            stringBuilder.append("max-age=").append(this.f6674f).append(", ");
        }
        if (this.f6675g != -1) {
            stringBuilder.append("s-maxage=").append(this.f6675g).append(", ");
        }
        if (this.f6676h) {
            stringBuilder.append("private, ");
        }
        if (this.f6677i) {
            stringBuilder.append("public, ");
        }
        if (this.f6678j) {
            stringBuilder.append("must-revalidate, ");
        }
        if (this.f6679k != -1) {
            stringBuilder.append("max-stale=").append(this.f6679k).append(", ");
        }
        if (this.f6680l != -1) {
            stringBuilder.append("min-fresh=").append(this.f6680l).append(", ");
        }
        if (this.f6681m) {
            stringBuilder.append("only-if-cached, ");
        }
        if (this.f6682n) {
            stringBuilder.append("no-transform, ");
        }
        if (stringBuilder.length() == 0) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    /* renamed from: a */
    public boolean m9938a() {
        return this.f6672d;
    }

    /* renamed from: b */
    public boolean m9939b() {
        return this.f6673e;
    }

    /* renamed from: c */
    public int m9940c() {
        return this.f6674f;
    }

    /* renamed from: d */
    public boolean m9941d() {
        return this.f6676h;
    }

    /* renamed from: e */
    public boolean m9942e() {
        return this.f6677i;
    }

    /* renamed from: f */
    public boolean m9943f() {
        return this.f6678j;
    }

    /* renamed from: g */
    public int m9944g() {
        return this.f6679k;
    }

    /* renamed from: h */
    public int m9945h() {
        return this.f6680l;
    }

    /* renamed from: i */
    public boolean m9946i() {
        return this.f6681m;
    }

    public String toString() {
        String str = this.f6671c;
        if (str != null) {
            return str;
        }
        str = m9937j();
        this.f6671c = str;
        return str;
    }
}
