package com.persianswitch.sdk.base.db.phoenix;

import android.content.ContentValues;
import android.database.Cursor;

public interface EntityConverter<T> {
    /* renamed from: a */
    T mo3237a(Cursor cursor);

    /* renamed from: a */
    void mo3238a(T t, ContentValues contentValues);
}
