package com.persianswitch.p122a;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.persianswitch.a.t */
public final class C2222t {
    /* renamed from: a */
    private static final Pattern f6817a = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
    /* renamed from: b */
    private static final Pattern f6818b = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    /* renamed from: c */
    private final String f6819c;
    /* renamed from: d */
    private final String f6820d;
    /* renamed from: e */
    private final String f6821e;
    /* renamed from: f */
    private final String f6822f;

    private C2222t(String str, String str2, String str3, String str4) {
        this.f6819c = str;
        this.f6820d = str2;
        this.f6821e = str3;
        this.f6822f = str4;
    }

    /* renamed from: a */
    public static C2222t m10083a(String str) {
        Matcher matcher = f6817a.matcher(str);
        if (!matcher.lookingAt()) {
            return null;
        }
        String toLowerCase = matcher.group(1).toLowerCase(Locale.US);
        String toLowerCase2 = matcher.group(2).toLowerCase(Locale.US);
        Matcher matcher2 = f6818b.matcher(str);
        String str2 = null;
        for (int end = matcher.end(); end < str.length(); end = matcher2.end()) {
            matcher2.region(end, str.length());
            if (!matcher2.lookingAt()) {
                return null;
            }
            String group = matcher2.group(1);
            if (group != null && group.equalsIgnoreCase("charset")) {
                group = matcher2.group(2) != null ? matcher2.group(2) : matcher2.group(3);
                if (str2 == null || group.equalsIgnoreCase(str2)) {
                    str2 = group;
                } else {
                    throw new IllegalArgumentException("Multiple different charsets: " + str);
                }
            }
        }
        return new C2222t(str, toLowerCase, toLowerCase2, str2);
    }

    /* renamed from: a */
    public Charset m10084a() {
        return this.f6822f != null ? Charset.forName(this.f6822f) : null;
    }

    /* renamed from: a */
    public Charset m10085a(Charset charset) {
        return this.f6822f != null ? Charset.forName(this.f6822f) : charset;
    }

    public boolean equals(Object obj) {
        return (obj instanceof C2222t) && ((C2222t) obj).f6819c.equals(this.f6819c);
    }

    public int hashCode() {
        return this.f6819c.hashCode();
    }

    public String toString() {
        return this.f6819c;
    }
}
