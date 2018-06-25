package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzar extends zzbgl {
    @Hide
    public static final Creator<zzar> CREATOR = new zzas();
    private String zzloh;

    private zzar() {
    }

    zzar(String str) {
        this.zzloh = str;
    }

    @Hide
    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzloh, false);
        zzbgo.zzai(parcel, zze);
    }
}
