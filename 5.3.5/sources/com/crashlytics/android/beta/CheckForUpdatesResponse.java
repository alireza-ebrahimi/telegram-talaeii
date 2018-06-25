package com.crashlytics.android.beta;

class CheckForUpdatesResponse {
    public final String buildVersion;
    public final String displayVersion;
    public final String instanceId;
    public final String packageName;
    public final String url;
    public final String versionString;

    public CheckForUpdatesResponse(String url, String versionString, String displayVersion, String buildVersion, String packageName, String instanceId) {
        this.url = url;
        this.versionString = versionString;
        this.displayVersion = displayVersion;
        this.buildVersion = buildVersion;
        this.packageName = packageName;
        this.instanceId = instanceId;
    }
}
