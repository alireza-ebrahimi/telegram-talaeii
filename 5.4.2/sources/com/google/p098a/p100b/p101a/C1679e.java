package com.google.p098a.p100b.p101a;

import com.google.p098a.C1771l;
import com.google.p098a.C1772i;
import com.google.p098a.C1775n;
import com.google.p098a.C1776o;
import com.google.p098a.C1777q;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1758b;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/* renamed from: com.google.a.b.a.e */
public final class C1679e extends C1678a {
    /* renamed from: a */
    private static final Reader f5150a = new C16771();
    /* renamed from: b */
    private static final Object f5151b = new Object();
    /* renamed from: c */
    private final List<Object> f5152c = new ArrayList();

    /* renamed from: com.google.a.b.a.e$1 */
    static class C16771 extends Reader {
        C16771() {
        }

        public void close() {
            throw new AssertionError();
        }

        public int read(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }
    }

    public C1679e(C1771l c1771l) {
        super(f5150a);
        this.f5152c.add(c1771l);
    }

    /* renamed from: a */
    private void m8140a(C1758b c1758b) {
        if (mo1262f() != c1758b) {
            throw new IllegalStateException("Expected " + c1758b + " but was " + mo1262f());
        }
    }

    /* renamed from: r */
    private Object m8141r() {
        return this.f5152c.get(this.f5152c.size() - 1);
    }

    /* renamed from: s */
    private Object m8142s() {
        return this.f5152c.remove(this.f5152c.size() - 1);
    }

    /* renamed from: a */
    public void mo1256a() {
        m8140a(C1758b.BEGIN_ARRAY);
        this.f5152c.add(((C1772i) m8141r()).iterator());
    }

    /* renamed from: b */
    public void mo1257b() {
        m8140a(C1758b.END_ARRAY);
        m8142s();
        m8142s();
    }

    /* renamed from: c */
    public void mo1258c() {
        m8140a(C1758b.BEGIN_OBJECT);
        this.f5152c.add(((C1776o) m8141r()).m8428o().iterator());
    }

    public void close() {
        this.f5152c.clear();
        this.f5152c.add(f5151b);
    }

    /* renamed from: d */
    public void mo1260d() {
        m8140a(C1758b.END_OBJECT);
        m8142s();
        m8142s();
    }

    /* renamed from: e */
    public boolean mo1261e() {
        C1758b f = mo1262f();
        return (f == C1758b.END_OBJECT || f == C1758b.END_ARRAY) ? false : true;
    }

    /* renamed from: f */
    public C1758b mo1262f() {
        if (this.f5152c.isEmpty()) {
            return C1758b.END_DOCUMENT;
        }
        Object r = m8141r();
        if (r instanceof Iterator) {
            boolean z = this.f5152c.get(this.f5152c.size() - 2) instanceof C1776o;
            Iterator it = (Iterator) r;
            if (!it.hasNext()) {
                return z ? C1758b.END_OBJECT : C1758b.END_ARRAY;
            } else {
                if (z) {
                    return C1758b.NAME;
                }
                this.f5152c.add(it.next());
                return mo1262f();
            }
        } else if (r instanceof C1776o) {
            return C1758b.BEGIN_OBJECT;
        } else {
            if (r instanceof C1772i) {
                return C1758b.BEGIN_ARRAY;
            }
            if (r instanceof C1777q) {
                C1777q c1777q = (C1777q) r;
                if (c1777q.m8441q()) {
                    return C1758b.STRING;
                }
                if (c1777q.m8439o()) {
                    return C1758b.BOOLEAN;
                }
                if (c1777q.m8440p()) {
                    return C1758b.NUMBER;
                }
                throw new AssertionError();
            } else if (r instanceof C1775n) {
                return C1758b.NULL;
            } else {
                if (r == f5151b) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    /* renamed from: g */
    public String mo1263g() {
        m8140a(C1758b.NAME);
        Entry entry = (Entry) ((Iterator) m8141r()).next();
        this.f5152c.add(entry.getValue());
        return (String) entry.getKey();
    }

    /* renamed from: h */
    public String mo1264h() {
        C1758b f = mo1262f();
        if (f == C1758b.STRING || f == C1758b.NUMBER) {
            return ((C1777q) m8142s()).mo1293b();
        }
        throw new IllegalStateException("Expected " + C1758b.STRING + " but was " + f);
    }

    /* renamed from: i */
    public boolean mo1265i() {
        m8140a(C1758b.BOOLEAN);
        return ((C1777q) m8142s()).mo1297f();
    }

    /* renamed from: j */
    public void mo1266j() {
        m8140a(C1758b.NULL);
        m8142s();
    }

    /* renamed from: k */
    public double mo1267k() {
        C1758b f = mo1262f();
        if (f == C1758b.NUMBER || f == C1758b.STRING) {
            double c = ((C1777q) m8141r()).mo1294c();
            if (m8138p() || !(Double.isNaN(c) || Double.isInfinite(c))) {
                m8142s();
                return c;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + c);
        }
        throw new IllegalStateException("Expected " + C1758b.NUMBER + " but was " + f);
    }

    /* renamed from: l */
    public long mo1268l() {
        C1758b f = mo1262f();
        if (f == C1758b.NUMBER || f == C1758b.STRING) {
            long d = ((C1777q) m8141r()).mo1295d();
            m8142s();
            return d;
        }
        throw new IllegalStateException("Expected " + C1758b.NUMBER + " but was " + f);
    }

    /* renamed from: m */
    public int mo1269m() {
        C1758b f = mo1262f();
        if (f == C1758b.NUMBER || f == C1758b.STRING) {
            int e = ((C1777q) m8141r()).mo1296e();
            m8142s();
            return e;
        }
        throw new IllegalStateException("Expected " + C1758b.NUMBER + " but was " + f);
    }

    /* renamed from: n */
    public void mo1270n() {
        if (mo1262f() == C1758b.NAME) {
            mo1263g();
        } else {
            m8142s();
        }
    }

    /* renamed from: o */
    public void mo1271o() {
        m8140a(C1758b.NAME);
        Entry entry = (Entry) ((Iterator) m8141r()).next();
        this.f5152c.add(entry.getValue());
        this.f5152c.add(new C1777q((String) entry.getKey()));
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
