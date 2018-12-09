package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;

public interface FieldConverter<T> {
    /* renamed from: a */
    ColumnType mo3234a();

    /* renamed from: a */
    T mo3235a(Cursor cursor, int i);

    /* renamed from: a */
    void mo3236a(String str, T t, ContentValues contentValues);
}
