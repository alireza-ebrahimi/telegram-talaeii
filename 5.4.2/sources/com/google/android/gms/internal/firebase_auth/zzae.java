package com.google.android.gms.internal.firebase_auth;

public enum zzae {
    REFRESH_TOKEN("refresh_token"),
    AUTHORIZATION_CODE("authorization_code");
    
    private final String value;

    private zzae(String str) {
        this.value = str;
    }

    public final String toString() {
        return this.value;
    }
}
