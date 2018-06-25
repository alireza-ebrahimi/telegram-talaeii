package com.persianswitch.sdk.base.manager;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;

public final class RequestTimeManager {
    public long getSyncedTime(Context context) {
        return (System.currentTimeMillis() / 1000) + BaseSetting.getServerTimeOffset(context);
    }

    public void syncTimeByServer(Context context, Long optServerTime) {
        if (optServerTime != null) {
            BaseSetting.setServerTimeOffset(context, optServerTime.longValue() - (System.currentTimeMillis() / 1000));
        }
    }
}
