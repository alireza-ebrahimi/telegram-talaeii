package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzfs extends zzbgl {
    public static final Creator<zzfs> CREATOR = new zzft();
    private String label;
    private String packageName;
    private long zzlvf;

    public zzfs(String str, String str2, long j) {
        this.packageName = str;
        this.label = str2;
        this.zzlvf = j;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.packageName, false);
        zzbgo.zza(parcel, 3, this.label, false);
        zzbgo.zza(parcel, 4, this.zzlvf);
        zzbgo.zzai(parcel, zze);
    }
}
