package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Debug;
import android.os.DropBoxManager;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.measurement.AppMeasurement;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.annotation.concurrent.GuardedBy;
import org.telegram.messenger.MessagesController;

public final class CrashUtils {
    private static final String[] zzzc = new String[]{"android.", "com.android.", "dalvik.", "java.", "javax."};
    private static DropBoxManager zzzd = null;
    private static boolean zzze = false;
    private static boolean zzzf;
    private static boolean zzzg;
    private static int zzzh = -1;
    @GuardedBy("CrashUtils.class")
    private static int zzzi = 0;
    @GuardedBy("CrashUtils.class")
    private static int zzzj = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorDialogData {
        public static final int AVG_CRASH_FREQ = 2;
        public static final int BINDER_CRASH = 268435456;
        public static final int DYNAMITE_CRASH = 536870912;
        public static final int FORCED_SHUSHED_BY_WRAPPER = 4;
        public static final int NONE = 0;
        public static final int POPUP_FREQ = 1;
        public static final int SUPPRESSED = 1073741824;
    }

    public static boolean addDynamiteErrorToDropBox(Context context, Throwable th) {
        return addErrorToDropBoxInternal(context, th, ErrorDialogData.DYNAMITE_CRASH);
    }

    @Deprecated
    public static boolean addErrorToDropBox(Context context, Throwable th) {
        return addDynamiteErrorToDropBox(context, th);
    }

