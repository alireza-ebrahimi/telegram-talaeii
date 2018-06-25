package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.location.zzr;
import com.google.android.gms.location.zzs;

@Hide
public final class zzcfw extends zzbgl {
    public static final Creator<zzcfw> CREATOR = new zzcfx();
    private int zzitp;
    private zzcfu zzitq;
    private zzr zzitr;
    private zzcgr zzits;

    zzcfw(int i, zzcfu zzcfu, IBinder iBinder, IBinder iBinder2) {
        zzcgr zzcgr = null;
        this.zzitp = i;
        this.zzitq = zzcfu;
        this.zzitr = iBinder == null ? null : zzs.zzbd(iBinder);
        if (!(iBinder2 == null || iBinder2 == null)) {
            IInterface queryLocalInterface = iBinder2.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
            zzcgr = queryLocalInterface instanceof zzcgr ? (zzcgr) queryLocalInterface : new zzcgt(iBinder2);
        }
        this.zzits = zzcgr;
    }

    @Hide
    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzitp);
        zzbgo.zza(parcel, 2, this.zzitq, i, false);
        zzbgo.zza(parcel, 3, this.zzitr == null ? null : this.zzitr.asBinder(), false);
        if (this.zzits != null) {
            iBinder = this.zzits.asBinder();
        }
        zzbgo.zza(parcel, 4, iBinder, false);
        zzbgo.zzai(parcel, zze);
    }
}
