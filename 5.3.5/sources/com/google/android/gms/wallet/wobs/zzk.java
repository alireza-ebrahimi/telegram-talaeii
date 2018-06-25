package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzk implements Creator<TimeInterval> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        int zzd = zzbgm.zzd(parcel);
        long j2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    j2 = zzbgm.zzi(parcel, readInt);
                    break;
                case 3:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new TimeInterval(j2, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new TimeInterval[i];
    }
}
