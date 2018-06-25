package com.persianswitch.p122a;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* renamed from: com.persianswitch.a.h */
public final class C2202h {
    /* renamed from: A */
    public static final C2202h f6691A = C2202h.m9959a("TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: B */
    public static final C2202h f6692B = C2202h.m9959a("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: C */
    public static final C2202h f6693C = C2202h.m9959a("TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: D */
    public static final C2202h f6694D = C2202h.m9959a("TLS_RSA_WITH_AES_128_CBC_SHA", 47, 5246, 6, 10);
    /* renamed from: E */
    public static final C2202h f6695E = C2202h.m9959a("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50, 5246, 6, 10);
    /* renamed from: F */
    public static final C2202h f6696F = C2202h.m9959a("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51, 5246, 6, 10);
    /* renamed from: G */
    public static final C2202h f6697G = C2202h.m9959a("TLS_DH_anon_WITH_AES_128_CBC_SHA", 52, 5246, 6, 10);
    /* renamed from: H */
    public static final C2202h f6698H = C2202h.m9959a("TLS_RSA_WITH_AES_256_CBC_SHA", 53, 5246, 6, 10);
    /* renamed from: I */
    public static final C2202h f6699I = C2202h.m9959a("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56, 5246, 6, 10);
    /* renamed from: J */
    public static final C2202h f6700J = C2202h.m9959a("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57, 5246, 6, 10);
    /* renamed from: K */
    public static final C2202h f6701K = C2202h.m9959a("TLS_DH_anon_WITH_AES_256_CBC_SHA", 58, 5246, 6, 10);
    /* renamed from: L */
    public static final C2202h f6702L = C2202h.m9959a("TLS_RSA_WITH_NULL_SHA256", 59, 5246, 7, 21);
    /* renamed from: M */
    public static final C2202h f6703M = C2202h.m9959a("TLS_RSA_WITH_AES_128_CBC_SHA256", 60, 5246, 7, 21);
    /* renamed from: N */
    public static final C2202h f6704N = C2202h.m9959a("TLS_RSA_WITH_AES_256_CBC_SHA256", 61, 5246, 7, 21);
    /* renamed from: O */
    public static final C2202h f6705O = C2202h.m9959a("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64, 5246, 7, 21);
    /* renamed from: P */
    public static final C2202h f6706P = C2202h.m9959a("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", 103, 5246, 7, 21);
    /* renamed from: Q */
    public static final C2202h f6707Q = C2202h.m9959a("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", 106, 5246, 7, 21);
    /* renamed from: R */
    public static final C2202h f6708R = C2202h.m9959a("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", 107, 5246, 7, 21);
    /* renamed from: S */
    public static final C2202h f6709S = C2202h.m9959a("TLS_DH_anon_WITH_AES_128_CBC_SHA256", 108, 5246, 7, 21);
    /* renamed from: T */
    public static final C2202h f6710T = C2202h.m9959a("TLS_DH_anon_WITH_AES_256_CBC_SHA256", 109, 5246, 7, 21);
    /* renamed from: U */
    public static final C2202h f6711U = C2202h.m9959a("TLS_RSA_WITH_AES_128_GCM_SHA256", 156, 5288, 8, 21);
    /* renamed from: V */
    public static final C2202h f6712V = C2202h.m9959a("TLS_RSA_WITH_AES_256_GCM_SHA384", 157, 5288, 8, 21);
    /* renamed from: W */
    public static final C2202h f6713W = C2202h.m9959a("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", 158, 5288, 8, 21);
    /* renamed from: X */
    public static final C2202h f6714X = C2202h.m9959a("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", 159, 5288, 8, 21);
    /* renamed from: Y */
    public static final C2202h f6715Y = C2202h.m9959a("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", 162, 5288, 8, 21);
    /* renamed from: Z */
    public static final C2202h f6716Z = C2202h.m9959a("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", 163, 5288, 8, 21);
    /* renamed from: a */
    public static final C2202h f6717a = C2202h.m9959a("SSL_RSA_WITH_NULL_MD5", 1, 5246, 6, 10);
    public static final C2202h aA = C2202h.m9959a("TLS_ECDH_anon_WITH_AES_128_CBC_SHA", 49176, 4492, 7, 14);
    public static final C2202h aB = C2202h.m9959a("TLS_ECDH_anon_WITH_AES_256_CBC_SHA", 49177, 4492, 7, 14);
    public static final C2202h aC = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", 49187, 5289, 7, 21);
    public static final C2202h aD = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", 49188, 5289, 7, 21);
    public static final C2202h aE = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", 49189, 5289, 7, 21);
    public static final C2202h aF = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", 49190, 5289, 7, 21);
    public static final C2202h aG = C2202h.m9959a("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", 49191, 5289, 7, 21);
    public static final C2202h aH = C2202h.m9959a("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", 49192, 5289, 7, 21);
    public static final C2202h aI = C2202h.m9959a("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", 49193, 5289, 7, 21);
    public static final C2202h aJ = C2202h.m9959a("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", 49194, 5289, 7, 21);
    public static final C2202h aK = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", 49195, 5289, 8, 21);
    public static final C2202h aL = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", 49196, 5289, 8, 21);
    public static final C2202h aM = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", 49197, 5289, 8, 21);
    public static final C2202h aN = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", 49198, 5289, 8, 21);
    public static final C2202h aO = C2202h.m9959a("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", 49199, 5289, 8, 21);
    public static final C2202h aP = C2202h.m9959a("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", 49200, 5289, 8, 21);
    public static final C2202h aQ = C2202h.m9959a("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", 49201, 5289, 8, 21);
    public static final C2202h aR = C2202h.m9959a("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", 49202, 5289, 8, 21);
    private static final ConcurrentMap<String, C2202h> aT = new ConcurrentHashMap();
    public static final C2202h aa = C2202h.m9959a("TLS_DH_anon_WITH_AES_128_GCM_SHA256", 166, 5288, 8, 21);
    public static final C2202h ab = C2202h.m9959a("TLS_DH_anon_WITH_AES_256_GCM_SHA384", 167, 5288, 8, 21);
    public static final C2202h ac = C2202h.m9959a("TLS_EMPTY_RENEGOTIATION_INFO_SCSV", 255, 5746, 6, 14);
    public static final C2202h ad = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_NULL_SHA", 49153, 4492, 7, 14);
    public static final C2202h ae = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_RC4_128_SHA", 49154, 4492, 7, 14);
    public static final C2202h af = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", 49155, 4492, 7, 14);
    public static final C2202h ag = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", 49156, 4492, 7, 14);
    public static final C2202h ah = C2202h.m9959a("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", 49157, 4492, 7, 14);
    public static final C2202h ai = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_NULL_SHA", 49158, 4492, 7, 14);
    public static final C2202h aj = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", 49159, 4492, 7, 14);
    public static final C2202h ak = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", 49160, 4492, 7, 14);
    public static final C2202h al = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", 49161, 4492, 7, 14);
    public static final C2202h am = C2202h.m9959a("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", 49162, 4492, 7, 14);
    public static final C2202h an = C2202h.m9959a("TLS_ECDH_RSA_WITH_NULL_SHA", 49163, 4492, 7, 14);
    public static final C2202h ao = C2202h.m9959a("TLS_ECDH_RSA_WITH_RC4_128_SHA", 49164, 4492, 7, 14);
    public static final C2202h ap = C2202h.m9959a("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", 49165, 4492, 7, 14);
    public static final C2202h aq = C2202h.m9959a("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", 49166, 4492, 7, 14);
    public static final C2202h ar = C2202h.m9959a("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", 49167, 4492, 7, 14);
    public static final C2202h as = C2202h.m9959a("TLS_ECDHE_RSA_WITH_NULL_SHA", 49168, 4492, 7, 14);
    public static final C2202h at = C2202h.m9959a("TLS_ECDHE_RSA_WITH_RC4_128_SHA", 49169, 4492, 7, 14);
    public static final C2202h au = C2202h.m9959a("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", 49170, 4492, 7, 14);
    public static final C2202h av = C2202h.m9959a("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", 49171, 4492, 7, 14);
    public static final C2202h aw = C2202h.m9959a("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", 49172, 4492, 7, 14);
    public static final C2202h ax = C2202h.m9959a("TLS_ECDH_anon_WITH_NULL_SHA", 49173, 4492, 7, 14);
    public static final C2202h ay = C2202h.m9959a("TLS_ECDH_anon_WITH_RC4_128_SHA", 49174, 4492, 7, 14);
    public static final C2202h az = C2202h.m9959a("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", 49175, 4492, 7, 14);
    /* renamed from: b */
    public static final C2202h f6718b = C2202h.m9959a("SSL_RSA_WITH_NULL_SHA", 2, 5246, 6, 10);
    /* renamed from: c */
    public static final C2202h f6719c = C2202h.m9959a("SSL_RSA_EXPORT_WITH_RC4_40_MD5", 3, 4346, 6, 10);
    /* renamed from: d */
    public static final C2202h f6720d = C2202h.m9959a("SSL_RSA_WITH_RC4_128_MD5", 4, 5246, 6, 10);
    /* renamed from: e */
    public static final C2202h f6721e = C2202h.m9959a("SSL_RSA_WITH_RC4_128_SHA", 5, 5246, 6, 10);
    /* renamed from: f */
    public static final C2202h f6722f = C2202h.m9959a("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", 8, 4346, 6, 10);
    /* renamed from: g */
    public static final C2202h f6723g = C2202h.m9959a("SSL_RSA_WITH_DES_CBC_SHA", 9, 5469, 6, 10);
    /* renamed from: h */
    public static final C2202h f6724h = C2202h.m9959a("SSL_RSA_WITH_3DES_EDE_CBC_SHA", 10, 5246, 6, 10);
    /* renamed from: i */
    public static final C2202h f6725i = C2202h.m9959a("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", 17, 4346, 6, 10);
    /* renamed from: j */
    public static final C2202h f6726j = C2202h.m9959a("SSL_DHE_DSS_WITH_DES_CBC_SHA", 18, 5469, 6, 10);
    /* renamed from: k */
    public static final C2202h f6727k = C2202h.m9959a("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19, 5246, 6, 10);
    /* renamed from: l */
    public static final C2202h f6728l = C2202h.m9959a("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", 20, 4346, 6, 10);
    /* renamed from: m */
    public static final C2202h f6729m = C2202h.m9959a("SSL_DHE_RSA_WITH_DES_CBC_SHA", 21, 5469, 6, 10);
    /* renamed from: n */
    public static final C2202h f6730n = C2202h.m9959a("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22, 5246, 6, 10);
    /* renamed from: o */
    public static final C2202h f6731o = C2202h.m9959a("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", 23, 4346, 6, 10);
    /* renamed from: p */
    public static final C2202h f6732p = C2202h.m9959a("SSL_DH_anon_WITH_RC4_128_MD5", 24, 5246, 6, 10);
    /* renamed from: q */
    public static final C2202h f6733q = C2202h.m9959a("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", 25, 4346, 6, 10);
    /* renamed from: r */
    public static final C2202h f6734r = C2202h.m9959a("SSL_DH_anon_WITH_DES_CBC_SHA", 26, 5469, 6, 10);
    /* renamed from: s */
    public static final C2202h f6735s = C2202h.m9959a("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", 27, 5246, 6, 10);
    /* renamed from: t */
    public static final C2202h f6736t = C2202h.m9959a("TLS_KRB5_WITH_DES_CBC_SHA", 30, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: u */
    public static final C2202h f6737u = C2202h.m9959a("TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: v */
    public static final C2202h f6738v = C2202h.m9959a("TLS_KRB5_WITH_RC4_128_SHA", 32, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: w */
    public static final C2202h f6739w = C2202h.m9959a("TLS_KRB5_WITH_DES_CBC_MD5", 34, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: x */
    public static final C2202h f6740x = C2202h.m9959a("TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: y */
    public static final C2202h f6741y = C2202h.m9959a("TLS_KRB5_WITH_RC4_128_MD5", 36, 2712, 6, Integer.MAX_VALUE);
    /* renamed from: z */
    public static final C2202h f6742z = C2202h.m9959a("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38, 2712, 6, Integer.MAX_VALUE);
    final String aS;

    private C2202h(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.aS = str;
    }

    /* renamed from: a */
    public static C2202h m9958a(String str) {
        C2202h c2202h = (C2202h) aT.get(str);
        if (c2202h != null) {
            return c2202h;
        }
        C2202h c2202h2 = new C2202h(str);
        c2202h = (C2202h) aT.putIfAbsent(str, c2202h2);
        return c2202h == null ? c2202h2 : c2202h;
    }

    /* renamed from: a */
    private static C2202h m9959a(String str, int i, int i2, int i3, int i4) {
        return C2202h.m9958a(str);
    }

    public String toString() {
        return this.aS;
    }
}
