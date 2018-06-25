package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

@Hide
public final class zzb implements Creator<CommonWalletObject> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        int i = 0;
        ArrayList arrayList = new ArrayList();
        TimeInterval timeInterval = null;
        ArrayList arrayList2 = new ArrayList();
        String str9 = null;
        String str10 = null;
        ArrayList arrayList3 = new ArrayList();
        boolean z = false;
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    str5 = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    str6 = zzbgm.zzq(parcel, readInt);
                    break;
                case 8:
                    str7 = zzbgm.zzq(parcel, readInt);
                    break;
                case 9:
                    str8 = zzbgm.zzq(parcel, readInt);
                    break;
                case 10:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 11:
                    arrayList = zzbgm.zzc(parcel, readInt, WalletObjectMessage.CREATOR);
                    break;
                case 12:
                    timeInterval = (TimeInterval) zzbgm.zza(parcel, readInt, TimeInterval.CREATOR);
                    break;
                case 13:
                    arrayList2 = zzbgm.zzc(parcel, readInt, LatLng.CREATOR);
                    break;
                case 14:
                    str9 = zzbgm.zzq(parcel, readInt);
                    break;
                case 15:
                    str10 = zzbgm.zzq(parcel, readInt);
                    break;
                case 16:
                    arrayList3 = zzbgm.zzc(parcel, readInt, LabelValueRow.CREATOR);
                    break;
                case 17:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 18:
                    arrayList4 = zzbgm.zzc(parcel, readInt, UriData.CREATOR);
                    break;
                case 19:
                    arrayList5 = zzbgm.zzc(parcel, readInt, TextModuleData.CREATOR);
                    break;
                case 20:
                    arrayList6 = zzbgm.zzc(parcel, readInt, UriData.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new CommonWalletObject(str, str2, str3, str4, str5, str6, str7, str8, i, arrayList, timeInterval, arrayList2, str9, str10, arrayList3, z, arrayList4, arrayList5, arrayList6);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CommonWalletObject[i];
    }
}
