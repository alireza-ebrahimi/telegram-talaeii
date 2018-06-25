package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzbib implements Creator<zzbia> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzbhv zzbhv = null;
        int zzd = zzbgm.zzd(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 2:
                    parcel2 = zzbgm.zzad(parcel, readInt);
                    break;
                case 3:
                    zzbhv = (zzbhv) zzbgm.zza(parcel, readInt, zzbhv.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzbia(i, parcel2, zzbhv);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbia[i];
    }
}
