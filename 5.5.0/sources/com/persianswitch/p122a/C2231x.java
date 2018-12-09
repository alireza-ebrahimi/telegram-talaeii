package com.persianswitch.p122a;

import com.persianswitch.p122a.C2217q.C2216a;
import com.persianswitch.p122a.p123a.p127b.C2152i;

/* renamed from: com.persianswitch.a.x */
public final class C2231x {
    /* renamed from: a */
    private final C2221r f6892a;
    /* renamed from: b */
    private final String f6893b;
    /* renamed from: c */
    private final C2217q f6894c;
    /* renamed from: d */
    private final C2232y f6895d;
    /* renamed from: e */
    private final Object f6896e;
    /* renamed from: f */
    private volatile C2195d f6897f;

    /* renamed from: com.persianswitch.a.x$a */
    public static class C2230a {
        /* renamed from: a */
        private C2221r f6887a;
        /* renamed from: b */
        private String f6888b;
        /* renamed from: c */
        private C2216a f6889c;
        /* renamed from: d */
        private C2232y f6890d;
        /* renamed from: e */
        private Object f6891e;

        public C2230a() {
            this.f6888b = "GET";
            this.f6889c = new C2216a();
        }

        private C2230a(C2231x c2231x) {
            this.f6887a = c2231x.f6892a;
            this.f6888b = c2231x.f6893b;
            this.f6890d = c2231x.f6895d;
            this.f6891e = c2231x.f6896e;
            this.f6889c = c2231x.f6894c.m10026b();
        }

        /* renamed from: a */
        public C2230a m10145a(C2221r c2221r) {
            if (c2221r == null) {
                throw new NullPointerException("url == null");
            }
            this.f6887a = c2221r;
            return this;
        }

        /* renamed from: a */
        public C2230a m10146a(C2232y c2232y) {
            return m10148a("POST", c2232y);
        }

        /* renamed from: a */
        public C2230a m10147a(String str) {
            if (str == null) {
                throw new NullPointerException("url == null");
            }
            if (str.regionMatches(true, 0, "ws:", 0, 3)) {
                str = "http:" + str.substring(3);
            } else {
                if (str.regionMatches(true, 0, "wss:", 0, 4)) {
                    str = "https:" + str.substring(4);
                }
            }
            C2221r e = C2221r.m10067e(str);
            if (e != null) {
                return m10145a(e);
            }
            throw new IllegalArgumentException("unexpected url: " + str);
        }

        /* renamed from: a */
        public C2230a m10148a(String str, C2232y c2232y) {
            if (str == null) {
                throw new NullPointerException("method == null");
            } else if (str.length() == 0) {
                throw new IllegalArgumentException("method.length() == 0");
            } else if (c2232y != null && !C2152i.m9723c(str)) {
                throw new IllegalArgumentException("method " + str + " must not have a request body.");
            } else if (c2232y == null && C2152i.m9722b(str)) {
                throw new IllegalArgumentException("method " + str + " must have a request body.");
            } else {
                this.f6888b = str;
                this.f6890d = c2232y;
                return this;
            }
        }

        /* renamed from: a */
        public C2230a m10149a(String str, String str2) {
            this.f6889c.m10021c(str, str2);
            return this;
        }

        /* renamed from: a */
        public C2231x m10150a() {
            if (this.f6887a != null) {
                return new C2231x();
            }
            throw new IllegalStateException("url == null");
        }

        /* renamed from: b */
        public C2230a m10151b(String str) {
            this.f6889c.m10019b(str);
            return this;
        }
    }

    private C2231x(C2230a c2230a) {
        Object e;
        this.f6892a = c2230a.f6887a;
        this.f6893b = c2230a.f6888b;
        this.f6894c = c2230a.f6889c.m10018a();
        this.f6895d = c2230a.f6890d;
        if (c2230a.f6891e != null) {
            e = c2230a.f6891e;
        } else {
            C2231x c2231x = this;
        }
        this.f6896e = e;
    }

    /* renamed from: a */
    public C2221r m10157a() {
        return this.f6892a;
    }

    /* renamed from: a */
    public String m10158a(String str) {
        return this.f6894c.m10025a(str);
    }

    /* renamed from: b */
    public String m10159b() {
        return this.f6893b;
    }

    /* renamed from: c */
    public C2217q m10160c() {
        return this.f6894c;
    }

    /* renamed from: d */
    public C2232y m10161d() {
        return this.f6895d;
    }

    /* renamed from: e */
    public C2230a m10162e() {
        return new C2230a();
    }

    /* renamed from: f */
    public C2195d m10163f() {
        C2195d c2195d = this.f6897f;
        if (c2195d != null) {
            return c2195d;
        }
        c2195d = C2195d.m9936a(this.f6894c);
        this.f6897f = c2195d;
        return c2195d;
    }

    /* renamed from: g */
    public boolean m10164g() {
        return this.f6892a.m10071c();
    }

    public String toString() {
        return "Request{method=" + this.f6893b + ", url=" + this.f6892a + ", tag=" + (this.f6896e != this ? this.f6896e : null) + '}';
    }
}
