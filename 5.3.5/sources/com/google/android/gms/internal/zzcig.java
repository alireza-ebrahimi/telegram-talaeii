package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzcig implements Creator<zzcif> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        long j = 0;
        long j2 = 0;
        String str5 = null;
        boolean z = true;
        boolean z2 = false;
        long j3 = -2147483648L;
        String str6 = null;
        long j4 = 0;
        long j5 = 0;
        int i = 0;
        boolean z3 = true;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 7:
                    j2 = zzbgm.zzi(parcel, readInt);
                    break;
                case 8:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                case 9:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 10:
                    z2 = zzbgm.zzc(parcel, readInt);
                    break;
                case 11:
                    j3 = zzbgm.zzi(parcel, readInt);
                    break;
                case 12:
                    str6 = zzbgm.zzq(parcel, readInt);
                    break;
                case 13:
                    j4 = zzbgm.zzi(parcel, readInt);
                    break;
                case 14:
                    j5 = zzbgm.zzi(parcel, readInt);
                    break;
                case 15:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 16:
                    z3 = zzbgm.zzc(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzcif(str, str2, str3, str4, j, j2, str5, z, z2, j3, str6, j4, j5, i, z3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcif[i];
    }
}
