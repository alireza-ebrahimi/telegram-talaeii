package com.persianswitch.sdk.payment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue.SqlitePreferenceTable;
import com.persianswitch.sdk.payment.repo.CardRepo;
import com.persianswitch.sdk.payment.repo.SyncRepo;

public final class SDKDatabase extends SQLiteOpenHelper {
    /* renamed from: a */
    private final Context f7385a;

    interface Version {
    }

    public SDKDatabase(Context context) {
        super(context, "sdk.dat", null, 1);
        this.f7385a = context;
    }

    /* renamed from: a */
    public void m11060a() {
        new SqlitePreferenceTable("pref").m10574b(getWritableDatabase());
        new SqlitePreferenceTable("secure_pref").m10574b(getWritableDatabase());
        new CardRepo(this.f7385a).m10614b().m10574b(getWritableDatabase());
        new SyncRepo(this.f7385a).m10614b().m10574b(getWritableDatabase());
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        new CardRepo(this.f7385a).m10614b().m10572a(sQLiteDatabase);
        new SyncRepo(this.f7385a).m10614b().m10572a(sQLiteDatabase);
        new SqlitePreferenceTable("pref").m10572a(sQLiteDatabase);
        new SqlitePreferenceTable("secure_pref").m10572a(sQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
