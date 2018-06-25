package org.telegram.messenger.support.customtabs;

import android.os.Bundle;
import android.os.IBinder;
import org.telegram.messenger.support.customtabs.ICustomTabsCallback.Stub;

class CustomTabsSessionToken$DummyCallback extends Stub {
    CustomTabsSessionToken$DummyCallback() {
    }

    public void onNavigationEvent(int navigationEvent, Bundle extras) {
    }

    public void extraCallback(String callbackName, Bundle args) {
    }

    public void onMessageChannelReady(Bundle extras) {
    }

    public void onPostMessage(String message, Bundle extras) {
    }

    public IBinder asBinder() {
        return this;
    }
}
