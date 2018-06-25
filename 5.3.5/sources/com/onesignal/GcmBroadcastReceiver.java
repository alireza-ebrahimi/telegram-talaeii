package com.onesignal;

import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.Random;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String GCM_RECEIVE_ACTION = "com.google.android.c2dm.intent.RECEIVE";
    private static final String GCM_TYPE = "gcm";
    private static final String MESSAGE_TYPE_EXTRA_KEY = "message_type";

    private static boolean isGcmMessage(Intent intent) {
        if (!GCM_RECEIVE_ACTION.equals(intent.getAction())) {
            return false;
        }
        String messageType = intent.getStringExtra(MESSAGE_TYPE_EXTRA_KEY);
        if (messageType == null || "gcm".equals(messageType)) {
            return true;
        }
        return false;
    }

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null && !"google.com/iid".equals(bundle.getString("from"))) {
            ProcessedBundleResult processedResult = processOrderBroadcast(context, intent, bundle);
            if (processedResult == null) {
                setResult(-1);
            } else if (processedResult.isDup || processedResult.hasExtenderService) {
                setAbort();
            } else if (processedResult.isOneSignalPayload && OneSignal.getFilterOtherGCMReceivers(context)) {
                setAbort();
            } else {
                setResult(-1);
            }
        }
    }

    private void setResult(int code) {
        if (isOrderedBroadcast()) {
            setResultCode(code);
        }
    }

    private void setAbort() {
        if (isOrderedBroadcast()) {
            abortBroadcast();
        }
    }

    private static ProcessedBundleResult processOrderBroadcast(Context context, Intent intent, Bundle bundle) {
        if (!isGcmMessage(intent)) {
            return null;
        }
        ProcessedBundleResult processedResult = NotificationBundleProcessor.processBundleFromReceiver(context, bundle);
        if (processedResult.processed()) {
            return processedResult;
        }
        startGCMService(context, bundle);
        return processedResult;
    }

    private static void startGCMService(Context context, Bundle bundle) {
        if (NotificationBundleProcessor.hasRemoteResource(bundle)) {
            if ((Integer.parseInt(bundle.getString("pri", "0")) > 9) || VERSION.SDK_INT < 26) {
                WakefulBroadcastReceiver.startWakefulService(context, new Intent().replaceExtras((Bundle) setCompatBundleForServer(bundle, new BundleCompatBundle()).getBundle()).setComponent(new ComponentName(context.getPackageName(), GcmIntentService.class.getName())));
                return;
            }
            ((JobScheduler) context.getSystemService("jobscheduler")).schedule(new Builder(new Random().nextInt(), new ComponentName(context.getPackageName(), GcmIntentJobService.class.getName())).setOverrideDeadline(0).setExtras((PersistableBundle) setCompatBundleForServer(bundle, BundleCompatFactory.getInstance()).getBundle()).build());
            return;
        }
        NotificationBundleProcessor.ProcessFromGCMIntentService(context, setCompatBundleForServer(bundle, BundleCompatFactory.getInstance()), null);
    }

    private static BundleCompat setCompatBundleForServer(Bundle bundle, BundleCompat taskExtras) {
        taskExtras.putString("json_payload", NotificationBundleProcessor.bundleAsJSONObject(bundle).toString());
        taskExtras.putLong(Param.TIMESTAMP, Long.valueOf(System.currentTimeMillis() / 1000));
        return taskExtras;
    }
}
