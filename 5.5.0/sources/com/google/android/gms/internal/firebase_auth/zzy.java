package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.List;

public final class zzy implements Creator<zzx> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        List list = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        zzbd zzbd = null;
        String str = null;
        boolean z2 = false;
        String str2 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 4:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 5:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 6:
                    zzbd = (zzbd) SafeParcelReader.createParcelable(parcel, readHeader, zzbd.CREATOR);
                    break;
                case 7:
                    list = SafeParcelReader.createStringList(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzx(str2, z2, str, z, zzbd, list);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzx[i];
    }
}
