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
import com.thin.downloadmanager.BuildConfig;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class WSRequest {
    private String deviceIdentifier;
    private String mApplicationDigest;
    private long mApplicationId;
    private String mApplicationInfo;
    private String mApplicationToken;
    private final String mDigestAlgorithm = CommonUtils.SHA1_INSTANCE;
    private JSONObject mExtraData = new JSONObject();
    private JSONObject mHostData;
    private int mHostId;
    @Deprecated
    private String[] mLegacyExtraData = new String[0];
    private String mMobileNumber;
    private int mOperationCode;
    private String mPackageName;
    private String mRequestTime;
    private long mTransactionId;

    static class JsonParser implements Jsonable<WSRequest> {
        JsonParser() {
        }

        public JSONObject toJson(WSRequest wsRequest) throws JsonWriteException {
            try {
                Map<String, Object> objectMap = new HashMap();
                objectMap.put("se", "");
                objectMap.put("ap", Long.valueOf(wsRequest.mApplicationId));
                if (wsRequest.mApplicationToken != null) {
                    objectMap.put("at", wsRequest.mApplicationToken);
                }
                objectMap.put("op", Integer.valueOf(wsRequest.mOperationCode));
                objectMap.put("tr", Long.valueOf(wsRequest.mTransactionId));
                if (wsRequest.mApplicationInfo != null) {
                    objectMap.put("av", wsRequest.mApplicationInfo);
                }
                if (wsRequest.mRequestTime != null) {
                    objectMap.put("te", wsRequest.mRequestTime);
                }
                if (wsRequest.mMobileNumber != null) {
                    objectMap.put("mo", wsRequest.mMobileNumber);
                }
                objectMap.put("hi", Integer.valueOf(wsRequest.mHostId));
                if (wsRequest.deviceIdentifier != null) {
                    objectMap.put("de", wsRequest.deviceIdentifier);
                }
                objectMap.put("pn", wsRequest.getPackageName());
                objectMap.put("kd", wsRequest.getApplicationDigest());
                objectMap.put("ka", wsRequest.getDigestAlgorithm());
                if (wsRequest.mHostData != null) {
                    objectMap.put("hd", wsRequest.mHostData);
                }
                if (wsRequest.mLegacyExtraData != null) {
                    objectMap.put("ed", new JSONArray(Arrays.asList(wsRequest.mLegacyExtraData)));
                }
                if (wsRequest.mExtraData != null) {
                    objectMap.put("ej", wsRequest.mExtraData);
                }
                return new JSONObject(objectMap);
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }

        public JSONObject parseJson(WSRequest instanceObject, String json) throws JsonParseException {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("ap")) {
                    instanceObject.mApplicationId = jsonObject.getLong("ap");
                }
                if (jsonObject.has("at")) {
                    instanceObject.mApplicationToken = jsonObject.getString("at");
                }
                if (jsonObject.has("op")) {
                    instanceObject.mOperationCode = jsonObject.getInt("op");
                }
                if (jsonObject.has("tr")) {
                    instanceObject.mTransactionId = jsonObject.getLong("tr");
                }
                if (jsonObject.has("av")) {
                    instanceObject.mApplicationInfo = jsonObject.getString("av");
                }
                if (jsonObject.has("te")) {
                    instanceObject.mRequestTime = jsonObject.getString("te");
                }
                if (jsonObject.has("mo")) {
                    instanceObject.mMobileNumber = jsonObject.getString("mo");
                }
                if (jsonObject.has("de")) {
                    instanceObject.deviceIdentifier = jsonObject.getString("de");
                }
                if (jsonObject.has("hi")) {
                    instanceObject.mHostId = jsonObject.getInt("hi");
                }
                if (jsonObject.has("hd")) {
                    instanceObject.mHostData = jsonObject.getJSONObject("hd");
                }
                if (jsonObject.has("ed")) {
                    JSONArray array = jsonObject.getJSONArray("ed");
                    if (array != null) {
                        instanceObject.mLegacyExtraData = new String[array.length()];
                        for (int i = 0; i < array.length(); i++) {
                            instanceObject.mLegacyExtraData[i] = array.getString(i);
                        }
                    }
                }
                if (jsonObject.has("ej")) {
                    instanceObject.mExtraData = jsonObject.getJSONObject("ej");
                }
                return jsonObject;
            } catch (Exception e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    public static WSRequest create(Context context, Config config, int opCode) {
        WSRequest wsRequest = new WSRequest();
        wsRequest.setApplicationId(BaseSetting.getApplicationId(context));
        wsRequest.setApplicationToken(BaseSetting.getApplicationToken(context));
        wsRequest.setApplicationInfo(DeviceInfo.getApplicationInfo(context, config));
        wsRequest.setMobileNumber(BaseSetting.getMobileNumber(context));
        wsRequest.setOperationCode(opCode);
        wsRequest.setHostId(BaseSetting.getHostId(context));
        wsRequest.setDeviceIdentifier(Base64.encodeToString(DeviceInfo.generateDeviceIdentifier(context).getBytes(), 2));
        wsRequest.setPackageName(SDKConfig.getPackageName(context));
        wsRequest.setApplicationDigest(CertificateUtils.getCertificateSHA1Fingerprint(context));
        return wsRequest;
    }

    public long getTransactionId() {
        return this.mTransactionId;
    }

    public void setTransactionId(long transactionId) {
        this.mTransactionId = transactionId;
    }

    public long getApplicationId() {
        return this.mApplicationId;
    }

    public void setApplicationId(long applicationId) {
        this.mApplicationId = applicationId;
    }

    public String getApplicationToken() {
        return this.mApplicationToken;
    }

    public void setApplicationToken(String applicationToken) {
        this.mApplicationToken = applicationToken;
    }

    public String getDeviceIdentifier() {
        return this.deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public String getApplicationInfo() {
        return this.mApplicationInfo;
    }

    public void setApplicationInfo(String applicationInfo) {
        this.mApplicationInfo = applicationInfo;
    }

    public String getMobileNumber() {
        return this.mMobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mMobileNumber = mobileNumber;
    }

    public int getOperationCode() {
        return this.mOperationCode;
    }

    public void setOperationCode(int operationCode) {
        this.mOperationCode = operationCode;
    }

    public String getRequestTime() {
        return this.mRequestTime;
    }

    public void setRequestTime(String requestTime) {
        this.mRequestTime = requestTime;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getApplicationDigest() {
        return this.mApplicationDigest;
    }

    public void setApplicationDigest(String applicationDigest) {
        this.mApplicationDigest = applicationDigest;
    }

    public String getDigestAlgorithm() {
        return CommonUtils.SHA1_INSTANCE;
    }

    public int getHostId() {
        return this.mHostId;
    }

    public void setHostId(int hostId) {
        this.mHostId = hostId;
    }

    public JSONObject getHostData() {
        return this.mHostData;
    }

    public void setHostData(JSONObject hostData) {
        this.mHostData = hostData;
    }

    @Deprecated
    public void setLegacyExtraData(String[] legacyExtraData) {
        this.mLegacyExtraData = legacyExtraData;
    }

    public JSONObject getExtraData() {
        return this.mExtraData;
    }

    public void setExtraData(JSONObject extraData) {
        this.mExtraData = extraData;
    }

    public String toJson() {
        try {
            return new JsonParser().toJson(this).toString();
        } catch (Exception e) {
            return "";
        }
    }

    public String getUrl(Config config) {
        return String.format(Locale.US, "%s/%s/%d/%d", new Object[]{config.getServerUrl(), getPath(BuildConfig.VERSION_NAME, "6", true), Long.valueOf(getApplicationId()), Integer.valueOf(getHostId())});
    }

    public String getPath(String osId, String certId, boolean ssl) {
        int certIdInt = Integer.parseInt(certId);
        if (ssl) {
            certIdInt += 10000;
        }
        return String.format(Locale.US, "%s/%s/%d", new Object[]{getBaseUrl(), osId, Integer.valueOf(certIdInt)});
    }

    protected String getBaseUrl() {
        return "sdk/w01";
    }
}
