package com.google.android.gms.internal.measurement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class zzen {
    static void zza(zzfh zzfh, SQLiteDatabase sQLiteDatabase) {
        if (zzfh == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzfh.zziv().log("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzfh.zziv().log("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzfh.zziv().log("Failed to turn on database read permission for owner");
        }
        if (!file.setWritable(true, true)) {
            zzfh.zziv().log("Failed to turn on database write permission for owner");
        }
    }

    static void zza(zzfh zzfh, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) {
        int i = 0;
        if (zzfh == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzfh, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        if (zzfh == null) {
            try {
                throw new IllegalArgumentException("Monitor must not be null");
            } catch (SQLiteException e) {
                zzfh.zzis().zzg("Failed to verify columns on table that was just created", str);
                throw e;
            }
        }
        Iterable zzb = zzb(sQLiteDatabase, str);
        String[] split = str3.split(",");
        int length = split.length;
        int i2 = 0;
        while (i2 < length) {
            String str4 = split[i2];
            if (zzb.remove(str4)) {
                i2++;
            } else {
                throw new SQLiteException(new StringBuilder((String.valueOf(str).length() + 35) + String.valueOf(str4).length()).append("Table ").append(str).append(" is missing required column: ").append(str4).toString());
            }
        }
        if (strArr != null) {
            while (i < strArr.length) {
                if (!zzb.remove(strArr[i])) {
                    sQLiteDatabase.execSQL(strArr[i + 1]);
                }
                i += 2;
            }
        }
        if (!zzb.isEmpty()) {
            zzfh.zziv().zze("Table has extra columns. table, columns", str, TextUtils.join(", ", zzb));
        }
    }

    private static boolean zza(zzfh zzfh, SQLiteDatabase sQLiteDatabase, String str) {
        Object e;
        Throwable th;
        Cursor cursor = null;
        if (zzfh == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Cursor query;
        try {
            SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
            query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
            try {
                boolean moveToFirst = query.moveToFirst();
                if (query == null) {
                    return moveToFirst;
                }
                query.close();
                return moveToFirst;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzfh.zziv().zze("Error querying for table", str, e);
                    if (query != null) {
                        query.close();
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzfh.zziv().zze("Error querying for table", str, e);
            if (query != null) {
                query.close();
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        Set<String> hashSet = new HashSet();
        Cursor rawQuery = sQLiteDatabase.rawQuery(new StringBuilder(String.valueOf(str).length() + 22).append("SELECT * FROM ").append(str).append(" LIMIT 0").toString(), null);
        try {
            Collections.addAll(hashSet, rawQuery.getColumnNames());
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }
}
