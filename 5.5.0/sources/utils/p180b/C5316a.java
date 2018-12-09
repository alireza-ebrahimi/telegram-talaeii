package utils.p180b;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;

/* renamed from: utils.b.a */
public class C5316a extends GregorianCalendar {
    /* renamed from: a */
    private int f10224a;
    /* renamed from: b */
    private int f10225b;
    /* renamed from: c */
    private int f10226c;
    /* renamed from: d */
    private String f10227d = "/";

    public C5316a() {
        setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public C5316a(long j) {
        setTimeInMillis(j);
    }

    /* renamed from: a */
    private String m14125a(int i) {
        return i < 9 ? "0" + i : String.valueOf(i);
    }

    /* renamed from: a */
    protected void m14126a() {
        long a = C5318c.m14132a(((long) Math.floor((double) (getTimeInMillis() - -210866803200000L))) / 86400000);
        long j = a >> 16;
        int i = ((int) (65280 & a)) >> 8;
        int i2 = (int) (a & 255);
        if (j <= 0) {
            j--;
        }
        this.f10224a = (int) j;
        this.f10225b = i;
        this.f10226c = i2;
    }

    /* renamed from: b */
    public int m14127b() {
        return this.f10225b;
    }

    /* renamed from: c */
    public String m14128c() {
        return C5317b.f10228a[this.f10225b];
    }

    /* renamed from: d */
    public int m14129d() {
        return this.f10226c;
    }

    /* renamed from: e */
    public String m14130e() {
        return TtmlNode.ANONYMOUS_REGION_ID + m14125a(this.f10224a) + this.f10227d + m14125a(m14127b() + 1) + this.f10227d + m14125a(this.f10226c);
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public void set(int i, int i2) {
        super.set(i, i2);
        m14126a();
    }

    public void setTimeInMillis(long j) {
        super.setTimeInMillis(j);
        m14126a();
    }

    public void setTimeZone(TimeZone timeZone) {
        super.setTimeZone(timeZone);
        m14126a();
    }

    public String toString() {
        String gregorianCalendar = super.toString();
        return gregorianCalendar.substring(0, gregorianCalendar.length() - 1) + ",PersianDate=" + m14130e() + "]";
    }
}
