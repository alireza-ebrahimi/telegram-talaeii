package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.wearable.MessageApi.MessageListener;

final class zzhm implements zzcl<MessageListener> {
    private /* synthetic */ zzfe zzlrs;

    zzhm(zzfe zzfe) {
        this.zzlrs = zzfe;
    }

    public final void zzajh() {
    }

    public final /* synthetic */ void zzu(Object obj) {
        ((MessageListener) obj).onMessageReceived(this.zzlrs);
    }
}
