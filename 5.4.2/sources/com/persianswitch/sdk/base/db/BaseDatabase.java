package com.persianswitch.sdk.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue.SqlitePreferenceTable;

public class BaseDatabase extends SQLiteOpenHelper {
    /* renamed from: a */
    private final Context f7005a;

    interface Version {
    }

    public BaseDatabase(Context context) {
        super(context, "base.dat", null, 1);
        this.f7005a = context;
    }

    /* renamed from: a */
    public void m10491a() {
        new SqlitePreferenceTable("pref").m10574b(getWritableDatabase());
        new SqlitePreferenceTable("secure_pref").m10574b(getWritableDatabase());
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        new SqlitePreferenceTable("pref").m10572a(sQLiteDatabase);
        new SqlitePreferenceTable("secure_pref").m10572a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
