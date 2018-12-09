package com.google.android.gms.internal.vision;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class zzo implements Creator<zzn> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        float f = BitmapDescriptorFactory.HUE_RED;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    i4 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 3:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 4:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 5:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 6:
                    f = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzn(i4, i3, i2, i, f);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzn[i];
    }
}
