package com.crashlytics.android.answers;

interface EventFilter {
    boolean skipEvent(SessionEvent sessionEvent);
}
