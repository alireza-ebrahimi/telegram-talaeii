package com.onesignal;

import android.content.Intent;
import android.os.Bundle;

/* compiled from: BundleCompat */
class BundleCompatBundle implements BundleCompat<Bundle> {
    private Bundle mBundle;

    BundleCompatBundle() {
        this.mBundle = new Bundle();
    }

    BundleCompatBundle(Bundle bundle) {
        this.mBundle = bundle;
    }

    BundleCompatBundle(Intent intent) {
        this.mBundle = intent.getExtras();
    }

    public void putString(String key, String value) {
        this.mBundle.putString(key, value);
    }

    public void putInt(String key, Integer value) {
        this.mBundle.putInt(key, value.intValue());
    }

    public void putLong(String key, Long value) {
        this.mBundle.putLong(key, value.longValue());
    }

    public void putBoolean(String key, Boolean value) {
        this.mBundle.putBoolean(key, value.booleanValue());
    }

    public String getString(String key) {
        return this.mBundle.getString(key);
    }

    public Integer getInt(String key) {
        return Integer.valueOf(this.mBundle.getInt(key));
    }

    public Long getLong(String key) {
        return Long.valueOf(this.mBundle.getLong(key));
    }

    public boolean getBoolean(String key) {
        return this.mBundle.getBoolean(key);
    }

    public boolean containsKey(String key) {
        return this.mBundle.containsKey(key);
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public boolean getBoolean(String key, boolean value) {
        return this.mBundle.getBoolean(key, value);
    }
}
