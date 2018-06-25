package com.persianswitch.sdk.base.security;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.thin.downloadmanager.BuildConfig;
import java.util.Locale;

public class DeviceInfo {
    public static boolean isTablet(Context context) {
        boolean xlarge;
        if ((context.getResources().getConfiguration().screenLayout & 15) == 4) {
            xlarge = true;
        } else {
            xlarge = false;
        }
        boolean large;
        if ((context.getResources().getConfiguration().screenLayout & 15) == 3) {
            large = true;
        } else {
            large = false;
        }
        if (xlarge || large) {
            return true;
        }
        return false;
    }

    public static int generateAuthCode(Context context, String mobileNo) {
        String mixedString = StringUtils.trimJoin("", generateDeviceIdentifier(context), mobileNo);
        int code = 0;
        for (int i = 0; i < mixedString.length(); i++) {
            code += mixedString.charAt(i) * i;
        }
        return code;
    }

    public static String generateDeviceIdentifier(Context context) {
        return generateDeviceIdentifier(context, BaseSetting.getIMEI(context), BaseSetting.getWifiMAC(context));
    }

    public static String generateDeviceIdentifier(Context context, String imei, String wifiMac) {
        return StringUtils.trimJoin("|", getAndroidID(context), imei, wifiMac);
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDeviceModel() {
        return String.format(Locale.US, "%s(%s)", new Object[]{Build.MODEL, Build.MANUFACTURER});
    }

    public static String getApplicationInfo(Context context, Config config) {
        try {
            String str = ";";
            Object[] objArr = new Object[4];
            objArr[0] = LanguageManager.getInstance(context).isPersian() ? BuildConfig.VERSION_NAME : "2";
            objArr[1] = config.getVersion();
            objArr[2] = config.getApplicationType();
            objArr[3] = config.getDistributorCode();
            return StringUtils.trimJoin(str, objArr);
        } catch (Exception e) {
            return "";
        }
    }
}
