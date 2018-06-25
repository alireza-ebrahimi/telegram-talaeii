package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.thin.downloadmanager.BuildConfig;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class HostDataResponseField {
    private String mHostDataResponse;
    private String mHostDataSign;
    private int mStatusCode;

    public enum ContinuePlan {
        ALLOW_CONTINUE("0"),
        RETURN_TO_MERCHANT(BuildConfig.VERSION_NAME);
        
        private final String mProtocolValue;

        private ContinuePlan(String protocolValue) {
            this.mProtocolValue = protocolValue;
        }

        public static ContinuePlan byProtocolValue(String protocolValue) {
            for (ContinuePlan continuePlan : values()) {
                if (continuePlan.mProtocolValue.equals(protocolValue)) {
                    return continuePlan;
                }
            }
            return ALLOW_CONTINUE;
        }
    }

    private static class JsonParser implements Jsonable<HostDataResponseField> {
        private JsonParser() {
        }

        public JSONObject toJson(HostDataResponseField hostDataField) throws JsonWriteException {
            try {
                Map<String, Object> objectMap = new HashMap();
                if (hostDataField.mHostDataResponse != null) {
                    objectMap.put("hresp", hostDataField.mHostDataResponse);
                } else {
                    objectMap.put("hresp", "");
                }
                if (hostDataField.mHostDataSign != null) {
                    objectMap.put("hsign", hostDataField.mHostDataSign);
                } else {
                    objectMap.put("hsign", "");
                }
                objectMap.put("hstat", Integer.valueOf(hostDataField.mStatusCode));
                return new JSONObject(objectMap);
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }

        public JSONObject parseJson(HostDataResponseField instanceObject, String json) throws JsonParseException {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("hresp")) {
                    instanceObject.mHostDataResponse = jsonObject.getString("hresp");
                }
                if (jsonObject.has("hsign")) {
                    instanceObject.mHostDataSign = jsonObject.getString("hsign");
                }
                if (jsonObject.has("hstat")) {
                    instanceObject.mStatusCode = jsonObject.getInt("hstat");
                }
                return jsonObject;
            } catch (Exception e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    public HostDataResponseField() {
        this.mHostDataResponse = "";
        this.mHostDataSign = "";
        this.mStatusCode = 0;
    }

    public HostDataResponseField(String hostDataResponse, String hostDataSign, int statusCode) {
        this.mHostDataResponse = hostDataResponse;
        this.mHostDataSign = hostDataSign;
        this.mStatusCode = statusCode;
    }

    public String getHostDataResponse() {
        return this.mHostDataResponse;
    }

    public String getHostDataSign() {
        return this.mHostDataSign;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = null;
        try {
            jSONObject = new JsonParser().toJson(this);
        } catch (JsonWriteException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static HostDataResponseField fromJson(String str) {
        try {
            HostDataResponseField responseHostData = new HostDataResponseField();
            new JsonParser().parseJson(responseHostData, str);
            return responseHostData;
        } catch (JsonParseException e) {
            return null;
        }
    }
}
