package com.onesignal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.RequiresApi;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.util.concurrent.atomic.AtomicBoolean;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;

class OneSignalSyncServiceUtils {
    private static final int SYNC_AFTER_BG_DELAY_MS = 120000;
    private static final int SYNC_TASK_ID = 2071862118;
    private static Long nextScheduledSyncTime = Long.valueOf(0);
    private static AtomicBoolean runningOnFocusTime = new AtomicBoolean();
    private static Thread syncBgThread;

    static abstract class SyncRunnable implements Runnable {

        /* renamed from: com.onesignal.OneSignalSyncServiceUtils$SyncRunnable$1 */
        class C06891 implements LocationHandler {
            C06891() {
            }

            public CALLBACK_TYPE getType() {
                return CALLBACK_TYPE.SYNC_SERVICE;
            }

            public void complete(LocationPoint point) {
                if (point != null) {
                    OneSignalStateSynchronizer.updateLocation(point);
                }
                OneSignalStateSynchronizer.syncUserState(true);
                OneSignalSyncServiceUtils.syncOnFocusTime();
                SyncRunnable.this.stopSync();
            }
        }

        protected abstract void stopSync();

        SyncRunnable() {
        }

        public final void run() {
            synchronized (OneSignalSyncServiceUtils.nextScheduledSyncTime) {
                OneSignalSyncServiceUtils.nextScheduledSyncTime = Long.valueOf(0);
            }
            if (OneSignal.getUserId() == null) {
                stopSync();
                return;
            }
            OneSignal.appId = OneSignal.getSavedAppId();
            OneSignalStateSynchronizer.initUserState();
            LocationGMS.getLocation(OneSignal.appContext, false, new C06891());
        }
    }

    static class LegacySyncRunnable extends SyncRunnable {
        Service callerService;

        LegacySyncRunnable(Service caller) {
            this.callerService = caller;
        }

        protected void stopSync() {
            OneSignal.Log(LOG_LEVEL.DEBUG, "LegacySyncRunnable:Stopped");
            this.callerService.stopSelf();
        }
    }

    @RequiresApi(api = 21)
    static class LollipopSyncRunnable extends SyncRunnable {
        private JobParameters jobParameters;
        private JobService jobService;

        LollipopSyncRunnable(JobService caller, JobParameters jobParameters) {
            this.jobService = caller;
            this.jobParameters = jobParameters;
        }

        protected void stopSync() {
            OneSignal.Log(LOG_LEVEL.DEBUG, "LollipopSyncRunnable:JobFinished");
            this.jobService.jobFinished(this.jobParameters, false);
        }
    }

    OneSignalSyncServiceUtils() {
    }

    static void scheduleLocationUpdateTask(Context context, long delayMs) {
        OneSignal.Log(LOG_LEVEL.VERBOSE, "scheduleLocationUpdateTask:delayMs: " + delayMs);
        scheduleSyncTask(context, delayMs);
    }

