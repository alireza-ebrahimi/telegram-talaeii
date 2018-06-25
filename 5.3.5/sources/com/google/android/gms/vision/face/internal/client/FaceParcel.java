package com.google.android.gms.vision.face.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.apps.common.proguard.UsedByNative;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
@UsedByNative("wrapper.cc")
public class FaceParcel extends zzbgl {
    public static final Creator<FaceParcel> CREATOR = new zzb();
    public final float centerX;
    public final float centerY;
    public final float height;
    public final int id;
    private int versionCode;
    public final float width;
    public final float zzlhj;
    public final float zzlhk;
    public final LandmarkParcel[] zzlhl;
    public final float zzlhm;
    public final float zzlhn;
    public final float zzlho;

    public FaceParcel(int i, int i2, float f, float f2, float f3, float f4, float f5, float f6, LandmarkParcel[] landmarkParcelArr, float f7, float f8, float f9) {
        this.versionCode = i;
        this.id = i2;
        this.centerX = f;
        this.centerY = f2;
        this.width = f3;
        this.height = f4;
        this.zzlhj = f5;
        this.zzlhk = f6;
        this.zzlhl = landmarkParcelArr;
        this.zzlhm = f7;
        this.zzlhn = f8;
        this.zzlho = f9;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zzc(parcel, 2, this.id);
        zzbgo.zza(parcel, 3, this.centerX);
        zzbgo.zza(parcel, 4, this.centerY);
        zzbgo.zza(parcel, 5, this.width);
        zzbgo.zza(parcel, 6, this.height);
        zzbgo.zza(parcel, 7, this.zzlhj);
        zzbgo.zza(parcel, 8, this.zzlhk);
        zzbgo.zza(parcel, 9, this.zzlhl, i, false);
        zzbgo.zza(parcel, 10, this.zzlhm);
        zzbgo.zza(parcel, 11, this.zzlhn);
        zzbgo.zza(parcel, 12, this.zzlho);
        zzbgo.zzai(parcel, zze);
    }
}
