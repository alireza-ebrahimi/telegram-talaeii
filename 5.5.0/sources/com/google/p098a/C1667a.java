package com.google.p098a;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* renamed from: com.google.a.a */
final class C1667a implements C1665k<Date>, C1666s<Date> {
    /* renamed from: a */
    private final DateFormat f5120a;
    /* renamed from: b */
    private final DateFormat f5121b;
    /* renamed from: c */
    private final DateFormat f5122c;

    C1667a() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    public C1667a(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    C1667a(String str) {
        this(new SimpleDateFormat(str, Locale.US), new SimpleDateFormat(str));
    }

    C1667a(DateFormat dateFormat, DateFormat dateFormat2) {
        this.f5120a = dateFormat;
        this.f5121b = dateFormat2;
        this.f5122c = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.f5122c.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /* renamed from: a */
    private Date m8087a(C1771l c1771l) {
        Date parse;
        synchronized (this.f5121b) {
            try {
                parse = this.f5121b.parse(c1771l.mo1293b());
            } catch (ParseException e) {
                try {
                    parse = this.f5120a.parse(c1771l.mo1293b());
                } catch (ParseException e2) {
                    try {
                        parse = this.f5122c.parse(c1771l.mo1293b());
                    } catch (Throwable e3) {
                        throw new C1778t(c1771l.mo1293b(), e3);
                    }
                }
            }
        }
        return parse;
    }

    /* renamed from: a */
    public C1771l m8089a(Date date, Type type, C1762r c1762r) {
        C1771l c1777q;
        synchronized (this.f5121b) {
            c1777q = new C1777q(this.f5120a.format(date));
        }
        return c1777q;
    }

    /* renamed from: a */
    public Date m8090a(C1771l c1771l, Type type, C1760j c1760j) {
        if (c1771l instanceof C1777q) {
            Date a = m8087a(c1771l);
            if (type == Date.class) {
                return a;
            }
            if (type == Timestamp.class) {
                return new Timestamp(a.getTime());
            }
            if (type == java.sql.Date.class) {
                return new java.sql.Date(a.getTime());
            }
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + type);
        }
        throw new C1773p("The date should be a string value");
    }

    /* renamed from: b */
    public /* synthetic */ Object mo1252b(C1771l c1771l, Type type, C1760j c1760j) {
        return m8090a(c1771l, type, c1760j);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(C1667a.class.getSimpleName());
        stringBuilder.append('(').append(this.f5121b.getClass().getSimpleName()).append(')');
        return stringBuilder.toString();
    }
}
