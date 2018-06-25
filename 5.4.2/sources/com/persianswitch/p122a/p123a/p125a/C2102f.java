package com.persianswitch.p122a.p123a.p125a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p126b.C2245f;

/* renamed from: com.persianswitch.a.a.a.f */
public final class C2102f {
    /* renamed from: a */
    public static final C2245f f6363a = C2245f.m10318a(":status");
    /* renamed from: b */
    public static final C2245f f6364b = C2245f.m10318a(":method");
    /* renamed from: c */
    public static final C2245f f6365c = C2245f.m10318a(":path");
    /* renamed from: d */
    public static final C2245f f6366d = C2245f.m10318a(":scheme");
    /* renamed from: e */
    public static final C2245f f6367e = C2245f.m10318a(":authority");
    /* renamed from: f */
    public static final C2245f f6368f = C2245f.m10318a(":host");
    /* renamed from: g */
    public static final C2245f f6369g = C2245f.m10318a(":version");
    /* renamed from: h */
    public final C2245f f6370h;
    /* renamed from: i */
    public final C2245f f6371i;
    /* renamed from: j */
    final int f6372j;

    public C2102f(C2245f c2245f, C2245f c2245f2) {
        this.f6370h = c2245f;
        this.f6371i = c2245f2;
        this.f6372j = (c2245f.mo3216e() + 32) + c2245f2.mo3216e();
    }

    public C2102f(C2245f c2245f, String str) {
        this(c2245f, C2245f.m10318a(str));
    }

    public C2102f(String str, String str2) {
        this(C2245f.m10318a(str), C2245f.m10318a(str2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C2102f)) {
            return false;
        }
        C2102f c2102f = (C2102f) obj;
        return this.f6370h.equals(c2102f.f6370h) && this.f6371i.equals(c2102f.f6371i);
    }

    public int hashCode() {
        return ((this.f6370h.hashCode() + 527) * 31) + this.f6371i.hashCode();
    }

    public String toString() {
        return C2187l.m9892a("%s: %s", this.f6370h.mo3209a(), this.f6371i.mo3209a());
    }
}
