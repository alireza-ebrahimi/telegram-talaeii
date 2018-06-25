package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzag {
    private String zzaf;

    public zzag(String str) {
        this.zzaf = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzk = new zzk();
        zzk.zzaf = this.zzaf;
        return zzk;
    }
}
