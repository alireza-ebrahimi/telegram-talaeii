package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.location.zzj;
import java.util.List;

@Hide
public final class zzcfv implements Creator<zzcfu> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        zzj zzj = zzcfu.zzitn;
        List list = zzcfu.zzitm;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    zzj = (zzj) zzbgm.zza(parcel, readInt, zzj.CREATOR);
                    break;
                case 2:
                    list = zzbgm.zzc(parcel, readInt, zzcfs.CREATOR);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzcfu(zzj, list, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcfu[i];
    }
}
