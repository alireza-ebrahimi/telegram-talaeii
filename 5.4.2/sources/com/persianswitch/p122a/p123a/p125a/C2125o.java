package com.persianswitch.p122a.p123a.p125a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p122a.p123a.p125a.C2075b.C2074a;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2245f;
import com.persianswitch.p126b.C2246g;
import com.persianswitch.p126b.C2253l;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.util.List;
import java.util.zip.Deflater;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: com.persianswitch.a.a.a.o */
public final class C2125o implements C2111q {
    /* renamed from: a */
    static final byte[] f6446a;

    /* renamed from: com.persianswitch.a.a.a.o$a */
    static final class C2123a implements C2075b {
        /* renamed from: a */
        private final C2243e f6438a;
        /* renamed from: b */
        private final boolean f6439b;
        /* renamed from: c */
        private final C2118k f6440c = new C2118k(this.f6438a);

        C2123a(C2243e c2243e, boolean z) {
            this.f6438a = c2243e;
            this.f6439b = z;
        }

        /* renamed from: a */
        private static IOException m9585a(String str, Object... objArr) {
            throw new IOException(C2187l.m9892a(str, objArr));
        }

        /* renamed from: a */
        private void m9586a(C2074a c2074a, int i, int i2) {
            boolean z = true;
            int j = this.f6438a.mo3190j() & Integer.MAX_VALUE;
            int j2 = this.f6438a.mo3190j() & Integer.MAX_VALUE;
            this.f6438a.mo3189i();
            List a = this.f6440c.m9559a(i2 - 10);
            boolean z2 = (i & 1) != 0;
            if ((i & 2) == 0) {
                z = false;
            }
            c2074a.mo3100a(z, z2, j, j2, a, C2103g.SPDY_SYN_STREAM);
        }

        /* renamed from: b */
        private void m9587b(C2074a c2074a, int i, int i2) {
            c2074a.mo3100a(false, (i & 1) != 0, this.f6438a.mo3190j() & Integer.MAX_VALUE, -1, this.f6440c.m9559a(i2 - 4), C2103g.SPDY_REPLY);
        }

