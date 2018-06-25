package com.persianswitch.sdk.base.db.phoenix;

public enum ColumnType {
    TEXT("TEXT"),
    INTEGER("INTEGER"),
    REAL("REAL"),
    BLOB("BLOB");
    
    private final String mSqlType;

    private ColumnType(String sqlType) {
        this.mSqlType = sqlType;
    }

    public static <T> ColumnType byJavaType(Class<T> clazz) {
        return FieldConverters.byType(clazz).getColumnType();
    }
}
