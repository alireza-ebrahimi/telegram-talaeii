package org.telegram.messenger.voip;

import java.util.Iterator;
import org.json.JSONObject;
import org.telegram.messenger.FileLog;

public class VoIPServerConfig {
    private static JSONObject config;

    public static boolean getBoolean(String str, boolean z) {
        return config.optBoolean(str, z);
    }

    public static double getDouble(String str, double d) {
        return config.optDouble(str, d);
    }

    public static int getInt(String str, int i) {
        return config.optInt(str, i);
    }

    public static String getString(String str, String str2) {
        return config.optString(str, str2);
    }

    private static native void nativeSetConfig(String[] strArr, String[] strArr2);

    public static void setConfig(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            config = jSONObject;
            String[] strArr = new String[jSONObject.length()];
            String[] strArr2 = new String[jSONObject.length()];
            Iterator keys = jSONObject.keys();
            int i = 0;
            while (keys.hasNext()) {
                strArr[i] = (String) keys.next();
                strArr2[i] = jSONObject.getString(strArr[i]);
                i++;
            }
            nativeSetConfig(strArr, strArr2);
        } catch (Throwable e) {
            FileLog.m13727e("Error parsing VoIP config", e);
        }
    }
}
