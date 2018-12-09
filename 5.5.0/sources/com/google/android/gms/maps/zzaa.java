package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

public final class zzaa implements Creator<GoogleMapOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        byte b = (byte) -1;
        byte b2 = (byte) -1;
        int i = 0;
        CameraPosition cameraPosition = null;
        byte b3 = (byte) -1;
        byte b4 = (byte) -1;
        byte b5 = (byte) -1;
        byte b6 = (byte) -1;
        byte b7 = (byte) -1;
        byte b8 = (byte) -1;
        byte b9 = (byte) -1;
        byte b10 = (byte) -1;
        byte b11 = (byte) -1;
        Float f = null;
        Float f2 = null;
        LatLngBounds latLngBounds = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    b = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 3:
                    b2 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 4:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 5:
                    cameraPosition = (CameraPosition) SafeParcelReader.createParcelable(parcel, readHeader, CameraPosition.CREATOR);
                    break;
                case 6:
                    b3 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 7:
                    b4 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 8:
                    b5 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 9:
                    b6 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 10:
                    b7 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 11:
                    b8 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 12:
                    b9 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 14:
                    b10 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 15:
                    b11 = SafeParcelReader.readByte(parcel, readHeader);
                    break;
                case 16:
                    f = SafeParcelReader.readFloatObject(parcel, readHeader);
                    break;
                case 17:
                    f2 = SafeParcelReader.readFloatObject(parcel, readHeader);
                    break;
                case 18:
                    latLngBounds = (LatLngBounds) SafeParcelReader.createParcelable(parcel, readHeader, LatLngBounds.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new GoogleMapOptions(b, b2, i, cameraPosition, b3, b4, b5, b6, b7, b8, b9, b10, b11, f, f2, latLngBounds);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleMapOptions[i];
    }
}
