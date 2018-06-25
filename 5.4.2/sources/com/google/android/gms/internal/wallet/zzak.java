package com.google.android.gms.internal.wallet;

import android.app.Activity;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.RemoteCreator;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public final class zzak extends RemoteCreator<zzs> {
    private static zzak zzgl;

    protected zzak() {
        super("com.google.android.gms.wallet.dynamite.WalletDynamiteCreatorImpl");
    }

    public static zzl zza(Activity activity, IFragmentWrapper iFragmentWrapper, WalletFragmentOptions walletFragmentOptions, zzo zzo) {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(activity, 12451000);
        if (isGooglePlayServicesAvailable != 0) {
            throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
        try {
            if (zzgl == null) {
                zzgl = new zzak();
            }
            return ((zzs) zzgl.getRemoteCreatorInstance(activity)).zza(ObjectWrapper.wrap(activity), iFragmentWrapper, walletFragmentOptions, zzo);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    protected final /* synthetic */ Object getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
        return queryLocalInterface instanceof zzs ? (zzs) queryLocalInterface : new zzt(iBinder);
    }
}
