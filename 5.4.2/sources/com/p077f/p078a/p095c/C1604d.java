package com.p077f.p078a.p095c;

import com.p077f.p078a.p086b.p087a.C1553e;
import java.util.Comparator;

/* renamed from: com.f.a.c.d */
public final class C1604d {

    /* renamed from: com.f.a.c.d$1 */
    static class C16031 implements Comparator<String> {
        C16031() {
        }

        /* renamed from: a */
        public int m7943a(String str, String str2) {
            return str.substring(0, str.lastIndexOf("_")).compareTo(str2.substring(0, str2.lastIndexOf("_")));
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m7943a((String) obj, (String) obj2);
        }
    }

    /* renamed from: a */
    public static String m7944a(String str, C1553e c1553e) {
        return "_" + c1553e.m7673a() + "x" + c1553e.m7676b();
    }

    /* renamed from: a */
    public static Comparator<String> m7945a() {
        return new C16031();
    }
}
