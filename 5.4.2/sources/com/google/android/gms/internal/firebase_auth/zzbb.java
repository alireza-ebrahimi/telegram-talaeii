package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzbb {
    private String zzah;
    private String zzbh;
    private String zzbi;
    private boolean zzbt;

    public zzbb() {
        this.zzbt = true;
    }

    public zzbb(String str, String str2, String str3) {
        this.zzah = Preconditions.checkNotEmpty(str);
        this.zzbi = Preconditions.checkNotEmpty(str2);
        this.zzbh = null;
        this.zzbt = true;
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzp = new zzp();
        zzp.zzah = this.zzah;
        zzp.zzbi = this.zzbi;
        zzp.zzbh = null;
        return zzp;
    }
}
