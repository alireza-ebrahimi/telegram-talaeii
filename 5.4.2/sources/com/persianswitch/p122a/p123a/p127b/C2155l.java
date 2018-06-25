package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2133s;
import com.persianswitch.p122a.C2133s.C2154a;
import com.persianswitch.p122a.C2170i;
import com.persianswitch.p122a.C2221r;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2236z;
import java.util.List;

/* renamed from: com.persianswitch.a.a.b.l */
public final class C2155l implements C2154a {
    /* renamed from: a */
    private final List<C2133s> f6524a;
    /* renamed from: b */
    private final C2170i f6525b;
    /* renamed from: c */
    private final C2162s f6526c;
    /* renamed from: d */
    private final C2143j f6527d;
    /* renamed from: e */
    private final int f6528e;
    /* renamed from: f */
    private final C2231x f6529f;
    /* renamed from: g */
    private int f6530g;

    public C2155l(List<C2133s> list, C2170i c2170i, C2162s c2162s, C2143j c2143j, int i, C2231x c2231x) {
        this.f6524a = list;
        this.f6525b = c2170i;
        this.f6526c = c2162s;
        this.f6527d = c2143j;
        this.f6528e = i;
        this.f6529f = c2231x;
    }

    /* renamed from: a */
    private boolean m9731a(C2221r c2221r) {
        return c2221r.m10075f().equals(m9735b().mo3152a().m9925a().m9914a().m10075f()) && c2221r.m10076g() == m9735b().mo3152a().m9925a().m9914a().m10076g();
    }

    /* renamed from: a */
    public C2231x mo3146a() {
        return this.f6529f;
    }

    /* renamed from: a */
    public C2236z mo3147a(C2231x c2231x) {
        return m9734a(c2231x, this.f6525b, this.f6526c, this.f6527d);
    }

    /* renamed from: a */
    public C2236z m9734a(C2231x c2231x, C2170i c2170i, C2162s c2162s, C2143j c2143j) {
        if (this.f6528e >= this.f6524a.size()) {
            throw new AssertionError();
        }
        this.f6530g++;
        if (this.f6525b != null && !m9731a(c2231x.m10157a())) {
            throw new IllegalStateException("network interceptor " + this.f6524a.get(this.f6528e - 1) + " must retain the same host and port");
        } else if (this.f6525b == null || this.f6530g <= 1) {
            Object c2155l = new C2155l(this.f6524a, c2170i, c2162s, c2143j, this.f6528e + 1, c2231x);
            C2133s c2133s = (C2133s) this.f6524a.get(this.f6528e);
            C2236z a = c2133s.mo3136a(c2155l);
            if (c2170i != null && this.f6528e + 1 < this.f6524a.size() && c2155l.f6530g != 1) {
                throw new IllegalStateException("network interceptor " + c2133s + " must call proceed() exactly once");
            } else if (a != null) {
                return a;
            } else {
                throw new NullPointerException("interceptor " + c2133s + " returned null");
            }
        } else {
            throw new IllegalStateException("network interceptor " + this.f6524a.get(this.f6528e - 1) + " must call proceed() exactly once");
        }
    }

    /* renamed from: b */
    public C2170i m9735b() {
        return this.f6525b;
    }

    /* renamed from: c */
    public C2162s m9736c() {
        return this.f6526c;
    }

    /* renamed from: d */
    public C2143j m9737d() {
        return this.f6527d;
    }
}
