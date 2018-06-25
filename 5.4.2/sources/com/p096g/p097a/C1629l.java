package com.p096g.p097a;

import android.graphics.Bitmap;
import com.p096g.p097a.C1609q.C1642a;
import com.p096g.p097a.C1623g.C1621a;
import com.p096g.p097a.C1636m.C1632b;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.g.a.l */
class C1629l extends C1609q {
    /* renamed from: a */
    private final C1623g f4976a;
    /* renamed from: b */
    private final C1643r f4977b;

    /* renamed from: com.g.a.l$a */
    static class C1628a extends IOException {
        public C1628a(String str) {
            super(str);
        }
    }

    /* renamed from: a */
    public C1642a mo1243a(C1640o c1640o, int i) {
        C1621a a = this.f4976a.m7996a(c1640o.f5028d, c1640o.f5027c);
        if (a == null) {
            return null;
        }
        C1632b c1632b = a.f4957c ? C1632b.DISK : C1632b.NETWORK;
        Bitmap b = a.m7994b();
        if (b != null) {
            return new C1642a(b, c1632b);
        }
        InputStream a2 = a.m7993a();
        if (a2 == null) {
            return null;
        }
        if (c1632b == C1632b.DISK && a.m7995c() == 0) {
            C1648v.m8062a(a2);
            throw new C1628a("Received response with 0 content-length header.");
        }
        if (c1632b == C1632b.NETWORK && a.m7995c() > 0) {
            this.f4977b.m8046a(a.m7995c());
        }
        return new C1642a(a2, c1632b);
    }
}
