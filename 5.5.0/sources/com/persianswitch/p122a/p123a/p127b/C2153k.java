package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2217q;
import com.persianswitch.p122a.C2236z;

/* renamed from: com.persianswitch.a.a.b.k */
public final class C2153k {
    /* renamed from: a */
    public static long m9725a(C2217q c2217q) {
        return C2153k.m9728b(c2217q.m10025a("Content-Length"));
    }

    /* renamed from: a */
    public static long m9726a(C2236z c2236z) {
        return C2153k.m9725a(c2236z.m10220e());
    }

    /* renamed from: a */
    static boolean m9727a(String str) {
        return ("Connection".equalsIgnoreCase(str) || "Keep-Alive".equalsIgnoreCase(str) || "Proxy-Authenticate".equalsIgnoreCase(str) || "Proxy-Authorization".equalsIgnoreCase(str) || "TE".equalsIgnoreCase(str) || "Trailers".equalsIgnoreCase(str) || "Transfer-Encoding".equalsIgnoreCase(str) || "Upgrade".equalsIgnoreCase(str)) ? false : true;
    }

    /* renamed from: b */
    private static long m9728b(String str) {
        long j = -1;
        if (str != null) {
            try {
                j = Long.parseLong(str);
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }
}
