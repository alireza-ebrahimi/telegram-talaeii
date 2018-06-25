package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzg.zzf;
import com.google.firebase.auth.p104a.p105a.C1794n;

public final class zzbc implements C1794n<zzbc, zzf> {
    private String zzaf;
    private String zzah;
    private String zzai;
    private long zzaj;
    private String zzbh;

    public final String getIdToken() {
        return this.zzaf;
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zzf zzf = (zzf) zzgt;
        this.zzaf = Strings.emptyToNull(zzf.zzaf);
        this.zzbh = Strings.emptyToNull(zzf.zzbh);
        this.zzah = Strings.emptyToNull(zzf.zzah);
        this.zzai = Strings.emptyToNull(zzf.zzai);
        this.zzaj = zzf.zzaj;
        return this;
    }

    public final Class<zzf> zzag() {
        return zzf.class;
    }

    public final String zzap() {
        return this.zzai;
    }

    public final long zzaq() {
        return this.zzaj;
    }
}
