package com.persianswitch.sdk.base.db.phoenix.query;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.persianswitch.sdk.base.db.phoenix.EntityConverter;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.utils.PreConditions;
import java.util.LinkedList;
import java.util.List;

public final class Query<T> implements SQLStatement {
    private Limit mLimit;
    private Order mOrder;
    private Project mProject;
    private final Table<T> mTable;
    private Where mWhere;

    public Query(@NonNull Table table) {
        PreConditions.checkNotNull(table, "table can not be null");
        PreConditions.checkNotNull(table.getColumns(), "getName can not be null");
        this.mTable = table;
    }

    public Query<T> project(Project project) {
        this.mProject = project;
        return this;
    }

    public Query<T> where(Where where) {
        this.mWhere = where;
        return this;
    }

    public Query<T> order(Order order) {
        this.mOrder = order;
        return this;
    }

    private Query<T> limit(Limit limit) {
        this.mLimit = limit;
        return this;
    }

    public Query<T> limit(long recordNumLimit) {
        return limit(new Limit(recordNumLimit));
    }

    public Query<T> limit(long recordNumLimit, long recordOffset) {
        return limit(new Limit(recordNumLimit, recordOffset));
    }

    @Nullable
    public synchronized T findFirst(SQLiteDatabase sqLiteDatabase) throws SQLException {
        return findFirst(sqLiteDatabase, this.mTable.getEntityConverter());
    }

    @Nullable
    public synchronized <E> E findFirst(SQLiteDatabase sqLiteDatabase, EntityConverter<E> converter) throws SQLException {
        E e;
        limit(1);
        List<E> all = findAll(sqLiteDatabase, converter);
        if (all == null || all.isEmpty()) {
            e = null;
        } else {
            e = all.get(0);
        }
        return e;
    }

    @Nullable
    public synchronized <E> List<E> findAll(SQLiteDatabase sqLiteDatabase, EntityConverter<E> converter) throws SQLException {
        List<E> result;
        PreConditions.checkNotNull(converter, "entity converter can not be null");
        result = new LinkedList();
        Cursor cursor = sqLiteDatabase.rawQuery(toSQL(), new String[0]);
        while (cursor.moveToNext()) {
            try {
                result.add(converter.fromCursor(cursor));
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return result;
    }

    @Nullable
    public synchronized List<T> findAll(SQLiteDatabase sqLiteDatabase) throws SQLException {
        return findAll(sqLiteDatabase, this.mTable.getEntityConverter());
    }

    @NonNull
    public synchronized String toSQL() {
        StringBuilder queryBuilder;
        queryBuilder = new StringBuilder(100);
        queryBuilder.append("SELECT ");
        if (this.mProject == null) {
            queryBuilder.append("* ");
        } else {
            queryBuilder.append(this.mProject.toSQL()).append(" ");
        }
        queryBuilder.append("FROM ");
        queryBuilder.append(this.mTable.getName()).append(" ");
        if (this.mWhere != null) {
            queryBuilder.append(this.mWhere.toSQL()).append(" ");
        }
        if (this.mLimit != null) {
            queryBuilder.append(this.mLimit.toSQL());
        }
        if (this.mOrder != null) {
            queryBuilder.append(this.mOrder.toSQL());
        }
        queryBuilder.append(";");
        return queryBuilder.toString().trim();
    }
}
