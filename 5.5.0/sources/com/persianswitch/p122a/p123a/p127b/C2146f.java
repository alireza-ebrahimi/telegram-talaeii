package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2217q;
import com.persianswitch.p122a.C2217q.C2216a;
import com.persianswitch.p122a.C2226v;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2236z;
import com.persianswitch.p122a.C2236z.C2235a;
import com.persianswitch.p122a.aa;
import com.persianswitch.p122a.p123a.C2179d;
import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p125a.C2092d;
import com.persianswitch.p122a.p123a.p125a.C2101e;
import com.persianswitch.p122a.p123a.p125a.C2102f;
import com.persianswitch.p126b.C2094r;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2115h;
import com.persianswitch.p126b.C2245f;
import com.persianswitch.p126b.C2253l;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* renamed from: com.persianswitch.a.a.b.f */
public final class C2146f implements C2143j {
    /* renamed from: a */
    private static final C2245f f6494a = C2245f.m10318a("connection");
    /* renamed from: b */
    private static final C2245f f6495b = C2245f.m10318a("host");
    /* renamed from: c */
    private static final C2245f f6496c = C2245f.m10318a("keep-alive");
    /* renamed from: d */
    private static final C2245f f6497d = C2245f.m10318a("proxy-connection");
    /* renamed from: e */
    private static final C2245f f6498e = C2245f.m10318a("transfer-encoding");
    /* renamed from: f */
    private static final C2245f f6499f = C2245f.m10318a("te");
    /* renamed from: g */
    private static final C2245f f6500g = C2245f.m10318a("encoding");
    /* renamed from: h */
    private static final C2245f f6501h = C2245f.m10318a("upgrade");
    /* renamed from: i */
    private static final List<C2245f> f6502i = C2187l.m9894a(f6494a, f6495b, f6496c, f6497d, f6498e, C2102f.f6364b, C2102f.f6365c, C2102f.f6366d, C2102f.f6367e, C2102f.f6368f, C2102f.f6369g);
    /* renamed from: j */
    private static final List<C2245f> f6503j = C2187l.m9894a(f6494a, f6495b, f6496c, f6497d, f6498e);
    /* renamed from: k */
    private static final List<C2245f> f6504k = C2187l.m9894a(f6494a, f6495b, f6496c, f6497d, f6499f, f6498e, f6500g, f6501h, C2102f.f6364b, C2102f.f6365c, C2102f.f6366d, C2102f.f6367e, C2102f.f6368f, C2102f.f6369g);
    /* renamed from: l */
    private static final List<C2245f> f6505l = C2187l.m9894a(f6494a, f6495b, f6496c, f6497d, f6499f, f6498e, f6500g, f6501h);
    /* renamed from: m */
    private final C2162s f6506m;
    /* renamed from: n */
    private final C2092d f6507n;
    /* renamed from: o */
    private C2151h f6508o;
    /* renamed from: p */
    private C2101e f6509p;

    /* renamed from: com.persianswitch.a.a.b.f$a */
    class C2145a extends C2115h {
        /* renamed from: a */
        final /* synthetic */ C2146f f6493a;

        public C2145a(C2146f c2146f, C2096s c2096s) {
            this.f6493a = c2146f;
            super(c2096s);
        }

        public void close() {
            this.f6493a.f6506m.m9776a(false, this.f6493a);
            super.close();
        }
    }

    public C2146f(C2162s c2162s, C2092d c2092d) {
        this.f6506m = c2162s;
        this.f6507n = c2092d;
    }

    /* renamed from: a */
    public static C2235a m9681a(List<C2102f> list) {
        String str = null;
        String str2 = "HTTP/1.1";
        C2216a c2216a = new C2216a();
        int size = list.size();
        int i = 0;
        while (i < size) {
            C2245f c2245f = ((C2102f) list.get(i)).f6370h;
            String a = ((C2102f) list.get(i)).f6371i.mo3209a();
            String str3 = str2;
            int i2 = 0;
            while (i2 < a.length()) {
                int indexOf = a.indexOf(0, i2);
                if (indexOf == -1) {
                    indexOf = a.length();
                }
                str2 = a.substring(i2, indexOf);
                if (!c2245f.equals(C2102f.f6363a)) {
                    if (c2245f.equals(C2102f.f6369g)) {
                        str3 = str2;
                        str2 = str;
                    } else {
                        if (!f6503j.contains(c2245f)) {
                            C2179d.f6617a.mo3165a(c2216a, c2245f.mo3209a(), str2);
                        }
                        str2 = str;
                    }
                }
                str = str2;
                i2 = indexOf + 1;
            }
            i++;
            str2 = str3;
        }
        if (str == null) {
            throw new ProtocolException("Expected ':status' header not present");
        }
        C2161r a2 = C2161r.m9766a(str2 + " " + str);
        return new C2235a().m10193a(C2226v.SPDY_3).m10188a(a2.f6549b).m10196a(a2.f6550c).m10192a(c2216a.m10018a());
    }

