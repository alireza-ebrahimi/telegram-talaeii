package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzk implements Creator<FullWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        PaymentMethodToken paymentMethodToken = null;
        int zzd = zzbgm.zzd(parcel);
        InstrumentInfo[] instrumentInfoArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        String[] strArr = null;
        zza zza = null;
        zza zza2 = null;
        String str = null;
        ProxyCard proxyCard = null;
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
                    proxyCard = (ProxyCard) zzbgm.zza(parcel, readInt, ProxyCard.CREATOR);
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
                    strArr = zzbgm.zzaa(parcel, readInt);
                    break;
                case 9:
                    userAddress2 = (UserAddress) zzbgm.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 10:
                    userAddress = (UserAddress) zzbgm.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 11:
                    instrumentInfoArr = (InstrumentInfo[]) zzbgm.zzb(parcel, readInt, InstrumentInfo.CREATOR);
                    break;
                case 12:
                    paymentMethodToken = (PaymentMethodToken) zzbgm.zza(parcel, readInt, PaymentMethodToken.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new FullWallet(str3, str2, proxyCard, str, zza2, zza, strArr, userAddress2, userAddress, instrumentInfoArr, paymentMethodToken);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new FullWallet[i];
    }
}
