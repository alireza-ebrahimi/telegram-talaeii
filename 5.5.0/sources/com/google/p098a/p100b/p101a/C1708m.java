package com.google.p098a.p100b.p101a;

import com.google.a.b.a.m.AnonymousClass15;
import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.C1771l;
import com.google.p098a.C1772i;
import com.google.p098a.C1774m;
import com.google.p098a.C1775n;
import com.google.p098a.C1776o;
import com.google.p098a.C1777q;
import com.google.p098a.C1778t;
import com.google.p098a.p099a.C1662c;
import com.google.p098a.p100b.C1728f;
import com.google.p098a.p100b.p101a.C1708m.C1707a;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p102d.C1758b;
import com.google.p098a.p103c.C1748a;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

/* renamed from: com.google.a.b.a.m */
public final class C1708m {
    /* renamed from: A */
    public static final C1670w<StringBuffer> f5214A = new C17069();
    /* renamed from: B */
    public static final C1668x f5215B = C1708m.m8271a(StringBuffer.class, f5214A);
    /* renamed from: C */
    public static final C1670w<URL> f5216C = new C1670w<URL>() {
        /* renamed from: a */
        public URL m8218a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            String h = c1678a.mo1264h();
            return !"null".equals(h) ? new URL(h) : null;
        }

