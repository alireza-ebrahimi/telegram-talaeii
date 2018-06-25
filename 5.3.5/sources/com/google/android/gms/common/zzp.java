package com.google.android.gms.common;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Hide;

@Hide
class zzp {
    private static final zzp zzfrl = new zzp(true, null, null);
    final Throwable cause;
    final boolean zzfrm;
    private String zzfrn;

    zzp(boolean z, String str, Throwable th) {
        this.zzfrm = z;
        this.zzfrn = str;
        this.cause = th;
    }

    static zzp zza(String str, zzh zzh, boolean z, boolean z2) {
        return new zzr(str, zzh, z, z2, null);
    }

    static zzp zzahj() {
        return zzfrl;
    }

    static zzp zzd(@NonNull String str, @NonNull Throwable th) {
        return new zzp(false, str, th);
    }

    static zzp zzgg(@NonNull String str) {
        return new zzp(false, str, null);
    }

    String getErrorMessage() {
        return this.zzfrn;
    }
}
