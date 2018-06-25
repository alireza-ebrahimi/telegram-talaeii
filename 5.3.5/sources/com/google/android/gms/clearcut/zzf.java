package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbfv;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.phenotype.ExperimentTokens;

@Hide
public final class zzf implements Creator<zze> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ExperimentTokens[] experimentTokensArr = null;
        int zzd = zzbgm.zzd(parcel);
        boolean z = true;
        byte[][] bArr = null;
        int[] iArr = null;
        String[] strArr = null;
        int[] iArr2 = null;
        byte[] bArr2 = null;
        zzbfv zzbfv = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    zzbfv = (zzbfv) zzbgm.zza(parcel, readInt, zzbfv.CREATOR);
                    break;
                case 3:
                    bArr2 = zzbgm.zzt(parcel, readInt);
                    break;
                case 4:
                    iArr2 = zzbgm.zzw(parcel, readInt);
                    break;
                case 5:
                    strArr = zzbgm.zzaa(parcel, readInt);
                    break;
                case 6:
                    iArr = zzbgm.zzw(parcel, readInt);
                    break;
                case 7:
                    bArr = zzbgm.zzu(parcel, readInt);
                    break;
                case 8:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 9:
                    experimentTokensArr = (ExperimentTokens[]) zzbgm.zzb(parcel, readInt, ExperimentTokens.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zze(zzbfv, bArr2, iArr2, strArr, iArr, bArr, z, experimentTokensArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zze[i];
    }
}
