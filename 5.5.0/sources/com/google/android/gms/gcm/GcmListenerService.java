package com.google.android.gms.gcm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import com.google.android.gms.iid.zze;
import java.util.Iterator;
import java.util.List;

@Deprecated
public class GcmListenerService extends zze {
    static void zzd(Bundle bundle) {
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && str.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    public void handleIntent(Intent intent) {
        String stringExtra;
        if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            stringExtra = intent.getStringExtra("message_type");
            if (stringExtra == null) {
                stringExtra = GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE;
            }
            Object obj = -1;
            switch (stringExtra.hashCode()) {
                case -2062414158:
                    if (stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)) {
                        int i = 1;
                        break;
                    }
                    break;
                case 102161:
                    if (stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
                        obj = null;
                        break;
                    }
                    break;
                case 814694033:
                    if (stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR)) {
                        obj = 3;
                        break;
                    }
                    break;
                case 814800675:
                    if (stringExtra.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_EVENT)) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    Bundle extras = intent.getExtras();
                    extras.remove("message_type");
                    extras.remove("android.support.content.wakelockid");
                    Object obj2 = ("1".equals(zzd.zzd(extras, "gcm.n.e")) || zzd.zzd(extras, "gcm.n.icon") != null) ? 1 : null;
                    if (obj2 != null) {
                        Bundle bundle;
                        Iterator it;
                        String string;
                        if (!((KeyguardManager) getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
                            int myPid = Process.myPid();
                            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) getSystemService("activity")).getRunningAppProcesses();
                            if (runningAppProcesses != null) {
                                for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                                    if (runningAppProcessInfo.pid == myPid) {
                                        obj2 = runningAppProcessInfo.importance == 100 ? 1 : null;
                                        if (obj2 != null) {
                                            zzd.zzd(this).zze(extras);
                                            return;
                                        }
                                        bundle = new Bundle();
                                        it = extras.keySet().iterator();
                                        while (it.hasNext()) {
                                            stringExtra = (String) it.next();
                                            string = extras.getString(stringExtra);
                                            if (stringExtra.startsWith("gcm.notification.")) {
                                                stringExtra = stringExtra.replace("gcm.notification.", "gcm.n.");
                                            }
                                            if (!stringExtra.startsWith("gcm.n.")) {
                                                if (!"gcm.n.e".equals(stringExtra)) {
                                                    bundle.putString(stringExtra.substring(6), string);
                                                }
                                                it.remove();
                                            }
                                        }
                                        stringExtra = bundle.getString("sound2");
                                        if (stringExtra != null) {
                                            bundle.remove("sound2");
                                            bundle.putString("sound", stringExtra);
                                        }
                                        if (!bundle.isEmpty()) {
                                            extras.putBundle("notification", bundle);
                                        }
                                    }
                                }
                            }
                        }
                        obj2 = null;
                        if (obj2 != null) {
                            bundle = new Bundle();
                            it = extras.keySet().iterator();
                            while (it.hasNext()) {
                                stringExtra = (String) it.next();
                                string = extras.getString(stringExtra);
                                if (stringExtra.startsWith("gcm.notification.")) {
                                    stringExtra = stringExtra.replace("gcm.notification.", "gcm.n.");
                                }
                                if (!stringExtra.startsWith("gcm.n.")) {
                                    if ("gcm.n.e".equals(stringExtra)) {
                                        bundle.putString(stringExtra.substring(6), string);
                                    }
                                    it.remove();
                                }
                            }
                            stringExtra = bundle.getString("sound2");
                            if (stringExtra != null) {
                                bundle.remove("sound2");
                                bundle.putString("sound", stringExtra);
                            }
                            if (bundle.isEmpty()) {
                                extras.putBundle("notification", bundle);
                            }
                        } else {
                            zzd.zzd(this).zze(extras);
                            return;
                        }
                    }
                    stringExtra = extras.getString("from");
                    extras.remove("from");
                    zzd(extras);
                    onMessageReceived(stringExtra, extras);
                    return;
                case 1:
                    onDeletedMessages();
                    return;
                case 2:
                    onMessageSent(intent.getStringExtra("google.message_id"));
                    return;
                case 3:
                    stringExtra = intent.getStringExtra("google.message_id");
                    if (stringExtra == null) {
                        stringExtra = intent.getStringExtra("message_id");
                    }
                    onSendError(stringExtra, intent.getStringExtra("error"));
                    return;
                default:
                    String str = "GcmListenerService";
                    String str2 = "Received message with unknown type: ";
                    stringExtra = String.valueOf(stringExtra);
                    Log.w(str, stringExtra.length() != 0 ? str2.concat(stringExtra) : new String(str2));
                    return;
            }
        }
        str = "GcmListenerService";
        str2 = "Unknown intent action: ";
        stringExtra = String.valueOf(intent.getAction());
        Log.w(str, stringExtra.length() != 0 ? str2.concat(stringExtra) : new String(str2));
    }

    public void onDeletedMessages() {
    }

    public void onMessageReceived(String str, Bundle bundle) {
    }

    public void onMessageSent(String str) {
    }

    public void onSendError(String str, String str2) {
    }
}
