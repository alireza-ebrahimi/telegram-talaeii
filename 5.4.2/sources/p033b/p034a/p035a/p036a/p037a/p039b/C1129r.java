package p033b.p034a.p035a.p036a.p037a.p039b;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: b.a.a.a.a.b.r */
public class C1129r implements Closeable {
    /* renamed from: b */
    private static final Logger f3320b = Logger.getLogger(C1129r.class.getName());
    /* renamed from: a */
    int f3321a;
    /* renamed from: c */
    private final RandomAccessFile f3322c;
    /* renamed from: d */
    private int f3323d;
    /* renamed from: e */
    private C1127a f3324e;
    /* renamed from: f */
    private C1127a f3325f;
    /* renamed from: g */
    private final byte[] f3326g = new byte[16];

    /* renamed from: b.a.a.a.a.b.r$c */
    public interface C1125c {
        /* renamed from: a */
        void mo1023a(InputStream inputStream, int i);
    }

    /* renamed from: b.a.a.a.a.b.r$a */
    static class C1127a {
        /* renamed from: a */
        static final C1127a f3314a = new C1127a(0, 0);
        /* renamed from: b */
        final int f3315b;
        /* renamed from: c */
        final int f3316c;

        C1127a(int i, int i2) {
            this.f3315b = i;
            this.f3316c = i2;
        }

        public String toString() {
            return getClass().getSimpleName() + "[position = " + this.f3315b + ", length = " + this.f3316c + "]";
        }
    }

    /* renamed from: b.a.a.a.a.b.r$b */
    private final class C1128b extends InputStream {
        /* renamed from: a */
        final /* synthetic */ C1129r f3317a;
        /* renamed from: b */
        private int f3318b;
        /* renamed from: c */
        private int f3319c;

        private C1128b(C1129r c1129r, C1127a c1127a) {
            this.f3317a = c1129r;
            this.f3318b = c1129r.m6088b(c1127a.f3315b + 4);
            this.f3319c = c1127a.f3316c;
        }

        public int read() {
            if (this.f3319c == 0) {
                return -1;
            }
            this.f3317a.f3322c.seek((long) this.f3318b);
            int read = this.f3317a.f3322c.read();
            this.f3318b = this.f3317a.m6088b(this.f3318b + 1);
            this.f3319c--;
            return read;
        }

        public int read(byte[] bArr, int i, int i2) {
            C1129r.m6090b(bArr, "buffer");
            if ((i | i2) < 0 || i2 > bArr.length - i) {
                throw new ArrayIndexOutOfBoundsException();
            } else if (this.f3319c <= 0) {
                return -1;
            } else {
                if (i2 > this.f3319c) {
                    i2 = this.f3319c;
                }
                this.f3317a.m6091b(this.f3318b, bArr, i, i2);
                this.f3318b = this.f3317a.m6088b(this.f3318b + i2);
                this.f3319c -= i2;
                return i2;
            }
        }
    }

    public C1129r(File file) {
        if (!file.exists()) {
            C1129r.m6086a(file);
        }
        this.f3322c = C1129r.m6089b(file);
        m6093c();
    }

    /* renamed from: a */
    private static int m6079a(byte[] bArr, int i) {
        return ((((bArr[i] & 255) << 24) + ((bArr[i + 1] & 255) << 16)) + ((bArr[i + 2] & 255) << 8)) + (bArr[i + 3] & 255);
    }

    /* renamed from: a */
    private C1127a m6080a(int i) {
        if (i == 0) {
            return C1127a.f3314a;
        }
        this.f3322c.seek((long) i);
        return new C1127a(i, this.f3322c.readInt());
    }

    /* renamed from: a */
    private void m6083a(int i, int i2, int i3, int i4) {
        C1129r.m6087a(this.f3326g, i, i2, i3, i4);
        this.f3322c.seek(0);
        this.f3322c.write(this.f3326g);
    }

