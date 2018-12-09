package com.p077f.p078a.p086b.p087a;

import java.io.InputStream;

/* renamed from: com.f.a.b.a.a */
public class C1548a extends InputStream {
    /* renamed from: a */
    private final InputStream f4692a;
    /* renamed from: b */
    private final int f4693b;

    public C1548a(InputStream inputStream, int i) {
        this.f4692a = inputStream;
        this.f4693b = i;
    }

    public int available() {
        return this.f4693b;
    }

    public void close() {
        this.f4692a.close();
    }

    public void mark(int i) {
        this.f4692a.mark(i);
    }

    public boolean markSupported() {
        return this.f4692a.markSupported();
    }

    public int read() {
        return this.f4692a.read();
    }

    public int read(byte[] bArr) {
        return this.f4692a.read(bArr);
    }

    public int read(byte[] bArr, int i, int i2) {
        return this.f4692a.read(bArr, i, i2);
    }

    public void reset() {
        this.f4692a.reset();
    }

    public long skip(long j) {
        return this.f4692a.skip(j);
    }
}
