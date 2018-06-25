package com.persianswitch.p122a.p123a.p125a;

/* renamed from: com.persianswitch.a.a.a.a */
public enum C2073a {
    NO_ERROR(0, -1, 0),
    PROTOCOL_ERROR(1, 1, 1),
    INVALID_STREAM(1, 2, -1),
    UNSUPPORTED_VERSION(1, 4, -1),
    STREAM_IN_USE(1, 8, -1),
    STREAM_ALREADY_CLOSED(1, 9, -1),
    INTERNAL_ERROR(2, 6, 2),
    FLOW_CONTROL_ERROR(3, 7, -1),
    STREAM_CLOSED(5, -1, -1),
    FRAME_TOO_LARGE(6, 11, -1),
    REFUSED_STREAM(7, 3, -1),
    CANCEL(8, 5, -1),
    COMPRESSION_ERROR(9, -1, -1),
    CONNECT_ERROR(10, -1, -1),
    ENHANCE_YOUR_CALM(11, -1, -1),
    INADEQUATE_SECURITY(12, -1, -1),
    HTTP_1_1_REQUIRED(13, -1, -1),
    INVALID_CREDENTIALS(-1, 10, -1);
    
    /* renamed from: s */
    public final int f6260s;
    /* renamed from: t */
    public final int f6261t;
    /* renamed from: u */
    public final int f6262u;

    private C2073a(int i, int i2, int i3) {
        this.f6260s = i;
        this.f6261t = i2;
        this.f6262u = i3;
    }

    /* renamed from: a */
    public static C2073a m9288a(int i) {
        for (C2073a c2073a : C2073a.values()) {
            if (c2073a.f6261t == i) {
                return c2073a;
            }
        }
        return null;
    }

    /* renamed from: b */
    public static C2073a m9289b(int i) {
        for (C2073a c2073a : C2073a.values()) {
            if (c2073a.f6260s == i) {
                return c2073a;
            }
        }
        return null;
    }

    /* renamed from: c */
    public static C2073a m9290c(int i) {
        for (C2073a c2073a : C2073a.values()) {
            if (c2073a.f6262u == i) {
                return c2073a;
            }
        }
        return null;
    }
}
