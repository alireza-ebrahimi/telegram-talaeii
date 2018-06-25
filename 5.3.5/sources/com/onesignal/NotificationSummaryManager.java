package com.onesignal;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;

class NotificationSummaryManager {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void restoreSummary(android.content.Context r11, java.lang.String r12) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x002d in list [B:4:0x0024]
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
        r2 = 0;
        r9 = com.onesignal.OneSignalDbHelper.getInstance(r11);
        r8 = 0;
        r1 = 1;
        r4 = new java.lang.String[r1];
        r4[r2] = r12;
        r0 = r9.getReadableDbWithRetries();	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r1 = "notification";	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r2 = com.onesignal.NotificationRestorer.COLUMNS_FOR_RESTORE;	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r3 = "group_id = ? AND dismissed = 0 AND opened = 0 AND is_summary = 0";	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r5 = 0;	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r6 = 0;	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r7 = 0;	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r8 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r1 = 0;	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        com.onesignal.NotificationRestorer.showNotifications(r11, r8, r1);	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        if (r8 == 0) goto L_0x002d;
    L_0x0024:
        r1 = r8.isClosed();
        if (r1 != 0) goto L_0x002d;
    L_0x002a:
        r8.close();
    L_0x002d:
        return;
    L_0x002e:
        r10 = move-exception;
        r1 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        r2 = "Error restoring notification records! ";	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        com.onesignal.OneSignal.Log(r1, r2, r10);	 Catch:{ Throwable -> 0x002e, all -> 0x0043 }
        if (r8 == 0) goto L_0x002d;
    L_0x0039:
        r1 = r8.isClosed();
        if (r1 != 0) goto L_0x002d;
    L_0x003f:
        r8.close();
        goto L_0x002d;
    L_0x0043:
        r1 = move-exception;
        if (r8 == 0) goto L_0x004f;
    L_0x0046:
        r2 = r8.isClosed();
        if (r2 != 0) goto L_0x004f;
    L_0x004c:
        r8.close();
    L_0x004f:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.NotificationSummaryManager.restoreSummary(android.content.Context, java.lang.String):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void updateSummaryNotificationAfterChildRemoved(android.content.Context r4, android.database.sqlite.SQLiteDatabase r5, java.lang.String r6, boolean r7) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0010 in list [B:5:0x0007]
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
        r0 = 0;
        r0 = internalUpdateSummaryNotificationAfterChildRemoved(r4, r5, r6, r7);	 Catch:{ Throwable -> 0x0011, all -> 0x0026 }
        if (r0 == 0) goto L_0x0010;
    L_0x0007:
        r2 = r0.isClosed();
        if (r2 != 0) goto L_0x0010;
    L_0x000d:
        r0.close();
    L_0x0010:
        return;
    L_0x0011:
        r1 = move-exception;
        r2 = com.onesignal.OneSignal.LOG_LEVEL.ERROR;	 Catch:{ Throwable -> 0x0011, all -> 0x0026 }
        r3 = "Error running updateSummaryNotificationAfterChildRemoved!";	 Catch:{ Throwable -> 0x0011, all -> 0x0026 }
        com.onesignal.OneSignal.Log(r2, r3, r1);	 Catch:{ Throwable -> 0x0011, all -> 0x0026 }
        if (r0 == 0) goto L_0x0010;
    L_0x001c:
        r2 = r0.isClosed();
        if (r2 != 0) goto L_0x0010;
    L_0x0022:
        r0.close();
        goto L_0x0010;
    L_0x0026:
        r2 = move-exception;
        if (r0 == 0) goto L_0x0032;
    L_0x0029:
        r3 = r0.isClosed();
        if (r3 != 0) goto L_0x0032;
    L_0x002f:
        r0.close();
    L_0x0032:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.NotificationSummaryManager.updateSummaryNotificationAfterChildRemoved(android.content.Context, android.database.sqlite.SQLiteDatabase, java.lang.String, boolean):void");
    }

    NotificationSummaryManager() {
    }

    static void updatePossibleDependentSummaryOnDismiss(Context context, SQLiteDatabase writableDb, int androidNotificationId) {
        Cursor cursor = writableDb.query(NotificationTable.TABLE_NAME, new String[]{"group_id"}, "android_notification_id = " + androidNotificationId, null, null, null, null);
        if (cursor.moveToFirst()) {
            String group = cursor.getString(cursor.getColumnIndex("group_id"));
            cursor.close();
            if (group != null) {
                updateSummaryNotificationAfterChildRemoved(context, writableDb, group, true);
                return;
            }
            return;
        }
        cursor.close();
    }

    private static Cursor internalUpdateSummaryNotificationAfterChildRemoved(Context context, SQLiteDatabase writableDb, String group, boolean dismissed) {
        Cursor cursor = writableDb.query(NotificationTable.TABLE_NAME, new String[]{NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID, NotificationTable.COLUMN_NAME_CREATED_TIME}, "group_id = ? AND dismissed = 0 AND opened = 0 AND is_summary = 0", new String[]{group}, null, null, "_id DESC");
        int notifsInGroup = cursor.getCount();
        if (notifsInGroup == 0) {
            cursor.close();
            Integer androidNotifId = getSummaryNotificationId(writableDb, group);
            if (androidNotifId != null) {
                ((NotificationManager) context.getSystemService(NotificationTable.TABLE_NAME)).cancel(androidNotifId.intValue());
                ContentValues values = new ContentValues();
                values.put(dismissed ? NotificationTable.COLUMN_NAME_DISMISSED : NotificationTable.COLUMN_NAME_OPENED, Integer.valueOf(1));
                writableDb.update(NotificationTable.TABLE_NAME, values, "android_notification_id = " + androidNotifId, null);
            }
        } else if (notifsInGroup == 1) {
            cursor.close();
            if (getSummaryNotificationId(writableDb, group) != null) {
                restoreSummary(context, group);
            }
        } else {
            try {
                cursor.moveToFirst();
                Long datetime = Long.valueOf(cursor.getLong(cursor.getColumnIndex(NotificationTable.COLUMN_NAME_CREATED_TIME)));
                cursor.close();
                if (getSummaryNotificationId(writableDb, group) != null) {
                    NotificationGenerationJob notifJob = new NotificationGenerationJob(context);
                    notifJob.restoring = true;
                    notifJob.shownTimeStamp = datetime;
                    JSONObject payload = new JSONObject();
                    payload.put("grp", group);
                    notifJob.jsonPayload = payload;
                    GenerateNotification.updateSummaryNotification(notifJob);
                }
            } catch (JSONException e) {
            }
        }
        return cursor;
    }

    private static Integer getSummaryNotificationId(SQLiteDatabase writableDb, String group) {
        Integer androidNotifId = null;
        Cursor cursor = null;
        try {
            SQLiteDatabase sQLiteDatabase = writableDb;
            cursor = sQLiteDatabase.query(NotificationTable.TABLE_NAME, new String[]{NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID}, "group_id = ? AND dismissed = 0 AND opened = 0 AND is_summary = 1", new String[]{group}, null, null, null);
            if (cursor.moveToFirst()) {
                androidNotifId = Integer.valueOf(cursor.getInt(cursor.getColumnIndex(NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID)));
                cursor.close();
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return androidNotifId;
            }
            cursor.close();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return null;
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }
}
