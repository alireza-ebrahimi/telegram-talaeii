package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;

public class SqliteKeyValue implements IPhoenixModel<String> {
    private final String mKey;
    private String mType;
    private final String mValue;

    public static class SqlitePreferenceTable extends Table<SqliteKeyValue> {
        public static final Column<String> COLUMN_KEY = new Column("Key", String.class, true);
        public static final Column<String> COLUMN_TYPE = new Column("Type", String.class);
        public static final Column<String> COLUMN_VALUE = new Column("Value", String.class);
        private final String mPreferenceName;

        /* renamed from: com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue$SqlitePreferenceTable$1 */
        class C07721 implements EntityConverter<SqliteKeyValue> {
            C07721() {
            }

            public SqliteKeyValue fromCursor(Cursor cursor) throws SQLException {
                return new SqliteKeyValue((String) SqlitePreferenceTable.COLUMN_KEY.getValue(cursor), (String) SqlitePreferenceTable.COLUMN_VALUE.getValue(cursor));
            }

            public void populateContentValues(SqliteKeyValue entity, ContentValues cv) throws SQLException {
                SqlitePreferenceTable.COLUMN_KEY.putValue(entity.mKey, cv);
                SqlitePreferenceTable.COLUMN_VALUE.putValue(entity.mValue, cv);
                SqlitePreferenceTable.COLUMN_TYPE.putValue(entity.mType, cv);
            }
        }

        public SqlitePreferenceTable(String preferenceName) {
            this.mPreferenceName = preferenceName;
        }

        public String getName() {
            return this.mPreferenceName;
        }

        public Column getIdColumn() {
            return COLUMN_KEY;
        }

        public Column[] getColumns() {
            return new Column[]{COLUMN_KEY, COLUMN_VALUE, COLUMN_TYPE};
        }

        public EntityConverter<SqliteKeyValue> getEntityConverter() {
            return new C07721();
        }
    }

    public SqliteKeyValue(String key, String value) {
        this.mKey = key;
        this.mValue = value;
    }

    public SqliteKeyValue(String key, String value, Class<?> clazz) {
        this.mKey = key;
        this.mValue = value;
        this.mType = clazz.getSimpleName();
    }

    public String getDatabaseId() {
        return this.mKey;
    }

    public String getKey() {
        return this.mKey;
    }

    public String getValue() {
        return this.mValue;
    }

    public String getType() {
        return this.mType;
    }
}
