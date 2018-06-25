package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.location.zzu;
import com.google.android.gms.location.zzv;
import com.google.android.gms.location.zzx;
import com.google.android.gms.location.zzy;

@Hide
public final class zzchn extends zzbgl {
    public static final Creator<zzchn> CREATOR = new zzcho();
    private PendingIntent zzekd;
    private int zzitp;
    private zzcgr zzits;
    private zzchl zziur;
    private zzx zzius;
    private zzu zziut;

    zzchn(int i, zzchl zzchl, IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2, IBinder iBinder3) {
        zzcgr zzcgr = null;
        this.zzitp = i;
        this.zziur = zzchl;
        this.zzius = iBinder == null ? null : zzy.zzbf(iBinder);
        this.zzekd = pendingIntent;
        this.zziut = iBinder2 == null ? null : zzv.zzbe(iBinder2);
        if (!(iBinder3 == null || iBinder3 == null)) {
            IInterface queryLocalInterface = iBinder3.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
            zzcgr = queryLocalInterface instanceof zzcgr ? (zzcgr) queryLocalInterface : new zzcgt(iBinder3);
        }
        this.zzits = zzcgr;
    }

    public static zzchn zza(zzu zzu, @Nullable zzcgr zzcgr) {
        return new zzchn(2, null, null, null, zzu.asBinder(), zzcgr != null ? zzcgr.asBinder() : null);
    }

    public static zzchn zza(zzx zzx, @Nullable zzcgr zzcgr) {
        return new zzchn(2, null, zzx.asBinder(), null, null, zzcgr != null ? zzcgr.asBinder() : null);
    }

    @Hide
    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder = null;
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzitp);
        zzbgo.zza(parcel, 2, this.zziur, i, false);
        zzbgo.zza(parcel, 3, this.zzius == null ? null : this.zzius.asBinder(), false);
        zzbgo.zza(parcel, 4, this.zzekd, i, false);
        zzbgo.zza(parcel, 5, this.zziut == null ? null : this.zziut.asBinder(), false);
        if (this.zzits != null) {
            iBinder = this.zzits.asBinder();
        }
        zzbgo.zza(parcel, 6, iBinder, false);
        zzbgo.zzai(parcel, zze);
    }
}
