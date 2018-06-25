package com.google.p098a.p100b.p101a;

import com.google.p098a.C1771l;
import com.google.p098a.C1772i;
import com.google.p098a.C1775n;
import com.google.p098a.C1776o;
import com.google.p098a.C1777q;
import com.google.p098a.p102d.C1681c;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.a.b.a.f */
public final class C1682f extends C1681c {
    /* renamed from: a */
    private static final Writer f5164a = new C16801();
    /* renamed from: b */
    private static final C1777q f5165b = new C1777q("closed");
    /* renamed from: c */
    private final List<C1771l> f5166c = new ArrayList();
    /* renamed from: d */
    private String f5167d;
    /* renamed from: e */
    private C1771l f5168e = C1775n.f5390a;

    /* renamed from: com.google.a.b.a.f$1 */
    static class C16801 extends Writer {
        C16801() {
        }

        public void close() {
            throw new AssertionError();
        }

        public void flush() {
            throw new AssertionError();
        }

        public void write(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }
    }

    public C1682f() {
        super(f5164a);
    }

    /* renamed from: a */
    private void m8185a(C1771l c1771l) {
        if (this.f5167d != null) {
            if (!c1771l.m8414j() || m8184i()) {
                ((C1776o) m8186j()).m8427a(this.f5167d, c1771l);
            }
            this.f5167d = null;
        } else if (this.f5166c.isEmpty()) {
            this.f5168e = c1771l;
        } else {
            C1771l j = m8186j();
            if (j instanceof C1772i) {
                ((C1772i) j).m8420a(c1771l);
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* renamed from: j */
    private C1771l m8186j() {
        return (C1771l) this.f5166c.get(this.f5166c.size() - 1);
    }

    /* renamed from: a */
    public C1681c mo1273a(long j) {
        m8185a(new C1777q(Long.valueOf(j)));
        return this;
    }

    /* renamed from: a */
    public C1681c mo1274a(Number number) {
        if (number == null) {
            return mo1284f();
        }
        if (!m8182g()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        m8185a(new C1777q(number));
        return this;
    }

    /* renamed from: a */
    public C1681c mo1275a(String str) {
        if (this.f5166c.isEmpty() || this.f5167d != null) {
            throw new IllegalStateException();
        } else if (m8186j() instanceof C1776o) {
            this.f5167d = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    /* renamed from: a */
    public C1681c mo1276a(boolean z) {
        m8185a(new C1777q(Boolean.valueOf(z)));
        return this;
    }

    /* renamed from: a */
    public C1771l mo1277a() {
        if (this.f5166c.isEmpty()) {
            return this.f5168e;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.f5166c);
    }

    /* renamed from: b */
    public C1681c mo1278b() {
        C1771l c1772i = new C1772i();
        m8185a(c1772i);
        this.f5166c.add(c1772i);
        return this;
    }

    /* renamed from: b */
    public C1681c mo1279b(String str) {
        if (str == null) {
            return mo1284f();
        }
        m8185a(new C1777q(str));
        return this;
    }

    /* renamed from: c */
    public C1681c mo1280c() {
        if (this.f5166c.isEmpty() || this.f5167d != null) {
            throw new IllegalStateException();
        } else if (m8186j() instanceof C1772i) {
            this.f5166c.remove(this.f5166c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public void close() {
        if (this.f5166c.isEmpty()) {
            this.f5166c.add(f5165b);
            return;
        }
        throw new IOException("Incomplete document");
    }

    /* renamed from: d */
    public C1681c mo1282d() {
        C1771l c1776o = new C1776o();
        m8185a(c1776o);
        this.f5166c.add(c1776o);
        return this;
    }

    /* renamed from: e */
    public C1681c mo1283e() {
        if (this.f5166c.isEmpty() || this.f5167d != null) {
            throw new IllegalStateException();
        } else if (m8186j() instanceof C1776o) {
            this.f5166c.remove(this.f5166c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    /* renamed from: f */
    public C1681c mo1284f() {
        m8185a(C1775n.f5390a);
        return this;
    }

    public void flush() {
    }
}
