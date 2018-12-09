package p033b.p034a.p035a.p036a.p037a.p039b;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: b.a.a.a.a.b.i */
public class C1110i {
    /* renamed from: a */
    public static final Comparator<File> f3265a = new C11081();
    /* renamed from: b */
    private static Boolean f3266b = null;
    /* renamed from: c */
    private static final char[] f3267c = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /* renamed from: d */
    private static long f3268d = -1;

    /* renamed from: b.a.a.a.a.b.i$1 */
    static class C11081 implements Comparator<File> {
        C11081() {
        }

        /* renamed from: a */
        public int m5991a(File file, File file2) {
            return (int) (file.lastModified() - file2.lastModified());
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m5991a((File) obj, (File) obj2);
        }
    }

    /* renamed from: b.a.a.a.a.b.i$a */
    enum C1109a {
        X86_32,
        X86_64,
        ARM_UNKNOWN,
        PPC,
        PPC64,
        ARMV6,
        ARMV7,
        UNKNOWN,
        ARMV7S,
        ARM64;
        
        /* renamed from: k */
        private static final Map<String, C1109a> f3263k = null;

        static {
            f3263k = new HashMap(4);
            f3263k.put("armeabi-v7a", ARMV7);
            f3263k.put("armeabi", ARMV6);
            f3263k.put("arm64-v8a", ARM64);
            f3263k.put("x86", X86_32);
        }

        /* renamed from: a */
        static C1109a m5992a() {
            Object obj = Build.CPU_ABI;
            if (TextUtils.isEmpty(obj)) {
                C1230c.m6414h().mo1062a("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
                return UNKNOWN;
            }
            C1109a c1109a = (C1109a) f3263k.get(obj.toLowerCase(Locale.US));
            return c1109a == null ? UNKNOWN : c1109a;
        }
    }

    /* renamed from: a */
    public static int m5993a() {
        return C1109a.m5992a().ordinal();
    }

    /* renamed from: a */
    public static int m5994a(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str, str2, C1110i.m6031j(context));
    }

    /* renamed from: a */
    public static int m5995a(Context context, boolean z) {
        Float c = C1110i.m6021c(context);
        return (!z || c == null) ? 1 : ((double) c.floatValue()) >= 99.0d ? 3 : ((double) c.floatValue()) < 99.0d ? 2 : 0;
    }

    /* renamed from: a */
    static long m5996a(String str, String str2, int i) {
        return Long.parseLong(str.split(str2)[0].trim()) * ((long) i);
    }

