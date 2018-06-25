package com.google.android.gms.vision.face.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzc extends zzbgl {
    public static final Creator<zzc> CREATOR = new zzd();
    public int mode;
    public int zzlhp;
    public int zzlhq;
    public boolean zzlhr;
    public boolean zzlhs;
    public float zzlht;

    public zzc(int i, int i2, int i3, boolean z, boolean z2, float f) {
        this.mode = i;
        this.zzlhp = i2;
        this.zzlhq = i3;
        this.zzlhr = z;
        this.zzlhs = z2;
        this.zzlht = f;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.mode);
        zzbgo.zzc(parcel, 3, this.zzlhp);
        zzbgo.zzc(parcel, 4, this.zzlhq);
        zzbgo.zza(parcel, 5, this.zzlhr);
        zzbgo.zza(parcel, 6, this.zzlhs);
        zzbgo.zza(parcel, 7, this.zzlht);
        zzbgo.zzai(parcel, zze);
    }
}
