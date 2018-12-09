package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;

public class SqliteKeyValue implements IPhoenixModel<String> {
    /* renamed from: a */
    private final String f7023a;
    /* renamed from: b */
    private final String f7024b;
    /* renamed from: c */
    private String f7025c;

    public static class SqlitePreferenceTable extends Table<SqliteKeyValue> {
        /* renamed from: a */
        public static final Column<String> f7019a = new Column("Key", String.class, true);
        /* renamed from: b */
        public static final Column<String> f7020b = new Column("Value", String.class);
        /* renamed from: c */
        public static final Column<String> f7021c = new Column("Type", String.class);
        /* renamed from: d */
        private final String f7022d;

        /* renamed from: com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue$SqlitePreferenceTable$1 */
        class C22641 implements EntityConverter<SqliteKeyValue> {
            /* renamed from: a */
            final /* synthetic */ SqlitePreferenceTable f7018a;

            C22641(SqlitePreferenceTable sqlitePreferenceTable) {
                this.f7018a = sqlitePreferenceTable;
            }

            /* renamed from: a */
            public /* synthetic */ Object mo3237a(Cursor cursor) {
                return m10570b(cursor);
            }

            /* renamed from: a */
            public void m10568a(SqliteKeyValue sqliteKeyValue, ContentValues contentValues) {
                SqlitePreferenceTable.f7019a.m10495a(sqliteKeyValue.f7023a, contentValues);
                SqlitePreferenceTable.f7020b.m10495a(sqliteKeyValue.f7024b, contentValues);
                SqlitePreferenceTable.f7021c.m10495a(sqliteKeyValue.f7025c, contentValues);
            }

            /* renamed from: b */
            public SqliteKeyValue m10570b(Cursor cursor) {
                return new SqliteKeyValue((String) SqlitePreferenceTable.f7019a.m10493a(cursor), (String) SqlitePreferenceTable.f7020b.m10493a(cursor));
            }
        }

        public SqlitePreferenceTable(String str) {
            this.f7022d = str;
        }

        /* renamed from: a */
        public String mo3239a() {
            return this.f7022d;
        }

        /* renamed from: b */
        public Column mo3240b() {
            return f7019a;
        }

        /* renamed from: c */
        public Column[] mo3241c() {
            return new Column[]{f7019a, f7020b, f7021c};
        }

        /* renamed from: d */
        public EntityConverter<SqliteKeyValue> mo3242d() {
            return new C22641(this);
        }
    }

    public SqliteKeyValue(String str, String str2) {
        this.f7023a = str;
        this.f7024b = str2;
    }

    public SqliteKeyValue(String str, String str2, Class<?> cls) {
        this.f7023a = str;
        this.f7024b = str2;
        this.f7025c = cls.getSimpleName();
    }

    /* renamed from: a */
    public String m10585a() {
        return this.f7023a;
    }

    /* renamed from: b */
    public String m10586b() {
        return this.f7024b;
    }

    /* renamed from: c */
    public /* synthetic */ Object mo3243c() {
        return m10585a();
    }
}
