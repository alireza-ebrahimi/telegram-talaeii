package com.crashlytics.android.p066c;

/* renamed from: com.crashlytics.android.c.aj */
class aj {
    /* renamed from: a */
    public final String f4221a;
    /* renamed from: b */
    public final String f4222b;
    /* renamed from: c */
    public final StackTraceElement[] f4223c;
    /* renamed from: d */
    public final aj f4224d;

    public aj(Throwable th, ai aiVar) {
        this.f4221a = th.getLocalizedMessage();
        this.f4222b = th.getClass().getName();
        this.f4223c = aiVar.mo1152a(th.getStackTrace());
        Throwable cause = th.getCause();
        this.f4224d = cause != null ? new aj(cause, aiVar) : null;
    }
}
