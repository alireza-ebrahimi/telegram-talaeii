package com.persianswitch.sdk.base.db.phoenix;

import android.database.sqlite.SQLiteDatabase;

public abstract class Table<T> {
    /* renamed from: a */
    public abstract String mo3239a();

    /* renamed from: a */
    public void m10572a(SQLiteDatabase sQLiteDatabase) {
        int i = 0;
        String a = mo3239a();
        if (a == null) {
            throw new IllegalStateException("getName() can not be null!");
        }
        Column[] c = mo3241c();
        if (c == null) {
            throw new IllegalStateException("getColumns() can not be null!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ");
        stringBuilder.append(mo3239a());
        stringBuilder.append("( ");
        int i2 = 0;
        while (i < c.length) {
            Column column = c[i];
            if (column.m10496b()) {
                i2++;
            }
            stringBuilder.append(column.mo3233a());
            if (i < c.length - 1) {
                stringBuilder.append(", ");
            }
            i++;
        }
        stringBuilder.append(" )");
        if (i2 == 0) {
            throw new IllegalStateException("Table " + a + " has not any primary key!");
        } else if (i2 > 1) {
            throw new IllegalStateException("Table " + a + " has more than one primary key!");
        } else {
            sQLiteDatabase.execSQL(stringBuilder.toString());
        }
    }

    /* renamed from: b */
    public abstract Column mo3240b();

    /* renamed from: b */
    public void m10574b(SQLiteDatabase sQLiteDatabase) {
        String a = mo3239a();
        if (a == null) {
            throw new IllegalStateException("getName() can not be null!");
        }
        sQLiteDatabase.execSQL(" DELETE FROM " + a);
    }

    /* renamed from: c */
    public abstract Column[] mo3241c();

    /* renamed from: d */
    public abstract EntityConverter<T> mo3242d();
}
