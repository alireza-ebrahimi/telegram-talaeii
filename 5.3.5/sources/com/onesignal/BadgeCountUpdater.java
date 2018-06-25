package com.onesignal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.onesignal.OneSignal.LOG_LEVEL;
import com.onesignal.shortcutbadger.ShortcutBadger;

class BadgeCountUpdater {
    private static int badgesEnabled = -1;

    BadgeCountUpdater() {
    }

    private static boolean areBadgeSettingsEnabled(Context context) {
        if (badgesEnabled == -1) {
            try {
                Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
                if (bundle != null) {
                    int i;
                    if ("DISABLE".equals(bundle.getString("com.onesignal.BadgeCount"))) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    badgesEnabled = i;
                } else {
                    badgesEnabled = 1;
                }
            } catch (Throwable t) {
                badgesEnabled = 0;
                OneSignal.Log(LOG_LEVEL.ERROR, "Error reading meta-data tag 'com.onesignal.BadgeCount'. Disabling badge setting.", t);
            }
            if (badgesEnabled != 1) {
                return false;
            }
            return true;
        } else if (badgesEnabled == 1) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean areBadgesEnabled(Context context) {
        return areBadgeSettingsEnabled(context) && OSUtils.areNotificationsEnabled(context);
    }

    static void update(SQLiteDatabase readableDb, Context context) {
        if (areBadgesEnabled(context)) {
            Cursor cursor = readableDb.query(NotificationTable.TABLE_NAME, null, "dismissed = 0 AND opened = 0 AND is_summary = 0 ", null, null, null, null);
            updateCount(cursor.getCount(), context);
            cursor.close();
        }
    }

    static void updateCount(int count, Context context) {
        if (areBadgeSettingsEnabled(context)) {
            try {
                ShortcutBadger.applyCountOrThrow(context, count);
            } catch (Throwable th) {
            }
        }
    }
}
