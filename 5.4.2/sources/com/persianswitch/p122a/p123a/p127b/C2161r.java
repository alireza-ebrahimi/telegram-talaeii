package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2226v;
import java.net.ProtocolException;

/* renamed from: com.persianswitch.a.a.b.r */
public final class C2161r {
    /* renamed from: a */
    public final C2226v f6548a;
    /* renamed from: b */
    public final int f6549b;
    /* renamed from: c */
    public final String f6550c;

    public C2161r(C2226v c2226v, int i, String str) {
        this.f6548a = c2226v;
        this.f6549b = i;
        this.f6550c = str;
    }

    /* renamed from: a */
    public static C2161r m9766a(String str) {
        C2226v c2226v;
        int i = 9;
        if (str.startsWith("HTTP/1.")) {
            if (str.length() < 9 || str.charAt(8) != ' ') {
                throw new ProtocolException("Unexpected status line: " + str);
            }
            int charAt = str.charAt(7) - 48;
            if (charAt == 0) {
                c2226v = C2226v.HTTP_1_0;
            } else if (charAt == 1) {
                c2226v = C2226v.HTTP_1_1;
            } else {
                throw new ProtocolException("Unexpected status line: " + str);
            }
        } else if (str.startsWith("ICY ")) {
            c2226v = C2226v.HTTP_1_0;
            i = 4;
        } else {
            throw new ProtocolException("Unexpected status line: " + str);
        }
        if (str.length() < i + 3) {
            throw new ProtocolException("Unexpected status line: " + str);
        }
        try {
            String str2;
            int parseInt = Integer.parseInt(str.substring(i, i + 3));
            String str3 = TtmlNode.ANONYMOUS_REGION_ID;
            if (str.length() <= i + 3) {
                str2 = str3;
            } else if (str.charAt(i + 3) != ' ') {
                throw new ProtocolException("Unexpected status line: " + str);
            } else {
                str2 = str.substring(i + 4);
            }
            return new C2161r(c2226v, parseInt, str2);
        } catch (NumberFormatException e) {
            throw new ProtocolException("Unexpected status line: " + str);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f6548a == C2226v.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1");
        stringBuilder.append(' ').append(this.f6549b);
        if (this.f6550c != null) {
            stringBuilder.append(' ').append(this.f6550c);
        }
        return stringBuilder.toString();
    }
}
