package org.telegram.customization.fetch;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

final class DatabaseHelper extends SQLiteOpenHelper {
    private static final String COLUMN_DOWNLOADED_BYTES = "_written_bytes";
    private static final String COLUMN_ERROR = "_error";
    private static final String COLUMN_FILEPATH = "_file_path";
    private static final String COLUMN_FILE_SIZE = "_file_size";
    private static final String COLUMN_HEADERS = "_headers";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRIORITY = "_priority";
    private static final String COLUMN_STATUS = "_status";
    private static final String COLUMN_URL = "_url";
    private static final String DB_NAME = "com_tonyodev_fetch.db";
    static final int EMPTY_COLUMN_VALUE = -1;
    static final int INDEX_COLUMN_DOWNLOADED_BYTES = 5;
    static final int INDEX_COLUMN_ERROR = 7;
    static final int INDEX_COLUMN_FILEPATH = 2;
    static final int INDEX_COLUMN_FILE_SIZE = 6;
    static final int INDEX_COLUMN_HEADERS = 4;
    static final int INDEX_COLUMN_ID = 0;
    static final int INDEX_COLUMN_PRIORITY = 8;
    static final int INDEX_COLUMN_STATUS = 3;
    static final int INDEX_COLUMN_URL = 1;
    private static final String TABLE_NAME = "requests";
    private static final int VERSION = 2;
    private static DatabaseHelper databaseHelper;
    private final SQLiteDatabase db = getWritableDatabase();
    private boolean loggingEnabled = true;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE requests ( _id INTEGER PRIMARY KEY NOT NULL, _url TEXT NOT NULL, _file_path TEXT NOT NULL, _status INTEGER NOT NULL, _headers TEXT NOT NULL, _written_bytes INTEGER NOT NULL, _file_size INTEGER NOT NULL, _error INTEGER NOT NULL, _priority INTEGER NOT NULL, unique( _file_path ) )");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("CREATE UNIQUE INDEX table_unique ON requests ( _file_path)");
                return;
            default:
                return;
        }
    }

    static synchronized DatabaseHelper getInstance(Context context) {
        DatabaseHelper databaseHelper;
        synchronized (DatabaseHelper.class) {
            if (databaseHelper != null) {
                databaseHelper = databaseHelper;
            } else if (context == null) {
                throw new NullPointerException("Context cannot be null");
            } else {
                databaseHelper = new DatabaseHelper(context.getApplicationContext());
                databaseHelper = databaseHelper;
            }
        }
        return databaseHelper;
    }

    synchronized boolean insert(long id, String url, String filePath, int status, String headers, long downloadedBytes, long fileSize, int priority, int error) {
        deleteUrl(url, filePath);
        return insert(getInsertStatementOpen() + getRowInsertStatement(id, url, filePath, status, headers, downloadedBytes, fileSize, priority, error) + getInsertStatementClose());
    }

    String getInsertStatementOpen() {
        return "INSERT INTO requests ( _id, _url, _file_path, _status, _headers, _written_bytes, _file_size, _error, _priority ) VALUES ";
    }

    String getRowInsertStatement(long id, String url, String filePath, int status, String headers, long downloadedBytes, long fileSize, int priority, int error) {
        return "( " + id + ", " + DatabaseUtils.sqlEscapeString(url) + ", " + DatabaseUtils.sqlEscapeString(filePath) + ", " + status + ", " + DatabaseUtils.sqlEscapeString(headers) + ", " + downloadedBytes + ", " + fileSize + ", " + error + ", " + priority + " )";
    }

    String getInsertStatementClose() {
        return ";";
    }

    synchronized boolean insert(java.lang.String r7) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:org.telegram.customization.fetch.DatabaseHelper.insert(java.lang.String):boolean. bs: [B:8:0x0016, B:29:0x0056]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r6 = this;
        monitor-enter(r6);
        r1 = 0;
        if (r7 != 0) goto L_0x0007;
    L_0x0004:
        r2 = r1;
    L_0x0005:
        monitor-exit(r6);
        return r2;
    L_0x0007:
        r3 = r6.db;	 Catch:{ Exception -> 0x003b }
        r3.beginTransaction();	 Catch:{ Exception -> 0x003b }
        r3 = r6.db;	 Catch:{ Exception -> 0x003b }
        r3.execSQL(r7);	 Catch:{ Exception -> 0x003b }
        r3 = r6.db;	 Catch:{ Exception -> 0x003b }
        r3.setTransactionSuccessful();	 Catch:{ Exception -> 0x003b }
        r3 = r6.db;	 Catch:{ SQLiteException -> 0x001e }
        r3.endTransaction();	 Catch:{ SQLiteException -> 0x001e }
        r1 = 1;
        r2 = r1;
        goto L_0x0005;
    L_0x001e:
        r0 = move-exception;
        r3 = r6.loggingEnabled;	 Catch:{ all -> 0x0038 }
        if (r3 == 0) goto L_0x0026;	 Catch:{ all -> 0x0038 }
    L_0x0023:
        r0.printStackTrace();	 Catch:{ all -> 0x0038 }
    L_0x0026:
        r3 = new org.telegram.customization.fetch.exception.EnqueueException;	 Catch:{ all -> 0x0038 }
        r4 = r0.getMessage();	 Catch:{ all -> 0x0038 }
        r5 = r0.getMessage();	 Catch:{ all -> 0x0038 }
        r5 = org.telegram.customization.fetch.ErrorUtils.getCode(r5);	 Catch:{ all -> 0x0038 }
        r3.<init>(r4, r5);	 Catch:{ all -> 0x0038 }
        throw r3;	 Catch:{ all -> 0x0038 }
    L_0x0038:
        r3 = move-exception;
        monitor-exit(r6);
        throw r3;
    L_0x003b:
        r0 = move-exception;
        r3 = r6.loggingEnabled;	 Catch:{ all -> 0x0055 }
        if (r3 == 0) goto L_0x0043;	 Catch:{ all -> 0x0055 }
    L_0x0040:
        r0.printStackTrace();	 Catch:{ all -> 0x0055 }
    L_0x0043:
        r3 = new org.telegram.customization.fetch.exception.EnqueueException;	 Catch:{ all -> 0x0055 }
        r4 = r0.getMessage();	 Catch:{ all -> 0x0055 }
        r5 = r0.getMessage();	 Catch:{ all -> 0x0055 }
        r5 = org.telegram.customization.fetch.ErrorUtils.getCode(r5);	 Catch:{ all -> 0x0055 }
        r3.<init>(r4, r5);	 Catch:{ all -> 0x0055 }
        throw r3;	 Catch:{ all -> 0x0055 }
    L_0x0055:
        r3 = move-exception;
        r4 = r6.db;	 Catch:{ SQLiteException -> 0x005d }
        r4.endTransaction();	 Catch:{ SQLiteException -> 0x005d }
        r1 = 1;
        throw r3;	 Catch:{ all -> 0x0038 }
    L_0x005d:
        r0 = move-exception;	 Catch:{ all -> 0x0038 }
        r3 = r6.loggingEnabled;	 Catch:{ all -> 0x0038 }
        if (r3 == 0) goto L_0x0065;	 Catch:{ all -> 0x0038 }
    L_0x0062:
        r0.printStackTrace();	 Catch:{ all -> 0x0038 }
    L_0x0065:
        r3 = new org.telegram.customization.fetch.exception.EnqueueException;	 Catch:{ all -> 0x0038 }
        r4 = r0.getMessage();	 Catch:{ all -> 0x0038 }
        r5 = r0.getMessage();	 Catch:{ all -> 0x0038 }
        r5 = org.telegram.customization.fetch.ErrorUtils.getCode(r5);	 Catch:{ all -> 0x0038 }
        r3.<init>(r4, r5);	 Catch:{ all -> 0x0038 }
        throw r3;	 Catch:{ all -> 0x0038 }
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.fetch.DatabaseHelper.insert(java.lang.String):boolean");
    }

    synchronized boolean pause(long id) {
        boolean paused;
        paused = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _status = 902 WHERE _id = " + id + " AND " + COLUMN_STATUS + " != " + FetchConst.STATUS_DONE + " AND " + COLUMN_STATUS + " != " + FetchConst.STATUS_ERROR);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        Cursor cursor = null;
        try {
            this.db.endTransaction();
            cursor = this.db.rawQuery("SELECT _id FROM requests WHERE _id = " + id + " AND " + COLUMN_STATUS + " = " + FetchConst.STATUS_PAUSED, null);
            if (cursor != null && cursor.getCount() > 0) {
                paused = true;
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return paused;
    }

    synchronized boolean resume(long id) {
        boolean resumed;
        resumed = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _status = 900 WHERE _id = " + id + " AND " + COLUMN_STATUS + " != " + FetchConst.STATUS_DONE + " AND " + COLUMN_STATUS + " != " + FetchConst.STATUS_ERROR);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        Cursor cursor = null;
        try {
            this.db.endTransaction();
            cursor = this.db.rawQuery("SELECT _id FROM requests WHERE _id = " + id + " AND " + COLUMN_STATUS + " = " + FetchConst.STATUS_QUEUED, null);
            if (cursor != null && cursor.getCount() > 0) {
                resumed = true;
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return resumed;
    }

    synchronized boolean updateStatus(long id, int status, int error) {
        boolean updated;
        updated = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _status = " + status + ", " + COLUMN_ERROR + " = " + error + " WHERE " + COLUMN_ID + " = " + id);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            this.db.endTransaction();
            updated = true;
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
        return updated;
    }

    synchronized boolean updateFileBytes(long id, long downloadedBytes, long fileSize) {
        boolean updated;
        updated = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _file_size = " + fileSize + ", " + COLUMN_DOWNLOADED_BYTES + " = " + downloadedBytes + " WHERE " + COLUMN_ID + " = " + id);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            this.db.endTransaction();
            updated = true;
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
        return updated;
    }

    synchronized boolean delete(long id) {
        boolean removed;
        removed = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("DELETE FROM requests WHERE _id = " + id);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            this.db.endTransaction();
            removed = true;
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
        return removed;
    }

    synchronized boolean deleteUrl(String url, String filePath) {
        boolean removed;
        removed = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("DELETE FROM requests WHERE _url = " + DatabaseUtils.sqlEscapeString(url) + " OR " + COLUMN_FILEPATH + " = " + DatabaseUtils.sqlEscapeString(filePath));
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            this.db.endTransaction();
            removed = true;
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
        return removed;
    }

    synchronized boolean deleteAll() {
        boolean removed;
        removed = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("DELETE FROM requests");
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            this.db.endTransaction();
            removed = true;
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
        return removed;
    }

    synchronized boolean setPriority(long id, int priority) {
        boolean updated;
        updated = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _priority = " + priority + " WHERE " + COLUMN_ID + " = " + id);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            this.db.endTransaction();
            updated = true;
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
        return updated;
    }

    synchronized boolean retry(long id) {
        boolean updated;
        updated = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _status = 900, _error = -1 WHERE _id = " + id + " AND " + COLUMN_STATUS + " = " + FetchConst.STATUS_ERROR);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        Cursor cursor = null;
        try {
            this.db.endTransaction();
            cursor = this.db.rawQuery("SELECT _id FROM requests WHERE _id = " + id + " AND " + COLUMN_STATUS + " = " + FetchConst.STATUS_QUEUED, null);
            if (cursor != null && cursor.getCount() > 0) {
                updated = true;
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return updated;
    }

    synchronized boolean updateUrl(long id, String url) {
        boolean updated;
        updated = false;
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _url = " + DatabaseUtils.sqlEscapeString(url) + " WHERE " + COLUMN_ID + " = " + id);
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        Cursor cursor = null;
        try {
            this.db.endTransaction();
            cursor = this.db.rawQuery("SELECT _id FROM requests WHERE _id = " + id + " AND " + COLUMN_URL + " = " + DatabaseUtils.sqlEscapeString(url), null);
            if (cursor != null && cursor.getCount() > 0) {
                updated = true;
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return updated;
    }

    synchronized Cursor get(long id) {
        Cursor cursor = null;
        synchronized (this) {
            try {
                cursor = this.db.rawQuery("SELECT * FROM requests WHERE _id = " + id, null);
            } catch (SQLiteException e) {
                if (this.loggingEnabled) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    synchronized Cursor get() {
        Cursor cursor = null;
        synchronized (this) {
            try {
                cursor = this.db.rawQuery("SELECT * FROM requests", null);
            } catch (SQLiteException e) {
                if (this.loggingEnabled) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    synchronized Cursor get(long[] ids) {
        Cursor cursor = null;
        synchronized (this) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append('(');
                if (ids.length > 0) {
                    for (long id : ids) {
                        stringBuilder.append(id).append(',');
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                stringBuilder.append(')');
                cursor = this.db.rawQuery("SELECT * FROM requests WHERE _id IN " + stringBuilder.toString(), null);
            } catch (SQLiteException e) {
                if (this.loggingEnabled) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    synchronized Cursor getByStatus(int status) {
        Cursor cursor = null;
        synchronized (this) {
            try {
                cursor = this.db.rawQuery("SELECT * FROM requests WHERE _status = " + status, null);
            } catch (SQLiteException e) {
                if (this.loggingEnabled) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    synchronized Cursor getByUrlAndFilePath(String url, String filePath) {
        Cursor cursor = null;
        synchronized (this) {
            try {
                cursor = this.db.rawQuery("SELECT * FROM requests WHERE _url = " + DatabaseUtils.sqlEscapeString(url) + " AND " + COLUMN_FILEPATH + " = " + DatabaseUtils.sqlEscapeString(filePath) + " LIMIT 1", null);
            } catch (SQLiteException e) {
                if (this.loggingEnabled) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    synchronized Cursor getNextPendingRequest() {
        Cursor cursor;
        cursor = this.db.rawQuery("SELECT * FROM requests WHERE _status = 900 AND _priority = 601 LIMIT 1", null);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) {
                cursor.close();
            }
            cursor = this.db.rawQuery("SELECT * FROM requests WHERE _status = 900 LIMIT 1", null);
        }
        return cursor;
    }

    synchronized boolean hasPendingRequests() {
        boolean hasPending;
        Cursor cursor = this.db.rawQuery("SELECT _id FROM requests WHERE _status = 900 LIMIT 1", null);
        hasPending = false;
        if (cursor != null && cursor.getCount() > 0) {
            hasPending = true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return hasPending;
    }

    synchronized void verifyOK() {
        try {
            this.db.beginTransaction();
            this.db.execSQL("UPDATE requests SET _status = 900 WHERE _status = 901");
            this.db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
        }
        try {
            this.db.endTransaction();
        } catch (SQLiteException e2) {
            if (this.loggingEnabled) {
                e2.printStackTrace();
            }
        }
    }

    synchronized void clean() {
        Cursor cursor = this.db.rawQuery("SELECT _id, _file_path FROM requests WHERE _status = 903", null);
        if (cursor != null) {
            if (cursor.getCount() < 1) {
                cursor.close();
            } else {
                try {
                    this.db.beginTransaction();
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        String destinationUri = cursor.getString(cursor.getColumnIndex(COLUMN_FILEPATH));
                        if (!(destinationUri == null || Utils.fileExist(destinationUri))) {
                            this.db.execSQL("UPDATE requests SET _status = 904, _error = -111 WHERE _id = " + cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                        }
                        cursor.moveToNext();
                    }
                    this.db.setTransactionSuccessful();
                } catch (SQLiteException e) {
                    if (this.loggingEnabled) {
                        e.printStackTrace();
                    }
                }
                try {
                    this.db.endTransaction();
                } catch (SQLiteException e2) {
                    if (this.loggingEnabled) {
                        e2.printStackTrace();
                    }
                } finally {
                    cursor.close();
                }
            }
        }
    }

    synchronized void setLoggingEnabled(boolean enabled) {
        this.loggingEnabled = enabled;
    }

    synchronized int getCountAll() {
        int ans;
        ans = -1;
        Cursor cursor = null;
        try {
            cursor = this.db.rawQuery("SELECT COUNT(*) FROM requests", null);
            ans = cursor.getInt(0);
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteException e) {
            if (this.loggingEnabled) {
                e.printStackTrace();
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return ans;
    }
}
