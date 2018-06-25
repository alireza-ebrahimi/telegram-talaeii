package org.telegram.customization.p153c;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;

/* renamed from: org.telegram.customization.c.d */
public class C2671d {
    /* renamed from: a */
    private static volatile HashMap<Integer, C2668b> f8906a;
    /* renamed from: b */
    private static volatile C2516a f8907b;
    /* renamed from: c */
    private static volatile C2669c f8908c = new C2669c();

    /* renamed from: org.telegram.customization.c.d$1 */
    static class C26701 implements C2516a {
        C26701() {
        }

        /* renamed from: a */
        public void mo3423a() {
            C2671d.f8908c.m12535a();
        }
    }

    /* renamed from: a */
    public static List<SlsBaseMessage> m12537a(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12520b() : new ArrayList();
    }

    /* renamed from: a */
    public static C2669c m12538a() {
        if (f8908c == null) {
            f8908c = new C2669c();
        }
        return f8908c;
    }

    /* renamed from: a */
    public static SlsBaseMessage m12539a(int i, int i2) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12512a(i2) : null;
    }

    /* renamed from: a */
    public static void m12540a(int i, long j) {
        if (C2671d.m12559k(i)) {
            ((C2668b) f8906a.get(Integer.valueOf(i))).m12514a(j);
        }
    }

    /* renamed from: a */
    public static void m12541a(int i, Context context, SlsBaseMessage slsBaseMessage) {
        if (C2671d.m12559k(i)) {
            ((C2668b) f8906a.get(Integer.valueOf(i))).m12515a(context, slsBaseMessage, C2671d.m12547b(), true);
        }
    }

    /* renamed from: a */
    public static void m12542a(int i, String str) {
        if (C2671d.m12559k(i)) {
            ((C2668b) f8906a.get(Integer.valueOf(i))).m12523b(str);
        }
    }

    /* renamed from: a */
    public static void m12543a(int i, ArrayList<ObjBase> arrayList) {
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                C2671d.m12545a((ObjBase) it.next(), i);
            }
        }
    }

    /* renamed from: a */
    public static void m12544a(int i, boolean z) {
        if (C2671d.m12559k(i)) {
            ((C2668b) f8906a.get(Integer.valueOf(i))).m12524b(z);
        }
    }

    /* renamed from: a */
    public static void m12545a(ObjBase objBase, int i) {
        if (SlsBaseMessage.isMediaAvailable(objBase)) {
            C2671d.m12541a(i, null, (SlsBaseMessage) objBase);
        }
    }

    /* renamed from: b */
    public static long m12546b(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12525c() : 0;
    }

    /* renamed from: b */
    static C2516a m12547b() {
        if (f8907b == null) {
            f8907b = new C26701();
        }
        return f8907b;
    }

    /* renamed from: b */
    public static void m12548b(int i, int i2) {
        if (C2671d.m12559k(i)) {
            ((C2668b) f8906a.get(Integer.valueOf(i))).m12526c(i2);
        }
    }

    /* renamed from: b */
    public static void m12549b(int i, long j) {
        if (C2671d.m12559k(i)) {
            ((C2668b) f8906a.get(Integer.valueOf(i))).m12522b(j);
        }
    }

    /* renamed from: c */
    public static void m12551c(int i) {
        if (C2671d.m12559k(i)) {
            ((C2668b) f8906a.get(Integer.valueOf(i))).m12517a(C2671d.m12547b());
        } else {
            f8906a.put(Integer.valueOf(i), new C2668b());
        }
    }

    /* renamed from: d */
    public static int m12552d(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12528e() : 0;
    }

    /* renamed from: e */
    public static boolean m12553e(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12529f() : false;
    }

    /* renamed from: f */
    public static String m12554f(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12530g() : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: g */
    public static boolean m12555g(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12534k() : false;
    }

    /* renamed from: h */
    public static int m12556h(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12531h() : 20;
    }

    /* renamed from: i */
    public static long m12557i(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12532i() : 0;
    }

    /* renamed from: j */
    public static long m12558j(int i) {
        return C2671d.m12559k(i) ? ((C2668b) f8906a.get(Integer.valueOf(i))).m12533j() : 0;
    }

    /* renamed from: k */
    private static boolean m12559k(int i) {
        if (f8906a == null) {
            f8906a = new HashMap();
        }
        if (!f8906a.containsKey(Integer.valueOf(i))) {
            f8906a.put(Integer.valueOf(i), new C2668b());
        }
        if (f8906a.get(Integer.valueOf(i)) == null) {
            f8906a.remove(Integer.valueOf(i));
            f8906a.put(Integer.valueOf(i), new C2668b());
        }
        ((C2668b) f8906a.get(Integer.valueOf(i))).m12521b(i);
        return true;
    }
}
