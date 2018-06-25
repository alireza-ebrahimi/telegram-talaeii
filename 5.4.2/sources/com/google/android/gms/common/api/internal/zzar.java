package com.google.android.gms.common.api.internal;

import com.google.android.gms.signin.internal.SignInResponse;

final class zzar extends zzbe {
    private final /* synthetic */ zzaj zzic;
    private final /* synthetic */ SignInResponse zzid;

    zzar(zzaq zzaq, zzbc zzbc, zzaj zzaj, SignInResponse signInResponse) {
        this.zzic = zzaj;
        this.zzid = signInResponse;
        super(zzbc);
    }

    public final void zzaq() {
        this.zzic.zza(this.zzid);
    }
}
