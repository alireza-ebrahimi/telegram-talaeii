package com.google.android.gms.vision.barcode;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.vision.barcode.Barcode.CalendarDateTime;

@Hide
public final class zzd implements Creator<CalendarDateTime> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    i6 = zzbgm.zzg(parcel, readInt);
                    break;
                case 3:
                    i5 = zzbgm.zzg(parcel, readInt);
                    break;
                case 4:
                    i4 = zzbgm.zzg(parcel, readInt);
                    break;
                case 5:
                    i3 = zzbgm.zzg(parcel, readInt);
                    break;
                case 6:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                case 7:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 8:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 9:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new CalendarDateTime(i6, i5, i4, i3, i2, i, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CalendarDateTime[i];
    }
}
