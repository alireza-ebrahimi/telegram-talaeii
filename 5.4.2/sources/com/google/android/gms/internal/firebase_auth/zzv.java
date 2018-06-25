package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public class zzv extends RuntimeException {
    private zzad zzfi;

    public zzv(zzad zzad) {
        this.zzfi = (zzad) Preconditions.checkNotNull(zzad);
    }

    public zzv(Throwable th) {
        super(th);
    }

    public final String getErrorMessage() {
        zzac zzar = this.zzfi != null ? this.zzfi.zzar() : null;
        return zzar != null ? zzar.getErrorMessage() : getMessage();
    }
}
