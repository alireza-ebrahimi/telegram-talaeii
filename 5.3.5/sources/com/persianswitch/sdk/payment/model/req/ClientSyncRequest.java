package com.persianswitch.sdk.payment.model.req;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.persianswitch.sdk.base.utils.JsonParser;
import com.persianswitch.sdk.base.utils.TODO;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ClientSyncRequest {
    private static final String TAG = "ClientSyncRequest";
    private final ArrayList<SyncVersion> mSyncVersions;

    public static class EntityJsonParser implements JsonParser<ClientSyncRequest> {
        public JSONObject toJson(ClientSyncRequest entity) throws JSONException {
            JSONObject jObject = new JSONObject();
            JSONArray syncJObject = new JSONArray();
            EntityJsonParser syncVersionParser = new EntityJsonParser();
            Iterator it = entity.getSyncVersions().iterator();
            while (it.hasNext()) {
                syncJObject.put(syncVersionParser.toJson((SyncVersion) it.next()));
            }
            jObject.put("syncs", syncJObject);
            return jObject;
        }

        public ClientSyncRequest fromJson(String json) {
            return (ClientSyncRequest) TODO.notImplementedYet();
        }
    }

    public static class SyncVersion {
        private final String mType;
        private final String mVersion;

        private static class EntityJsonParser implements JsonParser<SyncVersion> {
            private EntityJsonParser() {
            }

            public JSONObject toJson(SyncVersion entity) throws JSONException {
                JSONObject jObject = new JSONObject();
                jObject.put(Param.TYPE, entity.getType());
                jObject.put("ver", entity.getVersion());
                return jObject;
            }

            public SyncVersion fromJson(String json) {
                return (SyncVersion) TODO.notImplementedYet();
            }
        }

        public SyncVersion(String type, String version) {
            this.mType = type;
            this.mVersion = version;
        }

        public String getType() {
            return this.mType;
        }

        public String getVersion() {
            return this.mVersion;
        }
    }

    public ClientSyncRequest(ArrayList<SyncVersion> syncVersions) {
        this.mSyncVersions = syncVersions;
    }

    public ArrayList<SyncVersion> getSyncVersions() {
        return this.mSyncVersions;
    }
}
