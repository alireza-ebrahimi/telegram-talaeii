package com.crashlytics.android.answers;

class KeepAllEventFilter implements EventFilter {
    KeepAllEventFilter() {
    }

    public boolean skipEvent(SessionEvent sessionEvent) {
        return false;
    }
}
