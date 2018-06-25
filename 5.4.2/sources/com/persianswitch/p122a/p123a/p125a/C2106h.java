package com.persianswitch.p122a.p123a.p125a;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2245f;
import com.persianswitch.p126b.C2253l;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.persianswitch.a.a.a.h */
final class C2106h {
    /* renamed from: a */
    private static final C2102f[] f6394a = new C2102f[]{new C2102f(C2102f.f6367e, TtmlNode.ANONYMOUS_REGION_ID), new C2102f(C2102f.f6364b, "GET"), new C2102f(C2102f.f6364b, "POST"), new C2102f(C2102f.f6365c, "/"), new C2102f(C2102f.f6365c, "/index.html"), new C2102f(C2102f.f6366d, "http"), new C2102f(C2102f.f6366d, "https"), new C2102f(C2102f.f6363a, "200"), new C2102f(C2102f.f6363a, "204"), new C2102f(C2102f.f6363a, "206"), new C2102f(C2102f.f6363a, "304"), new C2102f(C2102f.f6363a, "400"), new C2102f(C2102f.f6363a, "404"), new C2102f(C2102f.f6363a, "500"), new C2102f("accept-charset", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("accept-encoding", "gzip, deflate"), new C2102f("accept-language", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("accept-ranges", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("accept", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("access-control-allow-origin", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("age", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("allow", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("authorization", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("cache-control", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("content-disposition", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("content-encoding", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("content-language", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("content-length", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("content-location", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("content-range", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("content-type", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("cookie", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("date", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("etag", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("expect", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("expires", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("from", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("host", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("if-match", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("if-modified-since", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("if-none-match", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("if-range", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("if-unmodified-since", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("last-modified", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("link", TtmlNode.ANONYMOUS_REGION_ID), new C2102f(C1797b.LOCATION, TtmlNode.ANONYMOUS_REGION_ID), new C2102f("max-forwards", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("proxy-authenticate", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("proxy-authorization", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("range", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("referer", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("refresh", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("retry-after", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("server", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("set-cookie", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("strict-transport-security", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("transfer-encoding", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("user-agent", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("vary", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("via", TtmlNode.ANONYMOUS_REGION_ID), new C2102f("www-authenticate", TtmlNode.ANONYMOUS_REGION_ID)};
    /* renamed from: b */
    private static final Map<C2245f, Integer> f6395b = C2106h.m9494c();

    /* renamed from: com.persianswitch.a.a.a.h$a */
    static final class C2104a {
        /* renamed from: a */
        C2102f[] f6378a = new C2102f[8];
        /* renamed from: b */
        int f6379b = (this.f6378a.length - 1);
        /* renamed from: c */
        int f6380c = 0;
        /* renamed from: d */
        int f6381d = 0;
        /* renamed from: e */
        private final List<C2102f> f6382e = new ArrayList();
        /* renamed from: f */
        private final C2243e f6383f;
        /* renamed from: g */
        private int f6384g;
        /* renamed from: h */
        private int f6385h;

        C2104a(int i, C2096s c2096s) {
            this.f6384g = i;
            this.f6385h = i;
            this.f6383f = C2253l.m10358a(c2096s);
        }

        /* renamed from: a */
        private void m9465a(int i, C2102f c2102f) {
            this.f6382e.add(c2102f);
            int i2 = c2102f.f6372j;
            if (i != -1) {
                i2 -= this.f6378a[m9468d(i)].f6372j;
            }
            if (i2 > this.f6385h) {
                m9470e();
                return;
            }
            int b = m9466b((this.f6381d + i2) - this.f6385h);
            if (i == -1) {
                if (this.f6380c + 1 > this.f6378a.length) {
                    Object obj = new C2102f[(this.f6378a.length * 2)];
                    System.arraycopy(this.f6378a, 0, obj, this.f6378a.length, this.f6378a.length);
                    this.f6379b = this.f6378a.length - 1;
                    this.f6378a = obj;
                }
                b = this.f6379b;
                this.f6379b = b - 1;
                this.f6378a[b] = c2102f;
                this.f6380c++;
            } else {
                this.f6378a[(b + m9468d(i)) + i] = c2102f;
            }
            this.f6381d = i2 + this.f6381d;
        }

        /* renamed from: b */
        private int m9466b(int i) {
            int i2 = 0;
            if (i > 0) {
                for (int length = this.f6378a.length - 1; length >= this.f6379b && i > 0; length--) {
                    i -= this.f6378a[length].f6372j;
                    this.f6381d -= this.f6378a[length].f6372j;
                    this.f6380c--;
                    i2++;
                }
                System.arraycopy(this.f6378a, this.f6379b + 1, this.f6378a, (this.f6379b + 1) + i2, this.f6380c);
                this.f6379b += i2;
            }
            return i2;
        }

        /* renamed from: c */
        private void m9467c(int i) {
            if (m9477h(i)) {
                this.f6382e.add(C2106h.f6394a[i]);
                return;
            }
            int d = m9468d(i - C2106h.f6394a.length);
            if (d < 0 || d > this.f6378a.length - 1) {
                throw new IOException("Header index too large " + (i + 1));
            }
            this.f6382e.add(this.f6378a[d]);
        }

        /* renamed from: d */
        private int m9468d(int i) {
            return (this.f6379b + 1) + i;
        }

        /* renamed from: d */
        private void m9469d() {
            if (this.f6385h >= this.f6381d) {
                return;
            }
            if (this.f6385h == 0) {
                m9470e();
            } else {
                m9466b(this.f6381d - this.f6385h);
            }
        }

        /* renamed from: e */
        private void m9470e() {
            this.f6382e.clear();
            Arrays.fill(this.f6378a, null);
            this.f6379b = this.f6378a.length - 1;
            this.f6380c = 0;
            this.f6381d = 0;
        }

        /* renamed from: e */
        private void m9471e(int i) {
            this.f6382e.add(new C2102f(m9474g(i), m9482c()));
        }

        /* renamed from: f */
        private void m9472f() {
            this.f6382e.add(new C2102f(C2106h.m9492b(m9482c()), m9482c()));
        }

        /* renamed from: f */
        private void m9473f(int i) {
            m9465a(-1, new C2102f(m9474g(i), m9482c()));
        }

        /* renamed from: g */
        private C2245f m9474g(int i) {
            return m9477h(i) ? C2106h.f6394a[i].f6370h : this.f6378a[m9468d(i - C2106h.f6394a.length)].f6370h;
        }

        /* renamed from: g */
        private void m9475g() {
            m9465a(-1, new C2102f(C2106h.m9492b(m9482c()), m9482c()));
        }

        /* renamed from: h */
        private int m9476h() {
            return this.f6383f.mo3186h() & 255;
        }

        /* renamed from: h */
        private boolean m9477h(int i) {
            return i >= 0 && i <= C2106h.f6394a.length - 1;
        }

        /* renamed from: a */
        int m9478a(int i, int i2) {
            int i3 = i & i2;
            if (i3 < i2) {
                return i3;
            }
            i3 = 0;
            while (true) {
                int h = m9476h();
                if ((h & 128) == 0) {
                    return (h << i3) + i2;
                }
                i2 += (h & 127) << i3;
                i3 += 7;
            }
        }

        /* renamed from: a */
        void m9479a() {
            while (!this.f6383f.mo3181e()) {
                int h = this.f6383f.mo3186h() & 255;
                if (h == 128) {
                    throw new IOException("index == 0");
                } else if ((h & 128) == 128) {
                    m9467c(m9478a(h, 127) - 1);
                } else if (h == 64) {
                    m9475g();
                } else if ((h & 64) == 64) {
                    m9473f(m9478a(h, 63) - 1);
                } else if ((h & 32) == 32) {
                    this.f6385h = m9478a(h, 31);
                    if (this.f6385h < 0 || this.f6385h > this.f6384g) {
                        throw new IOException("Invalid dynamic table size update " + this.f6385h);
                    }
                    m9469d();
                } else if (h == 16 || h == 0) {
                    m9472f();
                } else {
                    m9471e(m9478a(h, 15) - 1);
                }
            }
        }

        /* renamed from: a */
        void m9480a(int i) {
            this.f6384g = i;
            this.f6385h = i;
            m9469d();
        }

        /* renamed from: b */
        public List<C2102f> m9481b() {
            List arrayList = new ArrayList(this.f6382e);
            this.f6382e.clear();
            return arrayList;
        }

        /* renamed from: c */
        C2245f m9482c() {
            int h = m9476h();
            Object obj = (h & 128) == 128 ? 1 : null;
            h = m9478a(h, 127);
            return obj != null ? C2245f.m10319a(C2114j.m9548a().m9551a(this.f6383f.mo3183f((long) h))) : this.f6383f.mo3180c((long) h);
        }
    }

    /* renamed from: com.persianswitch.a.a.a.h$b */
    static final class C2105b {
        /* renamed from: a */
        C2102f[] f6386a;
        /* renamed from: b */
        int f6387b;
        /* renamed from: c */
        int f6388c;
        /* renamed from: d */
        int f6389d;
        /* renamed from: e */
        private final C2244c f6390e;
        /* renamed from: f */
        private final Map<C2245f, Integer> f6391f;
        /* renamed from: g */
        private int f6392g;
        /* renamed from: h */
        private int f6393h;

        C2105b(int i, C2244c c2244c) {
            this.f6391f = new LinkedHashMap();
            this.f6386a = new C2102f[8];
            this.f6387b = this.f6386a.length - 1;
            this.f6388c = 0;
            this.f6389d = 0;
            this.f6392g = i;
            this.f6393h = i;
            this.f6390e = c2244c;
        }

        C2105b(C2244c c2244c) {
            this(4096, c2244c);
        }

        /* renamed from: a */
        private int m9483a(int i) {
            if (i <= 0) {
                return 0;
            }
            int length = this.f6386a.length - 1;
            int i2 = 0;
            while (length >= this.f6387b && i > 0) {
                i -= this.f6386a[length].f6372j;
                this.f6389d -= this.f6386a[length].f6372j;
                this.f6388c--;
                length--;
                i2++;
            }
            System.arraycopy(this.f6386a, this.f6387b + 1, this.f6386a, (this.f6387b + 1) + i2, this.f6388c);
            for (Entry entry : this.f6391f.entrySet()) {
                entry.setValue(Integer.valueOf(((Integer) entry.getValue()).intValue() + i2));
            }
            this.f6387b += i2;
            return i2;
        }

        /* renamed from: a */
        private void m9484a() {
            Arrays.fill(this.f6386a, null);
            this.f6391f.clear();
            this.f6387b = this.f6386a.length - 1;
            this.f6388c = 0;
            this.f6389d = 0;
        }

        /* renamed from: b */
        private void m9485b(C2102f c2102f) {
            int i = c2102f.f6372j;
            if (i > this.f6393h) {
                m9484a();
                return;
            }
            m9483a((this.f6389d + i) - this.f6393h);
            if (this.f6388c + 1 > this.f6386a.length) {
                Object obj = new C2102f[(this.f6386a.length * 2)];
                System.arraycopy(this.f6386a, 0, obj, this.f6386a.length, this.f6386a.length);
                for (Entry entry : this.f6391f.entrySet()) {
                    entry.setValue(Integer.valueOf(((Integer) entry.getValue()).intValue() + this.f6386a.length));
                }
                this.f6387b = this.f6386a.length - 1;
                this.f6386a = obj;
            }
            int i2 = this.f6387b;
            this.f6387b = i2 - 1;
            this.f6386a[i2] = c2102f;
            this.f6391f.put(m9486a(c2102f), Integer.valueOf(i2));
            this.f6388c++;
            this.f6389d += i;
        }

        /* renamed from: a */
        C2245f m9486a(C2102f c2102f) {
            byte[] bArr = new byte[((c2102f.f6370h.mo3216e() + 1) + c2102f.f6371i.mo3216e())];
            System.arraycopy(c2102f.f6370h.mo3218f(), 0, bArr, 0, c2102f.f6370h.mo3216e());
            bArr[c2102f.f6370h.mo3216e()] = (byte) 58;
            System.arraycopy(c2102f.f6371i.mo3218f(), 0, bArr, c2102f.f6370h.mo3216e() + 1, c2102f.f6371i.mo3216e());
            return C2245f.m10319a(bArr);
        }

        /* renamed from: a */
        void m9487a(int i, int i2, int i3) {
            if (i < i2) {
                this.f6390e.m10275b(i3 | i);
                return;
            }
            this.f6390e.m10275b(i3 | i2);
            int i4 = i - i2;
            while (i4 >= 128) {
                this.f6390e.m10275b((i4 & 127) | 128);
                i4 >>>= 7;
            }
            this.f6390e.m10275b(i4);
        }

        /* renamed from: a */
        void m9488a(C2245f c2245f) {
            m9487a(c2245f.mo3216e(), 127, 0);
            this.f6390e.m10266a(c2245f);
        }

        /* renamed from: a */
        void m9489a(List<C2102f> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                C2102f c2102f = (C2102f) list.get(i);
                C2245f d = c2102f.f6370h.mo3215d();
                C2245f c2245f = c2102f.f6371i;
                Integer num = (Integer) C2106h.f6395b.get(d);
                if (num != null) {
                    m9487a(num.intValue() + 1, 15, 0);
                    m9488a(c2245f);
                } else {
                    num = (Integer) this.f6391f.get(m9486a(c2102f));
                    if (num != null) {
                        m9487a((this.f6386a.length - num.intValue()) + C2106h.f6394a.length, 127, 128);
                    } else {
                        this.f6390e.m10275b(64);
                        m9488a(d);
                        m9488a(c2245f);
                        m9485b(c2102f);
                    }
                }
            }
        }
    }

    /* renamed from: b */
    private static C2245f m9492b(C2245f c2245f) {
        int i = 0;
        int e = c2245f.mo3216e();
        while (i < e) {
            byte a = c2245f.mo3207a(i);
            if (a < (byte) 65 || a > (byte) 90) {
                i++;
            } else {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + c2245f.mo3209a());
            }
        }
        return c2245f;
    }

    /* renamed from: c */
    private static Map<C2245f, Integer> m9494c() {
        Map linkedHashMap = new LinkedHashMap(f6394a.length);
        for (int i = 0; i < f6394a.length; i++) {
            if (!linkedHashMap.containsKey(f6394a[i].f6370h)) {
                linkedHashMap.put(f6394a[i].f6370h, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }
}
