package com.mohamadamin.persianmaterialdatetimepicker.p121a;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.a.b */
public class C2018b extends GregorianCalendar {
    /* renamed from: a */
    private int f5940a;
    /* renamed from: b */
    private int f5941b;
    /* renamed from: c */
    private int f5942c;
    /* renamed from: d */
    private String f5943d = "/";

    public C2018b() {
        setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public C2018b(long j) {
        setTimeInMillis(j);
    }

    /* renamed from: a */
    private long m9091a(long j) {
        return ((86400000 * j) - 210866803200000L) + C2020d.m9102a((double) (getTimeInMillis() - -210866803200000L), 8.64E7d);
    }

    /* renamed from: a */
    private String m9092a(int i) {
        return i < 9 ? "0" + i : String.valueOf(i);
    }

    /* renamed from: a */
    protected void m9093a() {
        long a = C2020d.m9103a(((long) Math.floor((double) (getTimeInMillis() - -210866803200000L))) / 86400000);
        long j = a >> 16;
        int i = ((int) (65280 & a)) >> 8;
        int i2 = (int) (a & 255);
        if (j <= 0) {
            j--;
        }
        this.f5940a = (int) j;
        this.f5941b = i;
        this.f5942c = i2;
    }

    /* renamed from: a */
    public void m9094a(int i, int i2, int i3) {
        int i4 = i2 + 1;
        this.f5940a = i;
        this.f5941b = i4;
        this.f5942c = i3;
        setTimeInMillis(m9091a(C2020d.m9104a(this.f5940a > 0 ? (long) this.f5940a : (long) (this.f5940a + 1), this.f5941b - 1, this.f5942c)));
    }

    /* renamed from: b */
    public int m9095b() {
        return this.f5940a;
    }

    /* renamed from: c */
    public int m9096c() {
        return this.f5941b;
    }

    /* renamed from: d */
    public String m9097d() {
        return C2019c.f5944a[this.f5941b];
    }

    /* renamed from: e */
    public int m9098e() {
        return this.f5942c;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* renamed from: f */
    public String m9099f() {
        switch (get(7)) {
            case 1:
                return C2019c.f5945b[1];
            case 2:
                return C2019c.f5945b[2];
            case 3:
                return C2019c.f5945b[3];
            case 4:
                return C2019c.f5945b[4];
            case 5:
                return C2019c.f5945b[5];
            case 7:
                return C2019c.f5945b[0];
            default:
                return C2019c.f5945b[6];
        }
    }

    /* renamed from: g */
    public String m9100g() {
        return m9099f() + "  " + this.f5942c + "  " + m9097d() + "  " + this.f5940a;
    }

    /* renamed from: h */
    public String m9101h() {
        return TtmlNode.ANONYMOUS_REGION_ID + m9092a(this.f5940a) + this.f5943d + m9092a(m9096c() + 1) + this.f5943d + m9092a(this.f5942c);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public void set(int i, int i2) {
        super.set(i, i2);
        m9093a();
    }

    public void setTimeInMillis(long j) {
        super.setTimeInMillis(j);
        m9093a();
    }

    public void setTimeZone(TimeZone timeZone) {
        super.setTimeZone(timeZone);
        m9093a();
    }

    public String toString() {
        String gregorianCalendar = super.toString();
        return gregorianCalendar.substring(0, gregorianCalendar.length() - 1) + ",PersianDate=" + m9101h() + "]";
    }
}
