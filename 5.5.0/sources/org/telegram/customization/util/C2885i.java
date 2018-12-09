package org.telegram.customization.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.p098a.C1768f;
import com.google.p098a.p103c.C1748a;
import java.util.ArrayList;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.messenger.ApplicationLoader;
import utils.p178a.C3791b;

/* renamed from: org.telegram.customization.util.i */
public class C2885i {
    /* renamed from: b */
    private static C2885i f9511b;
    /* renamed from: a */
    public Editor f9512a = this.f9513c.edit();
    /* renamed from: c */
    private SharedPreferences f9513c;

    /* renamed from: org.telegram.customization.util.i$1 */
    static class C28831 extends C1748a<ArrayList<SlsFilter>> {
        C28831() {
        }
    }

    /* renamed from: org.telegram.customization.util.i$2 */
    static class C28842 extends C1748a<ArrayList<SlsFilter>> {
        C28842() {
        }
    }

    private C2885i(Context context, String str) {
        this.f9513c = context.getSharedPreferences(str, 0);
    }

    /* renamed from: a */
    public static Integer m13369a() {
        return Integer.valueOf(C2885i.m13370a(ApplicationLoader.applicationContext).m13393a("SP_FL"));
    }

    /* renamed from: a */
    public static C2885i m13370a(Context context) {
        if (f9511b == null) {
            f9511b = new C2885i(context, "SP_MAIN");
        }
        return f9511b;
    }

    /* renamed from: a */
    public static void m13371a(int i) {
        C2885i.m13370a(ApplicationLoader.applicationContext).m13394a("SP_FL", i);
    }

    /* renamed from: a */
    public static void m13372a(Context context, int i) {
        C2885i.m13370a(context).m13394a("SP_MAIN_GHOST", i);
    }

    /* renamed from: a */
    public static void m13373a(Context context, long j) {
        C2885i.m13370a(context).m13395a("SP_FILTERS_TIME", j);
    }

    /* renamed from: a */
    public static void m13374a(Context context, String str) {
        C2885i.m13370a(context).m13396a("SP_FILTERS", str);
    }

    /* renamed from: a */
    public static void m13375a(Context context, boolean z) {
        C2885i.m13370a(context).m13397a("SP_IS_FIRST_TIME", z);
    }

    /* renamed from: a */
    public static void m13376a(String str, Context context) {
        C2885i.m13370a(context).m13396a("SP_SEARCH_TERM", str);
    }

    /* renamed from: b */
    public static int m13377b(Context context) {
        int i = 0;
        if (C3791b.m13911O(context) != 0) {
            try {
                i = C2885i.m13370a(context).m13393a("SP_MAIN_GHOST");
            } catch (Exception e) {
            }
        }
        return i;
    }

    /* renamed from: b */
    public static void m13378b(Context context, boolean z) {
        C2885i.m13370a(context).m13397a("SP_IS_SECOND_TIME", z);
    }

    /* renamed from: c */
    public static void m13379c(Context context, boolean z) {
        C2885i.m13370a(context).m13397a("SP_MAIN_GHOST_TU", z);
    }

    /* renamed from: c */
    public static boolean m13380c(Context context) {
        return C2885i.m13370a(context).m13399c("SP_IS_FIRST_TIME");
    }

    /* renamed from: d */
    public static void m13381d(Context context, boolean z) {
        C2885i.m13370a(context).m13397a("SP_IS_3_TIME", z);
    }

    /* renamed from: d */
    public static boolean m13382d(Context context) {
        return C2885i.m13370a(context).m13399c("SP_IS_SECOND_TIME");
    }

    /* renamed from: e */
    public static long m13383e(Context context) {
        return C2885i.m13370a(context).m13398b("SP_FILTERS_TIME");
    }

    /* renamed from: e */
    public static void m13384e(Context context, boolean z) {
        C2885i.m13370a(context).m13397a("SP_MAIN_ADS_TU", z);
    }

    /* renamed from: f */
    public static ArrayList<SlsFilter> m13385f(Context context) {
        try {
            ArrayList<SlsFilter> arrayList = (ArrayList) new C1768f().m8393a(C2885i.m13370a(context).m13400d("SP_FILTERS"), new C28831().m8360b());
            if (arrayList == null || arrayList.size() <= 0) {
                return new ArrayList();
            }
            int i = 0;
            while (i < arrayList.size()) {
                int i2;
                if (((SlsFilter) arrayList.get(i)).getType() != 1) {
                    arrayList.remove(i);
                    i2 = i - 1;
                } else {
                    ((SlsFilter) arrayList.get(i)).setClickable(true);
                    i2 = i;
                }
                i = i2 + 1;
            }
            return arrayList;
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    /* renamed from: f */
    public static void m13386f(Context context, boolean z) {
        C2885i.m13370a(context).m13397a("SP_MAIN_ADS_TU1", z);
    }

    /* renamed from: g */
    public static ArrayList<SlsFilter> m13387g(Context context) {
        ArrayList<SlsFilter> arrayList = (ArrayList) new C1768f().m8393a(C2885i.m13370a(context).m13400d("SP_FILTERS"), new C28842().m8360b());
        if (arrayList == null || arrayList.size() <= 0) {
            return new ArrayList();
        }
        int i = 0;
        while (i < arrayList.size()) {
            if (((SlsFilter) arrayList.get(i)).getType() != 2) {
                arrayList.remove(i);
                i--;
            } else {
                ((SlsFilter) arrayList.get(i)).setClickable(true);
            }
            i++;
        }
        return arrayList;
    }

    /* renamed from: h */
    public static String m13388h(Context context) {
        return C2885i.m13370a(context).m13400d("SP_SEARCH_TERM");
    }

    /* renamed from: i */
    public static boolean m13389i(Context context) {
        return C2885i.m13370a(context).m13399c("SP_MAIN_GHOST_TU");
    }

    /* renamed from: j */
    public static boolean m13390j(Context context) {
        return C2885i.m13370a(context).m13399c("SP_IS_3_TIME");
    }

    /* renamed from: k */
    public static boolean m13391k(Context context) {
        return C2885i.m13370a(context).m13399c("SP_MAIN_ADS_TU");
    }

    /* renamed from: l */
    public static boolean m13392l(Context context) {
        return C2885i.m13370a(context).m13399c("SP_MAIN_ADS_TU1");
    }

    /* renamed from: a */
    public int m13393a(String str) {
        return this.f9513c.getInt(str, 0);
    }

    /* renamed from: a */
    public void m13394a(String str, int i) {
        this.f9512a.putInt(str, i);
        this.f9512a.commit();
    }

    /* renamed from: a */
    public void m13395a(String str, long j) {
        this.f9512a.putLong(str, j);
        this.f9512a.commit();
    }

    /* renamed from: a */
    public void m13396a(String str, String str2) {
        this.f9512a.putString(str, str2);
        this.f9512a.commit();
    }

    /* renamed from: a */
    public void m13397a(String str, boolean z) {
        this.f9512a.putBoolean(str, z);
        this.f9512a.commit();
    }

    /* renamed from: b */
    public long m13398b(String str) {
        return this.f9513c.getLong(str, 0);
    }

    /* renamed from: c */
    public boolean m13399c(String str) {
        return this.f9513c.getBoolean(str, true);
    }

    /* renamed from: d */
    public String m13400d(String str) {
        return this.f9513c.getString(str, null);
    }
}
