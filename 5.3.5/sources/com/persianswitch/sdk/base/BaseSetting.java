package com.persianswitch.sdk.base;

import android.content.Context;
import com.persianswitch.sdk.api.Request.General;
import com.persianswitch.sdk.base.db.BaseDatabase;
import com.persianswitch.sdk.base.manager.LanguageManager;
import com.persianswitch.sdk.base.preference.IPreference;
import com.persianswitch.sdk.base.preference.SqlitePreference;
import com.persianswitch.sdk.base.preference.SqliteSecurePreference;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class BaseSetting {
    private static IPreference defaultPreference;
    private static String password;
    private static IPreference securePreference;

    public static void initSecurePreference(String password) {
        password = password;
        defaultPreference = null;
        securePreference = null;
    }

    private static IPreference getDefaultPreferences(Context context) {
        if (defaultPreference == null) {
            defaultPreference = new SqlitePreference(new BaseDatabase(context), "pref");
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
            securePreference = new SqliteSecurePreference(password, saltAs64, new BaseDatabase(context), "secure_pref");
        }
        return securePreference;
    }

    private static String getSaltAsBase64(Context context) {
        return getDefaultPreferences(context).getString("salt", null);
    }

    private static void setSaltAsBase64(Context context, String saltAsBase64) {
        getDefaultPreferences(context).putString("salt", saltAsBase64);
    }

    public static synchronized long getAndIncrementTranId(Context context) {
        long tranId;
        synchronized (BaseSetting.class) {
            tranId = getDefaultPreferences(context).getLong("last_tran_id", 1);
            getDefaultPreferences(context).putLong("last_tran_id", tranId + 1);
        }
        return tranId;
    }

    public static long getApplicationId(Context context) {
        return getSecurePreferences(context).getLong("application_id", 0);
    }

    public static void setApplicationId(Context context, long val) {
        getSecurePreferences(context).putLong("application_id", val);
    }

    public static String getIMEI(Context context) {
        return getSecurePreferences(context).getString("imei", "");
    }

    public static void setIMEI(Context context, String val) {
        getSecurePreferences(context).putString("imei", val);
    }

    public static String getWifiMAC(Context context) {
        return getSecurePreferences(context).getString("mac", "");
    }

    public static void setWifiMAC(Context context, String val) {
        getSecurePreferences(context).putString("mac", val);
    }

    public static String getApplicationToken(Context context) {
        return getSecurePreferences(context).getString("application_token", "");
    }

    public static void setApplicationToken(Context context, String val) {
        getSecurePreferences(context).putString("application_token", val);
    }

    public static int getHostId(Context context) {
        return getSecurePreferences(context).getInt(General.HOST_ID, 0);
    }

    public static void setHostId(Context context, int val) {
        getSecurePreferences(context).putInt(General.HOST_ID, val);
    }

    public static String getMobileNumber(Context context) {
        return getSecurePreferences(context).getString("mobile_number", "");
    }

    public static void setMobileNumber(Context context, String val) {
        getSecurePreferences(context).putString("mobile_number", val);
    }

    public static void setLanguage(Context context, String val) {
        getDefaultPreferences(context).putString("language", val);
    }

    public static String getLanguage(Context context) {
        return getDefaultPreferences(context).getString("language", LanguageManager.getDefaultLanguage());
    }

    public static long getLastTimeCardsSynced(Context context) {
        return getDefaultPreferences(context).getLong("last_time_cards_synced", 0);
    }

    public static void setLastTimeCardsSynced(Context context, long val) {
        getDefaultPreferences(context).putLong("last_time_cards_synced", val);
    }

    public static void setServerTimeOffset(Context context, long offsetAsSecond) {
        getDefaultPreferences(context).putLong("server_time_diff", offsetAsSecond);
    }

    public static long getServerTimeOffset(Context context) {
        return getDefaultPreferences(context).getLong("server_time_diff", 0);
    }

    public static void setNeedReVerification(Context context, boolean val) {
        getDefaultPreferences(context).putBoolean("need_re_verification", val);
    }

    public static boolean isNeedReVerification(Context context) {
        return getDefaultPreferences(context).getBoolean("need_re_verification", false);
    }
}
