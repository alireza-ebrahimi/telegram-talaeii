package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzg.zzb;
import com.google.firebase.auth.p104a.p105a.C1794n;

public final class zzab implements C1794n<zzab, zzb> {
    private String zzad;
    private String zzaf;
    private String zzah;
    private String zzai;
    private long zzaj;
    private boolean zzak;

    public final String getIdToken() {
        return this.zzaf;
    }

    public final boolean isNewUser() {
        return this.zzak;
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zzb zzb = (zzb) zzgt;
        this.zzad = Strings.emptyToNull(zzb.zzad);
        this.zzah = Strings.emptyToNull(zzb.zzah);
        this.zzaf = Strings.emptyToNull(zzb.zzaf);
        this.zzai = Strings.emptyToNull(zzb.zzai);
        this.zzak = zzb.zzak;
        this.zzaj = zzb.zzaj;
        return this;
    }

    public final Class<zzb> zzag() {
        return zzb.class;
    }

    public final String zzap() {
        return this.zzai;
    }

    public final long zzaq() {
        return this.zzaj;
    }
}