    /* renamed from: a */
    public static RunningAppProcessInfo m5997a(String str, Context context) {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.equals(str)) {
                    return runningAppProcessInfo;
                }
            }
        }
        return null;
    }

    /* renamed from: a */
    public static SharedPreferences m5998a(Context context) {
        return context.getSharedPreferences("com.crashlytics.prefs", 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public static java.lang.String m5999a(java.io.File r7, java.lang.String r8) {
        /*
        r0 = 0;
        r5 = 1;
        r1 = r7.exists();
        if (r1 == 0) goto L_0x003b;
    L_0x0008:
        r2 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x003c, all -> 0x0063 }
        r1 = new java.io.FileReader;	 Catch:{ Exception -> 0x003c, all -> 0x0063 }
        r1.<init>(r7);	 Catch:{ Exception -> 0x003c, all -> 0x0063 }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2.<init>(r1, r3);	 Catch:{ Exception -> 0x003c, all -> 0x0063 }
    L_0x0014:
        r1 = r2.readLine();	 Catch:{ Exception -> 0x006f }
        if (r1 == 0) goto L_0x0035;
    L_0x001a:
        r3 = "\\s*:\\s*";
        r3 = java.util.regex.Pattern.compile(r3);	 Catch:{ Exception -> 0x006f }
        r4 = 2;
        r1 = r3.split(r1, r4);	 Catch:{ Exception -> 0x006f }
        r3 = r1.length;	 Catch:{ Exception -> 0x006f }
        if (r3 <= r5) goto L_0x0014;
    L_0x0029:
        r3 = 0;
        r3 = r1[r3];	 Catch:{ Exception -> 0x006f }
        r3 = r3.equals(r8);	 Catch:{ Exception -> 0x006f }
        if (r3 == 0) goto L_0x0014;
    L_0x0032:
        r3 = 1;
        r0 = r1[r3];	 Catch:{ Exception -> 0x006f }
    L_0x0035:
        r1 = "Failed to close system file reader.";
        p033b.p034a.p035a.p036a.p037a.p039b.C1110i.m6011a(r2, r1);
    L_0x003b:
        return r0;
    L_0x003c:
        r1 = move-exception;
        r2 = r0;
    L_0x003e:
        r3 = p033b.p034a.p035a.p036a.C1230c.m6414h();	 Catch:{ all -> 0x006d }
        r4 = "Fabric";
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006d }
        r5.<init>();	 Catch:{ all -> 0x006d }
        r6 = "Error parsing ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x006d }
        r5 = r5.append(r7);	 Catch:{ all -> 0x006d }
        r5 = r5.toString();	 Catch:{ all -> 0x006d }
        r3.mo1070e(r4, r5, r1);	 Catch:{ all -> 0x006d }
        r1 = "Failed to close system file reader.";
        p033b.p034a.p035a.p036a.p037a.p039b.C1110i.m6011a(r2, r1);
        goto L_0x003b;
    L_0x0063:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x0066:
        r1 = "Failed to close system file reader.";
        p033b.p034a.p035a.p036a.p037a.p039b.C1110i.m6011a(r2, r1);
        throw r0;
    L_0x006d:
        r0 = move-exception;
        goto L_0x0066;
    L_0x006f:
        r1 = move-exception;
        goto L_0x003e;
        */
        throw new UnsupportedOperationException("Method not decompiled: b.a.a.a.a.b.i.a(java.io.File, java.lang.String):java.lang.String");
    }

    /* renamed from: a */
    public static String m6000a(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: a */
    private static String m6001a(InputStream inputStream, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return C1110i.m6004a(instance.digest());
                }
                instance.update(bArr, 0, read);
            }
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Fabric", "Could not calculate hash for app icon.", e);
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: a */
    public static String m6002a(String str) {
        return C1110i.m6003a(str, "SHA-1");
    }

    /* renamed from: a */
    private static String m6003a(String str, String str2) {
        return C1110i.m6005a(str.getBytes(), str2);
    }

    /* renamed from: a */
    public static String m6004a(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            cArr[i * 2] = f3267c[i2 >>> 4];
            cArr[(i * 2) + 1] = f3267c[i2 & 15];
        }
        return new String(cArr);
    }

    /* renamed from: a */
    private static String m6005a(byte[] bArr, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return C1110i.m6004a(instance.digest());
        } catch (Throwable e) {
            C1230c.m6414h().mo1070e("Fabric", "Could not create hashing algorithm: " + str + ", returning empty string.", e);
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: a */
    public static String m6006a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        List<String> arrayList = new ArrayList();
        for (String str : strArr) {
            if (str != null) {
                arrayList.add(str.replace("-", TtmlNode.ANONYMOUS_REGION_ID).toLowerCase(Locale.US));
            }
        }
        Collections.sort(arrayList);
        StringBuilder stringBuilder = new StringBuilder();
        for (String append : arrayList) {
            stringBuilder.append(append);
        }
        String append2 = stringBuilder.toString();
        return append2.length() > 0 ? C1110i.m6002a(append2) : null;
    }

    /* renamed from: a */
    public static void m6007a(Context context, int i, String str, String str2) {
        if (C1110i.m6026e(context)) {
            C1230c.m6414h().mo1061a(i, "Fabric", str2);
        }
    }

    /* renamed from: a */
    public static void m6008a(Context context, String str) {
        if (C1110i.m6026e(context)) {
            C1230c.m6414h().mo1062a("Fabric", str);
        }
    }

    /* renamed from: a */
    public static void m6009a(Context context, String str, Throwable th) {
        if (C1110i.m6026e(context)) {
            C1230c.m6414h().mo1069e("Fabric", str);
        }
    }

    /* renamed from: a */
    public static void m6010a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    /* renamed from: a */
    public static void m6011a(Closeable closeable, String str) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Fabric", str, e);
            }
        }
    }

    /* renamed from: a */
    public static void m6012a(Flushable flushable, String str) {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (Throwable e) {
                C1230c.m6414h().mo1070e("Fabric", str, e);
            }
        }
    }

    /* renamed from: a */
    public static void m6013a(InputStream inputStream, OutputStream outputStream, byte[] bArr) {
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    public static boolean m6014a(Context context, String str, boolean z) {
        if (context == null) {
            return z;
        }
        Resources resources = context.getResources();
        if (resources == null) {
            return z;
        }
        int a = C1110i.m5994a(context, str, "bool");
        if (a > 0) {
            return resources.getBoolean(a);
        }
        int a2 = C1110i.m5994a(context, str, "string");
        return a2 > 0 ? Boolean.parseBoolean(context.getString(a2)) : z;
    }

    /* renamed from: b */
    public static synchronized long m6015b() {
        long j;
        synchronized (C1110i.class) {
            if (f3268d == -1) {
                j = 0;
                Object a = C1110i.m5999a(new File("/proc/meminfo"), "MemTotal");
                if (!TextUtils.isEmpty(a)) {
                    String toUpperCase = a.toUpperCase(Locale.US);
                    try {
                        if (toUpperCase.endsWith("KB")) {
                            j = C1110i.m5996a(toUpperCase, "KB", 1024);
                        } else if (toUpperCase.endsWith("MB")) {
                            j = C1110i.m5996a(toUpperCase, "MB", (int) ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES);
                        } else if (toUpperCase.endsWith("GB")) {
                            j = C1110i.m5996a(toUpperCase, "GB", 1073741824);
                        } else {
                            C1230c.m6414h().mo1062a("Fabric", "Unexpected meminfo format while computing RAM: " + toUpperCase);
                        }
                    } catch (Throwable e) {
                        C1230c.m6414h().mo1070e("Fabric", "Unexpected meminfo format while computing RAM: " + toUpperCase, e);
                    }
                }
                f3268d = j;
            }
            j = f3268d;
        }
        return j;
    }

    /* renamed from: b */
    public static long m6016b(Context context) {
        MemoryInfo memoryInfo = new MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    /* renamed from: b */
    public static String m6017b(Context context, String str) {
        int a = C1110i.m5994a(context, str, "string");
        return a > 0 ? context.getString(a) : TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: b */
    public static String m6018b(InputStream inputStream) {
        return C1110i.m6001a(inputStream, "SHA-1");
    }

    /* renamed from: b */
    public static String m6019b(String str) {
        return C1110i.m6003a(str, "SHA-256");
    }

    /* renamed from: c */
    public static long m6020c(String str) {
        StatFs statFs = new StatFs(str);
        long blockSize = (long) statFs.getBlockSize();
        return (((long) statFs.getBlockCount()) * blockSize) - (((long) statFs.getAvailableBlocks()) * blockSize);
    }

    /* renamed from: c */
    public static Float m6021c(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            return null;
        }
        return Float.valueOf(((float) registerReceiver.getIntExtra(C1797b.LEVEL, -1)) / ((float) registerReceiver.getIntExtra("scale", -1)));
    }

    /* renamed from: c */
    public static boolean m6022c() {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger();
    }

    /* renamed from: c */
    public static boolean m6023c(Context context, String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    /* renamed from: d */
    public static boolean m6024d(Context context) {
        if (C1110i.m6027f(context)) {
            return false;
        }
        return ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8) != null;
    }

    /* renamed from: d */
    public static boolean m6025d(String str) {
        return str == null || str.length() == 0;
    }

    /* renamed from: e */
    public static boolean m6026e(Context context) {
        if (f3266b == null) {
            f3266b = Boolean.valueOf(C1110i.m6014a(context, "com.crashlytics.Trace", false));
        }
        return f3266b.booleanValue();
    }

    /* renamed from: f */
    public static boolean m6027f(Context context) {
        return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || Secure.getString(context.getContentResolver(), "android_id") == null;
    }

    /* renamed from: g */
    public static boolean m6028g(Context context) {
        boolean f = C1110i.m6027f(context);
        String str = Build.TAGS;
        if ((!f && str != null && str.contains("test-keys")) || new File("/system/app/Superuser.apk").exists()) {
            return true;
        }
        return !f && new File("/system/xbin/su").exists();
    }

    /* renamed from: h */
    public static int m6029h(Context context) {
        int i = 0;
        if (C1110i.m6027f(context)) {
            i = 1;
        }
        if (C1110i.m6028g(context)) {
            i |= 2;
        }
        return C1110i.m6022c() ? i | 4 : i;
    }

    /* renamed from: i */
    public static boolean m6030i(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    /* renamed from: j */
    public static String m6031j(Context context) {
        int i = context.getApplicationContext().getApplicationInfo().icon;
        return i > 0 ? context.getResources().getResourcePackageName(i) : context.getPackageName();
    }

    /* renamed from: k */
    public static String m6032k(Context context) {
        Throwable e;
        Throwable th;
        String str = null;
        Closeable openRawResource;
        try {
            openRawResource = context.getResources().openRawResource(C1110i.m6033l(context));
            try {
                String b = C1110i.m6018b((InputStream) openRawResource);
                if (!C1110i.m6025d(b)) {
                    str = b;
                }
                C1110i.m6011a(openRawResource, "Failed to close icon input stream.");
            } catch (Exception e2) {
                e = e2;
                try {
                    C1230c.m6414h().mo1070e("Fabric", "Could not calculate hash for app icon.", e);
                    C1110i.m6011a(openRawResource, "Failed to close icon input stream.");
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    C1110i.m6011a(openRawResource, "Failed to close icon input stream.");
                    throw th;
                }
            }
        } catch (Exception e3) {
            e = e3;
            openRawResource = null;
            C1230c.m6414h().mo1070e("Fabric", "Could not calculate hash for app icon.", e);
            C1110i.m6011a(openRawResource, "Failed to close icon input stream.");
            return str;
        } catch (Throwable e4) {
            openRawResource = null;
            th = e4;
            C1110i.m6011a(openRawResource, "Failed to close icon input stream.");
            throw th;
        }
        return str;
    }

    /* renamed from: l */
    public static int m6033l(Context context) {
        return context.getApplicationContext().getApplicationInfo().icon;
    }

    /* renamed from: m */
    public static String m6034m(Context context) {
        int a = C1110i.m5994a(context, "io.fabric.android.build_id", "string");
        if (a == 0) {
            a = C1110i.m5994a(context, "com.crashlytics.android.build_id", "string");
        }
        if (a == 0) {
            return null;
        }
        String string = context.getResources().getString(a);
        C1230c.m6414h().mo1062a("Fabric", "Build ID is: " + string);
        return string;
    }

    @SuppressLint({"MissingPermission"})
    /* renamed from: n */
    public static boolean m6035n(Context context) {
        if (!C1110i.m6023c(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return true;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
