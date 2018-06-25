package com.google.android.gms.vision.barcode;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.vision.barcode.Barcode.PersonName;

@Hide
public final class zzj implements Creator<PersonName> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int zzd = zzbgm.zzd(parcel);
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str7 = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str6 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 8:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new PersonName(str7, str6, str5, str4, str3, str2, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PersonName[i];
    }
}
