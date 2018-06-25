package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2189a;
import com.persianswitch.p122a.C2201g;
import com.persianswitch.p122a.C2208l;
import com.persianswitch.p122a.C2209m;
import com.persianswitch.p122a.C2217q;
import com.persianswitch.p122a.C2217q.C2216a;
import com.persianswitch.p122a.C2221r;
import com.persianswitch.p122a.C2222t;
import com.persianswitch.p122a.C2225u;
import com.persianswitch.p122a.C2226v;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2231x.C2230a;
import com.persianswitch.p122a.C2236z;
import com.persianswitch.p122a.C2236z.C2235a;
import com.persianswitch.p122a.aa;
import com.persianswitch.p122a.p123a.C2179d;
import com.persianswitch.p122a.p123a.C2180e;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.C2188m;
import com.persianswitch.p122a.p123a.p127b.C2132b.C2131a;
import com.persianswitch.p126b.C2094r;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2098t;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2248j;
import com.persianswitch.p126b.C2253l;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: com.persianswitch.a.a.b.h */
public final class C2151h {
    /* renamed from: e */
    private static final aa f6518e = new C21491();
    /* renamed from: a */
    final C2225u f6519a;
    /* renamed from: b */
    final C2162s f6520b;
    /* renamed from: c */
    final C2236z f6521c;
    /* renamed from: d */
    final C2221r f6522d;
    /* renamed from: f */
    private boolean f6523f;

    /* renamed from: com.persianswitch.a.a.b.h$1 */
    static class C21491 extends aa {
        C21491() {
        }

        /* renamed from: a */
        public C2222t mo3143a() {
            return null;
        }

        /* renamed from: b */
        public long mo3144b() {
            return 0;
        }

        /* renamed from: d */
        public C2243e mo3145d() {
            return new C2244c();
        }
    }

    public C2151h(C2225u c2225u, C2221r c2221r, C2162s c2162s, C2236z c2236z) {
        this.f6519a = c2225u;
        this.f6522d = c2221r;
        if (c2162s == null) {
            c2162s = new C2162s(c2225u.m10120o(), C2151h.m9709a(c2225u, c2221r));
        }
        this.f6520b = c2162s;
        this.f6521c = c2236z;
    }

