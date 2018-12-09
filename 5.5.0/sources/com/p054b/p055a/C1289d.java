package com.p054b.p055a;

import com.p057c.p058a.C1247d;
import com.p057c.p058a.C1254e;
import com.p057c.p058a.C1323f;
import com.p057c.p058a.p063b.C1313f;
import java.io.Closeable;
import java.io.File;

/* renamed from: com.b.a.d */
public class C1289d extends C1247d implements Closeable {
    /* renamed from: a */
    private static C1313f f3843a = C1313f.m6751a(C1289d.class);

    public C1289d(C1254e c1254e) {
        this(c1254e, new C1292g(new String[0]));
    }

    public C1289d(C1254e c1254e, C1286b c1286b) {
        mo1094a(c1254e, c1254e.mo1103a(), c1286b);
    }

    public C1289d(String str) {
        this(new C1323f(new File(str)));
    }

    /* renamed from: a */
    public static byte[] m6665a(String str) {
        byte[] bArr = new byte[4];
        if (str != null) {
            for (int i = 0; i < Math.min(4, str.length()); i++) {
                bArr[i] = (byte) str.charAt(i);
            }
        }
        return bArr;
    }

    public void close() {
        this.g.close();
    }

    public String toString() {
        return "model(" + this.g.toString() + ")";
    }
}
