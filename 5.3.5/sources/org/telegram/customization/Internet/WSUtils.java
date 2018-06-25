package org.telegram.customization.Internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.ArrayList;
import utils.app.AppPreferences;
import utils.view.Constants;

public class WSUtils {
    public static final ArrayList<StringPair> jsonReplace = new ArrayList();

    private static class StringPair {
        public String str1;
        public String str2;

        public StringPair(String s1, String s2) {
            this.str1 = s1;
            this.str2 = s2;
        }

        public StringPair() {
            this.str1 = "";
            this.str2 = "";
        }
    }

    static {
        jsonReplace.add(new StringPair("creator", "publisher"));
        jsonReplace.add(new StringPair("downloadCount", "dl_count"));
        jsonReplace.add(new StringPair("imageLinkOnServer", "image_link"));
    }

    public static final String renameJSON(String json) {
        String ans = json;
        for (int i = 0; i < jsonReplace.size(); i++) {
            if (((StringPair) jsonReplace.get(i)).str1.compareTo(((StringPair) jsonReplace.get(i)).str2) != 0) {
                ans = ans.replace("\"" + ((StringPair) jsonReplace.get(i)).str1 + "\":", "\"" + ((StringPair) jsonReplace.get(i)).str2 + "\":");
            }
        }
        return ans;
    }

    public static String changeDomain(int retryCount, String url, Context context, String currentWebservice, String setKey) {
        url = url.replace("http://", "").replace("https://", "");
        url = url.replace(url.substring(0, url.indexOf("/") + 1), "");
        if (currentWebservice.contentEquals(Constants.KEY_SETTING_LIGHT_PROXY_API)) {
            if (retryCount > AppPreferences.getDomainsWithSetKey(context, setKey).size()) {
                return null;
            }
            return url;
        } else if (retryCount > 5) {
            return null;
        } else {
            return url;
        }
    }

    public static int defaultTimeOut(Context context) {
        return AppPreferences.getTimeout(context);
    }

    public static String getCarrier(Context context) {
        try {
            String carrierName = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
            if (!TextUtils.isEmpty(carrierName)) {
                return carrierName;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static boolean isOnMobileData(Context context) {
        boolean z = false;
        try {
            z = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0).isAvailable();
        } catch (Exception e) {
        }
        return z;
    }
}
