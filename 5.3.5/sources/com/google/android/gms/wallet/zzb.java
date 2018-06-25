package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzb implements Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int zzd = zzbgm.zzd(parcel);
        boolean z = false;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str10 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str9 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    str8 = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    str7 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    str6 = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                case 8:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 9:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 10:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 11:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 12:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zza(str10, str9, str8, str7, str6, str5, str4, str3, str2, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zza[i];
    }
}
