package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.widget.RemoteViews;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdmb extends zzbgl {
    public static final Creator<zzdmb> CREATOR = new zzdmc();
    private String[] zzlok;
    private int[] zzlol;
    private RemoteViews zzlom;
    private byte[] zzlon;

    private zzdmb() {
    }

    public zzdmb(String[] strArr, int[] iArr, RemoteViews remoteViews, byte[] bArr) {
        this.zzlok = strArr;
        this.zzlol = iArr;
        this.zzlom = remoteViews;
        this.zzlon = bArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzlok, false);
        zzbgo.zza(parcel, 2, this.zzlol, false);
        zzbgo.zza(parcel, 3, this.zzlom, i, false);
        zzbgo.zza(parcel, 4, this.zzlon, false);
        zzbgo.zzai(parcel, zze);
    }
}
