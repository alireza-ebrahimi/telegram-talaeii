package com.google.firebase.iid;

public final class zzu extends Exception {
    private final int errorCode;

    public zzu(int i, String str) {
        super(str);
        this.errorCode = i;
    }

    public final int getErrorCode() {
        return this.errorCode;
    }
}
