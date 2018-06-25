package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcci extends zzcce<Long> {
    public zzcci(int i, String str, Long l) {
        super(0, str, l);
    }

    private final Long zzd(zzccm zzccm) {
        try {
            return Long.valueOf(zzccm.getLongFlagValue(getKey(), ((Long) zzje()).longValue(), getSource()));
        } catch (RemoteException e) {
            return (Long) zzje();
        }
    }

    public final /* synthetic */ Object zza(zzccm zzccm) {
        return zzd(zzccm);
    }
}
