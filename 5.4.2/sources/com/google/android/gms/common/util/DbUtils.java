package com.google.android.gms.common.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.stable.zzk;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Set;
import javax.annotation.Nullable;
import org.telegram.messenger.exoplayer2.C3446C;

public final class DbUtils {
    private DbUtils() {
    }

    public static void clearDatabase(SQLiteDatabase sQLiteDatabase) {
        zza(sQLiteDatabase, "table", "sqlite_sequence", "android_metadata");
        zza(sQLiteDatabase, "trigger", new String[0]);
        zza(sQLiteDatabase, "view", new String[0]);
    }

    public static long countCurrentRowBytes(Cursor cursor) {
        return countCurrentRowBytes(cursor, Charset.forName(C3446C.UTF8_NAME));
    }

    public static long countCurrentRowBytes(Cursor cursor, Charset charset) {
        long j = 0;
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            switch (cursor.getType(i)) {
                case 0:
                case 1:
                case 2:
                    j += 4;
                    break;
                case 3:
                    j += (long) cursor.getString(i).getBytes(charset).length;
                    break;
                case 4:
                    j += (long) cursor.getBlob(i).length;
                    break;
                default:
                    break;
            }
        }
        return j;
    }

    public static long getDatabaseSize(Context context, String str) {
        try {
            File databasePath = context.getDatabasePath(str);
            if (databasePath != null) {
                return databasePath.length();
            }
        } catch (SecurityException e) {
            String str2 = "DbUtils";
            String str3 = "Failed to get db size for ";
            String valueOf = String.valueOf(str);
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        return 0;
    }

    @Nullable
    public static Integer getIntegerFromCursor(Cursor cursor, int i) {
        return getIntegerFromCursor(cursor, i, null);
    }

    @Nullable
    public static Integer getIntegerFromCursor(Cursor cursor, int i, @Nullable Integer num) {
        return (i < 0 || cursor.isNull(i)) ? num : Integer.valueOf(cursor.getInt(i));
    }

    @Nullable
    public static Long getLongFromCursor(Cursor cursor, int i) {
        return getLongFromCursor(cursor, i, null);
    }

    @Nullable
    public static Long getLongFromCursor(Cursor cursor, int i, @Nullable Long l) {
        return (i < 0 || cursor.isNull(i)) ? l : Long.valueOf(cursor.getLong(i));
    }

    @Nullable
    public static String getStringFromCursor(Cursor cursor, int i) {
        return getStringFromCursor(cursor, i, null);
    }

    @Nullable
    public static String getStringFromCursor(Cursor cursor, int i, @Nullable String str) {
        return (i < 0 || cursor.isNull(i)) ? str : cursor.getString(i);
    }

    public static void putIntegerIntoContentValues(ContentValues contentValues, String str, @Nullable Integer num) {
        if (num != null) {
            contentValues.put(str, num);
        } else {
            contentValues.putNull(str);
        }
    }

    public static void putLongIntoContentValues(ContentValues contentValues, String str, @Nullable Long l) {
        if (l != null) {
            contentValues.put(str, l);
        } else {
            contentValues.putNull(str);
        }
    }

    public static void putStringIntoContentValues(ContentValues contentValues, String str, @Nullable String str2) {
        if (str2 != null) {
            contentValues.put(str, str2);
        } else {
            contentValues.putNull(str);
        }
    }

    private static void zza(SQLiteDatabase sQLiteDatabase, String str, String... strArr) {
        Throwable th;
        Throwable th2 = null;
        boolean z = "table".equals(str) || "view".equals(str) || "trigger".equals(str);
        Preconditions.checkArgument(z);
        Cursor query = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "type == ?", new String[]{str}, null, null, null);
        try {
            Set of = CollectionUtils.setOf((Object[]) strArr);
            while (query.moveToNext()) {
                String string = query.getString(0);
                if (!of.contains(string)) {
                    sQLiteDatabase.execSQL(new StringBuilder((String.valueOf(str).length() + 8) + String.valueOf(string).length()).append("DROP ").append(str).append(" '").append(string).append("'").toString());
                }
            }
            if (query != null) {
                query.close();
                return;
            }
            return;
        } catch (Throwable th3) {
            th2 = th;
            th = th3;
        }
        if (query != null) {
            if (th2 != null) {
                try {
                    query.close();
                } catch (Throwable th32) {
                    zzk.zza(th2, th32);
                }
            } else {
                query.close();
            }
        }
        throw th;
        throw th;
    }
}
