package com.persianswitch.sdk.payment.model;

import android.content.Context;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.utils.Json;
import com.persianswitch.sdk.base.utils.TODO;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.persianswitch.sdk.payment.model.req.AbsRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class HostDataRequestField {
    private static final String TAG = "HostDataRequestField";
    private final String mHostDataRequest;
    private final String mHostDataSign;
    private final String mHostVersion;
    private final String mSDKProtocolVersion;

    private static class EntityJsonParser implements Jsonable<HostDataRequestField> {
        private EntityJsonParser() {
        }

        public JSONObject toJson(HostDataRequestField hostDataRequestField) throws JsonWriteException {
            try {
                Map<String, Object> objectMap = new HashMap();
                objectMap.put("hreq", hostDataRequestField.mHostDataRequest);
                objectMap.put("hsign", hostDataRequestField.mHostDataSign);
                objectMap.put("ver", hostDataRequestField.mSDKProtocolVersion);
                objectMap.put("hver", hostDataRequestField.mHostVersion);
                return Json.toJson(objectMap);
            } catch (Exception e) {
                throw new JsonWriteException(e.getMessage());
            }
        }

        public JSONObject parseJson(HostDataRequestField instanceObject, String json) throws JsonParseException {
            return (JSONObject) TODO.notImplementedYet();
        }
    }

    public HostDataRequestField(String hostDataRequest, String hostDataSign, String sdkProtocolVersion, String hostVersion) {
        this.mHostDataRequest = hostDataRequest;
        this.mHostDataSign = hostDataSign;
        this.mSDKProtocolVersion = sdkProtocolVersion;
        this.mHostVersion = hostVersion;
    }

    public static HostDataRequestField withHostVersion(Context context) {
        return new HostDataRequestField(null, null, null, AbsRequest.getHostVersion(context));
    }

    public JSONObject toJson() {
        try {
            return new EntityJsonParser().toJson(this);
        } catch (JsonWriteException e) {
            SDKLog.m37e(TAG, "error while parse json for host data request", new Object[0]);
            return new JSONObject();
        }
    }
}
