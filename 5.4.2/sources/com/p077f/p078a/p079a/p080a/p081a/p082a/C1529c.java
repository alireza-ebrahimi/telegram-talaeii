package com.p077f.p078a.p079a.p080a.p081a.p082a;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.telegram.messenger.MessagesController;

/* renamed from: com.f.a.a.a.a.a.c */
class C1529c implements Closeable {
    /* renamed from: a */
    private final InputStream f4653a;
    /* renamed from: b */
    private final Charset f4654b;
    /* renamed from: c */
    private byte[] f4655c;
    /* renamed from: d */
    private int f4656d;
    /* renamed from: e */
    private int f4657e;

    public C1529c(InputStream inputStream, int i, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        } else if (i < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(C1530d.f4658a)) {
            this.f4653a = inputStream;
            this.f4654b = charset;
            this.f4655c = new byte[i];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public C1529c(InputStream inputStream, Charset charset) {
        this(inputStream, MessagesController.UPDATE_MASK_CHANNEL, charset);
    }

    /* renamed from: b */
    private void m7623b() {
        int read = this.f4653a.read(this.f4655c, 0, this.f4655c.length);
        if (read == -1) {
            throw new EOFException();
        }
        this.f4656d = 0;
        this.f4657e = read;
    }

    /* renamed from: a */
    public String m7624a() {
        String str;
        synchronized (this.f4653a) {
            if (this.f4655c == null) {
                throw new IOException("LineReader is closed");
            }
            int i;
            if (this.f4656d >= this.f4657e) {
                m7623b();
            }
            int i2 = this.f4656d;
            while (i2 != this.f4657e) {
                if (this.f4655c[i2] == (byte) 10) {
                    int i3 = (i2 == this.f4656d || this.f4655c[i2 - 1] != (byte) 13) ? i2 : i2 - 1;
                    str = new String(this.f4655c, this.f4656d, i3 - this.f4656d, this.f4654b.name());
                    this.f4656d = i2 + 1;
                } else {
                    i2++;
                }
            }
            ByteArrayOutputStream c15281 = new ByteArrayOutputStream(this, (this.f4657e - this.f4656d) + 80) {
                /* renamed from: a */
                final /* synthetic */ C1529c f4652a;

                public String toString() {
                    int i = (this.count <= 0 || this.buf[this.count - 1] != (byte) 13) ? this.count : this.count - 1;
                    try {
                        return new String(this.buf, 0, i, this.f4652a.f4654b.name());
                    } catch (UnsupportedEncodingException e) {
                        throw new AssertionError(e);
                    }
                }
            };
            loop1:
            while (true) {
                c15281.write(this.f4655c, this.f4656d, this.f4657e - this.f4656d);
                this.f4657e = -1;
                m7623b();
                i = this.f4656d;
                while (i != this.f4657e) {
                    if (this.f4655c[i] == (byte) 10) {
                        break loop1;
                    }
                    i++;
                }
            }
            if (i != this.f4656d) {
                c15281.write(this.f4655c, this.f4656d, i - this.f4656d);
            }
            this.f4656d = i + 1;
            str = c15281.toString();
        }
        return str;
    }

    public void close() {
        synchronized (this.f4653a) {
            if (this.f4655c != null) {
                this.f4655c = null;
                this.f4653a.close();
            }
        }
    }
}
