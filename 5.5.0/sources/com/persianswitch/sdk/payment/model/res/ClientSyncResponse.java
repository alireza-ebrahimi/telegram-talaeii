package com.persianswitch.sdk.payment.model.res;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.persianswitch.sdk.base.utils.JsonParser;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ClientSyncResponse {
    /* renamed from: a */
    private final List<SyncData> f7625a;

    public static class EntityJsonParser implements JsonParser<ClientSyncResponse> {
        /* renamed from: a */
        public ClientSyncResponse m11360a(String str) {
            List arrayList = new ArrayList();
            JSONArray jSONArray = new JSONObject(str).getJSONArray("syncData");
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject != null) {
                        arrayList.add(new SyncData(jSONObject.getString(Param.TYPE), jSONObject.getString("ver"), jSONObject.getString(DataBufferSafeParcelable.DATA_FIELD)));
                    }
                }
            }
            return new ClientSyncResponse(arrayList);
        }
    }

    public static class SyncData {
        /* renamed from: a */
        private final String f7622a;
        /* renamed from: b */
        private final String f7623b;
        /* renamed from: c */
        private final String f7624c;

        public SyncData(String str, String str2, String str3) {
            this.f7622a = str;
            this.f7623b = str2;
            this.f7624c = str3;
        }

        /* renamed from: a */
        public String m11361a() {
            return this.f7622a;
        }

        /* renamed from: b */
        public String m11362b() {
            return this.f7623b;
        }

        /* renamed from: c */
        public String m11363c() {
            return this.f7624c;
        }
    }

    public ClientSyncResponse(List<SyncData> list) {
        this.f7625a = list;
    }

    /* renamed from: a */
    public static ClientSyncResponse m11364a(String str) {
        try {
            return new EntityJsonParser().m11360a(str);
        } catch (JSONException e) {
            return null;
        }
    }

    /* renamed from: a */
    public List<SyncData> m11365a() {
        return this.f7625a;
    }
}
