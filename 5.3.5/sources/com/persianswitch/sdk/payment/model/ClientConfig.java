package com.persianswitch.sdk.payment.model;

import android.content.Context;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.TODO;
import com.persianswitch.sdk.base.utils.strings.Jsonable;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonWriteException;
import com.persianswitch.sdk.payment.repo.SyncRepo;
import com.thin.downloadmanager.BuildConfig;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public final class ClientConfig {
    private static final String TAG = "ClientConfig";
    private long mCardSyncPeriodTime = TimeUnit.SECONDS.convert(1, TimeUnit.DAYS);
    private long mReportTimeout = 15;
    private RootConfigTypes mRootConfig;
    private String mRootConfigMessage;
    private boolean mShowNumberValidationDialog;
    private long mTransactionInquiryWaitTime = TimeUnit.MINUTES.toMillis(1);
    private long mTransactionTimeout = 420;
    private String mUnknownMessage;
    private boolean mUssdOriginality = false;
    private boolean mWebOriginality = false;

    private static class EntityJsonParser implements Jsonable<ClientConfig> {
        private EntityJsonParser() {
        }

        public JSONObject toJson(ClientConfig clientConfig) throws JsonWriteException {
            return (JSONObject) TODO.notImplementedYet();
        }

        public JSONObject parseJson(ClientConfig instanceObject, String json) throws JsonParseException {
            boolean z = true;
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("tranTimeout")) {
                    instanceObject.mTransactionTimeout = jsonObject.getLong("tranTimeout");
                }
                if (jsonObject.has("returnTimeout")) {
                    instanceObject.mReportTimeout = jsonObject.getLong("returnTimeout");
                }
                if (jsonObject.has("syncCardMinDelay")) {
                    instanceObject.mCardSyncPeriodTime = jsonObject.getLong("syncCardMinDelay");
                }
                if (jsonObject.has("root")) {
                    instanceObject.mRootConfig = RootConfigTypes.getEnumWithValue(jsonObject.getInt("root"));
                } else {
                    instanceObject.mRootConfig = RootConfigTypes.EnableNormal;
                }
                if (jsonObject.has("rootMsg")) {
                    instanceObject.mRootConfigMessage = jsonObject.getString("rootMsg");
                }
                if (jsonObject.has("verifyMobileNo")) {
                    if (jsonObject.getInt("verifyMobileNo") != 1) {
                        z = false;
                    }
                    instanceObject.mShowNumberValidationDialog = z;
                }
                if (jsonObject.has("originality") && jsonObject.getString("originality").length() >= 2) {
                    instanceObject.mUssdOriginality = BuildConfig.VERSION_NAME.equals(jsonObject.getString("originality").substring(0, 1));
                    instanceObject.mWebOriginality = BuildConfig.VERSION_NAME.equals(jsonObject.getString("originality").substring(1, 2));
                }
                if (jsonObject.has("inquiryWait")) {
                    instanceObject.mTransactionInquiryWaitTime = jsonObject.getLong("inquiryWait") * 1000;
                }
                if (jsonObject.has("unknownTranMsg")) {
                    instanceObject.mUnknownMessage = jsonObject.getString("unknownTranMsg");
                }
                return jsonObject;
            } catch (JSONException e) {
                throw new JsonParseException(e.getMessage());
            }
        }
    }

    public enum RootConfigTypes {
        Disable(2),
        EnableWithWarning(1),
        EnableNormal(0);
        
        private int value;

        private RootConfigTypes(int value) {
            this.value = value;
        }

        public static RootConfigTypes getEnumWithValue(int value) {
            for (RootConfigTypes rootConfigTypes : values()) {
                if (rootConfigTypes.value == value) {
                    return rootConfigTypes;
                }
            }
            return EnableNormal;
        }
    }

    public boolean isWebOriginality() {
        return this.mWebOriginality;
    }

    public boolean isUssdOriginality() {
        return this.mUssdOriginality;
    }

    public boolean isTrustAvailable() {
        return this.mUssdOriginality || this.mWebOriginality;
    }

    public long getInquiryWaitTimeMillis() {
        return this.mTransactionInquiryWaitTime;
    }

    public String getUnknownMessage() {
        return this.mUnknownMessage;
    }

    private ClientConfig() {
    }

    public static ClientConfig getInstance(Context context) {
        ClientConfig clientConfig = new ClientConfig();
        SyncableData clientConf = new SyncRepo(context).findByType(SyncType.CLIENT_CONFIG, LanguageManager.getInstance(context).getCurrentLanguage());
        if (clientConf != null) {
            try {
                new EntityJsonParser().parseJson(clientConfig, clientConf.getData());
            } catch (JsonParseException e) {
                SDKLog.m37e(TAG, "error in parse client config", new Object[0]);
            }
        } else {
            SDKLog.m37e(TAG, "client config not found use default value", new Object[0]);
        }
        return clientConfig;
    }

    public long getTransactionTimeout() {
        return this.mTransactionTimeout;
    }

    public long getReportTimeout() {
        return this.mReportTimeout;
    }

    public long getCardSyncPeriodTime() {
        return this.mCardSyncPeriodTime;
    }

    public RootConfigTypes getRootConfig() {
        return this.mRootConfig;
    }

    public String getRootConfigMessage() {
        return this.mRootConfigMessage;
    }

    public boolean getShowValidationDialog() {
        return this.mShowNumberValidationDialog;
    }
}
