package com.onesignal;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.util.ArrayList;

class NotificationRestorer {
    static final String[] COLUMNS_FOR_RESTORE = new String[]{NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID, NotificationTable.COLUMN_NAME_FULL_DATA, NotificationTable.COLUMN_NAME_CREATED_TIME};
    private static final int RESTORE_KICKOFF_REQUEST_CODE = 2071862120;
    private static final int RESTORE_NOTIFICATIONS_DELAY_MS = 15000;
    public static boolean restored;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    public static void restore(android.content.Context r15) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0004 in list [B:12:0x00bc]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r1 = restored;
        if (r1 == 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r1 = 1;
        restored = r1;
        r1 = com.onesignal.OneSignal.LOG_LEVEL.INFO;
        r2 = "restoring notifications";
        com.onesignal.OneSignal.Log(r1, r2);
        r11 = com.onesignal.OneSignalDbHelper.getInstance(r15);
        r14 = 0;
        r14 = r11.getWritableDbWithRetries();	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        r14.beginTransaction();	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        com.onesignal.NotificationBundleProcessor.deleteOldNotifications(r14);	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        r14.setTransactionSuccessful();	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        if (r14 == 0) goto L_0x0027;
    L_0x0024:
        r14.endTransaction();	 Catch:{ Throwable -> 0x00c7 }
    L_0x0027:
        r2 = java.lang.System.currentTimeMillis();
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = r2 / r4;
        r4 = 604800; // 0x93a80 float:8.47505E-40 double:2.98811E-318;
        r8 = r2 - r4;
        r12 = new java.lang.StringBuilder;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "created_time > ";
        r1 = r1.append(r2);
        r1 = r1.append(r8);
        r2 = " AND ";
        r1 = r1.append(r2);
        r2 = "dismissed";
        r1 = r1.append(r2);
        r2 = " = 0 AND ";
        r1 = r1.append(r2);
        r2 = "opened";
        r1 = r1.append(r2);
        r2 = " = 0 AND ";
        r1 = r1.append(r2);
        r2 = "is_summary";
        r1 = r1.append(r2);
        r2 = " = 0";
        r1 = r1.append(r2);
        r1 = r1.toString();
        r12.<init>(r1);
        skipVisibleNotifications(r15, r12);
        r1 = com.onesignal.OneSignal.LOG_LEVEL.INFO;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Querying DB for notfs to restore: ";
        r2 = r2.append(r3);
        r3 = r12.toString();
        r2 = r2.append(r3);
        r2 = r2.toString();
        com.onesignal.OneSignal.Log(r1, r2);
        r10 = 0;
        r0 = r11.getReadableDbWithRetries();	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r1 = "notification";	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r2 = COLUMNS_FOR_RESTORE;	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r3 = r12.toString();	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r4 = 0;	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r5 = 0;	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r6 = 0;	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r7 = "_id ASC";	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r10 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r1 = 100;	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        showNotifications(r15, r10, r1);	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        if (r10 == 0) goto L_0x0004;
    L_0x00bc:
        r1 = r10.isClosed();
        if (r1 != 0) goto L_0x0004;
    L_0x00c2:
        r10.close();
        goto L_0x0004;
    L_0x00c7:
        r13 = move-exception;
        r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;
        r2 = "Error closing transaction! ";
        com.onesignal.OneSignal.Log(r1, r2, r13);
        goto L_0x0027;
    L_0x00d2:
        r13 = move-exception;
        r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        r2 = "Error deleting old notification records! ";	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        com.onesignal.OneSignal.Log(r1, r2, r13);	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        if (r14 == 0) goto L_0x0027;
    L_0x00dd:
        r14.endTransaction();	 Catch:{ Throwable -> 0x00d2, all -> 0x00ed, Throwable -> 0x00e2 }
        goto L_0x0027;
    L_0x00e2:
        r13 = move-exception;
        r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;
        r2 = "Error closing transaction! ";
        com.onesignal.OneSignal.Log(r1, r2, r13);
        goto L_0x0027;
    L_0x00ed:
        r1 = move-exception;
        if (r14 == 0) goto L_0x00f3;
    L_0x00f0:
        r14.endTransaction();	 Catch:{ Throwable -> 0x00f4 }
    L_0x00f3:
        throw r1;
    L_0x00f4:
        r13 = move-exception;
        r2 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;
        r3 = "Error closing transaction! ";
        com.onesignal.OneSignal.Log(r2, r3, r13);
        goto L_0x00f3;
    L_0x00fe:
        r13 = move-exception;
        r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        r2 = "Error restoring notification records! ";	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        com.onesignal.OneSignal.Log(r1, r2, r13);	 Catch:{ Throwable -> 0x00fe, all -> 0x0114 }
        if (r10 == 0) goto L_0x0004;
    L_0x0109:
        r1 = r10.isClosed();
        if (r1 != 0) goto L_0x0004;
    L_0x010f:
        r10.close();
        goto L_0x0004;
    L_0x0114:
        r1 = move-exception;
        if (r10 == 0) goto L_0x0120;
    L_0x0117:
        r2 = r10.isClosed();
        if (r2 != 0) goto L_0x0120;
    L_0x011d:
        r10.close();
    L_0x0120:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.NotificationRestorer.restore(android.content.Context):void");
    }

