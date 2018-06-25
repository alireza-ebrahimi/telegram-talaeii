package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public class CountrySpecification extends zzbgl implements ReflectedParcelable {
    public static final Creator<CountrySpecification> CREATOR = new zza();
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
