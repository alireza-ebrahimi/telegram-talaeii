package com.persianswitch.sdk.base.log;

public final class SDKLog {
    /* renamed from: a */
    private static ILogger f7070a = new MemoryLogCatLogger();
    /* renamed from: b */
    private static int f7071b = 6;

    /* renamed from: a */
    private static void m10655a(int i, String str, String str2, Throwable th, Object... objArr) {
        if (m10658a(i)) {
            try {
                f7070a.mo3249a(i, str, str2, th, objArr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    public static void m10656a(String str, String str2, Throwable th, Object... objArr) {
        m10655a(3, str, str2, th, objArr);
    }

    /* renamed from: a */
    public static void m10657a(String str, String str2, Object... objArr) {
        m10655a(4, str, str2, null, objArr);
    }

    /* renamed from: a */
    private static boolean m10658a(int i) {
        return i <= f7071b;
    }

    /* renamed from: b */
    public static void m10659b(String str, String str2, Throwable th, Object... objArr) {
        m10655a(6, str, str2, th, objArr);
    }

    /* renamed from: b */
    public static void m10660b(String str, String str2, Object... objArr) {
        m10655a(3, str, str2, null, objArr);
    }

    /* renamed from: c */
    public static void m10661c(String str, String str2, Object... objArr) {
        m10655a(6, str, str2, null, objArr);
    }
}
