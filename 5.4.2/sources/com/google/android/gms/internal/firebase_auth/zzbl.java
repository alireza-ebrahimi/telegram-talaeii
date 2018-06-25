package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzbl {
    private String zzah;
    private String zzbi;
    private boolean zzbt = true;

    public zzbl(String str, String str2) {
        this.zzah = Preconditions.checkNotEmpty(str);
        this.zzbi = Preconditions.checkNotEmpty(str2);
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzs = new zzs();
        zzs.zzah = this.zzah;
        zzs.zzbi = this.zzbi;
        zzs.zzbt = this.zzbt;
        return zzs;
    }
}
