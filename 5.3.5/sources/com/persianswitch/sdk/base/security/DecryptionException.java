package com.persianswitch.sdk.base.security;

public class DecryptionException extends SecurityException {
    private final boolean noData;

    public DecryptionException(boolean noData) {
        this.noData = noData;
    }

    public DecryptionException(String message, boolean noData) {
        super(message);
        this.noData = noData;
    }

    public boolean isNoData() {
        return this.noData;
    }
}
