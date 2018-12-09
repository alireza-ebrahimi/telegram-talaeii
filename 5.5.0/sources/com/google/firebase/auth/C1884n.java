package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.firebase_auth.zzbf;

/* renamed from: com.google.firebase.auth.n */
public final class C1884n implements Creator<zzd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzbf zzbf = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 2:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    zzbf = (zzbf) SafeParcelReader.createParcelable(parcel, readHeader, zzbf.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzd(str3, str2, str, zzbf);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzd[i];
    }
}
