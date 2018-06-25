package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzo extends zzbgl {
    public static final Creator<zzo> CREATOR = new zzn();
    private Bundle mBundle;
    private int versionCode;
    private int zzenu;

    zzo(int i, int i2, Bundle bundle) {
        this.versionCode = i;
        this.zzenu = i2;
        this.mBundle = bundle;
    }

    public zzo(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
        this(1, googleSignInOptionsExtension.getExtensionType(), googleSignInOptionsExtension.toBundle());
    }

    public final int getType() {
        return this.zzenu;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.versionCode);
        zzbgo.zzc(parcel, 2, this.zzenu);
        zzbgo.zza(parcel, 3, this.mBundle, false);
        zzbgo.zzai(parcel, zze);
    }
}
