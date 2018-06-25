package com.persianswitch.sdk.base.preference;

import android.database.sqlite.SQLiteOpenHelper;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue;
import com.persianswitch.sdk.base.db.phoenix.SqliteKeyValue.SqlitePreferenceTable;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import com.persianswitch.sdk.base.db.phoenix.repo.PhoenixRepo;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class SqlitePreference extends PhoenixRepo<String, SqliteKeyValue> implements IPreference {
    public SqlitePreference(SQLiteOpenHelper dbHelper, String preferenceName) {
        super(dbHelper, new SqlitePreferenceTable(preferenceName));
    }

    public String serializeValue(String value) {
        return value;
    }

    public String deserializeValue(String serializedValue) {
        return serializedValue;
    }

    public String getString(String key, String defValue) {
        try {
            defValue = deserializeValue(((SqliteKeyValue) findById(key)).getValue());
        } catch (Exception e) {
        }
        return defValue;
    }

    public void putString(String key, String value) {
        createOrUpdate(new SqliteKeyValue(key, serializeValue(value), String.class));
    }

    public int getInt(String key, int defValue) {
        try {
            Integer optInteger = StringUtils.toInteger(deserializeValue(((SqliteKeyValue) findById(key)).getValue()));
            if (optInteger != null) {
                defValue = optInteger.intValue();
            }
        } catch (Exception e) {
        }
        return defValue;
    }

    public void putInt(String key, int value) {
        createOrUpdate(new SqliteKeyValue(key, serializeValue(String.valueOf(value)), Integer.class));
    }

    public long getLong(String key, long defValue) {
        try {
            Long optLong = StringUtils.toLong(deserializeValue(((SqliteKeyValue) findById(key)).getValue()));
            if (optLong != null) {
                defValue = optLong.longValue();
            }
        } catch (Exception e) {
        }
        return defValue;
    }

    public void putLong(String key, long value) {
        createOrUpdate(new SqliteKeyValue(key, serializeValue(String.valueOf(value)), Long.class));
    }

    public float getFloat(String key, float defValue) {
        try {
            Float optFloat = StringUtils.toFloat(deserializeValue(((SqliteKeyValue) findById(key)).getValue()));
            if (optFloat != null) {
                defValue = optFloat.floatValue();
            }
        } catch (Exception e) {
        }
        return defValue;
    }

    public void putFloat(String key, float value) {
        createOrUpdate(new SqliteKeyValue(key, serializeValue(String.valueOf(value)), Float.class));
    }

    public boolean getBoolean(String key, boolean defValue) {
        try {
            Boolean optBoolean = StringUtils.toBoolean(deserializeValue(((SqliteKeyValue) findById(key)).getValue()));
            if (optBoolean != null) {
                defValue = optBoolean.booleanValue();
            }
        } catch (Exception e) {
        }
        return defValue;
    }

    public void putBoolean(String key, boolean value) {
        createOrUpdate(new SqliteKeyValue(key, serializeValue(String.valueOf(value)), Boolean.class));
    }

    public void clearAll() {
        delete(Where.any());
    }

    public void remove(String key) {
        delete(getTableScheme().getIdColumn().isEqualTo(key));
    }

    public boolean contain(String key) {
        return findById(key) != null;
    }
}
