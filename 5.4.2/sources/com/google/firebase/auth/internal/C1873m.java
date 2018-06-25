package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.firebase.auth.zzd;
import java.util.List;

/* renamed from: com.google.firebase.auth.internal.m */
public final class C1873m implements Creator<zzl> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzd zzd = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z = false;
        zzn zzn = null;
        Boolean bool = null;
        String str = null;
        List list = null;
        List list2 = null;
        String str2 = null;
        String str3 = null;
        zzh zzh = null;
        zzao zzao = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    zzao = (zzao) SafeParcelReader.createParcelable(parcel, readHeader, zzao.CREATOR);
                    break;
                case 2:
                    zzh = (zzh) SafeParcelReader.createParcelable(parcel, readHeader, zzh.CREATOR);
                    break;
                case 3:
                    str3 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 4:
                    str2 = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 5:
                    list2 = SafeParcelReader.createTypedList(parcel, readHeader, zzh.CREATOR);
                    break;
                case 6:
                    list = SafeParcelReader.createStringList(parcel, readHeader);
                    break;
                case 7:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 8:
                    bool = SafeParcelReader.readBooleanObject(parcel, readHeader);
                    break;
                case 9:
                    zzn = (zzn) SafeParcelReader.createParcelable(parcel, readHeader, zzn.CREATOR);
                    break;
                case 10:
                    z = SafeParcelReader.readBoolean(parcel, readHeader);
                    break;
                case 11:
                    zzd = (zzd) SafeParcelReader.createParcelable(parcel, readHeader, zzd.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzl(zzao, zzh, str3, str2, list2, list, str, bool, zzn, z, zzd);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzl[i];
    }
}
