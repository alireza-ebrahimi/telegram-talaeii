package net.hockeyapp.android.p137e;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Patterns;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;
import net.hockeyapp.android.C2417f.C2416d;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: net.hockeyapp.android.e.i */
public class C2408i {
    /* renamed from: a */
    private static final Pattern f8099a = Pattern.compile("[0-9a-f]+", 2);
    /* renamed from: b */
    private static final char[] f8100b = "0123456789ABCDEF".toCharArray();
    /* renamed from: c */
    private static final ThreadLocal<DateFormat> f8101c = new C24071();

    /* renamed from: net.hockeyapp.android.e.i$1 */
    static class C24071 extends ThreadLocal<DateFormat> {
        C24071() {
        }

        /* renamed from: a */
        protected DateFormat m11878a() {
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat;
        }

        protected /* synthetic */ Object initialValue() {
            return m11878a();
        }
    }

    /* renamed from: a */
    public static Notification m11879a(Context context, PendingIntent pendingIntent, String str, String str2, int i) {
        return C2408i.m11881a() ? C2408i.m11886c(context, pendingIntent, str, str2, i) : C2408i.m11883b(context, pendingIntent, str, str2, i);
    }

    /* renamed from: a */
    public static String m11880a(String str) {
        try {
            return URLEncoder.encode(str, C3446C.UTF8_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: a */
    public static boolean m11881a() {
        return VERSION.SDK_INT >= 11 && C2408i.m11887c("android.app.Notification.Builder");
    }

    /* renamed from: a */
    public static boolean m11882a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* renamed from: b */
    private static Notification m11883b(Context context, PendingIntent pendingIntent, String str, String str2, int i) {
        Notification notification = new Notification(i, TtmlNode.ANONYMOUS_REGION_ID, System.currentTimeMillis());
        try {
            notification.getClass().getMethod("setLatestEventInfo", new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class}).invoke(notification, new Object[]{context, str, str2, pendingIntent});
        } catch (Exception e) {
        }
        return notification;
    }

    /* renamed from: b */
    public static String m11884b(Context context) {
        if (context == null) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (NameNotFoundException e) {
        }
        return applicationInfo != null ? (String) packageManager.getApplicationLabel(applicationInfo) : context.getString(C2416d.hockeyapp_crash_dialog_app_name_fallback);
    }

    /* renamed from: b */
    public static final boolean m11885b(String str) {
        return !TextUtils.isEmpty(str) && Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    @TargetApi(11)
    /* renamed from: c */
    private static Notification m11886c(Context context, PendingIntent pendingIntent, String str, String str2, int i) {
        Builder smallIcon = new Builder(context).setContentTitle(str).setContentText(str2).setContentIntent(pendingIntent).setSmallIcon(i);
        return VERSION.SDK_INT < 16 ? smallIcon.getNotification() : smallIcon.build();
    }

    /* renamed from: c */
    public static boolean m11887c(String str) {
        try {
            return Class.forName(str) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