        /* renamed from: c */
        private void m9588c(C2074a c2074a, int i, int i2) {
            if (i2 != 8) {
                throw C2123a.m9585a("TYPE_RST_STREAM length: %d != 8", Integer.valueOf(i2));
            }
            int j = this.f6438a.mo3190j() & Integer.MAX_VALUE;
            C2073a a = C2073a.m9288a(this.f6438a.mo3190j());
            if (a == null) {
                throw C2123a.m9585a("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(r1));
            } else {
                c2074a.mo3095a(j, a);
            }
        }

        /* renamed from: d */
        private void m9589d(C2074a c2074a, int i, int i2) {
            c2074a.mo3100a(false, false, this.f6438a.mo3190j() & Integer.MAX_VALUE, -1, this.f6440c.m9559a(i2 - 4), C2103g.SPDY_HEADERS);
        }

        /* renamed from: e */
        private void m9590e(C2074a c2074a, int i, int i2) {
            if (i2 != 8) {
                throw C2123a.m9585a("TYPE_WINDOW_UPDATE length: %d != 8", Integer.valueOf(i2));
            }
            int j = this.f6438a.mo3190j() & Integer.MAX_VALUE;
            long j2 = (long) (this.f6438a.mo3190j() & Integer.MAX_VALUE);
            if (j2 == 0) {
                throw C2123a.m9585a("windowSizeIncrement was 0", Long.valueOf(j2));
            } else {
                c2074a.mo3094a(j, j2);
            }
        }

        /* renamed from: f */
        private void m9591f(C2074a c2074a, int i, int i2) {
            boolean z = true;
            if (i2 != 4) {
                throw C2123a.m9585a("TYPE_PING length: %d != 4", Integer.valueOf(i2));
            }
            int j = this.f6438a.mo3190j();
            if (this.f6439b != ((j & 1) == 1)) {
                z = false;
            }
            c2074a.mo3097a(z, j, 0);
        }

        /* renamed from: g */
        private void m9592g(C2074a c2074a, int i, int i2) {
            if (i2 != 8) {
                throw C2123a.m9585a("TYPE_GOAWAY length: %d != 8", Integer.valueOf(i2));
            }
            int j = this.f6438a.mo3190j() & Integer.MAX_VALUE;
            C2073a c = C2073a.m9290c(this.f6438a.mo3190j());
            if (c == null) {
                throw C2123a.m9585a("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(r1));
            } else {
                c2074a.mo3096a(j, c, C2245f.f6938b);
            }
        }

        /* renamed from: h */
        private void m9593h(C2074a c2074a, int i, int i2) {
            boolean z = true;
            int j = this.f6438a.mo3190j();
            if (i2 != (j * 8) + 4) {
                throw C2123a.m9585a("TYPE_SETTINGS length: %d != 4 + 8 * %d", Integer.valueOf(i2), Integer.valueOf(j));
            }
            C2122n c2122n = new C2122n();
            for (int i3 = 0; i3 < j; i3++) {
                int j2 = this.f6438a.mo3190j();
                int i4 = (Theme.ACTION_BAR_VIDEO_EDIT_COLOR & j2) >>> 24;
                c2122n.m9572a(j2 & 16777215, i4, this.f6438a.mo3190j());
            }
            if ((i & 1) == 0) {
                z = false;
            }
            c2074a.mo3099a(z, c2122n);
        }

        /* renamed from: a */
        public void mo3110a() {
        }

        /* renamed from: a */
        public boolean mo3111a(C2074a c2074a) {
            boolean z = false;
            try {
                int j = this.f6438a.mo3190j();
                int j2 = this.f6438a.mo3190j();
                int i = (Theme.ACTION_BAR_VIDEO_EDIT_COLOR & j2) >>> 24;
                j2 &= 16777215;
                int i2;
                if ((Integer.MIN_VALUE & j) != 0) {
                    int i3 = (2147418112 & j) >>> 16;
                    i2 = 65535 & j;
                    if (i3 != 3) {
                        throw new ProtocolException("version != 3: " + i3);
                    }
                    switch (i2) {
                        case 1:
                            m9586a(c2074a, i, j2);
                            return true;
                        case 2:
                            m9587b(c2074a, i, j2);
                            return true;
                        case 3:
                            m9588c(c2074a, i, j2);
                            return true;
                        case 4:
                            m9593h(c2074a, i, j2);
                            return true;
                        case 6:
                            m9591f(c2074a, i, j2);
                            return true;
                        case 7:
                            m9592g(c2074a, i, j2);
                            return true;
                        case 8:
                            m9589d(c2074a, i, j2);
                            return true;
                        case 9:
                            m9590e(c2074a, i, j2);
                            return true;
                        default:
                            this.f6438a.mo3185g((long) j2);
                            return true;
                    }
                }
                i2 = Integer.MAX_VALUE & j;
                if ((i & 1) != 0) {
                    z = true;
                }
                c2074a.mo3098a(z, i2, this.f6438a, j2);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        public void close() {
            this.f6440c.m9560a();
        }
    }

    /* renamed from: com.persianswitch.a.a.a.o$b */
    static final class C2124b implements C2076c {
        /* renamed from: a */
        private final C2242d f6441a;
        /* renamed from: b */
        private final C2244c f6442b = new C2244c();
        /* renamed from: c */
        private final C2242d f6443c;
        /* renamed from: d */
        private final boolean f6444d;
        /* renamed from: e */
        private boolean f6445e;

        C2124b(C2242d c2242d, boolean z) {
            this.f6441a = c2242d;
            this.f6444d = z;
            Deflater deflater = new Deflater();
            deflater.setDictionary(C2125o.f6446a);
            this.f6443c = C2253l.m10357a(new C2246g(this.f6442b, deflater));
        }

        /* renamed from: a */
        private void m9596a(List<C2102f> list) {
            this.f6443c.mo3184g(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                C2245f c2245f = ((C2102f) list.get(i)).f6370h;
                this.f6443c.mo3184g(c2245f.mo3216e());
                this.f6443c.mo3175b(c2245f);
                c2245f = ((C2102f) list.get(i)).f6371i;
                this.f6443c.mo3184g(c2245f.mo3216e());
                this.f6443c.mo3175b(c2245f);
            }
            this.f6443c.flush();
        }

        /* renamed from: a */
        public synchronized void mo3112a() {
        }

        /* renamed from: a */
        void m9598a(int i, int i2, C2244c c2244c, int i3) {
            if (this.f6445e) {
                throw new IOException("closed");
            } else if (((long) i3) > 16777215) {
                throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + i3);
            } else {
                this.f6441a.mo3184g(Integer.MAX_VALUE & i);
                this.f6441a.mo3184g(((i2 & 255) << 24) | (16777215 & i3));
                if (i3 > 0) {
                    this.f6441a.a_(c2244c, (long) i3);
                }
            }
        }

        /* renamed from: a */
        public void mo3113a(int i, int i2, List<C2102f> list) {
        }

        /* renamed from: a */
        public synchronized void mo3114a(int i, long j) {
            if (this.f6445e) {
                throw new IOException("closed");
            } else if (j == 0 || j > 2147483647L) {
                throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + j);
            } else {
                this.f6441a.mo3184g(-2147287031);
                this.f6441a.mo3184g(8);
                this.f6441a.mo3184g(i);
                this.f6441a.mo3184g((int) j);
                this.f6441a.flush();
            }
        }

        /* renamed from: a */
        public synchronized void mo3115a(int i, C2073a c2073a) {
            if (this.f6445e) {
                throw new IOException("closed");
            } else if (c2073a.f6261t == -1) {
                throw new IllegalArgumentException();
            } else {
                this.f6441a.mo3184g(-2147287037);
                this.f6441a.mo3184g(8);
                this.f6441a.mo3184g(Integer.MAX_VALUE & i);
                this.f6441a.mo3184g(c2073a.f6261t);
                this.f6441a.flush();
            }
        }

        /* renamed from: a */
        public synchronized void mo3116a(int i, C2073a c2073a, byte[] bArr) {
            if (this.f6445e) {
                throw new IOException("closed");
            } else if (c2073a.f6262u == -1) {
                throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
            } else {
                this.f6441a.mo3184g(-2147287033);
                this.f6441a.mo3184g(8);
                this.f6441a.mo3184g(i);
                this.f6441a.mo3184g(c2073a.f6262u);
                this.f6441a.flush();
            }
        }

        /* renamed from: a */
        public void mo3117a(C2122n c2122n) {
        }

        /* renamed from: a */
        public synchronized void mo3118a(boolean z, int i, int i2) {
            boolean z2 = true;
            synchronized (this) {
                if (this.f6445e) {
                    throw new IOException("closed");
                }
                if (this.f6444d == ((i & 1) == 1)) {
                    z2 = false;
                }
                if (z != z2) {
                    throw new IllegalArgumentException("payload != reply");
                }
                this.f6441a.mo3184g(-2147287034);
                this.f6441a.mo3184g(4);
                this.f6441a.mo3184g(i);
                this.f6441a.flush();
            }
        }

        /* renamed from: a */
        public synchronized void mo3119a(boolean z, int i, C2244c c2244c, int i2) {
            m9598a(i, z ? 1 : 0, c2244c, i2);
        }

        /* renamed from: a */
        public synchronized void mo3120a(boolean z, boolean z2, int i, int i2, List<C2102f> list) {
            int i3 = 0;
            synchronized (this) {
                if (this.f6445e) {
                    throw new IOException("closed");
                }
                m9596a((List) list);
                int b = (int) (10 + this.f6442b.m10274b());
                int i4 = z ? 1 : 0;
                if (z2) {
                    i3 = 2;
                }
                i3 |= i4;
                this.f6441a.mo3184g(-2147287039);
                this.f6441a.mo3184g(((i3 & 255) << 24) | (b & 16777215));
                this.f6441a.mo3184g(i & Integer.MAX_VALUE);
                this.f6441a.mo3184g(i2 & Integer.MAX_VALUE);
                this.f6441a.mo3187h(0);
                this.f6441a.mo3173a(this.f6442b);
                this.f6441a.flush();
            }
        }

        /* renamed from: b */
        public synchronized void mo3121b() {
            if (this.f6445e) {
                throw new IOException("closed");
            }
            this.f6441a.flush();
        }

        /* renamed from: b */
        public synchronized void mo3122b(C2122n c2122n) {
            if (this.f6445e) {
                throw new IOException("closed");
            }
            int b = c2122n.m9576b();
            int i = (b * 8) + 4;
            this.f6441a.mo3184g(-2147287036);
            this.f6441a.mo3184g((i & 16777215) | 0);
            this.f6441a.mo3184g(b);
            for (b = 0; b <= 10; b++) {
                if (c2122n.m9575a(b)) {
                    this.f6441a.mo3184g(((c2122n.m9579c(b) & 255) << 24) | (b & 16777215));
                    this.f6441a.mo3184g(c2122n.m9577b(b));
                }
            }
            this.f6441a.flush();
        }

        /* renamed from: c */
        public int mo3123c() {
            return 16383;
        }

        public synchronized void close() {
            this.f6445e = true;
            C2187l.m9899a(this.f6441a, this.f6443c);
        }
    }

    static {
        try {
            f6446a = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(C2187l.f6636c.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    public C2075b mo3124a(C2243e c2243e, boolean z) {
        return new C2123a(c2243e, z);
    }

    /* renamed from: a */
    public C2076c mo3125a(C2242d c2242d, boolean z) {
        return new C2124b(c2242d, z);
    }
}
