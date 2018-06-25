package com.google.p098a.p100b;

import com.google.p098a.C1771l;
import com.google.p098a.C1774m;
import com.google.p098a.C1775n;
import com.google.p098a.C1778t;
import com.google.p098a.p100b.p101a.C1708m;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import java.io.Writer;

/* renamed from: com.google.a.b.j */
public final class C1741j {

    /* renamed from: com.google.a.b.j$a */
    private static final class C1740a extends Writer {
        /* renamed from: a */
        private final Appendable f5330a;
        /* renamed from: b */
        private final C1739a f5331b;

        /* renamed from: com.google.a.b.j$a$a */
        static class C1739a implements CharSequence {
            /* renamed from: a */
            char[] f5329a;

            C1739a() {
            }

            public char charAt(int i) {
                return this.f5329a[i];
            }

            public int length() {
                return this.f5329a.length;
            }

            public CharSequence subSequence(int i, int i2) {
                return new String(this.f5329a, i, i2 - i);
            }
        }

        private C1740a(Appendable appendable) {
            this.f5331b = new C1739a();
            this.f5330a = appendable;
        }

        public void close() {
        }

        public void flush() {
        }

        public void write(int i) {
            this.f5330a.append((char) i);
        }

        public void write(char[] cArr, int i, int i2) {
            this.f5331b.f5329a = cArr;
            this.f5330a.append(this.f5331b, i, i + i2);
        }
    }

    /* renamed from: a */
    public static C1771l m8345a(C1678a c1678a) {
        Object obj = 1;
        try {
            c1678a.mo1262f();
            obj = null;
            return (C1771l) C1708m.f5229P.read(c1678a);
        } catch (Throwable e) {
            if (obj != null) {
                return C1775n.f5390a;
            }
            throw new C1778t(e);
        } catch (Throwable e2) {
            throw new C1778t(e2);
        } catch (Throwable e22) {
            throw new C1774m(e22);
        } catch (Throwable e222) {
            throw new C1778t(e222);
        }
    }

    /* renamed from: a */
    public static Writer m8346a(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new C1740a(appendable);
    }

    /* renamed from: a */
    public static void m8347a(C1771l c1771l, C1681c c1681c) {
        C1708m.f5229P.write(c1681c, c1771l);
    }
}
