package com.persianswitch.sdk.payment.model.res;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.persianswitch.sdk.base.utils.JsonParser;
import com.persianswitch.sdk.base.utils.TODO;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ClientSyncResponse {
    private final List<SyncData> mSyncDataList;

    public static class EntityJsonParser implements JsonParser<ClientSyncResponse> {
        public JSONObject toJson(ClientSyncResponse entity) throws JSONException {
            return (JSONObject) TODO.notImplementedYet();
        }

        public ClientSyncResponse fromJson(String json) throws JSONException {
            ArrayList<SyncData> syncDataArrayList = new ArrayList();
            JSONArray syncData = new JSONObject(json).getJSONArray("syncData");
            if (syncData != null) {
                for (int i = 0; i < syncData.length(); i++) {
                    JSONObject syncDataJSONObject = syncData.getJSONObject(i);
                    if (syncDataJSONObject != null) {
                        syncDataArrayList.add(new SyncData(syncDataJSONObject.getString(Param.TYPE), syncDataJSONObject.getString("ver"), syncDataJSONObject.getString("data")));
                    }
                }
            }
            return new ClientSyncResponse(syncDataArrayList);
        }
    }

    public static class SyncData {
        private final String mData;
        private final String mFlatType;
        private final String mVersion;

        public SyncData(String flatType, String version, String data) {
            this.mFlatType = flatType;
            this.mVersion = version;
            this.mData = data;
        }

        public String getFlatType() {
            return this.mFlatType;
        }

        public String getVersion() {
            return this.mVersion;
        }

        public String getData() {
            return this.mData;
        }
    }

    public ClientSyncResponse(List<SyncData> syncDataList) {
        this.mSyncDataList = syncDataList;
    }

    public static ClientSyncResponse fromJson(String json) {
        try {
            return new EntityJsonParser().fromJson(json);
        } catch (JSONException e) {
            return null;
        }
    }

    public List<SyncData> getSyncDataList() {
        return this.mSyncDataList;
    }
}
