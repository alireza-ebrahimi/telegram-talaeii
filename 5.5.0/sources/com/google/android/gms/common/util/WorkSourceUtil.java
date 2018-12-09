package com.google.android.gms.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.os.WorkSource;
import android.util.Log;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkSourceUtil {
    public static final String TAG = "WorkSourceUtil";
    private static final int zzaam = Process.myUid();
    private static final Method zzaan = zzdf();
    private static final Method zzaao = zzdg();
    private static final Method zzaap = zzdh();
    private static final Method zzaaq = zzdi();
    private static final Method zzaar = zzdj();
    private static final Method zzaas = zzdk();
    private static final Method zzaat = zzdl();

    private WorkSourceUtil() {
    }

    public static void add(WorkSource workSource, int i, String str) {
        if (zzaao != null) {
            if (str == null) {
                str = TtmlNode.ANONYMOUS_REGION_ID;
            }
            try {
                zzaao.invoke(workSource, new Object[]{Integer.valueOf(i), str});
            } catch (Throwable e) {
                Log.wtf(TAG, "Unable to assign blame through WorkSource", e);
            }
        } else if (zzaan != null) {
            try {
                zzaan.invoke(workSource, new Object[]{Integer.valueOf(i)});
            } catch (Throwable e2) {
                Log.wtf(TAG, "Unable to assign blame through WorkSource", e2);
            }
        }
    }

    public static WorkSource fromPackage(Context context, String str) {
        if (context == null || context.getPackageManager() == null || str == null) {
            return null;
        }
        String str2;
        String str3;
        String valueOf;
        try {
            ApplicationInfo applicationInfo = Wrappers.packageManager(context).getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return fromUidAndPackage(applicationInfo.uid, str);
            }
            str2 = TAG;
            str3 = "Could not get applicationInfo from package: ";
            valueOf = String.valueOf(str);
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        } catch (NameNotFoundException e) {
            str2 = TAG;
            str3 = "Could not find package: ";
            valueOf = String.valueOf(str);
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
    }

    public static WorkSource fromPackageAndModuleExperimentalPi(Context context, String str, String str2) {
        WorkSource workSource = null;
        if (context == null || context.getPackageManager() == null || str2 == null || str == null) {
            Log.w(TAG, "Unexpected null arguments");
        } else {
            int zzc = zzc(context, str);
            if (zzc >= 0) {
                workSource = new WorkSource();
                if (zzaas == null || zzaat == null) {
                    add(workSource, zzc, str);
                } else {
                    try {
                        Object invoke = zzaas.invoke(workSource, new Object[0]);
                        if (zzc != zzaam) {
                            zzaat.invoke(invoke, new Object[]{Integer.valueOf(zzc), str});
                        }
                        zzaat.invoke(invoke, new Object[]{Integer.valueOf(zzaam), str2});
                    } catch (Throwable e) {
                        Log.w(TAG, "Unable to assign chained blame through WorkSource", e);
                    }
                }
            }
        }
        return workSource;
    }

    public static WorkSource fromUidAndPackage(int i, String str) {
        WorkSource workSource = new WorkSource();
        add(workSource, i, str);
        return workSource;
    }

    public static int get(WorkSource workSource, int i) {
        if (zzaaq != null) {
            try {
                return ((Integer) zzaaq.invoke(workSource, new Object[]{Integer.valueOf(i)})).intValue();
            } catch (Throwable e) {
                Log.wtf(TAG, "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    public static String getName(WorkSource workSource, int i) {
        if (zzaar != null) {
            try {
                return (String) zzaar.invoke(workSource, new Object[]{Integer.valueOf(i)});
            } catch (Throwable e) {
                Log.wtf(TAG, "Unable to assign blame through WorkSource", e);
            }
        }
        return null;
    }

    public static List<String> getNames(WorkSource workSource) {
        int i = 0;
        int size = workSource == null ? 0 : size(workSource);
        if (size == 0) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList();
        while (i < size) {
            String name = getName(workSource, i);
            if (!Strings.isEmptyOrWhitespace(name)) {
                arrayList.add(name);
            }
            i++;
        }
        return arrayList;
    }

    public static boolean hasWorkSourcePermission(Context context) {
        return (context == null || context.getPackageManager() == null || Wrappers.packageManager(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != 0) ? false : true;
    }

    public static int size(WorkSource workSource) {
        if (zzaap != null) {
            try {
                return ((Integer) zzaap.invoke(workSource, new Object[0])).intValue();
            } catch (Throwable e) {
                Log.wtf(TAG, "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    private static int zzc(Context context, String str) {
        String str2;
        String str3;
        String valueOf;
        try {
            ApplicationInfo applicationInfo = Wrappers.packageManager(context).getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return applicationInfo.uid;
            }
            str2 = TAG;
            str3 = "Could not get applicationInfo from package: ";
            valueOf = String.valueOf(str);
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return -1;
        } catch (NameNotFoundException e) {
            str2 = TAG;
            str3 = "Could not find package: ";
            valueOf = String.valueOf(str);
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return -1;
        }
    }

    private static Method zzdf() {
        Method method = null;
        try {
            method = WorkSource.class.getMethod("add", new Class[]{Integer.TYPE});
        } catch (Exception e) {
        }
        return method;
    }

    private static Method zzdg() {
        Method method = null;
        if (PlatformVersion.isAtLeastJellyBeanMR2()) {
            try {
                method = WorkSource.class.getMethod("add", new Class[]{Integer.TYPE, String.class});
            } catch (Exception e) {
            }
        }
        return method;
    }

    private static Method zzdh() {
        Method method = null;
        try {
            method = WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception e) {
        }
        return method;
    }

    private static Method zzdi() {
        Method method = null;
        try {
            method = WorkSource.class.getMethod("get", new Class[]{Integer.TYPE});
        } catch (Exception e) {
        }
        return method;
    }

    private static Method zzdj() {
        Method method = null;
        if (PlatformVersion.isAtLeastJellyBeanMR2()) {
            try {
                method = WorkSource.class.getMethod("getName", new Class[]{Integer.TYPE});
            } catch (Exception e) {
            }
        }
        return method;
    }

    private static final Method zzdk() {
        Method method = null;
        if (PlatformVersion.isAtLeastP()) {
            try {
                method = WorkSource.class.getMethod("createWorkChain", new Class[0]);
            } catch (Throwable e) {
                Log.w(TAG, "Missing WorkChain API createWorkChain", e);
            }
        }
        return method;
    }

    @SuppressLint({"PrivateApi"})
    private static final Method zzdl() {
        Method method = null;
        if (PlatformVersion.isAtLeastP()) {
            try {
                method = Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", new Class[]{Integer.TYPE, String.class});
            } catch (Throwable e) {
                Log.w(TAG, "Missing WorkChain class", e);
            }
        }
        return method;
    }
}
