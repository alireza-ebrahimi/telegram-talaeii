package com.persianswitch.p122a.p123a.p125a;

/* renamed from: com.persianswitch.a.a.a.g */
public enum C2103g {
    SPDY_SYN_STREAM,
    SPDY_REPLY,
    SPDY_HEADERS,
    HTTP_20_HEADERS;

    /* renamed from: a */
    public boolean m9461a() {
        return this == SPDY_REPLY || this == SPDY_HEADERS;
    }

    /* renamed from: b */
    public boolean m9462b() {
        return this == SPDY_SYN_STREAM;
    }

    /* renamed from: c */
    public boolean m9463c() {
        return this == SPDY_HEADERS;
    }

    /* renamed from: d */
    public boolean m9464d() {
        return this == SPDY_REPLY;
    }
}