    /* renamed from: a */
    private C2129a m9707a(C2236z c2236z, C2231x c2231x, C2180e c2180e) {
        if (c2180e == null) {
            return null;
        }
        if (C2132b.m9638a(c2236z, c2231x)) {
            return c2180e.m9861a(c2236z);
        }
        if (!C2152i.m9721a(c2231x.m10159b())) {
            return null;
        }
        try {
            c2180e.m9866b(c2231x);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /* renamed from: a */
    private C2143j m9708a(C2231x c2231x) {
        return this.f6520b.m9773a(this.f6519a.m10105a(), this.f6519a.m10107b(), this.f6519a.m10108c(), this.f6519a.m10123r(), !c2231x.m10159b().equals("GET"));
    }

    /* renamed from: a */
    private static C2189a m9709a(C2225u c2225u, C2221r c2221r) {
        SSLSocketFactory j;
        HostnameVerifier k;
        C2201g c2201g = null;
        if (c2221r.m10071c()) {
            j = c2225u.m10115j();
            k = c2225u.m10116k();
            c2201g = c2225u.m10117l();
        } else {
            k = null;
            j = null;
        }
        return new C2189a(c2221r.m10075f(), c2221r.m10076g(), c2225u.m10113h(), c2225u.m10114i(), j, k, c2201g, c2225u.m10119n(), c2225u.m10109d(), c2225u.m10125t(), c2225u.m10126u(), c2225u.m10110e());
    }

    /* renamed from: a */
    private static C2217q m9710a(C2217q c2217q, C2217q c2217q2) {
        int i;
        int i2 = 0;
        C2216a c2216a = new C2216a();
        int a = c2217q.m10023a();
        for (i = 0; i < a; i++) {
            String a2 = c2217q.m10024a(i);
            String b = c2217q.m10027b(i);
            if (!("Warning".equalsIgnoreCase(a2) && b.startsWith("1")) && (!C2153k.m9727a(a2) || c2217q2.m10025a(a2) == null)) {
                C2179d.f6617a.mo3165a(c2216a, a2, b);
            }
        }
        i = c2217q2.m10023a();
        while (i2 < i) {
            String a3 = c2217q2.m10024a(i2);
            if (!"Content-Length".equalsIgnoreCase(a3) && C2153k.m9727a(a3)) {
                C2179d.f6617a.mo3165a(c2216a, a3, c2217q2.m10027b(i2));
            }
            i2++;
        }
        return c2216a.m10018a();
    }

    /* renamed from: a */
    private C2236z m9711a(final C2129a c2129a, C2236z c2236z) {
        if (c2129a == null) {
            return c2236z;
        }
        C2094r a = c2129a.m9630a();
        if (a == null) {
            return c2236z;
        }
        final C2243e d = c2236z.m10221f().mo3145d();
        final C2242d a2 = C2253l.m10357a(a);
        return c2236z.m10222g().m10190a(new C2156m(c2236z.m10220e(), C2253l.m10358a(new C2096s(this) {
            /* renamed from: a */
            boolean f6513a;
            /* renamed from: e */
            final /* synthetic */ C2151h f6517e;

            /* renamed from: a */
            public long mo3105a(C2244c c2244c, long j) {
                try {
                    long a = d.mo3105a(c2244c, j);
                    if (a == -1) {
                        if (!this.f6513a) {
                            this.f6513a = true;
                            a2.close();
                        }
                        return -1;
                    }
                    c2244c.m10265a(a2.mo3177c(), c2244c.m10274b() - a, a);
                    a2.mo3198u();
                    return a;
                } catch (IOException e) {
                    if (!this.f6513a) {
                        this.f6513a = true;
                        c2129a.m9631b();
                    }
                    throw e;
                }
            }

            /* renamed from: a */
            public C2098t mo3106a() {
                return d.mo3106a();
            }

            public void close() {
                if (!(this.f6513a || C2187l.m9901a((C2096s) this, 100, TimeUnit.MILLISECONDS))) {
                    this.f6513a = true;
                    c2129a.m9631b();
                }
                d.close();
            }
        }))).m10198a();
    }

    /* renamed from: a */
    private String m9712a(List<C2208l> list) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append("; ");
            }
            C2208l c2208l = (C2208l) list.get(i);
            stringBuilder.append(c2208l.m9995a()).append('=').append(c2208l.m9996b());
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    private static boolean m9713a(C2236z c2236z, C2236z c2236z2) {
        if (c2236z2.m10217b() == 304) {
            return true;
        }
        Date b = c2236z.m10220e().m10028b("Last-Modified");
        if (b != null) {
            Date b2 = c2236z2.m10220e().m10028b("Last-Modified");
            if (b2 != null && b2.getTime() < b.getTime()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private C2231x m9714b(C2231x c2231x) {
        C2230a e = c2231x.m10162e();
        if (c2231x.m10158a("Host") == null) {
            e.m10149a("Host", C2187l.m9890a(c2231x.m10157a(), false));
        }
        if (c2231x.m10158a("Connection") == null) {
            e.m10149a("Connection", "Keep-Alive");
        }
        if (c2231x.m10158a("Accept-Encoding") == null) {
            this.f6523f = true;
            e.m10149a("Accept-Encoding", "gzip");
        }
        List a = this.f6519a.m10111f().mo3157a(c2231x.m10157a());
        if (!a.isEmpty()) {
            e.m10149a("Cookie", m9712a(a));
        }
        if (c2231x.m10158a("User-Agent") == null) {
            e.m10149a("User-Agent", C2188m.m9913a());
        }
        return e.m10150a();
    }

    /* renamed from: b */
    public static boolean m9715b(C2236z c2236z) {
        if (c2236z.m10214a().m10159b().equals("HEAD")) {
            return false;
        }
        int b = c2236z.m10217b();
        return ((b >= 100 && b < Callback.DEFAULT_DRAG_ANIMATION_DURATION) || b == 204 || b == 304) ? C2153k.m9726a(c2236z) != -1 || "chunked".equalsIgnoreCase(c2236z.m10215a("Transfer-Encoding")) : true;
    }

    /* renamed from: c */
    private static C2236z m9716c(C2236z c2236z) {
        return (c2236z == null || c2236z.m10221f() == null) ? c2236z : c2236z.m10222g().m10190a(null).m10198a();
    }

    /* renamed from: d */
    private C2236z m9717d(C2236z c2236z) {
        if (!this.f6523f || !"gzip".equalsIgnoreCase(c2236z.m10215a("Content-Encoding")) || c2236z.m10221f() == null) {
            return c2236z;
        }
        C2096s c2248j = new C2248j(c2236z.m10221f().mo3145d());
        C2217q a = c2236z.m10220e().m10026b().m10019b("Content-Encoding").m10019b("Content-Length").m10018a();
        return c2236z.m10222g().m10192a(a).m10190a(new C2156m(a, C2253l.m10358a(c2248j))).m10198a();
    }

    /* renamed from: a */
    public C2162s m9718a(C2236z c2236z) {
        if (c2236z != null) {
            C2187l.m9898a(c2236z.m10221f());
        } else {
            this.f6520b.m9775a(null);
        }
        return this.f6520b;
    }

    /* renamed from: a */
    public C2236z m9719a(C2231x c2231x, C2155l c2155l) {
        C2143j a;
        Throwable th;
        C2231x b = m9714b(c2231x);
        C2180e a2 = C2179d.f6617a.mo3161a(this.f6519a);
        C2236z a3 = a2 != null ? a2.m9862a(b) : null;
        C2132b a4 = new C2131a(System.currentTimeMillis(), b, a3).m9637a();
        C2231x c2231x2 = a4.f6467a;
        C2236z c2236z = a4.f6468b;
        if (a2 != null) {
            a2.m9864a(a4);
        }
        if (a3 != null && c2236z == null) {
            C2187l.m9898a(a3.m10221f());
        }
        if (c2231x2 == null && c2236z == null) {
            return new C2235a().m10194a(c2231x).m10201c(C2151h.m9716c(this.f6521c)).m10193a(C2226v.HTTP_1_1).m10188a(504).m10196a("Unsatisfiable Request (only-if-cached)").m10190a(f6518e).m10189a(-1).m10199b(System.currentTimeMillis()).m10198a();
        }
        if (c2231x2 == null) {
            return m9717d(c2236z.m10222g().m10194a(c2231x).m10201c(C2151h.m9716c(this.f6521c)).m10200b(C2151h.m9716c(c2236z)).m10198a());
        }
        try {
            a = m9708a(c2231x2);
            try {
                a.mo3140a(this);
                if (a == null && a3 != null) {
                    C2187l.m9898a(a3.m10221f());
                }
                C2236z a5 = c2155l.m9734a(c2231x2, this.f6520b.m9777b(), this.f6520b, a);
                m9720a(a5.m10220e());
                if (c2236z != null) {
                    if (C2151h.m9713a(c2236z, a5)) {
                        a3 = c2236z.m10222g().m10194a(c2231x).m10201c(C2151h.m9716c(this.f6521c)).m10192a(C2151h.m9710a(c2236z.m10220e(), a5.m10220e())).m10200b(C2151h.m9716c(c2236z)).m10195a(C2151h.m9716c(a5)).m10198a();
                        a5.m10221f().close();
                        this.f6520b.m9778c();
                        a2.m9863a();
                        a2.m9865a(c2236z, a3);
                        return m9717d(a3);
                    }
                    C2187l.m9898a(c2236z.m10221f());
                }
                a3 = a5.m10222g().m10194a(c2231x).m10201c(C2151h.m9716c(this.f6521c)).m10200b(C2151h.m9716c(c2236z)).m10195a(C2151h.m9716c(a5)).m10198a();
                return C2151h.m9715b(a3) ? m9717d(m9711a(m9707a(a3, a5.m10214a(), a2), a3)) : a3;
            } catch (Throwable th2) {
                th = th2;
                C2187l.m9898a(a3.m10221f());
                throw th;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            a = null;
            th = th4;
            if (a == null && a3 != null) {
                C2187l.m9898a(a3.m10221f());
            }
            throw th;
        }
    }

    /* renamed from: a */
    public void m9720a(C2217q c2217q) {
        if (this.f6519a.m10111f() != C2209m.f6777a) {
            List a = C2208l.m9992a(this.f6522d, c2217q);
            if (!a.isEmpty()) {
                this.f6519a.m10111f().mo3158a(this.f6522d, a);
            }
        }
    }
}
