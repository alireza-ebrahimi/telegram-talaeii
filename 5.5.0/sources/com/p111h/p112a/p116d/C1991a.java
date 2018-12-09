package com.p111h.p112a.p116d;

import java.util.Calendar;

/* renamed from: com.h.a.d.a */
public class C1991a {
    /* renamed from: b */
    private static C1991a f5868b;
    /* renamed from: a */
    protected Calendar f5869a;

    /* renamed from: a */
    protected static C1991a m9004a() {
        if (f5868b == null) {
            f5868b = new C1991a();
        }
        return f5868b;
    }

    /* renamed from: b */
    public static Calendar m9005b() {
        return C1991a.m9004a().m9006c();
    }

    /* renamed from: c */
    private Calendar m9006c() {
        return this.f5869a != null ? (Calendar) this.f5869a.clone() : Calendar.getInstance();
    }
}
