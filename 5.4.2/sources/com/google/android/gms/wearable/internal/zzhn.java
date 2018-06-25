package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder.Notifier;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;

final class zzhn implements Notifier<ChannelListener> {
    private final /* synthetic */ zzaw zzav;

    zzhn(zzaw zzaw) {
        this.zzav = zzaw;
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        this.zzav.zza((ChannelListener) obj);
    }

    public final void onNotifyListenerFailed() {
    }
}
