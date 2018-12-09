package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class zzk implements Creator<FullWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        PaymentMethodToken paymentMethodToken = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
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
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    proxyCard = (ProxyCard) SafeParcelReader.createParcelable(parcel, readHeader, ProxyCard.CREATOR);
                    break;
                case 5:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 6:
                    zza2 = (zza) SafeParcelReader.createParcelable(parcel, readHeader, zza.CREATOR);
                    break;
                case 7:
                    zza = (zza) SafeParcelReader.createParcelable(parcel, readHeader, zza.CREATOR);
                    break;
                case 8:
                    strArr = SafeParcelReader.createStringArray(parcel, readHeader);
                    break;
                case 9:
                    userAddress2 = (UserAddress) SafeParcelReader.createParcelable(parcel, readHeader, UserAddress.CREATOR);
                    break;
                case 10:
                    userAddress = (UserAddress) SafeParcelReader.createParcelable(parcel, readHeader, UserAddress.CREATOR);
                    break;
                case 11:
                    instrumentInfoArr = (InstrumentInfo[]) SafeParcelReader.createTypedArray(parcel, readHeader, InstrumentInfo.CREATOR);
                    break;
                case 12:
                    paymentMethodToken = (PaymentMethodToken) SafeParcelReader.createParcelable(parcel, readHeader, PaymentMethodToken.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new FullWallet(str3, str2, proxyCard, str, zza2, zza, strArr, userAddress2, userAddress, instrumentInfoArr, paymentMethodToken);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new FullWallet[i];
    }
}
