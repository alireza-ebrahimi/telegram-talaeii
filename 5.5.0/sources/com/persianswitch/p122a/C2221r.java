package com.persianswitch.p122a;

import com.persianswitch.p122a.p123a.C2187l;
import com.persianswitch.p126b.C2244c;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* renamed from: com.persianswitch.a.r */
public final class C2221r {
    /* renamed from: a */
    private static final char[] f6807a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    /* renamed from: b */
    private final String f6808b;
    /* renamed from: c */
    private final String f6809c;
    /* renamed from: d */
    private final String f6810d;
    /* renamed from: e */
    private final String f6811e;
    /* renamed from: f */
    private final int f6812f;
    /* renamed from: g */
    private final List<String> f6813g;
    /* renamed from: h */
    private final List<String> f6814h;
    /* renamed from: i */
    private final String f6815i;
    /* renamed from: j */
    private final String f6816j;

    /* renamed from: com.persianswitch.a.r$1 */
    static /* synthetic */ class C22181 {
        /* renamed from: a */
        static final /* synthetic */ int[] f6792a = new int[C2219a.values().length];

        static {
            try {
                f6792a[C2219a.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f6792a[C2219a.INVALID_HOST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f6792a[C2219a.UNSUPPORTED_SCHEME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f6792a[C2219a.MISSING_SCHEME.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f6792a[C2219a.INVALID_PORT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* renamed from: com.persianswitch.a.r$a */
    public static final class C2220a {
        /* renamed from: a */
        String f6799a;
        /* renamed from: b */
        String f6800b = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: c */
        String f6801c = TtmlNode.ANONYMOUS_REGION_ID;
        /* renamed from: d */
        String f6802d;
        /* renamed from: e */
        int f6803e = -1;
        /* renamed from: f */
        final List<String> f6804f = new ArrayList();
        /* renamed from: g */
        List<String> f6805g;
        /* renamed from: h */
        String f6806h;

        /* renamed from: com.persianswitch.a.r$a$a */
        enum C2219a {
            SUCCESS,
            MISSING_SCHEME,
            UNSUPPORTED_SCHEME,
            INVALID_PORT,
            INVALID_HOST
        }

        public C2220a() {
            this.f6804f.add(TtmlNode.ANONYMOUS_REGION_ID);
        }

        /* renamed from: a */
        private static String m10030a(byte[] bArr) {
            int i = 0;
            int i2 = 0;
            int i3 = -1;
            int i4 = 0;
            while (i4 < bArr.length) {
                int i5 = i4;
                while (i5 < 16 && bArr[i5] == (byte) 0 && bArr[i5 + 1] == (byte) 0) {
                    i5 += 2;
                }
                int i6 = i5 - i4;
                if (i6 > i2) {
                    i2 = i6;
                    i3 = i4;
                }
                i4 = i5 + 2;
            }
            C2244c c2244c = new C2244c();
            while (i < bArr.length) {
                if (i == i3) {
                    c2244c.m10275b(58);
                    i += i2;
                    if (i == 16) {
                        c2244c.m10275b(58);
                    }
                } else {
                    if (i > 0) {
                        c2244c.m10275b(58);
                    }
                    c2244c.m10300i((long) (((bArr[i] & 255) << 8) | (bArr[i + 1] & 255)));
                    i += 2;
                }
            }
            return c2244c.m10310o();
        }

        /* renamed from: a */
        private void m10031a(String str, int i, int i2) {
            if (i != i2) {
                char charAt = str.charAt(i);
                if (charAt == '/' || charAt == '\\') {
                    this.f6804f.clear();
                    this.f6804f.add(TtmlNode.ANONYMOUS_REGION_ID);
                    i++;
                } else {
                    this.f6804f.set(this.f6804f.size() - 1, TtmlNode.ANONYMOUS_REGION_ID);
                }
                int i3 = i;
                while (i3 < i2) {
                    int a = C2187l.m9888a(str, i3, i2, "/\\");
                    boolean z = a < i2;
                    m10032a(str, i3, a, z, true);
                    if (z) {
                        a++;
                    }
                    i3 = a;
                }
            }
        }

        /* renamed from: a */
        private void m10032a(String str, int i, int i2, boolean z, boolean z2) {
            String a = C2221r.m10054a(str, i, i2, " \"<>^`{}|/\\?#", z2, false, false, true);
            if (!m10038d(a)) {
                if (m10040e(a)) {
                    m10037d();
                    return;
                }
                if (((String) this.f6804f.get(this.f6804f.size() - 1)).isEmpty()) {
                    this.f6804f.set(this.f6804f.size() - 1, a);
                } else {
                    this.f6804f.add(a);
                }
                if (z) {
                    this.f6804f.add(TtmlNode.ANONYMOUS_REGION_ID);
                }
            }
        }

        /* renamed from: a */
        private static boolean m10033a(String str, int i, int i2, byte[] bArr, int i3) {
            int i4 = i;
            int i5 = i3;
            while (i4 < i2) {
                if (i5 == bArr.length) {
                    return false;
                }
                if (i5 != i3) {
                    if (str.charAt(i4) != '.') {
                        return false;
                    }
                    i4++;
                }
                int i6 = 0;
                int i7 = i4;
                while (i7 < i2) {
                    char charAt = str.charAt(i7);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    } else if (i6 == 0 && i4 != i7) {
                        return false;
                    } else {
                        i6 = ((i6 * 10) + charAt) - 48;
                        if (i6 > 255) {
                            return false;
                        }
                        i7++;
                    }
                }
                if (i7 - i4 == 0) {
                    return false;
                }
                i4 = i5 + 1;
                bArr[i5] = (byte) i6;
                i5 = i4;
                i4 = i7;
            }
            return i5 == i3 + 4;
        }

        /* renamed from: b */
        private static int m10034b(String str, int i, int i2) {
            if (i2 - i < 2) {
                return -1;
            }
            char charAt = str.charAt(i);
            if ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z')) {
                return -1;
            }
            int i3 = i + 1;
            while (i3 < i2) {
                char charAt2 = str.charAt(i3);
                if ((charAt2 < 'a' || charAt2 > 'z') && ((charAt2 < 'A' || charAt2 > 'Z') && ((charAt2 < '0' || charAt2 > '9') && charAt2 != '+' && charAt2 != '-' && charAt2 != '.'))) {
                    return charAt2 == ':' ? i3 : -1;
                } else {
                    i3++;
                }
            }
            return -1;
        }

        /* renamed from: c */
        private static int m10035c(String str, int i, int i2) {
            int i3 = 0;
            while (i < i2) {
                char charAt = str.charAt(i);
                if (charAt != '\\' && charAt != '/') {
                    break;
                }
                i3++;
                i++;
            }
            return i3;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        /* renamed from: d */
        private static int m10036d(java.lang.String r3, int r4, int r5) {
            /*
            r0 = r4;
        L_0x0001:
            if (r0 >= r5) goto L_0x001a;
        L_0x0003:
            r1 = r3.charAt(r0);
            switch(r1) {
                case 58: goto L_0x001b;
                case 91: goto L_0x000d;
                default: goto L_0x000a;
            };
        L_0x000a:
            r0 = r0 + 1;
            goto L_0x0001;
        L_0x000d:
            r0 = r0 + 1;
            if (r0 >= r5) goto L_0x000a;
        L_0x0011:
            r1 = r3.charAt(r0);
            r2 = 93;
            if (r1 != r2) goto L_0x000d;
        L_0x0019:
            goto L_0x000a;
        L_0x001a:
            r0 = r5;
        L_0x001b:
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.persianswitch.a.r.a.d(java.lang.String, int, int):int");
        }

        /* renamed from: d */
        private void m10037d() {
            if (!((String) this.f6804f.remove(this.f6804f.size() - 1)).isEmpty() || this.f6804f.isEmpty()) {
                this.f6804f.add(TtmlNode.ANONYMOUS_REGION_ID);
            } else {
                this.f6804f.set(this.f6804f.size() - 1, TtmlNode.ANONYMOUS_REGION_ID);
            }
        }

        /* renamed from: d */
        private boolean m10038d(String str) {
            return str.equals(".") || str.equalsIgnoreCase("%2e");
        }

        /* renamed from: e */
        private static String m10039e(String str, int i, int i2) {
            String a = C2221r.m10055a(str, i, i2, false);
            if (!a.contains(":")) {
                return C2187l.m9891a(a);
            }
            InetAddress f = (a.startsWith("[") && a.endsWith("]")) ? C2220a.m10041f(a, 1, a.length() - 1) : C2220a.m10041f(a, 0, a.length());
            if (f == null) {
                return null;
            }
            byte[] address = f.getAddress();
            if (address.length == 16) {
                return C2220a.m10030a(address);
            }
            throw new AssertionError();
        }

        /* renamed from: e */
        private boolean m10040e(String str) {
            return str.equals("..") || str.equalsIgnoreCase("%2e.") || str.equalsIgnoreCase(".%2e") || str.equalsIgnoreCase("%2e%2e");
        }

        /* renamed from: f */
        private static InetAddress m10041f(String str, int i, int i2) {
            byte[] bArr = new byte[16];
            int i3 = i;
            int i4 = -1;
            int i5 = -1;
            int i6 = 0;
            while (i3 < i2) {
                if (i6 == bArr.length) {
                    return null;
                }
                int a;
                if (i3 + 2 > i2 || !str.regionMatches(i3, "::", 0, 2)) {
                    if (i6 != 0) {
                        if (str.regionMatches(i3, ":", 0, 1)) {
                            i3++;
                        } else if (!str.regionMatches(i3, ".", 0, 1)) {
                            return null;
                        } else {
                            if (!C2220a.m10033a(str, i4, i2, bArr, i6 - 2)) {
                                return null;
                            }
                            i6 += 2;
                        }
                    }
                } else if (i5 != -1) {
                    return null;
                } else {
                    i3 += 2;
                    i5 = i6 + 2;
                    if (i3 == i2) {
                        i6 = i5;
                        break;
                    }
                    i6 = i5;
                }
                i4 = 0;
                int i7 = i3;
                while (i7 < i2) {
                    a = C2221r.m10051a(str.charAt(i7));
                    if (a == -1) {
                        break;
                    }
                    i4 = (i4 << 4) + a;
                    i7++;
                }
                a = i7 - i3;
                if (a == 0 || a > 4) {
                    return null;
                }
                a = i6 + 1;
                bArr[i6] = (byte) ((i4 >>> 8) & 255);
                i6 = a + 1;
                bArr[a] = (byte) (i4 & 255);
                i4 = i3;
                i3 = i7;
            }
            if (i6 != bArr.length) {
                if (i5 == -1) {
                    return null;
                }
                System.arraycopy(bArr, i5, bArr, bArr.length - (i6 - i5), i6 - i5);
                Arrays.fill(bArr, i5, (bArr.length - i6) + i5, (byte) 0);
            }
            try {
                return InetAddress.getByAddress(bArr);
            } catch (UnknownHostException e) {
                throw new AssertionError();
            }
        }

        /* renamed from: g */
        private static int m10042g(String str, int i, int i2) {
            try {
                int parseInt = Integer.parseInt(C2221r.m10054a(str, i, i2, TtmlNode.ANONYMOUS_REGION_ID, false, false, false, true));
                return (parseInt <= 0 || parseInt > 65535) ? -1 : parseInt;
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        /* renamed from: a */
        int m10043a() {
            return this.f6803e != -1 ? this.f6803e : C2221r.m10052a(this.f6799a);
        }

        /* renamed from: a */
        C2219a m10044a(C2221r c2221r, String str) {
            int d;
            int a = C2187l.m9886a(str, 0, str.length());
            int b = C2187l.m9906b(str, a, str.length());
            if (C2220a.m10034b(str, a, b) != -1) {
                if (str.regionMatches(true, a, "https:", 0, 6)) {
                    this.f6799a = "https";
                    a += "https:".length();
                } else {
                    if (!str.regionMatches(true, a, "http:", 0, 5)) {
                        return C2219a.UNSUPPORTED_SCHEME;
                    }
                    this.f6799a = "http";
                    a += "http:".length();
                }
            } else if (c2221r == null) {
                return C2219a.MISSING_SCHEME;
            } else {
                this.f6799a = c2221r.f6808b;
            }
            int c = C2220a.m10035c(str, a, b);
            if (c >= 2 || c2221r == null || !c2221r.f6808b.equals(this.f6799a)) {
                Object obj = null;
                Object obj2 = null;
                int i = a + c;
                while (true) {
                    Object obj3;
                    Object obj4;
                    int a2 = C2187l.m9888a(str, i, b, "@/\\?#");
                    switch (a2 != b ? str.charAt(a2) : '￿') {
                        case '￿':
                        case '#':
                        case '/':
                        case '?':
                        case '\\':
                            d = C2220a.m10036d(str, i, a2);
                            if (d + 1 < a2) {
                                this.f6802d = C2220a.m10039e(str, i, d);
                                this.f6803e = C2220a.m10042g(str, d + 1, a2);
                                if (this.f6803e == -1) {
                                    return C2219a.INVALID_PORT;
                                }
                            }
                            this.f6802d = C2220a.m10039e(str, i, d);
                            this.f6803e = C2221r.m10052a(this.f6799a);
                            if (this.f6802d != null) {
                                a = a2;
                                break;
                            }
                            return C2219a.INVALID_HOST;
                        case '@':
                            if (obj == null) {
                                a = C2187l.m9887a(str, i, a2, ':');
                                String a3 = C2221r.m10054a(str, i, a, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                if (obj2 != null) {
                                    a3 = this.f6800b + "%40" + a3;
                                }
                                this.f6800b = a3;
                                if (a != a2) {
                                    obj = 1;
                                    this.f6801c = C2221r.m10054a(str, a + 1, a2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                }
                                obj2 = 1;
                            } else {
                                this.f6801c += "%40" + C2221r.m10054a(str, i, a2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                            }
                            a = a2 + 1;
                            obj3 = obj;
                            obj4 = obj2;
                            continue;
                        default:
                            obj3 = obj;
                            a = i;
                            obj4 = obj2;
                            continue;
                    }
                    obj = obj3;
                    obj2 = obj4;
                    i = a;
                }
            } else {
                this.f6800b = c2221r.m10073d();
                this.f6801c = c2221r.m10074e();
                this.f6802d = c2221r.f6811e;
                this.f6803e = c2221r.f6812f;
                this.f6804f.clear();
                this.f6804f.addAll(c2221r.m10078i());
                if (a == b || str.charAt(a) == '#') {
                    m10049c(c2221r.m10079j());
                }
            }
            d = C2187l.m9888a(str, a, b, "?#");
            m10031a(str, a, d);
            if (d >= b || str.charAt(d) != '?') {
                a = d;
            } else {
                a = C2187l.m9887a(str, d, b, '#');
                this.f6805g = C2221r.m10064b(C2221r.m10054a(str, d + 1, a, " \"'<>#", true, false, true, true));
            }
            if (a < b && str.charAt(a) == '#') {
                this.f6806h = C2221r.m10054a(str, a + 1, b, TtmlNode.ANONYMOUS_REGION_ID, true, false, false, false);
            }
            return C2219a.SUCCESS;
        }

        /* renamed from: a */
        public C2220a m10045a(int i) {
            if (i <= 0 || i > 65535) {
                throw new IllegalArgumentException("unexpected port: " + i);
            }
            this.f6803e = i;
            return this;
        }

        /* renamed from: a */
        public C2220a m10046a(String str) {
            if (str == null) {
                throw new NullPointerException("scheme == null");
            }
            if (str.equalsIgnoreCase("http")) {
                this.f6799a = "http";
            } else if (str.equalsIgnoreCase("https")) {
                this.f6799a = "https";
            } else {
                throw new IllegalArgumentException("unexpected scheme: " + str);
            }
            return this;
        }

        /* renamed from: b */
        C2220a m10047b() {
            int size = this.f6804f.size();
            for (int i = 0; i < size; i++) {
                this.f6804f.set(i, C2221r.m10056a((String) this.f6804f.get(i), "[]", true, true, false, true));
            }
            if (this.f6805g != null) {
                int size2 = this.f6805g.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str = (String) this.f6805g.get(i2);
                    if (str != null) {
                        this.f6805g.set(i2, C2221r.m10056a(str, "\\^`{|}", true, true, true, true));
                    }
                }
            }
            if (this.f6806h != null) {
                this.f6806h = C2221r.m10056a(this.f6806h, " \"#<>\\^`{|}", true, true, false, false);
            }
            return this;
        }

        /* renamed from: b */
        public C2220a m10048b(String str) {
            if (str == null) {
                throw new NullPointerException("host == null");
            }
            String e = C2220a.m10039e(str, 0, str.length());
            if (e == null) {
                throw new IllegalArgumentException("unexpected host: " + str);
            }
            this.f6802d = e;
            return this;
        }

        /* renamed from: c */
        public C2220a m10049c(String str) {
            List b;
            if (str != null) {
                b = C2221r.m10064b(C2221r.m10056a(str, " \"'<>#", true, false, true, true));
            } else {
                b = null;
            }
            this.f6805g = b;
            return this;
        }

        /* renamed from: c */
        public C2221r m10050c() {
            if (this.f6799a == null) {
                throw new IllegalStateException("scheme == null");
            } else if (this.f6802d != null) {
                return new C2221r();
            } else {
                throw new IllegalStateException("host == null");
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.f6799a);
            stringBuilder.append("://");
            if (!(this.f6800b.isEmpty() && this.f6801c.isEmpty())) {
                stringBuilder.append(this.f6800b);
                if (!this.f6801c.isEmpty()) {
                    stringBuilder.append(':');
                    stringBuilder.append(this.f6801c);
                }
                stringBuilder.append('@');
            }
            if (this.f6802d.indexOf(58) != -1) {
                stringBuilder.append('[');
                stringBuilder.append(this.f6802d);
                stringBuilder.append(']');
            } else {
                stringBuilder.append(this.f6802d);
            }
            int a = m10043a();
            if (a != C2221r.m10052a(this.f6799a)) {
                stringBuilder.append(':');
                stringBuilder.append(a);
            }
            C2221r.m10061a(stringBuilder, this.f6804f);
            if (this.f6805g != null) {
                stringBuilder.append('?');
                C2221r.m10065b(stringBuilder, this.f6805g);
            }
            if (this.f6806h != null) {
                stringBuilder.append('#');
                stringBuilder.append(this.f6806h);
            }
            return stringBuilder.toString();
        }
    }

    private C2221r(C2220a c2220a) {
        String str = null;
        this.f6808b = c2220a.f6799a;
        this.f6809c = C2221r.m10057a(c2220a.f6800b, false);
        this.f6810d = C2221r.m10057a(c2220a.f6801c, false);
        this.f6811e = c2220a.f6802d;
        this.f6812f = c2220a.m10043a();
        this.f6813g = m10058a(c2220a.f6804f, false);
        this.f6814h = c2220a.f6805g != null ? m10058a(c2220a.f6805g, true) : null;
        if (c2220a.f6806h != null) {
            str = C2221r.m10057a(c2220a.f6806h, false);
        }
        this.f6815i = str;
        this.f6816j = c2220a.toString();
    }

    /* renamed from: a */
    static int m10051a(char c) {
        return (c < '0' || c > '9') ? (c < 'a' || c > 'f') ? (c < 'A' || c > 'F') ? -1 : (c - 65) + 10 : (c - 97) + 10 : c - 48;
    }

    /* renamed from: a */
    public static int m10052a(String str) {
        return str.equals("http") ? 80 : str.equals("https") ? 443 : -1;
    }

    /* renamed from: a */
    static String m10054a(String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        int i3 = i;
        while (i3 < i2) {
            int codePointAt = str.codePointAt(i3);
            if (codePointAt < 32 || codePointAt == 127 || ((codePointAt >= 128 && z4) || str2.indexOf(codePointAt) != -1 || ((codePointAt == 37 && (!z || (z2 && !C2221r.m10062a(str, i3, i2)))) || (codePointAt == 43 && z3)))) {
                C2244c c2244c = new C2244c();
                c2244c.m10268a(str, i, i3);
                C2221r.m10059a(c2244c, str, i3, i2, str2, z, z2, z3, z4);
                return c2244c.m10310o();
            }
            i3 += Character.charCount(codePointAt);
        }
        return str.substring(i, i2);
    }

    /* renamed from: a */
    static String m10055a(String str, int i, int i2, boolean z) {
        for (int i3 = i; i3 < i2; i3++) {
            char charAt = str.charAt(i3);
            if (charAt == '%' || (charAt == '+' && z)) {
                C2244c c2244c = new C2244c();
                c2244c.m10268a(str, i, i3);
                C2221r.m10060a(c2244c, str, i3, i2, z);
                return c2244c.m10310o();
            }
        }
        return str.substring(i, i2);
    }

    /* renamed from: a */
    static String m10056a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        return C2221r.m10054a(str, 0, str.length(), str2, z, z2, z3, z4);
    }

    /* renamed from: a */
    static String m10057a(String str, boolean z) {
        return C2221r.m10055a(str, 0, str.length(), z);
    }

    /* renamed from: a */
    private List<String> m10058a(List<String> list, boolean z) {
        List arrayList = new ArrayList(list.size());
        for (String str : list) {
            arrayList.add(str != null ? C2221r.m10057a(str, z) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* renamed from: a */
    static void m10059a(C2244c c2244c, String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        C2244c c2244c2 = null;
        while (i < i2) {
            int codePointAt = str.codePointAt(i);
            if (!(z && (codePointAt == 9 || codePointAt == 10 || codePointAt == 12 || codePointAt == 13))) {
                if (codePointAt == 43 && z3) {
                    c2244c.m10267a(z ? "+" : "%2B");
                } else if (codePointAt < 32 || codePointAt == 127 || ((codePointAt >= 128 && z4) || str2.indexOf(codePointAt) != -1 || (codePointAt == 37 && (!z || (z2 && !C2221r.m10062a(str, i, i2)))))) {
                    if (c2244c2 == null) {
                        c2244c2 = new C2244c();
                    }
                    c2244c2.m10264a(codePointAt);
                    while (!c2244c2.mo3181e()) {
                        int h = c2244c2.mo3186h() & 255;
                        c2244c.m10275b(37);
                        c2244c.m10275b(f6807a[(h >> 4) & 15]);
                        c2244c.m10275b(f6807a[h & 15]);
                    }
                } else {
                    c2244c.m10264a(codePointAt);
                }
            }
            i += Character.charCount(codePointAt);
        }
    }

    /* renamed from: a */
    static void m10060a(C2244c c2244c, String str, int i, int i2, boolean z) {
        int i3 = i;
        while (i3 < i2) {
            int codePointAt = str.codePointAt(i3);
            if (codePointAt != 37 || i3 + 2 >= i2) {
                if (codePointAt == 43 && z) {
                    c2244c.m10275b(32);
                }
                c2244c.m10264a(codePointAt);
            } else {
                int a = C2221r.m10051a(str.charAt(i3 + 1));
                int a2 = C2221r.m10051a(str.charAt(i3 + 2));
                if (!(a == -1 || a2 == -1)) {
                    c2244c.m10275b((a << 4) + a2);
                    i3 += 2;
                }
                c2244c.m10264a(codePointAt);
            }
            i3 += Character.charCount(codePointAt);
        }
    }

    /* renamed from: a */
    static void m10061a(StringBuilder stringBuilder, List<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            stringBuilder.append('/');
            stringBuilder.append((String) list.get(i));
        }
    }

    /* renamed from: a */
    static boolean m10062a(String str, int i, int i2) {
        return i + 2 < i2 && str.charAt(i) == '%' && C2221r.m10051a(str.charAt(i + 1)) != -1 && C2221r.m10051a(str.charAt(i + 2)) != -1;
    }

    /* renamed from: b */
    static List<String> m10064b(String str) {
        List<String> arrayList = new ArrayList();
        int i = 0;
        while (i <= str.length()) {
            int indexOf = str.indexOf(38, i);
            if (indexOf == -1) {
                indexOf = str.length();
            }
            int indexOf2 = str.indexOf(61, i);
            if (indexOf2 == -1 || indexOf2 > indexOf) {
                arrayList.add(str.substring(i, indexOf));
                arrayList.add(null);
            } else {
                arrayList.add(str.substring(i, indexOf2));
                arrayList.add(str.substring(indexOf2 + 1, indexOf));
            }
            i = indexOf + 1;
        }
        return arrayList;
    }

    /* renamed from: b */
    static void m10065b(StringBuilder stringBuilder, List<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i += 2) {
            String str = (String) list.get(i);
            String str2 = (String) list.get(i + 1);
            if (i > 0) {
                stringBuilder.append('&');
            }
            stringBuilder.append(str);
            if (str2 != null) {
                stringBuilder.append('=');
                stringBuilder.append(str2);
            }
        }
    }

    /* renamed from: e */
    public static C2221r m10067e(String str) {
        C2220a c2220a = new C2220a();
        return c2220a.m10044a(null, str) == C2219a.SUCCESS ? c2220a.m10050c() : null;
    }

    /* renamed from: a */
    public URI m10068a() {
        String c2220a = m10082m().m10047b().toString();
        try {
            return new URI(c2220a);
        } catch (Throwable e) {
            try {
                return URI.create(c2220a.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", TtmlNode.ANONYMOUS_REGION_ID));
            } catch (Exception e2) {
                throw new RuntimeException(e);
            }
        }
    }

    /* renamed from: b */
    public String m10069b() {
        return this.f6808b;
    }

    /* renamed from: c */
    public C2221r m10070c(String str) {
        C2220a d = m10072d(str);
        return d != null ? d.m10050c() : null;
    }

    /* renamed from: c */
    public boolean m10071c() {
        return this.f6808b.equals("https");
    }

    /* renamed from: d */
    public C2220a m10072d(String str) {
        C2220a c2220a = new C2220a();
        return c2220a.m10044a(this, str) == C2219a.SUCCESS ? c2220a : null;
    }

    /* renamed from: d */
    public String m10073d() {
        if (this.f6809c.isEmpty()) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        int length = this.f6808b.length() + 3;
        return this.f6816j.substring(length, C2187l.m9888a(this.f6816j, length, this.f6816j.length(), ":@"));
    }

    /* renamed from: e */
    public String m10074e() {
        if (this.f6810d.isEmpty()) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        return this.f6816j.substring(this.f6816j.indexOf(58, this.f6808b.length() + 3) + 1, this.f6816j.indexOf(64));
    }

    public boolean equals(Object obj) {
        return (obj instanceof C2221r) && ((C2221r) obj).f6816j.equals(this.f6816j);
    }

    /* renamed from: f */
    public String m10075f() {
        return this.f6811e;
    }

    /* renamed from: g */
    public int m10076g() {
        return this.f6812f;
    }

    /* renamed from: h */
    public String m10077h() {
        int indexOf = this.f6816j.indexOf(47, this.f6808b.length() + 3);
        return this.f6816j.substring(indexOf, C2187l.m9888a(this.f6816j, indexOf, this.f6816j.length(), "?#"));
    }

    public int hashCode() {
        return this.f6816j.hashCode();
    }

    /* renamed from: i */
    public List<String> m10078i() {
        int indexOf = this.f6816j.indexOf(47, this.f6808b.length() + 3);
        int a = C2187l.m9888a(this.f6816j, indexOf, this.f6816j.length(), "?#");
        List<String> arrayList = new ArrayList();
        while (indexOf < a) {
            int i = indexOf + 1;
            indexOf = C2187l.m9887a(this.f6816j, i, a, '/');
            arrayList.add(this.f6816j.substring(i, indexOf));
        }
        return arrayList;
    }

    /* renamed from: j */
    public String m10079j() {
        if (this.f6814h == null) {
            return null;
        }
        int indexOf = this.f6816j.indexOf(63) + 1;
        return this.f6816j.substring(indexOf, C2187l.m9887a(this.f6816j, indexOf + 1, this.f6816j.length(), '#'));
    }

    /* renamed from: k */
    public String m10080k() {
        if (this.f6814h == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        C2221r.m10065b(stringBuilder, this.f6814h);
        return stringBuilder.toString();
    }

    /* renamed from: l */
    public String m10081l() {
        if (this.f6815i == null) {
            return null;
        }
        return this.f6816j.substring(this.f6816j.indexOf(35) + 1);
    }

    /* renamed from: m */
    public C2220a m10082m() {
        C2220a c2220a = new C2220a();
        c2220a.f6799a = this.f6808b;
        c2220a.f6800b = m10073d();
        c2220a.f6801c = m10074e();
        c2220a.f6802d = this.f6811e;
        c2220a.f6803e = this.f6812f != C2221r.m10052a(this.f6808b) ? this.f6812f : -1;
        c2220a.f6804f.clear();
        c2220a.f6804f.addAll(m10078i());
        c2220a.m10049c(m10079j());
        c2220a.f6806h = m10081l();
        return c2220a;
    }

    public String toString() {
        return this.f6816j;
    }
}
