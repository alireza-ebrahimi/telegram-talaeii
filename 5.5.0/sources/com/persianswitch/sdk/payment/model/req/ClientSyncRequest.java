package com.persianswitch.sdk.payment.model.req;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.persianswitch.sdk.base.utils.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ClientSyncRequest {
    /* renamed from: a */
    private final ArrayList<SyncVersion> f7592a;

    public static class EntityJsonParser implements JsonParser<ClientSyncRequest> {
        /* renamed from: a */
        public JSONObject m11300a(ClientSyncRequest clientSyncRequest) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            EntityJsonParser entityJsonParser = new EntityJsonParser();
            Iterator it = clientSyncRequest.m11304a().iterator();
            while (it.hasNext()) {
                jSONArray.put(entityJsonParser.m11301a((SyncVersion) it.next()));
            }
            jSONObject.put("syncs", jSONArray);
            return jSONObject;
        }
    }

    public static class SyncVersion {
        /* renamed from: a */
        private final String f7590a;
        /* renamed from: b */
        private final String f7591b;

        private static class EntityJsonParser implements JsonParser<SyncVersion> {
            private EntityJsonParser() {
            }

            /* renamed from: a */
            public JSONObject m11301a(SyncVersion syncVersion) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Param.TYPE, syncVersion.m11302a());
                jSONObject.put("ver", syncVersion.m11303b());
                return jSONObject;
            }
        }

        public SyncVersion(String str, String str2) {
            this.f7590a = str;
            this.f7591b = str2;
        }

        /* renamed from: a */
        public String m11302a() {
            return this.f7590a;
        }

        /* renamed from: b */
        public String m11303b() {
            return this.f7591b;
        }
    }

    public ClientSyncRequest(ArrayList<SyncVersion> arrayList) {
        this.f7592a = arrayList;
    }

    /* renamed from: a */
    public ArrayList<SyncVersion> m11304a() {
        return this.f7592a;
    }
}
