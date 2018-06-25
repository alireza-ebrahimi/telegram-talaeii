package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdlz extends zzbgl {
    public static final Creator<zzdlz> CREATOR = new zzdma();
    private byte[] zzloj;

    zzdlz() {
        this(new byte[0]);
    }

    public zzdlz(byte[] bArr) {
        this.zzloj = bArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzloj, false);
        zzbgo.zzai(parcel, zze);
    }
}
