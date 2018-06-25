package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzee extends zzbgl {
    public static final Creator<zzee> CREATOR = new zzef();
    public final int statusCode;
    public final ParcelFileDescriptor zzjwp;

    public zzee(int i, ParcelFileDescriptor parcelFileDescriptor) {
        this.statusCode = i;
        this.zzjwp = parcelFileDescriptor;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int i2 = i | 1;
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzjwp, i2, false);
        zzbgo.zzai(parcel, zze);
    }
}
