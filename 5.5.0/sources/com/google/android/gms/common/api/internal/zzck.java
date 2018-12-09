package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzck {
    public static final Status zzmm = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zzmn = new BasePendingResult[0];
    private final Map<AnyClientKey<?>, Client> zzil;
    @VisibleForTesting
    final Set<BasePendingResult<?>> zzmo = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzcn zzmp = new zzcl(this);

    public zzck(Map<AnyClientKey<?>, Client> map) {
        this.zzil = map;
    }

    public final void release() {
        zzcn zzcn = null;
        for (PendingResult pendingResult : (BasePendingResult[]) this.zzmo.toArray(zzmn)) {
            pendingResult.zza(zzcn);
            if (pendingResult.zzo() != null) {
                pendingResult.setResultCallback(zzcn);
                IBinder serviceBrokerBinder = ((Client) this.zzil.get(((ApiMethodImpl) pendingResult).getClientKey())).getServiceBrokerBinder();
                if (pendingResult.isReady()) {
                    pendingResult.zza(new zzcm(pendingResult, zzcn, serviceBrokerBinder, zzcn));
                } else if (serviceBrokerBinder == null || !serviceBrokerBinder.isBinderAlive()) {
                    pendingResult.zza(zzcn);
                    pendingResult.cancel();
                    zzcn.remove(pendingResult.zzo().intValue());
                } else {
                    zzcn zzcm = new zzcm(pendingResult, zzcn, serviceBrokerBinder, zzcn);
                    pendingResult.zza(zzcm);
                    try {
                        serviceBrokerBinder.linkToDeath(zzcm, 0);
                    } catch (RemoteException e) {
                        pendingResult.cancel();
                        zzcn.remove(pendingResult.zzo().intValue());
                    }
                }
                this.zzmo.remove(pendingResult);
            } else if (pendingResult.zzw()) {
                this.zzmo.remove(pendingResult);
            }
        }
    }

    final void zzb(BasePendingResult<? extends Result> basePendingResult) {
        this.zzmo.add(basePendingResult);
        basePendingResult.zza(this.zzmp);
    }

    public final void zzce() {
        for (BasePendingResult zzb : (BasePendingResult[]) this.zzmo.toArray(zzmn)) {
            zzb.zzb(zzmm);
        }
    }
}
