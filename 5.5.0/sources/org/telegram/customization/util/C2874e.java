package org.telegram.customization.util;

import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: org.telegram.customization.util.e */
public class C2874e {
    /* renamed from: a */
    public static ArrayList<String> f9489a = new ArrayList();
    /* renamed from: b */
    private static C2874e f9490b = null;

    /* renamed from: b */
    public static C2874e m13352b() {
        if (f9490b == null) {
            f9490b = new C2874e();
        }
        return f9490b;
    }

    /* renamed from: a */
    public ArrayList<String> m13353a() {
        return f9489a;
    }

    /* renamed from: a */
    public void m13354a(String str) {
        Iterator it = f9489a.iterator();
        Object obj = null;
        while (it.hasNext()) {
            obj = ((String) it.next()).contentEquals(str) ? 1 : obj;
        }
        if (obj != null) {
            f9489a.remove(str);
            f9489a.add(str);
            return;
        }
        f9489a.add(str);
    }
}
