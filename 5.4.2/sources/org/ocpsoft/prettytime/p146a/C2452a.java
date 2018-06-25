package org.ocpsoft.prettytime.p146a;

import org.ocpsoft.prettytime.C2451d;
import org.ocpsoft.prettytime.C2453a;

/* renamed from: org.ocpsoft.prettytime.a.a */
public class C2452a implements C2451d {
    /* renamed from: a */
    private String f8198a = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: b */
    private String f8199b = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: c */
    private String f8200c = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: d */
    private String f8201d = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: e */
    private String f8202e = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: f */
    private String f8203f = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: g */
    private String f8204g = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: h */
    private String f8205h = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: i */
    private String f8206i = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: j */
    private String f8207j = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: k */
    private String f8208k = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: l */
    private int f8209l = 50;

    /* renamed from: a */
    private String m12001a(String str, String str2, long j) {
        return mo3410a(j).replaceAll("%s", str).replaceAll("%n", String.valueOf(j)).replaceAll("%u", str2);
    }

    /* renamed from: b */
    private String m12002b(C2453a c2453a) {
        return c2453a.mo3398a() < 0 ? "-" : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: c */
    private String m12003c(C2453a c2453a) {
        return (!c2453a.mo3402d() || this.f8200c == null || this.f8200c.length() <= 0) ? (!c2453a.mo3401c() || this.f8202e == null || this.f8202e.length() <= 0) ? this.f8198a : this.f8202e : this.f8200c;
    }

    /* renamed from: c */
    private String m12004c(C2453a c2453a, boolean z) {
        return m12001a(m12002b(c2453a), mo3407b(c2453a, z), m12006a(c2453a, z));
    }

    /* renamed from: d */
    private String m12005d(C2453a c2453a) {
        return (!c2453a.mo3402d() || this.f8201d == null || this.f8200c.length() <= 0) ? (!c2453a.mo3401c() || this.f8203f == null || this.f8202e.length() <= 0) ? this.f8199b : this.f8203f : this.f8201d;
    }

    /* renamed from: a */
    protected long m12006a(C2453a c2453a, boolean z) {
        return Math.abs(z ? c2453a.mo3399a(this.f8209l) : c2453a.mo3398a());
    }

    /* renamed from: a */
    public String m12007a() {
        return this.f8204g;
    }

    /* renamed from: a */
    protected String mo3410a(long j) {
        return this.f8204g;
    }

    /* renamed from: a */
    public String mo3396a(C2453a c2453a) {
        return m12004c(c2453a, true);
    }

    /* renamed from: a */
    public String mo3397a(C2453a c2453a, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        if (c2453a.mo3401c()) {
            stringBuilder.append(this.f8207j).append(" ").append(str).append(" ").append(this.f8208k);
        } else {
            stringBuilder.append(this.f8205h).append(" ").append(str).append(" ").append(this.f8206i);
        }
        return stringBuilder.toString().replaceAll("\\s+", " ").trim();
    }

    /* renamed from: a */
    public C2452a m12011a(String str) {
        this.f8204g = str;
        return this;
    }

    /* renamed from: b */
    protected String mo3407b(C2453a c2453a, boolean z) {
        return (Math.abs(m12006a(c2453a, z)) == 0 || Math.abs(m12006a(c2453a, z)) > 1) ? m12005d(c2453a) : m12003c(c2453a);
    }

    /* renamed from: b */
    public C2452a m12013b(String str) {
        this.f8205h = str.trim();
        return this;
    }

    /* renamed from: c */
    public C2452a m12014c(String str) {
        this.f8206i = str.trim();
        return this;
    }

    /* renamed from: d */
    public C2452a m12015d(String str) {
        this.f8207j = str.trim();
        return this;
    }

    /* renamed from: e */
    public C2452a m12016e(String str) {
        this.f8208k = str.trim();
        return this;
    }

    /* renamed from: f */
    public C2452a m12017f(String str) {
        this.f8198a = str;
        return this;
    }

    /* renamed from: g */
    public C2452a m12018g(String str) {
        this.f8199b = str;
        return this;
    }

    /* renamed from: h */
    public C2452a m12019h(String str) {
        this.f8200c = str;
        return this;
    }

    /* renamed from: i */
    public C2452a mo3411i(String str) {
        this.f8201d = str;
        return this;
    }

    /* renamed from: j */
    public C2452a m12021j(String str) {
        this.f8202e = str;
        return this;
    }

    /* renamed from: k */
    public C2452a mo3412k(String str) {
        this.f8203f = str;
        return this;
    }

    public String toString() {
        return "SimpleTimeFormat [pattern=" + this.f8204g + ", futurePrefix=" + this.f8205h + ", futureSuffix=" + this.f8206i + ", pastPrefix=" + this.f8207j + ", pastSuffix=" + this.f8208k + ", roundingTolerance=" + this.f8209l + "]";
    }
}
