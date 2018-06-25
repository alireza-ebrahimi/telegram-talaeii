package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import com.google.android.gms.internal.zzbgm;
import java.util.ArrayList;

@Hide
public final class zzz implements Creator<MaskedWalletRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        Cart cart = null;
        boolean z4 = false;
        boolean z5 = false;
        CountrySpecification[] countrySpecificationArr = null;
        boolean z6 = true;
        boolean z7 = true;
        ArrayList arrayList = null;
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = null;
        ArrayList arrayList2 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 4:
                    z2 = zzbgm.zzc(parcel, readInt);
                    break;
                case 5:
                    z3 = zzbgm.zzc(parcel, readInt);
                    break;
                case 6:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 8:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 9:
                    cart = (Cart) zzbgm.zza(parcel, readInt, Cart.CREATOR);
                    break;
                case 10:
                    z4 = zzbgm.zzc(parcel, readInt);
                    break;
                case 11:
                    z5 = zzbgm.zzc(parcel, readInt);
                    break;
                case 12:
                    countrySpecificationArr = (CountrySpecification[]) zzbgm.zzb(parcel, readInt, CountrySpecification.CREATOR);
                    break;
                case 13:
                    z6 = zzbgm.zzc(parcel, readInt);
                    break;
                case 14:
                    z7 = zzbgm.zzc(parcel, readInt);
                    break;
                case 15:
                    arrayList = zzbgm.zzc(parcel, readInt, CountrySpecification.CREATOR);
                    break;
                case 16:
                    paymentMethodTokenizationParameters = (PaymentMethodTokenizationParameters) zzbgm.zza(parcel, readInt, PaymentMethodTokenizationParameters.CREATOR);
                    break;
                case 17:
                    arrayList2 = zzbgm.zzab(parcel, readInt);
                    break;
                case 18:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new MaskedWalletRequest(str, z, z2, z3, str2, str3, str4, cart, z4, z5, countrySpecificationArr, z6, z7, arrayList, paymentMethodTokenizationParameters, arrayList2, str5);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MaskedWalletRequest[i];
    }
}
