package com.p111h.p112a.p114b;

import com.p111h.p112a.p117e.C1992a;
import com.p111h.p112a.p117e.C1995d;

/* renamed from: com.h.a.b.a */
public class C1977a {
    /* renamed from: a */
    public static final String[] f5818a = new String[]{"34", "37"};
    /* renamed from: b */
    public static final String[] f5819b = new String[]{"60", "62", "64", "65"};
    /* renamed from: c */
    public static final String[] f5820c = new String[]{"35"};
    /* renamed from: d */
    public static final String[] f5821d = new String[]{"300", "301", "302", "303", "304", "305", "309", "36", "38", "39"};
    /* renamed from: e */
    public static final String[] f5822e = new String[]{"4"};
    /* renamed from: f */
    public static final String[] f5823f = new String[]{"2221", "2222", "2223", "2224", "2225", "2226", "2227", "2228", "2229", "223", "224", "225", "226", "227", "228", "229", "23", "24", "25", "26", "270", "271", "2720", "50", "51", "52", "53", "54", "55"};
    /* renamed from: g */
    private String f5824g;
    /* renamed from: h */
    private String f5825h;
    /* renamed from: i */
    private Integer f5826i;
    /* renamed from: j */
    private Integer f5827j;
    /* renamed from: k */
    private String f5828k;
    /* renamed from: l */
    private String f5829l;
    /* renamed from: m */
    private String f5830m;
    /* renamed from: n */
    private String f5831n;
    /* renamed from: o */
    private String f5832o;
    /* renamed from: p */
    private String f5833p;
    /* renamed from: q */
    private String f5834q;
    /* renamed from: r */
    private String f5835r;
    /* renamed from: s */
    private String f5836s;
    /* renamed from: t */
    private String f5837t;
    /* renamed from: u */
    private String f5838u;
    /* renamed from: v */
    private String f5839v;
    /* renamed from: w */
    private String f5840w;

    public C1977a(String str, Integer num, Integer num2, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        this(str, num, num2, str2, str3, str4, str5, str6, str7, str8, str9, null, null, null, null, null, str10);
    }

    public C1977a(String str, Integer num, Integer num2, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15) {
        this.f5824g = C1995d.m9016b(m8949b(str));
        this.f5826i = num;
        this.f5827j = num2;
        this.f5825h = C1995d.m9016b(str2);
        this.f5828k = C1995d.m9016b(str3);
        this.f5829l = C1995d.m9016b(str4);
        this.f5830m = C1995d.m9016b(str5);
        this.f5831n = C1995d.m9016b(str6);
        this.f5832o = C1995d.m9016b(str7);
        this.f5833p = C1995d.m9016b(str8);
        this.f5834q = C1995d.m9016b(str9);
        if (C1995d.m9018d(str10) == null) {
            str10 = m8969t();
        }
        this.f5836s = str10;
        if (C1995d.m9016b(str11) == null) {
            str11 = m8967r();
        }
        this.f5835r = str11;
        this.f5838u = C1995d.m9016b(str12);
        this.f5837t = C1995d.m9019e(str13);
        this.f5839v = C1995d.m9016b(str14);
        this.f5840w = C1995d.m9016b(str15);
    }

    /* renamed from: a */
    private boolean m8948a(String str) {
        int i = 0;
        boolean z = true;
        for (int length = str.length() - 1; length >= 0; length--) {
            char charAt = str.charAt(length);
            if (!Character.isDigit(charAt)) {
                return false;
            }
            int parseInt = Integer.parseInt(TtmlNode.ANONYMOUS_REGION_ID + charAt);
            z = !z;
            if (z) {
                parseInt *= 2;
            }
            if (parseInt > 9) {
                parseInt -= 9;
            }
            i += parseInt;
        }
        return i % 10 == 0;
    }

