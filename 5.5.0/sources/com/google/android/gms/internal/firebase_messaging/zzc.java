package com.google.android.gms.internal.firebase_messaging;

import android.os.Parcel;
import android.os.Parcelable;

public class zzc {
    private static final ClassLoader zzd = zzc.class.getClassLoader();

    private zzc() {
    }

    public static void zza(Parcel parcel, Parcelable parcelable) {
        if (parcelable == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcelable.writeToParcel(parcel, 0);
    }
}
