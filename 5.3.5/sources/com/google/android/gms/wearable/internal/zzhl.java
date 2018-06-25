package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.zzcl;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataEventBuffer;

final class zzhl implements zzcl<DataListener> {
    private /* synthetic */ DataHolder zzlrq;

    zzhl(DataHolder dataHolder) {
        this.zzlrq = dataHolder;
    }

    public final void zzajh() {
        this.zzlrq.close();
    }

    public final /* synthetic */ void zzu(Object obj) {
        try {
            ((DataListener) obj).onDataChanged(new DataEventBuffer(this.zzlrq));
        } finally {
            this.zzlrq.close();
        }
    }
}
