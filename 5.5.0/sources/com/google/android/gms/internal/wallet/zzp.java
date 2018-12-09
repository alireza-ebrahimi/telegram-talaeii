package com.google.android.gms.internal.wallet;

import android.os.Bundle;
import android.os.Parcel;

public abstract class zzp extends zzb implements zzo {
    public zzp() {
        super("com.google.android.gms.wallet.fragment.internal.IWalletFragmentStateListener");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 2) {
            return false;
        }
        zza(parcel.readInt(), parcel.readInt(), (Bundle) zzc.zza(parcel, Bundle.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
