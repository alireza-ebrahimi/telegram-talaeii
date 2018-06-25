package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class UriData extends zzbgl {
    public static final Creator<UriData> CREATOR = new zzl();
    private String description;
    private String zzefk;

    UriData() {
    }

    public UriData(String str, String str2) {
        this.zzefk = str;
        this.description = str2;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getUri() {
        return this.zzefk;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzefk, false);
        zzbgo.zza(parcel, 3, this.description, false);
        zzbgo.zzai(parcel, zze);
    }
}
