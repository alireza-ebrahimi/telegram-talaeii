package com.persianswitch.sdk.payment.repo;

import android.content.Context;
import com.persianswitch.sdk.base.db.phoenix.repo.IPhoenixModel;
import com.persianswitch.sdk.base.db.phoenix.repo.PhoenixRepo;
import com.persianswitch.sdk.payment.database.SDKDatabase;
import com.persianswitch.sdk.payment.model.SyncType;
import com.persianswitch.sdk.payment.model.SyncableData;
import com.persianswitch.sdk.payment.model.SyncableData.SyncTable;

public final class SyncRepo extends PhoenixRepo<Long, SyncableData> {
    public SyncRepo(Context context) {
        super(new SDKDatabase(context), new SyncTable());
    }

    /* renamed from: a */
    public SyncableData m11591a(SyncType syncType, String str) {
        return (SyncableData) m10616c().m10596a(SyncTable.f7539a.m10492a(Integer.valueOf(syncType.m11205a())).m10603a(SyncTable.f7540b.m10492a(Integer.valueOf(syncType.m11206b())).m10603a(SyncTable.f7541c.m10492a(Integer.valueOf(syncType.m11207c())).m10603a(SyncTable.f7542d.m10492a((Object) str))))).m10597a(m10618d());
    }

    /* renamed from: a */
    public void m11593a(SyncableData syncableData) {
        SyncableData a = m11591a(syncableData.m11226d(), syncableData.m11218a());
        if (a != null) {
            syncableData.m11219a(a.m11222b().longValue());
        }
        super.mo3369a((IPhoenixModel) syncableData);
    }
}
