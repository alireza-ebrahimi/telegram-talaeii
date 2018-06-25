package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class LabelValue extends zzbgl {
    public static final Creator<LabelValue> CREATOR = new zzc();
    private String label;
    private String value;

    LabelValue() {
    }

    public LabelValue(String str, String str2) {
        this.label = str;
        this.value = str2;
    }

    public final String getLabel() {
        return this.label;
    }

    public final String getValue() {
        return this.value;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.label, false);
        zzbgo.zza(parcel, 3, this.value, false);
        zzbgo.zzai(parcel, zze);
    }
}
