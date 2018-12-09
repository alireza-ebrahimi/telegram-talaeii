package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

final class zzev extends zzn<SendMessageResult> {
    private final /* synthetic */ String val$action;
    private final /* synthetic */ String zzcb;
    private final /* synthetic */ byte[] zzee;

    zzev(zzeu zzeu, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr) {
        this.zzcb = str;
        this.val$action = str2;
        this.zzee = bArr;
        super(googleApiClient);
    }

    protected final /* synthetic */ Result createFailedResult(Status status) {
        return new zzey(status, -1);
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        zzhg zzhg = (zzhg) anyClient;
        ((zzep) zzhg.getService()).zza(new zzhe(this), this.zzcb, this.val$action, this.zzee);
    }
}
