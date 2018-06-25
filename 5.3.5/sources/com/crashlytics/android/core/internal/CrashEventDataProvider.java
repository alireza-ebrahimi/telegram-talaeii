package com.crashlytics.android.core.internal;

import com.crashlytics.android.core.internal.models.SessionEventData;

public interface CrashEventDataProvider {
    SessionEventData getCrashEventData();
}
