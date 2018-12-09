package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final Object sLock = new Object();
    private static final GoogleApiAvailabilityLight zzacw = GoogleApiAvailabilityLight.getInstance();
    private static Method zzacx = null;

    public interface ProviderInstallListener {
        void onProviderInstallFailed(int i, Intent intent);

        void onProviderInstalled();
    }

    public static void installIfNeeded(Context context) {
        Preconditions.checkNotNull(context, "Context must not be null");
        zzacw.verifyGooglePlayServicesIsAvailable(context, 11925000);
        try {
            Context remoteContext = GooglePlayServicesUtilLight.getRemoteContext(context);
            if (remoteContext == null) {
                if (Log.isLoggable("ProviderInstaller", 6)) {
                    Log.e("ProviderInstaller", "Failed to get remote context");
                }
                throw new GooglePlayServicesNotAvailableException(8);
            }
            synchronized (sLock) {
                try {
                    if (zzacx == null) {
                        zzacx = remoteContext.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", new Class[]{Context.class});
                    }
                    zzacx.invoke(null, new Object[]{remoteContext});
                } catch (Exception e) {
                    Throwable cause = e.getCause();
                    if (Log.isLoggable("ProviderInstaller", 6)) {
                        Object message = cause == null ? e.getMessage() : cause.getMessage();
                        String str = "ProviderInstaller";
                        String str2 = "Failed to install provider: ";
                        String valueOf = String.valueOf(message);
                        Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    }
                    throw new GooglePlayServicesNotAvailableException(8);
                }
            }
        } catch (NotFoundException e2) {
            if (Log.isLoggable("ProviderInstaller", 6)) {
                Log.e("ProviderInstaller", "Failed to get remote context - resource not found");
            }
            throw new GooglePlayServicesNotAvailableException(8);
        }
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener providerInstallListener) {
        Preconditions.checkNotNull(context, "Context must not be null");
        Preconditions.checkNotNull(providerInstallListener, "Listener must not be null");
        Preconditions.checkMainThread("Must be called on the UI thread");
        new zza(context, providerInstallListener).execute(new Void[0]);
    }
}
