package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzn implements Creator<WalletObjectMessage> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        UriData uriData = null;
        int zzd = zzbgm.zzd(parcel);
        UriData uriData2 = null;
        TimeInterval timeInterval = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    timeInterval = (TimeInterval) zzbgm.zza(parcel, readInt, TimeInterval.CREATOR);
                    break;
                case 5:
                    uriData2 = (UriData) zzbgm.zza(parcel, readInt, UriData.CREATOR);
                    break;
                case 6:
                    uriData = (UriData) zzbgm.zza(parcel, readInt, UriData.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new WalletObjectMessage(str2, str, timeInterval, uriData2, uriData);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new WalletObjectMessage[i];
    }
}
