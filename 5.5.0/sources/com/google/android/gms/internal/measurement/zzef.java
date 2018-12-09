package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzef implements Creator<zzee> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        String str2 = null;
        zzjz zzjz = null;
        long j = 0;
        boolean z = false;
        String str3 = null;
        zzew zzew = null;
        long j2 = 0;
        zzew zzew2 = null;
        long j3 = 0;
        zzew zzew3 = null;
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
                    zzjz = (zzjz) SafeParcelReader.createParcelable(parcel, readHeader, zzjz.CREATOR);
                    break;
                case 5:
                    j = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 6:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 7:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 8:
                    zzew = (zzew) SafeParcelReader.createParcelable(parcel, readHeader, zzew.CREATOR);
                    break;
                case 9:
                    j2 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 10:
                    zzew2 = (zzew) SafeParcelReader.createParcelable(parcel, readHeader, zzew.CREATOR);
                    break;
                case 11:
                    j3 = SafeParcelReader.readLong(parcel, readHeader);
                    break;
                case 12:
                    zzew3 = (zzew) SafeParcelReader.createParcelable(parcel, readHeader, zzew.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzee(str, str2, zzjz, j, z, str3, zzew, j2, zzew2, j3, zzew3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzee[i];
    }
}
