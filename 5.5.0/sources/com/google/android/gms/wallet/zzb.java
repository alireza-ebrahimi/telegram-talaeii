package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzb implements Creator<zza> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z = false;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str10 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    str9 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    str8 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 5:
                    str7 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 6:
                    str6 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 7:
                    str5 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 8:
                    str4 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 9:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 10:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 11:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 12:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zza(str10, str9, str8, str7, str6, str5, str4, str3, str2, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zza[i];
    }
}
