package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

final class zzev extends zzn<SendMessageResult> {
    private /* synthetic */ String val$action;
    private /* synthetic */ byte[] zzlbv;
    private /* synthetic */ String zzlsy;

    zzev(zzeu zzeu, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr) {
        this.zzlsy = str;
        this.val$action = str2;
        this.zzlbv = bArr;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zza(new zzhe(this), this.zzlsy, this.val$action, this.zzlbv);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzey(status, -1);
    }
}
