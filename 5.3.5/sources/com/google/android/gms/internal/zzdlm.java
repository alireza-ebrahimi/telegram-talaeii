package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdlm implements Creator<zzdll> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        String str = null;
        int zzd = zzbgm.zzd(parcel);
        float f = 0.0f;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        String str2 = null;
        zzdlf zzdlf = null;
        zzdlf zzdlf2 = null;
        zzdlf zzdlf3 = null;
        zzdlu[] zzdluArr = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    zzdluArr = (zzdlu[]) zzbgm.zzb(parcel, readInt, zzdlu.CREATOR);
                    break;
                case 3:
                    zzdlf3 = (zzdlf) zzbgm.zza(parcel, readInt, zzdlf.CREATOR);
                    break;
                case 4:
                    zzdlf2 = (zzdlf) zzbgm.zza(parcel, readInt, zzdlf.CREATOR);
                    break;
                case 5:
                    zzdlf = (zzdlf) zzbgm.zza(parcel, readInt, zzdlf.CREATOR);
                    break;
                case 6:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    f = zzbgm.zzl(parcel, readInt);
                    break;
                case 8:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 9:
                    i3 = zzbgm.zzg(parcel, readInt);
                    break;
                case 10:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 11:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                case 12:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzdll(zzdluArr, zzdlf3, zzdlf2, zzdlf, str2, f, str, i3, z, i2, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzdll[i];
    }
}
