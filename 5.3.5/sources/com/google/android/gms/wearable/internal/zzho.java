package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;

final class zzho implements zzcl<CapabilityListener> {
    private /* synthetic */ zzah zzlwg;

    zzho(zzah zzah) {
        this.zzlwg = zzah;
    }

    public final void zzajh() {
    }

    public final /* synthetic */ void zzu(Object obj) {
        ((CapabilityListener) obj).onCapabilityChanged(this.zzlwg);
    }
}
