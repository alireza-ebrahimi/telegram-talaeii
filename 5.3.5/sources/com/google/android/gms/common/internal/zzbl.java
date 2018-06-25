package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

final class zzbl implements zza {
    private /* synthetic */ PendingResult zzghq;
    private /* synthetic */ TaskCompletionSource zzghr;
    private /* synthetic */ zzbo zzghs;
    private /* synthetic */ zzbp zzght;

    zzbl(PendingResult pendingResult, TaskCompletionSource taskCompletionSource, zzbo zzbo, zzbp zzbp) {
        this.zzghq = pendingResult;
        this.zzghr = taskCompletionSource;
        this.zzghs = zzbo;
        this.zzght = zzbp;
    }

    public final void zzr(Status status) {
        if (status.isSuccess()) {
            this.zzghr.setResult(this.zzghs.zzb(this.zzghq.await(0, TimeUnit.MILLISECONDS)));
            return;
        }
        this.zzghr.setException(this.zzght.zzz(status));
    }
}
