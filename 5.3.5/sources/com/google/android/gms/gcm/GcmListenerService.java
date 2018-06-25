package com.google.android.gms.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.iid.zzb;
import com.thin.downloadmanager.BuildConfig;
import java.util.Iterator;

public class GcmListenerService extends zzb {
    static void zzr(Bundle bundle) {
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && str.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    @Hide
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
                    Object obj2 = (BuildConfig.VERSION_NAME.equals(zza.zzd(extras, "gcm.n.e")) || zza.zzd(extras, "gcm.n.icon") != null) ? 1 : null;
                    if (obj2 != null) {
                        if (zza.zzdm(this)) {
                            zza.zzs(extras);
                        } else {
                            zza.zzdl(this).zzt(extras);
                            return;
                        }
                    }
                    stringExtra = extras.getString("from");
                    extras.remove("from");
                    zzr(extras);
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
