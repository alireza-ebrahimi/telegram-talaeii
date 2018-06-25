package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdlx extends zzbgl {
    public static final Creator<zzdlx> CREATOR = new zzdly();
    private byte[] zzloi;

    zzdlx() {
        this(new byte[0]);
    }

    public zzdlx(byte[] bArr) {
        this.zzloi = bArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzloi, false);
        zzbgo.zzai(parcel, zze);
    }
}
