package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.C1778t;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* renamed from: com.google.a.b.a.j */
public final class C1693j extends C1670w<Date> {
    /* renamed from: a */
    public static final C1668x f5192a = new C16921();
    /* renamed from: b */
    private final DateFormat f5193b = new SimpleDateFormat("MMM d, yyyy");

    /* renamed from: com.google.a.b.a.j$1 */
    static class C16921 implements C1668x {
        C16921() {
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            return c1748a.m8359a() == Date.class ? new C1693j() : null;
        }
    }

    /* renamed from: a */
    public synchronized Date m8213a(C1678a c1678a) {
        Date date;
        if (c1678a.mo1262f() == C1758b.NULL) {
            c1678a.mo1266j();
            date = null;
        } else {
            try {
                date = new Date(this.f5193b.parse(c1678a.mo1264h()).getTime());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }
        return date;
    }

    /* renamed from: a */
    public synchronized void m8214a(C1681c c1681c, Date date) {
        c1681c.mo1279b(date == null ? null : this.f5193b.format(date));
    }

    public /* synthetic */ Object read(C1678a c1678a) {
        return m8213a(c1678a);
    }

    public /* synthetic */ void write(C1681c c1681c, Object obj) {
        m8214a(c1681c, (Date) obj);
    }
}
