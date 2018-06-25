package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.wallet.wobs.CommonWalletObject;

@Hide
public final class zzo implements Creator<GiftCardWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        String str = null;
        int zzd = zzbgm.zzd(parcel);
        String str2 = null;
        long j2 = 0;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        CommonWalletObject commonWalletObject = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    commonWalletObject = (CommonWalletObject) zzbgm.zza(parcel, readInt, CommonWalletObject.CREATOR);
                    break;
                case 3:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    j2 = zzbgm.zzi(parcel, readInt);
                    break;
                case 7:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 8:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 9:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new GiftCardWalletObject(commonWalletObject, str5, str4, str3, j2, str2, j, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GiftCardWalletObject[i];
    }
}
