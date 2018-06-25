package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.vision.Frame;

@Hide
public final class zzdld extends zzbgl {
    public static final Creator<zzdld> CREATOR = new zzdle();
    public int height;
    private int id;
    public int rotation;
    public int width;
    private long zzitd;

    public zzdld(int i, int i2, int i3, long j, int i4) {
        this.width = i;
        this.height = i2;
        this.id = i3;
        this.zzitd = j;
        this.rotation = i4;
    }

    public static zzdld zzc(Frame frame) {
        zzdld zzdld = new zzdld();
        zzdld.width = frame.getMetadata().getWidth();
        zzdld.height = frame.getMetadata().getHeight();
        zzdld.rotation = frame.getMetadata().getRotation();
        zzdld.id = frame.getMetadata().getId();
        zzdld.zzitd = frame.getMetadata().getTimestampMillis();
        return zzdld;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.width);
        zzbgo.zzc(parcel, 3, this.height);
        zzbgo.zzc(parcel, 4, this.id);
        zzbgo.zza(parcel, 5, this.zzitd);
        zzbgo.zzc(parcel, 6, this.rotation);
        zzbgo.zzai(parcel, zze);
    }
}
