package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzd extends zzbgl {
    public static final Creator<zzd> CREATOR = new zze();
    private zzem zzlsa;
    private IntentFilter[] zzlsb;
    private String zzlsc;
    private String zzlsd;

    @Hide
    zzd(IBinder iBinder, IntentFilter[] intentFilterArr, String str, String str2) {
        zzem zzem = null;
        if (iBinder != null) {
            if (iBinder != null) {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableListener");
                zzem = queryLocalInterface instanceof zzem ? (zzem) queryLocalInterface : new zzeo(iBinder);
            }
            this.zzlsa = zzem;
        } else {
            this.zzlsa = null;
        }
        this.zzlsb = intentFilterArr;
        this.zzlsc = str;
        this.zzlsd = str2;
    }

    public zzd(zzhk zzhk) {
        this.zzlsa = zzhk;
        this.zzlsb = zzhk.zzblz();
        this.zzlsc = zzhk.zzbma();
        this.zzlsd = null;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlsa == null ? null : this.zzlsa.asBinder(), false);
        zzbgo.zza(parcel, 3, this.zzlsb, i, false);
        zzbgo.zza(parcel, 4, this.zzlsc, false);
        zzbgo.zza(parcel, 5, this.zzlsd, false);
        zzbgo.zzai(parcel, zze);
    }
}
