package com.crashlytics.android.p066c;

import android.app.ActivityManager.RunningAppProcessInfo;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1122p.C1121a;

/* renamed from: com.crashlytics.android.c.ag */
class ag {
    /* renamed from: a */
    private static final C1395b f4216a = C1395b.m7039a("0");
    /* renamed from: b */
    private static final C1395b f4217b = C1395b.m7039a("Unity");

    /* renamed from: a */
    private static int m6998a() {
        return ((0 + C1400e.m7056b(1, f4216a)) + C1400e.m7056b(2, f4216a)) + C1400e.m7055b(3, 0);
    }

    /* renamed from: a */
    private static int m6999a(int i, C1395b c1395b, int i2, long j, long j2, boolean z, Map<C1121a, String> map, int i3, C1395b c1395b2, C1395b c1395b3) {
        int i4;
        int b = (((((c1395b == null ? 0 : C1400e.m7056b(4, c1395b)) + (C1400e.m7064e(3, i) + 0)) + C1400e.m7061d(5, i2)) + C1400e.m7055b(6, j)) + C1400e.m7055b(7, j2)) + C1400e.m7057b(10, z);
        if (map != null) {
            i4 = b;
            for (Entry entry : map.entrySet()) {
                b = ag.m7001a((C1121a) entry.getKey(), (String) entry.getValue());
                i4 = (b + (C1400e.m7069j(11) + C1400e.m7070l(b))) + i4;
            }
        } else {
            i4 = b;
        }
        return (c1395b3 == null ? 0 : C1400e.m7056b(14, c1395b3)) + ((i4 + C1400e.m7061d(12, i3)) + (c1395b2 == null ? 0 : C1400e.m7056b(13, c1395b2)));
    }

    /* renamed from: a */
    private static int m7000a(long j, String str, aj ajVar, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, Map<String, String> map, RunningAppProcessInfo runningAppProcessInfo, int i2, C1395b c1395b, C1395b c1395b2, Float f, int i3, boolean z, long j2, long j3, C1395b c1395b3) {
        int b = (0 + C1400e.m7055b(1, j)) + C1400e.m7056b(2, C1395b.m7039a(str));
        int a = ag.m7004a(ajVar, thread, stackTraceElementArr, threadArr, (List) list, i, c1395b, c1395b2, (Map) map, runningAppProcessInfo, i2);
        int j4 = b + (a + (C1400e.m7069j(3) + C1400e.m7070l(a)));
        a = ag.m7009a(f, i3, z, i2, j2, j3);
        a = (a + (C1400e.m7069j(5) + C1400e.m7070l(a))) + j4;
        if (c1395b3 == null) {
            return a;
        }
        int b2 = ag.m7028b(c1395b3);
        return a + (b2 + (C1400e.m7069j(6) + C1400e.m7070l(b2)));
    }

    /* renamed from: a */
    private static int m7001a(C1121a c1121a, String str) {
        return C1400e.m7064e(1, c1121a.f3293h) + C1400e.m7056b(2, C1395b.m7039a(str));
    }

    /* renamed from: a */
    private static int m7002a(aj ajVar, int i, int i2) {
        int i3 = 0;
        int b = C1400e.m7056b(1, C1395b.m7039a(ajVar.f4222b)) + 0;
        String str = ajVar.f4221a;
        if (str != null) {
            b += C1400e.m7056b(3, C1395b.m7039a(str));
        }
        StackTraceElement[] stackTraceElementArr = ajVar.f4223c;
        int length = stackTraceElementArr.length;
        int i4 = 0;
        while (i4 < length) {
            int a = ag.m7010a(stackTraceElementArr[i4], true);
            i4++;
            b = (a + (C1400e.m7069j(4) + C1400e.m7070l(a))) + b;
        }
        aj ajVar2 = ajVar.f4224d;
        if (ajVar2 == null) {
            return b;
        }
        if (i < i2) {
            i3 = ag.m7002a(ajVar2, i + 1, i2);
            return b + (i3 + (C1400e.m7069j(6) + C1400e.m7070l(i3)));
        }
        while (ajVar2 != null) {
            ajVar2 = ajVar2.f4224d;
            i3++;
        }
        return b + C1400e.m7061d(7, i3);
    }

