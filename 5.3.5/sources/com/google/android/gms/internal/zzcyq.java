package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

public abstract class zzcyq extends zzew implements zzcyp {
    public zzcyq() {
        attachInterface(this, "com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 3:
                zza((ConnectionResult) zzex.zza(parcel, ConnectionResult.CREATOR), (zzcym) zzex.zza(parcel, zzcym.CREATOR));
                break;
            case 4:
                zzas((Status) zzex.zza(parcel, Status.CREATOR));
                break;
            case 6:
                zzat((Status) zzex.zza(parcel, Status.CREATOR));
                break;
            case 7:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (GoogleSignInAccount) zzex.zza(parcel, GoogleSignInAccount.CREATOR));
                break;
            case 8:
                zzb((zzcyw) zzex.zza(parcel, zzcyw.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
