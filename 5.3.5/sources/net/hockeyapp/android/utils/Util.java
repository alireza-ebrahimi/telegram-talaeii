package net.hockeyapp.android.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.hockeyapp.android.C0962R;

public class Util {
    public static final String APP_IDENTIFIER_KEY = "net.hockeyapp.android.appIdentifier";
    public static final int APP_IDENTIFIER_LENGTH = 32;
    public static final String APP_IDENTIFIER_PATTERN = "[0-9a-f]+";
    private static final String APP_SECRET_KEY = "net.hockeyapp.android.appSecret";
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new C09891();
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static final String LOG_IDENTIFIER = "HockeyApp";
    public static final String PREFS_FEEDBACK_TOKEN = "net.hockeyapp.android.prefs_feedback_token";
    public static final String PREFS_KEY_FEEDBACK_TOKEN = "net.hockeyapp.android.prefs_key_feedback_token";
    public static final String PREFS_KEY_NAME_EMAIL_SUBJECT = "net.hockeyapp.android.prefs_key_name_email";
    public static final String PREFS_NAME_EMAIL_SUBJECT = "net.hockeyapp.android.prefs_name_email";
    private static final String SDK_VERSION_KEY = "net.hockeyapp.android.sdkVersion";
    private static final Pattern appIdentifierPattern = Pattern.compile(APP_IDENTIFIER_PATTERN, 2);

    /* renamed from: net.hockeyapp.android.utils.Util$1 */
    static class C09891 extends ThreadLocal<DateFormat> {
        C09891() {
        }

        protected DateFormat initialValue() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat;
        }
    }

    public static String encodeParam(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final boolean isValidEmail(String value) {
        return !TextUtils.isEmpty(value) && Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    @SuppressLint({"NewApi"})
    public static Boolean fragmentsSupported() {
        try {
            boolean z;
            if (VERSION.SDK_INT < 11 || !classExists("android.app.Fragment")) {
                z = false;
            } else {
                z = true;
            }
            return Boolean.valueOf(z);
        } catch (NoClassDefFoundError e) {
            return Boolean.valueOf(false);
        }
    }

    public static Boolean runsOnTablet(WeakReference<Activity> weakActivity) {
        boolean z = false;
        if (weakActivity != null) {
            Activity activity = (Activity) weakActivity.get();
            if (activity != null) {
                Configuration configuration = activity.getResources().getConfiguration();
                if ((configuration.screenLayout & 15) == 3 || (configuration.screenLayout & 15) == 4) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }
        return Boolean.valueOf(false);
    }

    public static String sanitizeAppIdentifier(String appIdentifier) throws IllegalArgumentException {
        if (appIdentifier == null) {
            throw new IllegalArgumentException("App ID must not be null.");
        }
        String sAppIdentifier = appIdentifier.trim();
        Matcher matcher = appIdentifierPattern.matcher(sAppIdentifier);
        if (sAppIdentifier.length() != 32) {
            throw new IllegalArgumentException("App ID length must be 32 characters.");
        } else if (matcher.matches()) {
            return sAppIdentifier;
        } else {
            throw new IllegalArgumentException("App ID must match regex pattern /[0-9a-f]+/i");
        }
    }

    public static String getFormString(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> protoList = new ArrayList();
        for (String key : params.keySet()) {
            String value = (String) params.get(key);
            String key2 = URLEncoder.encode(key2, "UTF-8");
            protoList.add(key2 + "=" + URLEncoder.encode(value, "UTF-8"));
        }
        return TextUtils.join("&", protoList);
    }

    public static boolean classExists(String className) {
        try {
            return Class.forName(className) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isNotificationBuilderSupported() {
        return VERSION.SDK_INT >= 11 && classExists("android.app.Notification.Builder");
    }

    public static Notification createNotification(Context context, PendingIntent pendingIntent, String title, String text, int iconId) {
        if (isNotificationBuilderSupported()) {
            return buildNotificationWithBuilder(context, pendingIntent, title, text, iconId);
        }
        return buildNotificationPreHoneycomb(context, pendingIntent, title, text, iconId);
    }

    private static Notification buildNotificationPreHoneycomb(Context context, PendingIntent pendingIntent, String title, String text, int iconId) {
        Notification notification = new Notification(iconId, "", System.currentTimeMillis());
        try {
            notification.getClass().getMethod("setLatestEventInfo", new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class}).invoke(notification, new Object[]{context, title, text, pendingIntent});
        } catch (Exception e) {
        }
        return notification;
    }

    @TargetApi(11)
    private static Notification buildNotificationWithBuilder(Context context, PendingIntent pendingIntent, String title, String text, int iconId) {
        Builder builder = new Builder(context).setContentTitle(title).setContentText(text).setContentIntent(pendingIntent).setSmallIcon(iconId);
        if (VERSION.SDK_INT < 16) {
            return builder.getNotification();
        }
        return builder.build();
    }

    public static String getAppIdentifier(Context context) {
        return getManifestString(context, APP_IDENTIFIER_KEY);
    }

    public static String getAppSecret(Context context) {
        return getManifestString(context, APP_SECRET_KEY);
    }

    public static String getManifestString(Context context, String key) {
        return getBundle(context).getString(key);
    }

    private static Bundle getBundle(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnected()) {
            return false;
        }
        return true;
    }

    public static String getAppName(Context context) {
        if (context == null) {
            return "";
        }
        String appTitle;
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (NameNotFoundException e) {
        }
        if (applicationInfo != null) {
            appTitle = (String) packageManager.getApplicationLabel(applicationInfo);
        } else {
            appTitle = context.getString(C0962R.string.hockeyapp_crash_dialog_app_name_fallback);
        }
        return appTitle;
    }

    public static String getSdkVersionFromManifest(Context context) {
        return getManifestString(context, SDK_VERSION_KEY);
    }

    public static String convertAppIdentifierToGuid(String appIdentifier) throws IllegalArgumentException {
        try {
            String sanitizedAppIdentifier = sanitizeAppIdentifier(appIdentifier);
            if (sanitizedAppIdentifier == null) {
                return null;
            }
            StringBuffer idBuf = new StringBuffer(sanitizedAppIdentifier);
            idBuf.insert(20, '-');
            idBuf.insert(16, '-');
            idBuf.insert(12, '-');
            idBuf.insert(8, '-');
            return idBuf.toString();
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static boolean isEmulator() {
        return Build.BRAND.equalsIgnoreCase("generic");
    }

    public static String dateToISO8601(Date date) {
        Date localDate = date;
        if (localDate == null) {
            localDate = new Date();
        }
        return ((DateFormat) DATE_FORMAT_THREAD_LOCAL.get()).format(localDate);
    }

    public static boolean sessionTrackingSupported() {
        return VERSION.SDK_INT >= 14;
    }
}
