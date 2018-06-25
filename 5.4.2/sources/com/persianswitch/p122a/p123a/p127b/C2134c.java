package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2133s;
import com.persianswitch.p122a.C2133s.C2154a;
import com.persianswitch.p122a.C2231x;
import com.persianswitch.p122a.C2236z;
import com.persianswitch.p126b.C2242d;
import com.persianswitch.p126b.C2253l;
import java.net.ProtocolException;

/* renamed from: com.persianswitch.a.a.b.c */
public final class C2134c implements C2133s {
    /* renamed from: a */
    private final boolean f6469a;

    public C2134c(boolean z) {
        this.f6469a = z;
    }

    /* renamed from: a */
    public C2236z mo3136a(C2154a c2154a) {
        C2143j d = ((C2155l) c2154a).m9737d();
        C2162s c = ((C2155l) c2154a).m9736c();
        C2231x a = c2154a.mo3146a();
        long currentTimeMillis = System.currentTimeMillis();
        d.mo3141a(a);
        if (C2152i.m9723c(a.m10159b()) && a.m10161d() != null) {
            C2242d a2 = C2253l.m10357a(d.mo3139a(a, a.m10161d().mo3171b()));
            a.m10161d().mo3170a(a2);
            a2.close();
        }
        d.mo3142b();
        C2236z a3 = d.mo3138a().m10194a(a).m10191a(c.m9777b().m9821c()).m10189a(currentTimeMillis).m10199b(System.currentTimeMillis()).m10198a();
        if (!(this.f6469a && a3.m10217b() == 101)) {
            a3 = a3.m10222g().m10190a(d.mo3137a(a3)).m10198a();
        }
        if ("close".equalsIgnoreCase(a3.m10214a().m10158a("Connection")) || "close".equalsIgnoreCase(a3.m10215a("Connection"))) {
            c.m9779d();
        }
        int b = a3.m10217b();
        if ((b != 204 && b != 205) || a3.m10221f().mo3144b() <= 0) {
            return a3;
        }
        throw new ProtocolException("HTTP " + b + " had non-zero Content-Length: " + a3.m10221f().mo3144b());
    }
}
