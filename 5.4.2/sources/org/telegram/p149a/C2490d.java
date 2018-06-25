package org.telegram.p149a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: org.telegram.a.d */
public class C2490d {
    /* renamed from: e */
    public static Pattern f8334e = Pattern.compile("[0-9]+");
    /* renamed from: a */
    public int f8335a;
    /* renamed from: b */
    public ArrayList<C2489c> f8336b = new ArrayList();
    /* renamed from: c */
    public boolean f8337c;
    /* renamed from: d */
    public boolean f8338d;

    /* renamed from: a */
    String m12200a(String str, String str2, String str3, boolean z) {
        if (str.length() < this.f8335a) {
            return null;
        }
        Matcher matcher = f8334e.matcher(str.substring(0, this.f8335a));
        int parseInt = matcher.find() ? Integer.parseInt(matcher.group(0)) : 0;
        Iterator it = this.f8336b.iterator();
        while (it.hasNext()) {
            C2489c c2489c = (C2489c) it.next();
            if (parseInt >= c2489c.f8323a && parseInt <= c2489c.f8324b && str.length() <= c2489c.f8326d) {
                if (z) {
                    if (((c2489c.f8329g & 3) == 0 && str3 == null && str2 == null) || !((str3 == null || (c2489c.f8329g & 1) == 0) && (str2 == null || (c2489c.f8329g & 2) == 0))) {
                        return c2489c.m12199a(str, str2, str3);
                    }
                } else if ((str3 == null && str2 == null) || !((str3 == null || (c2489c.f8329g & 1) == 0) && (str2 == null || (c2489c.f8329g & 2) == 0))) {
                    return c2489c.m12199a(str, str2, str3);
                }
            }
        }
        if (!z) {
            if (str2 != null) {
                it = this.f8336b.iterator();
                while (it.hasNext()) {
                    c2489c = (C2489c) it.next();
                    if (parseInt >= c2489c.f8323a && parseInt <= c2489c.f8324b && str.length() <= c2489c.f8326d) {
                        if (str3 == null || (c2489c.f8329g & 1) != 0) {
                            return c2489c.m12199a(str, str2, str3);
                        }
                    }
                }
            } else if (str3 != null) {
                it = this.f8336b.iterator();
                while (it.hasNext()) {
                    c2489c = (C2489c) it.next();
                    if (parseInt >= c2489c.f8323a && parseInt <= c2489c.f8324b && str.length() <= c2489c.f8326d) {
                        if (str2 == null || (c2489c.f8329g & 2) != 0) {
                            return c2489c.m12199a(str, str2, str3);
                        }
                    }
                }
            }
        }
        return null;
    }
}
