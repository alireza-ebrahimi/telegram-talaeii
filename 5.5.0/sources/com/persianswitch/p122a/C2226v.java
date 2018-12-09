package com.persianswitch.p122a;

import java.io.IOException;

/* renamed from: com.persianswitch.a.v */
public enum C2226v {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2");
    
    /* renamed from: e */
    private final String f6880e;

    private C2226v(String str) {
        this.f6880e = str;
    }

    /* renamed from: a */
    public static C2226v m10129a(String str) {
        if (str.equals(HTTP_1_0.f6880e)) {
            return HTTP_1_0;
        }
        if (str.equals(HTTP_1_1.f6880e)) {
            return HTTP_1_1;
        }
        if (str.equals(HTTP_2.f6880e)) {
            return HTTP_2;
        }
        if (str.equals(SPDY_3.f6880e)) {
            return SPDY_3;
        }
        throw new IOException("Unexpected protocol: " + str);
    }

    public String toString() {
        return this.f6880e;
    }
}
