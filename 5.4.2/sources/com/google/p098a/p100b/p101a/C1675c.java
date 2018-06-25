package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.C1778t;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* renamed from: com.google.a.b.a.c */
public final class C1675c extends C1670w<Date> {
    /* renamed from: a */
    public static final C1668x f5129a = new C16741();
    /* renamed from: b */
    private final DateFormat f5130b = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    /* renamed from: c */
    private final DateFormat f5131c = DateFormat.getDateTimeInstance(2, 2);
    /* renamed from: d */
    private final DateFormat f5132d = C1675c.m8094a();

    /* renamed from: com.google.a.b.a.c$1 */
    static class C16741 implements C1668x {
        C16741() {
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            return c1748a.m8359a() == Date.class ? new C1675c() : null;
        }
    }

    /* renamed from: a */
    private static DateFormat m8094a() {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    /* renamed from: a */
    private synchronized Date m8095a(String str) {
        Date parse;
        try {
            parse = this.f5131c.parse(str);
        } catch (ParseException e) {
            try {
                parse = this.f5130b.parse(str);
            } catch (ParseException e2) {
                try {
                    parse = this.f5132d.parse(str);
                } catch (Throwable e3) {
                    throw new C1778t(str, e3);
                }
            }
        }
        return parse;
    }

    /* renamed from: a */
    public Date m8096a(C1678a c1678a) {
        if (c1678a.mo1262f() != C1758b.NULL) {
            return m8095a(c1678a.mo1264h());
        }
        c1678a.mo1266j();
        return null;
    }

    /* renamed from: a */
    public synchronized void m8097a(C1681c c1681c, Date date) {
        if (date == null) {
            c1681c.mo1284f();
        } else {
            c1681c.mo1279b(this.f5130b.format(date));
        }
    }

    public /* synthetic */ Object read(C1678a c1678a) {
        return m8096a(c1678a);
    }

    public /* synthetic */ void write(C1681c c1681c, Object obj) {
        m8097a(c1681c, (Date) obj);
    }
}
