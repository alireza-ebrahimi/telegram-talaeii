package com.crashlytics.android.answers;

import android.os.Bundle;

public class FirebaseAnalyticsEvent {
    private final String eventName;
    private final Bundle eventParams;

    FirebaseAnalyticsEvent(String eventName, Bundle eventParams) {
        this.eventName = eventName;
        this.eventParams = eventParams;
    }

    public String getEventName() {
        return this.eventName;
    }

    public Bundle getEventParams() {
        return this.eventParams;
    }
}
