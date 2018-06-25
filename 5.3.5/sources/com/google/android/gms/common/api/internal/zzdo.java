package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzdo<A extends zzb, L> {
    private final zzck<L> zzgau;

    protected zzdo(zzck<L> zzck) {
        this.zzgau = zzck;
    }

    @Hide
    public final zzck<L> zzakx() {
        return this.zzgau;
    }

    @Hide
    protected abstract void zzc(A a, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException;
}
