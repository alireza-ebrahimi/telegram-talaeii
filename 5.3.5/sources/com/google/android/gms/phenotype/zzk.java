package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzk implements Creator<zzi> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        byte[] bArr = null;
        int i = 0;
        int zzd = zzbgm.zzd(parcel);
        long j = 0;
        double d = 0.0d;
        int i2 = 0;
        String str = null;
        boolean z = false;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 4:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 5:
                    d = zzbgm.zzn(parcel, readInt);
                    break;
                case 6:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    bArr = zzbgm.zzt(parcel, readInt);
                    break;
                case 8:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                case 9:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzi(str2, j, z, d, str, bArr, i2, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzi[i];
    }
}
