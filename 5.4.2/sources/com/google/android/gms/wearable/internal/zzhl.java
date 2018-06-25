package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.ListenerHolder.Notifier;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataEventBuffer;

final class zzhl implements Notifier<DataListener> {
    private final /* synthetic */ DataHolder zzan;

    zzhl(DataHolder dataHolder) {
        this.zzan = dataHolder;
    }

    public final /* synthetic */ void notifyListener(Object obj) {
        try {
            ((DataListener) obj).onDataChanged(new DataEventBuffer(this.zzan));
        } finally {
            this.zzan.close();
        }
    }

    public final void onNotifyListenerFailed() {
        this.zzan.close();
    }
}
