package com.google.android.gms.iid;

public final class zzv extends Exception {
    private final int errorCode;

    public zzv(int i, String str) {
        super(str);
        this.errorCode = i;
    }

    public final int getErrorCode() {
        return this.errorCode;
    }
}
