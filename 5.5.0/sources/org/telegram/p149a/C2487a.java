package org.telegram.p149a;

import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: org.telegram.a.a */
public class C2487a {
    /* renamed from: a */
    public ArrayList<String> f8308a = new ArrayList();
    /* renamed from: b */
    public String f8309b = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: c */
    public ArrayList<String> f8310c = new ArrayList();
    /* renamed from: d */
    public ArrayList<String> f8311d = new ArrayList();
    /* renamed from: e */
    public ArrayList<C2490d> f8312e = new ArrayList();

    /* renamed from: a */
    String m12184a(String str) {
        Iterator it = this.f8311d.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str.startsWith(str2)) {
                return str2;
            }
        }
        return null;
    }

    /* renamed from: b */
    String m12185b(String str) {
        Iterator it = this.f8310c.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str.startsWith(str2)) {
                return str2;
            }
        }
        return null;
    }

    /* renamed from: c */
    String m12186c(String str) {
        String str2;
        String substring;
        String str3;
        String str4 = null;
        if (str.startsWith(this.f8309b)) {
            str2 = this.f8309b;
            substring = str.substring(str2.length());
            str3 = null;
            str4 = str2;
        } else {
            str2 = m12185b(str);
            if (str2 != null) {
                substring = str.substring(str2.length());
                str3 = str2;
            } else {
                str3 = null;
                substring = str;
            }
        }
        Iterator it = this.f8312e.iterator();
        while (it.hasNext()) {
            str2 = ((C2490d) it.next()).m12200a(substring, str4, str3, true);
            if (str2 != null) {
                return str2;
            }
        }
        it = this.f8312e.iterator();
        while (it.hasNext()) {
            str2 = ((C2490d) it.next()).m12200a(substring, str4, str3, false);
            if (str2 != null) {
                return str2;
            }
        }
        if (str4 == null || substring.length() == 0) {
            return str;
        }
        return String.format("%s %s", new Object[]{str4, substring});
    }
}
