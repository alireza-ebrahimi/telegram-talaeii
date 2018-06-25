package com.google.android.gms.internal.clearcut;

import android.os.IInterface;
import com.google.android.gms.clearcut.zzc;
import com.google.android.gms.clearcut.zze;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public interface zzl extends IInterface {
    void zza(Status status);

    void zza(Status status, long j);

    void zza(Status status, zzc zzc);

    void zza(Status status, zze[] zzeArr);

    void zza(DataHolder dataHolder);

    void zzb(Status status);

    void zzb(Status status, long j);

    void zzb(Status status, zzc zzc);

    void zzc(Status status);
}
