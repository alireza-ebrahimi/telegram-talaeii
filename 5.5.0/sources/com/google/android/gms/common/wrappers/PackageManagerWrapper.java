package com.google.android.gms.common.wrappers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Process;
import android.support.v4.content.C0428o;
import android.support.v4.p022f.C0477i;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;

public class PackageManagerWrapper {
    private final Context zzjp;

    public PackageManagerWrapper(Context context) {
        this.zzjp = context;
    }

    public boolean allowApiAccess(String str, int i) {
        return true;
    }

    public void checkCallerIsNotInstantApp() {
        if (isCallerInstantApp()) {
            throw new SecurityException("This operation is not supported for instant apps.");
        }
    }

    public int checkCallingOrSelfPermission(String str) {
        return this.zzjp.checkCallingOrSelfPermission(str);
    }

    public int checkCallingOrSelfPermissionAndAppOps(String str) {
        return C0428o.m1909b(this.zzjp, str);
    }

    public int checkCallingPermission(String str) {
        return this.zzjp.checkCallingPermission(str);
    }

    @Deprecated
    public int checkCallingPermission(String str, String str2) {
        return checkPermission(str, str2);
    }

    public int checkCallingPermissionAndAppOps(String str, String str2) {
        return C0428o.m1908a(this.zzjp, str, str2);
    }

    public void checkPackage(int i, String str) {
        if (!uidHasPackageName(i, str)) {
            throw new SecurityException(new StringBuilder(String.valueOf(str).length() + 39).append("Package ").append(str).append(" does not belong to ").append(i).toString());
        }
    }

    public int checkPermission(String str, int i, int i2) {
        return this.zzjp.checkPermission(str, i, i2);
    }

    @Deprecated
    public int checkPermission(String str, int i, int i2, String str2) {
        return checkPermission(str, i, i2);
    }

    public int checkPermission(String str, String str2) {
        return this.zzjp.getPackageManager().checkPermission(str, str2);
    }

    public int checkPermissionAndAppOps(String str, int i, int i2, String str2) {
        return C0428o.m1907a(this.zzjp, str, i, i2, str2);
    }

    public ApplicationInfo getApplicationInfo(String str, int i) {
        return this.zzjp.getPackageManager().getApplicationInfo(str, i);
    }

    public CharSequence getApplicationLabel(String str) {
        return this.zzjp.getPackageManager().getApplicationLabel(this.zzjp.getPackageManager().getApplicationInfo(str, 0));
    }

    public C0477i<CharSequence, Drawable> getApplicationLabelAndIcon(String str) {
        ApplicationInfo applicationInfo = this.zzjp.getPackageManager().getApplicationInfo(str, 0);
        return C0477i.m2029a(this.zzjp.getPackageManager().getApplicationLabel(applicationInfo), this.zzjp.getPackageManager().getApplicationIcon(applicationInfo));
    }

    public ComponentName getCallingActivity(Activity activity) {
        return activity.getCallingActivity();
    }

    public String getCallingPackage(Activity activity) {
        ComponentName callingActivity = activity.getCallingActivity();
        if (callingActivity == null) {
            return null;
        }
        String packageName = callingActivity.getPackageName();
        if (packageName != null) {
            return packageName;
        }
        String valueOf = String.valueOf(callingActivity);
        Log.e("PackageManagerWrapper", new StringBuilder(String.valueOf(valueOf).length() + 54).append("getCallingPackage(): Couldn't get a package name from ").append(valueOf).toString());
        return null;
    }

    protected Context getContext() {
        return this.zzjp;
    }

    public PackageInfo getPackageInfo(String str, int i) {
        return this.zzjp.getPackageManager().getPackageInfo(str, i);
    }

    public String[] getPackagesForUid(int i) {
        return this.zzjp.getPackageManager().getPackagesForUid(i);
    }

    public boolean isCallerInstantApp() {
        if (Binder.getCallingUid() == Process.myUid()) {
            return InstantApps.isInstantApp(this.zzjp);
        }
        if (PlatformVersion.isAtLeastO()) {
            String nameForUid = this.zzjp.getPackageManager().getNameForUid(Binder.getCallingUid());
            if (nameForUid != null) {
                return this.zzjp.getPackageManager().isInstantApp(nameForUid);
            }
        }
        return false;
    }

    public boolean isInstantAppUid(int i) {
        return false;
    }

    @TargetApi(19)
    public boolean uidHasPackageName(int i, String str) {
        if (PlatformVersion.isAtLeastKitKat()) {
            try {
                ((AppOpsManager) this.zzjp.getSystemService("appops")).checkPackage(i, str);
                return true;
            } catch (SecurityException e) {
                return false;
            }
        }
        String[] packagesForUid = this.zzjp.getPackageManager().getPackagesForUid(i);
        if (str == null || packagesForUid == null) {
            return false;
        }
        for (Object equals : packagesForUid) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }
}