        /* renamed from: a */
        public void m8219a(C1681c c1681c, URL url) {
            c1681c.mo1279b(url == null ? null : url.toExternalForm());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8218a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8219a(c1681c, (URL) obj);
        }
    };
    /* renamed from: D */
    public static final C1668x f5217D = C1708m.m8271a(URL.class, f5216C);
    /* renamed from: E */
    public static final C1670w<URI> f5218E = new C1670w<URI>() {
        /* renamed from: a */
        public URI m8220a(C1678a c1678a) {
            URI uri = null;
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
            } else {
                try {
                    String h = c1678a.mo1264h();
                    if (!"null".equals(h)) {
                        uri = new URI(h);
                    }
                } catch (Throwable e) {
                    throw new C1774m(e);
                }
            }
            return uri;
        }

        /* renamed from: a */
        public void m8221a(C1681c c1681c, URI uri) {
            c1681c.mo1279b(uri == null ? null : uri.toASCIIString());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8220a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8221a(c1681c, (URI) obj);
        }
    };
    /* renamed from: F */
    public static final C1668x f5219F = C1708m.m8271a(URI.class, f5218E);
    /* renamed from: G */
    public static final C1670w<InetAddress> f5220G = new C1670w<InetAddress>() {
        /* renamed from: a */
        public InetAddress m8224a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return InetAddress.getByName(c1678a.mo1264h());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8225a(C1681c c1681c, InetAddress inetAddress) {
            c1681c.mo1279b(inetAddress == null ? null : inetAddress.getHostAddress());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8224a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8225a(c1681c, (InetAddress) obj);
        }
    };
    /* renamed from: H */
    public static final C1668x f5221H = C1708m.m8273b(InetAddress.class, f5220G);
    /* renamed from: I */
    public static final C1670w<UUID> f5222I = new C1670w<UUID>() {
        /* renamed from: a */
        public UUID m8226a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return UUID.fromString(c1678a.mo1264h());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8227a(C1681c c1681c, UUID uuid) {
            c1681c.mo1279b(uuid == null ? null : uuid.toString());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8226a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8227a(c1681c, (UUID) obj);
        }
    };
    /* renamed from: J */
    public static final C1668x f5223J = C1708m.m8271a(UUID.class, f5222I);
    /* renamed from: K */
    public static final C1668x f5224K = new C1668x() {
        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            if (c1748a.m8359a() != Timestamp.class) {
                return null;
            }
            final C1670w a = c1768f.m8389a(Date.class);
            return new C1670w<Timestamp>(this) {
                /* renamed from: b */
                final /* synthetic */ AnonymousClass15 f5200b;

                /* renamed from: a */
                public Timestamp m8228a(C1678a c1678a) {
                    Date date = (Date) a.read(c1678a);
                    return date != null ? new Timestamp(date.getTime()) : null;
                }

                /* renamed from: a */
                public void m8229a(C1681c c1681c, Timestamp timestamp) {
                    a.write(c1681c, timestamp);
                }

                public /* synthetic */ Object read(C1678a c1678a) {
                    return m8228a(c1678a);
                }

                public /* synthetic */ void write(C1681c c1681c, Object obj) {
                    m8229a(c1681c, (Timestamp) obj);
                }
            };
        }
    };
    /* renamed from: L */
    public static final C1670w<Calendar> f5225L = new C1670w<Calendar>() {
        /* renamed from: a */
        public Calendar m8230a(C1678a c1678a) {
            int i = 0;
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            c1678a.mo1258c();
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (c1678a.mo1262f() != C1758b.END_OBJECT) {
                String g = c1678a.mo1263g();
                int m = c1678a.mo1269m();
                if ("year".equals(g)) {
                    i6 = m;
                } else if ("month".equals(g)) {
                    i5 = m;
                } else if ("dayOfMonth".equals(g)) {
                    i4 = m;
                } else if ("hourOfDay".equals(g)) {
                    i3 = m;
                } else if ("minute".equals(g)) {
                    i2 = m;
                } else if ("second".equals(g)) {
                    i = m;
                }
            }
            c1678a.mo1260d();
            return new GregorianCalendar(i6, i5, i4, i3, i2, i);
        }

        /* renamed from: a */
        public void m8231a(C1681c c1681c, Calendar calendar) {
            if (calendar == null) {
                c1681c.mo1284f();
                return;
            }
            c1681c.mo1282d();
            c1681c.mo1275a("year");
            c1681c.mo1273a((long) calendar.get(1));
            c1681c.mo1275a("month");
            c1681c.mo1273a((long) calendar.get(2));
            c1681c.mo1275a("dayOfMonth");
            c1681c.mo1273a((long) calendar.get(5));
            c1681c.mo1275a("hourOfDay");
            c1681c.mo1273a((long) calendar.get(11));
            c1681c.mo1275a("minute");
            c1681c.mo1273a((long) calendar.get(12));
            c1681c.mo1275a("second");
            c1681c.mo1273a((long) calendar.get(13));
            c1681c.mo1283e();
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8230a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8231a(c1681c, (Calendar) obj);
        }
    };
    /* renamed from: M */
    public static final C1668x f5226M = C1708m.m8274b(Calendar.class, GregorianCalendar.class, f5225L);
    /* renamed from: N */
    public static final C1670w<Locale> f5227N = new C1670w<Locale>() {
        /* renamed from: a */
        public Locale m8232a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(c1678a.mo1264h(), "_");
            String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            String nextToken3 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            return (nextToken2 == null && nextToken3 == null) ? new Locale(nextToken) : nextToken3 == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, nextToken3);
        }

        /* renamed from: a */
        public void m8233a(C1681c c1681c, Locale locale) {
            c1681c.mo1279b(locale == null ? null : locale.toString());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8232a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8233a(c1681c, (Locale) obj);
        }
    };
    /* renamed from: O */
    public static final C1668x f5228O = C1708m.m8271a(Locale.class, f5227N);
    /* renamed from: P */
    public static final C1670w<C1771l> f5229P = new C1670w<C1771l>() {
        /* renamed from: a */
        public C1771l m8234a(C1678a c1678a) {
            C1771l c1772i;
            switch (c1678a.mo1262f()) {
                case NUMBER:
                    return new C1777q(new C1728f(c1678a.mo1264h()));
                case BOOLEAN:
                    return new C1777q(Boolean.valueOf(c1678a.mo1265i()));
                case STRING:
                    return new C1777q(c1678a.mo1264h());
                case NULL:
                    c1678a.mo1266j();
                    return C1775n.f5390a;
                case BEGIN_ARRAY:
                    c1772i = new C1772i();
                    c1678a.mo1256a();
                    while (c1678a.mo1261e()) {
                        c1772i.m8420a(m8234a(c1678a));
                    }
                    c1678a.mo1257b();
                    return c1772i;
                case BEGIN_OBJECT:
                    c1772i = new C1776o();
                    c1678a.mo1258c();
                    while (c1678a.mo1261e()) {
                        c1772i.m8427a(c1678a.mo1263g(), m8234a(c1678a));
                    }
                    c1678a.mo1260d();
                    return c1772i;
                default:
                    throw new IllegalArgumentException();
            }
        }

        /* renamed from: a */
        public void m8235a(C1681c c1681c, C1771l c1771l) {
            if (c1771l == null || c1771l.m8414j()) {
                c1681c.mo1284f();
            } else if (c1771l.m8413i()) {
                C1777q m = c1771l.m8417m();
                if (m.m8440p()) {
                    c1681c.mo1274a(m.mo1292a());
                } else if (m.m8439o()) {
                    c1681c.mo1276a(m.mo1297f());
                } else {
                    c1681c.mo1279b(m.mo1293b());
                }
            } else if (c1771l.m8411g()) {
                c1681c.mo1278b();
                Iterator it = c1771l.m8416l().iterator();
                while (it.hasNext()) {
                    m8235a(c1681c, (C1771l) it.next());
                }
                c1681c.mo1280c();
            } else if (c1771l.m8412h()) {
                c1681c.mo1282d();
                for (Entry entry : c1771l.m8415k().m8428o()) {
                    c1681c.mo1275a((String) entry.getKey());
                    m8235a(c1681c, (C1771l) entry.getValue());
                }
                c1681c.mo1283e();
            } else {
                throw new IllegalArgumentException("Couldn't write " + c1771l.getClass());
            }
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8234a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8235a(c1681c, (C1771l) obj);
        }
    };
    /* renamed from: Q */
    public static final C1668x f5230Q = C1708m.m8273b(C1771l.class, f5229P);
    /* renamed from: R */
    public static final C1668x f5231R = C1708m.m8270a();
    /* renamed from: a */
    public static final C1670w<Class> f5232a = new C16981();
    /* renamed from: b */
    public static final C1668x f5233b = C1708m.m8271a(Class.class, f5232a);
    /* renamed from: c */
    public static final C1670w<BitSet> f5234c = new C1670w<BitSet>() {
        /* renamed from: a */
        public BitSet m8222a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            BitSet bitSet = new BitSet();
            c1678a.mo1256a();
            C1758b f = c1678a.mo1262f();
            int i = 0;
            while (f != C1758b.END_ARRAY) {
                boolean z;
                switch (f) {
                    case NUMBER:
                        if (c1678a.mo1269m() == 0) {
                            z = false;
                            break;
                        }
                        z = true;
                        break;
                    case BOOLEAN:
                        z = c1678a.mo1265i();
                        break;
                    case STRING:
                        String h = c1678a.mo1264h();
                        try {
                            if (Integer.parseInt(h) == 0) {
                                z = false;
                                break;
                            }
                            z = true;
                            break;
                        } catch (NumberFormatException e) {
                            throw new C1778t("Error: Expecting: bitset number value (1, 0), Found: " + h);
                        }
                    default:
                        throw new C1778t("Invalid bitset value type: " + f);
                }
                if (z) {
                    bitSet.set(i);
                }
                i++;
                f = c1678a.mo1262f();
            }
            c1678a.mo1257b();
            return bitSet;
        }

        /* renamed from: a */
        public void m8223a(C1681c c1681c, BitSet bitSet) {
            if (bitSet == null) {
                c1681c.mo1284f();
                return;
            }
            c1681c.mo1278b();
            for (int i = 0; i < bitSet.length(); i++) {
                c1681c.mo1273a((long) (bitSet.get(i) ? 1 : 0));
            }
            c1681c.mo1280c();
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8222a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8223a(c1681c, (BitSet) obj);
        }
    };
    /* renamed from: d */
    public static final C1668x f5235d = C1708m.m8271a(BitSet.class, f5234c);
    /* renamed from: e */
    public static final C1670w<Boolean> f5236e = new C1670w<Boolean>() {
        /* renamed from: a */
        public Boolean m8238a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return c1678a.mo1262f() == C1758b.STRING ? Boolean.valueOf(Boolean.parseBoolean(c1678a.mo1264h())) : Boolean.valueOf(c1678a.mo1265i());
            } else {
                c1678a.mo1266j();
                return null;
            }
        }

        /* renamed from: a */
        public void m8239a(C1681c c1681c, Boolean bool) {
            if (bool == null) {
                c1681c.mo1284f();
            } else {
                c1681c.mo1276a(bool.booleanValue());
            }
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8238a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8239a(c1681c, (Boolean) obj);
        }
    };
    /* renamed from: f */
    public static final C1670w<Boolean> f5237f = new C1670w<Boolean>() {
        /* renamed from: a */
        public Boolean m8240a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return Boolean.valueOf(c1678a.mo1264h());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8241a(C1681c c1681c, Boolean bool) {
            c1681c.mo1279b(bool == null ? "null" : bool.toString());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8240a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8241a(c1681c, (Boolean) obj);
        }
    };
    /* renamed from: g */
    public static final C1668x f5238g = C1708m.m8272a(Boolean.TYPE, Boolean.class, f5236e);
    /* renamed from: h */
    public static final C1670w<Number> f5239h = new C1670w<Number>() {
        /* renamed from: a */
        public Number m8242a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            try {
                return Byte.valueOf((byte) c1678a.mo1269m());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }

        /* renamed from: a */
        public void m8243a(C1681c c1681c, Number number) {
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8242a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8243a(c1681c, (Number) obj);
        }
    };
    /* renamed from: i */
    public static final C1668x f5240i = C1708m.m8272a(Byte.TYPE, Byte.class, f5239h);
    /* renamed from: j */
    public static final C1670w<Number> f5241j = new C1670w<Number>() {
        /* renamed from: a */
        public Number m8244a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            try {
                return Short.valueOf((short) c1678a.mo1269m());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }

        /* renamed from: a */
        public void m8245a(C1681c c1681c, Number number) {
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8244a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8245a(c1681c, (Number) obj);
        }
    };
    /* renamed from: k */
    public static final C1668x f5242k = C1708m.m8272a(Short.TYPE, Short.class, f5241j);
    /* renamed from: l */
    public static final C1670w<Number> f5243l = new C1670w<Number>() {
        /* renamed from: a */
        public Number m8246a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            try {
                return Integer.valueOf(c1678a.mo1269m());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }

        /* renamed from: a */
        public void m8247a(C1681c c1681c, Number number) {
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8246a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8247a(c1681c, (Number) obj);
        }
    };
    /* renamed from: m */
    public static final C1668x f5244m = C1708m.m8272a(Integer.TYPE, Integer.class, f5243l);
    /* renamed from: n */
    public static final C1670w<Number> f5245n = new C1670w<Number>() {
        /* renamed from: a */
        public Number m8250a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            try {
                return Long.valueOf(c1678a.mo1268l());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }

        /* renamed from: a */
        public void m8251a(C1681c c1681c, Number number) {
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8250a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8251a(c1681c, (Number) obj);
        }
    };
    /* renamed from: o */
    public static final C1670w<Number> f5246o = new C1670w<Number>() {
        /* renamed from: a */
        public Number m8252a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return Float.valueOf((float) c1678a.mo1267k());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8253a(C1681c c1681c, Number number) {
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8252a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8253a(c1681c, (Number) obj);
        }
    };
    /* renamed from: p */
    public static final C1670w<Number> f5247p = new C16992();
    /* renamed from: q */
    public static final C1670w<Number> f5248q = new C17003();
    /* renamed from: r */
    public static final C1668x f5249r = C1708m.m8271a(Number.class, f5248q);
    /* renamed from: s */
    public static final C1670w<Character> f5250s = new C17014();
    /* renamed from: t */
    public static final C1668x f5251t = C1708m.m8272a(Character.TYPE, Character.class, f5250s);
    /* renamed from: u */
    public static final C1670w<String> f5252u = new C17025();
    /* renamed from: v */
    public static final C1670w<BigDecimal> f5253v = new C17036();
    /* renamed from: w */
    public static final C1670w<BigInteger> f5254w = new C17047();
    /* renamed from: x */
    public static final C1668x f5255x = C1708m.m8271a(String.class, f5252u);
    /* renamed from: y */
    public static final C1670w<StringBuilder> f5256y = new C17058();
    /* renamed from: z */
    public static final C1668x f5257z = C1708m.m8271a(StringBuilder.class, f5256y);

    /* renamed from: com.google.a.b.a.m$1 */
    static class C16981 extends C1670w<Class> {
        C16981() {
        }

        /* renamed from: a */
        public Class m8236a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }

        /* renamed from: a */
        public void m8237a(C1681c c1681c, Class cls) {
            if (cls == null) {
                c1681c.mo1284f();
                return;
            }
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + cls.getName() + ". Forgot to register a type adapter?");
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8236a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8237a(c1681c, (Class) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$2 */
    static class C16992 extends C1670w<Number> {
        C16992() {
        }

        /* renamed from: a */
        public Number m8248a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return Double.valueOf(c1678a.mo1267k());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8249a(C1681c c1681c, Number number) {
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8248a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8249a(c1681c, (Number) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$3 */
    static class C17003 extends C1670w<Number> {
        C17003() {
        }

        /* renamed from: a */
        public Number m8254a(C1678a c1678a) {
            C1758b f = c1678a.mo1262f();
            switch (f) {
                case NUMBER:
                    return new C1728f(c1678a.mo1264h());
                case NULL:
                    c1678a.mo1266j();
                    return null;
                default:
                    throw new C1778t("Expecting number, got: " + f);
            }
        }

        /* renamed from: a */
        public void m8255a(C1681c c1681c, Number number) {
            c1681c.mo1274a(number);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8254a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8255a(c1681c, (Number) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$4 */
    static class C17014 extends C1670w<Character> {
        C17014() {
        }

        /* renamed from: a */
        public Character m8256a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            String h = c1678a.mo1264h();
            if (h.length() == 1) {
                return Character.valueOf(h.charAt(0));
            }
            throw new C1778t("Expecting character, got: " + h);
        }

        /* renamed from: a */
        public void m8257a(C1681c c1681c, Character ch) {
            c1681c.mo1279b(ch == null ? null : String.valueOf(ch));
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8256a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8257a(c1681c, (Character) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$5 */
    static class C17025 extends C1670w<String> {
        C17025() {
        }

        /* renamed from: a */
        public String m8258a(C1678a c1678a) {
            C1758b f = c1678a.mo1262f();
            if (f != C1758b.NULL) {
                return f == C1758b.BOOLEAN ? Boolean.toString(c1678a.mo1265i()) : c1678a.mo1264h();
            } else {
                c1678a.mo1266j();
                return null;
            }
        }

        /* renamed from: a */
        public void m8259a(C1681c c1681c, String str) {
            c1681c.mo1279b(str);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8258a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8259a(c1681c, (String) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$6 */
    static class C17036 extends C1670w<BigDecimal> {
        C17036() {
        }

        /* renamed from: a */
        public BigDecimal m8260a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            try {
                return new BigDecimal(c1678a.mo1264h());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }

        /* renamed from: a */
        public void m8261a(C1681c c1681c, BigDecimal bigDecimal) {
            c1681c.mo1274a((Number) bigDecimal);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8260a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8261a(c1681c, (BigDecimal) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$7 */
    static class C17047 extends C1670w<BigInteger> {
        C17047() {
        }

        /* renamed from: a */
        public BigInteger m8262a(C1678a c1678a) {
            if (c1678a.mo1262f() == C1758b.NULL) {
                c1678a.mo1266j();
                return null;
            }
            try {
                return new BigInteger(c1678a.mo1264h());
            } catch (Throwable e) {
                throw new C1778t(e);
            }
        }

        /* renamed from: a */
        public void m8263a(C1681c c1681c, BigInteger bigInteger) {
            c1681c.mo1274a((Number) bigInteger);
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8262a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8263a(c1681c, (BigInteger) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$8 */
    static class C17058 extends C1670w<StringBuilder> {
        C17058() {
        }

        /* renamed from: a */
        public StringBuilder m8264a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return new StringBuilder(c1678a.mo1264h());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8265a(C1681c c1681c, StringBuilder stringBuilder) {
            c1681c.mo1279b(stringBuilder == null ? null : stringBuilder.toString());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8264a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8265a(c1681c, (StringBuilder) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$9 */
    static class C17069 extends C1670w<StringBuffer> {
        C17069() {
        }

        /* renamed from: a */
        public StringBuffer m8266a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return new StringBuffer(c1678a.mo1264h());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8267a(C1681c c1681c, StringBuffer stringBuffer) {
            c1681c.mo1279b(stringBuffer == null ? null : stringBuffer.toString());
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8266a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8267a(c1681c, (StringBuffer) obj);
        }
    }

    /* renamed from: com.google.a.b.a.m$a */
    private static final class C1707a<T extends Enum<T>> extends C1670w<T> {
        /* renamed from: a */
        private final Map<String, T> f5212a = new HashMap();
        /* renamed from: b */
        private final Map<T, String> f5213b = new HashMap();

        public C1707a(Class<T> cls) {
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    Object a;
                    String name = enumR.name();
                    C1662c c1662c = (C1662c) cls.getField(name).getAnnotation(C1662c.class);
                    if (c1662c != null) {
                        a = c1662c.m8082a();
                    } else {
                        String str = name;
                    }
                    this.f5212a.put(a, enumR);
                    this.f5213b.put(enumR, a);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError();
            }
        }

        /* renamed from: a */
        public T m8268a(C1678a c1678a) {
            if (c1678a.mo1262f() != C1758b.NULL) {
                return (Enum) this.f5212a.get(c1678a.mo1264h());
            }
            c1678a.mo1266j();
            return null;
        }

        /* renamed from: a */
        public void m8269a(C1681c c1681c, T t) {
            c1681c.mo1279b(t == null ? null : (String) this.f5213b.get(t));
        }

        public /* synthetic */ Object read(C1678a c1678a) {
            return m8268a(c1678a);
        }

        public /* synthetic */ void write(C1681c c1681c, Object obj) {
            m8269a(c1681c, (Enum) obj);
        }
    }

    /* renamed from: a */
    public static C1668x m8270a() {
        return new C1668x() {
            public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
                Class a = c1748a.m8359a();
                if (!Enum.class.isAssignableFrom(a) || a == Enum.class) {
                    return null;
                }
                if (!a.isEnum()) {
                    a = a.getSuperclass();
                }
                return new C1707a(a);
            }
        };
    }

    /* renamed from: a */
    public static <TT> C1668x m8271a(final Class<TT> cls, final C1670w<TT> c1670w) {
        return new C1668x() {
            public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
                return c1748a.m8359a() == cls ? c1670w : null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + ",adapter=" + c1670w + "]";
            }
        };
    }

    /* renamed from: a */
    public static <TT> C1668x m8272a(final Class<TT> cls, final Class<TT> cls2, final C1670w<? super TT> c1670w) {
        return new C1668x() {
            public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
                Class a = c1748a.m8359a();
                return (a == cls || a == cls2) ? c1670w : null;
            }

            public String toString() {
                return "Factory[type=" + cls2.getName() + "+" + cls.getName() + ",adapter=" + c1670w + "]";
            }
        };
    }

    /* renamed from: b */
    public static <TT> C1668x m8273b(final Class<TT> cls, final C1670w<TT> c1670w) {
        return new C1668x() {
            public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
                return cls.isAssignableFrom(c1748a.m8359a()) ? c1670w : null;
            }

            public String toString() {
                return "Factory[typeHierarchy=" + cls.getName() + ",adapter=" + c1670w + "]";
            }
        };
    }

    /* renamed from: b */
    public static <TT> C1668x m8274b(final Class<TT> cls, final Class<? extends TT> cls2, final C1670w<? super TT> c1670w) {
        return new C1668x() {
            public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
                Class a = c1748a.m8359a();
                return (a == cls || a == cls2) ? c1670w : null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + "+" + cls2.getName() + ",adapter=" + c1670w + "]";
            }
        };
    }
}
