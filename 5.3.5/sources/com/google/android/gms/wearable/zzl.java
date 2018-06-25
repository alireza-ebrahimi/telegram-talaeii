package com.google.android.gms.wearable;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;

final class zzl implements Runnable {
    private /* synthetic */ DataHolder zzlrq;
    private /* synthetic */ zzd zzlrr;

    zzl(zzd zzd, DataHolder dataHolder) {
        this.zzlrr = zzd;
        this.zzlrq = dataHolder;
    }

    public final void run() {
        AbstractDataBuffer dataEventBuffer = new DataEventBuffer(this.zzlrq);
        try {
            this.zzlrr.zzlrn.onDataChanged(dataEventBuffer);
        } finally {
            dataEventBuffer.release();
        }
    }
}
