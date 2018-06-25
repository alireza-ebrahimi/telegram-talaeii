package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzt implements Creator<LineItem> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int zzd = zzbgm.zzd(parcel);
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 7:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new LineItem(str5, str4, str3, str2, i, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LineItem[i];
    }
}
