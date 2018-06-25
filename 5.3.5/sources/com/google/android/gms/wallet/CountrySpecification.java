package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Deprecated
public class CountrySpecification extends zzbgl {
    public static final Creator<CountrySpecification> CREATOR = new zzh();
    private String zzcyf;

    public CountrySpecification(String str) {
        this.zzcyf = str;
    }

    public String getCountryCode() {
        return this.zzcyf;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzcyf, false);
        zzbgo.zzai(parcel, zze);
    }
}
