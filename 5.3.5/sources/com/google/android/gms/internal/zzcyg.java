package com.google.android.gms.internal;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;

public final class zzcyg {
    @Hide
    public static final Api<zzcyk> API = new Api("SignIn.API", zzegv, zzegu);
    @Hide
    private static zzf<zzcyt> zzegu = new zzf();
    @Hide
    public static final zza<zzcyt, zzcyk> zzegv = new zzcyh();
    private static Scope zzemx = new Scope(Scopes.PROFILE);
    private static Scope zzemy = new Scope("email");
    @Hide
    private static Api<Object> zzgpn = new Api("SignIn.INTERNAL_API", zzklo, zzkln);
    @Hide
    private static zzf<zzcyt> zzkln = new zzf();
    @Hide
    private static zza<zzcyt, Object> zzklo = new zzcyi();
}
