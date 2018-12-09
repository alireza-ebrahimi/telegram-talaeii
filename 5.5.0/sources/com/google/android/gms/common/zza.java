package com.google.android.gms.common;

import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;

final class zza implements Continuation<Map<zzh<?>, String>, Void> {
    zza(GoogleApiAvailability googleApiAvailability) {
    }

    public final /* synthetic */ Object then(Task task) {
        task.getResult();
        return null;
    }
}
