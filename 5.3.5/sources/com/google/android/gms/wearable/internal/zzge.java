package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.List;

@Hide
public final class zzge extends zzbgl {
    public static final Creator<zzge> CREATOR = new zzgf();
    private int statusCode;
    private long zzlvf;
    private List<zzfs> zzlvh;

    public zzge(int i, long j, List<zzfs> list) {
        this.statusCode = i;
        this.zzlvf = j;
        this.zzlvh = list;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.statusCode);
        zzbgo.zza(parcel, 3, this.zzlvf);
        zzbgo.zzc(parcel, 4, this.zzlvh, false);
        zzbgo.zzai(parcel, zze);
    }
}
