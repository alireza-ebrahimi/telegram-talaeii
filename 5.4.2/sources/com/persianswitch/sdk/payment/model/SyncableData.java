package com.persianswitch.sdk.payment.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.db.phoenix.EntityConverter;
import com.persianswitch.sdk.base.db.phoenix.Table;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;
import com.persianswitch.sdk.base.utils.func.Option;

public final class SyncableData implements IPhoenixModel<Long> {
    /* renamed from: a */
    private long f7546a;
    /* renamed from: b */
    private SyncType f7547b;
    /* renamed from: c */
    private String f7548c;
    /* renamed from: d */
    private String f7549d;
    /* renamed from: e */
    private String f7550e;

    public static final class SyncTable extends Table<SyncableData> {
        /* renamed from: a */
        public static final Column<Integer> f7539a = new Column("TypeId", Integer.class);
        /* renamed from: b */
        public static final Column<Integer> f7540b = new Column("SubType", Integer.class);
        /* renamed from: c */
        public static final Column<Integer> f7541c = new Column("VarType", Integer.class);
        /* renamed from: d */
        public static final Column<String> f7542d = new Column("Language", String.class);
        /* renamed from: e */
        public static final Column<String> f7543e = new Column("Version", String.class);
        /* renamed from: f */
        public static final Column<String> f7544f = new Column("Data", String.class);
        /* renamed from: g */
        private static final Column<Long> f7545g = new Column("SyncDataId", Long.class, true);

        /* renamed from: com.persianswitch.sdk.payment.model.SyncableData$SyncTable$1 */
        class C22941 implements EntityConverter<SyncableData> {
            /* renamed from: a */
            final /* synthetic */ SyncTable f7538a;

            C22941(SyncTable syncTable) {
                this.f7538a = syncTable;
            }

            /* renamed from: a */
            public /* synthetic */ Object mo3237a(Cursor cursor) {
                return m11212b(cursor);
            }

            /* renamed from: a */
            public void m11210a(SyncableData syncableData, ContentValues contentValues) {
                SyncTable.f7539a.m10495a(Integer.valueOf(syncableData.m11226d().m11205a()), contentValues);
                SyncTable.f7540b.m10495a(Integer.valueOf(syncableData.m11226d().m11206b()), contentValues);
                SyncTable.f7541c.m10495a(Integer.valueOf(syncableData.m11226d().m11207c()), contentValues);
                SyncTable.f7542d.m10495a(syncableData.m11218a(), contentValues);
                SyncTable.f7543e.m10495a(syncableData.m11227e(), contentValues);
                SyncTable.f7544f.m10495a(syncableData.m11228f(), contentValues);
            }

            /* renamed from: b */
            public SyncableData m11212b(Cursor cursor) {
                SyncableData syncableData = new SyncableData();
                syncableData.m11219a(((Long) Option.m10775a(SyncTable.f7545g.m10493a(cursor), Long.valueOf(0))).longValue());
                syncableData.m11220a(SyncType.m11203a(((Integer) Option.m10775a(SyncTable.f7539a.m10493a(cursor), Integer.valueOf(0))).intValue(), ((Integer) Option.m10775a(SyncTable.f7540b.m10493a(cursor), Integer.valueOf(0))).intValue(), ((Integer) Option.m10775a(SyncTable.f7541c.m10493a(cursor), Integer.valueOf(0))).intValue()));
                syncableData.m11221a((String) SyncTable.f7542d.m10493a(cursor));
                syncableData.m11223b((String) SyncTable.f7543e.m10493a(cursor));
                syncableData.m11225c((String) SyncTable.f7544f.m10493a(cursor));
                return syncableData;
            }
        }

        /* renamed from: a */
        public String mo3239a() {
            return "Sync";
        }

        /* renamed from: b */
        public Column mo3240b() {
            return f7545g;
        }

        /* renamed from: c */
        public Column[] mo3241c() {
            return new Column[]{f7545g, f7539a, f7540b, f7541c, f7542d, f7543e, f7544f};
        }

        /* renamed from: d */
        public EntityConverter<SyncableData> mo3242d() {
            return new C22941(this);
        }
    }

    /* renamed from: a */
    public String m11218a() {
        return this.f7548c;
    }

    /* renamed from: a */
    public void m11219a(long j) {
        this.f7546a = j;
    }

    /* renamed from: a */
    public void m11220a(SyncType syncType) {
        this.f7547b = syncType;
    }

    /* renamed from: a */
    public void m11221a(String str) {
        this.f7548c = str;
    }

    /* renamed from: b */
    public Long m11222b() {
        return Long.valueOf(this.f7546a);
    }

    /* renamed from: b */
    public void m11223b(String str) {
        this.f7549d = str;
    }

    /* renamed from: c */
    public /* synthetic */ Object mo3243c() {
        return m11222b();
    }

    /* renamed from: c */
    public void m11225c(String str) {
        this.f7550e = str;
    }

    /* renamed from: d */
    public SyncType m11226d() {
        return this.f7547b;
    }

    /* renamed from: e */
    public String m11227e() {
        return this.f7549d;
    }

    /* renamed from: f */
    public String m11228f() {
        return this.f7550e;
    }
}
