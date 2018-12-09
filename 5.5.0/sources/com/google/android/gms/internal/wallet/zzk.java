package com.google.android.gms.internal.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.widget.RemoteViews;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzk implements Creator<zzj> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        byte[] bArr = null;
        RemoteViews remoteViews = null;
        int[] iArr = null;
        String[] strArr = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    strArr = SafeParcelReader.createStringArray(parcel, readHeader);
                    break;
                case 2:
                    iArr = SafeParcelReader.createIntArray(parcel, readHeader);
                    break;
                case 3:
                    remoteViews = (RemoteViews) SafeParcelReader.createParcelable(parcel, readHeader, RemoteViews.CREATOR);
                    break;
                case 4:
                    bArr = SafeParcelReader.createByteArray(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new zzj(strArr, iArr, remoteViews, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzj[i];
    }
}