    /* renamed from: a */
    private static int m7003a(aj ajVar, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, C1395b c1395b, C1395b c1395b2) {
        int a;
        int a2 = ag.m7012a(thread, stackTraceElementArr, 4, true);
        a2 = (a2 + (C1400e.m7069j(1) + C1400e.m7070l(a2))) + 0;
        int length = threadArr.length;
        int i2 = a2;
        for (a2 = 0; a2 < length; a2++) {
            a = ag.m7012a(threadArr[a2], (StackTraceElement[]) list.get(a2), 0, false);
            i2 += a + (C1400e.m7069j(1) + C1400e.m7070l(a));
        }
        a = ag.m7002a(ajVar, 1, i);
        a = (a + (C1400e.m7069j(2) + C1400e.m7070l(a))) + i2;
        a2 = ag.m6998a();
        a += a2 + (C1400e.m7069j(3) + C1400e.m7070l(a2));
        a2 = ag.m7006a(c1395b, c1395b2);
        return a + (a2 + (C1400e.m7069j(3) + C1400e.m7070l(a2)));
    }

    /* renamed from: a */
    private static int m7004a(aj ajVar, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, C1395b c1395b, C1395b c1395b2, Map<String, String> map, RunningAppProcessInfo runningAppProcessInfo, int i2) {
        int a = ag.m7003a(ajVar, thread, stackTraceElementArr, threadArr, (List) list, i, c1395b, c1395b2);
        int j = 0 + (a + (C1400e.m7069j(1) + C1400e.m7070l(a)));
        if (map != null) {
            int i3 = j;
            for (Entry entry : map.entrySet()) {
                j = ag.m7011a((String) entry.getKey(), (String) entry.getValue());
                i3 = (j + (C1400e.m7069j(2) + C1400e.m7070l(j))) + i3;
            }
            a = i3;
        } else {
            a = j;
        }
        if (runningAppProcessInfo != null) {
            j = C1400e.m7057b(3, runningAppProcessInfo.importance != 100) + a;
        } else {
            j = a;
        }
        return j + C1400e.m7061d(4, i2);
    }

    /* renamed from: a */
    private static int m7005a(C1395b c1395b) {
        return 0 + C1400e.m7056b(1, c1395b);
    }

    /* renamed from: a */
    private static int m7006a(C1395b c1395b, C1395b c1395b2) {
        int b = ((0 + C1400e.m7055b(1, 0)) + C1400e.m7055b(2, 0)) + C1400e.m7056b(3, c1395b);
        return c1395b2 != null ? b + C1400e.m7056b(4, c1395b2) : b;
    }

    /* renamed from: a */
    private static int m7007a(C1395b c1395b, C1395b c1395b2, C1395b c1395b3, C1395b c1395b4, C1395b c1395b5, int i, C1395b c1395b6) {
        int b = ((0 + C1400e.m7056b(1, c1395b)) + C1400e.m7056b(2, c1395b3)) + C1400e.m7056b(3, c1395b4);
        int a = ag.m7005a(c1395b2);
        b = (b + (a + (C1400e.m7069j(5) + C1400e.m7070l(a)))) + C1400e.m7056b(6, c1395b5);
        if (c1395b6 != null) {
            b = (b + C1400e.m7056b(8, f4217b)) + C1400e.m7056b(9, c1395b6);
        }
        return b + C1400e.m7064e(10, i);
    }

    /* renamed from: a */
    private static int m7008a(C1395b c1395b, C1395b c1395b2, boolean z) {
        return (((0 + C1400e.m7064e(1, 3)) + C1400e.m7056b(2, c1395b)) + C1400e.m7056b(3, c1395b2)) + C1400e.m7057b(4, z);
    }

    /* renamed from: a */
    private static int m7009a(Float f, int i, boolean z, int i2, long j, long j2) {
        int i3 = 0;
        if (f != null) {
            i3 = 0 + C1400e.m7054b(1, f.floatValue());
        }
        return ((((i3 + C1400e.m7066f(2, i)) + C1400e.m7057b(3, z)) + C1400e.m7061d(4, i2)) + C1400e.m7055b(5, j)) + C1400e.m7055b(6, j2);
    }

