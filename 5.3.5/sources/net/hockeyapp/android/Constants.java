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
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import net.hockeyapp.android.utils.HockeyLog;

public class Constants {
    public static String ANDROID_BUILD = null;
    public static String ANDROID_VERSION = null;
    public static String APP_PACKAGE = null;
    public static String APP_VERSION = null;
    public static String APP_VERSION_NAME = null;
    public static final String BASE_URL = "https://sdk.hockeyapp.net/";
    private static final String BUNDLE_BUILD_NUMBER = "buildNumber";
    public static String CRASH_IDENTIFIER = null;
    public static String DEVICE_IDENTIFIER = null;
    public static final String FILES_DIRECTORY_NAME = "HockeyApp";
    public static String FILES_PATH = null;
    public static String PHONE_MANUFACTURER = null;
    public static String PHONE_MODEL = null;
    public static final String SDK_NAME = "HockeySDK";
    public static final String SDK_USER_AGENT = "HockeySDK/Android 4.1.3";
    public static final String SDK_VERSION = "4.1.3";
    public static final int UPDATE_PERMISSIONS_REQUEST = 1;

    public static void loadFromContext(Context context) {
        ANDROID_VERSION = VERSION.RELEASE;
        ANDROID_BUILD = Build.DISPLAY;
        PHONE_MODEL = Build.MODEL;
        PHONE_MANUFACTURER = Build.MANUFACTURER;
        loadFilesPath(context);
        loadPackageData(context);
        loadCrashIdentifier(context);
        loadDeviceIdentifier(context);
    }

    public static File getHockeyAppStorageDir() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "HockeyApp");
        boolean success = dir.exists() || dir.mkdirs();
        if (!success) {
            HockeyLog.warn("Couldn't create HockeyApp Storage dir");
        }
        return dir;
    }

    private static void loadFilesPath(Context context) {
        if (context != null) {
            try {
                File file = context.getFilesDir();
                if (file != null) {
                    FILES_PATH = file.getAbsolutePath();
                }
            } catch (Exception e) {
                HockeyLog.error("Exception thrown when accessing the files dir:");
                e.printStackTrace();
            }
        }
    }

    private static void loadPackageData(Context context) {
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                APP_PACKAGE = packageInfo.packageName;
                APP_VERSION = "" + packageInfo.versionCode;
                APP_VERSION_NAME = packageInfo.versionName;
                int buildNumber = loadBuildNumber(context, packageManager);
                if (buildNumber != 0 && buildNumber > packageInfo.versionCode) {
                    APP_VERSION = "" + buildNumber;
                }
            } catch (NameNotFoundException e) {
                HockeyLog.error("Exception thrown when accessing the package info:");
                e.printStackTrace();
            }
        }
    }

    private static int loadBuildNumber(Context context, PackageManager packageManager) {
        int i = 0;
        try {
            Bundle metaData = packageManager.getApplicationInfo(context.getPackageName(), 128).metaData;
            if (metaData != null) {
                i = metaData.getInt(BUNDLE_BUILD_NUMBER, 0);
            }
        } catch (NameNotFoundException e) {
            HockeyLog.error("Exception thrown when accessing the application info:");
            e.printStackTrace();
        }
        return i;
    }

    private static void loadCrashIdentifier(Context context) {
        String deviceIdentifier = Secure.getString(context.getContentResolver(), "android_id");
        if (!TextUtils.isEmpty(APP_PACKAGE) && !TextUtils.isEmpty(deviceIdentifier)) {
            String combined = APP_PACKAGE + ":" + deviceIdentifier + ":" + createSalt(context);
            try {
                MessageDigest digest = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
                byte[] bytes = combined.getBytes("UTF-8");
                digest.update(bytes, 0, bytes.length);
                CRASH_IDENTIFIER = bytesToHex(digest.digest());
            } catch (Throwable e) {
                HockeyLog.error("Couldn't create CrashIdentifier with Exception:" + e.toString());
            }
        }
    }

    private static void loadDeviceIdentifier(Context context) {
        String deviceIdentifier = Secure.getString(context.getContentResolver(), "android_id");
        if (deviceIdentifier != null) {
            String deviceIdentifierAnonymized = tryHashStringSha256(context, deviceIdentifier);
            if (deviceIdentifierAnonymized == null) {
                deviceIdentifierAnonymized = UUID.randomUUID().toString();
            }
            DEVICE_IDENTIFIER = deviceIdentifierAnonymized;
        }
    }

    private static String tryHashStringSha256(Context context, String input) {
        String salt = createSalt(context);
        try {
            MessageDigest hash = MessageDigest.getInstance(CommonUtils.SHA256_INSTANCE);
            hash.reset();
            hash.update(input.getBytes());
            hash.update(salt.getBytes());
            return bytesToHex(hash.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @SuppressLint({"InlinedApi"})
    private static String createSalt(Context context) {
        String abiString;
        if (VERSION.SDK_INT >= 21) {
            abiString = Build.SUPPORTED_ABIS[0];
        } else {
            abiString = Build.CPU_ABI;
        }
        String fingerprint = "HA" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (abiString.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial = "";
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
        } catch (Throwable th) {
        }
        return fingerprint + ":" + serial;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hex = new char[(bytes.length * 2)];
        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 255;
            hex[index * 2] = HEX_ARRAY[value >>> 4];
            hex[(index * 2) + 1] = HEX_ARRAY[value & 15];
        }
        return new String(hex).replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
    }
}
