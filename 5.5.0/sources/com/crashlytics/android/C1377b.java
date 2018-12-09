package com.crashlytics.android;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.crashlytics.android.CrashlyticsInitProvider.C1325a;

/* renamed from: com.crashlytics.android.b */
class C1377b implements C1325a {
    C1377b() {
    }

    /* renamed from: a */
    public boolean mo1148a(Context context) {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            return bundle == null || bundle.getBoolean("firebase_crashlytics_collection_enabled", true);
        } catch (NameNotFoundException e) {
            return true;
        }
    }
}
