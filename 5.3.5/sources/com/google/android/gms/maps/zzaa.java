package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;

@Hide
public final class zzaa implements Creator<GoogleMapOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbgm.zzd(parcel);
        byte b = (byte) -1;
        byte b2 = (byte) -1;
        int i = 0;
        CameraPosition cameraPosition = null;
        byte b3 = (byte) -1;
        byte b4 = (byte) -1;
        byte b5 = (byte) -1;
        byte b6 = (byte) -1;
        byte b7 = (byte) -1;
        byte b8 = (byte) -1;
        byte b9 = (byte) -1;
        byte b10 = (byte) -1;
        byte b11 = (byte) -1;
        Float f = null;
        Float f2 = null;
        LatLngBounds latLngBounds = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    b = zzbgm.zze(parcel, readInt);
                    break;
                case 3:
                    b2 = zzbgm.zze(parcel, readInt);
                    break;
                case 4:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 5:
                    cameraPosition = (CameraPosition) zzbgm.zza(parcel, readInt, CameraPosition.CREATOR);
                    break;
                case 6:
                    b3 = zzbgm.zze(parcel, readInt);
                    break;
                case 7:
                    b4 = zzbgm.zze(parcel, readInt);
                    break;
                case 8:
                    b5 = zzbgm.zze(parcel, readInt);
                    break;
                case 9:
                    b6 = zzbgm.zze(parcel, readInt);
                    break;
                case 10:
                    b7 = zzbgm.zze(parcel, readInt);
                    break;
                case 11:
                    b8 = zzbgm.zze(parcel, readInt);
                    break;
                case 12:
                    b9 = zzbgm.zze(parcel, readInt);
                    break;
                case 14:
                    b10 = zzbgm.zze(parcel, readInt);
                    break;
                case 15:
                    b11 = zzbgm.zze(parcel, readInt);
                    break;
                case 16:
                    f = zzbgm.zzm(parcel, readInt);
                    break;
                case 17:
                    f2 = zzbgm.zzm(parcel, readInt);
                    break;
                case 18:
                    latLngBounds = (LatLngBounds) zzbgm.zza(parcel, readInt, LatLngBounds.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new GoogleMapOptions(b, b2, i, cameraPosition, b3, b4, b5, b6, b7, b8, b9, b10, b11, f, f2, latLngBounds);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleMapOptions[i];
    }
}
