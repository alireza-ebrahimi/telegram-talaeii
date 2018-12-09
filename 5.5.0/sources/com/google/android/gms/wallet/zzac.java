package com.google.android.gms.wallet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class zzac implements Creator<PaymentData> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Bundle bundle = null;
        String str2 = null;
        PaymentMethodToken paymentMethodToken = null;
        UserAddress userAddress = null;
        CardInfo cardInfo = null;
        String str3 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 2:
                    cardInfo = (CardInfo) SafeParcelReader.createParcelable(parcel, readHeader, CardInfo.CREATOR);
                    break;
                case 3:
                    userAddress = (UserAddress) SafeParcelReader.createParcelable(parcel, readHeader, UserAddress.CREATOR);
                    break;
                case 4:
                    paymentMethodToken = (PaymentMethodToken) SafeParcelReader.createParcelable(parcel, readHeader, PaymentMethodToken.CREATOR);
                    break;
                case 5:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 6:
                    bundle = SafeParcelReader.createBundle(parcel, readHeader);
                    break;
                case 7:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new PaymentData(str3, cardInfo, userAddress, paymentMethodToken, str2, bundle, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PaymentData[i];
    }
}