    /* renamed from: a */
    private static String m9682a(String str, String str2) {
        return '\u0000' + str2;
    }

    /* renamed from: b */
    public static C2235a m9683b(List<C2102f> list) {
        String str = null;
        C2216a c2216a = new C2216a();
        int size = list.size();
        int i = 0;
        while (i < size) {
            C2245f c2245f = ((C2102f) list.get(i)).f6370h;
            String a = ((C2102f) list.get(i)).f6371i.mo3209a();
            if (!c2245f.equals(C2102f.f6363a)) {
                if (!f6505l.contains(c2245f)) {
                    C2179d.f6617a.mo3165a(c2216a, c2245f.mo3209a(), a);
                }
                a = str;
            }
            i++;
            str = a;
        }
        if (str == null) {
            throw new ProtocolException("Expected ':status' header not present");
        }
        C2161r a2 = C2161r.m9766a("HTTP/1.1 " + str);
        return new C2235a().m10193a(C2226v.HTTP_2).m10188a(a2.f6549b).m10196a(a2.f6550c).m10192a(c2216a.m10018a());
    }

    /* renamed from: b */
    public static List<C2102f> m9684b(C2231x c2231x) {
        C2217q c = c2231x.m10160c();
        List<C2102f> arrayList = new ArrayList(c.m10023a() + 5);
        arrayList.add(new C2102f(C2102f.f6364b, c2231x.m10159b()));
        arrayList.add(new C2102f(C2102f.f6365c, C2157n.m9741a(c2231x.m10157a())));
        arrayList.add(new C2102f(C2102f.f6369g, "HTTP/1.1"));
        arrayList.add(new C2102f(C2102f.f6368f, C2187l.m9890a(c2231x.m10157a(), false)));
        arrayList.add(new C2102f(C2102f.f6366d, c2231x.m10157a().m10069b()));
        Set linkedHashSet = new LinkedHashSet();
        int a = c.m10023a();
        for (int i = 0; i < a; i++) {
            C2245f a2 = C2245f.m10318a(c.m10024a(i).toLowerCase(Locale.US));
            if (!f6502i.contains(a2)) {
                String b = c.m10027b(i);
                if (linkedHashSet.add(a2)) {
                    arrayList.add(new C2102f(a2, b));
                } else {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        if (((C2102f) arrayList.get(i2)).f6370h.equals(a2)) {
                            arrayList.set(i2, new C2102f(a2, C2146f.m9682a(((C2102f) arrayList.get(i2)).f6371i.mo3209a(), b)));
                            break;
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    /* renamed from: c */
    public static List<C2102f> m9685c(C2231x c2231x) {
        int i = 0;
        C2217q c = c2231x.m10160c();
        List<C2102f> arrayList = new ArrayList(c.m10023a() + 4);
        arrayList.add(new C2102f(C2102f.f6364b, c2231x.m10159b()));
        arrayList.add(new C2102f(C2102f.f6365c, C2157n.m9741a(c2231x.m10157a())));
        arrayList.add(new C2102f(C2102f.f6367e, C2187l.m9890a(c2231x.m10157a(), false)));
        arrayList.add(new C2102f(C2102f.f6366d, c2231x.m10157a().m10069b()));
        int a = c.m10023a();
        while (i < a) {
            C2245f a2 = C2245f.m10318a(c.m10024a(i).toLowerCase(Locale.US));
            if (!f6504k.contains(a2)) {
                arrayList.add(new C2102f(a2, c.m10027b(i)));
            }
            i++;
        }
        return arrayList;
    }

    /* renamed from: a */
    public aa mo3137a(C2236z c2236z) {
        return new C2156m(c2236z.m10220e(), C2253l.m10358a(new C2145a(this, this.f6509p.m9458g())));
    }

    /* renamed from: a */
    public C2235a mo3138a() {
        return this.f6507n.m9387a() == C2226v.HTTP_2 ? C2146f.m9683b(this.f6509p.m9455d()) : C2146f.m9681a(this.f6509p.m9455d());
    }

    /* renamed from: a */
    public C2094r mo3139a(C2231x c2231x, long j) {
        return this.f6509p.m9459h();
    }

    /* renamed from: a */
    public void mo3140a(C2151h c2151h) {
        this.f6508o = c2151h;
    }

    /* renamed from: a */
    public void mo3141a(C2231x c2231x) {
        if (this.f6509p == null) {
            this.f6509p = this.f6507n.m9386a(this.f6507n.m9387a() == C2226v.HTTP_2 ? C2146f.m9685c(c2231x) : C2146f.m9684b(c2231x), C2152i.m9723c(c2231x.m10159b()), true);
            this.f6509p.m9456e().mo3200a((long) this.f6508o.f6519a.m10107b(), TimeUnit.MILLISECONDS);
            this.f6509p.m9457f().mo3200a((long) this.f6508o.f6519a.m10108c(), TimeUnit.MILLISECONDS);
        }
    }

    /* renamed from: b */
    public void mo3142b() {
        this.f6509p.m9459h().close();
    }
}
