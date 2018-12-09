package com.p077f.p078a.p095c;

import android.util.Log;
import com.p077f.p078a.p086b.C1575d;

/* renamed from: com.f.a.c.c */
public final class C1602c {
    /* renamed from: a */
    private static volatile boolean f4910a = false;
    /* renamed from: b */
    private static volatile boolean f4911b = true;

    @Deprecated
    /* renamed from: a */
    public static void m7934a() {
        C1602c.m7940b(false);
    }

    /* renamed from: a */
    private static void m7935a(int i, Throwable th, String str, Object... objArr) {
        if (f4911b) {
            String format = objArr.length > 0 ? String.format(str, objArr) : str;
            if (th != null) {
                if (format == null) {
                    format = th.getMessage();
                }
                String stackTraceString = Log.getStackTraceString(th);
                format = String.format("%1$s\n%2$s", new Object[]{format, stackTraceString});
            }
            Log.println(i, C1575d.f4801a, format);
        }
    }

    /* renamed from: a */
    public static void m7936a(String str, Object... objArr) {
        if (f4910a) {
            C1602c.m7935a(3, null, str, objArr);
        }
    }

    /* renamed from: a */
    public static void m7937a(Throwable th) {
        C1602c.m7935a(6, th, null, new Object[0]);
    }

    /* renamed from: a */
    public static void m7938a(boolean z) {
        f4910a = z;
    }

    /* renamed from: b */
    public static void m7939b(String str, Object... objArr) {
        C1602c.m7935a(4, null, str, objArr);
    }

    /* renamed from: b */
    public static void m7940b(boolean z) {
        f4911b = z;
    }

    /* renamed from: c */
    public static void m7941c(String str, Object... objArr) {
        C1602c.m7935a(5, null, str, objArr);
    }

    /* renamed from: d */
    public static void m7942d(String str, Object... objArr) {
        C1602c.m7935a(6, null, str, objArr);
    }
}
