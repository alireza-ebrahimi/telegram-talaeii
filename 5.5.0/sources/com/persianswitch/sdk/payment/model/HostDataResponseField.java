package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import org.json.JSONObject;

public class HostDataResponseField {
    /* renamed from: a */
    private String f7508a = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: b */
    private String f7509b = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: c */
    private int f7510c = 0;

    public enum ContinuePlan {
        ALLOW_CONTINUE("0"),
        RETURN_TO_MERCHANT("1");
        
        /* renamed from: c */
        private final String f7507c;

        private ContinuePlan(String str) {
            this.f7507c = str;
        }
    }

    private static class JsonParser implements Jsonable<HostDataResponseField> {
        private JsonParser() {
        }

        /* renamed from: a */
        public JSONObject m11153a(HostDataResponseField hostDataResponseField, String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("hresp")) {
                    hostDataResponseField.f7508a = jSONObject.getString("hresp");
                }
                if (jSONObject.has("hsign")) {
                    hostDataResponseField.f7509b = jSONObject.getString("hsign");
                }
                if (jSONObject.has("hstat")) {
                    hostDataResponseField.f7510c = jSONObject.getInt("hstat");
                }
                return jSONObject;
            } catch (Exception e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    /* renamed from: a */
    public static HostDataResponseField m11155a(String str) {
        try {
            HostDataResponseField hostDataResponseField = new HostDataResponseField();
            new JsonParser().m11153a(hostDataResponseField, str);
            return hostDataResponseField;
        } catch (JsonParseException e) {
            return null;
        }
    }

    /* renamed from: a */
    public String m11158a() {
        return this.f7508a;
    }

    /* renamed from: b */
    public String m11159b() {
        return this.f7509b;
    }

    /* renamed from: c */
    public int m11160c() {
        return this.f7510c;
    }
}
