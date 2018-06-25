package org.ocpsoft.prettytime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.ocpsoft.prettytime.p147b.C2454a;
import org.ocpsoft.prettytime.p147b.C2456b;
import org.ocpsoft.prettytime.p147b.C2458c;
import org.ocpsoft.prettytime.p148c.C2460a;
import org.ocpsoft.prettytime.p148c.C2461b;
import org.ocpsoft.prettytime.p148c.C2462c;
import org.ocpsoft.prettytime.p148c.C2463d;
import org.ocpsoft.prettytime.p148c.C2464e;
import org.ocpsoft.prettytime.p148c.C2465f;
import org.ocpsoft.prettytime.p148c.C2466g;
import org.ocpsoft.prettytime.p148c.C2467h;
import org.ocpsoft.prettytime.p148c.C2468i;
import org.ocpsoft.prettytime.p148c.C2469j;
import org.ocpsoft.prettytime.p148c.C2470k;
import org.ocpsoft.prettytime.p148c.C2471l;
import org.ocpsoft.prettytime.p148c.C2472m;

/* renamed from: org.ocpsoft.prettytime.c */
public class C2473c {
    /* renamed from: a */
    private volatile Date f8218a;
    /* renamed from: b */
    private volatile Locale f8219b = Locale.getDefault();
    /* renamed from: c */
    private volatile Map<C2457e, C2451d> f8220c = new LinkedHashMap();
    /* renamed from: d */
    private volatile List<C2457e> f8221d;

    public C2473c() {
        m12068c();
    }

    public C2473c(Locale locale) {
        m12072a(locale);
        m12068c();
    }

    /* renamed from: a */
    private C2453a m12064a(long j) {
        long abs = Math.abs(j);
        List a = m12070a();
        C2453a c2454a = new C2454a();
        int i = 0;
        while (i < a.size()) {
            C2457e c2457e = (C2457e) a.get(i);
            long abs2 = Math.abs(c2457e.mo3404a());
            long abs3 = Math.abs(c2457e.mo3405b());
            Object obj = i == a.size() + -1 ? 1 : null;
            if (0 == abs3 && obj == null) {
                abs3 = ((C2457e) a.get(i + 1)).mo3404a() / c2457e.mo3404a();
            }
            if (abs3 * abs2 > abs || obj != null) {
                c2454a.m12031a(c2457e);
                if (abs2 > abs) {
                    c2454a.m12030a(m12066b(j));
                    c2454a.m12033b(0);
                } else {
                    c2454a.m12030a(j / abs2);
                    c2454a.m12033b(j - (c2454a.mo3398a() * abs2));
                }
                return c2454a;
            }
            i++;
        }
        return c2454a;
    }

    /* renamed from: a */
    private void m12065a(C2458c c2458c) {
        m12073a(c2458c, new C2456b(c2458c));
    }

    /* renamed from: b */
    private long m12066b(long j) {
        return 0 > j ? -1 : 1;
    }

    /* renamed from: b */
    private Date m12067b() {
        return new Date();
    }

    /* renamed from: c */
    private void m12068c() {
        m12065a(new C2464e());
        m12065a(new C2466g());
        m12065a(new C2469j());
        m12065a(new C2467h());
        m12065a(new C2463d());
        m12065a(new C2461b());
        m12065a(new C2471l());
        m12065a(new C2468i());
        m12065a(new C2472m());
        m12065a(new C2462c());
        m12065a(new C2460a());
        m12065a(new C2465f());
    }

    /* renamed from: a */
    public String m12069a(C2453a c2453a) {
        if (c2453a == null) {
            return m12075b(m12067b());
        }
        C2451d a = m12074a(c2453a.mo3400b());
        return a.mo3397a(c2453a, a.mo3396a(c2453a));
    }

    /* renamed from: a */
    public List<C2457e> m12070a() {
        if (this.f8221d == null) {
            List arrayList = new ArrayList(this.f8220c.keySet());
            Collections.sort(arrayList, new C2470k());
            this.f8221d = Collections.unmodifiableList(arrayList);
        }
        return this.f8221d;
    }

    /* renamed from: a */
    public C2453a m12071a(Date date) {
        if (date == null) {
            date = m12067b();
        }
        Date date2 = this.f8218a;
        if (date2 == null) {
            date2 = m12067b();
        }
        return m12064a(date.getTime() - date2.getTime());
    }

    /* renamed from: a */
    public C2473c m12072a(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        this.f8219b = locale;
        for (C2457e c2457e : this.f8220c.keySet()) {
            if (c2457e instanceof C2455b) {
                ((C2455b) c2457e).mo3403a(locale);
            }
        }
        for (C2451d c2451d : this.f8220c.values()) {
            if (c2451d instanceof C2455b) {
                ((C2455b) c2451d).mo3403a(locale);
            }
        }
        return this;
    }

    /* renamed from: a */
    public C2473c m12073a(C2457e c2457e, C2451d c2451d) {
        if (c2457e == null) {
            throw new IllegalArgumentException("Unit to register must not be null.");
        } else if (c2451d == null) {
            throw new IllegalArgumentException("Format to register must not be null.");
        } else {
            this.f8221d = null;
            this.f8220c.put(c2457e, c2451d);
            if (c2457e instanceof C2455b) {
                ((C2455b) c2457e).mo3403a(this.f8219b);
            }
            if (c2451d instanceof C2455b) {
                ((C2455b) c2451d).mo3403a(this.f8219b);
            }
            return this;
        }
    }

    /* renamed from: a */
    public C2451d m12074a(C2457e c2457e) {
        return (c2457e == null || this.f8220c.get(c2457e) == null) ? null : (C2451d) this.f8220c.get(c2457e);
    }

    /* renamed from: b */
    public String m12075b(Date date) {
        if (date == null) {
            date = m12067b();
        }
        return m12069a(m12071a(date));
    }

    public String toString() {
        return "PrettyTime [reference=" + this.f8218a + ", locale=" + this.f8219b + "]";
    }
}
