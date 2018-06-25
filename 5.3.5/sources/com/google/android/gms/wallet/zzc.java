package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzc implements Creator<CardInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        UserAddress userAddress = null;
        int zzd = zzbgm.zzd(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 2:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 5:
                    userAddress = (UserAddress) zzbgm.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new CardInfo(str3, str2, str, i, userAddress);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CardInfo[i];
    }
}
