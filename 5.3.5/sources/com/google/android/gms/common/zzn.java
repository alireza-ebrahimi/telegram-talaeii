package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzn extends zzbgl {
    public static final Creator<zzn> CREATOR = new zzo();
    private final String zzfri;
    private final zzh zzfrj;
    private final boolean zzfrk;

    zzn(String str, IBinder iBinder, boolean z) {
        this.zzfri = str;
        this.zzfrj = zzak(iBinder);
        this.zzfrk = z;
    }

    zzn(String str, zzh zzh, boolean z) {
        this.zzfri = str;
        this.zzfrj = zzh;
        this.zzfrk = z;
    }

    private static zzh zzak(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            zzh zzi;
            IObjectWrapper zzahg = zzau.zzam(iBinder).zzahg();
            byte[] bArr = zzahg == null ? null : (byte[]) com.google.android.gms.dynamic.zzn.zzy(zzahg);
            if (bArr != null) {
                zzi = new zzi(bArr);
            } else {
                Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
                zzi = null;
            }
            return zzi;
        } catch (Throwable e) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", e);
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder;
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzfri, false);
        if (this.zzfrj == null) {
            Log.w("GoogleCertificatesQuery", "certificate binder is null");
            iBinder = null;
        } else {
            iBinder = this.zzfrj.asBinder();
        }
        zzbgo.zza(parcel, 2, iBinder, false);
        zzbgo.zza(parcel, 3, this.zzfrk);
        zzbgo.zzai(parcel, zze);
    }
}
