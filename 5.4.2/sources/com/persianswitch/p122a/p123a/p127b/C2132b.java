package com.persianswitch.p122a.p123a.p127b;

import com.google.android.gms.wallet.WalletConstants;
import com.persianswitch.p122a.C2195d;
import com.persianswitch.p122a.C2217q;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2231x.C2230a;
import com.persianswitch.p122a.C2236z;
import com.persianswitch.p122a.C2236z.C2235a;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: com.persianswitch.a.a.b.b */
public final class C2132b {
    /* renamed from: a */
    public final C2231x f6467a;
    /* renamed from: b */
    public final C2236z f6468b;

    /* renamed from: com.persianswitch.a.a.b.b$a */
    public static class C2131a {
        /* renamed from: a */
        final long f6455a;
        /* renamed from: b */
        final C2231x f6456b;
        /* renamed from: c */
        final C2236z f6457c;
        /* renamed from: d */
        private Date f6458d;
        /* renamed from: e */
        private String f6459e;
        /* renamed from: f */
        private Date f6460f;
        /* renamed from: g */
        private String f6461g;
        /* renamed from: h */
        private Date f6462h;
        /* renamed from: i */
        private long f6463i;
        /* renamed from: j */
        private long f6464j;
        /* renamed from: k */
        private String f6465k;
        /* renamed from: l */
        private int f6466l = -1;

        public C2131a(long j, C2231x c2231x, C2236z c2236z) {
            this.f6455a = j;
            this.f6456b = c2231x;
            this.f6457c = c2236z;
            if (c2236z != null) {
                this.f6463i = c2236z.m10224i();
                this.f6464j = c2236z.m10225j();
                C2217q e = c2236z.m10220e();
                int a = e.m10023a();
                for (int i = 0; i < a; i++) {
                    String a2 = e.m10024a(i);
                    String b = e.m10027b(i);
                    if ("Date".equalsIgnoreCase(a2)) {
                        this.f6458d = C2148g.m9694a(b);
                        this.f6459e = b;
                    } else if ("Expires".equalsIgnoreCase(a2)) {
                        this.f6462h = C2148g.m9694a(b);
                    } else if ("Last-Modified".equalsIgnoreCase(a2)) {
                        this.f6460f = C2148g.m9694a(b);
                        this.f6461g = b;
                    } else if ("ETag".equalsIgnoreCase(a2)) {
                        this.f6465k = b;
                    } else if ("Age".equalsIgnoreCase(a2)) {
                        this.f6466l = C2135d.m9643b(b, -1);
                    }
                }
            }
        }

        /* renamed from: a */
        private static boolean m9632a(C2231x c2231x) {
            return (c2231x.m10158a("If-Modified-Since") == null && c2231x.m10158a("If-None-Match") == null) ? false : true;
        }

        /* renamed from: b */
        private C2132b m9633b() {
            long j = 0;
            if (this.f6457c == null) {
                return new C2132b(this.f6456b, null);
            }
            if (this.f6456b.m10164g() && this.f6457c.m10219d() == null) {
                return new C2132b(this.f6456b, null);
            }
            if (!C2132b.m9638a(this.f6457c, this.f6456b)) {
                return new C2132b(this.f6456b, null);
            }
            C2195d f = this.f6456b.m10163f();
            if (f.m9938a() || C2131a.m9632a(this.f6456b)) {
                return new C2132b(this.f6456b, null);
            }
            long d = m9635d();
            long c = m9634c();
            if (f.m9940c() != -1) {
                c = Math.min(c, TimeUnit.SECONDS.toMillis((long) f.m9940c()));
            }
            long toMillis = f.m9945h() != -1 ? TimeUnit.SECONDS.toMillis((long) f.m9945h()) : 0;
            C2195d h = this.f6457c.m10223h();
            if (!(h.m9943f() || f.m9944g() == -1)) {
                j = TimeUnit.SECONDS.toMillis((long) f.m9944g());
            }
            if (h.m9938a() || d + toMillis >= r4 + c) {
                C2230a e = this.f6456b.m10162e();
                if (this.f6465k != null) {
                    e.m10149a("If-None-Match", this.f6465k);
                } else if (this.f6460f != null) {
                    e.m10149a("If-Modified-Since", this.f6461g);
                } else if (this.f6458d != null) {
                    e.m10149a("If-Modified-Since", this.f6459e);
                }
                C2231x a = e.m10150a();
                return C2131a.m9632a(a) ? new C2132b(a, this.f6457c) : new C2132b(a, null);
            } else {
                C2235a g = this.f6457c.m10222g();
                if (toMillis + d >= c) {
                    g.m10197a("Warning", "110 HttpURLConnection \"Response is stale\"");
                }
                if (d > 86400000 && m9636e()) {
                    g.m10197a("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                }
                return new C2132b(null, g.m10198a());
            }
        }

        /* renamed from: c */
        private long m9634c() {
            C2195d h = this.f6457c.m10223h();
            if (h.m9940c() != -1) {
                return TimeUnit.SECONDS.toMillis((long) h.m9940c());
            }
            long time;
            if (this.f6462h != null) {
                time = this.f6462h.getTime() - (this.f6458d != null ? this.f6458d.getTime() : this.f6464j);
                if (time <= 0) {
                    time = 0;
                }
                return time;
            } else if (this.f6460f == null || this.f6457c.m10214a().m10157a().m10080k() != null) {
                return 0;
            } else {
                time = (this.f6458d != null ? this.f6458d.getTime() : this.f6463i) - this.f6460f.getTime();
                return time > 0 ? time / 10 : 0;
            }
        }

        /* renamed from: d */
        private long m9635d() {
            long j = 0;
            if (this.f6458d != null) {
                j = Math.max(0, this.f6464j - this.f6458d.getTime());
            }
            if (this.f6466l != -1) {
                j = Math.max(j, TimeUnit.SECONDS.toMillis((long) this.f6466l));
            }
            return (j + (this.f6464j - this.f6463i)) + (this.f6455a - this.f6464j);
        }

        /* renamed from: e */
        private boolean m9636e() {
            return this.f6457c.m10223h().m9940c() == -1 && this.f6462h == null;
        }

        /* renamed from: a */
        public C2132b m9637a() {
            C2132b b = m9633b();
            return (b.f6467a == null || !this.f6456b.m10163f().m9946i()) ? b : new C2132b(null, null);
        }
    }

    private C2132b(C2231x c2231x, C2236z c2236z) {
        this.f6467a = c2231x;
        this.f6468b = c2236z;
    }

    /* renamed from: a */
    public static boolean m9638a(C2236z c2236z, C2231x c2231x) {
        switch (c2236z.m10217b()) {
            case Callback.DEFAULT_DRAG_ANIMATION_DURATION /*200*/:
            case 203:
            case 204:
            case 300:
            case 301:
            case 308:
            case WalletConstants.ERROR_CODE_INVALID_PARAMETERS /*404*/:
            case WalletConstants.ERROR_CODE_MERCHANT_ACCOUNT_ERROR /*405*/:
            case WalletConstants.ERROR_CODE_INVALID_TRANSACTION /*410*/:
            case 414:
            case 501:
                break;
            case 302:
            case 307:
                if (c2236z.m10215a("Expires") == null && c2236z.m10223h().m9940c() == -1 && !c2236z.m10223h().m9942e() && !c2236z.m10223h().m9941d()) {
                    return false;
                }
            default:
                return false;
        }
        return (c2236z.m10223h().m9939b() || c2231x.m10163f().m9939b()) ? false : true;
    }
}
