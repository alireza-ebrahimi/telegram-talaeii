package com.persianswitch.sdk.base.manager;

import android.content.Context;
import com.persianswitch.sdk.base.BaseSetting;

public final class RequestTimeManager {
    /* renamed from: a */
    public long m10679a(Context context) {
        return (System.currentTimeMillis() / 1000) + BaseSetting.m10474k(context);
    }

    /* renamed from: a */
    public void m10680a(Context context, Long l) {
        if (l != null) {
            BaseSetting.m10462c(context, l.longValue() - (System.currentTimeMillis() / 1000));
        }
    }
}
