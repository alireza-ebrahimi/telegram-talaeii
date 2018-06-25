package com.onesignal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.onesignal.OneSignal.LOG_LEVEL;

class AdvertisingIdProviderGPS implements AdvertisingIdentifierProvider {
    private static String lastValue;

    AdvertisingIdProviderGPS() {
    }

    static String getLastValue() {
        return lastValue;
    }

    public String getIdentifier(Context appContext) {
        try {
            Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(appContext);
            if (adInfo.isLimitAdTrackingEnabled()) {
                lastValue = "OptedOut";
            } else {
                lastValue = adInfo.getId();
            }
            return lastValue;
        } catch (Throwable t) {
            OneSignal.Log(LOG_LEVEL.INFO, "Error getting Google Ad id: ", t);
            return null;
        }
    }
}