    public static boolean addErrorToDropBoxInternal(Context context, String str, String str2, int i) {
        return zza(context, str, str2, i, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean addErrorToDropBoxInternal(android.content.Context r5, java.lang.Throwable r6, int r7) {
        /*
        r0 = 0;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r5);	 Catch:{ Exception -> 0x0027 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r6);	 Catch:{ Exception -> 0x0027 }
        r1 = isPackageSide();	 Catch:{ Exception -> 0x0027 }
        if (r1 != 0) goto L_0x000e;
    L_0x000d:
        return r0;
    L_0x000e:
        r1 = zzdb();	 Catch:{ Exception -> 0x0027 }
        if (r1 != 0) goto L_0x001a;
    L_0x0014:
        r6 = zza(r6);	 Catch:{ Exception -> 0x0027 }
        if (r6 == 0) goto L_0x000d;
    L_0x001a:
        r1 = android.util.Log.getStackTraceString(r6);	 Catch:{ Exception -> 0x0027 }
        r2 = com.google.android.gms.common.util.ProcessUtils.getMyProcessName();	 Catch:{ Exception -> 0x0027 }
        r0 = zza(r5, r1, r2, r7, r6);	 Catch:{ Exception -> 0x0027 }
        goto L_0x000d;
    L_0x0027:
        r1 = move-exception;
        r2 = zzdb();	 Catch:{ Exception -> 0x002f }
    L_0x002c:
        if (r2 == 0) goto L_0x003b;
    L_0x002e:
        throw r1;
    L_0x002f:
        r2 = move-exception;
        r3 = "CrashUtils";
        r4 = "Error determining which process we're running in!";
        android.util.Log.e(r3, r4, r2);
        r2 = r0;
        goto L_0x002c;
    L_0x003b:
        r2 = "CrashUtils";
        r3 = "Error adding exception to DropBox!";
        android.util.Log.e(r2, r3, r1);
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.CrashUtils.addErrorToDropBoxInternal(android.content.Context, java.lang.Throwable, int):boolean");
    }

    private static boolean isPackageSide() {
        return zzze ? zzzf : false;
    }

    public static boolean isSystemClassPrefixInternal(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String startsWith : zzzc) {
            if (str.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    @VisibleForTesting
    public static synchronized void setTestVariables(DropBoxManager dropBoxManager, boolean z, boolean z2, int i) {
        synchronized (CrashUtils.class) {
            zzze = true;
            zzzd = dropBoxManager;
            zzzg = z;
            zzzf = z2;
            zzzh = i;
            zzzi = 0;
            zzzj = 0;
        }
    }

    @VisibleForTesting
    private static synchronized String zza(Context context, String str, String str2, int i) {
        String str3;
        Throwable th;
        InputStreamReader inputStreamReader;
        synchronized (CrashUtils.class) {
            int i2;
            StringBuilder stringBuilder = new StringBuilder(1024);
            stringBuilder.append("Process: ").append(Strings.nullToEmpty(str2)).append("\n");
            stringBuilder.append("Package: com.google.android.gms");
            int i3 = 12451009;
            str3 = "12.4.51 (020308-{{cl}})";
            if (zzdb()) {
                try {
                    PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(context.getPackageName(), 0);
                    i3 = packageInfo.versionCode;
                    if (packageInfo.versionName != null) {
                        str3 = packageInfo.versionName;
                    }
                } catch (Throwable e) {
                    Log.w("CrashUtils", "Error while trying to get the package information! Using static version.", e);
                }
            }
            stringBuilder.append(" v").append(i3);
            if (!TextUtils.isEmpty(str3)) {
                if (str3.contains("(") && !str3.contains(")")) {
                    Object concat;
                    if (str3.endsWith("-")) {
                        concat = String.valueOf(str3).concat("111111111");
                    }
                    str3 = String.valueOf(concat).concat(")");
                }
                stringBuilder.append(" (").append(str3).append(")");
            }
            stringBuilder.append("\n");
            stringBuilder.append("Build: ").append(Build.FINGERPRINT).append("\n");
            if (Debug.isDebuggerConnected()) {
                stringBuilder.append("Debugger: Connected\n");
            }
            if (i != 0) {
                stringBuilder.append("DD-EDD: ").append(i).append("\n");
            }
            stringBuilder.append("\n");
            if (!TextUtils.isEmpty(str)) {
                stringBuilder.append(str);
            }
            if (zzdb()) {
                i2 = zzzh >= 0 ? zzzh : Secure.getInt(context.getContentResolver(), "logcat_for_system_app_crash", 0);
            } else {
                i2 = 0;
            }
            if (i2 > 0) {
                stringBuilder.append("\n");
                InputStreamReader inputStreamReader2 = null;
                try {
                    Process start = new ProcessBuilder(new String[]{"/system/bin/logcat", "-v", "time", "-b", "events", "-b", "system", "-b", "main", "-b", AppMeasurement.CRASH_ORIGIN, "-t", String.valueOf(i2)}).redirectErrorStream(true).start();
                    try {
                        start.getOutputStream().close();
                    } catch (IOException e2) {
                    } catch (Throwable th2) {
                        th = th2;
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException e3) {
                            }
                        }
                        throw th;
                    }
                    try {
                        start.getErrorStream().close();
                    } catch (IOException e4) {
                    } catch (Throwable th22) {
                        th = th22;
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException e32) {
                            }
                        }
                        throw th;
                    }
                    inputStreamReader = new InputStreamReader(start.getInputStream());
                    try {
                        char[] cArr = new char[MessagesController.UPDATE_MASK_CHANNEL];
                        while (true) {
                            i3 = inputStreamReader.read(cArr);
                            if (i3 > 0) {
                                stringBuilder.append(cArr, 0, i3);
                            } else {
                                try {
                                    break;
                                } catch (IOException e5) {
                                }
                            }
                        }
                        inputStreamReader.close();
                    } catch (IOException e6) {
                        th = e6;
                        try {
                            Log.e("CrashUtils", "Error running logcat", th);
                            if (inputStreamReader != null) {
                                try {
                                    inputStreamReader.close();
                                } catch (IOException e7) {
                                }
                            }
                            str3 = stringBuilder.toString();
                            return str3;
                        } catch (Throwable th3) {
                            th = th3;
                            inputStreamReader2 = inputStreamReader;
                            if (inputStreamReader2 != null) {
                                inputStreamReader2.close();
                            }
                            throw th;
                        }
                    }
                } catch (IOException e8) {
                    th = e8;
                    inputStreamReader = null;
                    Log.e("CrashUtils", "Error running logcat", th);
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    str3 = stringBuilder.toString();
                    return str3;
                } catch (Throwable th222) {
                    th = th222;
                    if (inputStreamReader2 != null) {
                        try {
                            inputStreamReader2.close();
                        } catch (IOException e322) {
                        }
                    }
                    throw th;
                }
            }
            str3 = stringBuilder.toString();
        }
        return str3;
    }

    @VisibleForTesting
    private static synchronized Throwable zza(Throwable th) {
        Throwable th2;
        int i;
        synchronized (CrashUtils.class) {
            LinkedList linkedList = new LinkedList();
            while (th != null) {
                linkedList.push(th);
                th = th.getCause();
            }
            th2 = null;
            i = 0;
            while (!linkedList.isEmpty()) {
                Throwable th3 = (Throwable) linkedList.pop();
                StackTraceElement[] stackTrace = th3.getStackTrace();
                ArrayList arrayList = new ArrayList();
                arrayList.add(new StackTraceElement(th3.getClass().getName(), "<filtered>", "<filtered>", 1));
                int i2 = i;
                for (Object obj : stackTrace) {
                    Object obj2;
                    String className = obj2.getClassName();
                    Object fileName = obj2.getFileName();
                    i = (TextUtils.isEmpty(fileName) || !fileName.startsWith(":com.google.android.gms")) ? 0 : 1;
                    i2 |= i;
                    if (i == 0 && !isSystemClassPrefixInternal(className)) {
                        obj2 = new StackTraceElement("<filtered>", "<filtered>", "<filtered>", 1);
                    }
                    arrayList.add(obj2);
                }
                th2 = th2 == null ? new Throwable("<filtered>") : new Throwable("<filtered>", th2);
                th2.setStackTrace((StackTraceElement[]) arrayList.toArray(new StackTraceElement[0]));
                i = i2;
            }
        }
        return i == 0 ? null : th2;
    }

    private static synchronized boolean zza(Context context, String str, String str2, int i, Throwable th) {
        boolean z;
        synchronized (CrashUtils.class) {
            Preconditions.checkNotNull(context);
            if (!isPackageSide() || Strings.isEmptyOrWhitespace(str)) {
                z = false;
            } else {
                int hashCode = str.hashCode();
                int hashCode2 = th == null ? zzzj : th.hashCode();
                if (zzzi == hashCode && zzzj == hashCode2) {
                    z = false;
                } else {
                    zzzi = hashCode;
                    zzzj = hashCode2;
                    DropBoxManager dropBoxManager = zzzd != null ? zzzd : (DropBoxManager) context.getSystemService("dropbox");
                    if (dropBoxManager == null || !dropBoxManager.isTagEnabled("system_app_crash")) {
                        z = false;
                    } else {
                        dropBoxManager.addText("system_app_crash", zza(context, str, str2, i));
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    private static boolean zzdb() {
        return zzze ? zzzg : false;
    }
}
