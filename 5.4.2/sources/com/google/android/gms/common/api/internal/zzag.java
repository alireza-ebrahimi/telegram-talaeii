package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;

public final class zzag implements zzbc {
    private final zzbd zzhf;
    private boolean zzhg = false;

    public zzag(zzbd zzbd) {
        this.zzhf = zzbd;
    }

    public final void begin() {
    }

    public final void connect() {
        if (this.zzhg) {
            this.zzhg = false;
            this.zzhf.zza(new zzai(this, this));
        }
    }

    public final boolean disconnect() {
        if (this.zzhg) {
            return false;
        }
        if (this.zzhf.zzfq.zzba()) {
            this.zzhg = true;
            for (zzch zzcc : this.zzhf.zzfq.zziq) {
                zzcc.zzcc();
            }
            return false;
        }
        this.zzhf.zzf(null);
        return true;
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(T t) {
        return execute(t);
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(T t) {
        try {
            this.zzhf.zzfq.zzir.zzb(t);
            zzav zzav = this.zzhf.zzfq;
            AnyClient anyClient = (Client) zzav.zzil.get(t.getClientKey());
            Preconditions.checkNotNull(anyClient, "Appropriate Api was not requested.");
            if (anyClient.isConnected() || !this.zzhf.zzjb.containsKey(t.getClientKey())) {
                if (anyClient instanceof SimpleClientAdapter) {
                    anyClient = ((SimpleClientAdapter) anyClient).getClient();
                }
                t.run(anyClient);
                return t;
            }
            t.setFailedResult(new Status(17));
            return t;
        } catch (DeadObjectException e) {
            this.zzhf.zza(new zzah(this, this));
        }
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void onConnectionSuspended(int i) {
        this.zzhf.zzf(null);
        this.zzhf.zzjf.zzb(i, this.zzhg);
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    final void zzap() {
        if (this.zzhg) {
            this.zzhg = false;
            this.zzhf.zzfq.zzir.release();
            disconnect();
        }
    }
}
