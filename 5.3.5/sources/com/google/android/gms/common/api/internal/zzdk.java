package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzdk {
    public static final Status zzgbq = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zzgbr = new BasePendingResult[0];
    private final Map<zzc<?>, zze> zzfyj;
    final Set<BasePendingResult<?>> zzgbs = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzdn zzgbt = new zzdl(this);

    public zzdk(Map<zzc<?>, zze> map) {
        this.zzfyj = map;
    }

    public final void release() {
        zzdn zzdn = null;
        for (PendingResult pendingResult : (BasePendingResult[]) this.zzgbs.toArray(zzgbr)) {
            pendingResult.zza(zzdn);
            if (pendingResult.zzaid() != null) {
                pendingResult.setResultCallback(zzdn);
                IBinder zzaho = ((zze) this.zzfyj.get(((zzm) pendingResult).zzahm())).zzaho();
                if (pendingResult.isReady()) {
                    pendingResult.zza(new zzdm(pendingResult, zzdn, zzaho, zzdn));
                } else if (zzaho == null || !zzaho.isBinderAlive()) {
                    pendingResult.zza(zzdn);
                    pendingResult.cancel();
                    zzdn.remove(pendingResult.zzaid().intValue());
                } else {
                    zzdn zzdm = new zzdm(pendingResult, zzdn, zzaho, zzdn);
                    pendingResult.zza(zzdm);
                    try {
                        zzaho.linkToDeath(zzdm, 0);
                    } catch (RemoteException e) {
                        pendingResult.cancel();
                        zzdn.remove(pendingResult.zzaid().intValue());
                    }
                }
                this.zzgbs.remove(pendingResult);
            } else if (pendingResult.zzaip()) {
                this.zzgbs.remove(pendingResult);
            }
        }
    }

    public final void zzald() {
        for (BasePendingResult zzv : (BasePendingResult[]) this.zzgbs.toArray(zzgbr)) {
            zzv.zzv(zzgbq);
        }
    }

    final void zzb(BasePendingResult<? extends Result> basePendingResult) {
        this.zzgbs.add(basePendingResult);
        basePendingResult.zza(this.zzgbt);
    }
}
