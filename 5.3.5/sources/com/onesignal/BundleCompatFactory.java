package com.onesignal;

import android.os.Build.VERSION;

/* compiled from: BundleCompat */
class BundleCompatFactory {
    BundleCompatFactory() {
    }

    static BundleCompat getInstance() {
        if (VERSION.SDK_INT >= 26) {
            return new BundleCompatPersistableBundle();
        }
        return new BundleCompatBundle();
    }
}
