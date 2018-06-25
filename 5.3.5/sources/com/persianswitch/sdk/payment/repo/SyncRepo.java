package com.persianswitch.sdk.payment.repo;

import android.content.Context;
import android.database.SQLException;
import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.db.phoenix.repo.PhoenixRepo;
import com.persianswitch.sdk.payment.database.SDKDatabase;
import com.persianswitch.sdk.payment.model.SyncType;
import com.persianswitch.sdk.payment.model.SyncableData;
import com.persianswitch.sdk.payment.model.SyncableData.SyncTable;

public final class SyncRepo extends PhoenixRepo<Long, SyncableData> {
    public SyncRepo(Context context) {
        super(new SDKDatabase(context), new SyncTable());
    }

    public void createOrUpdate(@NonNull SyncableData entity) throws SQLException {
        SyncableData byType = findByType(entity.getSyncType(), entity.getLanguage());
        if (byType != null) {
            entity.setDatabaseId(byType.getDatabaseId().longValue());
        }
        super.createOrUpdate(entity);
    }

    public SyncableData findByType(SyncType syncType, String language) {
        return (SyncableData) query().where(SyncTable.COLUMN_TYPE_ID.isEqualTo(Integer.valueOf(syncType.getType())).and(SyncTable.COLUMN_SUB_TYPE.isEqualTo(Integer.valueOf(syncType.getSubType())).and(SyncTable.COLUMN_VAR_TYPE.isEqualTo(Integer.valueOf(syncType.getVarType())).and(SyncTable.COLUMN_LANGUAGE.isEqualTo(language))))).findFirst(getReadableDatabase());
    }
}
