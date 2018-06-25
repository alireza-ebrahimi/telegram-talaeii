package org.telegram.customization.fetch.p166d;

/* renamed from: org.telegram.customization.fetch.d.a */
public final class C2751a {
    /* renamed from: a */
    private final String f9056a;
    /* renamed from: b */
    private final String f9057b;

    public C2751a(String str, String str2) {
        if (str == null) {
            throw new NullPointerException("header cannot be null");
        } else if (str.contains(":")) {
            throw new IllegalArgumentException("header may not contain ':'");
        } else {
            if (str2 == null) {
                str2 = TtmlNode.ANONYMOUS_REGION_ID;
            }
            this.f9056a = str;
            this.f9057b = str2;
        }
    }

    /* renamed from: a */
    public String m12744a() {
        return this.f9056a;
    }

    /* renamed from: b */
    public String m12745b() {
        return this.f9057b;
    }

    public String toString() {
        return this.f9056a + ":" + this.f9057b;
    }
}
