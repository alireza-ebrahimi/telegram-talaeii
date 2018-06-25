package com.google.android.gms.internal.firebase_auth;

import android.text.TextUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzg.zzg;
import com.google.firebase.auth.p104a.p105a.C1794n;
import com.google.firebase.auth.zzd;

public final class zzbh implements C1794n<zzbh, zzg> {
    private String zzad;
    private String zzaf;
    private String zzah;
    private String zzai;
    private long zzaj;
    private boolean zzak;
    private String zzbh;
    private String zzbr;
    private String zzdf;
    private String zzj;
    private boolean zzkl;
    private boolean zzkm;
    private String zzkn;
    private String zzko;

    public final String getIdToken() {
        return this.zzaf;
    }

    public final String getProviderId() {
        return this.zzj;
    }

    public final String getRawUserInfo() {
        return this.zzdf;
    }

    public final boolean isNewUser() {
        return this.zzak;
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zzg zzg = (zzg) zzgt;
        this.zzkl = zzg.zzcu;
        this.zzkm = zzg.zzdb;
        this.zzaf = Strings.emptyToNull(zzg.zzaf);
        this.zzai = Strings.emptyToNull(zzg.zzai);
        this.zzaj = zzg.zzaj;
        this.zzad = Strings.emptyToNull(zzg.zzad);
        this.zzah = Strings.emptyToNull(zzg.zzah);
        this.zzbh = Strings.emptyToNull(zzg.zzbh);
        this.zzbr = Strings.emptyToNull(zzg.zzbr);
        this.zzj = Strings.emptyToNull(zzg.zzj);
        this.zzdf = Strings.emptyToNull(zzg.zzdf);
        this.zzak = zzg.zzak;
        this.zzkn = zzg.zzcx;
        this.zzko = zzg.zzdd;
        return this;
    }

    public final Class<zzg> zzag() {
        return zzg.class;
    }

    public final String zzap() {
        return this.zzai;
    }

    public final long zzaq() {
        return this.zzaj;
    }

    public final zzd zzav() {
        return (TextUtils.isEmpty(this.zzkn) && TextUtils.isEmpty(this.zzko)) ? null : zzd.m8670a(this.zzj, this.zzko, this.zzkn);
    }

    public final boolean zzbe() {
        return this.zzkl;
    }
}
