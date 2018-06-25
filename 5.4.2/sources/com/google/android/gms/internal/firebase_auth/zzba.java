package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzg.zze;
import com.google.firebase.auth.p104a.p105a.C1794n;
import java.util.List;

public final class zzba implements C1794n<zzba, zze> {
    private String zzaf;
    private String zzah;
    private String zzai;
    private long zzaj;
    private String zzbh;
    private String zzbi;
    private String zzbr;
    private zzas zzjr;
    private Boolean zzkg;

    public final String getEmail() {
        return this.zzah;
    }

    public final String getIdToken() {
        return this.zzaf;
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zze zze = (zze) zzgt;
        this.zzah = Strings.emptyToNull(zze.zzah);
        this.zzbi = Strings.emptyToNull(zze.zzby);
        this.zzkg = Boolean.valueOf(zze.zzbk);
        this.zzbh = Strings.emptyToNull(zze.zzbh);
        this.zzbr = Strings.emptyToNull(zze.zzbr);
        this.zzjr = zzas.zza(zze.zzbx);
        this.zzaf = Strings.emptyToNull(zze.zzaf);
        this.zzai = Strings.emptyToNull(zze.zzai);
        this.zzaj = zze.zzaj;
        return this;
    }

    public final Class<zze> zzag() {
        return zze.class;
    }

    public final String zzap() {
        return this.zzai;
    }

    public final long zzaq() {
        return this.zzaj;
    }

    public final List<zzaq> zzat() {
        return this.zzjr != null ? this.zzjr.zzat() : null;
    }
}
