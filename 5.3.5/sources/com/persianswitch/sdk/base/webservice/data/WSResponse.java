package com.persianswitch.sdk.base.webservice.data;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.OpCode;
import com.persianswitch.sdk.base.webservice.StatusCode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class WSResponse {
    public static final int SECURITY_MODE_NORMAL = 1;
    public static final int SECURITY_MODE_NORMAL_WITH_UPDATE = 2;
    public static final int SECURITY_MODE_WIPE = 10;
    protected String mAds;
    protected String mDescription;
    protected JSONObject mExtraData;
    protected JSONObject mHostData;
    protected int mHostId;
    protected String[] mLegacyExtraData;
    protected int mOpCode;
    protected int mPoint;
    protected int mSecurityCode;
    protected String mServerTime;
    protected int mStatusCode;
    protected long mTranId;

    static class JsonParser implements Jsonable<WSResponse> {
        JsonParser() {
        }

        public JSONObject toJson(WSResponse object) throws JsonWriteException {
            try {
                Map<String, Object> objectMap = new HashMap();
                objectMap.put("hi", Integer.valueOf(object.mHostId));
                objectMap.put("tr", Long.valueOf(object.mTranId));
                objectMap.put("st", Integer.valueOf(object.mStatusCode));
                objectMap.put("op", Integer.valueOf(object.mOpCode));
                objectMap.put("sc", Integer.valueOf(object.mSecurityCode));
                objectMap.put("ds", object.mDescription);
                objectMap.put("sm", object.mServerTime);
                objectMap.put("ad", object.mAds);
                objectMap.put("pi", Integer.valueOf(object.mPoint));
                objectMap.put("hd", object.mHostData);
                if (object.mLegacyExtraData != null) {
                    objectMap.put("ed", new JSONArray(Arrays.asList(object.mLegacyExtraData)));
                }
                if (object.mExtraData != null) {
                    objectMap.put("ej", object.mExtraData);
                }
                return new JSONObject(objectMap);
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }

        public JSONObject parseJson(WSResponse instanceObject, String json) throws JsonParseException {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("hi")) {
                    instanceObject.mHostId = jsonObject.getInt("hi");
                }
                if (jsonObject.has("tr")) {
                    instanceObject.mTranId = jsonObject.getLong("tr");
                }
                if (jsonObject.has("st")) {
                    instanceObject.mStatusCode = jsonObject.getInt("st");
                }
                if (jsonObject.has("op")) {
                    instanceObject.mOpCode = jsonObject.getInt("op");
                }
                if (jsonObject.has("sc")) {
                    instanceObject.mSecurityCode = jsonObject.getInt("sc");
                }
                if (jsonObject.has("ds")) {
                    instanceObject.mDescription = jsonObject.getString("ds");
                }
                if (jsonObject.has("sm")) {
                    instanceObject.mServerTime = jsonObject.getString("sm");
                }
                if (jsonObject.has("ad")) {
                    instanceObject.mAds = jsonObject.getString("ad");
                }
                if (jsonObject.has("pi")) {
                    instanceObject.mPoint = jsonObject.getInt("pi");
                }
                if (jsonObject.has("hd")) {
                    instanceObject.mHostData = jsonObject.getJSONObject("hd");
                }
                if (jsonObject.has("ed")) {
                    JSONArray jsonExtraData = jsonObject.getJSONArray("ed");
                    if (jsonExtraData != null) {
                        instanceObject.mLegacyExtraData = new String[jsonExtraData.length()];
                        for (int i = 0; i < jsonExtraData.length(); i++) {
                            instanceObject.mLegacyExtraData[i] = jsonExtraData.getString(i);
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

    public WSResponse(String[] legacyExtraData) {
        this.mLegacyExtraData = legacyExtraData;
    }

    public static WSResponse fromJson(String str) {
        try {
            WSResponse WSResponse = new WSResponse();
            new JsonParser().parseJson(WSResponse, str);
            return WSResponse;
        } catch (JsonParseException e) {
            return null;
        }
    }

    public OpCode getOpCode() {
        return OpCode.getByCode(this.mOpCode);
    }

    public void setOpCode(int opCode) {
        this.mOpCode = opCode;
    }

    public int getSecurityCode() {
        return this.mSecurityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.mSecurityCode = securityCode;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    public StatusCode getStatus() {
        return StatusCode.getByCode(getStatusCode());
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getErrorMessage(Context context) {
        if (StringUtils.isEmpty(this.mDescription)) {
            return StatusCode.getErrorMessage(context, getStatusCode());
        }
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getPoint() {
        return this.mPoint;
    }

    public void setPoint(int point) {
        this.mPoint = point;
    }

    public JSONObject getHostData() {
        return this.mHostData;
    }

    public void setHostData(JSONObject hostData) {
        this.mHostData = hostData;
    }

    public JSONObject getExtraData() {
        return this.mExtraData;
    }

    public void setExtraData(JSONObject extraData) {
        this.mExtraData = extraData;
    }

    public String[] getLegacyExtraData() {
        return this.mLegacyExtraData;
    }

    public void setLegacyExtraData(String[] legacyExtraData) {
        this.mLegacyExtraData = legacyExtraData;
    }

    public void setHostId(int hostId) {
        this.mHostId = hostId;
    }

    public void setAds(String ads) {
        this.mAds = ads;
    }

    public void setServerTime(String serverTime) {
        this.mServerTime = serverTime;
    }

    public String getServerTime() {
        return this.mServerTime;
    }

    public void setTranId(long tranId) {
        this.mTranId = tranId;
    }

    public String toJson() {
        try {
            return new JsonParser().toJson(this).toString();
        } catch (JsonWriteException e) {
            return null;
        }
    }
}
