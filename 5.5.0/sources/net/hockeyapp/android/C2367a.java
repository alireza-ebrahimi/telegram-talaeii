package net.hockeyapp.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import net.hockeyapp.android.p137e.C2400d;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: net.hockeyapp.android.a */
public class C2367a {
    /* renamed from: a */
    public static String f7955a = null;
    /* renamed from: b */
    public static String f7956b = null;
    /* renamed from: c */
    public static String f7957c = null;
    /* renamed from: d */
    public static String f7958d = null;
    /* renamed from: e */
    public static String f7959e = null;
    /* renamed from: f */
    public static String f7960f = null;
    /* renamed from: g */
    public static String f7961g = null;
    /* renamed from: h */
    public static String f7962h = null;
    /* renamed from: i */
    public static String f7963i = null;
    /* renamed from: j */
    public static String f7964j = null;

    /* renamed from: a */
    private static int m11716a(Context context, PackageManager packageManager) {
        int i = 0;
        try {
            Bundle bundle = packageManager.getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                i = bundle.getInt("buildNumber", 0);
            }
        } catch (NameNotFoundException e) {
            C2400d.m11846c("Exception thrown when accessing the application info:");
            e.printStackTrace();
        }
        return i;
    }

    /* renamed from: a */
    public static File m11717a() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "HockeyApp");
        Object obj = (file.exists() || file.mkdirs()) ? 1 : null;
        if (obj == null) {
            C2400d.m11844b("Couldn't create HockeyApp Storage dir");
        }
        return file;
    }

    /* renamed from: a */
    private static String m11718a(Context context, String str) {
        String f = C2367a.m11725f(context);
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.reset();
            instance.update(str.getBytes());
            instance.update(f.getBytes());
            return C2367a.m11719a(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /* renamed from: a */
    private static String m11719a(byte[] bArr) {
        char[] toCharArray = "0123456789ABCDEF".toCharArray();
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            cArr[i * 2] = toCharArray[i2 >>> 4];
            cArr[(i * 2) + 1] = toCharArray[i2 & 15];
        }
        return new String(cArr).replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
    }

    /* renamed from: a */
    public static void m11720a(Context context) {
        f7959e = VERSION.RELEASE;
        f7960f = Build.DISPLAY;
        f7961g = Build.MODEL;
        f7962h = Build.MANUFACTURER;
        C2367a.m11721b(context);
        C2367a.m11722c(context);
        C2367a.m11723d(context);
        C2367a.m11724e(context);
    }

    /* renamed from: b */
    private static void m11721b(Context context) {
        if (context != null) {
            try {
                File filesDir = context.getFilesDir();
                if (filesDir != null) {
                    f7955a = filesDir.getAbsolutePath();
                }
            } catch (Exception e) {
                C2400d.m11846c("Exception thrown when accessing the files dir:");
                e.printStackTrace();
            }
        }
    }

    /* renamed from: c */
    private static void m11722c(Context context) {
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                f7958d = packageInfo.packageName;
                f7956b = TtmlNode.ANONYMOUS_REGION_ID + packageInfo.versionCode;
                f7957c = packageInfo.versionName;
                int a = C2367a.m11716a(context, packageManager);
                if (a != 0 && a > packageInfo.versionCode) {
                    f7956b = TtmlNode.ANONYMOUS_REGION_ID + a;
                }
            } catch (NameNotFoundException e) {
                C2400d.m11846c("Exception thrown when accessing the package info:");
                e.printStackTrace();
            }
        }
    }

    /* renamed from: d */
    private static void m11723d(Context context) {
        Object string = Secure.getString(context.getContentResolver(), "android_id");
        if (!TextUtils.isEmpty(f7958d) && !TextUtils.isEmpty(string)) {
            String str = f7958d + ":" + string + ":" + C2367a.m11725f(context);
            try {
                MessageDigest instance = MessageDigest.getInstance("SHA-1");
                byte[] bytes = str.getBytes(C3446C.UTF8_NAME);
                instance.update(bytes, 0, bytes.length);
                f7963i = C2367a.m11719a(instance.digest());
            } catch (Throwable th) {
                C2400d.m11846c("Couldn't create CrashIdentifier with Exception:" + th.toString());
            }
        }
    }

    /* renamed from: e */
    private static void m11724e(Context context) {
        String string = Secure.getString(context.getContentResolver(), "android_id");
        if (string != null) {
            string = C2367a.m11718a(context, string);
            if (string == null) {
                string = UUID.randomUUID().toString();
            }
            f7964j = string;
        }
    }

    @SuppressLint({"InlinedApi"})
    /* renamed from: f */
    private static String m11725f(Context context) {
        String str = "HA" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + ((VERSION.SDK_INT >= 21 ? Build.SUPPORTED_ABIS[0] : Build.CPU_ABI).length() % 10) + (Build.PRODUCT.length() % 10);
        String str2 = TtmlNode.ANONYMOUS_REGION_ID;
        try {
            str2 = Build.class.getField("SERIAL").get(null).toString();
        } catch (Throwable th) {
        }
        return str + ":" + str2;
    }
}
