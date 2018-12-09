package com.p096g.p097a;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.g.a.i */
final class C1625i extends InputStream {
    /* renamed from: a */
    private final InputStream f4962a;
    /* renamed from: b */
    private long f4963b;
    /* renamed from: c */
    private long f4964c;
    /* renamed from: d */
    private long f4965d;
    /* renamed from: e */
    private long f4966e;

    public C1625i(InputStream inputStream) {
        this(inputStream, 4096);
    }

    public C1625i(InputStream inputStream, int i) {
        this.f4966e = -1;
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream, i);
        }
        this.f4962a = inputStream;
    }

    /* renamed from: a */
    private void m8000a(long j, long j2) {
        while (j < j2) {
            long skip = this.f4962a.skip(j2 - j);
            if (skip == 0) {
                if (read() != -1) {
                    skip = 1;
                } else {
                    return;
                }
            }
            j += skip;
        }
    }

    /* renamed from: b */
    private void m8001b(long j) {
        try {
            if (this.f4964c >= this.f4963b || this.f4963b > this.f4965d) {
                this.f4964c = this.f4963b;
                this.f4962a.mark((int) (j - this.f4963b));
            } else {
                this.f4962a.reset();
                this.f4962a.mark((int) (j - this.f4964c));
                m8000a(this.f4964c, this.f4963b);
            }
            this.f4965d = j;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to mark: " + e);
        }
    }

    /* renamed from: a */
    public long m8002a(int i) {
        long j = this.f4963b + ((long) i);
        if (this.f4965d < j) {
            m8001b(j);
        }
        return this.f4963b;
    }

    /* renamed from: a */
    public void m8003a(long j) {
        if (this.f4963b > this.f4965d || j < this.f4964c) {
            throw new IOException("Cannot reset");
        }
        this.f4962a.reset();
        m8000a(this.f4964c, j);
        this.f4963b = j;
    }

    public int available() {
        return this.f4962a.available();
    }

    public void close() {
        this.f4962a.close();
    }

    public void mark(int i) {
        this.f4966e = m8002a(i);
    }

    public boolean markSupported() {
        return this.f4962a.markSupported();
    }

    public int read() {
        int read = this.f4962a.read();
        if (read != -1) {
            this.f4963b++;
        }
        return read;
    }

    public int read(byte[] bArr) {
        int read = this.f4962a.read(bArr);
        if (read != -1) {
            this.f4963b += (long) read;
        }
        return read;
    }

    public int read(byte[] bArr, int i, int i2) {
        int read = this.f4962a.read(bArr, i, i2);
        if (read != -1) {
            this.f4963b += (long) read;
        }
        return read;
    }

    public void reset() {
        m8003a(this.f4966e);
    }

    public long skip(long j) {
        long skip = this.f4962a.skip(j);
        this.f4963b += skip;
        return skip;
    }
}
