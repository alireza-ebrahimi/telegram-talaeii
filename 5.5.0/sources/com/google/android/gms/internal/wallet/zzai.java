package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;

final class zzai extends zzaf {
    private final ResultHolder<BooleanResult> zzgk;

    public zzai(ResultHolder<BooleanResult> resultHolder) {
        this.zzgk = resultHolder;
    }

    public final void zza(Status status, boolean z, Bundle bundle) {
        this.zzgk.setResult(new BooleanResult(status, z));
    }
}
