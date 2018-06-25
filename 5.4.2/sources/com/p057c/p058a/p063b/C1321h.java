package com.p057c.p058a.p063b;

import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.c.a.b.h */
public class C1321h {
    /* renamed from: a */
    static Pattern f3998a = Pattern.compile("(....|\\.\\.)(\\[(.*)\\])?");
    /* renamed from: b */
    static final /* synthetic */ boolean f3999b = (!C1321h.class.desiredAssertionStatus());

    private C1321h() {
    }

    /* renamed from: a */
    public static <T extends C1248b> T m6765a(C1246e c1246e, String str) {
        List a = C1321h.m6767a(c1246e, str, true);
        return a.isEmpty() ? null : (C1248b) a.get(0);
    }

    /* renamed from: a */
    private static <T extends C1248b> List<T> m6766a(C1248b c1248b, String str, boolean z) {
        return C1321h.m6768a((Object) c1248b, str, z);
    }

    /* renamed from: a */
    private static <T extends C1248b> List<T> m6767a(C1246e c1246e, String str, boolean z) {
        return C1321h.m6768a((Object) c1246e, str, z);
    }

    /* renamed from: a */
    private static <T extends C1248b> List<T> m6768a(Object obj, String str, boolean z) {
        C1246e c1246e;
        int i = 0;
        if (str.startsWith("/")) {
            CharSequence substring = str.substring(1);
            c1246e = obj;
            while (c1246e instanceof C1248b) {
                c1246e = ((C1248b) c1246e).getParent();
            }
        } else {
            c1246e = obj;
        }
        if (substring.length() != 0) {
            String substring2;
            if (substring.contains("/")) {
                substring2 = substring.substring(substring.indexOf(47) + 1);
                substring = substring.substring(0, substring.indexOf(47));
            } else {
                substring2 = TtmlNode.ANONYMOUS_REGION_ID;
            }
            Matcher matcher = f3998a.matcher(substring);
            if (matcher.matches()) {
                String group = matcher.group(1);
                if ("..".equals(group)) {
                    return c1246e instanceof C1248b ? C1321h.m6767a(((C1248b) c1246e).getParent(), substring2, z) : Collections.emptyList();
                } else {
                    if (!(c1246e instanceof C1246e)) {
                        return Collections.emptyList();
                    }
                    int parseInt = matcher.group(2) != null ? Integer.parseInt(matcher.group(3)) : -1;
                    List<T> linkedList = new LinkedList();
                    for (C1248b c1248b : c1246e.mo1093a()) {
                        int i2;
                        if (c1248b.getType().matches(group)) {
                            if (parseInt == -1 || parseInt == i) {
                                linkedList.addAll(C1321h.m6766a(c1248b, substring2, z));
                            }
                            i2 = i + 1;
                        } else {
                            i2 = i;
                        }
                        if ((z || parseInt >= 0) && !linkedList.isEmpty()) {
                            return linkedList;
                        }
                        i = i2;
                    }
                    return linkedList;
                }
            }
            throw new RuntimeException(new StringBuilder(String.valueOf(substring)).append(" is invalid path.").toString());
        } else if (c1246e instanceof C1248b) {
            return Collections.singletonList((C1248b) c1246e);
        } else {
            throw new RuntimeException("Result of path expression seems to be the root container. This is not allowed!");
        }
    }

    /* renamed from: b */
    public static <T extends C1248b> List<T> m6769b(C1246e c1246e, String str) {
        return C1321h.m6767a(c1246e, str, false);
    }
}
