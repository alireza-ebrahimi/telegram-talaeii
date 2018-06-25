package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzac implements Creator<PaymentData> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Bundle bundle = null;
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        PaymentMethodToken paymentMethodToken = null;
        UserAddress userAddress = null;
        CardInfo cardInfo = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 2:
                    cardInfo = (CardInfo) zzbgm.zza(parcel, readInt, CardInfo.CREATOR);
                    break;
                case 3:
                    userAddress = (UserAddress) zzbgm.zza(parcel, readInt, UserAddress.CREATOR);
                    break;
                case 4:
                    paymentMethodToken = (PaymentMethodToken) zzbgm.zza(parcel, readInt, PaymentMethodToken.CREATOR);
                    break;
                case 5:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    bundle = zzbgm.zzs(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new PaymentData(str2, cardInfo, userAddress, paymentMethodToken, str, bundle);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PaymentData[i];
    }
}
