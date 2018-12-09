package com.google.p098a;

import com.google.p098a.p100b.p101a.C1679e;
import com.google.p098a.p100b.p101a.C1682f;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/* renamed from: com.google.a.w */
public abstract class C1670w<T> {

    /* renamed from: com.google.a.w$1 */
    class C17851 extends C1670w<T> {
        /* renamed from: a */
        final /* synthetic */ C1670w f5408a;

        C17851(C1670w c1670w) {
            this.f5408a = c1670w;
        }

        public T read(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return this.f5408a.read(c1678a);
            }
            c1678a.mo1266j();
            return null;
        }

        public void write(C1681c c1681c, T t) {
            if (t == null) {
                c1681c.mo1284f();
            } else {
                this.f5408a.write(c1681c, t);
            }
        }
    }

    public final T fromJson(Reader reader) {
        return read(new C1678a(reader));
    }

    public final T fromJson(String str) {
        return fromJson(new StringReader(str));
    }

    public final T fromJsonTree(C1771l c1771l) {
        try {
            return read(new C1679e(c1771l));
        } catch (Throwable e) {
            throw new C1774m(e);
        }
    }

    public final C1670w<T> nullSafe() {
        return new C17851(this);
    }

    public abstract T read(C1678a c1678a);

    public final String toJson(T t) {
        Writer stringWriter = new StringWriter();
        toJson(stringWriter, t);
        return stringWriter.toString();
    }

    public final void toJson(Writer writer, T t) {
        write(new C1681c(writer), t);
    }

    public final C1771l toJsonTree(T t) {
        try {
            C1681c c1682f = new C1682f();
            write(c1682f, t);
            return c1682f.mo1277a();
        } catch (Throwable e) {
            throw new C1774m(e);
        }
    }

    public abstract void write(C1681c c1681c, T t);
}
