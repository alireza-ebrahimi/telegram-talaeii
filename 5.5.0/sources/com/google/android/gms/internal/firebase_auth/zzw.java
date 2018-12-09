package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzw {
    private String zzg;
    private String zzh = "http://localhost";

    public zzw(String str) {
        this.zzg = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzh = new zzh();
        zzh.zzg = this.zzg;
        zzh.zzh = this.zzh;
        return zzh;
    }
}
