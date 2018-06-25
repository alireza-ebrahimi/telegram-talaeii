package com.persianswitch.sdk.base.preference;

import android.database.sqlite.SQLiteOpenHelper;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue.SqlitePreferenceTable;
import com.persianswitch.sdk.base.db.phoenix.repo.PhoenixRepo;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class SqlitePreference extends PhoenixRepo<String, SqliteKeyValue> implements IPreference {
    public SqlitePreference(SQLiteOpenHelper sQLiteOpenHelper, String str) {
        super(sQLiteOpenHelper, new SqlitePreferenceTable(str));
    }

    /* renamed from: a */
    public int mo3252a(String str, int i) {
        try {
            Integer e = StringUtils.m10809e(mo3261b(((SqliteKeyValue) m10610a((Object) str)).m10586b()));
            if (e != null) {
                i = e.intValue();
            }
        } catch (Exception e2) {
        }
        return i;
    }

    /* renamed from: a */
    public long mo3253a(String str, long j) {
        try {
            Long d = StringUtils.m10808d(mo3261b(((SqliteKeyValue) m10610a((Object) str)).m10586b()));
            if (d != null) {
                j = d.longValue();
            }
        } catch (Exception e) {
        }
        return j;
    }

    /* renamed from: a */
    public String mo3260a(String str) {
        return str;
    }

    /* renamed from: a */
    public String mo3254a(String str, String str2) {
        try {
            str2 = mo3261b(((SqliteKeyValue) m10610a((Object) str)).m10586b());
        } catch (Exception e) {
        }
        return str2;
    }

    /* renamed from: a */
    public boolean mo3255a(String str, boolean z) {
        try {
            Boolean c = StringUtils.m10807c(mo3261b(((SqliteKeyValue) m10610a((Object) str)).m10586b()));
            if (c != null) {
                z = c.booleanValue();
            }
        } catch (Exception e) {
        }
        return z;
    }

    /* renamed from: b */
    public String mo3261b(String str) {
        return str;
    }

    /* renamed from: b */
    public void mo3256b(String str, int i) {
        mo3369a(new SqliteKeyValue(str, mo3260a(String.valueOf(i)), Integer.class));
    }

    /* renamed from: b */
    public void mo3257b(String str, long j) {
        mo3369a(new SqliteKeyValue(str, mo3260a(String.valueOf(j)), Long.class));
    }

    /* renamed from: b */
    public void mo3258b(String str, String str2) {
        mo3369a(new SqliteKeyValue(str, mo3260a(str2), String.class));
    }

    /* renamed from: b */
    public void mo3259b(String str, boolean z) {
        mo3369a(new SqliteKeyValue(str, mo3260a(String.valueOf(z)), Boolean.class));
    }
}
