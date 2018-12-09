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
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.internal.MetadataValueReader;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.GmsVersionParser;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import org.telegram.messenger.MessagesController;

public class GooglePlayServicesUtilLight {
    public static final String FEATURE_SIDEWINDER = "cn.google";
    public static final String GOOGLE_PLAY_GAMES_PACKAGE = "com.google.android.play.games";
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12451000;
    public static final String GOOGLE_PLAY_STORE_GAMES_URI_STRING = "http://play.google.com/store/apps/category/GAME";
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    public static final String GOOGLE_PLAY_STORE_URI_STRING = "http://play.google.com/store/apps/";
    public static final boolean HONOR_DEBUG_CERTIFICATES = false;
    public static final String KEY_METADATA_GOOGLE_PLAY_SERVICES_VERSION = "com.google.android.gms.version";
    public static final int MAX_ATTEMPTS_NO_SUCH_ALGORITHM = 2;
    @VisibleForTesting
    public static boolean sIsTestMode = false;
    @VisibleForTesting
    public static boolean sTestIsUserBuild = false;
    private static boolean zzbr = false;
    @VisibleForTesting
    private static boolean zzbs = false;
    @VisibleForTesting
    static final AtomicBoolean zzbt = new AtomicBoolean();
    private static final AtomicBoolean zzbu = new AtomicBoolean();

    GooglePlayServicesUtilLight() {
    }

