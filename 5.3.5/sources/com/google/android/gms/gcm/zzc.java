package com.google.android.gms.gcm;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class zzc extends Handler {
    private /* synthetic */ GoogleCloudMessaging zzikg;

    zzc(GoogleCloudMessaging googleCloudMessaging, Looper looper) {
        this.zzikg = googleCloudMessaging;
        super(looper);
    }

    public final void handleMessage(Message message) {
        if (message == null || !(message.obj instanceof Intent)) {
            Log.w(GoogleCloudMessaging.INSTANCE_ID_SCOPE, "Dropping invalid message");
        }
        Intent intent = (Intent) message.obj;
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
            this.zzikg.zzike.add(intent);
        } else if (!this.zzikg.zzf(intent)) {
            intent.setPackage(this.zzikg.zzaiq.getPackageName());
            this.zzikg.zzaiq.sendBroadcast(intent);
        }
    }
}
