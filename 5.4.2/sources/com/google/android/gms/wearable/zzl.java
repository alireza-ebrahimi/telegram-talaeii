package com.google.android.gms.wearable;

import com.google.android.gms.common.data.DataHolder;

final class zzl implements Runnable {
    private final /* synthetic */ DataHolder zzan;
    private final /* synthetic */ zzd zzao;

    zzl(zzd zzd, DataHolder dataHolder) {
        this.zzao = zzd;
        this.zzan = dataHolder;
    }

    public final void run() {
        DataEventBuffer dataEventBuffer = new DataEventBuffer(this.zzan);
        try {
            this.zzao.zzak.onDataChanged(dataEventBuffer);
        } finally {
            dataEventBuffer.release();
        }
    }
}
