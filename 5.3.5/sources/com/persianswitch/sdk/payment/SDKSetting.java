package com.persianswitch.sdk.payment;

import android.content.Context;
import com.persianswitch.sdk.base.preference.IPreference;
import com.persianswitch.sdk.base.preference.SqlitePreference;
import com.persianswitch.sdk.base.preference.SqliteSecurePreference;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.payment.database.SDKDatabase;

public final class SDKSetting {
    private static SqlitePreference defaultPreference;
    private static String password;
    private static SqlitePreference securePreference;

    public static void initSecurePreference(String password) {
        password = password;
        defaultPreference = null;
        securePreference = null;
    }

    private static IPreference getDefaultPreferences(Context context) {
        if (defaultPreference == null) {
            defaultPreference = new SqlitePreference(new SDKDatabase(context), "pref");
        }
        return defaultPreference;
    }

    public static IPreference getSecurePreferences(Context context) {
        if (securePreference == null) {
            String saltAs64 = getSaltAsBase64(context);
            if (StringUtils.isEmpty(saltAs64)) {
                saltAs64 = SqliteSecurePreference.generateSalt();
                setSaltAsBase64(context, saltAs64);
            }
            securePreference = new SqliteSecurePreference(password, saltAs64, new SDKDatabase(context), "secure_pref");
        }
        return securePreference;
    }

    private static String getSaltAsBase64(Context context) {
        return getDefaultPreferences(context).getString("salt", null);
    }

    private static void setSaltAsBase64(Context context, String saltAsBase64) {
        getDefaultPreferences(context).putString("salt", saltAsBase64);
    }

    public static boolean getRootWarningShowed(Context context) {
        return getSecurePreferences(context).getBoolean("rootWarningShowed", false);
    }

    public static void setRootWarningShowed(Context context, boolean val) {
        getSecurePreferences(context).putBoolean("rootWarningShowed", val);
    }

    public static boolean getNumberValidateDialogIsShowed(Context context) {
        return getSecurePreferences(context).getBoolean("numberValidatedIsShowed", false);
    }

    public static void setNumberValidateDialogIsShowed(Context context, boolean val) {
        getSecurePreferences(context).putBoolean("numberValidatedIsShowed", val);
    }
}
