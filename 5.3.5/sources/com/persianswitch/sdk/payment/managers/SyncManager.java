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
    private final Context mContext;

    public SyncManager(Context context) {
        this.mContext = context;
    }

    public ClientSyncRequest getSyncRequest(String language) {
        return new ClientSyncRequest(getAvailableSyncVersions(language));
    }

    private ArrayList<SyncVersion> getAvailableSyncVersions(String language) {
        ArrayList<SyncVersion> syncVersionList = new ArrayList(SyncType.ALL_SYNCS_TYPE.length);
        SyncRepo syncRepo = new SyncRepo(this.mContext);
        for (SyncType syncType : SyncType.ALL_SYNCS_TYPE) {
            SyncableData byType = syncRepo.findByType(syncType, language);
            if (byType != null) {
                syncVersionList.add(new SyncVersion(syncType.getFlatType(), byType.getVersion()));
            } else {
                syncVersionList.add(new SyncVersion(syncType.getFlatType(), "0.0.0"));
            }
        }
        return syncVersionList;
    }

    public boolean updateSyncData(String responseAsJson, String language) {
        ClientSyncResponse clientSyncResponse = ClientSyncResponse.fromJson(responseAsJson);
        SyncRepo syncRepo = new SyncRepo(this.mContext);
        if (clientSyncResponse == null) {
            return false;
        }
        List<SyncData> syncDataList = clientSyncResponse.getSyncDataList();
        if (syncDataList != null) {
            for (SyncData newSyncData : syncDataList) {
                SyncableData syncableData = new SyncableData();
                syncableData.setSyncType(SyncType.getInstance(newSyncData.getFlatType()));
                syncableData.setVersion(newSyncData.getVersion());
                syncableData.setData(newSyncData.getData());
                syncableData.setLanguage(language);
                syncRepo.createOrUpdate(syncableData);
            }
        }
        return true;
    }
}
