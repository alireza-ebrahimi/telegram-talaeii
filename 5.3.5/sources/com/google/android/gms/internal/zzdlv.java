package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdlv implements Creator<zzdlu> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int zzd = zzbgm.zzd(parcel);
        float f = 0.0f;
        boolean z = false;
        String str2 = null;
        zzdlf zzdlf = null;
        zzdlf zzdlf2 = null;
        zzdlp[] zzdlpArr = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    zzdlpArr = (zzdlp[]) zzbgm.zzb(parcel, readInt, zzdlp.CREATOR);
                    break;
                case 3:
                    zzdlf2 = (zzdlf) zzbgm.zza(parcel, readInt, zzdlf.CREATOR);
                    break;
                case 4:
                    zzdlf = (zzdlf) zzbgm.zza(parcel, readInt, zzdlf.CREATOR);
                    break;
                case 5:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    f = zzbgm.zzl(parcel, readInt);
                    break;
                case 7:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 8:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzdlu(zzdlpArr, zzdlf2, zzdlf, str2, f, str, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzdlu[i];
    }
}
