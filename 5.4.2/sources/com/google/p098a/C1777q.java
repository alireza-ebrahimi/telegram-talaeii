package com.google.p098a;

import com.google.p098a.p100b.C1709a;
import com.google.p098a.p100b.C1728f;
import java.math.BigInteger;

/* renamed from: com.google.a.q */
public final class C1777q extends C1771l {
    /* renamed from: a */
    private static final Class<?>[] f5392a = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    /* renamed from: b */
    private Object f5393b;

    public C1777q(Boolean bool) {
        m8432a((Object) bool);
    }

    public C1777q(Number number) {
        m8432a((Object) number);
    }

    public C1777q(String str) {
        m8432a((Object) str);
    }

    /* renamed from: a */
    private static boolean m8429a(C1777q c1777q) {
        if (!(c1777q.f5393b instanceof Number)) {
            return false;
        }
        Number number = (Number) c1777q.f5393b;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    /* renamed from: b */
    private static boolean m8430b(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class cls = obj.getClass();
        for (Class isAssignableFrom : f5392a) {
            if (isAssignableFrom.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public Number mo1292a() {
        return this.f5393b instanceof String ? new C1728f((String) this.f5393b) : (Number) this.f5393b;
    }

    /* renamed from: a */
    void m8432a(Object obj) {
        if (obj instanceof Character) {
            this.f5393b = String.valueOf(((Character) obj).charValue());
            return;
        }
        boolean z = (obj instanceof Number) || C1777q.m8430b(obj);
        C1709a.m8276a(z);
        this.f5393b = obj;
    }

    /* renamed from: b */
    public String mo1293b() {
        return m8440p() ? mo1292a().toString() : m8439o() ? mo1298n().toString() : (String) this.f5393b;
    }

    /* renamed from: c */
    public double mo1294c() {
        return m8440p() ? mo1292a().doubleValue() : Double.parseDouble(mo1293b());
    }

    /* renamed from: d */
    public long mo1295d() {
        return m8440p() ? mo1292a().longValue() : Long.parseLong(mo1293b());
    }

    /* renamed from: e */
    public int mo1296e() {
        return m8440p() ? mo1292a().intValue() : Integer.parseInt(mo1293b());
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C1777q c1777q = (C1777q) obj;
        if (this.f5393b == null) {
            return c1777q.f5393b == null;
        } else {
            if (C1777q.m8429a(this) && C1777q.m8429a(c1777q)) {
                return mo1292a().longValue() == c1777q.mo1292a().longValue();
            } else {
                if (!(this.f5393b instanceof Number) || !(c1777q.f5393b instanceof Number)) {
                    return this.f5393b.equals(c1777q.f5393b);
                }
                double doubleValue = mo1292a().doubleValue();
                double doubleValue2 = c1777q.mo1292a().doubleValue();
                if (doubleValue == doubleValue2 || (Double.isNaN(doubleValue) && Double.isNaN(doubleValue2))) {
                    z = true;
                }
                return z;
            }
        }
    }

    /* renamed from: f */
    public boolean mo1297f() {
        return m8439o() ? mo1298n().booleanValue() : Boolean.parseBoolean(mo1293b());
    }

    public int hashCode() {
        if (this.f5393b == null) {
            return 31;
        }
        long longValue;
        if (C1777q.m8429a(this)) {
            longValue = mo1292a().longValue();
            return (int) (longValue ^ (longValue >>> 32));
        } else if (!(this.f5393b instanceof Number)) {
            return this.f5393b.hashCode();
        } else {
            longValue = Double.doubleToLongBits(mo1292a().doubleValue());
            return (int) (longValue ^ (longValue >>> 32));
        }
    }

    /* renamed from: n */
    Boolean mo1298n() {
        return (Boolean) this.f5393b;
    }

    /* renamed from: o */
    public boolean m8439o() {
        return this.f5393b instanceof Boolean;
    }

    /* renamed from: p */
    public boolean m8440p() {
        return this.f5393b instanceof Number;
    }

    /* renamed from: q */
    public boolean m8441q() {
        return this.f5393b instanceof String;
    }
}
