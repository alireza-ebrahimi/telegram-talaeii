package net.hockeyapp.android;

public abstract class CrashManagerListener {
    public boolean ignoreDefaultHandler() {
        return false;
    }

    public boolean includeDeviceData() {
        return true;
    }

    public boolean includeDeviceIdentifier() {
        return true;
    }

    public boolean includeThreadDetails() {
        return true;
    }

    public String getContact() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public String getUserID() {
        return null;
    }

    public boolean onCrashesFound() {
        return false;
    }

    public boolean shouldAutoUploadCrashes() {
        return false;
    }

    public void onNewCrashesFound() {
    }

    public void onConfirmedCrashesFound() {
    }

    public void onCrashesSent() {
    }

    public void onCrashesNotSent() {
    }

    public void onUserDeniedCrashes() {
    }

    public int getMaxRetryAttempts() {
        return 1;
    }

    public boolean onHandleAlertView() {
        return false;
    }
}
