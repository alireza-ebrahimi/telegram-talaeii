package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewSource;

@Hide
public final class zzai implements Creator<StreetViewPanoramaOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        StreetViewSource streetViewSource = null;
        byte b = (byte) 0;
        int zzd = zzbgm.zzd(parcel);
        byte b2 = (byte) 0;
        byte b3 = (byte) 0;
        byte b4 = (byte) 0;
        byte b5 = (byte) 0;
        Integer num = null;
        LatLng latLng = null;
        String str = null;
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    streetViewPanoramaCamera = (StreetViewPanoramaCamera) zzbgm.zza(parcel, readInt, StreetViewPanoramaCamera.CREATOR);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    latLng = (LatLng) zzbgm.zza(parcel, readInt, LatLng.CREATOR);
                    break;
                case 5:
                    num = zzbgm.zzh(parcel, readInt);
                    break;
                case 6:
                    b5 = zzbgm.zze(parcel, readInt);
                    break;
                case 7:
                    b4 = zzbgm.zze(parcel, readInt);
                    break;
                case 8:
                    b3 = zzbgm.zze(parcel, readInt);
                    break;
                case 9:
                    b2 = zzbgm.zze(parcel, readInt);
                    break;
                case 10:
                    b = zzbgm.zze(parcel, readInt);
                    break;
                case 11:
                    streetViewSource = (StreetViewSource) zzbgm.zza(parcel, readInt, StreetViewSource.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zzd);
        return new StreetViewPanoramaOptions(streetViewPanoramaCamera, str, latLng, num, b5, b4, b3, b2, b, streetViewSource);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new StreetViewPanoramaOptions[i];
    }
}
