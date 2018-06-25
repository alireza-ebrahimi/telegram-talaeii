package com.google.android.gms.common.internal;

import android.util.Log;

public final class zzal {
    private static int zzghg = 15;
    private static final String zzghh = null;
    private final String zzghi;
    private final String zzghj;

    public zzal(String str) {
        this(str, null);
    }

    public zzal(String str, String str2) {
        zzbq.checkNotNull(str, "log tag cannot be null");
        zzbq.zzb(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, Integer.valueOf(23));
        this.zzghi = str;
        if (str2 == null || str2.length() <= 0) {
            this.zzghj = null;
        } else {
            this.zzghj = str2;
        }
    }

    private final boolean zzcg(int i) {
        return Log.isLoggable(this.zzghi, i);
    }

    private final String zzgu(String str) {
        return this.zzghj == null ? str : this.zzghj.concat(str);
    }

    private final String zzh(String str, Object... objArr) {
        String format = String.format(str, objArr);
        return this.zzghj == null ? format : this.zzghj.concat(format);
    }

    public final void zzb(String str, String str2, Throwable th) {
        if (zzcg(4)) {
            Log.i(str, zzgu(str2), th);
        }
    }

    public final void zzb(String str, String str2, Object... objArr) {
        if (zzcg(3)) {
            Log.d(str, zzh(str2, objArr));
        }
    }

    public final void zzc(String str, String str2, Throwable th) {
        if (zzcg(5)) {
            Log.w(str, zzgu(str2), th);
        }
    }

    public final void zzc(String str, String str2, Object... objArr) {
        if (zzcg(5)) {
            Log.w(this.zzghi, zzh(str2, objArr));
        }
    }

    public final void zzd(String str, String str2, Throwable th) {
        if (zzcg(6)) {
            Log.e(str, zzgu(str2), th);
        }
    }

    public final void zzd(String str, String str2, Object... objArr) {
        if (zzcg(6)) {
            Log.e(str, zzh(str2, objArr));
        }
    }

    public final void zzu(String str, String str2) {
        if (zzcg(3)) {
            Log.d(str, zzgu(str2));
        }
    }

    public final void zzv(String str, String str2) {
        if (zzcg(5)) {
            Log.w(str, zzgu(str2));
        }
    }

    public final void zzw(String str, String str2) {
        if (zzcg(6)) {
            Log.e(str, zzgu(str2));
        }
    }
}
