package org.telegram.news.p175c;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.telegram.customization.p153c.C2669c;
import org.telegram.news.p177b.C3744b;

/* renamed from: org.telegram.news.c.b */
public class C3749b {
    /* renamed from: a */
    private static volatile HashMap<Integer, C3751c> f10007a;
    /* renamed from: b */
    private static volatile C3727a f10008b;
    /* renamed from: c */
    private static volatile C2669c f10009c = new C2669c();

    /* renamed from: org.telegram.news.c.b$1 */
    static class C37481 implements C3727a {
        C37481() {
        }

        /* renamed from: a */
        public void mo4194a() {
            C3749b.f10009c.m12535a();
        }
    }

    /* renamed from: a */
    public static ArrayList<C3744b> m13818a(int i) {
        return C3749b.m13829e(i) ? ((C3751c) f10007a.get(Integer.valueOf(i))).m13839b() : new ArrayList();
    }

    /* renamed from: a */
    public static C2669c m13819a() {
        if (f10009c == null) {
            f10009c = new C2669c();
        }
        return f10009c;
    }

    /* renamed from: a */
    public static void m13820a(int i, Context context, ArrayList<C3744b> arrayList) {
        if (C3749b.m13829e(i)) {
            ((C3751c) f10007a.get(Integer.valueOf(i))).m13833a(context, (ArrayList) arrayList, C3749b.m13825b());
        }
    }

    /* renamed from: a */
    public static void m13821a(int i, Context context, C3744b c3744b) {
        if (C3749b.m13829e(i)) {
            ((C3751c) f10007a.get(Integer.valueOf(i))).m13834a(context, c3744b, C3749b.m13825b());
        }
    }

    /* renamed from: a */
    public static void m13822a(int i, Context context, C3744b[] c3744bArr) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, c3744bArr);
        C3749b.m13820a(i, context, arrayList);
    }

    /* renamed from: a */
    public static void m13823a(int i, boolean z) {
        if (C3749b.m13829e(i)) {
            ((C3751c) f10007a.get(Integer.valueOf(i))).m13837a(z);
        }
    }

    /* renamed from: b */
    public static String m13824b(int i) {
        return C3749b.m13829e(i) ? ((C3751c) f10007a.get(Integer.valueOf(i))).m13841c() : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: b */
    static C3727a m13825b() {
        if (f10008b == null) {
            f10008b = new C37481();
        }
        return f10008b;
    }

    /* renamed from: c */
    public static void m13827c(int i) {
        if (C3749b.m13829e(i)) {
            ((C3751c) f10007a.get(Integer.valueOf(i))).m13836a(C3749b.m13825b());
        } else {
            f10007a.put(Integer.valueOf(i), new C3751c());
        }
    }

    /* renamed from: d */
    public static boolean m13828d(int i) {
        return C3749b.m13829e(i) ? ((C3751c) f10007a.get(Integer.valueOf(i))).m13844f() : false;
    }

    /* renamed from: e */
    private static boolean m13829e(int i) {
        if (f10007a == null) {
            f10007a = new HashMap();
        }
        if (!f10007a.containsKey(Integer.valueOf(i))) {
            f10007a.put(Integer.valueOf(i), new C3751c());
        }
        if (f10007a.get(Integer.valueOf(i)) == null) {
            f10007a.remove(Integer.valueOf(i));
            f10007a.put(Integer.valueOf(i), new C3751c());
        }
        ((C3751c) f10007a.get(Integer.valueOf(i))).m13840b(i);
        return true;
    }
}
