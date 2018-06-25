package com.persianswitch.sdk.payment.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.EntityConverter;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;
import com.persianswitch.sdk.base.utils.func.Option;

public final class SyncableData implements IPhoenixModel<Long> {
    private String mData;
    private long mDatabaseId;
    private String mLanguage;
    private SyncType mSyncType;
    private String mVersion;

    public static final class SyncTable extends Table<SyncableData> {
        public static final Column<String> COLUMN_DATA = new Column("Data", String.class);
        public static final Column<String> COLUMN_LANGUAGE = new Column("Language", String.class);
        public static final Column<Integer> COLUMN_SUB_TYPE = new Column("SubType", Integer.class);
        private static final Column<Long> COLUMN_SYNC_ID = new Column("SyncDataId", Long.class, true);
        public static final Column<Integer> COLUMN_TYPE_ID = new Column("TypeId", Integer.class);
        public static final Column<Integer> COLUMN_VAR_TYPE = new Column("VarType", Integer.class);
        public static final Column<String> COLUMN_VERSION = new Column("Version", String.class);

        /* renamed from: com.persianswitch.sdk.payment.model.SyncableData$SyncTable$1 */
        class C08021 implements EntityConverter<SyncableData> {
            C08021() {
            }

            public SyncableData fromCursor(Cursor cursor) throws SQLException {
                SyncableData syncableData = new SyncableData();
                syncableData.setDatabaseId(((Long) Option.getOrDefault(SyncTable.COLUMN_SYNC_ID.getValue(cursor), Long.valueOf(0))).longValue());
                syncableData.setSyncType(SyncType.getInstance(((Integer) Option.getOrDefault(SyncTable.COLUMN_TYPE_ID.getValue(cursor), Integer.valueOf(0))).intValue(), ((Integer) Option.getOrDefault(SyncTable.COLUMN_SUB_TYPE.getValue(cursor), Integer.valueOf(0))).intValue(), ((Integer) Option.getOrDefault(SyncTable.COLUMN_VAR_TYPE.getValue(cursor), Integer.valueOf(0))).intValue()));
                syncableData.setLanguage((String) SyncTable.COLUMN_LANGUAGE.getValue(cursor));
                syncableData.setVersion((String) SyncTable.COLUMN_VERSION.getValue(cursor));
                syncableData.setData((String) SyncTable.COLUMN_DATA.getValue(cursor));
                return syncableData;
            }

            public void populateContentValues(SyncableData entity, ContentValues cv) throws SQLException {
                SyncTable.COLUMN_TYPE_ID.putValue(Integer.valueOf(entity.getSyncType().getType()), cv);
                SyncTable.COLUMN_SUB_TYPE.putValue(Integer.valueOf(entity.getSyncType().getSubType()), cv);
                SyncTable.COLUMN_VAR_TYPE.putValue(Integer.valueOf(entity.getSyncType().getVarType()), cv);
                SyncTable.COLUMN_LANGUAGE.putValue(entity.getLanguage(), cv);
                SyncTable.COLUMN_VERSION.putValue(entity.getVersion(), cv);
                SyncTable.COLUMN_DATA.putValue(entity.getData(), cv);
            }
        }

        public String getName() {
            return "Sync";
        }

        public Column getIdColumn() {
            return COLUMN_SYNC_ID;
        }

        public Column[] getColumns() {
            return new Column[]{COLUMN_SYNC_ID, COLUMN_TYPE_ID, COLUMN_SUB_TYPE, COLUMN_VAR_TYPE, COLUMN_LANGUAGE, COLUMN_VERSION, COLUMN_DATA};
        }

        public EntityConverter<SyncableData> getEntityConverter() {
            return new C08021();
        }
    }

    public String getLanguage() {
        return this.mLanguage;
    }

    public void setLanguage(String language) {
        this.mLanguage = language;
    }

    public Long getDatabaseId() {
        return Long.valueOf(this.mDatabaseId);
    }

    public void setDatabaseId(long databaseId) {
        this.mDatabaseId = databaseId;
    }

    public SyncType getSyncType() {
        return this.mSyncType;
    }

    public void setSyncType(SyncType syncType) {
        this.mSyncType = syncType;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public void setVersion(String version) {
        this.mVersion = version;
    }

    public String getData() {
        return this.mData;
    }

    public void setData(String data) {
        this.mData = data;
    }
}
