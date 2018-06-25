package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import android.widget.RemoteViews;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzdmc implements Creator<zzdmb> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        byte[] bArr = null;
        RemoteViews remoteViews = null;
        int[] iArr = null;
        String[] strArr = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    strArr = zzbgm.zzaa(parcel, readInt);
                    break;
                case 2:
                    iArr = zzbgm.zzw(parcel, readInt);
                    break;
                case 3:
                    remoteViews = (RemoteViews) zzbgm.zza(parcel, readInt, RemoteViews.CREATOR);
                    break;
                case 4:
                    bArr = zzbgm.zzt(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new zzdmb(strArr, iArr, remoteViews, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzdmb[i];
    }
}
