package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzx implements Creator<MaskedWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        InstrumentInfo[] instrumentInfoArr = null;
        int zzd = zzbgm.zzd(parcel);
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        OfferWalletObject[] offerWalletObjectArr = null;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        zza zza = null;
        zza zza2 = null;
        String str = null;
        String[] strArr = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    strArr = zzbgm.zzaa(parcel, readInt);
                    break;
                case 5:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    zza2 = (zza) zzbgm.zza(parcel, readInt, zza.CREATOR);
                    break;
                case 7:
                    zza = (zza) zzbgm.zza(parcel, readInt, zza.CREATOR);
                    break;
                case 8:
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) zzbgm.zzb(parcel, readInt, LoyaltyWalletObject.CREATOR);
                    break;
                case 9:
                    offerWalletObjectArr = (OfferWalletObject[]) zzbgm.zzb(parcel, readInt, OfferWalletObject.CREATOR);
                    break;
                case 10:
                    userAddress2 = (UserAddress) zzbgm.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 11:
                    userAddress = (UserAddress) zzbgm.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 12:
                    instrumentInfoArr = (InstrumentInfo[]) zzbgm.zzb(parcel, readInt, InstrumentInfo.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new MaskedWallet(str3, str2, strArr, str, zza2, zza, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress2, userAddress, instrumentInfoArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MaskedWallet[i];
    }
}
