package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public final class zzae {
    private final Map<BasePendingResult<?>, Boolean> zzfww = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zzfwx = Collections.synchronizedMap(new WeakHashMap());

    private final void zza(boolean z, Status status) {
        synchronized (this.zzfww) {
            Map hashMap = new HashMap(this.zzfww);
        }
        synchronized (this.zzfwx) {
            Map hashMap2 = new HashMap(this.zzfwx);
        }
        for (Entry entry : hashMap.entrySet()) {
            if (z || ((Boolean) entry.getValue()).booleanValue()) {
                ((BasePendingResult) entry.getKey()).zzv(status);
            }
        }
        for (Entry entry2 : hashMap2.entrySet()) {
            if (z || ((Boolean) entry2.getValue()).booleanValue()) {
                ((TaskCompletionSource) entry2.getKey()).trySetException(new ApiException(status));
            }
        }
    }

    final void zza(BasePendingResult<? extends Result> basePendingResult, boolean z) {
        this.zzfww.put(basePendingResult, Boolean.valueOf(z));
        basePendingResult.zza(new zzaf(this, basePendingResult));
    }

    final <TResult> void zza(TaskCompletionSource<TResult> taskCompletionSource, boolean z) {
        this.zzfwx.put(taskCompletionSource, Boolean.valueOf(z));
        taskCompletionSource.getTask().addOnCompleteListener(new zzag(this, taskCompletionSource));
    }

    final boolean zzajc() {
        return (this.zzfww.isEmpty() && this.zzfwx.isEmpty()) ? false : true;
    }

    public final void zzajd() {
        zza(false, zzbm.zzfzg);
    }

    public final void zzaje() {
        zza(true, zzdk.zzgbq);
    }
}
