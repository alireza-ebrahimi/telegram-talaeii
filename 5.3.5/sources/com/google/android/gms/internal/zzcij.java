package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzcij implements Creator<zzcii> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        zzcnl zzcnl = null;
        long j = 0;
        boolean z = false;
        String str3 = null;
        zzcix zzcix = null;
        long j2 = 0;
        zzcix zzcix2 = null;
        long j3 = 0;
        zzcix zzcix3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 2:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    zzcnl = (zzcnl) zzbgm.zza(parcel, readInt, zzcnl.CREATOR);
                    break;
                case 5:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 6:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 7:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 8:
                    zzcix = (zzcix) zzbgm.zza(parcel, readInt, zzcix.CREATOR);
                    break;
                case 9:
                    j2 = zzbgm.zzi(parcel, readInt);
                    break;
                case 10:
                    zzcix2 = (zzcix) zzbgm.zza(parcel, readInt, zzcix.CREATOR);
                    break;
                case 11:
                    j3 = zzbgm.zzi(parcel, readInt);
                    break;
                case 12:
                    zzcix3 = (zzcix) zzbgm.zza(parcel, readInt, zzcix.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzcii(i, str, str2, zzcnl, j, z, str3, zzcix, j2, zzcix2, j3, zzcix3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcii[i];
    }
}
