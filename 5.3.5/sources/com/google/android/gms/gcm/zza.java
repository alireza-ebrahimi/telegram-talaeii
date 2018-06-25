package com.google.android.gms.gcm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
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
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.C0489R;
import com.google.android.gms.common.util.zzs;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

final class zza {
    static zza zzijk;
    private final Context mContext;
    private String zzijl;
    private final AtomicInteger zzijm = new AtomicInteger((int) SystemClock.elapsedRealtime());

    private zza(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private final Bundle zzawf() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
        } catch (NameNotFoundException e) {
        }
        return (applicationInfo == null || applicationInfo.metaData == null) ? Bundle.EMPTY : applicationInfo.metaData;
    }

    static String zzd(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    static synchronized zza zzdl(Context context) {
        zza zza;
        synchronized (zza.class) {
            if (zzijk == null) {
                zzijk = new zza(context);
            }
            zza = zzijk;
        }
        return zza;
    }

    static boolean zzdm(Context context) {
        if (((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            return false;
        }
        int myPid = Process.myPid();
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.importance == 100;
                }
            }
        }
        return false;
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
        Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(valueOf, "string", this.mContext.getPackageName());
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

    static void zzs(Bundle bundle) {
        String str;
        Bundle bundle2 = new Bundle();
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            str = (String) it.next();
            String string = bundle.getString(str);
            if (str.startsWith("gcm.notification.")) {
                str = str.replace("gcm.notification.", "gcm.n.");
            }
            if (str.startsWith("gcm.n.")) {
                if (!"gcm.n.e".equals(str)) {
                    bundle2.putString(str.substring(6), string);
                }
                it.remove();
            }
        }
        str = bundle2.getString("sound2");
        if (str != null) {
            bundle2.remove("sound2");
            bundle2.putString("sound", str);
        }
        if (!bundle2.isEmpty()) {
            bundle.putBundle(NotificationTable.TABLE_NAME, bundle2);
        }
    }

    private final PendingIntent zzu(Bundle bundle) {
        Intent intent;
        Object zzd = zzd(bundle, "gcm.n.click_action");
        Intent launchIntentForPackage;
        if (TextUtils.isEmpty(zzd)) {
            launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
            if (launchIntentForPackage == null) {
                Log.w("GcmNotification", "No activity found to launch app");
                return null;
            }
            intent = launchIntentForPackage;
        } else {
            launchIntentForPackage = new Intent(zzd);
            launchIntentForPackage.setPackage(this.mContext.getPackageName());
            launchIntentForPackage.setFlags(268435456);
            intent = launchIntentForPackage;
        }
        Bundle bundle2 = new Bundle(bundle);
        GcmListenerService.zzr(bundle2);
        intent.putExtras(bundle2);
        for (String str : bundle2.keySet()) {
            if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                intent.removeExtra(str);
            }
        }
        return PendingIntent.getActivity(this.mContext, this.zzijm.getAndIncrement(), intent, 1073741824);
    }

    final boolean zzt(Bundle bundle) {
        int identifier;
        int i;
        Object zzd;
        String zzd2;
        Uri uri;
        PendingIntent zzu;
        Builder smallIcon;
        Notification build;
        NotificationManager notificationManager;
        String str = null;
        CharSequence zze = zze(bundle, "gcm.n.title");
        CharSequence loadLabel = TextUtils.isEmpty(zze) ? this.mContext.getApplicationInfo().loadLabel(this.mContext.getPackageManager()) : zze;
        CharSequence zze2 = zze(bundle, "gcm.n.body");
        String zzd3 = zzd(bundle, "gcm.n.icon");
        if (!TextUtils.isEmpty(zzd3)) {
            Resources resources = this.mContext.getResources();
            identifier = resources.getIdentifier(zzd3, "drawable", this.mContext.getPackageName());
            if (identifier != 0) {
                i = identifier;
            } else {
                identifier = resources.getIdentifier(zzd3, "mipmap", this.mContext.getPackageName());
                if (identifier != 0) {
                    i = identifier;
                } else {
                    Log.w("GcmNotification", new StringBuilder(String.valueOf(zzd3).length() + 57).append("Icon resource ").append(zzd3).append(" not found. Notification will use app icon.").toString());
                }
            }
            zzd = zzd(bundle, "gcm.n.color");
            zzd2 = zzd(bundle, "gcm.n.sound2");
            if (TextUtils.isEmpty(zzd2)) {
                uri = null;
            } else if (!"default".equals(zzd2) || this.mContext.getResources().getIdentifier(zzd2, "raw", this.mContext.getPackageName()) == 0) {
                uri = RingtoneManager.getDefaultUri(2);
            } else {
                String packageName = this.mContext.getPackageName();
                uri = Uri.parse(new StringBuilder((String.valueOf(packageName).length() + 24) + String.valueOf(zzd2).length()).append("android.resource://").append(packageName).append("/raw/").append(zzd2).toString());
            }
            zzu = zzu(bundle);
            if (zzs.isAtLeastO() || this.mContext.getApplicationInfo().targetSdkVersion <= 25) {
                smallIcon = new Builder(this.mContext).setAutoCancel(true).setSmallIcon(i);
                if (!TextUtils.isEmpty(loadLabel)) {
                    smallIcon.setContentTitle(loadLabel);
                }
                if (!TextUtils.isEmpty(zze2)) {
                    smallIcon.setContentText(zze2);
                }
                if (!TextUtils.isEmpty(zzd)) {
                    smallIcon.setColor(Color.parseColor(zzd));
                }
                if (uri != null) {
                    smallIcon.setSound(uri);
                }
                if (zzu != null) {
                    smallIcon.setContentIntent(zzu);
                }
                build = smallIcon.build();
            } else {
                String zzd4 = zzd(bundle, "gcm.n.android_channel_id");
                if (zzs.isAtLeastO()) {
                    notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
                    if (!TextUtils.isEmpty(zzd4)) {
                        if (notificationManager.getNotificationChannel(zzd4) != null) {
                            str = zzd4;
                        } else {
                            Log.w("GcmNotification", new StringBuilder(String.valueOf(zzd4).length() + 122).append("Notification Channel requested (").append(zzd4).append(") has not been created by the app. Manifest configuration, or default, value will be used.").toString());
                        }
                    }
                    if (this.zzijl != null) {
                        str = this.zzijl;
                    } else {
                        this.zzijl = zzawf().getString("com.google.android.gms.gcm.default_notification_channel_id");
                        if (TextUtils.isEmpty(this.zzijl)) {
                            Log.w("GcmNotification", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
                        } else if (notificationManager.getNotificationChannel(this.zzijl) != null) {
                            str = this.zzijl;
                        } else {
                            Log.w("GcmNotification", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
                        }
                        if (notificationManager.getNotificationChannel("fcm_fallback_notification_channel") == null) {
                            notificationManager.createNotificationChannel(new NotificationChannel("fcm_fallback_notification_channel", this.mContext.getString(C0489R.string.gcm_fallback_notification_channel_label), 3));
                        }
                        this.zzijl = "fcm_fallback_notification_channel";
                        str = this.zzijl;
                    }
                }
                Notification.Builder smallIcon2 = new Notification.Builder(this.mContext).setAutoCancel(true).setSmallIcon(i);
                if (!TextUtils.isEmpty(loadLabel)) {
                    smallIcon2.setContentTitle(loadLabel);
                }
                if (!TextUtils.isEmpty(zze2)) {
                    smallIcon2.setContentText(zze2);
                    smallIcon2.setStyle(new BigTextStyle().bigText(zze2));
                }
                if (!TextUtils.isEmpty(zzd)) {
                    smallIcon2.setColor(Color.parseColor(zzd));
                }
                if (uri != null) {
                    smallIcon2.setSound(uri);
                }
                if (zzu != null) {
                    smallIcon2.setContentIntent(zzu);
                }
                if (str != null) {
                    smallIcon2.setChannelId(str);
                }
                build = smallIcon2.build();
            }
            zzd3 = zzd(bundle, "gcm.n.tag");
            if (Log.isLoggable("GcmNotification", 3)) {
                Log.d("GcmNotification", "Showing notification");
            }
            notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationTable.TABLE_NAME);
            if (TextUtils.isEmpty(zzd3)) {
                zzd3 = "GCM-Notification:" + SystemClock.uptimeMillis();
            }
            notificationManager.notify(zzd3, 0, build);
            return true;
        }
        identifier = this.mContext.getApplicationInfo().icon;
        if (identifier == 0) {
            identifier = 17301651;
        }
        i = identifier;
        zzd = zzd(bundle, "gcm.n.color");
        zzd2 = zzd(bundle, "gcm.n.sound2");
        if (TextUtils.isEmpty(zzd2)) {
            uri = null;
        } else {
            if ("default".equals(zzd2)) {
            }
            uri = RingtoneManager.getDefaultUri(2);
        }
        zzu = zzu(bundle);
        if (zzs.isAtLeastO()) {
        }
        smallIcon = new Builder(this.mContext).setAutoCancel(true).setSmallIcon(i);
        if (TextUtils.isEmpty(loadLabel)) {
            smallIcon.setContentTitle(loadLabel);
        }
        if (TextUtils.isEmpty(zze2)) {
            smallIcon.setContentText(zze2);
        }
        if (TextUtils.isEmpty(zzd)) {
            smallIcon.setColor(Color.parseColor(zzd));
        }
        if (uri != null) {
            smallIcon.setSound(uri);
        }
        if (zzu != null) {
            smallIcon.setContentIntent(zzu);
        }
        build = smallIcon.build();
        zzd3 = zzd(bundle, "gcm.n.tag");
        if (Log.isLoggable("GcmNotification", 3)) {
            Log.d("GcmNotification", "Showing notification");
        }
        notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationTable.TABLE_NAME);
        if (TextUtils.isEmpty(zzd3)) {
            zzd3 = "GCM-Notification:" + SystemClock.uptimeMillis();
        }
        notificationManager.notify(zzd3, 0, build);
        return true;
    }
}
