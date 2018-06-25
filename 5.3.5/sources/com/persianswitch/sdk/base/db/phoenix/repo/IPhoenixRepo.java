package com.persianswitch.sdk.base.db.phoenix.repo;

import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.persianswitch.sdk.base.db.phoenix.query.Where;
import java.util.List;

public interface IPhoenixRepo<I, T extends IPhoenixModel<I>> {
    void create(@NonNull T t) throws SQLException;

    void createOrUpdate(@NonNull T t) throws SQLException;

    void delete(@NonNull Where where) throws SQLException;

    void delete(@NonNull T t) throws SQLException;

    @Nullable
    T findById(@NonNull I i) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(@NonNull T t) throws SQLException;
}
