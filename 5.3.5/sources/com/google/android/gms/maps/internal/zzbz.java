package com.google.android.gms.maps.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.maps.model.RuntimeRemoteException;

@Hide
public class zzbz {
    private static final String TAG = zzbz.class.getSimpleName();
    @Nullable
    @SuppressLint({"StaticFieldLeak"})
    private static Context zzjcs = null;
    private static zze zzjct;

    private static <T> T zza(ClassLoader classLoader, String str) {
        try {
            return zzd(((ClassLoader) zzbq.checkNotNull(classLoader)).loadClass(str));
        } catch (ClassNotFoundException e) {
            String str2 = "Unable to find dynamic class ";
            String valueOf = String.valueOf(str);
            throw new IllegalStateException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
    }

    private static <T> T zzd(Class<?> cls) {
        String str;
        String valueOf;
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            str = "Unable to instantiate the dynamic class ";
            valueOf = String.valueOf(cls.getName());
            throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        } catch (IllegalAccessException e2) {
            str = "Unable to call the default constructor of ";
            valueOf = String.valueOf(cls.getName());
            throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    public static zze zzdz(Context context) throws GooglePlayServicesNotAvailableException {
        zzbq.checkNotNull(context);
        if (zzjct != null) {
            return zzjct;
        }
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (isGooglePlayServicesAvailable) {
            case 0:
                zze zze;
                Log.i(TAG, "Making Creator dynamically");
                IBinder iBinder = (IBinder) zza(zzea(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl");
                if (iBinder == null) {
                    zze = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
                    if (queryLocalInterface instanceof zze) {
                        zze = (zze) queryLocalInterface;
                    } else {
                        Object zzf = new zzf(iBinder);
                    }
                }
                zzjct = zze;
                try {
                    zzjct.zzi(zzn.zzz(zzea(context).getResources()), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
                    return zzjct;
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            default:
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
    }

    @Nullable
    private static Context zzea(Context context) {
        if (zzjcs != null) {
            return zzjcs;
        }
        Context zzeb = zzeb(context);
        zzjcs = zzeb;
        return zzeb;
    }

    @Nullable
    private static Context zzeb(Context context) {
        try {
            return DynamiteModule.zza(context, DynamiteModule.zzhdi, "com.google.android.gms.maps_dynamite").zzarl();
        } catch (Throwable th) {
            Log.e(TAG, "Failed to load maps module, use legacy", th);
            return GooglePlayServicesUtil.getRemoteContext(context);
        }
    }
}
