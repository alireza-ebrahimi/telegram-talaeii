package com.persianswitch.p122a.p123a.p127b;

import utils.volley.toolbox.HttpClientStack$HttpPatch;

/* renamed from: com.persianswitch.a.a.b.i */
public final class C2152i {
    /* renamed from: a */
    public static boolean m9721a(String str) {
        return str.equals("POST") || str.equals(HttpClientStack$HttpPatch.METHOD_NAME) || str.equals("PUT") || str.equals("DELETE") || str.equals("MOVE");
    }

    /* renamed from: b */
    public static boolean m9722b(String str) {
        return str.equals("POST") || str.equals("PUT") || str.equals(HttpClientStack$HttpPatch.METHOD_NAME) || str.equals("PROPPATCH") || str.equals("REPORT");
    }

    /* renamed from: c */
    public static boolean m9723c(String str) {
        return C2152i.m9722b(str) || str.equals("OPTIONS") || str.equals("DELETE") || str.equals("PROPFIND") || str.equals("MKCOL") || str.equals("LOCK");
    }

    /* renamed from: d */
    public static boolean m9724d(String str) {
        return !str.equals("PROPFIND");
    }
}
