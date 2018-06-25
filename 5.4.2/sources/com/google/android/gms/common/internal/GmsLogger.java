package com.google.android.gms.common.internal;

import android.util.Log;

public final class GmsLogger {
    public static final int MAX_PII_TAG_LENGTH = 15;
    public static final int MAX_TAG_LENGTH = 23;
    private static final String zzub = null;
    private final String zzuc;
    private final String zzud;

    public GmsLogger(String str) {
        this(str, null);
    }

    public GmsLogger(String str, String str2) {
        Preconditions.checkNotNull(str, "log tag cannot be null");
        Preconditions.checkArgument(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, Integer.valueOf(23));
        this.zzuc = str;
        if (str2 == null || str2.length() <= 0) {
            this.zzud = null;
        } else {
            this.zzud = str2;
        }
    }

    public static boolean isBuildPiiEnabled() {
        return false;
    }

    private final String zza(String str, Object... objArr) {
        String format = String.format(str, objArr);
        return this.zzud == null ? format : this.zzud.concat(format);
    }

    private final String zzl(String str) {
        return this.zzud == null ? str : this.zzud.concat(str);
    }

    public final boolean canLog(int i) {
        return Log.isLoggable(this.zzuc, i);
    }

    public final boolean canLogPii() {
        return false;
    }

    /* renamed from: d */
    public final void m8444d(String str, String str2) {
        if (canLog(3)) {
            Log.d(str, zzl(str2));
        }
    }

    /* renamed from: d */
    public final void m8445d(String str, String str2, Throwable th) {
        if (canLog(3)) {
            Log.d(str, zzl(str2), th);
        }
    }

    public final void dfmt(String str, String str2, Object... objArr) {
        if (canLog(3)) {
            Log.d(str, zza(str2, objArr));
        }
    }

    /* renamed from: e */
    public final void m8446e(String str, String str2) {
        if (canLog(6)) {
            Log.e(str, zzl(str2));
        }
    }

    /* renamed from: e */
    public final void m8447e(String str, String str2, Throwable th) {
        if (canLog(6)) {
            Log.e(str, zzl(str2), th);
        }
    }

    public final void efmt(String str, String str2, Object... objArr) {
        if (canLog(6)) {
            Log.e(str, zza(str2, objArr));
        }
    }

    public final String getTag() {
        return this.zzuc;
    }

    /* renamed from: i */
    public final void m8448i(String str, String str2) {
        if (canLog(4)) {
            Log.i(str, zzl(str2));
        }
    }

    /* renamed from: i */
    public final void m8449i(String str, String str2, Throwable th) {
        if (canLog(4)) {
            Log.i(str, zzl(str2), th);
        }
    }

    public final void ifmt(String str, String str2, Object... objArr) {
        if (canLog(4)) {
            Log.i(str, zza(str2, objArr));
        }
    }

    public final void pii(String str, String str2) {
        if (canLogPii()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf(" PII_LOG");
            Log.i(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), zzl(str2));
        }
    }

    public final void pii(String str, String str2, Throwable th) {
        if (canLogPii()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf(" PII_LOG");
            Log.i(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), zzl(str2), th);
        }
    }

    public final void piifmt(String str, String str2, Object... objArr) {
        if (canLogPii()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf(" PII_LOG");
            Log.i(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), zza(str2, objArr));
        }
    }

    /* renamed from: v */
    public final void m8450v(String str, String str2) {
        if (canLog(2)) {
            Log.v(str, zzl(str2));
        }
    }

    /* renamed from: v */
    public final void m8451v(String str, String str2, Throwable th) {
        if (canLog(2)) {
            Log.v(str, zzl(str2), th);
        }
    }

    public final void vfmt(String str, String str2, Object... objArr) {
        if (canLog(2)) {
            Log.v(str, zza(str2, objArr));
        }
    }

    /* renamed from: w */
    public final void m8452w(String str, String str2) {
        if (canLog(5)) {
            Log.w(str, zzl(str2));
        }
    }

    /* renamed from: w */
    public final void m8453w(String str, String str2, Throwable th) {
        if (canLog(5)) {
            Log.w(str, zzl(str2), th);
        }
    }

    public final void wfmt(String str, String str2, Object... objArr) {
        if (canLog(5)) {
            Log.w(this.zzuc, zza(str2, objArr));
        }
    }

    public final GmsLogger withMessagePrefix(String str) {
        return new GmsLogger(this.zzuc, str);
    }

    public final void wtf(String str, String str2, Throwable th) {
        if (canLog(7)) {
            Log.e(str, zzl(str2), th);
            Log.wtf(str, zzl(str2), th);
        }
    }
}
