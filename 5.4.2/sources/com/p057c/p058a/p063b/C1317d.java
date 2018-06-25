package com.p057c.p058a.p063b;

import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: com.c.a.b.d */
public class C1317d extends C1313f {
    /* renamed from: a */
    Logger f3979a;

    public C1317d(String str) {
        this.f3979a = Logger.getLogger(str);
    }

    /* renamed from: a */
    public void mo1122a(String str) {
        this.f3979a.log(Level.FINE, str);
    }

    /* renamed from: b */
    public void mo1123b(String str) {
        this.f3979a.log(Level.SEVERE, str);
    }
}
