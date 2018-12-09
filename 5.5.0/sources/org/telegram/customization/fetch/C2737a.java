package org.telegram.customization.fetch;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: org.telegram.customization.fetch.a */
final class C2737a extends SQLiteOpenHelper {
    /* renamed from: a */
    private static C2737a f9027a;
    /* renamed from: b */
    private final SQLiteDatabase f9028b = getWritableDatabase();
    /* renamed from: c */
    private boolean f9029c = true;

    private C2737a(Context context) {
        super(context, "com_tonyodev_fetch.db", null, 2);
    }

    /* renamed from: a */
    static synchronized C2737a m12701a(Context context) {
        C2737a c2737a;
        synchronized (C2737a.class) {
            if (f9027a != null) {
                c2737a = f9027a;
            } else if (context == null) {
                throw new NullPointerException("Context cannot be null");
            } else {
                f9027a = new C2737a(context.getApplicationContext());
                c2737a = f9027a;
            }
        }
        return c2737a;
    }

    /* renamed from: a */
    synchronized Cursor m12702a(int i) {
        Cursor cursor = null;
        synchronized (this) {
            try {
                cursor = this.f9028b.rawQuery("SELECT * FROM requests WHERE _status = " + i, null);
            } catch (SQLiteException e) {
                if (this.f9029c) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    /* renamed from: a */
    String m12703a() {
        return "INSERT INTO requests ( _id, _url, _file_path, _status, _headers, _written_bytes, _file_size, _error, _priority ) VALUES ";
    }

    /* renamed from: a */
    synchronized void m12704a(boolean z) {
        this.f9029c = z;
    }

    /* renamed from: a */
    synchronized boolean m12705a(long j) {
        boolean z;
        Cursor cursor = null;
        synchronized (this) {
            z = false;
            try {
                this.f9028b.beginTransaction();
                this.f9028b.execSQL("UPDATE requests SET _status = 902 WHERE _id = " + j + " AND " + "_status" + " != " + 903 + " AND " + "_status" + " != " + 904);
                this.f9028b.setTransactionSuccessful();
            } catch (SQLiteException e) {
                if (this.f9029c) {
                    e.printStackTrace();
                }
            }
            try {
                this.f9028b.endTransaction();
                cursor = this.f9028b.rawQuery("SELECT _id FROM requests WHERE _id = " + j + " AND " + "_status" + " = " + 902, null);
                if (cursor != null && cursor.getCount() > 0) {
                    z = true;
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e2) {
                if (this.f9029c) {
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
        }
        return z;
    }

    /* renamed from: a */
    synchronized boolean m12706a(long j, int i) {
        boolean z;
        z = false;
        try {
            this.f9028b.beginTransaction();
            this.f9028b.execSQL("UPDATE requests SET _priority = " + i + " WHERE " + "_id" + " = " + j);
            this.f9028b.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.f9029c) {
                e.printStackTrace();
            }
        }
        try {
            this.f9028b.endTransaction();
            z = true;
        } catch (SQLiteException e2) {
            if (this.f9029c) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    /* renamed from: a */
    synchronized boolean m12707a(long j, int i, int i2) {
        boolean z;
        z = false;
        try {
            this.f9028b.beginTransaction();
            this.f9028b.execSQL("UPDATE requests SET _status = " + i + ", " + "_error" + " = " + i2 + " WHERE " + "_id" + " = " + j);
            this.f9028b.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.f9029c) {
                e.printStackTrace();
            }
        }
        try {
            this.f9028b.endTransaction();
            z = true;
        } catch (SQLiteException e2) {
            if (this.f9029c) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    /* renamed from: a */
    synchronized boolean m12708a(long j, long j2, long j3) {
        boolean z;
        z = false;
        try {
            this.f9028b.beginTransaction();
            this.f9028b.execSQL("UPDATE requests SET _file_size = " + j3 + ", " + "_written_bytes" + " = " + j2 + " WHERE " + "_id" + " = " + j);
            this.f9028b.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.f9029c) {
                e.printStackTrace();
            }
        }
        try {
            this.f9028b.endTransaction();
            z = true;
        } catch (SQLiteException e2) {
            if (this.f9029c) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    /* renamed from: a */
    synchronized boolean m12709a(long j, String str) {
        boolean z;
        Cursor cursor = null;
        synchronized (this) {
            z = false;
            try {
                this.f9028b.beginTransaction();
                this.f9028b.execSQL("UPDATE requests SET _url = " + DatabaseUtils.sqlEscapeString(str) + " WHERE " + "_id" + " = " + j);
                this.f9028b.setTransactionSuccessful();
            } catch (SQLiteException e) {
                if (this.f9029c) {
                    e.printStackTrace();
                }
            }
            try {
                this.f9028b.endTransaction();
                cursor = this.f9028b.rawQuery("SELECT _id FROM requests WHERE _id = " + j + " AND " + "_url" + " = " + DatabaseUtils.sqlEscapeString(str), null);
                if (cursor != null && cursor.getCount() > 0) {
                    z = true;
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e2) {
                if (this.f9029c) {
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
        }
        return z;
    }

    /* renamed from: a */
    synchronized boolean m12710a(long j, String str, String str2, int i, String str3, long j2, long j3, int i2, int i3) {
        m12712a(str, str2);
        return m12711a(m12703a() + m12714b(j, str, str2, i, str3, j2, j3, i2, i3) + m12713b());
    }

    /* renamed from: a */
    synchronized boolean m12711a(java.lang.String r4) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:org.telegram.customization.fetch.a.a(java.lang.String):boolean. bs: [B:7:0x0015, B:28:0x0054]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
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
        r3 = this;
        monitor-enter(r3);
        r0 = 0;
        if (r4 != 0) goto L_0x0006;
    L_0x0004:
        monitor-exit(r3);
        return r0;
    L_0x0006:
        r0 = r3.f9028b;	 Catch:{ Exception -> 0x0039 }
        r0.beginTransaction();	 Catch:{ Exception -> 0x0039 }
        r0 = r3.f9028b;	 Catch:{ Exception -> 0x0039 }
        r0.execSQL(r4);	 Catch:{ Exception -> 0x0039 }
        r0 = r3.f9028b;	 Catch:{ Exception -> 0x0039 }
        r0.setTransactionSuccessful();	 Catch:{ Exception -> 0x0039 }
        r0 = r3.f9028b;	 Catch:{ SQLiteException -> 0x001c }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x001c }
        r0 = 1;
        goto L_0x0004;
    L_0x001c:
        r0 = move-exception;
        r1 = r3.f9029c;	 Catch:{ all -> 0x0036 }
        if (r1 == 0) goto L_0x0024;	 Catch:{ all -> 0x0036 }
    L_0x0021:
        r0.printStackTrace();	 Catch:{ all -> 0x0036 }
    L_0x0024:
        r1 = new org.telegram.customization.fetch.b.b;	 Catch:{ all -> 0x0036 }
        r2 = r0.getMessage();	 Catch:{ all -> 0x0036 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0036 }
        r0 = org.telegram.customization.fetch.C2741b.m12726a(r0);	 Catch:{ all -> 0x0036 }
        r1.<init>(r2, r0);	 Catch:{ all -> 0x0036 }
        throw r1;	 Catch:{ all -> 0x0036 }
    L_0x0036:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x0039:
        r0 = move-exception;
        r1 = r3.f9029c;	 Catch:{ all -> 0x0053 }
        if (r1 == 0) goto L_0x0041;	 Catch:{ all -> 0x0053 }
    L_0x003e:
        r0.printStackTrace();	 Catch:{ all -> 0x0053 }
    L_0x0041:
        r1 = new org.telegram.customization.fetch.b.b;	 Catch:{ all -> 0x0053 }
        r2 = r0.getMessage();	 Catch:{ all -> 0x0053 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0053 }
        r0 = org.telegram.customization.fetch.C2741b.m12726a(r0);	 Catch:{ all -> 0x0053 }
        r1.<init>(r2, r0);	 Catch:{ all -> 0x0053 }
        throw r1;	 Catch:{ all -> 0x0053 }
    L_0x0053:
        r0 = move-exception;
        r1 = r3.f9028b;	 Catch:{ SQLiteException -> 0x005a }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x005a }
        throw r0;	 Catch:{ all -> 0x0036 }
    L_0x005a:
        r0 = move-exception;	 Catch:{ all -> 0x0036 }
        r1 = r3.f9029c;	 Catch:{ all -> 0x0036 }
        if (r1 == 0) goto L_0x0062;	 Catch:{ all -> 0x0036 }
    L_0x005f:
        r0.printStackTrace();	 Catch:{ all -> 0x0036 }
    L_0x0062:
        r1 = new org.telegram.customization.fetch.b.b;	 Catch:{ all -> 0x0036 }
        r2 = r0.getMessage();	 Catch:{ all -> 0x0036 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0036 }
        r0 = org.telegram.customization.fetch.C2741b.m12726a(r0);	 Catch:{ all -> 0x0036 }
        r1.<init>(r2, r0);	 Catch:{ all -> 0x0036 }
        throw r1;	 Catch:{ all -> 0x0036 }
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.customization.fetch.a.a(java.lang.String):boolean");
    }

    /* renamed from: a */
    synchronized boolean m12712a(String str, String str2) {
        boolean z;
        z = false;
        try {
            this.f9028b.beginTransaction();
            this.f9028b.execSQL("DELETE FROM requests WHERE _url = " + DatabaseUtils.sqlEscapeString(str) + " OR " + "_file_path" + " = " + DatabaseUtils.sqlEscapeString(str2));
            this.f9028b.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.f9029c) {
                e.printStackTrace();
            }
        }
        try {
            this.f9028b.endTransaction();
            z = true;
        } catch (SQLiteException e2) {
            if (this.f9029c) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    /* renamed from: b */
    String m12713b() {
        return ";";
    }

    /* renamed from: b */
    String m12714b(long j, String str, String str2, int i, String str3, long j2, long j3, int i2, int i3) {
        return "( " + j + ", " + DatabaseUtils.sqlEscapeString(str) + ", " + DatabaseUtils.sqlEscapeString(str2) + ", " + i + ", " + DatabaseUtils.sqlEscapeString(str3) + ", " + j2 + ", " + j3 + ", " + i3 + ", " + i2 + " )";
    }

    /* renamed from: b */
    synchronized boolean m12715b(long j) {
        boolean z;
        Cursor cursor = null;
        synchronized (this) {
            z = false;
            try {
                this.f9028b.beginTransaction();
                this.f9028b.execSQL("UPDATE requests SET _status = 900 WHERE _id = " + j + " AND " + "_status" + " != " + 903 + " AND " + "_status" + " != " + 904);
                this.f9028b.setTransactionSuccessful();
            } catch (SQLiteException e) {
                if (this.f9029c) {
                    e.printStackTrace();
                }
            }
            try {
                this.f9028b.endTransaction();
                cursor = this.f9028b.rawQuery("SELECT _id FROM requests WHERE _id = " + j + " AND " + "_status" + " = " + 900, null);
                if (cursor != null && cursor.getCount() > 0) {
                    z = true;
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e2) {
                if (this.f9029c) {
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
        }
        return z;
    }

    /* renamed from: c */
    synchronized boolean m12716c() {
        boolean z;
        z = false;
        try {
            this.f9028b.beginTransaction();
            this.f9028b.execSQL("DELETE FROM requests");
            this.f9028b.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.f9029c) {
                e.printStackTrace();
            }
        }
        try {
            this.f9028b.endTransaction();
            z = true;
        } catch (SQLiteException e2) {
            if (this.f9029c) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    /* renamed from: c */
    synchronized boolean m12717c(long j) {
        boolean z;
        z = false;
        try {
            this.f9028b.beginTransaction();
            this.f9028b.execSQL("DELETE FROM requests WHERE _id = " + j);
            this.f9028b.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.f9029c) {
                e.printStackTrace();
            }
        }
        try {
            this.f9028b.endTransaction();
            z = true;
        } catch (SQLiteException e2) {
            if (this.f9029c) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    /* renamed from: d */
    synchronized Cursor m12718d() {
        Cursor cursor = null;
        synchronized (this) {
            try {
                cursor = this.f9028b.rawQuery("SELECT * FROM requests", null);
            } catch (SQLiteException e) {
                if (this.f9029c) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    /* renamed from: d */
    synchronized boolean m12719d(long j) {
        boolean z;
        Cursor cursor = null;
        synchronized (this) {
            z = false;
            try {
                this.f9028b.beginTransaction();
                this.f9028b.execSQL("UPDATE requests SET _status = 900, _error = -1 WHERE _id = " + j + " AND " + "_status" + " = " + 904);
                this.f9028b.setTransactionSuccessful();
            } catch (SQLiteException e) {
                if (this.f9029c) {
                    e.printStackTrace();
                }
            }
            try {
                this.f9028b.endTransaction();
                cursor = this.f9028b.rawQuery("SELECT _id FROM requests WHERE _id = " + j + " AND " + "_status" + " = " + 900, null);
                if (cursor != null && cursor.getCount() > 0) {
                    z = true;
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e2) {
                if (this.f9029c) {
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
        }
        return z;
    }

    /* renamed from: e */
    synchronized Cursor m12720e() {
        Cursor rawQuery;
        rawQuery = this.f9028b.rawQuery("SELECT * FROM requests WHERE _status = 900 AND _priority = 601 LIMIT 1", null);
        if (rawQuery == null || rawQuery.getCount() <= 0) {
            if (rawQuery != null) {
                rawQuery.close();
            }
            rawQuery = this.f9028b.rawQuery("SELECT * FROM requests WHERE _status = 900 LIMIT 1", null);
        }
        return rawQuery;
    }

    /* renamed from: e */
    synchronized Cursor m12721e(long j) {
        Cursor cursor = null;
        synchronized (this) {
            try {
                cursor = this.f9028b.rawQuery("SELECT * FROM requests WHERE _id = " + j, null);
            } catch (SQLiteException e) {
                if (this.f9029c) {
                    e.printStackTrace();
                }
            }
        }
        return cursor;
    }

    /* renamed from: f */
    synchronized boolean m12722f() {
        boolean z;
        Cursor rawQuery = this.f9028b.rawQuery("SELECT _id FROM requests WHERE _status = 900 LIMIT 1", null);
        z = false;
        if (rawQuery != null && rawQuery.getCount() > 0) {
            z = true;
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
        return z;
    }

    /* renamed from: g */
    synchronized void m12723g() {
        try {
            this.f9028b.beginTransaction();
            this.f9028b.execSQL("UPDATE requests SET _status = 900 WHERE _status = 901");
            this.f9028b.setTransactionSuccessful();
        } catch (SQLiteException e) {
            if (this.f9029c) {
                e.printStackTrace();
            }
        }
        try {
            this.f9028b.endTransaction();
        } catch (SQLiteException e2) {
            if (this.f9029c) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: h */
    synchronized void m12724h() {
        Cursor rawQuery = this.f9028b.rawQuery("SELECT _id, _file_path FROM requests WHERE _status = 903", null);
        if (rawQuery != null) {
            if (rawQuery.getCount() < 1) {
                rawQuery.close();
            } else {
                try {
                    this.f9028b.beginTransaction();
                    rawQuery.moveToFirst();
                    while (!rawQuery.isAfterLast()) {
                        String string = rawQuery.getString(rawQuery.getColumnIndex("_file_path"));
                        if (!(string == null || C2756f.m12798e(string))) {
                            this.f9028b.execSQL("UPDATE requests SET _status = 904, _error = -111 WHERE _id = " + rawQuery.getLong(rawQuery.getColumnIndex("_id")));
                        }
                        rawQuery.moveToNext();
                    }
                    this.f9028b.setTransactionSuccessful();
                } catch (SQLiteException e) {
                    if (this.f9029c) {
                        e.printStackTrace();
                    }
                }
                try {
                    this.f9028b.endTransaction();
                } catch (SQLiteException e2) {
                    if (this.f9029c) {
                        e2.printStackTrace();
                    }
                } finally {
                    rawQuery.close();
                }
            }
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE requests ( _id INTEGER PRIMARY KEY NOT NULL, _url TEXT NOT NULL, _file_path TEXT NOT NULL, _status INTEGER NOT NULL, _headers TEXT NOT NULL, _written_bytes INTEGER NOT NULL, _file_size INTEGER NOT NULL, _error INTEGER NOT NULL, _priority INTEGER NOT NULL, unique( _file_path ) )");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        switch (i) {
            case 1:
                sQLiteDatabase.execSQL("CREATE UNIQUE INDEX table_unique ON requests ( _file_path)");
                return;
            default:
                return;
        }
    }
}
