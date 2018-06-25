package com.persianswitch.sdk.base.db.phoenix;

public enum ColumnType {
    TEXT("TEXT"),
    INTEGER("INTEGER"),
    REAL("REAL"),
    BLOB("BLOB");
    
    /* renamed from: e */
    private final String f7017e;

    private ColumnType(String str) {
        this.f7017e = str;
    }

    /* renamed from: a */
    public static <T> ColumnType m10500a(Class<T> cls) {
        return FieldConverters.m10566a(cls).mo3234a();
    }
}
