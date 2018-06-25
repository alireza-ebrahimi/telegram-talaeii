package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public interface FieldConverter<T> {
    T fromCursor(Cursor cursor, int i) throws SQLException;

    ColumnType getColumnType();

    Class<T> getFieldType();

    void populateContentValues(String str, T t, ContentValues contentValues) throws SQLException;
}
