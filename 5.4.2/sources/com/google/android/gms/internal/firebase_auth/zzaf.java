package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzaf {
    private String zzjm;
    private String zzjn;
    private final String zzjo;

    public zzaf(String str) {
        this(str, null);
    }

    private zzaf(String str, String str2) {
        this.zzjm = zzae.REFRESH_TOKEN.toString();
        this.zzjn = Preconditions.checkNotEmpty(str);
        this.zzjo = null;
    }

    public final /* synthetic */ zzgt zzao() {
        zzgt zzgz = new zzgz();
        zzgz.zzjm = this.zzjm;
        zzgz.zzai = this.zzjn;
        zzgz.zzjo = null;
        return zzgz;
    }
}
