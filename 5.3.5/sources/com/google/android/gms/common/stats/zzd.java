package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import java.util.List;

@Hide
public final class zzd implements Creator<WakeLockEvent> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        int i = 0;
        long j = 0;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        List list = null;
        String str2 = null;
        long j2 = 0;
        int i4 = 0;
        String str3 = null;
        String str4 = null;
        float f = 0.0f;
        long j3 = 0;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 2:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 4:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    i3 = zzbgm.zzg(parcel, readInt);
                    break;
                case 6:
                    list = zzbgm.zzac(parcel, readInt);
                    break;
                case 8:
                    j2 = zzbgm.zzi(parcel, readInt);
                    break;
                case 10:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 11:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                case 12:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 13:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 14:
                    i4 = zzbgm.zzg(parcel, readInt);
                    break;
                case 15:
                    f = zzbgm.zzl(parcel, readInt);
                    break;
                case 16:
                    j3 = zzbgm.zzi(parcel, readInt);
                    break;
                case 17:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new WakeLockEvent(i, j, i2, str, i3, list, str2, j2, i4, str3, str4, f, j3, str5);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new WakeLockEvent[i];
    }
}
