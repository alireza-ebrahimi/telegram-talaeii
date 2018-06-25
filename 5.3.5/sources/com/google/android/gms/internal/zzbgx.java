package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzbgx extends zzbgr {
    private final zzn<Status> zzgbf;

    public zzbgx(zzn<Status> zzn) {
        this.zzgbf = zzn;
    }

    public final void zzch(int i) throws RemoteException {
        this.zzgbf.setResult(new Status(i));
    }
}
