package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* renamed from: com.google.firebase.auth.t */
public final class C1890t implements Creator<PhoneAuthCredential> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        String str = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str2 = null;
        boolean z2 = false;
        String str3 = null;
        String str4 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    str4 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 2:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    z2 = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 4:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 5:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 6:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new PhoneAuthCredential(str4, str3, z2, str2, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PhoneAuthCredential[i];
    }
}
