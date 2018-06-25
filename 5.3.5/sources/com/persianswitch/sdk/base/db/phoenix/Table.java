package com.persianswitch.sdk.base.db.phoenix;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class Table<T> {
    public abstract Column[] getColumns();

    public abstract EntityConverter<T> getEntityConverter();

    public abstract Column getIdColumn();

    public abstract String getName();

    public void createTable(SQLiteDatabase database) throws SQLException {
        String tableName = getName();
        if (tableName == null) {
            throw new IllegalStateException("getName() can not be null!");
        }
        Column[] columns = getColumns();
        if (columns == null) {
            throw new IllegalStateException("getColumns() can not be null!");
        }
        StringBuilder createQuery = new StringBuilder();
        createQuery.append("CREATE TABLE ");
        createQuery.append(getName());
        createQuery.append("( ");
        int primaryKeyCount = 0;
        for (int i = 0; i < columns.length; i++) {
            Column column = columns[i];
            if (column.isPrimaryKey()) {
                primaryKeyCount++;
            }
            createQuery.append(column.toDefineStatement());
            if (i < columns.length - 1) {
                createQuery.append(", ");
            }
        }
        createQuery.append(" )");
        if (primaryKeyCount == 0) {
            throw new IllegalStateException("Table " + tableName + " has not any primary key!");
        } else if (primaryKeyCount > 1) {
            throw new IllegalStateException("Table " + tableName + " has more than one primary key!");
        } else {
            database.execSQL(createQuery.toString());
        }
    }

    public void dropTable(SQLiteDatabase writableDatabase) {
        String getName = getName();
        if (getName == null) {
            throw new IllegalStateException("getName() can not be null!");
        }
        writableDatabase.execSQL("DROP TABLE IF EXISTS " + getName);
    }

    public void clearTable(SQLiteDatabase writableDatabase) {
        String getName = getName();
        if (getName == null) {
            throw new IllegalStateException("getName() can not be null!");
        }
        writableDatabase.execSQL(" DELETE FROM " + getName);
    }
}
