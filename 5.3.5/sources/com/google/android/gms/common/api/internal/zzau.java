package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api.zze;
import java.util.ArrayList;

final class zzau extends zzay {
    private /* synthetic */ zzao zzfxt;
    private final ArrayList<zze> zzfxz;

    public zzau(zzao zzao, ArrayList<zze> arrayList) {
        this.zzfxt = zzao;
        super(zzao);
        this.zzfxz = arrayList;
    }

    @WorkerThread
    public final void zzajj() {
        this.zzfxt.zzfxd.zzfvq.zzfyk = this.zzfxt.zzajp();
        ArrayList arrayList = this.zzfxz;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((zze) obj).zza(this.zzfxt.zzfxp, this.zzfxt.zzfxd.zzfvq.zzfyk);
        }
    }
}
