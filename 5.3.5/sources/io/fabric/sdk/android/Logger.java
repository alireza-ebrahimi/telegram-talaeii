package io.fabric.sdk.android;

public interface Logger {
    /* renamed from: d */
    void mo4381d(String str, String str2);

    /* renamed from: d */
    void mo4382d(String str, String str2, Throwable th);

    /* renamed from: e */
    void mo4383e(String str, String str2);

    /* renamed from: e */
    void mo4384e(String str, String str2, Throwable th);

    int getLogLevel();

    /* renamed from: i */
    void mo4386i(String str, String str2);

    /* renamed from: i */
    void mo4387i(String str, String str2, Throwable th);

    boolean isLoggable(String str, int i);

    void log(int i, String str, String str2);

    void log(int i, String str, String str2, boolean z);

    void setLogLevel(int i);

    /* renamed from: v */
    void mo4392v(String str, String str2);

    /* renamed from: v */
    void mo4393v(String str, String str2, Throwable th);

    /* renamed from: w */
    void mo4394w(String str, String str2);

    /* renamed from: w */
    void mo4395w(String str, String str2, Throwable th);
}