    /* renamed from: b */
    private String m8949b(String str) {
        return str == null ? null : str.trim().replaceAll("\\s+|-", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    public boolean m8950a() {
        if (C1995d.m9017c(this.f5824g)) {
            return false;
        }
        String replaceAll = this.f5824g.trim().replaceAll("\\s+|-", TtmlNode.ANONYMOUS_REGION_ID);
        if (C1995d.m9017c(replaceAll) || !C1995d.m9014a(replaceAll) || !m8948a(replaceAll)) {
            return false;
        }
        String t = m8969t();
        return "American Express".equals(t) ? replaceAll.length() == 15 : "Diners Club".equals(t) ? replaceAll.length() == 14 : replaceAll.length() == 16;
    }

    /* renamed from: b */
    public boolean m8951b() {
        return m8953d() && m8954e() && !C1992a.m9008a(this.f5827j.intValue(), this.f5826i.intValue());
    }

    /* renamed from: c */
    public boolean m8952c() {
        boolean z = true;
        if (C1995d.m9017c(this.f5825h)) {
            return false;
        }
        String trim = this.f5825h.trim();
        String t = m8969t();
        boolean z2 = (t == null && trim.length() >= 3 && trim.length() <= 4) || (("American Express".equals(t) && trim.length() == 4) || trim.length() == 3);
        if (!(C1995d.m9014a(trim) && z2)) {
            z = false;
        }
        return z;
    }

    /* renamed from: d */
    public boolean m8953d() {
        return this.f5826i != null && this.f5826i.intValue() >= 1 && this.f5826i.intValue() <= 12;
    }

    /* renamed from: e */
    public boolean m8954e() {
        return (this.f5827j == null || C1992a.m9007a(this.f5827j.intValue())) ? false : true;
    }

    /* renamed from: f */
    public String m8955f() {
        return this.f5824g;
    }

    /* renamed from: g */
    public String m8956g() {
        return this.f5825h;
    }

    /* renamed from: h */
    public Integer m8957h() {
        return this.f5826i;
    }

    /* renamed from: i */
    public Integer m8958i() {
        return this.f5827j;
    }

    /* renamed from: j */
    public String m8959j() {
        return this.f5828k;
    }

    /* renamed from: k */
    public String m8960k() {
        return this.f5829l;
    }

    /* renamed from: l */
    public String m8961l() {
        return this.f5830m;
    }

    /* renamed from: m */
    public String m8962m() {
        return this.f5831n;
    }

    /* renamed from: n */
    public String m8963n() {
        return this.f5833p;
    }

    /* renamed from: o */
    public String m8964o() {
        return this.f5832o;
    }

    /* renamed from: p */
    public String m8965p() {
        return this.f5834q;
    }

    /* renamed from: q */
    public String m8966q() {
        return this.f5840w;
    }

    /* renamed from: r */
    public String m8967r() {
        if (!C1995d.m9017c(this.f5835r)) {
            return this.f5835r;
        }
        if (this.f5824g == null || this.f5824g.length() <= 4) {
            return null;
        }
        this.f5835r = this.f5824g.substring(this.f5824g.length() - 4, this.f5824g.length());
        return this.f5835r;
    }

    @Deprecated
    /* renamed from: s */
    public String m8968s() {
        return m8969t();
    }

    /* renamed from: t */
    public String m8969t() {
        if (C1995d.m9017c(this.f5836s) && !C1995d.m9017c(this.f5824g)) {
            String str = C1995d.m9015a(this.f5824g, f5818a) ? "American Express" : C1995d.m9015a(this.f5824g, f5819b) ? "Discover" : C1995d.m9015a(this.f5824g, f5820c) ? "JCB" : C1995d.m9015a(this.f5824g, f5821d) ? "Diners Club" : C1995d.m9015a(this.f5824g, f5822e) ? "Visa" : C1995d.m9015a(this.f5824g, f5823f) ? "MasterCard" : "Unknown";
            this.f5836s = str;
        }
        return this.f5836s;
    }
}
