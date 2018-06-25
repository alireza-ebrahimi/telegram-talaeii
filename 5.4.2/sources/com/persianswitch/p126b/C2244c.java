package com.persianswitch.p126b;

import java.io.EOFException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

/* renamed from: com.persianswitch.b.c */
public final class C2244c implements C2242d, C2243e, Cloneable {
    /* renamed from: c */
    private static final byte[] f6934c = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102};
    /* renamed from: a */
    C2257o f6935a;
    /* renamed from: b */
    long f6936b;

    /* renamed from: com.persianswitch.b.c$1 */
    class C22411 extends InputStream {
        /* renamed from: a */
        final /* synthetic */ C2244c f6933a;

        C22411(C2244c c2244c) {
            this.f6933a = c2244c;
        }

        public int available() {
            return (int) Math.min(this.f6933a.f6936b, 2147483647L);
        }

        public void close() {
        }

        public int read() {
            return this.f6933a.f6936b > 0 ? this.f6933a.mo3186h() & 255 : -1;
        }

        public int read(byte[] bArr, int i, int i2) {
            return this.f6933a.m10259a(bArr, i, i2);
        }

        public String toString() {
            return this.f6933a + ".inputStream()";
        }
    }

    /* renamed from: a */
    public int m10259a(byte[] bArr, int i, int i2) {
        C2261u.m10423a((long) bArr.length, (long) i, (long) i2);
        C2257o c2257o = this.f6935a;
        if (c2257o == null) {
            return -1;
        }
        int min = Math.min(i2, c2257o.f6970c - c2257o.f6969b);
        System.arraycopy(c2257o.f6968a, c2257o.f6969b, bArr, i, min);
        c2257o.f6969b += min;
        this.f6936b -= (long) min;
        if (c2257o.f6969b != c2257o.f6970c) {
            return min;
        }
        this.f6935a = c2257o.m10398a();
        C2258p.m10404a(c2257o);
        return min;
    }

    /* renamed from: a */
    public long mo3172a(byte b) {
        return m10261a(b, 0);
    }

    /* renamed from: a */
    public long m10261a(byte b, long j) {
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        C2257o c2257o = this.f6935a;
        if (c2257o == null) {
            return -1;
        }
        C2257o c2257o2;
        if (this.f6936b - j >= j) {
            c2257o2 = c2257o;
            while (true) {
                long j3 = ((long) (c2257o2.f6970c - c2257o2.f6969b)) + j2;
                if (j3 >= j) {
                    break;
                }
                c2257o2 = c2257o2.f6973f;
                j2 = j3;
            }
        } else {
            j2 = this.f6936b;
            c2257o2 = c2257o;
            while (j2 > j) {
                c2257o2 = c2257o2.f6974g;
                j2 -= (long) (c2257o2.f6970c - c2257o2.f6969b);
            }
        }
        while (j2 < this.f6936b) {
            byte[] bArr = c2257o2.f6968a;
            int i = c2257o2.f6970c;
            for (int i2 = (int) ((((long) c2257o2.f6969b) + j) - j2); i2 < i; i2++) {
                if (bArr[i2] == b) {
                    return j2 + ((long) (i2 - c2257o2.f6969b));
                }
            }
            j2 += (long) (c2257o2.f6970c - c2257o2.f6969b);
            c2257o2 = c2257o2.f6973f;
            j = j2;
        }
        return -1;
    }

    /* renamed from: a */
    public long mo3105a(C2244c c2244c, long j) {
        if (c2244c == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.f6936b == 0) {
            return -1;
        } else {
            if (j > this.f6936b) {
                j = this.f6936b;
            }
            c2244c.a_(this, j);
            return j;
        }
    }

    /* renamed from: a */
    public long mo3173a(C2096s c2096s) {
        if (c2096s == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long a = c2096s.mo3105a(this, 8192);
            if (a == -1) {
                return j;
            }
            j += a;
        }
    }

    /* renamed from: a */
    public C2244c m10264a(int i) {
        if (i < 128) {
            m10275b(i);
        } else if (i < 2048) {
            m10275b((i >> 6) | PsExtractor.AUDIO_STREAM);
            m10275b((i & 63) | 128);
        } else if (i < C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) {
            if (i < 55296 || i > 57343) {
                m10275b((i >> 12) | 224);
                m10275b(((i >> 6) & 63) | 128);
                m10275b((i & 63) | 128);
            } else {
                throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
            }
        } else if (i <= 1114111) {
            m10275b((i >> 18) | PsExtractor.VIDEO_STREAM_MASK);
            m10275b(((i >> 12) & 63) | 128);
            m10275b(((i >> 6) & 63) | 128);
            m10275b((i & 63) | 128);
        } else {
            throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
        }
        return this;
    }

    /* renamed from: a */
    public C2244c m10265a(C2244c c2244c, long j, long j2) {
        if (c2244c == null) {
            throw new IllegalArgumentException("out == null");
        }
        C2261u.m10423a(this.f6936b, j, j2);
        if (j2 != 0) {
            c2244c.f6936b += j2;
            C2257o c2257o = this.f6935a;
            while (j >= ((long) (c2257o.f6970c - c2257o.f6969b))) {
                j -= (long) (c2257o.f6970c - c2257o.f6969b);
                c2257o = c2257o.f6973f;
            }
            while (j2 > 0) {
                C2257o c2257o2 = new C2257o(c2257o);
                c2257o2.f6969b = (int) (((long) c2257o2.f6969b) + j);
                c2257o2.f6970c = Math.min(c2257o2.f6969b + ((int) j2), c2257o2.f6970c);
                if (c2244c.f6935a == null) {
                    c2257o2.f6974g = c2257o2;
                    c2257o2.f6973f = c2257o2;
                    c2244c.f6935a = c2257o2;
                } else {
                    c2244c.f6935a.f6974g.m10400a(c2257o2);
                }
                j2 -= (long) (c2257o2.f6970c - c2257o2.f6969b);
                c2257o = c2257o.f6973f;
                j = 0;
            }
        }
        return this;
    }

    /* renamed from: a */
    public C2244c m10266a(C2245f c2245f) {
        if (c2245f == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        c2245f.mo3210a(this);
        return this;
    }

    /* renamed from: a */
    public C2244c m10267a(String str) {
        return m10268a(str, 0, str.length());
    }

    /* renamed from: a */
    public C2244c m10268a(String str, int i, int i2) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i);
        } else if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        } else if (i2 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
        } else {
            while (i < i2) {
                int i3;
                char charAt = str.charAt(i);
                if (charAt < '') {
                    int i4;
                    C2257o e = m10288e(1);
                    byte[] bArr = e.f6968a;
                    int i5 = e.f6970c - i;
                    int min = Math.min(i2, 8192 - i5);
                    i3 = i + 1;
                    bArr[i5 + i] = (byte) charAt;
                    while (i3 < min) {
                        char charAt2 = str.charAt(i3);
                        if (charAt2 >= '') {
                            break;
                        }
                        i4 = i3 + 1;
                        bArr[i3 + i5] = (byte) charAt2;
                        i3 = i4;
                    }
                    i4 = (i3 + i5) - e.f6970c;
                    e.f6970c += i4;
                    this.f6936b += (long) i4;
                } else if (charAt < 'ࠀ') {
                    m10275b((charAt >> 6) | PsExtractor.AUDIO_STREAM);
                    m10275b((charAt & 63) | 128);
                    i3 = i + 1;
                } else if (charAt < '?' || charAt > '?') {
                    m10275b((charAt >> 12) | 224);
                    m10275b(((charAt >> 6) & 63) | 128);
                    m10275b((charAt & 63) | 128);
                    i3 = i + 1;
                } else {
                    i3 = i + 1 < i2 ? str.charAt(i + 1) : 0;
                    if (charAt > '?' || i3 < 56320 || i3 > 57343) {
                        m10275b(63);
                        i++;
                    } else {
                        i3 = ((i3 & -56321) | ((charAt & -55297) << 10)) + C3446C.DEFAULT_BUFFER_SEGMENT_SIZE;
                        m10275b((i3 >> 18) | PsExtractor.VIDEO_STREAM_MASK);
                        m10275b(((i3 >> 12) & 63) | 128);
                        m10275b(((i3 >> 6) & 63) | 128);
                        m10275b((i3 & 63) | 128);
                        i3 = i + 2;
                    }
                }
                i = i3;
            }
            return this;
        }
    }

    /* renamed from: a */
    public C2098t mo3101a() {
        return C2098t.f6342b;
    }

    /* renamed from: a */
    public String m10270a(long j, Charset charset) {
        C2261u.m10423a(this.f6936b, 0, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        } else if (j == 0) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        } else {
            C2257o c2257o = this.f6935a;
            if (((long) c2257o.f6969b) + j > ((long) c2257o.f6970c)) {
                return new String(mo3183f(j), charset);
            }
            String str = new String(c2257o.f6968a, c2257o.f6969b, (int) j, charset);
            c2257o.f6969b = (int) (((long) c2257o.f6969b) + j);
            this.f6936b -= j;
            if (c2257o.f6969b != c2257o.f6970c) {
                return str;
            }
            this.f6935a = c2257o.m10398a();
            C2258p.m10404a(c2257o);
            return str;
        }
    }

    /* renamed from: a */
    public void mo3174a(long j) {
        if (this.f6936b < j) {
            throw new EOFException();
        }
    }

    /* renamed from: a */
    public void m10272a(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            int a = m10259a(bArr, i, bArr.length - i);
            if (a == -1) {
                throw new EOFException();
            }
            i += a;
        }
    }

    public void a_(C2244c c2244c, long j) {
        if (c2244c == null) {
            throw new IllegalArgumentException("source == null");
        } else if (c2244c == this) {
            throw new IllegalArgumentException("source == this");
        } else {
            C2261u.m10423a(c2244c.f6936b, 0, j);
            while (j > 0) {
                C2257o c2257o;
                if (j < ((long) (c2244c.f6935a.f6970c - c2244c.f6935a.f6969b))) {
                    c2257o = this.f6935a != null ? this.f6935a.f6974g : null;
                    if (c2257o != null && c2257o.f6972e) {
                        if ((((long) c2257o.f6970c) + j) - ((long) (c2257o.f6971d ? 0 : c2257o.f6969b)) <= 8192) {
                            c2244c.f6935a.m10401a(c2257o, (int) j);
                            c2244c.f6936b -= j;
                            this.f6936b += j;
                            return;
                        }
                    }
                    c2244c.f6935a = c2244c.f6935a.m10399a((int) j);
                }
                C2257o c2257o2 = c2244c.f6935a;
                long j2 = (long) (c2257o2.f6970c - c2257o2.f6969b);
                c2244c.f6935a = c2257o2.m10398a();
                if (this.f6935a == null) {
                    this.f6935a = c2257o2;
                    c2257o2 = this.f6935a;
                    c2257o = this.f6935a;
                    C2257o c2257o3 = this.f6935a;
                    c2257o.f6974g = c2257o3;
                    c2257o2.f6973f = c2257o3;
                } else {
                    this.f6935a.f6974g.m10400a(c2257o2).m10402b();
                }
                c2244c.f6936b -= j2;
                this.f6936b += j2;
                j -= j2;
            }
        }
    }

    /* renamed from: b */
    public byte m10273b(long j) {
        C2261u.m10423a(this.f6936b, j, 1);
        C2257o c2257o = this.f6935a;
        while (true) {
            int i = c2257o.f6970c - c2257o.f6969b;
            if (j < ((long) i)) {
                return c2257o.f6968a[c2257o.f6969b + ((int) j)];
            }
            j -= (long) i;
            c2257o = c2257o.f6973f;
        }
    }

    /* renamed from: b */
    public long m10274b() {
        return this.f6936b;
    }

    /* renamed from: b */
    public C2244c m10275b(int i) {
        C2257o e = m10288e(1);
        byte[] bArr = e.f6968a;
        int i2 = e.f6970c;
        e.f6970c = i2 + 1;
        bArr[i2] = (byte) i;
        this.f6936b++;
        return this;
    }

    /* renamed from: b */
    public C2244c m10276b(byte[] bArr) {
        if (bArr != null) {
            return m10277b(bArr, 0, bArr.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    /* renamed from: b */
    public C2244c m10277b(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        C2261u.m10423a((long) bArr.length, (long) i, (long) i2);
        int i3 = i + i2;
        while (i < i3) {
            C2257o e = m10288e(1);
            int min = Math.min(i3 - i, 8192 - e.f6970c);
            System.arraycopy(bArr, i, e.f6968a, e.f6970c, min);
            i += min;
            e.f6970c = min + e.f6970c;
        }
        this.f6936b += (long) i2;
        return this;
    }

    /* renamed from: b */
    public /* synthetic */ C2242d mo3175b(C2245f c2245f) {
        return m10266a(c2245f);
    }

    /* renamed from: b */
    public /* synthetic */ C2242d mo3176b(String str) {
        return m10267a(str);
    }

    /* renamed from: c */
    public C2244c mo3177c() {
        return this;
    }

    /* renamed from: c */
    public C2244c m10281c(int i) {
        C2257o e = m10288e(2);
        byte[] bArr = e.f6968a;
        int i2 = e.f6970c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        i2 = i3 + 1;
        bArr[i3] = (byte) (i & 255);
        e.f6970c = i2;
        this.f6936b += 2;
        return this;
    }

    /* renamed from: c */
    public /* synthetic */ C2242d mo3178c(byte[] bArr) {
        return m10276b(bArr);
    }

    /* renamed from: c */
    public /* synthetic */ C2242d mo3179c(byte[] bArr, int i, int i2) {
        return m10277b(bArr, i, i2);
    }

    /* renamed from: c */
    public C2245f mo3180c(long j) {
        return new C2245f(mo3183f(j));
    }

    public /* synthetic */ Object clone() {
        return m10314s();
    }

    public void close() {
    }

    /* renamed from: d */
    public C2244c m10285d() {
        return this;
    }

    /* renamed from: d */
    public C2244c m10286d(int i) {
        C2257o e = m10288e(4);
        byte[] bArr = e.f6968a;
        int i2 = e.f6970c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        i2 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        i2 = i3 + 1;
        bArr[i3] = (byte) (i & 255);
        e.f6970c = i2;
        this.f6936b += 4;
        return this;
    }

    /* renamed from: d */
    public String m10287d(long j) {
        return m10270a(j, C2261u.f6979a);
    }

    /* renamed from: e */
    C2257o m10288e(int i) {
        if (i < 1 || i > MessagesController.UPDATE_MASK_CHANNEL) {
            throw new IllegalArgumentException();
        } else if (this.f6935a == null) {
            this.f6935a = C2258p.m10403a();
            C2257o c2257o = this.f6935a;
            C2257o c2257o2 = this.f6935a;
            r0 = this.f6935a;
            c2257o2.f6974g = r0;
            c2257o.f6973f = r0;
            return r0;
        } else {
            r0 = this.f6935a.f6974g;
            return (r0.f6970c + i > MessagesController.UPDATE_MASK_CHANNEL || !r0.f6972e) ? r0.m10400a(C2258p.m10403a()) : r0;
        }
    }

    /* renamed from: e */
    String m10289e(long j) {
        if (j <= 0 || m10273b(j - 1) != (byte) 13) {
            String d = m10287d(j);
            mo3185g(1);
            return d;
        }
        d = m10287d(j - 1);
        mo3185g(2);
        return d;
    }

    /* renamed from: e */
    public boolean mo3181e() {
        return this.f6936b == 0;
    }

    public boolean equals(Object obj) {
        long j = 0;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2244c)) {
            return false;
        }
        C2244c c2244c = (C2244c) obj;
        if (this.f6936b != c2244c.f6936b) {
            return false;
        }
        if (this.f6936b == 0) {
            return true;
        }
        C2257o c2257o = this.f6935a;
        C2257o c2257o2 = c2244c.f6935a;
        int i = c2257o.f6969b;
        int i2 = c2257o2.f6969b;
        while (j < this.f6936b) {
            long min = (long) Math.min(c2257o.f6970c - i, c2257o2.f6970c - i2);
            int i3 = 0;
            while (((long) i3) < min) {
                int i4 = i + 1;
                byte b = c2257o.f6968a[i];
                i = i2 + 1;
                if (b != c2257o2.f6968a[i2]) {
                    return false;
                }
                i3++;
                i2 = i;
                i = i4;
            }
            if (i == c2257o.f6970c) {
                c2257o = c2257o.f6973f;
                i = c2257o.f6969b;
            }
            if (i2 == c2257o2.f6970c) {
                c2257o2 = c2257o2.f6973f;
                i2 = c2257o2.f6969b;
            }
            j += min;
        }
        return true;
    }

    /* renamed from: f */
    public C2245f m10291f(int i) {
        return i == 0 ? C2245f.f6938b : new C2259q(this, i);
    }

    /* renamed from: f */
    public InputStream mo3182f() {
        return new C22411(this);
    }

    /* renamed from: f */
    public byte[] mo3183f(long j) {
        C2261u.m10423a(this.f6936b, 0, j);
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        byte[] bArr = new byte[((int) j)];
        m10272a(bArr);
        return bArr;
    }

    public void flush() {
    }

    /* renamed from: g */
    public long m10294g() {
        long j = this.f6936b;
        if (j == 0) {
            return 0;
        }
        C2257o c2257o = this.f6935a.f6974g;
        return (c2257o.f6970c >= MessagesController.UPDATE_MASK_CHANNEL || !c2257o.f6972e) ? j : j - ((long) (c2257o.f6970c - c2257o.f6969b));
    }

    /* renamed from: g */
    public /* synthetic */ C2242d mo3184g(int i) {
        return m10286d(i);
    }

    /* renamed from: g */
    public void mo3185g(long j) {
        while (j > 0) {
            if (this.f6935a == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j, (long) (this.f6935a.f6970c - this.f6935a.f6969b));
            this.f6936b -= (long) min;
            j -= (long) min;
            C2257o c2257o = this.f6935a;
            c2257o.f6969b = min + c2257o.f6969b;
            if (this.f6935a.f6969b == this.f6935a.f6970c) {
                C2257o c2257o2 = this.f6935a;
                this.f6935a = c2257o2.m10398a();
                C2258p.m10404a(c2257o2);
            }
        }
    }

    /* renamed from: h */
    public byte mo3186h() {
        if (this.f6936b == 0) {
            throw new IllegalStateException("size == 0");
        }
        C2257o c2257o = this.f6935a;
        int i = c2257o.f6969b;
        int i2 = c2257o.f6970c;
        int i3 = i + 1;
        byte b = c2257o.f6968a[i];
        this.f6936b--;
        if (i3 == i2) {
            this.f6935a = c2257o.m10398a();
            C2258p.m10404a(c2257o);
        } else {
            c2257o.f6969b = i3;
        }
        return b;
    }

    /* renamed from: h */
    public C2244c m10298h(long j) {
        if (j == 0) {
            return m10275b(48);
        }
        long j2;
        Object obj;
        if (j < 0) {
            j2 = -j;
            if (j2 < 0) {
                return m10267a("-9223372036854775808");
            }
            obj = 1;
        } else {
            obj = null;
            j2 = j;
        }
        int i = j2 < 100000000 ? j2 < 10000 ? j2 < 100 ? j2 < 10 ? 1 : 2 : j2 < 1000 ? 3 : 4 : j2 < C3446C.MICROS_PER_SECOND ? j2 < 100000 ? 5 : 6 : j2 < 10000000 ? 7 : 8 : j2 < 1000000000000L ? j2 < 10000000000L ? j2 < C3446C.NANOS_PER_SECOND ? 9 : 10 : j2 < 100000000000L ? 11 : 12 : j2 < 1000000000000000L ? j2 < 10000000000000L ? 13 : j2 < 100000000000000L ? 14 : 15 : j2 < 100000000000000000L ? j2 < 10000000000000000L ? 16 : 17 : j2 < 1000000000000000000L ? 18 : 19;
        if (obj != null) {
            i++;
        }
        C2257o e = m10288e(i);
        byte[] bArr = e.f6968a;
        int i2 = e.f6970c + i;
        while (j2 != 0) {
            i2--;
            bArr[i2] = f6934c[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (obj != null) {
            bArr[i2 - 1] = (byte) 45;
        }
        e.f6970c += i;
        this.f6936b = ((long) i) + this.f6936b;
        return this;
    }

    /* renamed from: h */
    public /* synthetic */ C2242d mo3187h(int i) {
        return m10281c(i);
    }

    public int hashCode() {
        C2257o c2257o = this.f6935a;
        if (c2257o == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = c2257o.f6969b;
            while (i2 < c2257o.f6970c) {
                int i3 = c2257o.f6968a[i2] + (i * 31);
                i2++;
                i = i3;
            }
            c2257o = c2257o.f6973f;
        } while (c2257o != this.f6935a);
        return i;
    }

    /* renamed from: i */
    public C2244c m10300i(long j) {
        if (j == 0) {
            return m10275b(48);
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        C2257o e = m10288e(numberOfTrailingZeros);
        byte[] bArr = e.f6968a;
        int i = e.f6970c;
        for (int i2 = (e.f6970c + numberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = f6934c[(int) (15 & j)];
            j >>>= 4;
        }
        e.f6970c += numberOfTrailingZeros;
        this.f6936b = ((long) numberOfTrailingZeros) + this.f6936b;
        return this;
    }

    /* renamed from: i */
    public /* synthetic */ C2242d mo3188i(int i) {
        return m10275b(i);
    }

    /* renamed from: i */
    public short mo3189i() {
        if (this.f6936b < 2) {
            throw new IllegalStateException("size < 2: " + this.f6936b);
        }
        C2257o c2257o = this.f6935a;
        int i = c2257o.f6969b;
        int i2 = c2257o.f6970c;
        if (i2 - i < 2) {
            return (short) (((mo3186h() & 255) << 8) | (mo3186h() & 255));
        }
        byte[] bArr = c2257o.f6968a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        i = ((bArr[i] & 255) << 8) | (bArr[i3] & 255);
        this.f6936b -= 2;
        if (i4 == i2) {
            this.f6935a = c2257o.m10398a();
            C2258p.m10404a(c2257o);
        } else {
            c2257o.f6969b = i4;
        }
        return (short) i;
    }

    /* renamed from: j */
    public int mo3190j() {
        if (this.f6936b < 4) {
            throw new IllegalStateException("size < 4: " + this.f6936b);
        }
        C2257o c2257o = this.f6935a;
        int i = c2257o.f6969b;
        int i2 = c2257o.f6970c;
        if (i2 - i < 4) {
            return ((((mo3186h() & 255) << 24) | ((mo3186h() & 255) << 16)) | ((mo3186h() & 255) << 8)) | (mo3186h() & 255);
        }
        byte[] bArr = c2257o.f6968a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        i = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
        i3 = i4 + 1;
        i |= (bArr[i4] & 255) << 8;
        i4 = i3 + 1;
        i |= bArr[i3] & 255;
        this.f6936b -= 4;
        if (i4 == i2) {
            this.f6935a = c2257o.m10398a();
            C2258p.m10404a(c2257o);
            return i;
        }
        c2257o.f6969b = i4;
        return i;
    }

    /* renamed from: j */
    public /* synthetic */ C2242d mo3191j(long j) {
        return m10300i(j);
    }

    /* renamed from: k */
    public /* synthetic */ C2242d mo3192k(long j) {
        return m10298h(j);
    }

    /* renamed from: k */
    public short mo3193k() {
        return C2261u.m10422a(mo3189i());
    }

    /* renamed from: l */
    public int mo3194l() {
        return C2261u.m10421a(mo3190j());
    }

    /* renamed from: m */
    public long mo3195m() {
        if (this.f6936b == 0) {
            throw new IllegalStateException("size == 0");
        }
        long j = 0;
        int i = 0;
        Object obj = null;
        do {
            C2257o c2257o = this.f6935a;
            byte[] bArr = c2257o.f6968a;
            int i2 = c2257o.f6969b;
            int i3 = c2257o.f6970c;
            int i4 = i2;
            while (i4 < i3) {
                byte b = bArr[i4];
                if (b >= (byte) 48 && b <= (byte) 57) {
                    i2 = b - 48;
                } else if (b >= (byte) 97 && b <= (byte) 102) {
                    i2 = (b - 97) + 10;
                } else if (b < (byte) 65 || b > (byte) 70) {
                    if (i != 0) {
                        obj = 1;
                        if (i4 != i3) {
                            this.f6935a = c2257o.m10398a();
                            C2258p.m10404a(c2257o);
                        } else {
                            c2257o.f6969b = i4;
                        }
                        if (obj == null) {
                            break;
                        }
                    } else {
                        throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(b));
                    }
                } else {
                    i2 = (b - 65) + 10;
                }
                if ((-1152921504606846976L & j) != 0) {
                    throw new NumberFormatException("Number too large: " + new C2244c().m10300i(j).m10275b((int) b).m10310o());
                }
                i++;
                i4++;
                j = ((long) i2) | (j << 4);
            }
            if (i4 != i3) {
                c2257o.f6969b = i4;
            } else {
                this.f6935a = c2257o.m10398a();
                C2258p.m10404a(c2257o);
            }
            if (obj == null) {
                break;
            }
        } while (this.f6935a != null);
        this.f6936b -= (long) i;
        return j;
    }

    /* renamed from: n */
    public C2245f m10309n() {
        return new C2245f(mo3197q());
    }

    /* renamed from: o */
    public String m10310o() {
        try {
            return m10270a(this.f6936b, C2261u.f6979a);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: p */
    public String mo3196p() {
        long a = mo3172a((byte) 10);
        if (a != -1) {
            return m10289e(a);
        }
        C2244c c2244c = new C2244c();
        m10265a(c2244c, 0, Math.min(32, this.f6936b));
        throw new EOFException("\\n not found: size=" + m10274b() + " content=" + c2244c.m10309n().mo3214c() + "…");
    }

    /* renamed from: q */
    public byte[] mo3197q() {
        try {
            return mo3183f(this.f6936b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: r */
    public void m10313r() {
        try {
            mo3185g(this.f6936b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    /* renamed from: s */
    public C2244c m10314s() {
        C2244c c2244c = new C2244c();
        if (this.f6936b == 0) {
            return c2244c;
        }
        c2244c.f6935a = new C2257o(this.f6935a);
        C2257o c2257o = c2244c.f6935a;
        C2257o c2257o2 = c2244c.f6935a;
        C2257o c2257o3 = c2244c.f6935a;
        c2257o2.f6974g = c2257o3;
        c2257o.f6973f = c2257o3;
        for (c2257o = this.f6935a.f6973f; c2257o != this.f6935a; c2257o = c2257o.f6973f) {
            c2244c.f6935a.f6974g.m10400a(new C2257o(c2257o));
        }
        c2244c.f6936b = this.f6936b;
        return c2244c;
    }

    /* renamed from: t */
    public C2245f m10315t() {
        if (this.f6936b <= 2147483647L) {
            return m10291f((int) this.f6936b);
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.f6936b);
    }

    public String toString() {
        return m10315t().toString();
    }

    /* renamed from: u */
    public /* synthetic */ C2242d mo3198u() {
        return m10285d();
    }
}
