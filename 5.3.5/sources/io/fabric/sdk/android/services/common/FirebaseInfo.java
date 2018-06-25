package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;

public class FirebaseInfo {
    static final String FIREBASE_FEATURE_SWITCH = "com.crashlytics.useFirebaseAppId";
    static final String GOOGLE_APP_ID = "google_app_id";

    protected String getApiKeyFromFirebaseAppId(Context context) {
        int id = CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string");
        if (id == 0) {
            return null;
        }
        Fabric.getLogger().mo4381d(Fabric.TAG, "Generating Crashlytics ApiKey from google_app_id in Strings");
        return createApiKeyFromFirebaseAppId(context.getResources().getString(id));
    }

    protected String createApiKeyFromFirebaseAppId(String appId) {
        return CommonUtils.sha256(appId).substring(0, 40);
    }

    public boolean isFirebaseCrashlyticsEnabled(Context context) {
        if (CommonUtils.getBooleanResourceValue(context, FIREBASE_FEATURE_SWITCH, false)) {
            return true;
        }
        boolean hasGoogleAppId;
        if (CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string") != 0) {
            hasGoogleAppId = true;
        } else {
            hasGoogleAppId = false;
        }
        boolean hasApiKey;
        if (TextUtils.isEmpty(new ApiKey().getApiKeyFromManifest(context)) && TextUtils.isEmpty(new ApiKey().getApiKeyFromStrings(context))) {
            hasApiKey = false;
        } else {
            hasApiKey = true;
        }
        if (!hasGoogleAppId || hasApiKey) {
            return false;
        }
        return true;
    }
}
