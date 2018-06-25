package com.persianswitch.sdk.base.webservice.data;

import android.content.Context;
import android.util.Base64;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.security.DeviceInfo;
import com.persianswitch.sdk.base.utils.CertificateUtils;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.persianswitch.sdk.payment.SDKConfig;
import org.json.JSONObject;

public class WSTranRequest extends WSRequest {
    private long amount;
    private String card;
    private String pin;

    private static final class JsonParser implements Jsonable<WSTranRequest> {
        private JsonParser() {
        }

        public JSONObject toJson(WSTranRequest wsTranRequest) throws JsonWriteException {
            try {
                JSONObject jsonObject = new JsonParser().toJson((WSRequest) wsTranRequest);
                if (wsTranRequest.card != null) {
                    jsonObject.put("an", wsTranRequest.card);
                }
                if (wsTranRequest.pin != null) {
                    jsonObject.put("nn", wsTranRequest.pin);
                }
                jsonObject.put("ao", wsTranRequest.amount);
                return jsonObject;
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }

        public JSONObject parseJson(WSTranRequest instanceObject, String json) throws JsonParseException {
            try {
                WSRequest tranRequestObject = new WSTranRequest();
                JSONObject jsonObject = new JsonParser().parseJson(tranRequestObject, json);
                if (jsonObject.has("an")) {
                    tranRequestObject.card = jsonObject.getString("an");
                }
                if (jsonObject.has("nn")) {
                    tranRequestObject.pin = jsonObject.getString("nn");
                }
                if (jsonObject.has("ao")) {
                    tranRequestObject.amount = jsonObject.getLong("ao");
                }
                return jsonObject;
            } catch (Exception e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    private WSTranRequest() {
        this.pin = "";
        this.card = "";
        this.amount = 0;
    }

    public static WSTranRequest create(Context context, Config config, int opCode, long amount) {
        WSTranRequest wsTranRequest = new WSTranRequest();
        wsTranRequest.setApplicationId(BaseSetting.getApplicationId(context));
        wsTranRequest.setApplicationToken(BaseSetting.getApplicationToken(context));
        wsTranRequest.setApplicationInfo(DeviceInfo.getApplicationInfo(context, config));
        wsTranRequest.setMobileNumber(BaseSetting.getMobileNumber(context));
        wsTranRequest.setOperationCode(opCode);
        wsTranRequest.setHostId(BaseSetting.getHostId(context));
        wsTranRequest.setDeviceIdentifier(Base64.encodeToString(DeviceInfo.generateDeviceIdentifier(context).getBytes(), 2));
        wsTranRequest.setPackageName(SDKConfig.getPackageName(context));
        wsTranRequest.setApplicationDigest(CertificateUtils.getCertificateSHA1Fingerprint(context));
        wsTranRequest.amount = amount;
        return wsTranRequest;
    }

    public String toJson() {
        try {
            return new JsonParser().toJson(this).toString();
        } catch (Exception e) {
            return "";
        }
    }

    protected String getBaseUrl() {
        return "sdk/w01";
    }

    public String getCard() {
        return this.card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
