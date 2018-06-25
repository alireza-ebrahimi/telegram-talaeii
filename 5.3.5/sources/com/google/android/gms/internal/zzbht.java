package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzbht implements Creator<zzbhq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzbhj zzbhj = null;
        int i = 0;
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        String str2 = null;
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    i4 = zzbgm.zzg(parcel, readInt);
                    break;
                case 2:
                    i3 = zzbgm.zzg(parcel, readInt);
                    break;
                case 3:
                    z2 = zzbgm.zzc(parcel, readInt);
                    break;
                case 4:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                case 5:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 6:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 8:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 9:
                    zzbhj = (zzbhj) zzbgm.zza(parcel, readInt, zzbhj.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzbhq(i4, i3, z2, i2, z, str2, i, str, zzbhj);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbhq[i];
    }
}
