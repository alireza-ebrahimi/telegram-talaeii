package com.onesignal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.util.ArrayList;
import java.util.List;

public class OneSignalDbHelper extends SQLiteOpenHelper {
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "OneSignal.db";
    private static final int DATABASE_VERSION = 2;
    private static final int DB_OPEN_RETRY_BACKOFF = 400;
    private static final int DB_OPEN_RETRY_MAX = 5;
    private static final String INT_TYPE = " INTEGER";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE notification (_id INTEGER PRIMARY KEY,notification_id TEXT,android_notification_id INTEGER,group_id TEXT,collapse_id TEXT,is_summary INTEGER DEFAULT 0,opened INTEGER DEFAULT 0,dismissed INTEGER DEFAULT 0,title TEXT,message TEXT,full_data TEXT,created_time TIMESTAMP DEFAULT (strftime('%s', 'now')));";
    private static final String SQL_INDEX_ENTRIES = "CREATE INDEX notification_notification_id_idx ON notification(notification_id); CREATE INDEX notification_android_notification_id_idx ON notification(android_notification_id); CREATE INDEX notification_group_id_idx ON notification(group_id); CREATE INDEX notification_collapse_id_idx ON notification(collapse_id); CREATE INDEX notification_created_time_idx ON notification(created_time); ";
    private static final String TEXT_TYPE = " TEXT";
    private static OneSignalDbHelper sInstance;

    private OneSignalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public static synchronized OneSignalDbHelper getInstance(Context context) {
        OneSignalDbHelper oneSignalDbHelper;
        synchronized (OneSignalDbHelper.class) {
            if (sInstance == null) {
                sInstance = new OneSignalDbHelper(context.getApplicationContext());
            }
            oneSignalDbHelper = sInstance;
        }
        return oneSignalDbHelper;
    }

    synchronized SQLiteDatabase getWritableDbWithRetries() {
        int count = 0;
        while (true) {
            try {
                break;
            } catch (Throwable th) {
                count++;
                if (count < 5) {
                    SystemClock.sleep((long) (count * 400));
                }
            }
        }
        return getWritableDatabase();
    }

    synchronized SQLiteDatabase getReadableDbWithRetries() {
        int count = 0;
        while (true) {
            try {
                break;
            } catch (Throwable th) {
                count++;
                if (count < 5) {
                    SystemClock.sleep((long) (count * 400));
                }
            }
        }
        return getReadableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_INDEX_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            internalOnUpgrade(db, oldVersion, newVersion);
        } catch (SQLiteException e) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Error in upgrade, migration may have already run! Skipping!", e);
        }
    }

    private static void internalOnUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE notification ADD COLUMN collapse_id TEXT;");
            db.execSQL(NotificationTable.INDEX_CREATE_GROUP_ID);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        OneSignal.Log(LOG_LEVEL.WARN, "SDK version rolled back! Clearing OneSignal.db as it could be in an unexpected state.");
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        try {
            List<String> tables = new ArrayList(cursor.getCount());
            while (cursor.moveToNext()) {
                tables.add(cursor.getString(0));
            }
            for (String table : tables) {
                if (!table.startsWith("sqlite_")) {
                    db.execSQL("DROP TABLE IF EXISTS " + table);
                }
            }
            onCreate(db);
        } finally {
            cursor.close();
        }
    }
}
