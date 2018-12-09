package com.p057c.p058a.p063b;

import java.util.Date;

/* renamed from: com.c.a.b.c */
public class C1316c {
    /* renamed from: a */
    public static long m6757a(Date date) {
        return (date.getTime() / 1000) + 2082844800;
    }

    /* renamed from: a */
    public static Date m6758a(long j) {
        return new Date((j - 2082844800) * 1000);
    }
}
