package com.persianswitch.sdk.base.db.phoenix;

import android.database.Cursor;

public interface ColumnTypeConverter<T> {
    T parseColumn(Cursor cursor);
}
