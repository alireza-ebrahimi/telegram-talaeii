package com.onesignal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.TelephonyManager;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OSUtils {
    static final int UNINITIALIZABLE_STATUS = -999;

    OSUtils() {
    }

    int initializationChecker(Context context, int deviceType, String oneSignalAppId) {
        int subscribableStatus = 1;
        try {
            UUID.fromString(oneSignalAppId);
            if ("b2f7f966-d8cc-11e4-bed1-df8f05be55ba".equals(oneSignalAppId) || "5eb5a37e-b458-11e3-ac11-000c2940e62c".equals(oneSignalAppId)) {
                OneSignal.Log(LOG_LEVEL.ERROR, "OneSignal Example AppID detected, please update to your app's id found on OneSignal.com");
            }
            if (deviceType == 1) {
                try {
                    Class.forName("com.google.android.gms.gcm.GoogleCloudMessaging");
                } catch (ClassNotFoundException e) {
                    OneSignal.Log(LOG_LEVEL.FATAL, "The GCM Google Play services client library was not found. Please make sure to include it in your project.", e);
                    subscribableStatus = -4;
                }
                try {
                    Class.forName("com.google.android.gms.common.GooglePlayServicesUtil");
                } catch (ClassNotFoundException e2) {
                    OneSignal.Log(LOG_LEVEL.FATAL, "The GooglePlayServicesUtil class part of Google Play services client library was not found. Include this in your project.", e2);
                    subscribableStatus = -4;
                }
            }
            try {
                Class.forName("android.support.v4.view.MenuCompat");
                try {
                    Class.forName("android.support.v4.content.WakefulBroadcastReceiver");
                    Class.forName("android.support.v4.app.NotificationManagerCompat");
                    if (VERSION.SDK_INT >= 26 && getTargetSdkVersion(context) >= 26) {
                        Class.forName("android.support.v4.app.JobIntentService");
                    }
                } catch (ClassNotFoundException e22) {
                    OneSignal.Log(LOG_LEVEL.FATAL, "The included Android Support Library is to old or incomplete. Please update to the 26.0.0 revision or newer.", e22);
                    subscribableStatus = -5;
                }
            } catch (ClassNotFoundException e222) {
                OneSignal.Log(LOG_LEVEL.FATAL, "Could not find the Android Support Library. Please make sure it has been correctly added to your project.", e222);
                subscribableStatus = -3;
            }
            return subscribableStatus;
        } catch (Throwable t) {
            OneSignal.Log(LOG_LEVEL.FATAL, "OneSignal AppId format is invalid.\nExample: 'b2f7f966-d8cc-11e4-bed1-df8f05be55ba'\n", t);
            return UNINITIALIZABLE_STATUS;
        }
    }

    int getDeviceType() {
        try {
            Class.forName("com.amazon.device.messaging.ADM");
            return 2;
        } catch (ClassNotFoundException e) {
            return 1;
        }
    }

    Integer getNetType() {
        NetworkInfo netInfo = ((ConnectivityManager) OneSignal.appContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null) {
            return null;
        }
        int networkType = netInfo.getType();
        if (networkType == 1 || networkType == 9) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(1);
    }

    String getCarrierName() {
        try {
            String carrierName = ((TelephonyManager) OneSignal.appContext.getSystemService("phone")).getNetworkOperatorName();
            if ("".equals(carrierName)) {
                return null;
            }
            return carrierName;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    static String getManifestMeta(Context context, String metaName) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(metaName);
        } catch (Throwable t) {
            OneSignal.Log(LOG_LEVEL.ERROR, "", t);
            return null;
        }
    }

    static String getResourceString(Context context, String key, String defaultStr) {
        Resources resources = context.getResources();
        int bodyResId = resources.getIdentifier(key, "string", context.getPackageName());
        if (bodyResId != 0) {
            return resources.getString(bodyResId);
        }
        return defaultStr;
    }

    static String getCorrectedLanguage() {
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("iw")) {
            return "he";
        }
        if (lang.equals("in")) {
            return "id";
        }
        if (lang.equals("ji")) {
            return "yi";
        }
        if (lang.equals("zh")) {
            return lang + "-" + Locale.getDefault().getCountry();
        }
        return lang;
    }

    static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$").matcher(email).matches();
    }

    static boolean areNotificationsEnabled(Context context) {
        try {
            return NotificationManagerCompat.from(OneSignal.appContext).areNotificationsEnabled();
        } catch (Throwable th) {
            return true;
        }
    }

    static void runOnMainUIThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    static int getTargetSdkVersion(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).targetSdkVersion;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 15;
        }
    }

    static boolean isValidResourceName(String name) {
        return (name == null || name.matches("^[0-9]")) ? false : true;
    }

    static Uri getSoundUri(Context context, String sound) {
        int soundId;
        Resources resources = context.getResources();
        String packageName = context.getPackageName();
        if (isValidResourceName(sound)) {
            soundId = resources.getIdentifier(sound, "raw", packageName);
            if (soundId != 0) {
                return Uri.parse("android.resource://" + packageName + "/" + soundId);
            }
        }
        soundId = resources.getIdentifier("onesignal_default_sound", "raw", packageName);
        if (soundId != 0) {
            return Uri.parse("android.resource://" + packageName + "/" + soundId);
        }
        return null;
    }

    static long[] parseVibrationPattern(JSONObject gcmBundle) {
        try {
            JSONArray jsonVibArray;
            Object patternObj = gcmBundle.opt("vib_pt");
            if (patternObj instanceof String) {
                jsonVibArray = new JSONArray((String) patternObj);
            } else {
                jsonVibArray = (JSONArray) patternObj;
            }
            long[] jArr = new long[jsonVibArray.length()];
            for (int i = 0; i < jsonVibArray.length(); i++) {
                jArr[i] = jsonVibArray.optLong(i);
            }
            return jArr;
        } catch (JSONException e) {
            return null;
        }
    }

    static String hexDigest(String str, String digestInstance) throws Throwable {
        MessageDigest digest = MessageDigest.getInstance(digestInstance);
        digest.update(str.getBytes("UTF-8"));
        byte[] messageDigest = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(aMessageDigest & 255);
            while (h.length() < 2) {
                h = "0" + h;
            }
            hexString.append(h);
        }
        return hexString.toString();
    }

    static void sleep(int ms) {
        try {
            Thread.sleep((long) ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
