package com.google.android.gms.internal.firebase_auth;

final class zzbz {
    private final byte[] buffer;
    private final zzci zzmo;

    private zzbz(int i) {
        this.buffer = new byte[i];
        this.zzmo = zzci.zzb(this.buffer);
    }

    public final zzbu zzca() {
        if (this.zzmo.zzdc() == 0) {
            return new zzcb(this.buffer);
        }
        throw new IllegalStateException("Did not write as much data as expected.");
    }

    public final zzci zzcb() {
        return this.zzmo;
    }
}
