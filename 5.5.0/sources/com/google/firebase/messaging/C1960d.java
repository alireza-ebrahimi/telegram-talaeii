package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.al.C0265c;
import android.support.v4.app.al.C0266d;
import android.support.v4.content.C0235a;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.firebase.iid.C1945q;
import com.google.firebase.messaging.C1958b.C1957a;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.telegram.tgnet.ConnectionsManager;

/* renamed from: com.google.firebase.messaging.d */
final class C1960d {
    /* renamed from: a */
    private static C1960d f5795a;
    /* renamed from: b */
    private final Context f5796b;
    /* renamed from: c */
    private Bundle f5797c;
    /* renamed from: d */
    private Method f5798d;
    /* renamed from: e */
    private Method f5799e;
    /* renamed from: f */
    private final AtomicInteger f5800f = new AtomicInteger((int) SystemClock.elapsedRealtime());

    private C1960d(Context context) {
        this.f5796b = context.getApplicationContext();
    }

    @TargetApi(26)
    /* renamed from: a */
    private final Notification m8914a(CharSequence charSequence, String str, int i, Integer num, Uri uri, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str2) {
        Builder smallIcon = new Builder(this.f5796b).setAutoCancel(true).setSmallIcon(i);
        if (!TextUtils.isEmpty(charSequence)) {
            smallIcon.setContentTitle(charSequence);
        }
        if (!TextUtils.isEmpty(str)) {
            smallIcon.setContentText(str);
            smallIcon.setStyle(new BigTextStyle().bigText(str));
        }
        if (num != null) {
            smallIcon.setColor(num.intValue());
        }
        if (uri != null) {
            smallIcon.setSound(uri);
        }
        if (pendingIntent != null) {
            smallIcon.setContentIntent(pendingIntent);
        }
        if (pendingIntent2 != null) {
            smallIcon.setDeleteIntent(pendingIntent2);
        }
        if (str2 != null) {
            if (this.f5798d == null) {
                this.f5798d = C1960d.m8918a("setChannelId");
            }
            if (this.f5798d == null) {
                this.f5798d = C1960d.m8918a("setChannel");
            }
            if (this.f5798d == null) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel");
            } else {
                try {
                    this.f5798d.invoke(smallIcon, new Object[]{str2});
                } catch (Throwable e) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
                } catch (Throwable e2) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e2);
                } catch (Throwable e22) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e22);
                } catch (Throwable e222) {
                    Log.e("FirebaseMessaging", "Error while setting the notification channel", e222);
                }
            }
        }
        return smallIcon.build();
    }

    /* renamed from: a */
    private final Bundle m8915a() {
        if (this.f5797c != null) {
            return this.f5797c;
        }
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = this.f5796b.getPackageManager().getApplicationInfo(this.f5796b.getPackageName(), 128);
        } catch (NameNotFoundException e) {
        }
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return Bundle.EMPTY;
        }
        this.f5797c = applicationInfo.metaData;
        return this.f5797c;
    }

    /* renamed from: a */
    static synchronized C1960d m8916a(Context context) {
        C1960d c1960d;
        synchronized (C1960d.class) {
            if (f5795a == null) {
                f5795a = new C1960d(context);
            }
            c1960d = f5795a;
        }
        return c1960d;
    }

    /* renamed from: a */
    static String m8917a(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    @TargetApi(26)
    /* renamed from: a */
    private static Method m8918a(String str) {
        try {
            return Builder.class.getMethod(str, new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            return null;
        } catch (SecurityException e2) {
            return null;
        }
    }

    /* renamed from: a */
    private static void m8919a(Intent intent, Bundle bundle) {
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals("from")) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    @TargetApi(26)
    /* renamed from: a */
    private final boolean m8920a(int i) {
        if (VERSION.SDK_INT != 26) {
            return true;
        }
        try {
            if (!(this.f5796b.getResources().getDrawable(i, null) instanceof AdaptiveIconDrawable)) {
                return true;
            }
            Log.e("FirebaseMessaging", "Adaptive icons cannot be used in notifications. Ignoring icon id: " + i);
            return false;
        } catch (NotFoundException e) {
            return false;
        }
    }

    /* renamed from: a */
    static boolean m8921a(Bundle bundle) {
        return "1".equals(C1960d.m8917a(bundle, "gcm.n.e")) || C1960d.m8917a(bundle, "gcm.n.icon") != null;
    }

    /* renamed from: b */
    static Uri m8922b(Bundle bundle) {
        Object a = C1960d.m8917a(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty(a)) {
            a = C1960d.m8917a(bundle, "gcm.n.link");
        }
        return !TextUtils.isEmpty(a) ? Uri.parse(a) : null;
    }

    /* renamed from: b */
    private final Integer m8923b(String str) {
        Integer num = null;
        if (VERSION.SDK_INT >= 21) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    num = Integer.valueOf(Color.parseColor(str));
                } catch (IllegalArgumentException e) {
                    Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(str).length() + 54).append("Color ").append(str).append(" not valid. Notification will use default color.").toString());
                }
            }
            int i = m8915a().getInt("com.google.firebase.messaging.default_notification_color", 0);
            if (i != 0) {
                try {
                    num = Integer.valueOf(C0235a.m1075c(this.f5796b, i));
                } catch (NotFoundException e2) {
                    Log.w("FirebaseMessaging", "Cannot find the color resource referenced in AndroidManifest.");
                }
            }
        }
        return num;
    }

    /* renamed from: b */
    static String m8924b(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_key");
        return C1960d.m8917a(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    @TargetApi(26)
    /* renamed from: c */
    private final String m8925c(String str) {
        if (!PlatformVersion.isAtLeastO()) {
            return null;
        }
        NotificationManager notificationManager = (NotificationManager) this.f5796b.getSystemService(NotificationManager.class);
        try {
            if (this.f5799e == null) {
                this.f5799e = notificationManager.getClass().getMethod("getNotificationChannel", new Class[]{String.class});
            }
            if (!TextUtils.isEmpty(str)) {
                if (this.f5799e.invoke(notificationManager, new Object[]{str}) != null) {
                    return str;
                }
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(str).length() + 122).append("Notification Channel requested (").append(str).append(") has not been created by the app. Manifest configuration, or default, value will be used.").toString());
            }
            Object string = m8915a().getString("com.google.firebase.messaging.default_notification_channel_id");
            if (TextUtils.isEmpty(string)) {
                Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
            } else {
                if (this.f5799e.invoke(notificationManager, new Object[]{string}) != null) {
                    return string;
                }
                Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
            }
            if (this.f5799e.invoke(notificationManager, new Object[]{"fcm_fallback_notification_channel"}) == null) {
                Object newInstance = Class.forName("android.app.NotificationChannel").getConstructor(new Class[]{String.class, CharSequence.class, Integer.TYPE}).newInstance(new Object[]{"fcm_fallback_notification_channel", this.f5796b.getString(C1957a.fcm_fallback_notification_channel_label), Integer.valueOf(3)});
                notificationManager.getClass().getMethod("createNotificationChannel", new Class[]{r2}).invoke(notificationManager, new Object[]{newInstance});
            }
            return "fcm_fallback_notification_channel";
        } catch (Throwable e) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
            return null;
        } catch (Throwable e2) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e2);
            return null;
        } catch (Throwable e22) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e22);
            return null;
        } catch (Throwable e222) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e222);
            return null;
        } catch (Throwable e2222) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e2222);
            return null;
        } catch (Throwable e22222) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e22222);
            return null;
        } catch (Throwable e222222) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e222222);
            return null;
        } catch (Throwable e2222222) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e2222222);
            return null;
        }
    }

    /* renamed from: c */
    static Object[] m8926c(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_args");
        String a = C1960d.m8917a(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(a);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = jSONArray.opt(i);
            }
            return strArr;
        } catch (JSONException e) {
            valueOf = "FirebaseMessaging";
            String valueOf3 = String.valueOf(str);
            valueOf2 = String.valueOf("_loc_args");
            valueOf2 = (valueOf2.length() != 0 ? valueOf3.concat(valueOf2) : new String(valueOf3)).substring(6);
            Log.w(valueOf, new StringBuilder((String.valueOf(valueOf2).length() + 41) + String.valueOf(a).length()).append("Malformed ").append(valueOf2).append(": ").append(a).append("  Default value will be used.").toString());
            return null;
        }
    }

    /* renamed from: d */
    static String m8927d(Bundle bundle) {
        Object a = C1960d.m8917a(bundle, "gcm.n.sound2");
        return TextUtils.isEmpty(a) ? C1960d.m8917a(bundle, "gcm.n.sound") : a;
    }

    /* renamed from: d */
    private final String m8928d(Bundle bundle, String str) {
        Object a = C1960d.m8917a(bundle, str);
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        String b = C1960d.m8924b(bundle, str);
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        Resources resources = this.f5796b.getResources();
        int identifier = resources.getIdentifier(b, "string", this.f5796b.getPackageName());
        if (identifier == 0) {
            String str2 = "FirebaseMessaging";
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf("_loc_key");
            valueOf2 = (valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).substring(6);
            Log.w(str2, new StringBuilder((String.valueOf(valueOf2).length() + 49) + String.valueOf(b).length()).append(valueOf2).append(" resource not found: ").append(b).append(" Default value will be used.").toString());
            return null;
        }
        Object[] c = C1960d.m8926c(bundle, str);
        if (c == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, c);
        } catch (Throwable e) {
            valueOf = Arrays.toString(c);
            Log.w("FirebaseMessaging", new StringBuilder((String.valueOf(b).length() + 58) + String.valueOf(valueOf).length()).append("Missing format argument for ").append(b).append(": ").append(valueOf).append(" Default value will be used.").toString(), e);
            return null;
        }
    }

    /* renamed from: c */
    final boolean m8929c(Bundle bundle) {
        if ("1".equals(C1960d.m8917a(bundle, "gcm.n.noui"))) {
            return true;
        }
        boolean z;
        CharSequence d;
        CharSequence d2;
        String a;
        Resources resources;
        int identifier;
        Integer b;
        Uri uri;
        Object a2;
        Uri b2;
        Intent intent;
        Intent intent2;
        Parcelable parcelable;
        Bundle bundle2;
        Intent intent3;
        PendingIntent a3;
        PendingIntent a4;
        Parcelable parcelable2;
        C0266d a5;
        Notification b3;
        String a6;
        NotificationManager notificationManager;
        int i;
        if (!((KeyguardManager) this.f5796b.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            if (!PlatformVersion.isAtLeastLollipop()) {
                SystemClock.sleep(10);
            }
            int myPid = Process.myPid();
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.f5796b.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo.pid == myPid) {
                        z = runningAppProcessInfo.importance == 100;
                        if (z) {
                            return false;
                        }
                        d = m8928d(bundle, "gcm.n.title");
                        if (TextUtils.isEmpty(d)) {
                            d = this.f5796b.getApplicationInfo().loadLabel(this.f5796b.getPackageManager());
                        }
                        d2 = m8928d(bundle, "gcm.n.body");
                        a = C1960d.m8917a(bundle, "gcm.n.icon");
                        if (!TextUtils.isEmpty(a)) {
                            resources = this.f5796b.getResources();
                            identifier = resources.getIdentifier(a, "drawable", this.f5796b.getPackageName());
                            if (identifier == 0 || !m8920a(identifier)) {
                                identifier = resources.getIdentifier(a, "mipmap", this.f5796b.getPackageName());
                                if (identifier == 0 || !m8920a(identifier)) {
                                    Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(a).length() + 61).append("Icon resource ").append(a).append(" not found. Notification will use default icon.").toString());
                                }
                            }
                            b = m8923b(C1960d.m8917a(bundle, "gcm.n.color"));
                            a = C1960d.m8927d(bundle);
                            if (TextUtils.isEmpty(a)) {
                                uri = null;
                            } else if (!"default".equals(a) || this.f5796b.getResources().getIdentifier(a, "raw", this.f5796b.getPackageName()) == 0) {
                                uri = RingtoneManager.getDefaultUri(2);
                            } else {
                                String packageName = this.f5796b.getPackageName();
                                uri = Uri.parse(new StringBuilder((String.valueOf(packageName).length() + 24) + String.valueOf(a).length()).append("android.resource://").append(packageName).append("/raw/").append(a).toString());
                            }
                            a2 = C1960d.m8917a(bundle, "gcm.n.click_action");
                            if (TextUtils.isEmpty(a2)) {
                                b2 = C1960d.m8922b(bundle);
                                if (b2 != null) {
                                    intent = new Intent("android.intent.action.VIEW");
                                    intent.setPackage(this.f5796b.getPackageName());
                                    intent.setData(b2);
                                    intent2 = intent;
                                } else {
                                    intent = this.f5796b.getPackageManager().getLaunchIntentForPackage(this.f5796b.getPackageName());
                                    if (intent == null) {
                                        Log.w("FirebaseMessaging", "No activity found to launch app");
                                    }
                                    intent2 = intent;
                                }
                            } else {
                                intent = new Intent(a2);
                                intent.setPackage(this.f5796b.getPackageName());
                                intent.setFlags(ErrorDialogData.BINDER_CRASH);
                                intent2 = intent;
                            }
                            if (intent2 == null) {
                                parcelable = null;
                            } else {
                                intent2.addFlags(ConnectionsManager.FileTypeFile);
                                bundle2 = new Bundle(bundle);
                                FirebaseMessagingService.m8900a(bundle2);
                                intent2.putExtras(bundle2);
                                for (String a7 : bundle2.keySet()) {
                                    if (!a7.startsWith("gcm.n.") || a7.startsWith("gcm.notification.")) {
                                        intent2.removeExtra(a7);
                                    }
                                }
                                parcelable = PendingIntent.getActivity(this.f5796b, this.f5800f.incrementAndGet(), intent2, 1073741824);
                            }
                            if (FirebaseMessagingService.m8901b(bundle)) {
                                intent3 = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                                C1960d.m8919a(intent3, bundle);
                                intent3.putExtra("pending_intent", parcelable);
                                a3 = C1945q.m8871a(this.f5796b, this.f5800f.incrementAndGet(), intent3, 1073741824);
                                intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                                C1960d.m8919a(intent, bundle);
                                a4 = C1945q.m8871a(this.f5796b, this.f5800f.incrementAndGet(), intent, 1073741824);
                            } else {
                                a4 = null;
                                parcelable2 = parcelable;
                            }
                            if (PlatformVersion.isAtLeastO() || this.f5796b.getApplicationInfo().targetSdkVersion <= 25) {
                                a5 = new C0266d(this.f5796b).m1240a(true).m1227a(identifier);
                                if (!TextUtils.isEmpty(d)) {
                                    a5.m1238a(d);
                                }
                                if (!TextUtils.isEmpty(d2)) {
                                    a5.m1245b(d2);
                                    a5.m1237a(new C0265c().m1223a(d2));
                                }
                                if (b != null) {
                                    a5.m1255e(b.intValue());
                                }
                                if (uri != null) {
                                    a5.m1234a(uri);
                                }
                                if (a3 != null) {
                                    a5.m1232a(a3);
                                }
                                if (a4 != null) {
                                    a5.m1244b(a4);
                                }
                                b3 = a5.m1242b();
                            } else {
                                b3 = m8914a(d, d2, identifier, b, uri, a3, a4, m8925c(C1960d.m8917a(bundle, "gcm.n.android_channel_id")));
                            }
                            a6 = C1960d.m8917a(bundle, "gcm.n.tag");
                            if (Log.isLoggable("FirebaseMessaging", 3)) {
                                Log.d("FirebaseMessaging", "Showing notification");
                            }
                            notificationManager = (NotificationManager) this.f5796b.getSystemService("notification");
                            if (TextUtils.isEmpty(a6)) {
                                a6 = "FCM-Notification:" + SystemClock.uptimeMillis();
                            }
                            notificationManager.notify(a6, 0, b3);
                            return true;
                        }
                        i = m8915a().getInt("com.google.firebase.messaging.default_notification_icon", 0);
                        if (i == 0 || !m8920a(i)) {
                            i = this.f5796b.getApplicationInfo().icon;
                        }
                        if (i == 0 || !m8920a(i)) {
                            i = 17301651;
                        }
                        identifier = i;
                        b = m8923b(C1960d.m8917a(bundle, "gcm.n.color"));
                        a7 = C1960d.m8927d(bundle);
                        if (TextUtils.isEmpty(a7)) {
                            if ("default".equals(a7)) {
                            }
                            uri = RingtoneManager.getDefaultUri(2);
                        } else {
                            uri = null;
                        }
                        a2 = C1960d.m8917a(bundle, "gcm.n.click_action");
                        if (TextUtils.isEmpty(a2)) {
                            b2 = C1960d.m8922b(bundle);
                            if (b2 != null) {
                                intent = this.f5796b.getPackageManager().getLaunchIntentForPackage(this.f5796b.getPackageName());
                                if (intent == null) {
                                    Log.w("FirebaseMessaging", "No activity found to launch app");
                                }
                                intent2 = intent;
                            } else {
                                intent = new Intent("android.intent.action.VIEW");
                                intent.setPackage(this.f5796b.getPackageName());
                                intent.setData(b2);
                                intent2 = intent;
                            }
                        } else {
                            intent = new Intent(a2);
                            intent.setPackage(this.f5796b.getPackageName());
                            intent.setFlags(ErrorDialogData.BINDER_CRASH);
                            intent2 = intent;
                        }
                        if (intent2 == null) {
                            intent2.addFlags(ConnectionsManager.FileTypeFile);
                            bundle2 = new Bundle(bundle);
                            FirebaseMessagingService.m8900a(bundle2);
                            intent2.putExtras(bundle2);
                            for (String a72 : bundle2.keySet()) {
                                if (a72.startsWith("gcm.n.")) {
                                }
                                intent2.removeExtra(a72);
                            }
                            parcelable = PendingIntent.getActivity(this.f5796b, this.f5800f.incrementAndGet(), intent2, 1073741824);
                        } else {
                            parcelable = null;
                        }
                        if (FirebaseMessagingService.m8901b(bundle)) {
                            a4 = null;
                            parcelable2 = parcelable;
                        } else {
                            intent3 = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
                            C1960d.m8919a(intent3, bundle);
                            intent3.putExtra("pending_intent", parcelable);
                            a3 = C1945q.m8871a(this.f5796b, this.f5800f.incrementAndGet(), intent3, 1073741824);
                            intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
                            C1960d.m8919a(intent, bundle);
                            a4 = C1945q.m8871a(this.f5796b, this.f5800f.incrementAndGet(), intent, 1073741824);
                        }
                        if (PlatformVersion.isAtLeastO()) {
                        }
                        a5 = new C0266d(this.f5796b).m1240a(true).m1227a(identifier);
                        if (TextUtils.isEmpty(d)) {
                            a5.m1238a(d);
                        }
                        if (TextUtils.isEmpty(d2)) {
                            a5.m1245b(d2);
                            a5.m1237a(new C0265c().m1223a(d2));
                        }
                        if (b != null) {
                            a5.m1255e(b.intValue());
                        }
                        if (uri != null) {
                            a5.m1234a(uri);
                        }
                        if (a3 != null) {
                            a5.m1232a(a3);
                        }
                        if (a4 != null) {
                            a5.m1244b(a4);
                        }
                        b3 = a5.m1242b();
                        a6 = C1960d.m8917a(bundle, "gcm.n.tag");
                        if (Log.isLoggable("FirebaseMessaging", 3)) {
                            Log.d("FirebaseMessaging", "Showing notification");
                        }
                        notificationManager = (NotificationManager) this.f5796b.getSystemService("notification");
                        if (TextUtils.isEmpty(a6)) {
                            a6 = "FCM-Notification:" + SystemClock.uptimeMillis();
                        }
                        notificationManager.notify(a6, 0, b3);
                        return true;
                    }
                }
            }
        }
        z = false;
        if (z) {
            return false;
        }
        d = m8928d(bundle, "gcm.n.title");
        if (TextUtils.isEmpty(d)) {
            d = this.f5796b.getApplicationInfo().loadLabel(this.f5796b.getPackageManager());
        }
        d2 = m8928d(bundle, "gcm.n.body");
        a72 = C1960d.m8917a(bundle, "gcm.n.icon");
        if (TextUtils.isEmpty(a72)) {
            resources = this.f5796b.getResources();
            identifier = resources.getIdentifier(a72, "drawable", this.f5796b.getPackageName());
            identifier = resources.getIdentifier(a72, "mipmap", this.f5796b.getPackageName());
            Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(a72).length() + 61).append("Icon resource ").append(a72).append(" not found. Notification will use default icon.").toString());
        }
        i = m8915a().getInt("com.google.firebase.messaging.default_notification_icon", 0);
        i = this.f5796b.getApplicationInfo().icon;
        i = 17301651;
        identifier = i;
        b = m8923b(C1960d.m8917a(bundle, "gcm.n.color"));
        a72 = C1960d.m8927d(bundle);
        if (TextUtils.isEmpty(a72)) {
            uri = null;
        } else {
            if ("default".equals(a72)) {
            }
            uri = RingtoneManager.getDefaultUri(2);
        }
        a2 = C1960d.m8917a(bundle, "gcm.n.click_action");
        if (TextUtils.isEmpty(a2)) {
            intent = new Intent(a2);
            intent.setPackage(this.f5796b.getPackageName());
            intent.setFlags(ErrorDialogData.BINDER_CRASH);
            intent2 = intent;
        } else {
            b2 = C1960d.m8922b(bundle);
            if (b2 != null) {
                intent = new Intent("android.intent.action.VIEW");
                intent.setPackage(this.f5796b.getPackageName());
                intent.setData(b2);
                intent2 = intent;
            } else {
                intent = this.f5796b.getPackageManager().getLaunchIntentForPackage(this.f5796b.getPackageName());
                if (intent == null) {
                    Log.w("FirebaseMessaging", "No activity found to launch app");
                }
                intent2 = intent;
            }
        }
        if (intent2 == null) {
            parcelable = null;
        } else {
            intent2.addFlags(ConnectionsManager.FileTypeFile);
            bundle2 = new Bundle(bundle);
            FirebaseMessagingService.m8900a(bundle2);
            intent2.putExtras(bundle2);
            for (String a722 : bundle2.keySet()) {
                if (a722.startsWith("gcm.n.")) {
                }
                intent2.removeExtra(a722);
            }
            parcelable = PendingIntent.getActivity(this.f5796b, this.f5800f.incrementAndGet(), intent2, 1073741824);
        }
        if (FirebaseMessagingService.m8901b(bundle)) {
            intent3 = new Intent("com.google.firebase.messaging.NOTIFICATION_OPEN");
            C1960d.m8919a(intent3, bundle);
            intent3.putExtra("pending_intent", parcelable);
            a3 = C1945q.m8871a(this.f5796b, this.f5800f.incrementAndGet(), intent3, 1073741824);
            intent = new Intent("com.google.firebase.messaging.NOTIFICATION_DISMISS");
            C1960d.m8919a(intent, bundle);
            a4 = C1945q.m8871a(this.f5796b, this.f5800f.incrementAndGet(), intent, 1073741824);
        } else {
            a4 = null;
            parcelable2 = parcelable;
        }
        if (PlatformVersion.isAtLeastO()) {
        }
        a5 = new C0266d(this.f5796b).m1240a(true).m1227a(identifier);
        if (TextUtils.isEmpty(d)) {
            a5.m1238a(d);
        }
        if (TextUtils.isEmpty(d2)) {
            a5.m1245b(d2);
            a5.m1237a(new C0265c().m1223a(d2));
        }
        if (b != null) {
            a5.m1255e(b.intValue());
        }
        if (uri != null) {
            a5.m1234a(uri);
        }
        if (a3 != null) {
            a5.m1232a(a3);
        }
        if (a4 != null) {
            a5.m1244b(a4);
        }
        b3 = a5.m1242b();
        a6 = C1960d.m8917a(bundle, "gcm.n.tag");
        if (Log.isLoggable("FirebaseMessaging", 3)) {
            Log.d("FirebaseMessaging", "Showing notification");
        }
        notificationManager = (NotificationManager) this.f5796b.getSystemService("notification");
        if (TextUtils.isEmpty(a6)) {
            a6 = "FCM-Notification:" + SystemClock.uptimeMillis();
        }
        notificationManager.notify(a6, 0, b3);
        return true;
    }
}
