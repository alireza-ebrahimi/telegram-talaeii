package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.C0489R;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzj;
import com.google.android.gms.common.util.zzz;
import java.util.concurrent.atomic.AtomicBoolean;
import org.telegram.customization.fetch.FetchService;

@Hide
public class zzs {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12211000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    @Hide
    private static boolean zzfrr = false;
    @Hide
    private static boolean zzfrs = false;
    private static boolean zzfrt = false;
    private static boolean zzfru = false;
    static final AtomicBoolean zzfrv = new AtomicBoolean();
    private static final AtomicBoolean zzfrw = new AtomicBoolean();

    zzs() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zzf.zzahf().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.getStatusString(i);
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return zzc(context, -1);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 9:
                return true;
            default:
                return false;
        }
    }

    private static int zza(Context context, boolean z, int i, int i2) {
        boolean z2 = i2 == -1 || i2 >= 0;
        zzbq.checkArgument(z2);
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        if (z) {
            try {
                packageInfo = packageManager.getPackageInfo("com.android.vending", 8256);
            } catch (NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
                return 9;
            }
        }
        try {
            PackageInfo packageInfo2 = packageManager.getPackageInfo("com.google.android.gms", 64);
            zzt.zzcj(context);
            if (!zzt.zza(packageInfo2, true)) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            } else if (!z || (zzt.zza(r0, true) && r0.signatures[0].equals(packageInfo2.signatures[0]))) {
                int i3 = packageInfo2.versionCode / 1000;
                if (i3 >= i / 1000 || (i2 != -1 && i3 >= i2 / 1000)) {
                    ApplicationInfo applicationInfo = packageInfo2.applicationInfo;
                    if (applicationInfo == null) {
                        try {
                            applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
                        } catch (Throwable e2) {
                            Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", e2);
                            return 1;
                        }
                    }
                    return !applicationInfo.enabled ? 3 : 0;
                } else {
                    Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + packageInfo2.versionCode);
                    return 2;
                }
            } else {
                Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                return 9;
            }
        } catch (NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @Hide
    @TargetApi(19)
    @Deprecated
    public static boolean zzb(Context context, int i, String str) {
        return zzz.zzb(context, i, str);
    }

    @Hide
    @Deprecated
    public static void zzbo(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzf.zzahf();
        int zzc = zzf.zzc(context, -1);
        if (zzc != 0) {
            zzf.zzahf();
            Intent zza = zzf.zza(context, zzc, "e");
            Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + zzc);
            if (zza == null) {
                throw new GooglePlayServicesNotAvailableException(zzc);
            }
            throw new GooglePlayServicesRepairableException(zzc, "Google Play Services not available", zza);
        }
    }

    @Deprecated
    public static int zzc(Context context, int i) {
        try {
            context.getResources().getString(C0489R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!("com.google.android.gms".equals(context.getPackageName()) || zzfrw.get())) {
            int zzcs = zzbf.zzcs(context);
            if (zzcs == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            } else if (zzcs != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                throw new IllegalStateException(new StringBuilder(FetchService.ACTION_LOGGING).append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ").append(GOOGLE_PLAY_SERVICES_VERSION_CODE).append(" but found ").append(zzcs).append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />").toString());
            }
        }
        boolean z = (zzj.zzcv(context) || zzj.zzcx(context)) ? false : true;
        return zza(context, z, GOOGLE_PLAY_SERVICES_VERSION_CODE, i);
    }

    @Hide
    @Deprecated
    public static void zzcf(Context context) {
        if (!zzfrv.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME);
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException e) {
            }
        }
    }

    @Hide
    @Deprecated
    public static int zzcg(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return i;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.internal.Hide
    public static boolean zzci(android.content.Context r5) {
        /*
        r0 = 0;
        r1 = 1;
        r2 = zzfru;
        if (r2 != 0) goto L_0x002b;
    L_0x0006:
        r2 = com.google.android.gms.internal.zzbih.zzdd(r5);	 Catch:{ NameNotFoundException -> 0x0040 }
        r3 = "com.google.android.gms";
        r4 = 64;
        r2 = r2.getPackageInfo(r3, r4);	 Catch:{ NameNotFoundException -> 0x0040 }
        com.google.android.gms.common.zzt.zzcj(r5);	 Catch:{ NameNotFoundException -> 0x0040 }
        if (r2 == 0) goto L_0x003c;
    L_0x0018:
        r3 = 0;
        r3 = com.google.android.gms.common.zzt.zza(r2, r3);	 Catch:{ NameNotFoundException -> 0x0040 }
        if (r3 != 0) goto L_0x003c;
    L_0x001f:
        r3 = 1;
        r2 = com.google.android.gms.common.zzt.zza(r2, r3);	 Catch:{ NameNotFoundException -> 0x0040 }
        if (r2 == 0) goto L_0x003c;
    L_0x0026:
        r2 = 1;
        zzfrt = r2;	 Catch:{ NameNotFoundException -> 0x0040 }
    L_0x0029:
        zzfru = r1;
    L_0x002b:
        r2 = zzfrt;
        if (r2 != 0) goto L_0x003a;
    L_0x002f:
        r2 = "user";
        r3 = android.os.Build.TYPE;
        r2 = r2.equals(r3);
        if (r2 != 0) goto L_0x003b;
    L_0x003a:
        r0 = r1;
    L_0x003b:
        return r0;
    L_0x003c:
        r2 = 0;
        zzfrt = r2;	 Catch:{ NameNotFoundException -> 0x0040 }
        goto L_0x0029;
    L_0x0040:
        r2 = move-exception;
        r3 = "GooglePlayServicesUtil";
        r4 = "Cannot find Google Play services package name.";
        android.util.Log.w(r3, r4, r2);	 Catch:{ all -> 0x004d }
        zzfru = r1;
        goto L_0x002b;
    L_0x004d:
        r0 = move-exception;
        zzfru = r1;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzs.zzci(android.content.Context):boolean");
    }

    @Hide
    @Deprecated
    public static boolean zzd(Context context, int i) {
        return i == 18 ? true : i == 1 ? zzr(context, "com.google.android.gms") : false;
    }

    @Hide
    @Deprecated
    public static boolean zze(Context context, int i) {
        return zzz.zze(context, i);
    }

    @TargetApi(21)
    static boolean zzr(Context context, String str) {
        boolean equals = str.equals("com.google.android.gms");
        if (com.google.android.gms.common.util.zzs.zzanx()) {
            try {
                for (SessionInfo appPackageName : context.getPackageManager().getPackageInstaller().getAllSessions()) {
                    if (str.equals(appPackageName.getAppPackageName())) {
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 8192);
            if (equals) {
                return applicationInfo.enabled;
            }
            if (applicationInfo.enabled) {
                Object obj;
                if (com.google.android.gms.common.util.zzs.zzanu()) {
                    Bundle applicationRestrictions = ((UserManager) context.getSystemService("user")).getApplicationRestrictions(context.getPackageName());
                    if (applicationRestrictions != null && "true".equals(applicationRestrictions.getString("restricted_profile"))) {
                        obj = 1;
                        if (obj == null) {
                            return true;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException e2) {
            return false;
        }
    }
}
