package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class TextModuleData extends zzbgl {
    public static final Creator<TextModuleData> CREATOR = new zzj();
    private String body;
    private String zzlqg;

    TextModuleData() {
    }

    public TextModuleData(String str, String str2) {
        this.zzlqg = str;
        this.body = str2;
    }

    public final String getBody() {
        return this.body;
    }

    public final String getHeader() {
        return this.zzlqg;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlqg, false);
        zzbgo.zza(parcel, 3, this.body, false);
        zzbgo.zzai(parcel, zze);
    }
}
