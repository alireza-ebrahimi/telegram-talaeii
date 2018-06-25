package com.p057c.p058a;

import com.p054b.p055a.C1286b;
import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import com.p057c.p058a.p063b.C1313f;
import com.p057c.p058a.p063b.C1319e;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* renamed from: com.c.a.d */
public class C1247d implements C1246e, Closeable, Iterator<C1248b> {
    /* renamed from: a */
    private static final C1248b f3603a = new C13221("eof ");
    /* renamed from: b */
    private static C1313f f3604b = C1313f.m6751a(C1247d.class);
    /* renamed from: c */
    private List<C1248b> f3605c = new ArrayList();
    /* renamed from: f */
    protected C1286b f3606f;
    /* renamed from: g */
    protected C1254e f3607g;
    /* renamed from: h */
    C1248b f3608h = null;
    /* renamed from: i */
    long f3609i = 0;
    /* renamed from: j */
    long f3610j = 0;
    /* renamed from: k */
    long f3611k = 0;

    /* renamed from: com.c.a.d$1 */
    class C13221 extends C1257a {
        C13221(String str) {
            super(str);
        }

        /* renamed from: a */
        protected void mo1111a(ByteBuffer byteBuffer) {
        }

        /* renamed from: b */
        protected void mo1113b(ByteBuffer byteBuffer) {
        }

        protected long b_() {
            return 0;
        }
    }

    /* renamed from: a */
    public List<C1248b> mo1093a() {
        return (this.f3607g == null || this.f3608h == f3603a) ? this.f3605c : new C1319e(this.f3605c, this);
    }

    /* renamed from: a */
    public void m6482a(C1248b c1248b) {
        if (c1248b != null) {
            this.f3605c = new ArrayList(mo1093a());
            c1248b.setParent(this);
            this.f3605c.add(c1248b);
        }
    }

    /* renamed from: a */
    public void mo1094a(C1254e c1254e, long j, C1286b c1286b) {
        this.f3607g = c1254e;
        long b = c1254e.mo1107b();
        this.f3610j = b;
        this.f3609i = b;
        c1254e.mo1106a(c1254e.mo1107b() + j);
        this.f3611k = c1254e.mo1107b();
        this.f3606f = c1286b;
    }

    /* renamed from: a */
    public final void m6484a(WritableByteChannel writableByteChannel) {
        for (C1248b box : mo1093a()) {
            box.getBox(writableByteChannel);
        }
    }

    public void close() {
        this.f3607g.close();
    }

    public boolean hasNext() {
        if (this.f3608h == f3603a) {
            return false;
        }
        if (this.f3608h != null) {
            return true;
        }
        try {
            this.f3608h = m6486k();
            return true;
        } catch (NoSuchElementException e) {
            this.f3608h = f3603a;
            return false;
        }
    }

    /* renamed from: j */
    protected long m6485j() {
        long j = 0;
        for (int i = 0; i < mo1093a().size(); i++) {
            j += ((C1248b) this.f3605c.get(i)).getSize();
        }
        return j;
    }

    /* renamed from: k */
    public C1248b m6486k() {
        C1248b c1248b;
        if (this.f3608h != null && this.f3608h != f3603a) {
            c1248b = this.f3608h;
            this.f3608h = null;
            return c1248b;
        } else if (this.f3607g == null || this.f3609i >= this.f3611k) {
            this.f3608h = f3603a;
            throw new NoSuchElementException();
        } else {
            try {
                synchronized (this.f3607g) {
                    this.f3607g.mo1106a(this.f3609i);
                    c1248b = this.f3606f.mo1117a(this.f3607g, this);
                    this.f3609i = this.f3607g.mo1107b();
                }
                return c1248b;
            } catch (EOFException e) {
                throw new NoSuchElementException();
            } catch (IOException e2) {
                throw new NoSuchElementException();
            }
        }
    }

    public /* synthetic */ Object next() {
        return m6486k();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName()).append("[");
        for (int i = 0; i < this.f3605c.size(); i++) {
            if (i > 0) {
                stringBuilder.append(";");
            }
            stringBuilder.append(((C1248b) this.f3605c.get(i)).toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
