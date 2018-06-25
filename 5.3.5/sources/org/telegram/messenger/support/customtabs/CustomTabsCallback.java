package org.telegram.messenger.support.customtabs;

import android.os.Bundle;

public class CustomTabsCallback {
    public static final int NAVIGATION_ABORTED = 4;
    public static final int NAVIGATION_FAILED = 3;
    public static final int NAVIGATION_FINISHED = 2;
    public static final int NAVIGATION_STARTED = 1;
    public static final int TAB_HIDDEN = 6;
    public static final int TAB_SHOWN = 5;

    public void onNavigationEvent(int navigationEvent, Bundle extras) {
    }

    public void extraCallback(String callbackName, Bundle args) {
    }

    public void onMessageChannelReady(Bundle extras) {
    }

    public void onPostMessage(String message, Bundle extras) {
    }
}
