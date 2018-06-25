package com.persianswitch.sdk.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue.SqlitePreferenceTable;

public class BaseDatabase extends SQLiteOpenHelper {
    static final String DB_NAME = "base.dat";
    static final int DB_VERSION = 1;
    private final Context mContext;

    interface Version {
        public static final int V0_1_0 = 1;
    }

    public BaseDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void onCreate(SQLiteDatabase db) {
        new SqlitePreferenceTable("pref").createTable(db);
        new SqlitePreferenceTable("secure_pref").createTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void clearAllData() {
        new SqlitePreferenceTable("pref").clearTable(getWritableDatabase());
        new SqlitePreferenceTable("secure_pref").clearTable(getWritableDatabase());
    }
}
