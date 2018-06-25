package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;

public final class zzal implements zzbh {
    private final zzbi zzfxd;
    private boolean zzfxe = false;

    public zzal(zzbi zzbi) {
        this.zzfxd = zzbi;
    }

    public final void begin() {
    }

    public final void connect() {
        if (this.zzfxe) {
            this.zzfxe = false;
            this.zzfxd.zza(new zzan(this, this));
        }
    }

    public final boolean disconnect() {
        if (this.zzfxe) {
            return false;
        }
        if (this.zzfxd.zzfvq.zzajt()) {
            this.zzfxe = true;
            for (zzdh zzalb : this.zzfxd.zzfvq.zzfyo) {
                zzalb.zzalb();
            }
            return false;
        }
        this.zzfxd.zzg(null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void onConnectionSuspended(int i) {
        this.zzfxd.zzg(null);
        this.zzfxd.zzfzc.zzf(i, this.zzfxe);
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    final void zzaji() {
        if (this.zzfxe) {
            this.zzfxe = false;
            this.zzfxd.zzfvq.zzfyp.release();
            disconnect();
        }
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zzd(T t) {
        return zze(t);
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zze(T t) {
        try {
            this.zzfxd.zzfvq.zzfyp.zzb(t);
            zzba zzba = this.zzfxd.zzfvq;
            zzb zzb = (zze) zzba.zzfyj.get(t.zzahm());
            zzbq.checkNotNull(zzb, "Appropriate Api was not requested.");
            if (zzb.isConnected() || !this.zzfxd.zzfyy.containsKey(t.zzahm())) {
                if (zzb instanceof zzbz) {
                    zzb = zzbz.zzanb();
                }
                t.zzb(zzb);
                return t;
            }
            t.zzu(new Status(17));
            return t;
        } catch (DeadObjectException e) {
            this.zzfxd.zza(new zzam(this, this));
        }
    }
}
