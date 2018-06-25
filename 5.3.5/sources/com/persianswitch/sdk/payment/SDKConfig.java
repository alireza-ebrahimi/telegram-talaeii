package com.persianswitch.sdk.payment;

import android.content.Context;
import android.os.Bundle;
import com.persianswitch.sdk.BuildConfig;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.style.HostPersonalizedConfig;
import com.persianswitch.sdk.base.style.PersonalizedConfig;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class SDKConfig implements Config {
    private static String sURL = null;

    public static void injectQAConfig(Bundle config) {
        sURL = config.getString("config.url", "");
    }

    public static String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return StringUtils.isEquals("com.persianswitch.sdk.test", packageName) ? "com.persianswitch.sdk.app" : packageName;
    }

    public String getServerUrl() {
        if (!isDebugMode() || StringUtils.isEmpty(sURL)) {
            return BuildConfig.SERVER_URL;
        }
        return sURL;
    }

    public String getVersion() {
        return BuildConfig.SDK_VERSION;
    }

    public String getApplicationType() {
        return "10";
    }

    public String getDistributorCode() {
        return BuildConfig.DISTRIBUTOR_CODE;
    }

    public boolean isDebugMode() {
        return false;
    }

    public PersonalizedConfig getPersonalizedConfig(int hostId) {
        return new HostPersonalizedConfig(hostId);
    }
}
