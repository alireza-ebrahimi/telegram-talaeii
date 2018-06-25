package com.google.android.gms.security;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.zzf;
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener;

final class zza extends AsyncTask<Void, Void, Integer> {
    private /* synthetic */ Context val$context;
    private /* synthetic */ ProviderInstallListener zzklm;

    zza(Context context, ProviderInstallListener providerInstallListener) {
        this.val$context = context;
        this.zzklm = providerInstallListener;
    }

    private final Integer zzb(Void... voidArr) {
        try {
            ProviderInstaller.installIfNeeded(this.val$context);
            return Integer.valueOf(0);
        } catch (GooglePlayServicesRepairableException e) {
            return Integer.valueOf(e.getConnectionStatusCode());
        } catch (GooglePlayServicesNotAvailableException e2) {
            return Integer.valueOf(e2.errorCode);
        }
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        return zzb((Void[]) objArr);
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        Integer num = (Integer) obj;
        if (num.intValue() == 0) {
            this.zzklm.onProviderInstalled();
            return;
        }
        ProviderInstaller.zzklk;
        this.zzklm.onProviderInstallFailed(num.intValue(), zzf.zza(this.val$context, num.intValue(), "pi"));
    }
}