    /* renamed from: a */
    private void m6084a(int i, byte[] bArr, int i2, int i3) {
        int b = m6088b(i);
        if (b + i3 <= this.f3321a) {
            this.f3322c.seek((long) b);
            this.f3322c.write(bArr, i2, i3);
            return;
        }
        int i4 = this.f3321a - b;
        this.f3322c.seek((long) b);
        this.f3322c.write(bArr, i2, i4);
        this.f3322c.seek(16);
        this.f3322c.write(bArr, i2 + i4, i3 - i4);
    }

    /* renamed from: a */
    private static void m6086a(File file) {
        File file2 = new File(file.getPath() + ".tmp");
        RandomAccessFile b = C1129r.m6089b(file2);
        try {
            b.setLength(4096);
            b.seek(0);
            byte[] bArr = new byte[16];
            C1129r.m6087a(bArr, 4096, 0, 0, 0);
            b.write(bArr);
            if (!file2.renameTo(file)) {
                throw new IOException("Rename failed!");
            }
        } finally {
            b.close();
        }
    }

    /* renamed from: a */
    private static void m6087a(byte[] bArr, int... iArr) {
        int i = 0;
        int length = iArr.length;
        int i2 = 0;
        while (i < length) {
            C1129r.m6092b(bArr, i2, iArr[i]);
            i2 += 4;
            i++;
        }
    }

    /* renamed from: b */
    private int m6088b(int i) {
        return i < this.f3321a ? i : (i + 16) - this.f3321a;
    }

    /* renamed from: b */
    private static RandomAccessFile m6089b(File file) {
        return new RandomAccessFile(file, "rwd");
    }

    /* renamed from: b */
    private static <T> T m6090b(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    /* renamed from: b */
    private void m6091b(int i, byte[] bArr, int i2, int i3) {
        int b = m6088b(i);
        if (b + i3 <= this.f3321a) {
            this.f3322c.seek((long) b);
            this.f3322c.readFully(bArr, i2, i3);
            return;
        }
        int i4 = this.f3321a - b;
        this.f3322c.seek((long) b);
        this.f3322c.readFully(bArr, i2, i4);
        this.f3322c.seek(16);
        this.f3322c.readFully(bArr, i2 + i4, i3 - i4);
    }

    /* renamed from: b */
    private static void m6092b(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >> 24);
        bArr[i + 1] = (byte) (i2 >> 16);
        bArr[i + 2] = (byte) (i2 >> 8);
        bArr[i + 3] = (byte) i2;
    }

    /* renamed from: c */
    private void m6093c() {
        this.f3322c.seek(0);
        this.f3322c.readFully(this.f3326g);
        this.f3321a = C1129r.m6079a(this.f3326g, 0);
        if (((long) this.f3321a) > this.f3322c.length()) {
            throw new IOException("File is truncated. Expected length: " + this.f3321a + ", Actual length: " + this.f3322c.length());
        }
        this.f3323d = C1129r.m6079a(this.f3326g, 4);
        int a = C1129r.m6079a(this.f3326g, 8);
        int a2 = C1129r.m6079a(this.f3326g, 12);
        this.f3324e = m6080a(a);
        this.f3325f = m6080a(a2);
    }

    /* renamed from: c */
    private void m6094c(int i) {
        int i2 = i + 4;
        int d = m6095d();
        if (d < i2) {
            int i3 = this.f3321a;
            do {
                d += i3;
                i3 <<= 1;
            } while (d < i2);
            m6096d(i3);
            i2 = m6088b((this.f3325f.f3315b + 4) + this.f3325f.f3316c);
            if (i2 < this.f3324e.f3315b) {
                FileChannel channel = this.f3322c.getChannel();
                channel.position((long) this.f3321a);
                int i4 = i2 - 4;
                if (channel.transferTo(16, (long) i4, channel) != ((long) i4)) {
                    throw new AssertionError("Copied insufficient number of bytes!");
                }
            }
            if (this.f3325f.f3315b < this.f3324e.f3315b) {
                d = (this.f3321a + this.f3325f.f3315b) - 16;
                m6083a(i3, this.f3323d, this.f3324e.f3315b, d);
                this.f3325f = new C1127a(d, this.f3325f.f3316c);
            } else {
                m6083a(i3, this.f3323d, this.f3324e.f3315b, this.f3325f.f3315b);
            }
            this.f3321a = i3;
        }
    }

