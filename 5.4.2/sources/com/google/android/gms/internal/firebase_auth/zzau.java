package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzau {
    private final String zzba;
    private final String zzgu;

    public zzau(String str, String str2) {
        this.zzgu = Preconditions.checkNotEmpty(str);
        this.zzba = str2;
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzm = new zzm();
        zzm.zzag = this.zzgu;
        zzm.zzba = this.zzba;
        return zzm;
    }
}
