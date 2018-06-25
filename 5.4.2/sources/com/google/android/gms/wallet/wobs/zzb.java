package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public final class zzb implements Creator<CommonWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        int i = 0;
        ArrayList newArrayList = ArrayUtils.newArrayList();
        TimeInterval timeInterval = null;
        ArrayList newArrayList2 = ArrayUtils.newArrayList();
        String str9 = null;
        String str10 = null;
        ArrayList newArrayList3 = ArrayUtils.newArrayList();
        boolean z = false;
        ArrayList newArrayList4 = ArrayUtils.newArrayList();
        ArrayList newArrayList5 = ArrayUtils.newArrayList();
        ArrayList newArrayList6 = ArrayUtils.newArrayList();
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 5:
                    str4 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 6:
                    str5 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 7:
                    str6 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 8:
                    str7 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 9:
                    str8 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 10:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 11:
                    newArrayList = SafeParcelReader.createTypedList(parcel, readHeader, WalletObjectMessage.CREATOR);
                    break;
                case 12:
                    timeInterval = (TimeInterval) SafeParcelReader.createParcelable(parcel, readHeader, TimeInterval.CREATOR);
                    break;
                case 13:
                    newArrayList2 = SafeParcelReader.createTypedList(parcel, readHeader, LatLng.CREATOR);
                    break;
                case 14:
                    str9 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 15:
                    str10 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 16:
                    newArrayList3 = SafeParcelReader.createTypedList(parcel, readHeader, LabelValueRow.CREATOR);
                    break;
                case 17:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 18:
                    newArrayList4 = SafeParcelReader.createTypedList(parcel, readHeader, UriData.CREATOR);
                    break;
                case 19:
                    newArrayList5 = SafeParcelReader.createTypedList(parcel, readHeader, TextModuleData.CREATOR);
                    break;
                case 20:
                    newArrayList6 = SafeParcelReader.createTypedList(parcel, readHeader, UriData.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new CommonWalletObject(str, str2, str3, str4, str5, str6, str7, str8, i, newArrayList, timeInterval, newArrayList2, str9, str10, newArrayList3, z, newArrayList4, newArrayList5, newArrayList6);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CommonWalletObject[i];
    }
}
