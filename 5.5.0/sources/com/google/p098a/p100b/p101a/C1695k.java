package com.google.p098a.p100b.p101a;

import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.C1778t;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* renamed from: com.google.a.b.a.k */
public final class C1695k extends C1670w<Time> {
    /* renamed from: a */
    public static final C1668x f5194a = new C16941();
    /* renamed from: b */
    private final DateFormat f5195b = new SimpleDateFormat("hh:mm:ss a");

    /* renamed from: com.google.a.b.a.k$1 */
    static class C16941 implements C1668x {
        C16941() {
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            return c1748a.m8359a() == Time.class ? new C1695k() : null;
        }
    }

    /* renamed from: a */
    public synchronized Time m8215a(C1678a c1678a) {
        Time time;
        if (c1678a.mo1262f() == C1758b.NULL) {
            c1678a.mo1266j();
            time = null;
        } else {
            try {
                time = new Time(this.f5195b.parse(c1678a.mo1264h()).getTime());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }
        return time;
    }

    /* renamed from: a */
    public synchronized void m8216a(C1681c c1681c, Time time) {
        c1681c.mo1279b(time == null ? null : this.f5195b.format(time));
    }

    public /* synthetic */ Object read(C1678a c1678a) {
        return m8215a(c1678a);
    }

    public /* synthetic */ void write(C1681c c1681c, Object obj) {
        m8216a(c1681c, (Time) obj);
    }
}