    /* renamed from: d */
    private int m6095d() {
        return this.f3321a - m6097a();
    }

    /* renamed from: d */
    private void m6096d(int i) {
        this.f3322c.setLength((long) i);
        this.f3322c.getChannel().force(true);
    }

    /* renamed from: a */
    public int m6097a() {
        return this.f3323d == 0 ? 16 : this.f3325f.f3315b >= this.f3324e.f3315b ? (((this.f3325f.f3315b - this.f3324e.f3315b) + 4) + this.f3325f.f3316c) + 16 : (((this.f3325f.f3315b + 4) + this.f3325f.f3316c) + this.f3321a) - this.f3324e.f3315b;
    }

    /* renamed from: a */
    public synchronized void m6098a(C1125c c1125c) {
        int i = this.f3324e.f3315b;
        for (int i2 = 0; i2 < this.f3323d; i2++) {
            C1127a a = m6080a(i);
            c1125c.mo1023a(new C1128b(a), a.f3316c);
            i = m6088b(a.f3316c + (a.f3315b + 4));
        }
    }

    /* renamed from: a */
    public void m6099a(byte[] bArr) {
        m6100a(bArr, 0, bArr.length);
    }

    /* renamed from: a */
    public synchronized void m6100a(byte[] bArr, int i, int i2) {
        C1129r.m6090b(bArr, "buffer");
        if ((i | i2) < 0 || i2 > bArr.length - i) {
            throw new IndexOutOfBoundsException();
        }
        m6094c(i2);
        boolean b = m6102b();
        C1127a c1127a = new C1127a(b ? 16 : m6088b((this.f3325f.f3315b + 4) + this.f3325f.f3316c), i2);
        C1129r.m6092b(this.f3326g, 0, i2);
        m6084a(c1127a.f3315b, this.f3326g, 0, 4);
        m6084a(c1127a.f3315b + 4, bArr, i, i2);
        m6083a(this.f3321a, this.f3323d + 1, b ? c1127a.f3315b : this.f3324e.f3315b, c1127a.f3315b);
        this.f3325f = c1127a;
        this.f3323d++;
        if (b) {
            this.f3324e = this.f3325f;
        }
    }

    /* renamed from: a */
    public boolean m6101a(int i, int i2) {
        return (m6097a() + 4) + i <= i2;
    }

    /* renamed from: b */
    public synchronized boolean m6102b() {
        return this.f3323d == 0;
    }

    public synchronized void close() {
        this.f3322c.close();
    }

    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName()).append('[');
        stringBuilder.append("fileLength=").append(this.f3321a);
        stringBuilder.append(", size=").append(this.f3323d);
        stringBuilder.append(", first=").append(this.f3324e);
        stringBuilder.append(", last=").append(this.f3325f);
        stringBuilder.append(", element lengths=[");
        try {
            m6098a(new C1125c(this) {
                /* renamed from: a */
                boolean f3311a = true;
                /* renamed from: c */
                final /* synthetic */ C1129r f3313c;

                /* renamed from: a */
                public void mo1023a(InputStream inputStream, int i) {
                    if (this.f3311a) {
                        this.f3311a = false;
                    } else {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(i);
                }
            });
        } catch (Throwable e) {
            f3320b.log(Level.WARNING, "read error", e);
        }
        stringBuilder.append("]]");
        return stringBuilder.toString();
    }
}
