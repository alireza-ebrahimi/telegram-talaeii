package com.google.android.gms.internal.clearcut;

import android.util.Log;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.zze;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;

final class zzh extends ApiMethodImpl<Status, zzj> {
    private final zze zzao;

    zzh(zze zze, GoogleApiClient googleApiClient) {
        super(ClearcutLogger.API, googleApiClient);
        this.zzao = zze;
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzj zzj = (zzj) anyClient;
        zzl zzi = new zzi(this);
        try {
            zze zze = this.zzao;
            if (zze.zzt != null && zze.zzaa.zzbjp.length == 0) {
                zze.zzaa.zzbjp = zze.zzt.zza();
            }
            if (zze.zzan != null && zze.zzaa.zzbjw.length == 0) {
                zze.zzaa.zzbjw = zze.zzan.zza();
            }
            zzfz zzfz = zze.zzaa;
            byte[] bArr = new byte[zzfz.zzas()];
            zzfz.zza(zzfz, bArr, 0, bArr.length);
            zze.zzah = bArr;
            ((zzn) zzj.getService()).zza(zzi, this.zzao);
        } catch (Throwable e) {
            Log.e("ClearcutLoggerApiImpl", "derived ClearcutLogger.MessageProducer ", e);
            setFailedResult(new Status(10, "MessageProducer"));
        }
    }
}
