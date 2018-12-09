package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2221r;
import com.persianswitch.p122a.C2231x;
import java.net.Proxy.Type;

/* renamed from: com.persianswitch.a.a.b.n */
public final class C2157n {
    /* renamed from: a */
    public static String m9741a(C2221r c2221r) {
        String h = c2221r.m10077h();
        String j = c2221r.m10079j();
        return j != null ? h + '?' + j : h;
    }

    /* renamed from: a */
    static String m9742a(C2231x c2231x, Type type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(c2231x.m10159b());
        stringBuilder.append(' ');
        if (C2157n.m9743b(c2231x, type)) {
            stringBuilder.append(c2231x.m10157a());
        } else {
            stringBuilder.append(C2157n.m9741a(c2231x.m10157a()));
        }
        stringBuilder.append(" HTTP/1.1");
        return stringBuilder.toString();
    }

    /* renamed from: b */
    private static boolean m9743b(C2231x c2231x, Type type) {
        return !c2231x.m10164g() && type == Type.HTTP;
    }
}