    @Deprecated
    public static void cancelAvailabilityErrorNotifications(Context context) {
        if (!zzbt.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException e) {
            }
        }
    }

    public static void enableUsingApkIndependentContext() {
        zzbu.set(true);
    }

    @Deprecated
    public static void ensurePlayServicesAvailable(Context context) {
        ensurePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    @Deprecated
    public static void ensurePlayServicesAvailable(Context context, int i) {
        int isGooglePlayServicesAvailable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, i);
        if (isGooglePlayServicesAvailable != 0) {
            Intent errorResolutionIntent = GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(context, isGooglePlayServicesAvailable, "e");
            Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + isGooglePlayServicesAvailable);
            if (errorResolutionIntent == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", errorResolutionIntent);
        }
    }

    @Deprecated
    public static int getApkVersion(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return i;
        }
    }

    @Deprecated
    public static int getClientVersion(Context context) {
        Preconditions.checkState(true);
        return ClientLibraryUtils.getClientVersion(context, context.getPackageName());
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    @VisibleForTesting
    public static String getErrorString(int i) {
        return ConnectionResult.zza(i);
    }

    @Deprecated
    public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(int i) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(null, i, null);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        InputStream openInputStream;
        try {
            openInputStream = context.getContentResolver().openInputStream(new Builder().scheme("android.resource").authority("com.google.android.gms").appendPath("raw").appendPath("oss_notice").build());
            String next = new Scanner(openInputStream).useDelimiter("\\A").next();
            if (openInputStream == null) {
                return next;
            }
            openInputStream.close();
            return next;
        } catch (NoSuchElementException e) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            return null;
        } catch (Exception e2) {
            return null;
        } catch (Throwable th) {
            if (openInputStream != null) {
                openInputStream.close();
            }
        }
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

    public static boolean honorsDebugCertificates(Context context) {
        return isTestKeysBuild(context) || !isUserBuildDevice();
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context, int i) {
        try {
            context.getResources().getString(C1789R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!("com.google.android.gms".equals(context.getPackageName()) || zzbu.get())) {
            int googlePlayServicesVersion = MetadataValueReader.getGooglePlayServicesVersion(context);
            if (googlePlayServicesVersion == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            } else if (googlePlayServicesVersion != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                throw new IllegalStateException("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + googlePlayServicesVersion + ".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            }
        }
        boolean z = (DeviceProperties.isWearableWithoutPlayStore(context) || DeviceProperties.isIoT(context)) ? false : true;
        return zza(context, z, i);
    }

    @Deprecated
    public static boolean isGooglePlayServicesUid(Context context, int i) {
        return UidVerifier.isGooglePlayServicesUid(context, i);
    }

    @Deprecated
    public static boolean isPlayServicesPossiblyUpdating(Context context, int i) {
        return i == 18 ? true : i == 1 ? isUninstalledAppPossiblyUpdating(context, "com.google.android.gms") : false;
    }

    @Deprecated
    public static boolean isPlayStorePossiblyUpdating(Context context, int i) {
        return i == 9 ? isUninstalledAppPossiblyUpdating(context, "com.android.vending") : false;
    }

    @TargetApi(18)
    public static boolean isRestrictedUserProfile(Context context) {
        if (PlatformVersion.isAtLeastJellyBeanMR2()) {
            Bundle applicationRestrictions = ((UserManager) context.getSystemService("user")).getApplicationRestrictions(context.getPackageName());
            if (applicationRestrictions != null && "true".equals(applicationRestrictions.getString("restricted_profile"))) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    @VisibleForTesting
    public static boolean isSidewinderDevice(Context context) {
        return DeviceProperties.isSidewinder(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isTestKeysBuild(android.content.Context r4) {
        /*
        r3 = 1;
        r0 = zzbs;
        if (r0 != 0) goto L_0x002b;
    L_0x0005:
        r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r4);	 Catch:{ NameNotFoundException -> 0x0032 }
        r1 = "com.google.android.gms";
        r2 = 64;
        r0 = r0.getPackageInfo(r1, r2);	 Catch:{ NameNotFoundException -> 0x0032 }
        r1 = com.google.android.gms.common.GoogleSignatureVerifier.getInstance(r4);	 Catch:{ NameNotFoundException -> 0x0032 }
        if (r0 == 0) goto L_0x002e;
    L_0x0018:
        r2 = 0;
        r2 = r1.isGooglePublicSignedPackage(r0, r2);	 Catch:{ NameNotFoundException -> 0x0032 }
        if (r2 != 0) goto L_0x002e;
    L_0x001f:
        r2 = 1;
        r0 = r1.isGooglePublicSignedPackage(r0, r2);	 Catch:{ NameNotFoundException -> 0x0032 }
        if (r0 == 0) goto L_0x002e;
    L_0x0026:
        r0 = 1;
        zzbr = r0;	 Catch:{ NameNotFoundException -> 0x0032 }
    L_0x0029:
        zzbs = r3;
    L_0x002b:
        r0 = zzbr;
        return r0;
    L_0x002e:
        r0 = 0;
        zzbr = r0;	 Catch:{ NameNotFoundException -> 0x0032 }
        goto L_0x0029;
    L_0x0032:
        r0 = move-exception;
        r1 = "GooglePlayServicesUtil";
        r2 = "Cannot find Google Play services package name.";
        android.util.Log.w(r1, r2, r0);	 Catch:{ all -> 0x003f }
        zzbs = r3;
        goto L_0x002b;
    L_0x003f:
        r0 = move-exception;
        zzbs = r3;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GooglePlayServicesUtilLight.isTestKeysBuild(android.content.Context):boolean");
    }

    @TargetApi(21)
    static boolean isUninstalledAppPossiblyUpdating(Context context, String str) {
        boolean equals = str.equals("com.google.android.gms");
        if (PlatformVersion.isAtLeastLollipop()) {
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
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, MessagesController.UPDATE_MASK_CHANNEL);
            return equals ? applicationInfo.enabled : applicationInfo.enabled && !isRestrictedUserProfile(context);
        } catch (NameNotFoundException e2) {
            return false;
        }
    }

    @Deprecated
    public static boolean isUserBuildDevice() {
        return DeviceProperties.isUserBuild();
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

    @TargetApi(19)
    @Deprecated
    public static boolean uidHasPackageName(Context context, int i, String str) {
        return UidVerifier.uidHasPackageName(context, i, str);
    }

    @VisibleForTesting
    private static int zza(Context context, boolean z, int i) {
        Preconditions.checkArgument(i >= 0);
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
            GoogleSignatureVerifier instance = GoogleSignatureVerifier.getInstance(context);
            if (!instance.isGooglePublicSignedPackage(packageInfo2, true)) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            } else if (z && (!instance.isGooglePublicSignedPackage(r0, true) || !r0.signatures[0].equals(packageInfo2.signatures[0]))) {
                Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                return 9;
            } else if (GmsVersionParser.parseBuildVersion(packageInfo2.versionCode) < GmsVersionParser.parseBuildVersion(i)) {
                Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires " + i + " but found " + packageInfo2.versionCode);
                return 2;
            } else {
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
            }
        } catch (NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }
}
