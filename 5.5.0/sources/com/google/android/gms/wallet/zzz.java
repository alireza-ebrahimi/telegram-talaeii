package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;

public final class zzz implements Creator<MaskedWalletRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
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
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 4:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 5:
                    z3 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 6:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 7:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 8:
                    str4 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 9:
                    cart = (Cart) SafeParcelReader.createParcelable(parcel, readHeader, Cart.CREATOR);
                    break;
                case 10:
                    z4 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 11:
                    z5 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 12:
                    countrySpecificationArr = (CountrySpecification[]) SafeParcelReader.createTypedArray(parcel, readHeader, CountrySpecification.CREATOR);
                    break;
                case 13:
                    z6 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 14:
                    z7 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 15:
                    arrayList = SafeParcelReader.createTypedList(parcel, readHeader, CountrySpecification.CREATOR);
                    break;
                case 16:
                    paymentMethodTokenizationParameters = (PaymentMethodTokenizationParameters) SafeParcelReader.createParcelable(parcel, readHeader, PaymentMethodTokenizationParameters.CREATOR);
                    break;
                case 17:
                    arrayList2 = SafeParcelReader.createIntegerList(parcel, readHeader);
                    break;
                case 18:
                    str5 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new MaskedWalletRequest(str, z, z2, z3, str2, str3, str4, cart, z4, z5, countrySpecificationArr, z6, z7, arrayList, paymentMethodTokenizationParameters, arrayList2, str5);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MaskedWalletRequest[i];
    }
}
