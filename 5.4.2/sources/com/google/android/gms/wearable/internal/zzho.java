package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder.Notifier;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;

final class zzho implements Notifier<CapabilityListener> {
    private final /* synthetic */ zzah zzfr;

    zzho(zzah zzah) {
        this.zzfr = zzah;
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        ((CapabilityListener) obj).onCapabilityChanged(this.zzfr);
    }

    public final void onNotifyListenerFailed() {
    }
}
