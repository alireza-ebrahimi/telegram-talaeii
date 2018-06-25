package com.google.android.gms.wearable;

import java.util.List;

final class zzp implements Runnable {
    private final /* synthetic */ zzd zzao;
    private final /* synthetic */ List zzar;

    zzp(zzd zzd, List list) {
        this.zzao = zzd;
        this.zzar = list;
    }

    public final void run() {
        this.zzao.zzak.onConnectedNodes(this.zzar);
    }
}
