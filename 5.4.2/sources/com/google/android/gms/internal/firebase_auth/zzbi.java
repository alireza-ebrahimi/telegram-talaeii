package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzbi {
    private boolean zzbt = true;
    private String zzdh;

    public zzbi(String str) {
        this.zzdh = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzr = new zzr();
        zzr.zzdh = this.zzdh;
        zzr.zzbt = this.zzbt;
        return zzr;
    }
}
