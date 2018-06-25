package com.persianswitch.sdk.payment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue.SqlitePreferenceTable;
import com.persianswitch.sdk.payment.repo.CardRepo;
import com.persianswitch.sdk.payment.repo.SyncRepo;

public final class SDKDatabase extends SQLiteOpenHelper {
    static final String DB_NAME = "sdk.dat";
    static final int DB_VERSION = 1;
    private final Context mContext;

    interface Version {
        public static final int V0_1_0 = 1;
    }

    public SDKDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        new CardRepo(this.mContext).getTableScheme().createTable(db);
        new SyncRepo(this.mContext).getTableScheme().createTable(db);
        new SqlitePreferenceTable("pref").createTable(db);
        new SqlitePreferenceTable("secure_pref").createTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void clearAllData() {
        new SqlitePreferenceTable("pref").clearTable(getWritableDatabase());
        new SqlitePreferenceTable("secure_pref").clearTable(getWritableDatabase());
        new CardRepo(this.mContext).getTableScheme().clearTable(getWritableDatabase());
        new SyncRepo(this.mContext).getTableScheme().clearTable(getWritableDatabase());
    }
}
