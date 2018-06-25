package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzh implements Creator<LoyaltyPointsBalance> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int zzd = zzbgm.zzd(parcel);
        int i = 0;
        double d = 0.0d;
        long j = 0;
        int i2 = -1;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    d = zzbgm.zzn(parcel, readInt);
                    break;
                case 5:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 7:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new LoyaltyPointsBalance(i, str2, d, str, j, i2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LoyaltyPointsBalance[i];
    }
}
