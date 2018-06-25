package com.crashlytics.android.beta;

class ImmediateCheckForUpdatesController extends AbstractCheckForUpdatesController {
    public ImmediateCheckForUpdatesController() {
        super(true);
    }

    public boolean isActivityLifecycleTriggered() {
        return false;
    }
}
