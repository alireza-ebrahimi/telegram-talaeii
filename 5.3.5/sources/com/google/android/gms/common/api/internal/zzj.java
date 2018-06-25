package com.google.android.gms.common.api.internal;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;
import java.util.Set;

@Hide
public final class zzj {
    private final ArrayMap<zzh<?>, ConnectionResult> zzfse = new ArrayMap();
    private final ArrayMap<zzh<?>, String> zzfuk = new ArrayMap();
    private final TaskCompletionSource<Map<zzh<?>, String>> zzful = new TaskCompletionSource();
    private int zzfum;
    private boolean zzfun = false;

    public zzj(Iterable<? extends GoogleApi<?>> iterable) {
        for (GoogleApi zzahv : iterable) {
            this.zzfse.put(zzahv.zzahv(), null);
        }
        this.zzfum = this.zzfse.keySet().size();
    }

    public final Task<Map<zzh<?>, String>> getTask() {
        return this.zzful.getTask();
    }

    public final void zza(zzh<?> zzh, ConnectionResult connectionResult, @Nullable String str) {
        this.zzfse.put(zzh, connectionResult);
        this.zzfuk.put(zzh, str);
        this.zzfum--;
        if (!connectionResult.isSuccess()) {
            this.zzfun = true;
        }
        if (this.zzfum != 0) {
            return;
        }
        if (this.zzfun) {
            this.zzful.setException(new AvailabilityException(this.zzfse));
            return;
        }
        this.zzful.setResult(this.zzfuk);
    }

    public final Set<zzh<?>> zzaii() {
        return this.zzfse.keySet();
    }
}
