package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzg.zzi;
import com.google.firebase.auth.p104a.p105a.C1794n;

public final class zzbm implements C1794n<zzbm, zzi> {
    private String zzad;
    private String zzaf;
    private String zzah;
    private String zzai;
    private long zzaj;
    private String zzbh;
    private String zzbr;

    public final String getIdToken() {
        return this.zzaf;
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zzi zzi = (zzi) zzgt;
        this.zzad = Strings.emptyToNull(zzi.zzad);
        this.zzah = Strings.emptyToNull(zzi.zzah);
        this.zzbh = Strings.emptyToNull(zzi.zzbh);
        this.zzaf = Strings.emptyToNull(zzi.zzaf);
        this.zzbr = Strings.emptyToNull(zzi.zzbr);
        this.zzai = Strings.emptyToNull(zzi.zzai);
        this.zzaj = zzi.zzaj;
        return this;
    }

    public final Class<zzi> zzag() {
        return zzi.class;
    }

    public final String zzap() {
        return this.zzai;
    }

    public final long zzaq() {
        return this.zzaj;
    }
}
