package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzh implements Creator<ExperimentTokens> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        byte[][] bArr = null;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int[] iArr = null;
        byte[][] bArr2 = null;
        byte[][] bArr3 = null;
        byte[][] bArr4 = null;
        byte[][] bArr5 = null;
        byte[] bArr6 = null;
        String str = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str = SafeParcelReader.createString(parcel, readHeader);
                    break;
                case 3:
                    bArr6 = SafeParcelReader.createByteArray(parcel, readHeader);
                    break;
                case 4:
                    bArr5 = SafeParcelReader.createByteArrayArray(parcel, readHeader);
                    break;
                case 5:
                    bArr4 = SafeParcelReader.createByteArrayArray(parcel, readHeader);
                    break;
                case 6:
                    bArr3 = SafeParcelReader.createByteArrayArray(parcel, readHeader);
                    break;
                case 7:
                    bArr2 = SafeParcelReader.createByteArrayArray(parcel, readHeader);
                    break;
                case 8:
                    iArr = SafeParcelReader.createIntArray(parcel, readHeader);
                    break;
                case 9:
                    bArr = SafeParcelReader.createByteArrayArray(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new ExperimentTokens(str, bArr6, bArr5, bArr4, bArr3, bArr2, iArr, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new ExperimentTokens[i];
    }
}
