package com.persianswitch.sdk.payment;

public enum SDKVersion {
    VERSION_UNDEFINED(0, TtmlNode.ANONYMOUS_REGION_ID),
    VERSION1(1, "1.8.0");
    
    /* renamed from: c */
    private final int f7376c;
    /* renamed from: d */
    private final String f7377d;

    private SDKVersion(int i, String str) {
        this.f7376c = i;
        this.f7377d = str;
    }
}