    static void scheduleSyncTask(Context context) {
        OneSignal.Log(LOG_LEVEL.VERBOSE, "scheduleSyncTask:SYNC_AFTER_BG_DELAY_MS: 120000");
        scheduleSyncTask(context, 120000);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void cancelSyncTask(android.content.Context r8) {
        /*
        r4 = nextScheduledSyncTime;
        monitor-enter(r4);
        r6 = 0;
        r3 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x002a }
        nextScheduledSyncTime = r3;	 Catch:{ all -> 0x002a }
        r1 = com.onesignal.LocationGMS.scheduleUpdate(r8);	 Catch:{ all -> 0x002a }
        if (r1 == 0) goto L_0x0013;
    L_0x0011:
        monitor-exit(r4);	 Catch:{ all -> 0x002a }
    L_0x0012:
        return;
    L_0x0013:
        r3 = useJob();	 Catch:{ all -> 0x002a }
        if (r3 == 0) goto L_0x002d;
    L_0x0019:
        r3 = "jobscheduler";
        r2 = r8.getSystemService(r3);	 Catch:{ all -> 0x002a }
        r2 = (android.app.job.JobScheduler) r2;	 Catch:{ all -> 0x002a }
        r3 = 2071862118; // 0x7b7e1b66 float:1.3193991E36 double:1.0236358954E-314;
        r2.cancel(r3);	 Catch:{ all -> 0x002a }
    L_0x0028:
        monitor-exit(r4);	 Catch:{ all -> 0x002a }
        goto L_0x0012;
    L_0x002a:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x002a }
        throw r3;
    L_0x002d:
        r3 = "alarm";
        r0 = r8.getSystemService(r3);	 Catch:{ all -> 0x002a }
        r0 = (android.app.AlarmManager) r0;	 Catch:{ all -> 0x002a }
        r3 = syncServicePendingIntent(r8);	 Catch:{ all -> 0x002a }
        r0.cancel(r3);	 Catch:{ all -> 0x002a }
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignalSyncServiceUtils.cancelSyncTask(android.content.Context):void");
    }

    private static PendingIntent syncServicePendingIntent(Context context) {
        return PendingIntent.getService(context, SYNC_TASK_ID, new Intent(context, SyncService.class), 134217728);
    }

    private static boolean useJob() {
        return VERSION.SDK_INT >= 21;
    }

    private static void scheduleSyncTask(Context context, long delayMs) {
        synchronized (nextScheduledSyncTime) {
            if (nextScheduledSyncTime.longValue() == 0 || System.currentTimeMillis() + delayMs <= nextScheduledSyncTime.longValue()) {
                if (delayMs < DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
                    delayMs = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
                }
                if (useJob()) {
                    scheduleSyncServiceAsJob(context, delayMs);
                } else {
                    scheduleSyncServiceAsAlarm(context, delayMs);
                }
                nextScheduledSyncTime = Long.valueOf(System.currentTimeMillis() + delayMs);
                return;
            }
        }
    }

    @RequiresApi(21)
    private static void scheduleSyncServiceAsJob(Context context, long delayMs) {
        OneSignal.Log(LOG_LEVEL.VERBOSE, "scheduleSyncServiceAsJob:atTime: " + delayMs);
        try {
            OneSignal.Log(LOG_LEVEL.INFO, "scheduleSyncServiceAsJob:result: " + ((JobScheduler) context.getSystemService("jobscheduler")).schedule(new Builder(SYNC_TASK_ID, new ComponentName(context, SyncJobService.class)).setMinimumLatency(delayMs).setRequiredNetworkType(1).setPersisted(true).build()));
        } catch (NullPointerException e) {
            OneSignal.Log(LOG_LEVEL.ERROR, "scheduleSyncServiceAsJob called JobScheduler.jobScheduler which triggered an internal null Android error. Skipping job.", e);
        }
    }

    private static void scheduleSyncServiceAsAlarm(Context context, long delayMs) {
        OneSignal.Log(LOG_LEVEL.VERBOSE, "scheduleServiceSyncTask:atTime: " + delayMs);
        ((AlarmManager) context.getSystemService("alarm")).set(0, (System.currentTimeMillis() + delayMs) + delayMs, syncServicePendingIntent(context));
    }

    static void syncOnFocusTime() {
        if (!runningOnFocusTime.get()) {
            synchronized (runningOnFocusTime) {
                runningOnFocusTime.set(true);
                internalSyncOnFocusTime();
                runningOnFocusTime.set(false);
            }
        }
    }

    private static void internalSyncOnFocusTime() {
        long unsentTime = OneSignal.GetUnsentActiveTime();
        if (unsentTime >= 60) {
            OneSignal.sendOnFocus(unsentTime, true);
        }
    }

    static void doBackgroundSync(Context context, SyncRunnable runnable) {
        OneSignal.appContext = context.getApplicationContext();
        syncBgThread = new Thread(runnable, "OS_SYNCSRV_BG_SYNC");
        syncBgThread.start();
    }

    static boolean stopSyncBgThread() {
        if (syncBgThread == null || !syncBgThread.isAlive()) {
            return false;
        }
        syncBgThread.interrupt();
        return true;
    }
}
