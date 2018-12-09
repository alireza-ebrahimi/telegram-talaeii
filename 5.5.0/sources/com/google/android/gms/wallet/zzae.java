package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzae implements Creator<PaymentDataRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        String str = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z2 = true;
        TransactionInfo transactionInfo = null;
        PaymentMethodTokenizationParameters paymentMethodTokenizationParameters = null;
        ArrayList arrayList = null;
        ShippingAddressRequirements shippingAddressRequirements = null;
        CardRequirements cardRequirements = null;
        boolean z3 = false;
        boolean z4 = false;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    z4 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 2:
                    z3 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 3:
                    cardRequirements = (CardRequirements) SafeParcelReader.createParcelable(parcel, readHeader, CardRequirements.CREATOR);
                    break;
                case 4:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 5:
                    shippingAddressRequirements = (ShippingAddressRequirements) SafeParcelReader.createParcelable(parcel, readHeader, ShippingAddressRequirements.CREATOR);
                    break;
                case 6:
                    arrayList = SafeParcelReader.createIntegerList(parcel, readHeader);
                    break;
                case 7:
                    paymentMethodTokenizationParameters = (PaymentMethodTokenizationParameters) SafeParcelReader.createParcelable(parcel, readHeader, PaymentMethodTokenizationParameters.CREATOR);
                    break;
                case 8:
                    transactionInfo = (TransactionInfo) SafeParcelReader.createParcelable(parcel, readHeader, TransactionInfo.CREATOR);
                    break;
                case 9:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 10:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new PaymentDataRequest(z4, z3, cardRequirements, z, shippingAddressRequirements, arrayList, paymentMethodTokenizationParameters, transactionInfo, z2, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PaymentDataRequest[i];
    }
}
