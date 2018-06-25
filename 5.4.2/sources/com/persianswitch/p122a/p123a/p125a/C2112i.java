package com.persianswitch.p122a.p123a.p125a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p125a.C2075b.C2074a;
import com.persianswitch.p122a.p123a.p125a.C2106h.C2104a;
import com.persianswitch.p122a.p123a.p125a.C2106h.C2105b;
import com.persianswitch.p126b.C2096s;
import com.persianswitch.p126b.C2098t;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2245f;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.MessagesController;

/* renamed from: com.persianswitch.a.a.a.i */
public final class C2112i implements C2111q {
    /* renamed from: a */
    private static final Logger f6415a = Logger.getLogger(C2108b.class.getName());
    /* renamed from: b */
    private static final C2245f f6416b = C2245f.m10318a("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");

    /* renamed from: com.persianswitch.a.a.a.i$a */
    static final class C2107a implements C2096s {
        /* renamed from: a */
        int f6396a;
        /* renamed from: b */
        byte f6397b;
        /* renamed from: c */
        int f6398c;
        /* renamed from: d */
        int f6399d;
        /* renamed from: e */
        short f6400e;
        /* renamed from: f */
        private final C2243e f6401f;

        public C2107a(C2243e c2243e) {
            this.f6401f = c2243e;
        }

