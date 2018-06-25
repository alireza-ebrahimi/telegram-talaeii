package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import com.persianswitch.sdk.base.db.BaseDatabase;
import com.persianswitch.sdk.payment.database.SDKDatabase;

public final class DataManager {
    public static void wipeAllData(Context context) {
        new BaseDatabase(context).clearAllData();
        new SDKDatabase(context).clearAllData();
    }
}
