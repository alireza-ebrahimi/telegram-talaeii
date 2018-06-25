package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.zzar;

public abstract class zzdmn extends zzew implements zzdmm {
    public zzdmn() {
        attachInterface(this, "com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza(parcel.readInt(), (MaskedWallet) zzex.zza(parcel, MaskedWallet.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 2:
                zza(parcel.readInt(), (FullWallet) zzex.zza(parcel, FullWallet.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 3:
                zza(parcel.readInt(), zzex.zza(parcel), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 4:
                zzl(parcel.readInt(), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 6:
                zzb(parcel.readInt(), zzex.zza(parcel), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 7:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zzdlx) zzex.zza(parcel, zzdlx.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 8:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 9:
                zza((Status) zzex.zza(parcel, Status.CREATOR), zzex.zza(parcel), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 10:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zzdlz) zzex.zza(parcel, zzdlz.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 11:
                zzb((Status) zzex.zza(parcel, Status.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 12:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zzar) zzex.zza(parcel, zzar.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 13:
                zzc((Status) zzex.zza(parcel, Status.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 14:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (PaymentData) zzex.zza(parcel, PaymentData.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            case 15:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zzdmb) zzex.zza(parcel, zzdmb.CREATOR), (Bundle) zzex.zza(parcel, Bundle.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
