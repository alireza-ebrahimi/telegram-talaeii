package com.onesignal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationManagerCompat;
import com.onesignal.OneSignal.LOG_LEVEL;
import org.json.JSONArray;
import org.json.JSONObject;

class NotificationOpenedProcessor {
    NotificationOpenedProcessor() {
    }

    static void processFromContext(Context context, Intent intent) {
        if (isOneSignalIntent(intent)) {
            handleDismissFromActionButtonPress(context, intent);
            processIntent(context, intent);
        }
    }

    private static boolean isOneSignalIntent(Intent intent) {
        return intent.hasExtra("onesignal_data") || intent.hasExtra("summary") || intent.hasExtra("notificationId");
    }

    private static void handleDismissFromActionButtonPress(Context context, Intent intent) {
        if (intent.getBooleanExtra("action_button", false)) {
            NotificationManagerCompat.from(context).cancel(intent.getIntExtra("notificationId", 0));
            context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        }
    }

    static void processIntent(Context context, Intent intent) {
        String summaryGroup = intent.getStringExtra("summary");
        boolean dismissed = intent.getBooleanExtra(NotificationTable.COLUMN_NAME_DISMISSED, false);
        JSONArray dataArray = null;
        if (!dismissed) {
            try {
                JSONObject jsonData = new JSONObject(intent.getStringExtra("onesignal_data"));
                jsonData.put("notificationId", intent.getIntExtra("notificationId", 0));
                intent.putExtra("onesignal_data", jsonData.toString());
                dataArray = NotificationBundleProcessor.newJsonArray(new JSONObject(intent.getStringExtra("onesignal_data")));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        SQLiteDatabase writableDb = null;
        try {
            writableDb = OneSignalDbHelper.getInstance(context).getWritableDbWithRetries();
            writableDb.beginTransaction();
            if (!(dismissed || summaryGroup == null)) {
                addChildNotifications(dataArray, summaryGroup, writableDb);
            }
            markNotificationsConsumed(context, intent, writableDb);
            if (summaryGroup == null) {
                String group = intent.getStringExtra("grp");
                if (group != null) {
                    NotificationSummaryManager.updateSummaryNotificationAfterChildRemoved(context, writableDb, group, dismissed);
                }
            }
            writableDb.setTransactionSuccessful();
            if (writableDb != null) {
                try {
                    writableDb.endTransaction();
                } catch (Throwable t2) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t2);
                }
            }
        } catch (Exception e) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Error processing notification open or dismiss record! ", e);
            if (writableDb != null) {
                writableDb.endTransaction();
            }
        } catch (Throwable t22) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", t22);
        }
        if (!dismissed) {
            OneSignal.handleNotificationOpen(context, dataArray, intent.getBooleanExtra("from_alert", false));
        }
    }

    private static void addChildNotifications(JSONArray dataArray, String summaryGroup, SQLiteDatabase writableDb) {
        SQLiteDatabase sQLiteDatabase = writableDb;
        Cursor cursor = sQLiteDatabase.query(NotificationTable.TABLE_NAME, new String[]{NotificationTable.COLUMN_NAME_FULL_DATA}, "group_id = ? AND dismissed = 0 AND opened = 0 AND is_summary = 0", new String[]{summaryGroup}, null, null, null);
        if (cursor.getCount() > 1) {
            cursor.moveToFirst();
            do {
                try {
                    dataArray.put(new JSONObject(cursor.getString(cursor.getColumnIndex(NotificationTable.COLUMN_NAME_FULL_DATA))));
                } catch (Throwable th) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "Could not parse JSON of sub notification in group: " + summaryGroup);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private static void markNotificationsConsumed(Context context, Intent intent, SQLiteDatabase writableDb) {
        String whereStr;
        String[] whereArgs = null;
        if (intent.getStringExtra("summary") != null) {
            whereStr = "group_id = ?";
            whereArgs = new String[]{intent.getStringExtra("summary")};
        } else {
            whereStr = "android_notification_id = " + intent.getIntExtra("notificationId", 0);
        }
        writableDb.update(NotificationTable.TABLE_NAME, newContentValuesWithConsumed(intent), whereStr, whereArgs);
        BadgeCountUpdater.update(writableDb, context);
    }

    private static ContentValues newContentValuesWithConsumed(Intent intent) {
        ContentValues values = new ContentValues();
        if (intent.getBooleanExtra(NotificationTable.COLUMN_NAME_DISMISSED, false)) {
            values.put(NotificationTable.COLUMN_NAME_DISMISSED, Integer.valueOf(1));
        } else {
            values.put(NotificationTable.COLUMN_NAME_OPENED, Integer.valueOf(1));
        }
        return values;
    }
}
