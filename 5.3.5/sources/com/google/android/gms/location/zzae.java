package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzae extends zzbgl {
    public static final Creator<zzae> CREATOR = new zzaf();
    private final String zzfaz;
    private final String zzisn;
    private final String zziso;

    @Hide
    zzae(String str, String str2, String str3) {
        this.zzfaz = str;
        this.zzisn = str2;
        this.zziso = str3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzisn, false);
        zzbgo.zza(parcel, 2, this.zziso, false);
        zzbgo.zza(parcel, 5, this.zzfaz, false);
        zzbgo.zzai(parcel, zze);
    }
}
