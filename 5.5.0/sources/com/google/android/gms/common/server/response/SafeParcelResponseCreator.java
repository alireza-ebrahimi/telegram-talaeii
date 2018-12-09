package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public class SafeParcelResponseCreator implements Creator<SafeParcelResponse> {
    public static final int CONTENT_DESCRIPTION = 0;

    public SafeParcelResponse createFromParcel(Parcel parcel) {
        FieldMappingDictionary fieldMappingDictionary = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 2:
                    parcel2 = SafeParcelReader.createParcel(parcel, readHeader);
                    break;
                case 3:
                    fieldMappingDictionary = (FieldMappingDictionary) SafeParcelReader.createParcelable(parcel, readHeader, FieldMappingDictionary.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new SafeParcelResponse(i, parcel2, fieldMappingDictionary);
    }

    public SafeParcelResponse[] newArray(int i) {
        return new SafeParcelResponse[i];
    }
}
