package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import java.util.ArrayList;

@Hide
public final class zzae implements Creator<PaymentDataRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        TransactionInfo transactionInfo = null;
        int zzd = zzbgm.zzd(parcel);
        boolean z2 = true;
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = null;
        ArrayList arrayList = null;
        ShippingAddressRequirements shippingAddressRequirements = null;
        CardRequirements cardRequirements = null;
        boolean z3 = false;
        boolean z4 = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    z4 = zzbgm.zzc(parcel, readInt);
                    break;
                case 2:
                    z3 = zzbgm.zzc(parcel, readInt);
                    break;
                case 3:
                    cardRequirements = (CardRequirements) zzbgm.zza(parcel, readInt, CardRequirements.CREATOR);
                    break;
                case 4:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 5:
                    shippingAddressRequirements = (ShippingAddressRequirements) zzbgm.zza(parcel, readInt, ShippingAddressRequirements.CREATOR);
                    break;
                case 6:
                    arrayList = zzbgm.zzab(parcel, readInt);
                    break;
                case 7:
                    paymentMethodTokenizationParameters = (PaymentMethodTokenizationParameters) zzbgm.zza(parcel, readInt, PaymentMethodTokenizationParameters.CREATOR);
                    break;
                case 8:
                    transactionInfo = (TransactionInfo) zzbgm.zza(parcel, readInt, TransactionInfo.CREATOR);
                    break;
                case 9:
                    z2 = zzbgm.zzc(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new PaymentDataRequest(z4, z3, cardRequirements, z, shippingAddressRequirements, arrayList, paymentMethodTokenizationParameters, transactionInfo, z2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PaymentDataRequest[i];
    }
}
