package com.persianswitch.sdk.base.security;

public class DecryptionException extends SecurityException {
    /* renamed from: a */
    private final boolean f7096a;

    public DecryptionException(String str, boolean z) {
        super(str);
        this.f7096a = z;
    }

    /* renamed from: a */
    public boolean m10718a() {
        return this.f7096a;
    }
}
