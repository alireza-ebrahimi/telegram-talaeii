package com.google.p098a;

import com.google.p098a.p100b.C1741j;
import com.google.p098a.p102d.C1681c;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/* renamed from: com.google.a.l */
public abstract class C1771l {
    /* renamed from: a */
    public Number mo1292a() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* renamed from: b */
    public String mo1293b() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* renamed from: c */
    public double mo1294c() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* renamed from: d */
    public long mo1295d() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* renamed from: e */
    public int mo1296e() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* renamed from: f */
    public boolean mo1297f() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* renamed from: g */
    public boolean m8411g() {
        return this instanceof C1772i;
    }

    /* renamed from: h */
    public boolean m8412h() {
        return this instanceof C1776o;
    }

    /* renamed from: i */
    public boolean m8413i() {
        return this instanceof C1777q;
    }

    /* renamed from: j */
    public boolean m8414j() {
        return this instanceof C1775n;
    }

    /* renamed from: k */
    public C1776o m8415k() {
        if (m8412h()) {
            return (C1776o) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    /* renamed from: l */
    public C1772i m8416l() {
        if (m8411g()) {
            return (C1772i) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    /* renamed from: m */
    public C1777q m8417m() {
        if (m8413i()) {
            return (C1777q) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    /* renamed from: n */
    Boolean mo1298n() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            Writer stringWriter = new StringWriter();
            C1681c c1681c = new C1681c(stringWriter);
            c1681c.m8174b(true);
            C1741j.m8347a(this, c1681c);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
