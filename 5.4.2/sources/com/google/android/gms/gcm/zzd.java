package com.google.android.gms.gcm;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.al.C0266d;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

final class zzd {
    static zzd zzj;
    private final Context zzk;
    private String zzl;
    private final AtomicInteger zzm = new AtomicInteger((int) SystemClock.elapsedRealtime());

    private zzd(Context context) {
        this.zzk = context.getApplicationContext();
    }

    static synchronized zzd zzd(Context context) {
        zzd zzd;
        synchronized (zzd.class) {
            if (zzj == null) {
                zzj = new zzd(context);
            }
            zzd = zzj;
        }
        return zzd;
    }

    static String zzd(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    private final Bundle zze() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = this.zzk.getPackageManager().getApplicationInfo(this.zzk.getPackageName(), 128);
        } catch (NameNotFoundException e) {
        }
        return (applicationInfo == null || applicationInfo.metaData == null) ? Bundle.EMPTY : applicationInfo.metaData;
    }

    private final String zze(Bundle bundle, String str) {
        Object zzd = zzd(bundle, str);
        if (!TextUtils.isEmpty(zzd)) {
            return zzd;
        }
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_key");
        valueOf = zzd(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (TextUtils.isEmpty(valueOf)) {
            return null;
        }
        Resources resources = this.zzk.getResources();
        int identifier = resources.getIdentifier(valueOf, "string", this.zzk.getPackageName());
        if (identifier == 0) {
            String str2 = "GcmNotification";
            String valueOf3 = String.valueOf(str);
            valueOf2 = String.valueOf("_loc_key");
            valueOf2 = (valueOf2.length() != 0 ? valueOf3.concat(valueOf2) : new String(valueOf3)).substring(6);
            Log.w(str2, new StringBuilder((String.valueOf(valueOf2).length() + 49) + String.valueOf(valueOf).length()).append(valueOf2).append(" resource not found: ").append(valueOf).append(" Default value will be used.").toString());
            return null;
        }
        String valueOf4 = String.valueOf(str);
        valueOf2 = String.valueOf("_loc_args");
        valueOf4 = zzd(bundle, valueOf2.length() != 0 ? valueOf4.concat(valueOf2) : new String(valueOf4));
        if (TextUtils.isEmpty(valueOf4)) {
            return resources.getString(identifier);
        }
        try {
            JSONArray jSONArray = new JSONArray(valueOf4);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = jSONArray.opt(i);
            }
            return resources.getString(identifier, strArr);
        } catch (JSONException e) {
            valueOf = "GcmNotification";
            str2 = String.valueOf(str);
            valueOf2 = String.valueOf("_loc_args");
            valueOf2 = (valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2)).substring(6);
            Log.w(valueOf, new StringBuilder((String.valueOf(valueOf2).length() + 41) + String.valueOf(valueOf4).length()).append("Malformed ").append(valueOf2).append(": ").append(valueOf4).append("  Default value will be used.").toString());
            return null;
        } catch (Throwable e2) {
            Log.w("GcmNotification", new StringBuilder((String.valueOf(valueOf).length() + 58) + String.valueOf(valueOf4).length()).append("Missing format argument for ").append(valueOf).append(": ").append(valueOf4).append(" Default value will be used.").toString(), e2);
            return null;
        }
    }

    final boolean zze(Bundle bundle) {
        int identifier;
        int i;
        Object zzd;
        String zzd2;
        Uri uri;
        Object zzd3;
        Intent intent;
        Intent intent2;
        PendingIntent pendingIntent;
        C0266d a;
        Notification b;
        NotificationManager notificationManager;
        Bundle bundle2;
        String str = null;
        CharSequence zze = zze(bundle, "gcm.n.title");
        CharSequence loadLabel = TextUtils.isEmpty(zze) ? this.zzk.getApplicationInfo().loadLabel(this.zzk.getPackageManager()) : zze;
        CharSequence zze2 = zze(bundle, "gcm.n.body");
        String zzd4 = zzd(bundle, "gcm.n.icon");
        if (!TextUtils.isEmpty(zzd4)) {
            Resources resources = this.zzk.getResources();
            identifier = resources.getIdentifier(zzd4, "drawable", this.zzk.getPackageName());
            if (identifier != 0) {
                i = identifier;
            } else {
                identifier = resources.getIdentifier(zzd4, "mipmap", this.zzk.getPackageName());
                if (identifier != 0) {
                    i = identifier;
                } else {
                    Log.w("GcmNotification", new StringBuilder(String.valueOf(zzd4).length() + 57).append("Icon resource ").append(zzd4).append(" not found. Notification will use app icon.").toString());
                }
            }
            zzd = zzd(bundle, "gcm.n.color");
            zzd2 = zzd(bundle, "gcm.n.sound2");
            if (TextUtils.isEmpty(zzd2)) {
                uri = null;
            } else if (!"default".equals(zzd2) || this.zzk.getResources().getIdentifier(zzd2, "raw", this.zzk.getPackageName()) == 0) {
                uri = RingtoneManager.getDefaultUri(2);
            } else {
                String packageName = this.zzk.getPackageName();
                uri = Uri.parse(new StringBuilder((String.valueOf(packageName).length() + 24) + String.valueOf(zzd2).length()).append("android.resource://").append(packageName).append("/raw/").append(zzd2).toString());
            }
            zzd3 = zzd(bundle, "gcm.n.click_action");
            if (TextUtils.isEmpty(zzd3)) {
                intent = new Intent(zzd3);
                intent.setPackage(this.zzk.getPackageName());
                intent.setFlags(ErrorDialogData.BINDER_CRASH);
                intent2 = intent;
            } else {
                intent = this.zzk.getPackageManager().getLaunchIntentForPackage(this.zzk.getPackageName());
                if (intent != null) {
                    Log.w("GcmNotification", "No activity found to launch app");
                    pendingIntent = null;
                    if (PlatformVersion.isAtLeastO() || this.zzk.getApplicationInfo().targetSdkVersion < 26) {
                        a = new C0266d(this.zzk).m1240a(true).m1227a(i);
                        if (!TextUtils.isEmpty(loadLabel)) {
                            a.m1238a(loadLabel);
                        }
                        if (!TextUtils.isEmpty(zze2)) {
                            a.m1245b(zze2);
                        }
                        if (!TextUtils.isEmpty(zzd)) {
                            a.m1255e(Color.parseColor(zzd));
                        }
                        if (uri != null) {
                            a.m1234a(uri);
                        }
                        if (pendingIntent != null) {
                            a.m1232a(pendingIntent);
                        }
                        b = a.m1242b();
                    } else {
                        String zzd5 = zzd(bundle, "gcm.n.android_channel_id");
                        if (PlatformVersion.isAtLeastO()) {
                            notificationManager = (NotificationManager) this.zzk.getSystemService(NotificationManager.class);
                            if (!TextUtils.isEmpty(zzd5)) {
                                if (notificationManager.getNotificationChannel(zzd5) != null) {
                                    str = zzd5;
                                } else {
                                    Log.w("GcmNotification", new StringBuilder(String.valueOf(zzd5).length() + 122).append("Notification Channel requested (").append(zzd5).append(") has not been created by the app. Manifest configuration, or default, value will be used.").toString());
                                }
                            }
                            if (this.zzl != null) {
                                str = this.zzl;
                            } else {
                                this.zzl = zze().getString("com.google.android.gms.gcm.default_notification_channel_id");
                                if (TextUtils.isEmpty(this.zzl)) {
                                    Log.w("GcmNotification", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                                } else if (notificationManager.getNotificationChannel(this.zzl) != null) {
                                    str = this.zzl;
                                } else {
                                    Log.w("GcmNotification", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                                }
                                if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                                    notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", this.zzk.getString(C1791R.string.gcm_fallback_notification_channel_label), 3));
                                }
                                this.zzl = "fcm_fallback_notification_channel";
                                str = this.zzl;
                            }
                        }
                        Builder smallIcon = new Builder(this.zzk).setAutoCancel(true).setSmallIcon(i);
                        if (!TextUtils.isEmpty(loadLabel)) {
                            smallIcon.setContentTitle(loadLabel);
                        }
                        if (!TextUtils.isEmpty(zze2)) {
                            smallIcon.setContentText(zze2);
                            smallIcon.setStyle(new BigTextStyle().bigText(zze2));
                        }
                        if (!TextUtils.isEmpty(zzd)) {
                            smallIcon.setColor(Color.parseColor(zzd));
                        }
                        if (uri != null) {
                            smallIcon.setSound(uri);
                        }
                        if (pendingIntent != null) {
                            smallIcon.setContentIntent(pendingIntent);
                        }
                        if (str != null) {
                            smallIcon.setChannelId(str);
                        }
                        b = smallIcon.build();
                    }
                    zzd4 = zzd(bundle, "gcm.n.tag");
                    if (Log.isLoggable("GcmNotification", 3)) {
                        Log.d("GcmNotification", "Showing notification");
                    }
                    notificationManager = (NotificationManager) this.zzk.getSystemService("notification");
                    if (TextUtils.isEmpty(zzd4)) {
                        zzd4 = "GCM-Notification:" + SystemClock.uptimeMillis();
                    }
                    notificationManager.notify(zzd4, 0, b);
                    return true;
                }
                intent2 = intent;
            }
            bundle2 = new Bundle(bundle);
            GcmListenerService.zzd(bundle2);
            intent2.putExtras(bundle2);
            for (String zzd22 : bundle2.keySet()) {
                if (!zzd22.startsWith("gcm.n.") || zzd22.startsWith("gcm.notification.")) {
                    intent2.removeExtra(zzd22);
                }
            }
            pendingIntent = PendingIntent.getActivity(this.zzk, this.zzm.getAndIncrement(), intent2, 1073741824);
            if (PlatformVersion.isAtLeastO()) {
            }
            a = new C0266d(this.zzk).m1240a(true).m1227a(i);
            if (TextUtils.isEmpty(loadLabel)) {
                a.m1238a(loadLabel);
            }
            if (TextUtils.isEmpty(zze2)) {
                a.m1245b(zze2);
            }
            if (TextUtils.isEmpty(zzd)) {
                a.m1255e(Color.parseColor(zzd));
            }
            if (uri != null) {
                a.m1234a(uri);
            }
            if (pendingIntent != null) {
                a.m1232a(pendingIntent);
            }
            b = a.m1242b();
            zzd4 = zzd(bundle, "gcm.n.tag");
            if (Log.isLoggable("GcmNotification", 3)) {
                Log.d("GcmNotification", "Showing notification");
            }
            notificationManager = (NotificationManager) this.zzk.getSystemService("notification");
            if (TextUtils.isEmpty(zzd4)) {
                zzd4 = "GCM-Notification:" + SystemClock.uptimeMillis();
            }
            notificationManager.notify(zzd4, 0, b);
            return true;
        }
        identifier = this.zzk.getApplicationInfo().icon;
        if (identifier == 0) {
            identifier = 17301651;
        }
        i = identifier;
        zzd = zzd(bundle, "gcm.n.color");
        zzd22 = zzd(bundle, "gcm.n.sound2");
        if (TextUtils.isEmpty(zzd22)) {
            uri = null;
        } else {
            if ("default".equals(zzd22)) {
            }
            uri = RingtoneManager.getDefaultUri(2);
        }
        zzd3 = zzd(bundle, "gcm.n.click_action");
        if (TextUtils.isEmpty(zzd3)) {
            intent = this.zzk.getPackageManager().getLaunchIntentForPackage(this.zzk.getPackageName());
            if (intent != null) {
                intent2 = intent;
            } else {
                Log.w("GcmNotification", "No activity found to launch app");
                pendingIntent = null;
                if (PlatformVersion.isAtLeastO()) {
                }
                a = new C0266d(this.zzk).m1240a(true).m1227a(i);
                if (TextUtils.isEmpty(loadLabel)) {
                    a.m1238a(loadLabel);
                }
                if (TextUtils.isEmpty(zze2)) {
                    a.m1245b(zze2);
                }
                if (TextUtils.isEmpty(zzd)) {
                    a.m1255e(Color.parseColor(zzd));
                }
                if (uri != null) {
                    a.m1234a(uri);
                }
                if (pendingIntent != null) {
                    a.m1232a(pendingIntent);
                }
                b = a.m1242b();
                zzd4 = zzd(bundle, "gcm.n.tag");
                if (Log.isLoggable("GcmNotification", 3)) {
                    Log.d("GcmNotification", "Showing notification");
                }
                notificationManager = (NotificationManager) this.zzk.getSystemService("notification");
                if (TextUtils.isEmpty(zzd4)) {
                    zzd4 = "GCM-Notification:" + SystemClock.uptimeMillis();
                }
                notificationManager.notify(zzd4, 0, b);
                return true;
            }
        }
        intent = new Intent(zzd3);
        intent.setPackage(this.zzk.getPackageName());
        intent.setFlags(ErrorDialogData.BINDER_CRASH);
        intent2 = intent;
        bundle2 = new Bundle(bundle);
        GcmListenerService.zzd(bundle2);
        intent2.putExtras(bundle2);
        for (String zzd222 : bundle2.keySet()) {
            if (zzd222.startsWith("gcm.n.")) {
            }
            intent2.removeExtra(zzd222);
        }
        pendingIntent = PendingIntent.getActivity(this.zzk, this.zzm.getAndIncrement(), intent2, 1073741824);
        if (PlatformVersion.isAtLeastO()) {
        }
        a = new C0266d(this.zzk).m1240a(true).m1227a(i);
        if (TextUtils.isEmpty(loadLabel)) {
            a.m1238a(loadLabel);
        }
        if (TextUtils.isEmpty(zze2)) {
            a.m1245b(zze2);
        }
        if (TextUtils.isEmpty(zzd)) {
            a.m1255e(Color.parseColor(zzd));
        }
        if (uri != null) {
            a.m1234a(uri);
        }
        if (pendingIntent != null) {
            a.m1232a(pendingIntent);
        }
        b = a.m1242b();
        zzd4 = zzd(bundle, "gcm.n.tag");
        if (Log.isLoggable("GcmNotification", 3)) {
            Log.d("GcmNotification", "Showing notification");
        }
        notificationManager = (NotificationManager) this.zzk.getSystemService("notification");
        if (TextUtils.isEmpty(zzd4)) {
            zzd4 = "GCM-Notification:" + SystemClock.uptimeMillis();
        }
        notificationManager.notify(zzd4, 0, b);
        return true;
    }
}
