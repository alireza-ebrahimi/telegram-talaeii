package com.google.android.gms.internal.wallet;

import android.os.IBinder;
import android.os.IInterface;

public abstract class zzm extends zzb implements zzl {
    public static zzl zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.fragment.internal.IWalletFragmentDelegate");
        return queryLocalInterface instanceof zzl ? (zzl) queryLocalInterface : new zzn(iBinder);
    }
}
