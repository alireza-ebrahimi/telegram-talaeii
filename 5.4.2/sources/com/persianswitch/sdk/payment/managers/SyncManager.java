package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import com.persianswitch.sdk.payment.model.SyncType;
import com.persianswitch.sdk.payment.model.SyncableData;
import com.persianswitch.sdk.payment.model.req.ClientSyncRequest;
import com.persianswitch.sdk.payment.model.req.ClientSyncRequest.SyncVersion;
import com.persianswitch.sdk.payment.model.res.ClientSyncResponse;
import com.persianswitch.sdk.payment.model.res.ClientSyncResponse.SyncData;
import com.persianswitch.sdk.payment.repo.SyncRepo;
import java.util.ArrayList;
import java.util.List;

public final class SyncManager {
    /* renamed from: a */
    private final Context f7405a;

    public SyncManager(Context context) {
        this.f7405a = context;
    }

    /* renamed from: b */
    private ArrayList<SyncVersion> m11104b(String str) {
        ArrayList<SyncVersion> arrayList = new ArrayList(SyncType.f7534c.length);
        SyncRepo syncRepo = new SyncRepo(this.f7405a);
        for (SyncType syncType : SyncType.f7534c) {
            SyncableData a = syncRepo.m11591a(syncType, str);
            if (a != null) {
                arrayList.add(new SyncVersion(syncType.m11208d(), a.m11227e()));
            } else {
                arrayList.add(new SyncVersion(syncType.m11208d(), "0.0.0"));
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public ClientSyncRequest m11105a(String str) {
        return new ClientSyncRequest(m11104b(str));
    }

    /* renamed from: a */
    public boolean m11106a(String str, String str2) {
        ClientSyncResponse a = ClientSyncResponse.m11364a(str);
        SyncRepo syncRepo = new SyncRepo(this.f7405a);
        if (a == null) {
            return false;
        }
        List<SyncData> a2 = a.m11365a();
        if (a2 != null) {
            for (SyncData syncData : a2) {
                SyncableData syncableData = new SyncableData();
                syncableData.m11220a(SyncType.m11204a(syncData.m11361a()));
                syncableData.m11223b(syncData.m11362b());
                syncableData.m11225c(syncData.m11363c());
                syncableData.m11221a(str2);
                syncRepo.m11593a(syncableData);
            }
        }
        return true;
    }
}
