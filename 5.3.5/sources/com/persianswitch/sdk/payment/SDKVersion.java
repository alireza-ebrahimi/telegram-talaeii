package com.persianswitch.sdk.payment;

public enum SDKVersion {
    VERSION_UNDEFINED(0, ""),
    VERSION1(1, "1.8.0");
    
    private final String protocolVersion;
    private final int versionCode;

    private SDKVersion(int versionCode, String protocolVersion) {
        this.versionCode = versionCode;
        this.protocolVersion = protocolVersion;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public static SDKVersion getInstance(int versionCode) {
        for (SDKVersion sdkVersion : values()) {
            if (sdkVersion.getVersionCode() == versionCode) {
                return sdkVersion;
            }
        }
        return VERSION_UNDEFINED;
    }
}
