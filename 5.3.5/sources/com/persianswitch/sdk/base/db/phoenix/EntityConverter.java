package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public interface EntityConverter<T> {
    T fromCursor(Cursor cursor) throws SQLException;

    void populateContentValues(T t, ContentValues contentValues) throws SQLException;
}
