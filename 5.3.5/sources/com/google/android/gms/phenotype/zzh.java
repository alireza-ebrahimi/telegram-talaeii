package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzh implements Creator<ExperimentTokens> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        byte[][] bArr = null;
        int zzd = zzbgm.zzd(parcel);
        int[] iArr = null;
        byte[][] bArr2 = null;
        byte[][] bArr3 = null;
        byte[][] bArr4 = null;
        byte[][] bArr5 = null;
        byte[] bArr6 = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    bArr6 = zzbgm.zzt(parcel, readInt);
                    break;
                case 4:
                    bArr5 = zzbgm.zzu(parcel, readInt);
                    break;
                case 5:
                    bArr4 = zzbgm.zzu(parcel, readInt);
                    break;
                case 6:
                    bArr3 = zzbgm.zzu(parcel, readInt);
                    break;
                case 7:
                    bArr2 = zzbgm.zzu(parcel, readInt);
                    break;
                case 8:
                    iArr = zzbgm.zzw(parcel, readInt);
                    break;
                case 9:
                    bArr = zzbgm.zzu(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new ExperimentTokens(str, bArr6, bArr5, bArr4, bArr3, bArr2, iArr, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new ExperimentTokens[i];
    }
}