    NotificationRestorer() {
    }

    static void asyncRestore(final Context context) {
        new Thread(new Runnable() {
            public void run() {
                Thread.currentThread().setPriority(10);
                NotificationRestorer.restore(context);
            }
        }, "OS_RESTORE_NOTIFS").start();
    }

    private static void skipVisibleNotifications(Context context, StringBuilder dbQuerySelection) {
        if (VERSION.SDK_INT >= 23) {
            NotificationManager notifManager = (NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME);
            if (notifManager != null) {
                try {
                    StatusBarNotification[] activeNotifs = notifManager.getActiveNotifications();
                    if (activeNotifs.length != 0) {
                        ArrayList<Integer> activeNotifIds = new ArrayList();
                        for (StatusBarNotification activeNotif : activeNotifs) {
                            activeNotifIds.add(Integer.valueOf(activeNotif.getId()));
                        }
                        dbQuerySelection.append(" AND android_notification_id NOT IN (").append(TextUtils.join(",", activeNotifIds)).append(")");
                    }
                } catch (Throwable th) {
                }
            }
        }
    }

    static void showNotifications(Context context, Cursor cursor, int delay) {
        if (cursor.moveToFirst()) {
            boolean useExtender = NotificationExtenderService.getIntent(context) != null;
            do {
                if (useExtender) {
                    Intent intent = NotificationExtenderService.getIntent(context);
                    addRestoreExtras(intent, cursor);
                    JobIntentService.enqueueWork(context, intent.getComponent(), 2071862121, intent);
                } else {
                    JobIntentService.enqueueWork(context, new ComponentName(context, RestoreJobService.class), 2071862122, addRestoreExtras(new Intent(), cursor));
                }
                if (delay > 0) {
                    OSUtils.sleep(delay);
                }
            } while (cursor.moveToNext());
        }
    }

    private static Intent addRestoreExtras(Intent intent, Cursor cursor) {
        int existingId = cursor.getInt(cursor.getColumnIndex(NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID));
        String fullData = cursor.getString(cursor.getColumnIndex(NotificationTable.COLUMN_NAME_FULL_DATA));
        intent.putExtra("json_payload", fullData).putExtra("android_notif_id", existingId).putExtra("restoring", true).putExtra(Param.TIMESTAMP, Long.valueOf(cursor.getLong(cursor.getColumnIndex(NotificationTable.COLUMN_NAME_CREATED_TIME))));
        return intent;
    }

    static void startDelayedRestoreTaskFromReceiver(Context context) {
        if (VERSION.SDK_INT >= 26) {
            OneSignal.Log(LOG_LEVEL.INFO, "scheduleRestoreKickoffJob");
            ((JobScheduler) context.getSystemService("jobscheduler")).schedule(new Builder(RESTORE_KICKOFF_REQUEST_CODE, new ComponentName(context, RestoreKickoffJobService.class)).setOverrideDeadline(15000).setMinimumLatency(15000).build());
            return;
        }
        OneSignal.Log(LOG_LEVEL.INFO, "scheduleRestoreKickoffAlarmTask");
        Intent intentForService = new Intent();
        intentForService.setComponent(new ComponentName(context.getPackageName(), NotificationRestoreService.class.getName()));
        long scheduleTime = System.currentTimeMillis() + 15000;
        ((AlarmManager) context.getSystemService("alarm")).set(1, scheduleTime, PendingIntent.getService(context, RESTORE_KICKOFF_REQUEST_CODE, intentForService, 268435456));
    }
}
