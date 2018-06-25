package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeviceIdentifierProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Beta extends Kit<Boolean> implements DeviceIdentifierProvider {
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String CRASHLYTICS_BUILD_PROPERTIES = "crashlytics-build.properties";
    static final String NO_DEVICE_TOKEN = "";
    public static final String TAG = "Beta";
    private final MemoryValueCache<String> deviceTokenCache = new MemoryValueCache();
    private final DeviceTokenLoader deviceTokenLoader = new DeviceTokenLoader();
    private UpdatesController updatesController;

    public static Beta getInstance() {
        return (Beta) Fabric.getKit(Beta.class);
    }

    @TargetApi(14)
    protected boolean onPreExecute() {
        this.updatesController = createUpdatesController(VERSION.SDK_INT, (Application) getContext().getApplicationContext());
        return true;
    }

    protected Boolean doInBackground() {
        Fabric.getLogger().mo4381d(TAG, "Beta kit initializing...");
        Context context = getContext();
        IdManager idManager = getIdManager();
        if (TextUtils.isEmpty(getBetaDeviceToken(context, idManager.getInstallerPackageName()))) {
            Fabric.getLogger().mo4381d(TAG, "A Beta device token was not found for this app");
            return Boolean.valueOf(false);
        }
        Fabric.getLogger().mo4381d(TAG, "Beta device token is present, checking for app updates.");
        BetaSettingsData betaSettings = getBetaSettingsData();
        BuildProperties buildProps = loadBuildProperties(context);
        if (canCheckForUpdates(betaSettings, buildProps)) {
            this.updatesController.initialize(context, this, idManager, betaSettings, buildProps, new PreferenceStoreImpl(this), new SystemCurrentTimeProvider(), new DefaultHttpRequestFactory(Fabric.getLogger()));
        }
        return Boolean.valueOf(true);
    }

    @TargetApi(14)
    UpdatesController createUpdatesController(int apiLevel, Application application) {
        if (apiLevel >= 14) {
            return new ActivityLifecycleCheckForUpdatesController(getFabric().getActivityLifecycleManager(), getFabric().getExecutorService());
        }
        return new ImmediateCheckForUpdatesController();
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        String betaDeviceToken = getBetaDeviceToken(getContext(), getIdManager().getInstallerPackageName());
        Map<DeviceIdentifierType, String> ids = new HashMap();
        if (!TextUtils.isEmpty(betaDeviceToken)) {
            ids.put(DeviceIdentifierType.FONT_TOKEN, betaDeviceToken);
        }
        return ids;
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:beta";
    }

    public String getVersion() {
        return "1.2.7.19";
    }

    boolean canCheckForUpdates(BetaSettingsData betaSettings, BuildProperties buildProps) {
        return (betaSettings == null || TextUtils.isEmpty(betaSettings.updateUrl) || buildProps == null) ? false : true;
    }

    private String getBetaDeviceToken(Context context, String installerPackageName) {
        String token = null;
        try {
            String cachedToken = (String) this.deviceTokenCache.get(context, this.deviceTokenLoader);
            if ("".equals(cachedToken)) {
                token = null;
            } else {
                token = cachedToken;
            }
        } catch (Exception e) {
            Fabric.getLogger().mo4384e(TAG, "Failed to load the Beta device token", e);
        }
        Fabric.getLogger().mo4381d(TAG, "Beta device token present: " + (!TextUtils.isEmpty(token)));
        return token;
    }

    private BetaSettingsData getBetaSettingsData() {
        SettingsData settingsData = Settings.getInstance().awaitSettingsData();
        if (settingsData != null) {
            return settingsData.betaSettingsData;
        }
        return null;
    }

    private BuildProperties loadBuildProperties(Context context) {
        InputStream buildPropsStream = null;
        BuildProperties buildProps = null;
        try {
            buildPropsStream = context.getAssets().open(CRASHLYTICS_BUILD_PROPERTIES);
            if (buildPropsStream != null) {
                buildProps = BuildProperties.fromPropertiesStream(buildPropsStream);
                Fabric.getLogger().mo4381d(TAG, buildProps.packageName + " build properties: " + buildProps.versionName + " (" + buildProps.versionCode + ") - " + buildProps.buildId);
            }
            if (buildPropsStream != null) {
                try {
                    buildPropsStream.close();
                } catch (IOException e) {
                    Fabric.getLogger().mo4384e(TAG, "Error closing Beta build properties asset", e);
                }
            }
        } catch (Exception e2) {
            Fabric.getLogger().mo4384e(TAG, "Error reading Beta build properties", e2);
            if (buildPropsStream != null) {
                try {
                    buildPropsStream.close();
                } catch (IOException e3) {
                    Fabric.getLogger().mo4384e(TAG, "Error closing Beta build properties asset", e3);
                }
            }
        } catch (Throwable th) {
            if (buildPropsStream != null) {
                try {
                    buildPropsStream.close();
                } catch (IOException e32) {
                    Fabric.getLogger().mo4384e(TAG, "Error closing Beta build properties asset", e32);
                }
            }
        }
        return buildProps;
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(getContext(), CRASHLYTICS_API_ENDPOINT);
    }
}