    /* renamed from: a */
    private static int m7010a(StackTraceElement stackTraceElement, boolean z) {
        int b = (stackTraceElement.isNativeMethod() ? C1400e.m7055b(1, (long) Math.max(stackTraceElement.getLineNumber(), 0)) + 0 : C1400e.m7055b(1, 0) + 0) + C1400e.m7056b(2, C1395b.m7039a(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
        if (stackTraceElement.getFileName() != null) {
            b += C1400e.m7056b(3, C1395b.m7039a(stackTraceElement.getFileName()));
        }
        int b2 = (stackTraceElement.isNativeMethod() || stackTraceElement.getLineNumber() <= 0) ? b : b + C1400e.m7055b(4, (long) stackTraceElement.getLineNumber());
        return C1400e.m7061d(5, z ? 2 : 0) + b2;
    }

    /* renamed from: a */
    private static int m7011a(String str, String str2) {
        int b = C1400e.m7056b(1, C1395b.m7039a(str));
        if (str2 == null) {
            str2 = TtmlNode.ANONYMOUS_REGION_ID;
        }
        return b + C1400e.m7056b(2, C1395b.m7039a(str2));
    }

    /* renamed from: a */
    private static int m7012a(Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) {
        int d = C1400e.m7061d(2, i) + C1400e.m7056b(1, C1395b.m7039a(thread.getName()));
        for (StackTraceElement a : stackTraceElementArr) {
            int a2 = ag.m7010a(a, z);
            d += a2 + (C1400e.m7069j(3) + C1400e.m7070l(a2));
        }
        return d;
    }

    /* renamed from: a */
    private static C1395b m7013a(String str) {
        return str == null ? null : C1395b.m7039a(str);
    }

    /* renamed from: a */
    private static void m7014a(C1400e c1400e, int i, StackTraceElement stackTraceElement, boolean z) {
        int i2 = 4;
        c1400e.m7093g(i, 2);
        c1400e.m7095k(ag.m7010a(stackTraceElement, z));
        if (stackTraceElement.isNativeMethod()) {
            c1400e.m7077a(1, (long) Math.max(stackTraceElement.getLineNumber(), 0));
        } else {
            c1400e.m7077a(1, 0);
        }
        c1400e.m7078a(2, C1395b.m7039a(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
        if (stackTraceElement.getFileName() != null) {
            c1400e.m7078a(3, C1395b.m7039a(stackTraceElement.getFileName()));
        }
        if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
            c1400e.m7077a(4, (long) stackTraceElement.getLineNumber());
        }
        if (!z) {
            i2 = 0;
        }
        c1400e.m7076a(5, i2);
    }

    /* renamed from: a */
    public static void m7015a(C1400e c1400e, int i, String str, int i2, long j, long j2, boolean z, Map<C1121a, String> map, int i3, String str2, String str3) {
        C1395b a = ag.m7013a(str);
        C1395b a2 = ag.m7013a(str3);
        C1395b a3 = ag.m7013a(str2);
        c1400e.m7093g(9, 2);
        c1400e.m7095k(ag.m6999a(i, a, i2, j, j2, z, map, i3, a3, a2));
        c1400e.m7087b(3, i);
        c1400e.m7078a(4, a);
        c1400e.m7076a(5, i2);
        c1400e.m7077a(6, j);
        c1400e.m7077a(7, j2);
        c1400e.m7079a(10, z);
        for (Entry entry : map.entrySet()) {
            c1400e.m7093g(11, 2);
            c1400e.m7095k(ag.m7001a((C1121a) entry.getKey(), (String) entry.getValue()));
            c1400e.m7087b(1, ((C1121a) entry.getKey()).f3293h);
            c1400e.m7078a(2, C1395b.m7039a((String) entry.getValue()));
        }
        c1400e.m7076a(12, i3);
        if (a3 != null) {
            c1400e.m7078a(13, a3);
        }
        if (a2 != null) {
            c1400e.m7078a(14, a2);
        }
    }

    /* renamed from: a */
    public static void m7016a(C1400e c1400e, long j, String str, aj ajVar, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, Map<String, String> map, C1460u c1460u, RunningAppProcessInfo runningAppProcessInfo, int i, String str2, String str3, Float f, int i2, boolean z, long j2, long j3) {
        C1395b c1395b;
        C1395b a = C1395b.m7039a(str2);
        if (str3 == null) {
            c1395b = null;
        } else {
            c1395b = C1395b.m7039a(str3.replace("-", TtmlNode.ANONYMOUS_REGION_ID));
        }
        C1395b a2 = c1460u.m7268a();
        if (a2 == null) {
            C1230c.m6414h().mo1062a("CrashlyticsCore", "No log data to include with this event.");
        }
        c1460u.m7272b();
        c1400e.m7093g(10, 2);
        c1400e.m7095k(ag.m7000a(j, str, ajVar, thread, stackTraceElementArr, threadArr, (List) list, 8, (Map) map, runningAppProcessInfo, i, a, c1395b, f, i2, z, j2, j3, a2));
        c1400e.m7077a(1, j);
        c1400e.m7078a(2, C1395b.m7039a(str));
        ag.m7019a(c1400e, ajVar, thread, stackTraceElementArr, threadArr, list, 8, a, c1395b, map, runningAppProcessInfo, i);
        ag.m7021a(c1400e, f, i2, z, i, j2, j3);
        ag.m7020a(c1400e, a2);
    }

    /* renamed from: a */
    private static void m7017a(C1400e c1400e, aj ajVar, int i, int i2, int i3) {
        int i4 = 0;
        c1400e.m7093g(i3, 2);
        c1400e.m7095k(ag.m7002a(ajVar, 1, i2));
        c1400e.m7078a(1, C1395b.m7039a(ajVar.f4222b));
        String str = ajVar.f4221a;
        if (str != null) {
            c1400e.m7078a(3, C1395b.m7039a(str));
        }
        for (StackTraceElement a : ajVar.f4223c) {
            ag.m7014a(c1400e, 4, a, true);
        }
        aj ajVar2 = ajVar.f4224d;
        if (ajVar2 == null) {
            return;
        }
        if (i < i2) {
            ag.m7017a(c1400e, ajVar2, i + 1, i2, 6);
            return;
        }
        while (ajVar2 != null) {
            ajVar2 = ajVar2.f4224d;
            i4++;
        }
        c1400e.m7076a(7, i4);
    }

    /* renamed from: a */
    private static void m7018a(C1400e c1400e, aj ajVar, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, C1395b c1395b, C1395b c1395b2) {
        c1400e.m7093g(1, 2);
        c1400e.m7095k(ag.m7003a(ajVar, thread, stackTraceElementArr, threadArr, (List) list, i, c1395b, c1395b2));
        ag.m7026a(c1400e, thread, stackTraceElementArr, 4, true);
        int length = threadArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            ag.m7026a(c1400e, threadArr[i2], (StackTraceElement[]) list.get(i2), 0, false);
        }
        ag.m7017a(c1400e, ajVar, 1, i, 2);
        c1400e.m7093g(3, 2);
        c1400e.m7095k(ag.m6998a());
        c1400e.m7078a(1, f4216a);
        c1400e.m7078a(2, f4216a);
        c1400e.m7077a(3, 0);
        c1400e.m7093g(4, 2);
        c1400e.m7095k(ag.m7006a(c1395b, c1395b2));
        c1400e.m7077a(1, 0);
        c1400e.m7077a(2, 0);
        c1400e.m7078a(3, c1395b);
        if (c1395b2 != null) {
            c1400e.m7078a(4, c1395b2);
        }
    }

    /* renamed from: a */
    private static void m7019a(C1400e c1400e, aj ajVar, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, C1395b c1395b, C1395b c1395b2, Map<String, String> map, RunningAppProcessInfo runningAppProcessInfo, int i2) {
        c1400e.m7093g(3, 2);
        c1400e.m7095k(ag.m7004a(ajVar, thread, stackTraceElementArr, threadArr, (List) list, i, c1395b, c1395b2, (Map) map, runningAppProcessInfo, i2));
        ag.m7018a(c1400e, ajVar, thread, stackTraceElementArr, threadArr, list, i, c1395b, c1395b2);
        if (!(map == null || map.isEmpty())) {
            ag.m7027a(c1400e, (Map) map);
        }
        if (runningAppProcessInfo != null) {
            c1400e.m7079a(3, runningAppProcessInfo.importance != 100);
        }
        c1400e.m7076a(4, i2);
    }

    /* renamed from: a */
    private static void m7020a(C1400e c1400e, C1395b c1395b) {
        if (c1395b != null) {
            c1400e.m7093g(6, 2);
            c1400e.m7095k(ag.m7028b(c1395b));
            c1400e.m7078a(1, c1395b);
        }
    }

    /* renamed from: a */
    private static void m7021a(C1400e c1400e, Float f, int i, boolean z, int i2, long j, long j2) {
        c1400e.m7093g(5, 2);
        c1400e.m7095k(ag.m7009a(f, i, z, i2, j, j2));
        if (f != null) {
            c1400e.m7075a(1, f.floatValue());
        }
        c1400e.m7089c(2, i);
        c1400e.m7079a(3, z);
        c1400e.m7076a(4, i2);
        c1400e.m7077a(5, j);
        c1400e.m7077a(6, j2);
    }

    /* renamed from: a */
    public static void m7022a(C1400e c1400e, String str, String str2, long j) {
        c1400e.m7078a(1, C1395b.m7039a(str2));
        c1400e.m7078a(2, C1395b.m7039a(str));
        c1400e.m7077a(3, j);
    }

    /* renamed from: a */
    public static void m7023a(C1400e c1400e, String str, String str2, String str3) {
        if (str == null) {
            str = TtmlNode.ANONYMOUS_REGION_ID;
        }
        C1395b a = C1395b.m7039a(str);
        C1395b a2 = ag.m7013a(str2);
        C1395b a3 = ag.m7013a(str3);
        int b = 0 + C1400e.m7056b(1, a);
        if (str2 != null) {
            b += C1400e.m7056b(2, a2);
        }
        if (str3 != null) {
            b += C1400e.m7056b(3, a3);
        }
        c1400e.m7093g(6, 2);
        c1400e.m7095k(b);
        c1400e.m7078a(1, a);
        if (str2 != null) {
            c1400e.m7078a(2, a2);
        }
        if (str3 != null) {
            c1400e.m7078a(3, a3);
        }
    }

    /* renamed from: a */
    public static void m7024a(C1400e c1400e, String str, String str2, String str3, String str4, String str5, int i, String str6) {
        C1395b a = C1395b.m7039a(str);
        C1395b a2 = C1395b.m7039a(str2);
        C1395b a3 = C1395b.m7039a(str3);
        C1395b a4 = C1395b.m7039a(str4);
        C1395b a5 = C1395b.m7039a(str5);
        C1395b a6 = str6 != null ? C1395b.m7039a(str6) : null;
        c1400e.m7093g(7, 2);
        c1400e.m7095k(ag.m7007a(a, a2, a3, a4, a5, i, a6));
        c1400e.m7078a(1, a);
        c1400e.m7078a(2, a3);
        c1400e.m7078a(3, a4);
        c1400e.m7093g(5, 2);
        c1400e.m7095k(ag.m7005a(a2));
        c1400e.m7078a(1, a2);
        c1400e.m7078a(6, a5);
        if (a6 != null) {
            c1400e.m7078a(8, f4217b);
            c1400e.m7078a(9, a6);
        }
        c1400e.m7087b(10, i);
    }

    /* renamed from: a */
    public static void m7025a(C1400e c1400e, String str, String str2, boolean z) {
        C1395b a = C1395b.m7039a(str);
        C1395b a2 = C1395b.m7039a(str2);
        c1400e.m7093g(8, 2);
        c1400e.m7095k(ag.m7008a(a, a2, z));
        c1400e.m7087b(1, 3);
        c1400e.m7078a(2, a);
        c1400e.m7078a(3, a2);
        c1400e.m7079a(4, z);
    }

    /* renamed from: a */
    private static void m7026a(C1400e c1400e, Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) {
        c1400e.m7093g(1, 2);
        c1400e.m7095k(ag.m7012a(thread, stackTraceElementArr, i, z));
        c1400e.m7078a(1, C1395b.m7039a(thread.getName()));
        c1400e.m7076a(2, i);
        for (StackTraceElement a : stackTraceElementArr) {
            ag.m7014a(c1400e, 3, a, z);
        }
    }

    /* renamed from: a */
    private static void m7027a(C1400e c1400e, Map<String, String> map) {
        for (Entry entry : map.entrySet()) {
            c1400e.m7093g(2, 2);
            c1400e.m7095k(ag.m7011a((String) entry.getKey(), (String) entry.getValue()));
            c1400e.m7078a(1, C1395b.m7039a((String) entry.getKey()));
            String str = (String) entry.getValue();
            if (str == null) {
                str = TtmlNode.ANONYMOUS_REGION_ID;
            }
            c1400e.m7078a(2, C1395b.m7039a(str));
        }
    }

    /* renamed from: b */
    private static int m7028b(C1395b c1395b) {
        return C1400e.m7056b(1, c1395b);
    }
}
