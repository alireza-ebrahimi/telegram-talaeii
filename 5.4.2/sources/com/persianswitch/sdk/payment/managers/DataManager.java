package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import com.persianswitch.sdk.base.db.BaseDatabase;
import com.persianswitch.sdk.payment.database.SDKDatabase;

public final class DataManager {
    /* renamed from: a */
    public static void m11080a(Context context) {
        new BaseDatabase(context).m10491a();
        new SDKDatabase(context).m11060a();
    }
}
