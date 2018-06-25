package com.persianswitch.sdk.base.db.phoenix.repo;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.db.phoenix.query.Query;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import com.persianswitch.sdk.base.utils.PreConditions;
import java.util.List;

public abstract class PhoenixRepo<I, T extends IPhoenixModel<I>> implements IPhoenixRepo<I, T> {
    private static final String TAG = "PhoenixRepo";
    private final SQLiteOpenHelper mSQLiteOpenHelper;
    private final Table<T> mTableScheme;

    public PhoenixRepo(SQLiteOpenHelper sqLiteOpenHelper, Table<T> tableScheme) {
        this.mSQLiteOpenHelper = sqLiteOpenHelper;
        this.mTableScheme = tableScheme;
    }

    public void createOrUpdate(@NonNull T entity) throws SQLException {
        if (findById(entity.getDatabaseId()) == null) {
            create(entity);
        } else {
            update(entity);
        }
    }

    @Nullable
    public T findById(@NonNull I idValue) throws SQLException {
        Column idColumn = getTableScheme().getIdColumn();
        PreConditions.checkNotNull(idColumn, "you must specify id column in your entity schema");
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            IPhoenixModel iPhoenixModel = (IPhoenixModel) query().where(idColumn.isEqualTo(idValue)).findFirst(readableDatabase);
            return iPhoenixModel;
        } finally {
            readableDatabase.close();
        }
    }

    public void create(@NonNull T entity) throws SQLException {
        PreConditions.checkNotNull(entity, "can not create null entity");
        ContentValues contentValues = new ContentValues();
        getTableScheme().getEntityConverter().populateContentValues(entity, contentValues);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            writableDatabase.insert(getTableScheme().getName(), null, contentValues);
        } finally {
            writableDatabase.close();
        }
    }

    public void update(@NonNull T entity) throws SQLException {
        PreConditions.checkNotNull(entity, "can not update null entity");
        ContentValues contentValues = new ContentValues();
        getTableScheme().getEntityConverter().populateContentValues(entity, contentValues);
        String where = getTableScheme().getIdColumn().isEqualTo(entity.getDatabaseId()).toSQLConditionOnly();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            writableDatabase.update(getTableScheme().getName(), contentValues, where, null);
        } finally {
            writableDatabase.close();
        }
    }

    public List<T> getAll() throws SQLException {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            List<T> findAll = query().findAll(readableDatabase);
            return findAll;
        } finally {
            readableDatabase.close();
        }
    }

    public Table<T> getTableScheme() {
        return this.mTableScheme;
    }

    public Query<T> query() {
        return new Query(getTableScheme());
    }

    public SQLiteDatabase getReadableDatabase() {
        return this.mSQLiteOpenHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return this.mSQLiteOpenHelper.getWritableDatabase();
    }

    public void delete(@NonNull T entity) throws SQLException {
        delete(getTableScheme().getIdColumn().isEqualTo(entity.getDatabaseId()));
    }

    public void delete(@NonNull Where where) throws SQLException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            writableDatabase.delete(getTableScheme().getName(), where.toSQLConditionOnly(), null);
        } finally {
            writableDatabase.close();
        }
    }
}
