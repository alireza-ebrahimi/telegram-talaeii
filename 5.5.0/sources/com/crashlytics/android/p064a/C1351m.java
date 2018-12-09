package com.crashlytics.android.p064a;

/* renamed from: com.crashlytics.android.a.m */
public class C1351m extends C1326d<C1351m> {
    /* renamed from: a */
    private final String f4096a;

    public C1351m(String str) {
        if (str == null) {
            throw new NullPointerException("eventName must not be null");
        }
        this.f4096a = this.b.m6830a(str);
    }

    /* renamed from: a */
    String m6877a() {
        return this.f4096a;
    }

    public String toString() {
        return "{eventName:\"" + this.f4096a + '\"' + ", customAttributes:" + this.c + "}";
    }
}