        /* renamed from: b */
        private void m9495b() {
            int i = this.f6398c;
            int a = C2112i.m9537b(this.f6401f);
            this.f6399d = a;
            this.f6396a = a;
            byte h = (byte) (this.f6401f.mo3186h() & 255);
            this.f6397b = (byte) (this.f6401f.mo3186h() & 255);
            if (C2112i.f6415a.isLoggable(Level.FINE)) {
                C2112i.f6415a.fine(C2108b.m9499a(true, this.f6398c, this.f6396a, h, this.f6397b));
            }
            this.f6398c = this.f6401f.mo3190j() & Integer.MAX_VALUE;
            if (h != (byte) 9) {
                throw C2112i.m9542d("%s != TYPE_CONTINUATION", Byte.valueOf(h));
            } else if (this.f6398c != i) {
                throw C2112i.m9542d("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        /* renamed from: a */
        public long mo3105a(C2244c c2244c, long j) {
            while (this.f6399d == 0) {
                this.f6401f.mo3185g((long) this.f6400e);
                this.f6400e = (short) 0;
                if ((this.f6397b & 4) != 0) {
                    return -1;
                }
                m9495b();
            }
            long a = this.f6401f.mo3105a(c2244c, Math.min(j, (long) this.f6399d));
            if (a == -1) {
                return -1;
            }
            this.f6399d = (int) (((long) this.f6399d) - a);
            return a;
        }

        /* renamed from: a */
        public C2098t mo3106a() {
            return this.f6401f.mo3106a();
        }

        public void close() {
        }
    }

    /* renamed from: com.persianswitch.a.a.a.i$b */
    static final class C2108b {
        /* renamed from: a */
        private static final String[] f6402a = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
        /* renamed from: b */
        private static final String[] f6403b = new String[64];
        /* renamed from: c */
        private static final String[] f6404c = new String[256];

        static {
            int i = 0;
            for (int i2 = 0; i2 < f6404c.length; i2++) {
                f6404c[i2] = C2187l.m9892a("%8s", Integer.toBinaryString(i2)).replace(' ', '0');
            }
            f6403b[0] = TtmlNode.ANONYMOUS_REGION_ID;
            f6403b[1] = "END_STREAM";
            int[] iArr = new int[]{1};
            f6403b[8] = "PADDED";
            for (int i3 : iArr) {
                f6403b[i3 | 8] = f6403b[i3] + "|PADDED";
            }
            f6403b[4] = "END_HEADERS";
            f6403b[32] = "PRIORITY";
            f6403b[36] = "END_HEADERS|PRIORITY";
            for (int i4 : new int[]{4, 32, 36}) {
                for (int i5 : iArr) {
                    f6403b[i5 | i4] = f6403b[i5] + '|' + f6403b[i4];
                    f6403b[(i5 | i4) | 8] = f6403b[i5] + '|' + f6403b[i4] + "|PADDED";
                }
            }
            while (i < f6403b.length) {
                if (f6403b[i] == null) {
                    f6403b[i] = f6404c[i];
                }
                i++;
            }
        }

        C2108b() {
        }

        /* renamed from: a */
        static String m9498a(byte b, byte b2) {
            if (b2 == (byte) 0) {
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
            switch (b) {
                case (byte) 2:
                case (byte) 3:
                case (byte) 7:
                case (byte) 8:
                    return f6404c[b2];
                case (byte) 4:
                case (byte) 6:
                    return b2 == (byte) 1 ? "ACK" : f6404c[b2];
                default:
                    String str = b2 < f6403b.length ? f6403b[b2] : f6404c[b2];
                    return (b != (byte) 5 || (b2 & 4) == 0) ? (b != (byte) 0 || (b2 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED") : str.replace("HEADERS", "PUSH_PROMISE");
            }
        }

        /* renamed from: a */
        static String m9499a(boolean z, int i, int i2, byte b, byte b2) {
            String a = b < f6402a.length ? f6402a[b] : C2187l.m9892a("0x%02x", Byte.valueOf(b));
            String a2 = C2108b.m9498a(b, b2);
            String str = "%s 0x%08x %5d %-13s %s";
            Object[] objArr = new Object[5];
            objArr[0] = z ? "<<" : ">>";
            objArr[1] = Integer.valueOf(i);
            objArr[2] = Integer.valueOf(i2);
            objArr[3] = a;
            objArr[4] = a2;
            return C2187l.m9892a(str, objArr);
        }
    }

    /* renamed from: com.persianswitch.a.a.a.i$c */
    static final class C2109c implements C2075b {
        /* renamed from: a */
        final C2104a f6405a;
        /* renamed from: b */
        private final C2243e f6406b;
        /* renamed from: c */
        private final C2107a f6407c = new C2107a(this.f6406b);
        /* renamed from: d */
        private final boolean f6408d;

        C2109c(C2243e c2243e, int i, boolean z) {
            this.f6406b = c2243e;
            this.f6408d = z;
            this.f6405a = new C2104a(i, this.f6407c);
        }

        /* renamed from: a */
        private List<C2102f> m9500a(int i, short s, byte b, int i2) {
            C2107a c2107a = this.f6407c;
            this.f6407c.f6399d = i;
            c2107a.f6396a = i;
            this.f6407c.f6400e = s;
            this.f6407c.f6397b = b;
            this.f6407c.f6398c = i2;
            this.f6405a.m9479a();
            return this.f6405a.m9481b();
        }

        /* renamed from: a */
        private void m9501a(C2074a c2074a, int i) {
            int j = this.f6406b.mo3190j();
            c2074a.mo3092a(i, j & Integer.MAX_VALUE, (this.f6406b.mo3186h() & 255) + 1, (Integer.MIN_VALUE & j) != 0);
        }

        /* renamed from: a */
        private void m9502a(C2074a c2074a, int i, byte b, int i2) {
            if (i2 == 0) {
                throw C2112i.m9542d("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            }
            boolean z = (b & 1) != 0;
            short h = (b & 8) != 0 ? (short) (this.f6406b.mo3186h() & 255) : (short) 0;
            if ((b & 32) != 0) {
                m9501a(c2074a, i2);
                i -= 5;
            }
            c2074a.mo3100a(false, z, i2, -1, m9500a(C2112i.m9536b(i, b, h), h, b, i2), C2103g.HTTP_20_HEADERS);
        }

        /* renamed from: b */
        private void m9503b(C2074a c2074a, int i, byte b, int i2) {
            short s = (short) 1;
            short s2 = (short) 0;
            boolean z = (b & 1) != 0;
            if ((b & 32) == 0) {
                s = (short) 0;
            }
            if (s != (short) 0) {
                throw C2112i.m9542d("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            }
            if ((b & 8) != 0) {
                s2 = (short) (this.f6406b.mo3186h() & 255);
            }
            c2074a.mo3098a(z, i2, this.f6406b, C2112i.m9536b(i, b, s2));
            this.f6406b.mo3185g((long) s2);
        }

        /* renamed from: c */
        private void m9504c(C2074a c2074a, int i, byte b, int i2) {
            if (i != 5) {
                throw C2112i.m9542d("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i));
            } else if (i2 == 0) {
                throw C2112i.m9542d("TYPE_PRIORITY streamId == 0", new Object[0]);
            } else {
                m9501a(c2074a, i2);
            }
        }

        /* renamed from: d */
        private void m9505d(C2074a c2074a, int i, byte b, int i2) {
            if (i != 4) {
                throw C2112i.m9542d("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i));
            } else if (i2 == 0) {
                throw C2112i.m9542d("TYPE_RST_STREAM streamId == 0", new Object[0]);
            } else {
                C2073a b2 = C2073a.m9289b(this.f6406b.mo3190j());
                if (b2 == null) {
                    throw C2112i.m9542d("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(r0));
                } else {
                    c2074a.mo3095a(i2, b2);
                }
            }
        }

        /* renamed from: e */
        private void m9506e(C2074a c2074a, int i, byte b, int i2) {
            if (i2 != 0) {
                throw C2112i.m9542d("TYPE_SETTINGS streamId != 0", new Object[0]);
            } else if ((b & 1) != 0) {
                if (i != 0) {
                    throw C2112i.m9542d("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                c2074a.mo3091a();
            } else if (i % 6 != 0) {
                throw C2112i.m9542d("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i));
            } else {
                C2122n c2122n = new C2122n();
                for (int i3 = 0; i3 < i; i3 += 6) {
                    int i4 = this.f6406b.mo3189i();
                    int j = this.f6406b.mo3190j();
                    switch (i4) {
                        case 2:
                            if (!(j == 0 || j == 1)) {
                                throw C2112i.m9542d("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                        case 3:
                            i4 = 4;
                            break;
                        case 4:
                            i4 = 7;
                            if (j >= 0) {
                                break;
                            }
                            throw C2112i.m9542d("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                        case 5:
                            if (j >= MessagesController.UPDATE_MASK_CHAT_ADMINS && j <= 16777215) {
                                break;
                            }
                            throw C2112i.m9542d("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(j));
                            break;
                        default:
                            break;
                    }
                    c2122n.m9572a(i4, 0, j);
                }
                c2074a.mo3099a(false, c2122n);
                if (c2122n.m9578c() >= 0) {
                    this.f6405a.m9480a(c2122n.m9578c());
                }
            }
        }

        /* renamed from: f */
        private void m9507f(C2074a c2074a, int i, byte b, int i2) {
            short s = (short) 0;
            if (i2 == 0) {
                throw C2112i.m9542d("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            }
            if ((b & 8) != 0) {
                s = (short) (this.f6406b.mo3186h() & 255);
            }
            c2074a.mo3093a(i2, this.f6406b.mo3190j() & Integer.MAX_VALUE, m9500a(C2112i.m9536b(i - 4, b, s), s, b, i2));
        }

        /* renamed from: g */
        private void m9508g(C2074a c2074a, int i, byte b, int i2) {
            boolean z = true;
            if (i != 8) {
                throw C2112i.m9542d("TYPE_PING length != 8: %s", Integer.valueOf(i));
            } else if (i2 != 0) {
                throw C2112i.m9542d("TYPE_PING streamId != 0", new Object[0]);
            } else {
                int j = this.f6406b.mo3190j();
                int j2 = this.f6406b.mo3190j();
                if ((b & 1) == 0) {
                    z = false;
                }
                c2074a.mo3097a(z, j, j2);
            }
        }

        /* renamed from: h */
        private void m9509h(C2074a c2074a, int i, byte b, int i2) {
            if (i < 8) {
                throw C2112i.m9542d("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i));
            } else if (i2 != 0) {
                throw C2112i.m9542d("TYPE_GOAWAY streamId != 0", new Object[0]);
            } else {
                int j = this.f6406b.mo3190j();
                int i3 = i - 8;
                C2073a b2 = C2073a.m9289b(this.f6406b.mo3190j());
                if (b2 == null) {
                    throw C2112i.m9542d("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(r0));
                }
                C2245f c2245f = C2245f.f6938b;
                if (i3 > 0) {
                    c2245f = this.f6406b.mo3180c((long) i3);
                }
                c2074a.mo3096a(j, b2, c2245f);
            }
        }

        /* renamed from: i */
        private void m9510i(C2074a c2074a, int i, byte b, int i2) {
            if (i != 4) {
                throw C2112i.m9542d("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i));
            }
            long j = ((long) this.f6406b.mo3190j()) & 2147483647L;
            if (j == 0) {
                throw C2112i.m9542d("windowSizeIncrement was 0", Long.valueOf(j));
            } else {
                c2074a.mo3094a(i2, j);
            }
        }

        /* renamed from: a */
        public void mo3110a() {
            if (!this.f6408d) {
                C2245f c = this.f6406b.mo3180c((long) C2112i.f6416b.mo3216e());
                if (C2112i.f6415a.isLoggable(Level.FINE)) {
                    C2112i.f6415a.fine(C2187l.m9892a("<< CONNECTION %s", c.mo3214c()));
                }
                if (!C2112i.f6416b.equals(c)) {
                    throw C2112i.m9542d("Expected a connection header but was %s", c.mo3209a());
                }
            }
        }

        /* renamed from: a */
        public boolean mo3111a(C2074a c2074a) {
            try {
                this.f6406b.mo3174a(9);
                int a = C2112i.m9537b(this.f6406b);
                if (a < 0 || a > MessagesController.UPDATE_MASK_CHAT_ADMINS) {
                    throw C2112i.m9542d("FRAME_SIZE_ERROR: %s", Integer.valueOf(a));
                }
                byte h = (byte) (this.f6406b.mo3186h() & 255);
                byte h2 = (byte) (this.f6406b.mo3186h() & 255);
                int j = this.f6406b.mo3190j() & Integer.MAX_VALUE;
                if (C2112i.f6415a.isLoggable(Level.FINE)) {
                    C2112i.f6415a.fine(C2108b.m9499a(true, j, a, h, h2));
                }
                switch (h) {
                    case (byte) 0:
                        m9503b(c2074a, a, h2, j);
                        return true;
                    case (byte) 1:
                        m9502a(c2074a, a, h2, j);
                        return true;
                    case (byte) 2:
                        m9504c(c2074a, a, h2, j);
                        return true;
                    case (byte) 3:
                        m9505d(c2074a, a, h2, j);
                        return true;
                    case (byte) 4:
                        m9506e(c2074a, a, h2, j);
                        return true;
                    case (byte) 5:
                        m9507f(c2074a, a, h2, j);
                        return true;
                    case (byte) 6:
                        m9508g(c2074a, a, h2, j);
                        return true;
                    case (byte) 7:
                        m9509h(c2074a, a, h2, j);
                        return true;
                    case (byte) 8:
                        m9510i(c2074a, a, h2, j);
                        return true;
                    default:
                        this.f6406b.mo3185g((long) a);
                        return true;
                }
            } catch (IOException e) {
                return false;
            }
        }

        public void close() {
            this.f6406b.close();
        }
    }

    /* renamed from: com.persianswitch.a.a.a.i$d */
    static final class C2110d implements C2076c {
        /* renamed from: a */
        private final C2242d f6409a;
        /* renamed from: b */
        private final boolean f6410b;
        /* renamed from: c */
        private final C2244c f6411c = new C2244c();
        /* renamed from: d */
        private final C2105b f6412d = new C2105b(this.f6411c);
        /* renamed from: e */
        private int f6413e = MessagesController.UPDATE_MASK_CHAT_ADMINS;
        /* renamed from: f */
        private boolean f6414f;

        C2110d(C2242d c2242d, boolean z) {
            this.f6409a = c2242d;
            this.f6410b = z;
        }

        /* renamed from: b */
        private void m9513b(int i, long j) {
            while (j > 0) {
                int min = (int) Math.min((long) this.f6413e, j);
                j -= (long) min;
                m9516a(i, min, (byte) 9, j == 0 ? (byte) 4 : (byte) 0);
                this.f6409a.a_(this.f6411c, (long) min);
            }
        }

        /* renamed from: a */
        public synchronized void mo3112a() {
            if (this.f6414f) {
                throw new IOException("closed");
            } else if (this.f6410b) {
                if (C2112i.f6415a.isLoggable(Level.FINE)) {
                    C2112i.f6415a.fine(C2187l.m9892a(">> CONNECTION %s", C2112i.f6416b.mo3214c()));
                }
                this.f6409a.mo3178c(C2112i.f6416b.mo3218f());
                this.f6409a.flush();
            }
        }

        /* renamed from: a */
        void m9515a(int i, byte b, C2244c c2244c, int i2) {
            m9516a(i, i2, (byte) 0, b);
            if (i2 > 0) {
                this.f6409a.a_(c2244c, (long) i2);
            }
        }

        /* renamed from: a */
        void m9516a(int i, int i2, byte b, byte b2) {
            if (C2112i.f6415a.isLoggable(Level.FINE)) {
                C2112i.f6415a.fine(C2108b.m9499a(false, i, i2, b, b2));
            }
            if (i2 > this.f6413e) {
                throw C2112i.m9541c("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(this.f6413e), Integer.valueOf(i2));
            } else if ((Integer.MIN_VALUE & i) != 0) {
                throw C2112i.m9541c("reserved bit set: %s", Integer.valueOf(i));
            } else {
                C2112i.m9540b(this.f6409a, i2);
                this.f6409a.mo3188i(b & 255);
                this.f6409a.mo3188i(b2 & 255);
                this.f6409a.mo3184g(Integer.MAX_VALUE & i);
            }
        }

        /* renamed from: a */
        public synchronized void mo3113a(int i, int i2, List<C2102f> list) {
            if (this.f6414f) {
                throw new IOException("closed");
            }
            this.f6412d.m9489a((List) list);
            long b = this.f6411c.m10274b();
            int min = (int) Math.min((long) (this.f6413e - 4), b);
            m9516a(i, min + 4, (byte) 5, b == ((long) min) ? (byte) 4 : (byte) 0);
            this.f6409a.mo3184g(Integer.MAX_VALUE & i2);
            this.f6409a.a_(this.f6411c, (long) min);
            if (b > ((long) min)) {
                m9513b(i, b - ((long) min));
            }
        }

        /* renamed from: a */
        public synchronized void mo3114a(int i, long j) {
            if (this.f6414f) {
                throw new IOException("closed");
            } else if (j == 0 || j > 2147483647L) {
                throw C2112i.m9541c("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j));
            } else {
                m9516a(i, 4, (byte) 8, (byte) 0);
                this.f6409a.mo3184g((int) j);
                this.f6409a.flush();
            }
        }

        /* renamed from: a */
        public synchronized void mo3115a(int i, C2073a c2073a) {
            if (this.f6414f) {
                throw new IOException("closed");
            } else if (c2073a.f6260s == -1) {
                throw new IllegalArgumentException();
            } else {
                m9516a(i, 4, (byte) 3, (byte) 0);
                this.f6409a.mo3184g(c2073a.f6260s);
                this.f6409a.flush();
            }
        }

        /* renamed from: a */
        public synchronized void mo3116a(int i, C2073a c2073a, byte[] bArr) {
            if (this.f6414f) {
                throw new IOException("closed");
            } else if (c2073a.f6260s == -1) {
                throw C2112i.m9541c("errorCode.httpCode == -1", new Object[0]);
            } else {
                m9516a(0, bArr.length + 8, (byte) 7, (byte) 0);
                this.f6409a.mo3184g(i);
                this.f6409a.mo3184g(c2073a.f6260s);
                if (bArr.length > 0) {
                    this.f6409a.mo3178c(bArr);
                }
                this.f6409a.flush();
            }
        }

        /* renamed from: a */
        public synchronized void mo3117a(C2122n c2122n) {
            if (this.f6414f) {
                throw new IOException("closed");
            }
            this.f6413e = c2122n.m9581e(this.f6413e);
            m9516a(0, 0, (byte) 4, (byte) 1);
            this.f6409a.flush();
        }

        /* renamed from: a */
        public synchronized void mo3118a(boolean z, int i, int i2) {
            byte b = (byte) 0;
            synchronized (this) {
                if (this.f6414f) {
                    throw new IOException("closed");
                }
                if (z) {
                    b = (byte) 1;
                }
                m9516a(0, 8, (byte) 6, b);
                this.f6409a.mo3184g(i);
                this.f6409a.mo3184g(i2);
                this.f6409a.flush();
            }
        }

        /* renamed from: a */
        public synchronized void mo3119a(boolean z, int i, C2244c c2244c, int i2) {
            if (this.f6414f) {
                throw new IOException("closed");
            }
            byte b = (byte) 0;
            if (z) {
                b = (byte) 1;
            }
            m9515a(i, b, c2244c, i2);
        }

        /* renamed from: a */
        void m9524a(boolean z, int i, List<C2102f> list) {
            if (this.f6414f) {
                throw new IOException("closed");
            }
            this.f6412d.m9489a((List) list);
            long b = this.f6411c.m10274b();
            int min = (int) Math.min((long) this.f6413e, b);
            byte b2 = b == ((long) min) ? (byte) 4 : (byte) 0;
            if (z) {
                b2 = (byte) (b2 | 1);
            }
            m9516a(i, min, (byte) 1, b2);
            this.f6409a.a_(this.f6411c, (long) min);
            if (b > ((long) min)) {
                m9513b(i, b - ((long) min));
            }
        }

        /* renamed from: a */
        public synchronized void mo3120a(boolean z, boolean z2, int i, int i2, List<C2102f> list) {
            if (z2) {
                throw new UnsupportedOperationException();
            } else if (this.f6414f) {
                throw new IOException("closed");
            } else {
                m9524a(z, i, (List) list);
            }
        }

        /* renamed from: b */
        public synchronized void mo3121b() {
            if (this.f6414f) {
                throw new IOException("closed");
            }
            this.f6409a.flush();
        }

        /* renamed from: b */
        public synchronized void mo3122b(C2122n c2122n) {
            int i = 0;
            synchronized (this) {
                if (this.f6414f) {
                    throw new IOException("closed");
                }
                m9516a(0, c2122n.m9576b() * 6, (byte) 4, (byte) 0);
                while (i < 10) {
                    if (c2122n.m9575a(i)) {
                        int i2 = i == 4 ? 3 : i == 7 ? 4 : i;
                        this.f6409a.mo3187h(i2);
                        this.f6409a.mo3184g(c2122n.m9577b(i));
                    }
                    i++;
                }
                this.f6409a.flush();
            }
        }

        /* renamed from: c */
        public int mo3123c() {
            return this.f6413e;
        }

        public synchronized void close() {
            this.f6414f = true;
            this.f6409a.close();
        }
    }

    /* renamed from: b */
    private static int m9536b(int i, byte b, short s) {
        if ((b & 8) != 0) {
            short s2 = i - 1;
        }
        if (s <= s2) {
            return (short) (s2 - s);
        }
        throw C2112i.m9542d("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(s2));
    }

    /* renamed from: b */
    private static int m9537b(C2243e c2243e) {
        return (((c2243e.mo3186h() & 255) << 16) | ((c2243e.mo3186h() & 255) << 8)) | (c2243e.mo3186h() & 255);
    }

    /* renamed from: b */
    private static void m9540b(C2242d c2242d, int i) {
        c2242d.mo3188i((i >>> 16) & 255);
        c2242d.mo3188i((i >>> 8) & 255);
        c2242d.mo3188i(i & 255);
    }

    /* renamed from: c */
    private static IllegalArgumentException m9541c(String str, Object... objArr) {
        throw new IllegalArgumentException(C2187l.m9892a(str, objArr));
    }

    /* renamed from: d */
    private static IOException m9542d(String str, Object... objArr) {
        throw new IOException(C2187l.m9892a(str, objArr));
    }

    /* renamed from: a */
    public C2075b mo3124a(C2243e c2243e, boolean z) {
        return new C2109c(c2243e, 4096, z);
    }

    /* renamed from: a */
    public C2076c mo3125a(C2242d c2242d, boolean z) {
        return new C2110d(c2242d, z);
    }
}
