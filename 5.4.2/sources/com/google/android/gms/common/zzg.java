package com.google.android.gms.common;

import android.util.Log;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
class zzg {
    private static final zzg zzbk = new zzg(true, null, null);
    private final Throwable cause;
    final boolean zzbl;
    private final String zzbm;

    zzg(boolean z, @Nullable String str, @Nullable Throwable th) {
        this.zzbl = z;
        this.zzbm = str;
        this.cause = th;
    }

    static zzg zza(String str, CertData certData, boolean z, boolean z2) {
        return new zzi(str, certData, z, z2, null);
    }

    static zzg zza(String str, Throwable th) {
        return new zzg(false, str, th);
    }

    static zzg zze(String str) {
        return new zzg(false, str, null);
    }

    static zzg zzg() {
        return zzbk;
    }

    @Nullable
    String getErrorMessage() {
        return this.zzbm;
    }

    final void zzh() {
        if (!this.zzbl) {
            String valueOf = String.valueOf("GoogleCertificatesRslt: ");
            String valueOf2 = String.valueOf(getErrorMessage());
            valueOf2 = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            if (this.cause != null) {
                throw new SecurityException(valueOf2, this.cause);
            }
            throw new SecurityException(valueOf2);
        }
    }

    final void zzi() {
        if (!this.zzbl) {
            if (this.cause != null) {
                Log.d("GoogleCertificatesRslt", getErrorMessage(), this.cause);
            } else {
                Log.d("GoogleCertificatesRslt", getErrorMessage());
            }
        }
    }
}
