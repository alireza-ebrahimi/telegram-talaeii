package com.google.android.gms.internal;

import android.app.Activity;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.zzk;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

@Hide
public final class zzdnc extends zzp<zzdmk> {
    private static zzdnc zzlps;

    protected zzdnc() {
        super("com.google.android.gms.wallet.dynamite.WalletDynamiteCreatorImpl");
    }

    public static zzdmd zza(Activity activity, zzk zzk, WalletFragmentOptions walletFragmentOptions, zzdmg zzdmg) throws GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (isGooglePlayServicesAvailable != 0) {
            throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
        try {
            if (zzlps == null) {
                zzlps = new zzdnc();
            }
            return ((zzdmk) zzlps.zzdg(activity)).zza(zzn.zzz(activity), zzk, walletFragmentOptions, zzdmg);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    protected final /* synthetic */ Object zze(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
        return queryLocalInterface instanceof zzdmk ? (zzdmk) queryLocalInterface : new zzdml(iBinder);
    }
}
