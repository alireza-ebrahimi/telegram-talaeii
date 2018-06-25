package com.p111h.p112a.p113a;

/* renamed from: com.h.a.a.h */
public abstract class C1968h extends Exception {
    /* renamed from: a */
    private String f5809a;
    /* renamed from: b */
    private Integer f5810b;

    public C1968h(String str, String str2, Integer num) {
        super(str, null);
        this.f5809a = str2;
        this.f5810b = num;
    }

    public C1968h(String str, String str2, Integer num, Throwable th) {
        super(str, th);
        this.f5810b = num;
        this.f5809a = str2;
    }

    public String toString() {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        if (this.f5809a != null) {
            str = "; request-id: " + this.f5809a;
        }
        return super.toString() + str;
    }
}
