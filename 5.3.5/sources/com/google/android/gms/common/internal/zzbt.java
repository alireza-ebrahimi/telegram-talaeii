package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzbt extends zzbgl {
    public static final Creator<zzbt> CREATOR = new zzbu();
    private int zzehz;
    private ConnectionResult zzfuw;
    private boolean zzfxq;
    private IBinder zzghx;
    private boolean zzghy;

    zzbt(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.zzehz = i;
        this.zzghx = iBinder;
        this.zzfuw = connectionResult;
        this.zzfxq = z;
        this.zzghy = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbt)) {
            return false;
        }
        zzbt zzbt = (zzbt) obj;
        return this.zzfuw.equals(zzbt.zzfuw) && zzamy().equals(zzbt.zzamy());
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, this.zzghx, false);
        zzbgo.zza(parcel, 3, this.zzfuw, i, false);
        zzbgo.zza(parcel, 4, this.zzfxq);
        zzbgo.zza(parcel, 5, this.zzghy);
        zzbgo.zzai(parcel, zze);
    }

    public final ConnectionResult zzain() {
        return this.zzfuw;
    }

    public final zzan zzamy() {
        IBinder iBinder = this.zzghx;
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
        return queryLocalInterface instanceof zzan ? (zzan) queryLocalInterface : new zzap(iBinder);
    }

    public final boolean zzamz() {
        return this.zzfxq;
    }

    public final boolean zzana() {
        return this.zzghy;
    }
}
