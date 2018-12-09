package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzn implements Creator<WalletObjectMessage> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        UriData uriData = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        UriData uriData2 = null;
        TimeInterval timeInterval = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    timeInterval = (TimeInterval) SafeParcelReader.createParcelable(parcel, readHeader, TimeInterval.CREATOR);
                    break;
                case 5:
                    uriData2 = (UriData) SafeParcelReader.createParcelable(parcel, readHeader, UriData.CREATOR);
                    break;
                case 6:
                    uriData = (UriData) SafeParcelReader.createParcelable(parcel, readHeader, UriData.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new WalletObjectMessage(str2, str, timeInterval, uriData2, uriData);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new WalletObjectMessage[i];
    }
}
