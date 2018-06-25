package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.text.TextUtils;
import io.fabric.sdk.android.services.events.EventTransform;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventTransform implements EventTransform<SessionEvent> {
    static final String ADVERTISING_ID_KEY = "advertisingId";
    static final String ANDROID_ID_KEY = "androidId";
    static final String APP_BUNDLE_ID_KEY = "appBundleId";
    static final String APP_VERSION_CODE_KEY = "appVersionCode";
    static final String APP_VERSION_NAME_KEY = "appVersionName";
    static final String BETA_DEVICE_TOKEN_KEY = "betaDeviceToken";
    static final String BUILD_ID_KEY = "buildId";
    static final String CUSTOM_ATTRIBUTES = "customAttributes";
    static final String CUSTOM_TYPE = "customType";
    static final String DETAILS_KEY = "details";
    static final String DEVICE_MODEL_KEY = "deviceModel";
    static final String EXECUTION_ID_KEY = "executionId";
    static final String INSTALLATION_ID_KEY = "installationId";
    static final String LIMIT_AD_TRACKING_ENABLED_KEY = "limitAdTrackingEnabled";
    static final String OS_VERSION_KEY = "osVersion";
    static final String PREDEFINED_ATTRIBUTES = "predefinedAttributes";
    static final String PREDEFINED_TYPE = "predefinedType";
    static final String TIMESTAMP_KEY = "timestamp";
    static final String TYPE_KEY = "type";

    SessionEventTransform() {
    }

    public byte[] toBytes(SessionEvent event) throws IOException {
        return buildJsonForEvent(event).toString().getBytes("UTF-8");
    }

    @TargetApi(9)
    public JSONObject buildJsonForEvent(SessionEvent event) throws IOException {
        try {
            JSONObject jsonObject = new JSONObject();
            SessionEventMetadata eventMetadata = event.sessionEventMetadata;
            jsonObject.put(APP_BUNDLE_ID_KEY, eventMetadata.appBundleId);
            jsonObject.put(EXECUTION_ID_KEY, eventMetadata.executionId);
            jsonObject.put(INSTALLATION_ID_KEY, eventMetadata.installationId);
            if (TextUtils.isEmpty(eventMetadata.advertisingId)) {
                jsonObject.put(ANDROID_ID_KEY, eventMetadata.androidId);
            } else {
                jsonObject.put(ADVERTISING_ID_KEY, eventMetadata.advertisingId);
            }
            jsonObject.put(LIMIT_AD_TRACKING_ENABLED_KEY, eventMetadata.limitAdTrackingEnabled);
            jsonObject.put(BETA_DEVICE_TOKEN_KEY, eventMetadata.betaDeviceToken);
            jsonObject.put(BUILD_ID_KEY, eventMetadata.buildId);
            jsonObject.put(OS_VERSION_KEY, eventMetadata.osVersion);
            jsonObject.put(DEVICE_MODEL_KEY, eventMetadata.deviceModel);
            jsonObject.put(APP_VERSION_CODE_KEY, eventMetadata.appVersionCode);
            jsonObject.put(APP_VERSION_NAME_KEY, eventMetadata.appVersionName);
            jsonObject.put("timestamp", event.timestamp);
            jsonObject.put("type", event.type.toString());
            if (event.details != null) {
                jsonObject.put(DETAILS_KEY, new JSONObject(event.details));
            }
            jsonObject.put(CUSTOM_TYPE, event.customType);
            if (event.customAttributes != null) {
                jsonObject.put(CUSTOM_ATTRIBUTES, new JSONObject(event.customAttributes));
            }
            jsonObject.put(PREDEFINED_TYPE, event.predefinedType);
            if (event.predefinedAttributes != null) {
                jsonObject.put(PREDEFINED_ATTRIBUTES, new JSONObject(event.predefinedAttributes));
            }
            return jsonObject;
        } catch (JSONException e) {
            if (VERSION.SDK_INT >= 9) {
                throw new IOException(e.getMessage(), e);
            }
            throw new IOException(e.getMessage());
        }
    }
}
