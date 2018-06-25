package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import com.persianswitch.sdk.base.db.phoenix.query.ColumnSelect;
import com.persianswitch.sdk.base.db.phoenix.query.Project;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import com.persianswitch.sdk.base.db.phoenix.query.WhereCondition.SimpleOperatorCondition;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class Column<T> {
    private final String mColumnName;
    private final ColumnType mColumnType;
    private final String mDefaultValue;
    private final FieldConverter<T> mFieldConverter;
    private final Class<T> mFieldType;
    private final boolean mPrimaryKey;

    public Column(String columnName, Class<T> javaTypeClass) {
        this(columnName, (Class) javaTypeClass, false, "");
    }

    public Column(String columnName, Class<T> fieldType, boolean primaryKey, String defaultValue) {
        this.mColumnName = columnName;
        this.mFieldType = fieldType;
        this.mFieldConverter = FieldConverters.byType(this.mFieldType);
        this.mColumnType = ColumnType.byJavaType(this.mFieldType);
        this.mPrimaryKey = primaryKey;
        this.mDefaultValue = defaultValue;
    }

    public Column(String columnName, FieldConverter<T> fieldConverter) {
        this(columnName, (FieldConverter) fieldConverter, false, "");
    }

    public Column(String columnName, FieldConverter<T> fieldConverter, boolean primaryKey, String defaultValue) {
        this.mColumnName = columnName;
        this.mFieldConverter = fieldConverter;
        this.mFieldType = this.mFieldConverter.getFieldType();
        this.mColumnType = this.mFieldConverter.getColumnType();
        this.mPrimaryKey = primaryKey;
        this.mDefaultValue = defaultValue;
    }

    public Column(String columnName, Class<T> javaTypeClass, boolean primaryKey) {
        this(columnName, (Class) javaTypeClass, primaryKey, "");
    }

    public Column(String columnName, Class<T> javaTypeClass, String defaultValue) {
        this(columnName, (Class) javaTypeClass, false, defaultValue);
    }

    public Where isEqualTo(Object value) {
        return new Where(new SimpleOperatorCondition(this, " = ", value));
    }

    public Where isNotEqualTo(Object value) {
        return new Where(new SimpleOperatorCondition(this, " <> ", value));
    }

    public Where isGreaterThan(Object value) {
        return new Where(new SimpleOperatorCondition(this, " > ", value));
    }

    public Where isGreaterEqualThan(Object value) {
        return new Where(new SimpleOperatorCondition(this, " >= ", value));
    }

    public Where isLessThan(Object value) {
        return new Where(new SimpleOperatorCondition(this, " < ", value));
    }

    public Where isLessEqualThan(Object value) {
        return new Where(new SimpleOperatorCondition(this, " <= ", value));
    }

    public Project and(Column anotherColumn) {
        return new Project(new ColumnSelect(this)).and(new ColumnSelect(anotherColumn));
    }

    boolean isPrimaryKey() {
        return this.mPrimaryKey;
    }

    public String getColumnName() {
        return this.mColumnName;
    }

    public ColumnType getColumnType() {
        return this.mColumnType;
    }

    String toDefineStatement() {
        String str = " ";
        Object[] objArr = new Object[3];
        objArr[0] = this.mColumnName;
        objArr[1] = this.mColumnType;
        objArr[2] = this.mPrimaryKey ? "PRIMARY KEY" : "";
        return StringUtils.trimJoin(str, objArr);
    }

    public T getValue(Cursor cursor) throws SQLException {
        return this.mFieldConverter.fromCursor(cursor, cursor.getColumnIndexOrThrow(this.mColumnName));
    }

    public void putValue(T value, ContentValues contentValues) throws SQLException {
        this.mFieldConverter.populateContentValues(this.mColumnName, value, contentValues);
    }
}
