package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder.Notifier;
import com.google.android.gms.wearable.MessageApi.MessageListener;

final class zzhm implements Notifier<MessageListener> {
    private final /* synthetic */ zzfe zzap;

    zzhm(zzfe zzfe) {
        this.zzap = zzfe;
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        ((MessageListener) obj).onMessageReceived(this.zzap);
    }

    public final void onNotifyListenerFailed() {
    }
}
