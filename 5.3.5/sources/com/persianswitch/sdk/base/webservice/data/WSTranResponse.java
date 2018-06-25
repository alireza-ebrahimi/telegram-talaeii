package com.persianswitch.sdk.base.webservice.data;

import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.thin.downloadmanager.BuildConfig;
import org.json.JSONObject;

public class WSTranResponse extends WSResponse {
    private String mCardId;
    protected String mReferenceNumber;
    private String mTranBalance;

    public enum ExpirationStatus {
        UNKNOWN("0"),
        SAVED(BuildConfig.VERSION_NAME),
        UNSAVED("2"),
        REMOVE_CAUSE_CHANGED("3");
        
        private final String serverSign;

        private ExpirationStatus(String serverSign) {
            this.serverSign = serverSign;
        }

        public static ExpirationStatus getInstance(String expirySegment) {
            Object obj = -1;
            switch (expirySegment.hashCode()) {
                case 48:
                    if (expirySegment.equals("0")) {
                        obj = 3;
                        break;
                    }
                    break;
                case 49:
                    if (expirySegment.equals(BuildConfig.VERSION_NAME)) {
                        obj = null;
                        break;
                    }
                    break;
                case 50:
                    if (expirySegment.equals("2")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 51:
                    if (expirySegment.equals("3")) {
                        obj = 2;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    return SAVED;
                case 1:
                    return UNSAVED;
                case 2:
                    return REMOVE_CAUSE_CHANGED;
                default:
                    return UNKNOWN;
            }
        }
    }

    private static final class JsonParser implements Jsonable<WSTranResponse> {
        private JsonParser() {
        }

        public JSONObject toJson(WSTranResponse object) throws JsonWriteException {
            try {
                JSONObject jsonObject = new JsonParser().toJson((WSResponse) object);
                jsonObject.put("rn", object.mReferenceNumber);
                jsonObject.put("cd", object.mCardId);
                jsonObject.put("tb", object.mTranBalance);
                return jsonObject;
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }

        public JSONObject parseJson(WSTranResponse instanceObject, String json) throws JsonParseException {
            try {
                JSONObject jsonObject = new JsonParser().parseJson((WSResponse) instanceObject, json);
                if (jsonObject.has("rn")) {
                    instanceObject.mReferenceNumber = jsonObject.getString("rn");
                }
                if (jsonObject.has("cd")) {
                    instanceObject.mCardId = jsonObject.getString("cd");
                }
                if (jsonObject.has("tb")) {
                    instanceObject.mTranBalance = jsonObject.getString("tb");
                }
                return jsonObject;
            } catch (Exception e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    public String getReferenceNumber() {
        return this.mReferenceNumber;
    }

    public String getCardId() {
        return this.mCardId;
    }

    public String getTranBalance() {
        return this.mTranBalance;
    }

    public static WSTranResponse fromJson(String str) {
        try {
            WSTranResponse responseObject = new WSTranResponse();
            new JsonParser().parseJson(responseObject, str);
            return responseObject;
        } catch (JsonParseException e) {
            return null;
        }
    }

    public String toJson() {
        String str = null;
        try {
            str = new JsonParser().toJson(this).toString();
        } catch (JsonWriteException e) {
        }
        return str;
    }
}
