package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.identity.intents.model.UserAddress;

public final class zzx implements Creator<MaskedWallet> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        InstrumentInfo[] instrumentInfoArr = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
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
                    strArr = SafeParcelReader.createStringArray(parcel, readHeader);
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
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) SafeParcelReader.createTypedArray(parcel, readHeader, LoyaltyWalletObject.CREATOR);
                    break;
                case 9:
                    offerWalletObjectArr = (OfferWalletObject[]) SafeParcelReader.createTypedArray(parcel, readHeader, OfferWalletObject.CREATOR);
                    break;
                case 10:
                    userAddress2 = (UserAddress) SafeParcelReader.createParcelable(parcel, readHeader, UserAddress.CREATOR);
                    break;
                case 11:
                    userAddress = (UserAddress) SafeParcelReader.createParcelable(parcel, readHeader, UserAddress.CREATOR);
                    break;
                case 12:
                    instrumentInfoArr = (InstrumentInfo[]) SafeParcelReader.createTypedArray(parcel, readHeader, InstrumentInfo.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new MaskedWallet(str3, str2, strArr, str, zza2, zza, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress2, userAddress, instrumentInfoArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MaskedWallet[i];
    }
}
